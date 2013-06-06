package vazkii.heraldry.core.data;

import java.io.Serializable;

import net.minecraft.nbt.NBTTagCompound;

public class CrestData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4380625955792933617L;
	private static final String TAG_COLOR_1 = "color1";
	private static final String TAG_COLOR_2 = "color2";
	private static final String TAG_ICON = "icon";

	public CrestData(int color1, int color2, short icon) {
		this.color1 = color1;
		this.color2 = color2;
		this.icon = icon;
	}

	public int color1;
	public int color2;
	public short icon;

	public void writeToCmp(NBTTagCompound cmp) {
		cmp.setInteger(TAG_COLOR_1, color1);
		cmp.setInteger(TAG_COLOR_2, color2);
		cmp.setShort(TAG_ICON, icon);
	}

	public static CrestData readFromCmp(NBTTagCompound cmp) {
		if(!(cmp.hasKey(TAG_COLOR_1) && cmp.hasKey(TAG_COLOR_2) && cmp.hasKey(TAG_ICON)))
			return null;

		int color1 = cmp.getInteger(TAG_COLOR_1);
		int color2 = cmp.getInteger(TAG_COLOR_2);
		short icon = cmp.getShort(TAG_ICON);

		return new CrestData(color1, color2, icon);
	}

}
