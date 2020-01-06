package org.theswirlingvoid.VoidUtilities.tileentities;

import javax.annotation.Nonnull;

import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.items.ModItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CombinerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{
	
	private ItemStackHandler handler;
	
	public CombinerTileEntity()
	{
		super(RegistryEvents.combinerTE);
	}

	@Override
	public void tick() 
	{
		
	}
	
	@Override
	public void read(CompoundNBT compound) 
	{
		CompoundNBT invTag = compound.getCompound("inv");
		getHandler().deserializeNBT(invTag);
		// TODO Auto-generated method stub
		super.read(compound);
	}
	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{
		CompoundNBT tag = getHandler().serializeNBT();
		compound.put("inv", tag);
		// TODO Auto-generated method stub
		return super.write(compound);
	}
	private ItemStackHandler getHandler()
	{
		if (handler == null)
		{
			handler = new ItemStackHandler(2)
					{
						@Override
						public boolean isItemValid(int slot, @Nonnull ItemStack stack)
						{
							return(slot==0 || stack.getItem() != ModItems.ingotnt);
						}
						@Nonnull
						@Override
						public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
						{
							if (slot == 0 && stack.getItem() == ModItems.ingotnt)
							{
								return stack;
							} else if (slot == 1 && stack.getItem() != ModItems.ingotnt) {
								return stack;
							}
							return super.insertItem(slot, stack, simulate);
						}
					};
			
		}
		return handler;
	}
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return LazyOptional.of(() -> (T) getHandler());
		}
		
		// TODO Auto-generated method stub
		return super.getCapability(cap, side);
	}

	@Override
	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		// TODO Auto-generated method stub
		return new CombinerBlockContainer(i,world,pos,playerInventory,playerEntity);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}
}
