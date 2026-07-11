package com.codesloth.easiershulkers;

import com.codesloth.easiershulkers.network.OpenOffHandShulkerPayload;
import com.codesloth.easiershulkers.network.PlaceOffHandShulkerPayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = EasierShulkers.MOD_ID, dist = Dist.CLIENT)
public class EasierShulkersClient {

    public static final KeyMapping.Category CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(EasierShulkers.MOD_ID, "shulkers"));

    public static final KeyMapping OPEN_OFFHAND_SHULKER = new KeyMapping(
            "key.easiershulkers.open_offhand_shulker",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            CATEGORY
    );

    public static final KeyMapping PLACE_OFFHAND_SHULKER = new KeyMapping(
            "key.easiershulkers.place_offhand_shulker",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            CATEGORY
    );

    public EasierShulkersClient(IEventBus modEventBus) {
        modEventBus.addListener(this::registerKeyMappings);
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(OPEN_OFFHAND_SHULKER);
        event.register(PLACE_OFFHAND_SHULKER);
    }

    private void onClientTick(ClientTickEvent.Post event) {
        while (OPEN_OFFHAND_SHULKER.consumeClick()) {
            ClientPacketDistributor.sendToServer(new OpenOffHandShulkerPayload());
        }
        while (PLACE_OFFHAND_SHULKER.consumeClick()) {
            ClientPacketDistributor.sendToServer(new PlaceOffHandShulkerPayload());
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.swing(InteractionHand.OFF_HAND);
            }
        }
    }
}
