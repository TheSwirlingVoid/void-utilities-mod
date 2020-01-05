package org.theswirlingvoid.VoidUtilities.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SoulsandntBlock extends Block {
	   protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	   public SoulsandntBlock(Block.Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }

	   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
	      entityIn.setMotion(entityIn.getMotion().mul(1.2D, 1.0D, 1.2D));
	   }
	   @Override
	   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
	      BubbleColumnCustom.placeBubbleColumn(worldIn, pos.up(), true);
	   }

	   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
	      worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	   }

	   public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
	      return true;
	   }

	   /**
	    * How many world ticks before ticking
	    */
	   public int tickRate(IWorldReader worldIn) {
	      return 20;
	   }

	   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	      worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	   }

	   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
	      return false;
	   }

	   public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
	      return true;
	   }
	}
