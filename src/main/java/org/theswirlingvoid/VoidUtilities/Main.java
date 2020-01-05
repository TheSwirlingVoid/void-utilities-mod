package org.theswirlingvoid.VoidUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;
import org.theswirlingvoid.VoidUtilities.entities.TntntEntity;
import org.theswirlingvoid.VoidUtilities.items.ModItems;
import org.theswirlingvoid.VoidUtilities.tileentities.FurnacentContainer;
import org.theswirlingvoid.VoidUtilities.tileentities.FurnacentTileEntity;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
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
	public static <T> T Null() {
		return null;
	}
	public static final String MODID="voidutilities";
	private static final Logger LOGGER = LogManager.getLogger();
	public Main()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}
	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
	private void setup(final FMLCommonSetupEvent event)
	{
		proxy.init();
		OreGeneration.setupOreGeneration();
	}
	@ObjectHolder(Main.MODID)
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		public static final TileEntityType<FurnacentTileEntity> furnacentTE = TEbuild("furnacentte", TileEntityType.Builder.create(FurnacentTileEntity::new, ModBlocks.furnacent));
		public static final ContainerType<FurnacentContainer> furnacentCont = Null();
		
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
		{
			blockRegistryEvent.getRegistry().registerAll(
				ModBlocks.furnacent,
				ModBlocks.ntore,
				ModBlocks.tntnt,
				ModBlocks.soulsandnt,
				ModBlocks.BUBBLE_COLUMN
			);
		}
		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
		{
			itemRegistryEvent.getRegistry().registerAll(
					ModItems.ntoreitem,
					ModItems.furnacentitem,
					ModItems.ingotnt,
					ModItems.tntntitem,
					ModItems.soulsandntitem);
		}
		@SubscribeEvent
		public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> tileEntityRegistryEvent)
		{
			tileEntityRegistryEvent.getRegistry().registerAll(
					furnacentTE
			);
		}
		@SubscribeEvent
		public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
			
			final EntityType<TntntEntity> TNTNT = buildEnt(
					"tntnt",
					
					EntityType.Builder.<TntntEntity>create((TntntEntity::new), EntityClassification.MISC)
					.setCustomClientFactory(TntntEntity::new)
							.size(1.0f, 1.0f)
							
			);
			event.getRegistry().registerAll(
					TNTNT
					);
		
			
		}
		private static <T extends Entity> EntityType<T> buildEnt(String name, EntityType.Builder<T> builder) {
			final ResourceLocation registryName = new ResourceLocation(Main.MODID, name);

			final EntityType<T> entityType = builder
					.build(registryName.toString());

			entityType.setRegistryName(registryName);
			

			return entityType;
		}
		@SubscribeEvent
		public static void registerContainerTypes(final RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().registerAll(
					new ContainerType<>(FurnacentContainer::new).setRegistryName("furnacentcont")
			);
		}
		private static <T extends TileEntity> TileEntityType<T> TEbuild(final String name, final TileEntityType.Builder<T> builder) {
			final ResourceLocation registryName = new ResourceLocation(Main.MODID, name);

			Type<?> dataFixerType = null;

			try {
				dataFixerType = DataFixesManager.getDataFixer()
						.getSchema(DataFixUtils.makeKey(103))
						.getChoiceType(TypeReferences.BLOCK_ENTITY, registryName.toString());
			} catch (final IllegalArgumentException e) {
				if (SharedConstants.developmentMode) {
					throw e;
				}

				LOGGER.warn("No data fixer registered for TileEntity {}", registryName);
			}

			// dataFixerType will always be null until mod data fixers are implemented
			final TileEntityType<T> tileEntityType = builder.build(dataFixerType);
			tileEntityType.setRegistryName(registryName);

			return tileEntityType;
		}
	
		
}

}
