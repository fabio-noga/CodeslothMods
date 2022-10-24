package com.codesloth.magicmirror.item;

import com.codesloth.magicmirror.MagicMirror;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagicMirror.MOD_ID);

    public static final RegistryObject<Item> MAGIC_MIRROR_ITEM = ITEMS.register("magicmirror",
            () -> new MagicMirrorItem(new Item.Properties().tab(CreativeModeTab.TAB_BREWING).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
