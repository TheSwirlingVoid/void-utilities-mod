package org.theswirlingvoid.VoidUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.theswirlingvoid.VoidUtilities.blocks.FurnacentBlock;
import org.theswirlingvoid.VoidUtilities.blocks.NtoreBlock;
import org.theswirlingvoid.VoidUtilities.tileentities.FurnacentContainer;
import org.theswirlingvoid.VoidUtilities.tileentities.FurnacentTileEntity;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
	}
	@ObjectHolder(Main.MODID)
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		public static final FurnacentBlock furnacent = (FurnacentBlock)new FurnacentBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)).setRegistryName(Main.MODID,"furnacent");
		public static final TileEntityType<FurnacentTileEntity> furnacentTE = TEbuild("furnacentte", TileEntityType.Builder.create(FurnacentTileEntity::new, furnacent));
		public static final ContainerType<FurnacentContainer> furnacentCont = Null();
		public static final NtoreBlock ntore = (NtoreBlock) new NtoreBlock().setRegistryName(Main.MODID, "ntore");
		public static final BlockItem ntoreitem=(BlockItem) new BlockItem(ModBlocks.NTOREBLOCK, new Item.Properties().group(ItemGroup.SEARCH).group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(Main.MODID,"ntore");
		public static final Item ingotnt=new Item(new Item.Properties().group(ItemGroup.SEARCH).group(ItemGroup.MATERIALS)).setRegistryName(Main.MODID,"ingotnt");
		public static final BlockItem furnacentitem=(BlockItem)new BlockItem(furnacent, new Item.Properties().group(ItemGroup.SEARCH).group(ItemGroup.SEARCH).group(ItemGroup.DECORATIONS)).setRegistryName(Main.MODID,"furnacent");
		
		

		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
		{
			blockRegistryEvent.getRegistry().registerAll(
				furnacent,
				ntore
			);
		}
		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
		{
			itemRegistryEvent.getRegistry().registerAll(
					ntoreitem,
					furnacentitem,
					ingotnt);
		}
		@SubscribeEvent
		public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> tileEntityRegistryEvent)
		{
			tileEntityRegistryEvent.getRegistry().registerAll(
					furnacentTE
			);
		}
		@SubscribeEvent
		public static void registerContainerTypes(final RegistryEvent.Register<ContainerType<?>> event) {
			System.out.println("registcontainer");
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
