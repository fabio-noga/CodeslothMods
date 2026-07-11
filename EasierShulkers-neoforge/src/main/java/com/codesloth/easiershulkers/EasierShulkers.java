package com.codesloth.easiershulkers;

import com.codesloth.easiershulkers.network.OpenOffHandShulkerPayload;
import com.codesloth.easiershulkers.network.PlaceOffHandShulkerPayload;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(EasierShulkers.MOD_ID)
public class EasierShulkers {

    public static final String MOD_ID = "easiershulkers";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EasierShulkers(IEventBus modEventBus) {
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::registerPayloads);

        NeoForge.EVENT_BUS.register(new Events());
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(OpenOffHandShulkerPayload.TYPE, OpenOffHandShulkerPayload.STREAM_CODEC, this::handleOpenOffHandShulker);
        registrar.playToServer(PlaceOffHandShulkerPayload.TYPE, PlaceOffHandShulkerPayload.STREAM_CODEC, this::handlePlaceOffHandShulker);
    }

    private void handleOpenOffHandShulker(final OpenOffHandShulkerPayload payload, final IPayloadContext context) {
        ItemStack offHandStack = Utils.getShulkerBox(context.player(), InteractionHand.OFF_HAND);
        if (offHandStack != null) {
            Utils.openShulkerBox(context.player(), offHandStack, InteractionHand.OFF_HAND);
        }
    }

    private void handlePlaceOffHandShulker(final PlaceOffHandShulkerPayload payload, final IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ItemStack stack = Utils.getShulkerBox(serverPlayer, InteractionHand.OFF_HAND);
        if (stack == null) {
            return;
        }

        Vec3 eyePos = serverPlayer.getEyePosition();
        Vec3 reachPos = eyePos.add(serverPlayer.getViewVector(1.0F).scale(serverPlayer.blockInteractionRange()));
        ClipContext clipContext = new ClipContext(eyePos, reachPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, serverPlayer);
        BlockHitResult hitResult = serverPlayer.level().clip(clipContext);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        InteractionResult result = stack.useOn(new UseOnContext(serverPlayer, InteractionHand.OFF_HAND, hitResult));
        if (result.consumesAction()) {
            serverPlayer.swing(InteractionHand.OFF_HAND);
        }
    }
}
