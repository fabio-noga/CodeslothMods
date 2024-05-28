package com.codesloth.betterfarming;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Events {

    @SubscribeEvent
    public void onPlayerUser(PlayerInteractEvent.RightClickBlock event) {
        if (harvest(event.getPos(), event.getEntity())) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    private boolean harvest(BlockPos pos, Player player) {
        Level world = player.level();
        BlockState state = world.getBlockState(pos);
        Block blockClicked = state.getBlock();

        BonemealableBlock growable = getGrowable(blockClicked);

        if (growable == null || growable.isValidBonemealTarget(world, pos, state)) {
            return false;
        }

        if (world.isClientSide || !(world instanceof ServerLevel)) {
            return true;
        }

        ItemStack mainHandItem = player.getMainHandItem();

        LootParams.Builder context = new LootParams.Builder((ServerLevel) world)
                .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.TOOL, mainHandItem);

        List<ItemStack> drops = state.getDrops(context);

        if (mainHandItem.getItem() instanceof HoeItem ||
            mainHandItem.getItem() instanceof BoneMealItem ||
            containsDrop(drops, mainHandItem.getItem()))
        {
            BlockState newState = blockClicked.defaultBlockState();

            if (state.getProperties().stream().anyMatch(p -> p.equals(BlockStateProperties.HORIZONTAL_FACING))) {
                newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
            }

            if (state.getProperties().stream().anyMatch(p -> p.equals(BlockStateProperties.AGE_7))) {
                newState = state.setValue(BlockStateProperties.AGE_7, 0);
            }

            world.setBlockAndUpdate(pos, newState);

            drops.stream()
                    .filter(o -> o.getItem().equals(blockClicked.asItem()))
                    .findFirst()
                    .ifPresent(sameItemAsClicked -> drops.get(drops.indexOf(sameItemAsClicked)).setCount(sameItemAsClicked.getCount() - 1));

            for (ItemStack stack : drops) {
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
            return true;
        }
        return false;
    }

    private boolean containsDrop(List<ItemStack> drops, Item mainHandItem) {
        return drops.stream().anyMatch(o -> o.getItem().equals(mainHandItem));
    }

    private BonemealableBlock getGrowable(Block blockClicked) {

        if (blockClicked instanceof CropBlock ||
            blockClicked instanceof CocoaBlock)
        {
            return (BonemealableBlock) blockClicked;
        }

        if (blockClicked instanceof NetherWartBlock) {
            return new BonemealableBlock() {
                @Override
                public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
                    return state.getValue(NetherWartBlock.AGE) < NetherWartBlock.MAX_AGE;
                }

                @Override
                public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) {
                    return false;
                }

                @Override
                public void performBonemeal(ServerLevel p_220874_, RandomSource p_220875_, BlockPos p_220876_, BlockState p_220877_) {

                }
            };
        }
        return null;
    }
}
