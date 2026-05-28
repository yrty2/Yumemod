package com.example.yumemod.item;

import com.example.yumemod.block.ModBlocks;
import com.example.yumemod.yumemod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, yumemod.MODID);

    public static final RegistryObject<Item> YUMENIKKI =
            ITEMS.register("yumenikki",
                    () -> new yumenikki(new Item.Properties().stacksTo(1))
            );
    public static final RegistryObject<Item> SILT_ITEM = ITEMS.register("silt_block",
            () -> new BlockItem(ModBlocks.SILT.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_SILT_ITEM = ITEMS.register("light_silt_block",
            () -> new BlockItem(ModBlocks.LIGHT_SILT.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLASS_SPONGE_ITEM = ITEMS.register("glass_sponge", () -> new BlockItem(ModBlocks.GLASS_SPONGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> YUMEDOOR_ABYSS_ITEM = ITEMS.register("yumedoor_abyss", () -> new BlockItem(ModBlocks.YUMEDOOR_ABYSS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COLORED_TV =ITEMS.register("colored_tv",
            () -> new usableItem(new Item.Properties().stacksTo(1),"colored_tv:use","別の夢の世界に入る　消費アイテム")
    );
    public static final RegistryObject<Item> BROKEN_CLOCK =ITEMS.register("broken_clock",
            () -> new usableItem(new Item.Properties().stacksTo(1),"broken_clock:use","昼夜を切り替える")
    );
}