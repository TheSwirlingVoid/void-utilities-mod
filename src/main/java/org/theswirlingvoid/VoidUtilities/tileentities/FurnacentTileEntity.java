package org.theswirlingvoid.VoidUtilities.tileentities;

import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FurnacentTileEntity extends AbstractFurnacentTileEntity {
   public FurnacentTileEntity() {
      super(RegistryEvents.furnacentTE, IRecipeType.SMELTING);
   }

   protected ITextComponent getDefaultName() {
      return new TranslationTextComponent(Main.MODID,"container.furnace");
   }

   protected Container createMenu(int id, PlayerInventory player) {
      return new FurnacentContainer(id, player, this, this.furnaceData);
   }
}