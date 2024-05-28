package com.codesloth.magicmirror.event;

import com.codesloth.magicmirror.MagicMirror;
import com.codesloth.magicmirror.item.ModCreativeModeTab;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MagicMirror.MOD_ID)
public class Events {

    @SubscribeEvent
    public static void addCustomTrade(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CLERIC) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            int villagerLevel = 5;

            trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 3),
                    new ItemStack(Items.GLASS_PANE, 4),
                    new ItemStack(ModCreativeModeTab.MAGIC_MIRROR_ITEM.get(), 1),
                    1, 8, 0));
        }
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();
        rareTrades.add((trader,rand) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 3),
                new ItemStack(Items.GLASS_PANE, 4),
                new ItemStack(ModCreativeModeTab.MAGIC_MIRROR_ITEM.get(), 1),
                1, 8, 0));
    }
}
