package com.codesloth.magicmirror.item;

import com.codesloth.magicmirror.MagicMirror;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTab {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MagicMirror.MOD_ID);

    public static final DeferredItem<MagicMirrorItem> MAGIC_MIRROR_ITEM = ITEMS.register("magicmirror",
            key -> new MagicMirrorItem(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, key))));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(MAGIC_MIRROR_ITEM);
        }
    }
}
