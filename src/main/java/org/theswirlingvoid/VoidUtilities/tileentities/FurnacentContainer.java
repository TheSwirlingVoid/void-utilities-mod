package org.theswirlingvoid.VoidUtilities.tileentities;

import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;

public class FurnacentContainer extends AbstractFurnacentContainer {
   public FurnacentContainer(int p_i50082_1_, PlayerInventory p_i50082_2_) {
      super(RegistryEvents.furnacentCont, IRecipeType.SMELTING, p_i50082_1_, p_i50082_2_);
   }

   public FurnacentContainer(int p_i50083_1_, PlayerInventory p_i50083_2_, IInventory p_i50083_3_, IIntArray p_i50083_4_) {
      super(RegistryEvents.furnacentCont, IRecipeType.SMELTING, p_i50083_1_, p_i50083_2_, p_i50083_3_, p_i50083_4_);
   }
}