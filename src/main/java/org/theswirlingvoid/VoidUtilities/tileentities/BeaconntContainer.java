package org.theswirlingvoid.VoidUtilities.tileentities;


import javax.annotation.Nullable;

import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeaconntContainer extends Container{
	public BlockPos pos;
   private final BeaconntContainer.BeaconSlot beaconSlot;
   public final IWorldPosCallable field_216971_e;
   private final IIntArray field_216972_f;
   public final IInventory invent;
//   public BeaconntContainer(int p_i50099_1_, IInventory p_i50099_2_) {
//	   this(p_i50099_1_, p_i50099_2_, new IntArray(3), IWorldPosCallable.DUMMY);
//   }
   public BeaconntContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
	   this(pos,windowId, playerInventory,new Inventory(1), new IntArray(3), IWorldPosCallable.DUMMY);
   }
   public BeaconntContainer(BlockPos pos,int p_i50100_1_, IInventory p_i50100_2_,IInventory inv, IIntArray p_i50100_3_, IWorldPosCallable p_i50100_4_) {
      super(RegistryEvents.beaconntCont, p_i50100_1_);
      assertInventorySize(inv, 1);
      this.pos=pos;
      this.invent = inv;
//    System.out.println(BlockPos.fromLong(buf.readLong()).getX());
      assertIntArraySize(p_i50100_3_, 3);
      
      this.field_216972_f = p_i50100_3_;
      this.field_216971_e = p_i50100_4_;
      this.beaconSlot = new BeaconntContainer.BeaconSlot(inv, 0, 136, 110);
      this.addSlot(this.beaconSlot);
      this.trackIntArray(p_i50100_3_);
      int i = 36;
      int j = 137;

      for(int k = 0; k < 3; ++k) {
         for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(p_i50100_2_, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.addSlot(new Slot(p_i50100_2_, i1, 36 + i1 * 18, 195));
      }

   }

/**
    * Called when the container is closed.
    */
   public void onContainerClosed(PlayerEntity playerIn) {
      super.onContainerClosed(playerIn);
      if (!playerIn.world.isRemote) {
         ItemStack itemstack = this.beaconSlot.decrStackSize(this.beaconSlot.getSlotStackLimit());
         if (!itemstack.isEmpty()) {
            playerIn.dropItem(itemstack, false);
         }

      }
   }

   /**
    * Determines whether supplied player can use this container
    */
   public boolean canInteractWith(PlayerEntity playerIn) {
      return isWithinUsableDistance(this.field_216971_e, playerIn, ModBlocks.beaconnt);
   }

   public void updateProgressBar(int id, int data) {
      super.updateProgressBar(id, data);
      this.detectAndSendChanges();
   }

   /**
    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
    * inventory and the other inventory(s).
    */
   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if (index == 0) {
            if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
               return ItemStack.EMPTY;
            }

            slot.onSlotChange(itemstack1, itemstack);
         } else if (this.mergeItemStack(itemstack1, 0, 1, false)) { //Forge Fix Shift Clicking in beacons with stacks larger then 1.
            return ItemStack.EMPTY;
         } else if (index >= 1 && index < 28) {
            if (!this.mergeItemStack(itemstack1, 28, 37, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 28 && index < 37) {
            if (!this.mergeItemStack(itemstack1, 1, 28, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.mergeItemStack(itemstack1, 1, 37, false)) {
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

   @OnlyIn(Dist.CLIENT)
   public int func_216969_e() {
      return this.field_216972_f.get(0);
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public Effect func_216967_f() {
      return Effect.get(this.field_216972_f.get(1));
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public Effect func_216968_g() {
      return Effect.get(this.field_216972_f.get(2));
   }

   public void func_216966_c(int p_216966_1_, int p_216966_2_) {
      if (this.beaconSlot.getHasStack()) {
         this.field_216972_f.set(1, p_216966_1_);
         this.field_216972_f.set(2, p_216966_2_);
         this.beaconSlot.decrStackSize(1);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_216970_h() {
      return !this.invent.getStackInSlot(0).isEmpty();
   }

   class BeaconSlot extends Slot {
      public BeaconSlot(IInventory inventoryIn, int index, int xIn, int yIn) {
         super(inventoryIn, index, xIn, yIn);
      }

      /**
       * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
       */
      public boolean isItemValid(ItemStack stack) {
         return stack.isBeaconPayment();
      }

      /**
       * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
       * case of armor slots)
       */
      public int getSlotStackLimit() {
         return 1;
      }
   }


}