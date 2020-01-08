package org.theswirlingvoid.VoidUtilities;

import org.theswirlingvoid.VoidUtilities.combinerrecipes.CombinerRecipe;

import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface CustomRecipeType<T extends IRecipe<?>> extends IRecipeType{
	   IRecipeType<CombinerRecipe> COMBINING = register("combining");

	   static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
	      return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("voidutilities",key), new IRecipeType<T>() {
	         public String toString() {
	            return "voidutilities:"+key;
	         }
	      });
	   }

	   
	}
