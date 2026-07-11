package com.codesloth.easiershulkers.ShulkerBoxHand;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ContainerUser;
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
    private InteractionHand hand;

    public ShulkerBoxHand(@Nullable DyeColor pColor, BlockPos pPos, BlockState pBlockState) {
        super(pColor, pPos, pBlockState);
    }

    public ShulkerBoxHand(BlockPos pPos, BlockState pBlockState, Player player, ItemStack shulkerBoxItem, InteractionHand hand) {
        this(DyeColor.WHITE, pPos, pBlockState);
        this.level = player.level();
        this.shulkerBoxItem = shulkerBoxItem;
        this.hand = hand;
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
    public void stopOpen(@NotNull ContainerUser containerUser) {
        super.stopOpen(containerUser);
        if (!this.remove && !containerUser.getLivingEntity().isSpectator() && containerUser instanceof Player pPlayer) {
            this.openCount--;
            if (this.openCount <= 0) {
                saveShulker(pPlayer);
            }
        }
    }

    private void saveShulker(Player pPlayer) {
        ItemStack itemStack = this.shulkerBoxItem;
        itemStack.applyComponents(this.collectComponents());
        pPlayer.setItemInHand(this.hand, itemStack);
    }
}
