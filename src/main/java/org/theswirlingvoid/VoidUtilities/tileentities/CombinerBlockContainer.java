package org.theswirlingvoid.VoidUtilities.tileentities;



import javax.annotation.Nonnull;

import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;
import org.theswirlingvoid.VoidUtilities.items.ModItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CombinerBlockContainer extends Container 
{
	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	
	public CombinerBlockContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory,PlayerEntity player)
	{
		super(Main.RegistryEvents.combinerCont,windowId);
		tileEntity = world.getTileEntity(pos);
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
			addSlot(new SlotItemHandler(h,0, 34, 21));
			
		});
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h2 -> {
			addSlot(new SlotItemHandler(h2,1, 56, 21));
			
		});
		
		layoutPlayerInventorySlots(8,84);
	}
	@Override
	public boolean canInteractWith(PlayerEntity player) 
	{
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.combiner);
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
	         if (index != 1 && index != 0) {
	            if (itemstack.getItem()!=ModItems.ingotnt) {
	               if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
	                  return ItemStack.EMPTY;
	               }
	            } else {
	               if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
	                  return ItemStack.EMPTY;
	               }
	            }  
	         } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
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
