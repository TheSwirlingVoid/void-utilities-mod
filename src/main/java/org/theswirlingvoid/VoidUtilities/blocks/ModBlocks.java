package org.theswirlingvoid.VoidUtilities.blocks;

import org.theswirlingvoid.VoidUtilities.Main;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.ObjectHolder;
@ObjectHolder(Main.MODID)
public class ModBlocks 
{
	public static final NtoreBlock ntore=(NtoreBlock) new NtoreBlock().setRegistryName(Main.MODID, "ntore");
	public static final FurnacentBlock furnacent = (FurnacentBlock)new FurnacentBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)).setRegistryName(Main.MODID,"furnacent");
	public static final TntntBlock tntnt = (TntntBlock)new TntntBlock(Block.Properties.create(Material.TNT).sound(SoundType.PLANT).hardnessAndResistance(0, 0)).setRegistryName(Main.MODID,"tntnt");
	public static final SoulsandntBlock soulsandnt = (SoulsandntBlock)new SoulsandntBlock(Block.Properties.create(Material.SAND, MaterialColor.BROWN).tickRandomly().hardnessAndResistance(0.5F).sound(SoundType.SAND)).setRegistryName(Main.MODID,"soulsandnt");
	public static final BubbleColumnCustom BUBBLE_COLUMN = (BubbleColumnCustom)new BubbleColumnCustom(Block.Properties.create(Material.BUBBLE_COLUMN).doesNotBlockMovement().noDrops()).setRegistryName(Main.MODID,"bubblecolumn");
}
