package com.codesloth.easiershulkers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Objects;

public class Events {

    public static boolean IS_SERVER;

    @SubscribeEvent
    public void onPlaceBlock(PlayerInteractEvent.RightClickBlock event){
        try{
            boolean isLan = Objects.requireNonNull(Minecraft.getInstance().getCurrentServer()).isLan();
            if (IS_SERVER == isLan){
                IS_SERVER = !isLan;
            }
        } catch (Exception ignore){}

        Player player = event.getEntity();
        if(IS_SERVER || player == null || player.containerMenu instanceof ShulkerBoxMenu)
            return;

        ItemStack mainHandItem = player.getMainHandItem();
        if(!Utils.isShulkerBox(mainHandItem)) {
            // Vanilla falls back to the off-hand item when the main hand does nothing. Off-hand
            // shulkers are handled exclusively via the dedicated keybinds now, so suppress that
            // fallback here rather than let it place/open automatically.
            if (event.getHand() == InteractionHand.OFF_HAND && Utils.isShulkerBox(player.getOffhandItem())) {
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
            return;
        }

        if (!player.isShiftKeyDown()) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            Utils.openShulkerBox(player, mainHandItem, InteractionHand.MAIN_HAND);
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        try{
            boolean isLan = Objects.requireNonNull(Minecraft.getInstance().getCurrentServer()).isLan();
            if (IS_SERVER == isLan){
                IS_SERVER = !isLan;
            }
        } catch (Exception ignore){}

        Player player = event.getEntity();
        if (IS_SERVER || player.containerMenu instanceof ShulkerBoxMenu)
            return;

        InteractionHand hand = event.getHand();
        // Off-hand shulkers only open via the dedicated "Open Off-Hand Shulker Box" keybind now,
        // not automatically on right-click -- see EasierShulkers#handleOpenOffHandShulker.
        if (!hand.equals(InteractionHand.MAIN_HAND))
            return;

        ItemStack stack = Utils.getShulkerBox(player, hand);
        if (stack != null) {
            Utils.openShulkerBox(player, stack, hand);
        }
    }

}
