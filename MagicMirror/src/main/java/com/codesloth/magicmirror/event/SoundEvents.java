package com.codesloth.magicmirror.event;

import com.codesloth.magicmirror.MagicMirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MagicMirror.MOD_ID);

    public static final RegistryObject<SoundEvent> TELEPORT =
            registerSoundEvent("teleport");

    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name,
                () -> new SoundEvent(( new ResourceLocation(MagicMirror.MOD_ID, name))));
    }

    public static void register (IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
