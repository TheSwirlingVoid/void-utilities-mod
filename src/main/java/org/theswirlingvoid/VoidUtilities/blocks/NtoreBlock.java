package org.theswirlingvoid.VoidUtilities.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class NtoreBlock extends Block {

	public NtoreBlock() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(3.0f,3.0f).harvestLevel(2).lightValue(0).sound(SoundType.STONE));
		// TODO Auto-generated constructor stub
	}

}
