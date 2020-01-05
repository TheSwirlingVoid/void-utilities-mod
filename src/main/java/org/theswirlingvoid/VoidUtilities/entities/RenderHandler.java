package org.theswirlingvoid.VoidUtilities.entities;


import org.theswirlingvoid.VoidUtilities.Main;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, value=Dist.CLIENT,bus = Bus.MOD)
public class RenderHandler {
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(TntntEntity.class, renderManager -> new RenderTntnt(renderManager));
	}
}

