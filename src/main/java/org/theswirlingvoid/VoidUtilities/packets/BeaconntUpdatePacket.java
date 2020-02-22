package org.theswirlingvoid.VoidUtilities.packets;

import java.util.function.Supplier;

import org.theswirlingvoid.VoidUtilities.tileentities.BeaconntTileEntity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class BeaconntUpdatePacket {
	public int primaryEffect;
	public int secondaryEffect;
	public BlockPos pos;
	public static void encode(BeaconntUpdatePacket msg, PacketBuffer buf) {
		buf.writeInt(msg.primaryEffect);
		buf.writeInt(msg.secondaryEffect);
		buf.writeBlockPos(msg.pos);
	
	}
	public BeaconntUpdatePacket(int primeffect,int secondeffect,BlockPos pos) {
		this.primaryEffect = primeffect;
		this.secondaryEffect = secondeffect;
		this.pos=pos;
	}
	public static BeaconntUpdatePacket decode(PacketBuffer buf) {
		return new BeaconntUpdatePacket(buf.readInt(),buf.readInt(),buf.readBlockPos());
	}
    public static void handle(BeaconntUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
  
        ctx.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            ServerPlayerEntity sender = ctx.get().getSender(); 
            
            // the client that sent this packet
            TileEntity te=sender.getEntityWorld().getTileEntity(msg.pos);
            if (te instanceof BeaconntTileEntity) {
            	BeaconntTileEntity bte=(BeaconntTileEntity)te;
            	bte.primaryEffect=Effect.get(msg.primaryEffect);
            	bte.secondaryEffect=Effect.get(msg.secondaryEffect);
            	bte.setInventorySlotContents(0, ItemStack.EMPTY);
            	bte.updateContainingBlockInfo();
            }
            // do stuff
        });
        ctx.get().setPacketHandled(true);
    }
}