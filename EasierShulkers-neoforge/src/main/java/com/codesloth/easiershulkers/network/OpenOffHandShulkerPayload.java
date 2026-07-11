package com.codesloth.easiershulkers.network;

import com.codesloth.easiershulkers.EasierShulkers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenOffHandShulkerPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenOffHandShulkerPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(EasierShulkers.MOD_ID, "open_offhand_shulker"));

    public static final StreamCodec<FriendlyByteBuf, OpenOffHandShulkerPayload> STREAM_CODEC =
            StreamCodec.unit(new OpenOffHandShulkerPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
