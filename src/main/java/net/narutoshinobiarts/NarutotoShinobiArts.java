package net.narutoshinobiarts;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.narutoshinobiarts.content.item.ShinobiItems;

@Mod(NarutotoShinobiArts.MODID)
public class NarutotoShinobiArts {
    public static final String MODID = "narutoto_shinobi_arts";
    public NarutotoShinobiArts() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ShinobiItems.ITEMS.register(bus);
        ShinobiItems.TABS.register(bus);
    }
}
