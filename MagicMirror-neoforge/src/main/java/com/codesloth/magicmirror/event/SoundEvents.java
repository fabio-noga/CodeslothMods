package com.codesloth.magicmirror.event;

import com.codesloth.magicmirror.MagicMirror;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, MagicMirror.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TELEPORT =
            registerSoundEvent("teleport");

    public static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(MagicMirror.MOD_ID, name)));
    }

    public static void register (IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
