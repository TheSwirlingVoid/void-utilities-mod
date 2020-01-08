package org.theswirlingvoid.VoidUtilities.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;

public class NtoreBlock extends Block {

	public NtoreBlock() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(3.0f,3.0f).harvestLevel(1).lightValue(0).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE));
		// TODO Auto-generated constructor stub
	}
	@Override
	   public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
	      return silktouch == 0 ? MathHelper.nextInt(RANDOM, 1, 3) : 0;
	   }
}
