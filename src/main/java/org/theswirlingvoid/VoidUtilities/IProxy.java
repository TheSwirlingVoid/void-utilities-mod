package org.theswirlingvoid.VoidUtilities;

import net.minecraft.world.World;

public interface IProxy {
	World getClientWorld();

	void init();
}
