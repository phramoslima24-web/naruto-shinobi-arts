package net.narutoshinobiarts.content.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import java.util.List;

public class JutsuScrollItem extends Item {
    public enum Technique { RASENGAN, CHIDORI, FIREBALL, WATER_DRAGON, EARTH_DRAGON, TAIJUTSU }
    private final Technique technique;
    public JutsuScrollItem(Technique technique, Properties properties) { super(properties); this.technique = technique; }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.success(stack);
        int cooldown = technique == Technique.TAIJUTSU ? 80 : 45;
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.fail(stack);
        Vec3 look = player.getLookAngle().normalize();
        Vec3 center = player.position().add(0, player.getBbHeight() * 0.65, 0).add(look.scale(technique == Technique.TAIJUTSU ? 0.0 : 2.4));
        ServerLevel server = (ServerLevel) level;
        switch (technique) {
            case TAIJUTSU -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 18, 1));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 18, 0));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 20 * 18, 0));
                player.displayClientMessage(Component.literal("§bModo Taijutsu ativado! §7(18 segundos)"), true);
                server.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY()+0.2, player.getZ(), 18, .5,.2,.5,.08);
            }
            case RASENGAN, CHIDORI -> {
                player.setDeltaMovement(player.getDeltaMovement().add(look.scale(0.95)));
                player.hurtMarked = true;
                damageInFront(level, player, center, technique == Technique.RASENGAN ? 2.5 : 2.0,
                        technique == Technique.RASENGAN ? 11.0f : 9.0f, technique == Technique.CHIDORI);
                if (technique == Technique.RASENGAN) {
                    server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, center.x, center.y, center.z, 35, .45,.45,.45,.06);
                    server.sendParticles(ParticleTypes.CLOUD, center.x, center.y, center.z, 24, .5,.4,.5,.08);
                    level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 1.0f, 1.25f);
                } else {
                    server.sendParticles(ParticleTypes.ELECTRIC_SPARK, center.x, center.y, center.z, 55, .55,.55,.55,.12);
                    level.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THUNDER, SoundSource.PLAYERS, .65f, 1.6f);
                }
            }
            case FIREBALL -> {
                damageInFront(level, player, center, 4.0, 8.0f, false);
                server.sendParticles(ParticleTypes.FLAME, center.x, center.y, center.z, 85, 1.1,.8,1.1,.06);
                server.sendParticles(ParticleTypes.LAVA, center.x, center.y, center.z, 12, .7,.5,.7,.02);
                level.playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0f, .85f);
            }
            case WATER_DRAGON -> {
                damageInFront(level, player, center, 3.5, 7.0f, false);
                server.sendParticles(ParticleTypes.SPLASH, center.x, center.y, center.z, 95, .8,.7,.8,.18);
                server.sendParticles(ParticleTypes.BUBBLE, center.x, center.y, center.z, 30, .7,.6,.7,.08);
                level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_SPLASH, SoundSource.PLAYERS, 1.0f, .75f);
            }
            case EARTH_DRAGON -> {
                damageInFront(level, player, center, 3.5, 8.0f, false);
                server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, net.minecraft.world.level.block.Blocks.STONE.defaultBlockState()), center.x, center.y, center.z, 65, .8,.5,.8,.18);
                server.sendParticles(ParticleTypes.CLOUD, center.x, center.y, center.z, 30, .7,.4,.7,.08);
                level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, .6f, 1.25f);
            }
        }
        player.getCooldowns().addCooldown(this, cooldown);
        return InteractionResultHolder.consume(stack);
    }

    private static void damageInFront(Level level, Player player, Vec3 center, double radius, float damage, boolean stun) {
        AABB area = new AABB(center.x-radius, center.y-radius, center.z-radius, center.x+radius, center.y+radius, center.z+radius);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, e -> e != player && e.isAlive());
        for (LivingEntity target : targets) {
            Vec3 delta = target.position().subtract(player.position()).normalize();
            if (delta.dot(player.getLookAngle()) > 0.15) {
                target.hurt(level.damageSources().playerAttack(player), damage);
                target.knockback(stun ? 1.1 : .65, player.getX()-target.getX(), player.getZ()-target.getZ());
                if (stun) target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 2));
                if (damage > 9) target.setSecondsOnFire(2);
            }
        }
    }
}
