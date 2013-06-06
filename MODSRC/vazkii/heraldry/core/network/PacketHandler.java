package vazkii.heraldry.core.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.heraldry.CraftHeraldry;
import vazkii.heraldry.content.ItemHeraldry;
import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.core.proxy.CommonProxy;
import vazkii.heraldry.lib.LibMisc;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.data);
			ObjectInputStream objStream = new ObjectInputStream(byteStream);
			PacketPayload payload = (PacketPayload) objStream.readObject();
			if(payload.tilePacket) {
				CraftHeraldry.proxy.recieveSyncPacket(payload);
			} else {
				CrestData crest = payload.data;
				if(player instanceof EntityPlayer) {
					EntityPlayer entityPlayer = (EntityPlayer) player;
					ItemStack item = entityPlayer.getCurrentEquippedItem();
					if(item != null && item.itemID == CommonProxy.itemHeraldry.itemID && item.getItemDamage() == 0) {
						ItemHeraldry.writeCrestData(item, crest);
						entityPlayer.addChatMessage("Crest edited!");
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Packet250CustomPayload writePayload(PacketPayload payload) {
		try {
			Packet250CustomPayload packet = new Packet250CustomPayload();
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream data = new ObjectOutputStream(byteStream);

			data.writeObject(payload);

			packet.channel = LibMisc.PACKET_CHANNEL;
			packet.data = byteStream.toByteArray();
			packet.length = packet.data.length;

			return packet;
		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
