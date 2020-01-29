package org.theswirlingvoid.VoidUtilities.tileentities;



import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.items.ModItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CombinerBlockContainer extends Container 
{
//	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private final IInventory combinerInventory;
	private final IIntArray numbers;
	public CombinerBlockContainer(int p_i50082_1_, PlayerInventory p_i50082_2_) {
		this(p_i50082_1_, p_i50082_2_.player.getEntityWorld(),new Inventory(4), p_i50082_2_, p_i50082_2_.player,new IntArray(4));
	   }
	public CombinerBlockContainer(int windowId, World world,IInventory combinerInventory, PlayerInventory inventory,PlayerEntity player, IIntArray p_i50104_6_)
	{
		super(Main.RegistryEvents.combinerCont,windowId);
		assertInventorySize(combinerInventory, 4);
		assertIntArraySize(p_i50104_6_, 4);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		this.combinerInventory = combinerInventory;
		this.numbers=p_i50104_6_;
			addSlot(new CombinerIngredientSlot(combinerInventory,0, 34, 21));
			addSlot(new CombinerIngotntSlot(combinerInventory,1, 56, 21));
			addSlot(new CombinerFuelSlot(combinerInventory,2, 45, 62));
			addSlot(new CombinerResultSlot(player,combinerInventory,3, 116, 35));
			this.trackIntArray(p_i50104_6_);
		layoutPlayerInventorySlots(8,84);
		//TODO: I don't know what exactly this does so figure out how to change it
	}
	@OnlyIn(Dist.CLIENT)
	   public int getCombineProgressionScaled() {
	      int i = this.numbers.get(1);
	      int j = this.numbers.get(2);
	      return j != 0 && i != 0 ? i * 24 / j : 0;
	   }
	public void clear() {
	      this.combinerInventory.clear();
	   }
	public int fuelProgressionScaled() {
		if (this.numbers.get(3)!=0) {
		return this.numbers.get(0) * 21 / this.numbers.get(3);
		} else {
			return 0;
		}
	}
	@Override
	public boolean canInteractWith(PlayerEntity player) 
	{
		return this.combinerInventory.isUsableByPlayer(playerEntity);
//		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.combiner);
	}
	public void addSlot (IItemHandler handler, int index, int x, int y)
	{
		addSlot(new SlotItemHandler(handler,index,x,y));
	}
	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx)
	{
		for (int i = 0; i < amount; i++)
		{
			addSlot(handler,index,x,y);
			x+=dx;
			index++;
		}
		return index;
	}
	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy)
	{
		for (int j=0; j<verAmount;j++)
		{
			index = addSlotRange(handler,index,x,y,horAmount,dx);
			y+=dy;
		}
		return index;
	}
	
	protected void layoutPlayerInventorySlots(int leftCol,int topRow)
	{
		addSlotBox(playerInventory, 9, leftCol, topRow, 9,18,3,18);
		
		topRow +=58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
	}
	public boolean isFuel(Item item) {
		return CombinerTileEntity.getFuelTimes().getOrDefault(item, 0)!=0;
	}
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
	      ItemStack itemstack = ItemStack.EMPTY;
	      Slot slot = this.inventorySlots.get(index);
	      if (slot != null && slot.getHasStack()) {
	         ItemStack itemstack1 = slot.getStack();
	         itemstack = itemstack1.copy();
	         if (index != 1 && index != 0&& index != 2&& index != 3) {
	        	 if (isFuel(itemstack.getItem())) {
	        		 if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
		                  return ItemStack.EMPTY;
		               }
	        	 }else
	            if (itemstack.getItem()!=ModItems.ingotnt) {
	               if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
	                  return ItemStack.EMPTY;
	               }
	            } else {
	               if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
	                  return ItemStack.EMPTY;
	               }
	            }  
	         } else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
	        	//TODO: Change the two numbers to 4 and 40
	         	//TODO: I will have to change the rest as there isn't a simple solution to allow shift clicking
	            return ItemStack.EMPTY;
	         }

	         if (itemstack1.isEmpty()) {
	            slot.putStack(ItemStack.EMPTY);
	         } else {
	            slot.onSlotChanged();
	         }

	         if (itemstack1.getCount() == itemstack.getCount()) {
	            return ItemStack.EMPTY;
	         }

	         slot.onTake(playerIn, itemstack1);
	      }

	      return itemstack;
	   }
}
