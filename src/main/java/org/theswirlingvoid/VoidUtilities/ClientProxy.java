package org.theswirlingvoid.VoidUtilities;

import org.theswirlingvoid.VoidUtilities.Main.RegistryEvents;
import org.theswirlingvoid.VoidUtilities.screen.FurnacentScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.world.World;

public class ClientProxy implements IProxy
{

	public void init() {
		ScreenManager.registerFactory(RegistryEvents.furnacentCont, FurnacentScreen::new);
	}
	@Override
	public World getClientWorld() {
		// TODO Auto-generated method stub
		return Minecraft.getInstance().world;
	}
	
}
