package org.theswirlingvoid.VoidUtilities;

import org.theswirlingvoid.VoidUtilities.packets.BeaconntUpdatePacket;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
	    new ResourceLocation(Main.MODID, "main"),
	    () -> PROTOCOL_VERSION,
	    PROTOCOL_VERSION::equals,
	    PROTOCOL_VERSION::equals
	);
	public static void register()
	{
		INSTANCE.registerMessage(0, BeaconntUpdatePacket.class, BeaconntUpdatePacket::encode, BeaconntUpdatePacket::decode, BeaconntUpdatePacket::handle);
	}
	public static void sendBeaconntPacket(int primeffect,int secondeffect,BlockPos pos) {
		INSTANCE.sendToServer(new BeaconntUpdatePacket(primeffect,secondeffect,pos));
	}
}
