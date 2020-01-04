package org.theswirlingvoid.VoidUtilities.blocks;

import org.theswirlingvoid.VoidUtilities.Main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.registries.ObjectHolder;
@ObjectHolder(Main.MODID)
public class ModBlocks 
{
	public static final NtoreBlock ntore=(NtoreBlock) new NtoreBlock().setRegistryName(Main.MODID, "ntore");
	public static final FurnacentBlock furnacent = (FurnacentBlock)new FurnacentBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)).setRegistryName(Main.MODID,"furnacent");
}
