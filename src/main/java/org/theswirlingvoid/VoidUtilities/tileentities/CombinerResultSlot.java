package org.theswirlingvoid.VoidUtilities.tileentities;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class CombinerResultSlot extends Slot {
	private final PlayerEntity player;
	private int removeCount;
	   public CombinerResultSlot(PlayerEntity player,IInventory p_i50084_2_, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
	      super(p_i50084_2_, p_i50084_3_, p_i50084_4_, p_i50084_5_);
	      this.player=player;
   }

   /**
    * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
    */
   public boolean isItemValid(ItemStack stack) {
      return false;
   }
   public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
	      this.onCrafting(stack);
	      super.onTake(thePlayer, stack);
	      return stack;
	   }

	   /**
	    * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
	    * internal count then calls onCrafting(item).
	    */
   protected void onCrafting(ItemStack stack, int amount) {
	      this.removeCount += amount;
	      this.onCrafting(stack);
	   }

	   /**
	    * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
	    */
	   protected void onCrafting(ItemStack stack) {
		   stack.onCrafting(this.player.world, this.player, this.removeCount);
		      if (!this.player.world.isRemote && this.inventory instanceof CombinerTileEntity) {
		         ((CombinerTileEntity)this.inventory).func_213995_d(this.player);
		      }

		      this.removeCount = 0;
	   }
	   public ItemStack decrStackSize(int amount) {
		      if (this.getHasStack()) {
		         this.removeCount += Math.min(amount, this.getStack().getCount());
		      }

		      return super.decrStackSize(amount);
		   }
}
