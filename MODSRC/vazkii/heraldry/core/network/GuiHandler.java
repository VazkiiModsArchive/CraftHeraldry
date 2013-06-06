package vazkii.heraldry.core.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import vazkii.heraldry.client.gui.GuiCrestCreator;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		return new GuiCrestCreator(player.getCurrentEquippedItem());
	}

}
