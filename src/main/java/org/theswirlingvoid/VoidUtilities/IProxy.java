package org.theswirlingvoid.VoidUtilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {
	World getClientWorld();
	
	void init();
	public PlayerEntity getClientPlayer();
}
