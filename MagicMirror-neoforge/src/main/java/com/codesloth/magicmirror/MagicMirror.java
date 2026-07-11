package com.codesloth.magicmirror;

import com.codesloth.magicmirror.event.SoundEvents;
import com.codesloth.magicmirror.item.ModCreativeModeTab;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(MagicMirror.MOD_ID)
public class MagicMirror
{
    public static final String MOD_ID = "magicmirror";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MagicMirror(IEventBus modEventBus)
    {
        modEventBus.addListener(this::setup);
        ModCreativeModeTab.register(modEventBus);
        SoundEvents.register(modEventBus);
        modEventBus.addListener(ModCreativeModeTab::addCreative);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
    }
}
