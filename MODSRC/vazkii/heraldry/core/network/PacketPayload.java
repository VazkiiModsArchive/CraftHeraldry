package vazkii.heraldry.core.network;

import java.io.Serializable;

import vazkii.heraldry.core.data.CrestData;

public class PacketPayload implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5554533983303325249L;
	public CrestData data;
	boolean tilePacket;
	public int x, y, z;

	public PacketPayload(CrestData data) {
		this.data = data;
		tilePacket = false;
	}

	public PacketPayload(int x, int y, int z, CrestData data) {
		this.data = data;
		tilePacket = true;
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
