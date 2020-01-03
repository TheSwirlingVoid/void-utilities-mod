package org.theswirlingvoid.VoidUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.theswirlingvoid.VoidUtilities.blocks.FurnacentBlock;
import org.theswirlingvoid.VoidUtilities.tileentities.FurnacentTileEntity;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Main.MODID)
public class Main 
{
	@SuppressWarnings({"ConstantConditions", "SameReturnValue"})
	public static <T> T Null() {
		return null;
	}
	public static final String MODID="voidutilities";
	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
	private static final Logger LOGGER = LogManager.getLogger();
	public Main()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}
	private void setup(final FMLCommonSetupEvent event)
	{
		
	}
	@ObjectHolder(Main.MODID)
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		public static final FurnacentBlock furnacent = (FurnacentBlock)new FurnacentBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)).setRegistryName(Main.MODID,"furnacent");
		public static final TileEntityType<FurnacentTileEntity> furnacentTE = Null();
		
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
		{
			blockRegistryEvent.getRegistry().registerAll(
				furnacent
			);
		}
		@SubscribeEvent
		public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> tileEntityRegistryEvent)
		{
			tileEntityRegistryEvent.getRegistry().registerAll(
					TEbuild("furnacent_te", TileEntityType.Builder.create(FurnacentTileEntity::new, furnacent))
			);
		}
		private static <T extends TileEntity> TileEntityType<T> TEbuild(final String name, final TileEntityType.Builder<T> builder) {
			final ResourceLocation registryName = new ResourceLocation(Main.MODID, name);

			Type<?> dataFixerType = null;

			try {
				dataFixerType = DataFixesManager.getDataFixer()
						.getSchema(DataFixUtils.makeKey(ModDataFixers.DATA_VERSION))
						.getChoiceType(TypeReferences.BLOCK_ENTITY, registryName.toString());
			} catch (final IllegalArgumentException e) {
				if (SharedConstants.developmentMode) {
					throw e;
				}

				LOGGER.warn("No data fixer registered for TileEntity {}", registryName);
			}

			@SuppressWarnings("ConstantConditions")
			// dataFixerType will always be null until mod data fixers are implemented
			final TileEntityType<T> tileEntityType = builder.build(dataFixerType);
			tileEntityType.setRegistryName(registryName);

			return tileEntityType;
		}
	}
	}


