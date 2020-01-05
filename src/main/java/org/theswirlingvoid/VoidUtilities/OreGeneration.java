package org.theswirlingvoid.VoidUtilities;

import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration 
{
	public static void setupOreGeneration()
	{
		for (Biome biome : ForgeRegistries.BIOMES)
		{
			CountRangeConfig orePlacement = new CountRangeConfig(20,1,0,63);
			biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, ModBlocks.ntore.getDefaultState(), 9), Placement.COUNT_RANGE, orePlacement));
		}
	}
}
