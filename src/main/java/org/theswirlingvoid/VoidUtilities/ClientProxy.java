package org.theswirlingvoid.VoidUtilities;

import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.screen.BeaconntScreen;
import org.theswirlingvoid.VoidUtilities.screen.CombinerBlockScreen;
import org.theswirlingvoid.VoidUtilities.screen.FurnacentScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy
{

	public void init() {
		ScreenManager.registerFactory(RegistryEvents.furnacentCont, FurnacentScreen::new);
		ScreenManager.registerFactory(RegistryEvents.combinerCont, CombinerBlockScreen::new);
		ScreenManager.registerFactory(RegistryEvents.beaconntCont, BeaconntScreen::new);
	}
	@Override
	public World getClientWorld() {
		// TODO Auto-generated method stub
		return Minecraft.getInstance().world;
	}
	@Override
	public PlayerEntity getClientPlayer() {
		// TODO Auto-generated method stub
		return Minecraft.getInstance().player;
	}
	
}
