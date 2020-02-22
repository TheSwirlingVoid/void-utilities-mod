package org.theswirlingvoid.VoidUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.theswirlingvoid.VoidUtilities.blocks.ModBlocks;
import org.theswirlingvoid.VoidUtilities.combinerrecipes.CombinerRecipe;
import org.theswirlingvoid.VoidUtilities.combinerrecipes.CombinerRecipeSerializer;
import org.theswirlingvoid.VoidUtilities.effects.EffectHandler;
import org.theswirlingvoid.VoidUtilities.entities.TntntEntity;
import org.theswirlingvoid.VoidUtilities.items.ModItems;
import org.theswirlingvoid.VoidUtilities.tileentities.BeaconntContainer;
import org.theswirlingvoid.VoidUtilities.tileentities.BeaconntTileEntity;
import org.theswirlingvoid.VoidUtilities.tileentities.BeaconntTileEntityRenderer;
import org.theswirlingvoid.VoidUtilities.tileentities.CombinerBlockContainer;
import org.theswirlingvoid.VoidUtilities.tileentities.CombinerTileEntity;
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
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientStart);
	}
	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
	private void setup(final FMLCommonSetupEvent event)
	{
		proxy.init();
		PacketHandler.register();
		OreGeneration.setupOreGeneration();
	}
	public void clientStart( final FMLClientSetupEvent event )
	{
		ClientRegistry.bindTileEntitySpecialRenderer(BeaconntTileEntity.class, new BeaconntTileEntityRenderer());
	}
	@ObjectHolder(Main.MODID)
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		
		public static final CombinerRecipeSerializer<CombinerRecipe> combinerrecipe=(CombinerRecipeSerializer<CombinerRecipe>) new CombinerRecipeSerializer<>(CombinerRecipe::new, 100).setRegistryName(Main.MODID, "combining");
		public static final TileEntityType<FurnacentTileEntity> furnacentTE = TEbuild("furnacentte", TileEntityType.Builder.create(FurnacentTileEntity::new, ModBlocks.furnacent));
		public static final TileEntityType<CombinerTileEntity> combinerTE = TEbuild("combinerte",TileEntityType.Builder.create(CombinerTileEntity::new,ModBlocks.combiner));
		public static final TileEntityType<BeaconntTileEntity> beaconntTE = TEbuild("beaconntte",TileEntityType.Builder.create(BeaconntTileEntity::new,ModBlocks.beaconnt));
		public static final ContainerType<FurnacentContainer> furnacentCont = Null();
		public static final ContainerType<BeaconntContainer> beaconntCont = Null();
		public static final ContainerType<CombinerBlockContainer> combinerCont = Null();
//		public static final IRecipeType<CombinerRecipe> combinerrecipetype= Null();
		
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
		{
			blockRegistryEvent.getRegistry().registerAll(
				ModBlocks.furnacent,
				ModBlocks.ntore,
				ModBlocks.tntnt,
				ModBlocks.combiner,
				ModBlocks.soulsandnt,
				ModBlocks.beaconnt,
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
					ModItems.combineritem,
					ModItems.beaconntitem,
					ModItems.soulsandntitem);
		}
		@SubscribeEvent
		public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> tileEntityRegistryEvent)
		{
			tileEntityRegistryEvent.getRegistry().registerAll(
					furnacentTE,
					combinerTE,
					beaconntTE
			);
		}
//		@SubscribeEvent
//		public static void maybe(RegistryEvent.Register<> event) {
//			event.
//		}
		@SubscribeEvent
		public static void onEffectRegistry(final RegistryEvent.Register<Effect> recipeRegistryEvent)
		{
			
			recipeRegistryEvent.getRegistry().registerAll(
					EffectHandler.light,
					EffectHandler.fragile,
					EffectHandler.myopia
			);
		}
		@SubscribeEvent
		public static void onRecipeCerealRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> recipeRegistryEvent)
		{
			
			recipeRegistryEvent.getRegistry().registerAll(
					combinerrecipe
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
					new ContainerType<>(FurnacentContainer::new).setRegistryName("furnacentcont"),
					new ContainerType<>(CombinerBlockContainer::new).setRegistryName("combinercont")
			);
			event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new BeaconntContainer(windowId, Main.proxy.getClientWorld(), pos, inv, Main.proxy.getClientPlayer());
            }).setRegistryName("beaconntcont"));
//			event.getRegistry().register(IForgeContainerType.create((windowId,inv,data) -> {
//				BlockPos pos = data.readBlockPos();
//				return new CombinerBlockContainer(windowId, Main.proxy.getClientWorld(), pos, inv,Main.proxy.getClientPlayer());
//			}).setRegistryName("combinercont"));
			
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
