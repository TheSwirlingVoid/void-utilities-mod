package org.theswirlingvoid.VoidUtilities;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy implements IProxy
{

	@Override
	public World getClientWorld() {
		// TODO Auto-generated method stub
		return Minecraft.getInstance().world;
	}
	
}
