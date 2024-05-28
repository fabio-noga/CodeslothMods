package com.codesloth.magicmirror;

import com.codesloth.magicmirror.event.SoundEvents;
import com.codesloth.magicmirror.item.ModCreativeModeTab;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MagicMirror.MOD_ID)
public class MagicMirror
{
    public static final String MOD_ID = "magicmirror";
    private static final Logger LOGGER = LogUtils.getLogger();
    public MagicMirror()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeModeTab.register(modEventBus);
        SoundEvents.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(ModCreativeModeTab::addCreative);
    }
}
