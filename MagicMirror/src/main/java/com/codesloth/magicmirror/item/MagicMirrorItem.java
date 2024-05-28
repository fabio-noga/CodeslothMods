package com.codesloth.magicmirror.item;

import com.codesloth.magicmirror.event.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagicMirrorItem extends Item {
    public MagicMirrorItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem().equals(ModCreativeModeTab.MAGIC_MIRROR_ITEM.get())) {
            if (level.dimension().location().toString().equals("minecraft:overworld")) {
                if(!level.isClientSide && hand.equals(InteractionHand.MAIN_HAND)) {
                    MinecraftServer server = level.getServer();
                    assert server != null;
                    ServerPlayer serverPlayer = server.getPlayerList().getPlayer(player.getUUID());
                    assert serverPlayer != null;
                    BlockPos respawnPosition = serverPlayer.getRespawnPosition();

                    if (respawnPosition == null)
                        respawnPosition = server.overworld().getSharedSpawnPos();
                    player.teleportTo(respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ());
                    player.getCooldowns().addCooldown(mainHandItem.getItem(), 20);
                } else {
                    player.playSound(SoundEvents.TELEPORT.get());
                }
            }
        }
        return super.use(level, player, hand);
    }
}
