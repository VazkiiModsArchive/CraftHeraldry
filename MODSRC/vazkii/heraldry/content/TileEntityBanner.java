package vazkii.heraldry.content;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.core.network.PacketHandler;
import vazkii.heraldry.core.network.PacketPayload;

public class TileEntityBanner extends TileEntity {

	public CrestData data;

	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.writePayload(new PacketPayload(xCoord, yCoord, zCoord, data));
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		data = CrestData.readFromCmp(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		if(data != null)
			data.writeToCmp(par1nbtTagCompound);
	}

}
