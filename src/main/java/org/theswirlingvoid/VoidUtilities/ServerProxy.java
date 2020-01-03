package org.theswirlingvoid.VoidUtilities;

import net.minecraft.world.World;

public class ServerProxy implements IProxy {
	//test
	@Override
	public World getClientWorld() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("This is a client-only mod.");
	}
	
}
