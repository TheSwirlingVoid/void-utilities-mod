package org.theswirlingvoid.VoidUtilities.tileentities;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.theswirlingvoid.VoidUtilities.CustomRecipeType;
import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.combinerrecipes.AbsCombinerRecipe;
import org.theswirlingvoid.VoidUtilities.combinerrecipes.CombinerRecipe;
import org.theswirlingvoid.VoidUtilities.items.ModItems;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CombinerTileEntity extends LockableTileEntity implements ITickableTileEntity,ISidedInventory
{
	public void func_213995_d(PlayerEntity p_213995_1_) {
	      List<IRecipe<?>> list = Lists.newArrayList();

	      for(Entry<ResourceLocation, Integer> entry : this.field_214022_n.entrySet()) {
	         p_213995_1_.world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((p_213993_3_) -> {
	            list.add(p_213993_3_);
	            func_214003_a(p_213995_1_, entry.getValue(), ((CombinerRecipe)p_213993_3_).getExperience());
	         });
	      }

	      this.field_214022_n.clear();
	   }
	private final Map<ResourceLocation, Integer> field_214022_n = Maps.newHashMap();
	   private static void func_214003_a(PlayerEntity p_214003_0_, int p_214003_1_, float p_214003_2_) {
	      if (p_214003_2_ == 0.0F) {
	         p_214003_1_ = 0;
	      } else {
	         int i = MathHelper.floor((float)p_214003_1_ * p_214003_2_);
	         if (i < MathHelper.ceil((float)p_214003_1_ * p_214003_2_) && Math.random() < (double)((float)p_214003_1_ * p_214003_2_ - (float)i)) {
	            ++i;
	         }

	         p_214003_1_ = i;
	      }

	      while(p_214003_1_ > 0) {
	         int j = ExperienceOrbEntity.getXPSplit(p_214003_1_);
	         p_214003_1_ -= j;
	         p_214003_0_.world.addEntity(new ExperienceOrbEntity(p_214003_0_.world, p_214003_0_.posX, p_214003_0_.posY + 0.5D, p_214003_0_.posZ + 0.5D, j));
	      }

	   }
	private ItemStackHandler handler;
	private int fuelTime;
	   private int combineTime;
	   private int combineTimeTotal;
	   protected final IIntArray combinerData = new IIntArray() {
		      public int get(int index) {
		         switch(index) {
		         case 0:
		            return CombinerTileEntity.this.fuelTime;
		         case 1:
		            return CombinerTileEntity.this.combineTime;
		         case 2:
		            return CombinerTileEntity.this.combineTimeTotal;
		         default:
		            return 0;
		         }
		      }

		      public void set(int index, int value) {
		         switch(index) {
		         case 0:
		        	 CombinerTileEntity.this.fuelTime = value;
		            break;
		         case 1:
		        	 CombinerTileEntity.this.combineTime = value;
		            break;
		         case 2:
		        	 CombinerTileEntity.this.combineTimeTotal = value;
		         }

		      }

		      public int size() {
		         return 3;
		      }
		   };
	public CombinerTileEntity()
	{
		super(RegistryEvents.combinerTE);
		
	}
	protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	//TODO: Change the number to 4
	public boolean combining() {
		return fuelTime!=0;
	}
	@Override
	public void tick() 
	{
		boolean flag = this.combining();
		IRecipe<?> recipe=getRecipe(this.items.get(0));
		if (flag) {
		fuelTime--;
		} 
			if (!this.world.isRemote) {
		if (recipe!=null&&this.items.get(1).getItem()==ModItems.ingotnt&&flag) {
			if (recipe.getRecipeOutput().isItemEqual(this.items.get(3))||this.items.get(3).isEmpty()) {
			this.combineTimeTotal=((AbsCombinerRecipe)recipe).getCookTime();
			this.combineTime++;
			if (this.combineTime==this.combineTimeTotal) {
				this.combineTime=0;
				combine(recipe);
			}
			}
		}else if(this.items.get(2).getItem()==Items.DIAMOND&&recipe!=null&&this.items.get(1).getItem()==ModItems.ingotnt&&!flag){
			if (recipe.getRecipeOutput().isItemEqual(this.items.get(3))||this.items.get(3).isEmpty()) {
				this.items.get(2).shrink(1);
				fuelTime=8000;
			}
		}else
		{
			if (combineTime==0) {
				this.combineTimeTotal=200;
			} else {
				this.combineTime--;
			}
		}
			}
	}
	public IRecipe<?> getRecipe(ItemStack stack) {
		for(IRecipe<?> rec:this.world.getRecipeManager().getRecipes()) {
			if (rec.getType()==CustomRecipeType.COMBINING) {
			for (ItemStack s:rec.getIngredients().get(0).getMatchingStacks()) {
				if (ItemStack.areItemsEqual(s, stack)) {
					return rec;
				}
			}}
		}
		return null;
	}
//	public int getCombineProgress() {
//		return (float)this.combineTime
//	}
	public void combine(IRecipe recipe){
		ItemStack itemstack=this.items.get(0);
		ItemStack itemstack1=recipe.getRecipeOutput();
		ItemStack itemstack2 = this.items.get(3);
         if (itemstack2.isEmpty()) {
            this.items.set(3, itemstack1.copy());
         } else if (itemstack2.getItem() == itemstack1.getItem()) {
            itemstack2.grow(1);
         }
         itemstack.shrink(1);
         this.items.get(1).shrink(1);
         setRecipeUsed(recipe);
	}
	@Override
	public void read(CompoundNBT compound) 
	{
		super.read(compound);
		ItemStackHelper.loadAllItems(compound, this.items);
		this.fuelTime = compound.getInt("FuelTime");
	      this.combineTime = compound.getInt("CombineTime");
	      this.combineTimeTotal = compound.getInt("CombineTimeTotal");
	      int i = compound.getShort("RecipesUsedSize");

	      for(int j = 0; j < i; ++j) {
	         ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + j));
	         int k = compound.getInt("RecipeAmount" + j);
	         this.field_214022_n.put(resourcelocation, k);
	      }
		// TODO Auto-generated method stub
	}
	public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
	      if (recipe != null) {
	         this.field_214022_n.compute(recipe.getId(), (p_214004_0_, p_214004_1_) -> {
	            return 1 + (p_214004_1_ == null ? 0 : p_214004_1_);
	         });
	      }

	   }
	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, this.items);
		compound.putInt("FuelTime", this.fuelTime);
	      compound.putInt("CombineTime", this.combineTime);
	      compound.putInt("CombineTimeTotal", this.combineTimeTotal);
	      compound.putShort("RecipesUsedSize", (short)this.field_214022_n.size());
	      int i = 0;

	      for(Entry<ResourceLocation, Integer> entry : this.field_214022_n.entrySet()) {
	         compound.putString("RecipeLocation" + i, entry.getKey().toString());
	         compound.putInt("RecipeAmount" + i, entry.getValue());
	         ++i;
	      }

		// TODO Auto-generated method stub
		return compound;
	}
	private ItemStackHandler getHandler()
	{
		if (handler == null)
		{
			handler = new ItemStackHandler(items)
					{
						@Override
						public boolean isItemValid(int slot, @Nonnull ItemStack stack)
						{
							if (slot<2) {
							return(slot==(stack.getItem() == ModItems.ingotnt ? 1 : 0));
							} else {
								return slot==2&&stack.getItem()==Items.DIAMOND;
							}
						}
						@Override
						public ItemStack extractItem(int slot, int amount, boolean simulate) {
							// TODO Auto-generated method stub
							
							return slot==3? super.extractItem(slot, amount, simulate): ItemStack.EMPTY;
						}
						@Nonnull
						@Override
						public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
						{
							if (slot<2) {
								return (slot==(stack.getItem() == ModItems.ingotnt ? 1 : 0))? stack: super.insertItem(slot, stack, simulate);
								} else {
									return (slot==2&&stack.getItem()==Items.DIAMOND)? stack: super.insertItem(slot, stack, simulate);
								}
							
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

//	@Override
//	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
//		// TODO Auto-generated method stub
//		return new CombinerBlockContainer(i,world,this,playerInventory,playerEntity,combinerData);
//	}

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
		 if (slot<2) {
				return(slot==(stack.getItem() == ModItems.ingotnt ? 1 : 0));
				} else {
					return stack.getItem()==Items.DIAMOND;
				}
		}

	   /**
	    * Returns true if automation can extract the given item in the given slot from the given side.
	    */
	   public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
	      return index==3;
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
	@Override
	  protected ITextComponent getDefaultName() {
	      return new TranslationTextComponent("container.combiner");
	   }
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		// TODO Auto-generated method stub
		return new CombinerBlockContainer(id,world,this,player,player.player,combinerData);
	}
}
