package org.theswirlingvoid.VoidUtilities.tileentities;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.theswirlingvoid.VoidUtilities.CustomRecipeType;
import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.items.ModItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CombinerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider,ISidedInventory
{
	
	private ItemStackHandler handler;
	
	public CombinerTileEntity()
	{
		super(RegistryEvents.combinerTE);
	}
	protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	//TODO: Change the number to 4
	@Override
	public void tick() 
	{
			this.world.getRecipeManager().getRecipes().forEach(recipe->{
				if (recipe.getType()==CustomRecipeType.COMBINING) {
					for (ItemStack s:recipe.getIngredients().get(0).getMatchingStacks()) {
						if (s.isItemEqual(this.items.get(0))) {
							//Do not remove this, even though it does nothing I need it for later stuff.
						}
					
					}
				}
			});
	}
	
	@Override
	public void read(CompoundNBT compound) 
	{
		CompoundNBT invTag = compound.getCompound("inv");
		getHandler().deserializeNBT(invTag);
		ItemStackHelper.loadAllItems(compound, this.items);
		// TODO Auto-generated method stub
		super.read(compound);
	}
	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{
		CompoundNBT tag = getHandler().serializeNBT();
		compound.put("inv", tag);
		ItemStackHelper.saveAllItems(compound, this.items);
		// TODO Auto-generated method stub
		return super.write(compound);
	}
	private ItemStackHandler getHandler()
	{
		if (handler == null)
		{
			handler = new ItemStackHandler(4)
					{
						@Override
						public boolean isItemValid(int slot, @Nonnull ItemStack stack)
						{
//							if (slot<2) {
							return(slot==(stack.getItem() == ModItems.ingotnt ? 1 : 0));
//							} else {
//								return true;
//							}
						}
						@Nonnull
						@Override
						public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
						{
//							if (slot<2) {
								return (slot==(stack.getItem() == ModItems.ingotnt ? 1 : 0))? stack: super.insertItem(slot, stack, simulate);
//								} else {
//									return super.insertItem(slot, stack, simulate);
//								}
							
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
		return new CombinerBlockContainer(i,world,this,playerInventory,playerEntity);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	public int getSizeInventory() {
	      return this.items.size();
	   }

	   public boolean isEmpty() {
	      for(ItemStack itemstack : this.items) {
	         if (!itemstack.isEmpty()) {
	            return false;
	         }
	      }

	      return true;
	   }

	   /**
	    * Returns the stack in the given slot.
	    */
	   public ItemStack getStackInSlot(int index) {
	      return this.items.get(index);
	   }

	   /**
	    * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	    */
	   public ItemStack decrStackSize(int index, int count) {
	      return ItemStackHelper.getAndSplit(this.items, index, count);
	   }

	   /**
	    * Removes a stack from the given slot and returns it.
	    */
	   public ItemStack removeStackFromSlot(int index) {
	      return ItemStackHelper.getAndRemove(this.items, index);
	   }

	   /**
	    * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	    */
	   public void setInventorySlotContents(int index, ItemStack stack) {
	      ItemStack itemstack = this.items.get(index);
	      boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
	      this.items.set(index, stack);
	      if (stack.getCount() > this.getInventoryStackLimit()) {
	         stack.setCount(this.getInventoryStackLimit());
	      }

	      

	   }
	public void fillStackedContents(RecipeItemHelper helper) {
	      for(ItemStack itemstack : this.items) {
	         helper.accountStack(itemstack);
	      }

	   }
	@Override
	public boolean isUsableByPlayer(PlayerEntity arg0) {
		// TODO Auto-generated method stub
		 if (this.world.getTileEntity(this.pos) != this) {
	         return false;
	      } else {
	         return arg0.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	      }
	}


	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	 public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
	      return this.isItemValidForSlot(index, itemStackIn);
	   }
	 @Override
		public boolean isItemValidForSlot(int slot, @Nonnull ItemStack stack)
		{
//		 if (slot<2) {
				return(slot==(stack.getItem() == ModItems.ingotnt ? 1 : 0));
//				} else {
//					return true;
//				}
		}

	   /**
	    * Returns true if automation can extract the given item in the given slot from the given side.
	    */
	   public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
	      return false;
	   }

	@Override
	public int[] getSlotsForFace(Direction arg0) {
		// TODO Auto-generated method stub
		if(arg0 == Direction.DOWN) {
			return new int[] {3};
		} else if (arg0==Direction.UP) {
		return new int[] {0,1};
		} else {
			return new int[] {2};
		}
	}
}
