package org.theswirlingvoid.VoidUtilities.tileentities;


import org.theswirlingvoid.VoidUtilities.items.ModItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class CombinerIngredientSlot extends Slot {

	   public CombinerIngredientSlot(IInventory p_i50084_2_, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
	      super(p_i50084_2_, p_i50084_3_, p_i50084_4_, p_i50084_5_);
	      }

   /**
    * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
    */
   public boolean isItemValid(ItemStack stack) {
	   return stack.getItem()!=ModItems.ingotnt;
   }

   
}