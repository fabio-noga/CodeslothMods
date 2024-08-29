package com.codesloth.easiershulkers.ShulkerBoxHand;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class ShulkerBoxHand extends ShulkerBoxBlockEntity {

    private int openCount;
    private ItemStack shulkerBoxItem;

    public ShulkerBoxHand(@Nullable DyeColor pColor, BlockPos pPos, BlockState pBlockState) {
        super(pColor, pPos, pBlockState);
    }

    public ShulkerBoxHand(BlockPos pPos, BlockState pBlockState, Player player, ItemStack shulkerBoxItem) {
        this(DyeColor.WHITE, pPos, pBlockState);
        this.level = player.level();
        this.shulkerBoxItem = shulkerBoxItem;
    }

    public void openShulker(Player player) {
        this.openCount++;
        super.startOpen(player);
        player.openMenu(this);
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pId, @NotNull Inventory pPlayer) {
        return new ShulkerBoxHandMenu(pId, pPlayer, this);
    }

    @Override
    public void stopOpen(@NotNull Player pPlayer) {
        super.stopOpen(pPlayer);
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openCount--;
            if (this.openCount <= 0) {
                saveShulker(pPlayer);
            }
        }
    }

    private void saveShulker(Player pPlayer) {
        ItemStack itemStack = this.shulkerBoxItem;
        itemStack.applyComponents(this.collectComponents());
        BlockPos pPos = pPlayer.getOnPos();
        ItemEntity itementity = new ItemEntity(
                pPlayer.level(), (double)pPos.getX() + 0.5, (double)pPos.getY() + 0.5, (double)pPos.getZ() + 0.5, itemStack
        );
        itementity.setDefaultPickUpDelay();
        pPlayer.setItemInHand(pPlayer.getUsedItemHand(), itemStack);
    }
}
