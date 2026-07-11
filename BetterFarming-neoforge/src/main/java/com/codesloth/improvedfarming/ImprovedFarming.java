package com.codesloth.improvedfarming;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ImprovedFarming.MOD_ID)
public class ImprovedFarming {
    public static final String MOD_ID = "improvedfarming";

    public ImprovedFarming(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        NeoForge.EVENT_BUS.register(new Events());
    }
}
