package com.codesloth.easiershulkers.network;

import com.codesloth.easiershulkers.EasierShulkers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PlaceOffHandShulkerPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlaceOffHandShulkerPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(EasierShulkers.MOD_ID, "place_offhand_shulker"));

    public static final StreamCodec<FriendlyByteBuf, PlaceOffHandShulkerPayload> STREAM_CODEC =
            StreamCodec.unit(new PlaceOffHandShulkerPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
