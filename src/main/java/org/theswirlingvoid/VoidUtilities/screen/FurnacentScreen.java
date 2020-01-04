package org.theswirlingvoid.VoidUtilities.screen;

import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.tileentities.FurnacentContainer;

import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FurnacentScreen extends AbstractFurnacentScreen<FurnacentContainer> {
   private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation(Main.MODID,"textures/gui/container/furnacent.png");

   public FurnacentScreen(FurnacentContainer p_i51089_1_, PlayerInventory p_i51089_2_, ITextComponent p_i51089_3_) {
      super(p_i51089_1_, new FurnaceRecipeGui(), p_i51089_2_, p_i51089_3_, FURNACE_GUI_TEXTURES);
   }

@Override
public RecipeBookGui func_194310_f() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void recipesUpdated() {
	// TODO Auto-generated method stub
	
}
}