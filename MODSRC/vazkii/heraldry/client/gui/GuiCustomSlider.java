package vazkii.heraldry.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.settings.EnumOptions;

import org.lwjgl.input.Mouse;

public class GuiCustomSlider extends GuiSlider {

	private String constString;

	public GuiCustomSlider(int par1, int par2, int par3, String par5Str, float par6) {
		super(par1, par2, par3, EnumOptions.ANAGLYPH, par5Str, par6);
		constString = par5Str;
	}

	@Override
	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
		super.mouseDragged(par1Minecraft, par2, par3);
		displayString = String.format(constString, (int) (sliderValue * 255));

		if(!Mouse.isButtonDown(0))
			dragging = false;
	}

	@Override
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
		boolean retr = super.mousePressed(par1Minecraft, par2, par3);
		displayString = String.format(constString, (int) (sliderValue * 255));
		return retr;
	}
}
