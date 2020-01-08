package org.theswirlingvoid.VoidUtilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {
	//test
	@Override
	public World getClientWorld() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Only run this on the Client!");
	}
	public void init() {
		
	}
	@Override
	public PlayerEntity getClientPlayer() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Only run this on the Client!");
	}
	
}
