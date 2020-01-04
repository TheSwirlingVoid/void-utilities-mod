package org.theswirlingvoid.VoidUtilities.items;

import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {
	@ObjectHolder(Main.MODID)
	public static final BlockItem ntoreitem=(BlockItem) new BlockItem(ModBlocks.ntore, new Item.Properties().group(ItemGroup.SEARCH).group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(Main.MODID,"ntore");
	public static final Item ingotnt=new Item(new Item.Properties().group(ItemGroup.SEARCH).group(ItemGroup.MATERIALS)).setRegistryName(Main.MODID,"ingotnt");
	public static final BlockItem furnacentitem=(BlockItem)new BlockItem(ModBlocks.furnacent, new Item.Properties().group(ItemGroup.SEARCH).group(ItemGroup.SEARCH).group(ItemGroup.DECORATIONS)).setRegistryName(Main.MODID,"furnacent");

}
