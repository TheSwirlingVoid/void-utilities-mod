package org.theswirlingvoid.VoidUtilities.combinerrecipes;

import org.theswirlingvoid.VoidUtilities.CustomRecipeType;
import org.theswirlingvoid.VoidUtilities.Main;
import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CombinerRecipe extends AbsCombinerRecipe{
	public CombinerRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
	      super(CustomRecipeType.COMBINING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	   }

	   public ItemStack getIcon() {
	      return new ItemStack(ModBlocks.combiner);
	   }

	   public IRecipeSerializer<?> getSerializer() {
	      return RegistryEvents.combinerrecipe;
	   }
}
