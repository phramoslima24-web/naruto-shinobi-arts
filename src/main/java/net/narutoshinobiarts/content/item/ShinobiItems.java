package net.narutoshinobiarts.content.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.narutoshinobiarts.NarutotoShinobiArts;

public final class ShinobiItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NarutotoShinobiArts.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NarutotoShinobiArts.MODID);
    public static final RegistryObject<Item> RASENGAN = jutsu("rasengan", JutsuScrollItem.Technique.RASENGAN);
    public static final RegistryObject<Item> CHIDORI = jutsu("chidori", JutsuScrollItem.Technique.CHIDORI);
    public static final RegistryObject<Item> FIREBALL = jutsu("fireball", JutsuScrollItem.Technique.FIREBALL);
    public static final RegistryObject<Item> WATER_DRAGON = jutsu("water_dragon", JutsuScrollItem.Technique.WATER_DRAGON);
    public static final RegistryObject<Item> EARTH_DRAGON = jutsu("earth_dragon", JutsuScrollItem.Technique.EARTH_DRAGON);
    public static final RegistryObject<Item> TAIJUTSU = jutsu("taijutsu", JutsuScrollItem.Technique.TAIJUTSU);
    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("shinobi_arts", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.narutoto_shinobi_arts"))
            .icon(() -> RASENGAN.get().getDefaultInstance())
            .displayItems((parameters, output) -> { output.accept(RASENGAN.get()); output.accept(CHIDORI.get()); output.accept(FIREBALL.get()); output.accept(WATER_DRAGON.get()); output.accept(EARTH_DRAGON.get()); output.accept(TAIJUTSU.get()); })
            .build());
    private static RegistryObject<Item> jutsu(String id, JutsuScrollItem.Technique technique) {
        return ITEMS.register(id, () -> new JutsuScrollItem(technique, new Item.Properties().stacksTo(1)));
    }
    private ShinobiItems() {}
}
