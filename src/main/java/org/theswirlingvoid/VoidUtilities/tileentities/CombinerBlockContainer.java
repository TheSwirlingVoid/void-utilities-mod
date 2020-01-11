package org.theswirlingvoid.VoidUtilities.tileentities;



import javax.annotation.Nonnull;

import org.theswirlingvoid.VoidUtilities.CustomRecipeType;
import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;
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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CombinerBlockContainer extends Container 
{
//	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private final IInventory combinerInventory;
	
	public CombinerBlockContainer(int p_i50082_1_, PlayerInventory p_i50082_2_) {
		this(p_i50082_1_, p_i50082_2_.player.getEntityWorld(),new Inventory(4), p_i50082_2_, p_i50082_2_.player);
	   }
	public CombinerBlockContainer(int windowId, World world,IInventory combinerInventory, PlayerInventory inventory,PlayerEntity player)
	{
		super(Main.RegistryEvents.combinerCont,windowId);
		assertInventorySize(combinerInventory, 4);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		this.combinerInventory = combinerInventory;
			addSlot(new Slot(combinerInventory,0, 34, 21));
			addSlot(new Slot(combinerInventory,1, 56, 21));
			addSlot(new Slot(combinerInventory,2, 45, 62));
			addSlot(new Slot(combinerInventory,3, 116, 35));
		
		layoutPlayerInventorySlots(8,84);
		//TODO: I don't know what exactly this does so figure out how to change it
	}
	public void clear() {
	      this.combinerInventory.clear();
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
	
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
	      ItemStack itemstack = ItemStack.EMPTY;
	      Slot slot = this.inventorySlots.get(index);
	      if (slot != null && slot.getHasStack()) {
	         ItemStack itemstack1 = slot.getStack();
	         itemstack = itemstack1.copy();
	         if (index != 1 && index != 0&& index != 2&& index != 3) {
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
