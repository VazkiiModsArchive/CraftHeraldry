package vazkii.heraldry.core.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChatComponentText;
import vazkii.heraldry.content.ItemHeraldry;
import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.core.proxy.CommonProxy;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketChangeBanner implements IMessage, IMessageHandler<PacketChangeBanner, IMessage> {

	public CrestData data;

	public PacketChangeBanner() { }

	public PacketChangeBanner(CrestData data) {
		this.data = data;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		PacketBuffer packet = new PacketBuffer(buffer);
		if(data == null)
			data = new CrestData(0xFFFFFF, 0xFFFFFF, (short) -1);

		NBTTagCompound cmp = new NBTTagCompound();
		data.writeToCmp(cmp);
		try {
			packet.writeNBTTagCompoundToBuffer(cmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		PacketBuffer packet = new PacketBuffer(buffer);
		try {
			data = CrestData.readFromCmp(packet.readNBTTagCompoundFromBuffer());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IMessage onMessage(PacketChangeBanner message, MessageContext context) {
		EntityPlayer player = context.side == Side.CLIENT ? FMLClientHandler.instance().getClient().thePlayer : context.getServerHandler().playerEntity;
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null && item.getItem() == CommonProxy.itemHeraldry && item.getItemDamage() == 0 && message.data != null) {
			ItemHeraldry.writeCrestData(item, message.data);
			player.addChatMessage(new ChatComponentText("Crest edited!"));
		}

		return null;
	}

}