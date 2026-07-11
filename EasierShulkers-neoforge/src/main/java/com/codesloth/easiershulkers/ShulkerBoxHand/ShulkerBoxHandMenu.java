package com.codesloth.easiershulkers.ShulkerBoxHand;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ShulkerBoxMenu;

public class ShulkerBoxHandMenu extends ShulkerBoxMenu {

    public ShulkerBoxHandMenu(int pContainerId, Inventory pPlayerInventory, Container pContainer) {
        super(pContainerId, pPlayerInventory, pContainer);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
