package org.theswirlingvoid.VoidUtilities.tileentities;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CombinerFuelSlot extends Slot {

   public CombinerFuelSlot(IInventory p_i50084_2_, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
      super(p_i50084_2_, p_i50084_3_, p_i50084_4_, p_i50084_5_);
   }

   /**
    * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
    */
   public boolean isItemValid(ItemStack stack) {
      return stack.getItem()==Items.DIAMOND;
   }

   public int getItemStackLimit(ItemStack stack) {
      return super.getItemStackLimit(stack);
   }
}
