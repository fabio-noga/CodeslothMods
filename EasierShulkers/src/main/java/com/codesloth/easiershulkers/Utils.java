package com.codesloth.easiershulkers;

import com.codesloth.easiershulkers.ShulkerBoxHand.ShulkerBoxHand;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {

    public static ItemStack getShulkerBox(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (isShulkerBox(stack)) {
            return stack;
        }
        return null;
    }

    public static boolean isShulkerBox(ItemStack stack) {
        return stack != null && Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock;
    }

    public static void openShulkerBox(Player player, ItemStack stack) {
        if (!player.level().isClientSide && player instanceof ServerPlayer) {
            ShulkerBoxHand shulkerBox = createShulkerBox(player, stack);
            shulkerBox.applyComponentsFromItemStack(stack);
            shulkerBox.openShulker(player);
        }
    }

    private static ShulkerBoxHand createShulkerBox(Player player, ItemStack stack) {
        BlockPos pPos = player.getOnPos();
        BlockState pBlockState = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
        return new ShulkerBoxHand(pPos, pBlockState, player, stack);
    }

}
