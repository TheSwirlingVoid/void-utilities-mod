package org.theswirlingvoid.VoidUtilities.blocks;

import org.theswirlingvoid.VoidUtilities.tileentities.AbstractFurnacentTileEntity;
import org.theswirlingvoid.VoidUtilities.tileentities.CombinerTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class CombinerBlock extends Block 
{

	public CombinerBlock() 
	{
		super(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.5f).harvestLevel(0).harvestTool(ToolType.PICKAXE));
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("deprecation")
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	      if (state.getBlock() != newState.getBlock()) {
	         TileEntity tileentity = worldIn.getTileEntity(pos);
	         if (tileentity instanceof CombinerTileEntity) {
	            InventoryHelper.dropInventoryItems(worldIn, pos, (AbstractFurnacentTileEntity)tileentity);
	            worldIn.updateComparatorOutputLevel(pos, this);
	         }

	         super.onReplaced(state, worldIn, pos, newState, isMoving);
	      }
	   }
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
	}
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new CombinerTileEntity();
	}
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote)
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof INamedContainerProvider)
			{
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
			return true;
			}
		}
		// TODO Auto-generated method stub
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}
}
