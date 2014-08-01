package vazkii.heraldry.content;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import vazkii.heraldry.core.data.CrestData;

public class TileEntityBanner extends TileEntity {

	public CrestData data = new CrestData(0xFFFFFF, 0xFFFFFF, (short) -1);
	public boolean locked;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readCustomNBT(packet.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		readCustomNBT(par1nbtTagCompound);
	}

	public void readCustomNBT(NBTTagCompound cmp) {
		data = CrestData.readFromCmp(cmp);
		locked = cmp.getBoolean("locked");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		writeCustomNBT(par1nbtTagCompound);
	}

	public void writeCustomNBT(NBTTagCompound cmp) {
		if(data != null)
			data.writeToCmp(cmp);
		cmp.setBoolean("locked", locked);
	}

}
