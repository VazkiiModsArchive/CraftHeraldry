package vazkii.heraldry.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import vazkii.heraldry.client.model.ModelBanner;
import vazkii.heraldry.client.render.HeraldryRender;
import vazkii.heraldry.content.ItemHeraldry;
import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.core.network.PacketChangeBanner;
import vazkii.heraldry.core.network.PacketHandler;
import vazkii.heraldry.core.proxy.ClientProxy;
import vazkii.heraldry.lib.LibResources;
import cpw.mods.fml.client.config.GuiSlider;

public class GuiCrestCreator extends GuiScreen {

	GuiCrestList list;
	public CrestData currentCrest = new CrestData(0x000000, 0xFFFFFF, (short) 0);

	public List<Integer> viewableCrests = new ArrayList();
	
	GuiSlider[] color1Sliders;
	GuiSlider[] color2Sliders;

	GuiTextField field;
	
	public GuiCrestCreator(ItemStack stack) {
		if(stack != null) {
			CrestData tempData = ItemHeraldry.readCrestData(stack);
			if(tempData != null)
				currentCrest = tempData;
		}
	}

	@Override
	public void initGui() {
		list = new GuiCrestList(this);

		GuiSlider slider1Red = new GuiCustomSlider(0, 258, 90, "Red: %s", 0F);
		GuiSlider slider1Green = new GuiCustomSlider(1, 258, 112, "Green: %s", 0F);
		GuiSlider slider1Blue = new GuiCustomSlider(2, 258, 134, "Blue: %s", 0F);
		color1Sliders = new GuiSlider[] { slider1Red, slider1Green, slider1Blue };

		GuiSlider slider2Red = new GuiCustomSlider(3, 258, 200, "Red: %s", 1F);
		GuiSlider slider2Green = new GuiCustomSlider(4, 258, 222, "Green: %s", 1F);
		GuiSlider slider2Blue = new GuiCustomSlider(5, 258, 244, "Blue: %s", 1F);
		color2Sliders = new GuiSlider[] { slider2Red, slider2Green, slider2Blue };

		buttonList.clear();
		for(GuiSlider slider : color1Sliders)
			buttonList.add(slider);
		for(GuiSlider slider : color2Sliders)
			buttonList.add(slider);

		buttonList.add(new GuiButton(6, 15, height - 25, 60, 20, "Random"));
		buttonList.add(new GuiButton(7, width / 2 - 80, height - 25, 200, 20, "Done"));

		field = new GuiTextField(fontRendererObj, 85, height - 25, 140, 20);
		field.setFocused(true);
		field.setCanLoseFocus(false);
		
		filterViewableCrests();
		updateSliders(currentCrest);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		list.drawScreen(par1, par2, par3);

		boolean uni = fontRendererObj.getUnicodeFlag();

		GL11.glScalef(2F, 2F, 2F);
		drawRect((width - 162) / 2, 29, (width - 30) / 2, 95, 0x55FFFFFF);
		fontRendererObj.setUnicodeFlag(true);
		drawCenteredString(fontRendererObj, "Heraldic Editor", width / 4, 3, 0xFFFFFF);
		fontRendererObj.drawStringWithShadow("Preview:", (width - 136) / 2, 18, 0xFFFFFF);
		HeraldryRender.renderCrest(currentCrest, (width - 160) / 2, 30, zLevel + 0.1);
		GL11.glScalef(0.5F, 0.5F, 0.5F);

		fontRendererObj.drawStringWithShadow("Background Color", 265, 76, 0xFFFFFF);
		fontRendererObj.drawStringWithShadow("Foreground Color", 265, 186, 0xFFFFFF);
		fontRendererObj.setUnicodeFlag(uni);

		field.drawTextBox();
		
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		field.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}
	
	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		super.keyTyped(p_73869_1_, p_73869_2_);
		field.textboxKeyTyped(p_73869_1_, p_73869_2_);
		filterViewableCrests();
	}
	
	private void filterViewableCrests() {
		viewableCrests.clear();
		for(int i = 0; i < LibResources.ICON_COUNT; i++)
			if(ClientProxy.iconNames.get(i).toLowerCase().contains(field.getText().toLowerCase()))
				viewableCrests.add(i);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch(par1GuiButton.id) {
			case 6 : { // Random
				Random rand = new Random();
				currentCrest.color1 = rand.nextInt(0xFFFFFF);
				currentCrest.color2 = rand.nextInt(0xFFFFFF);
				currentCrest.icon = (short) rand.nextInt(LibResources.ICON_COUNT);
				updateSliders(currentCrest);
				break;
			}
			case 7 : { // Done
				System.out.println(currentCrest);
				PacketHandler.INSTANCE.sendToServer(new PacketChangeBanner(currentCrest));
				mc.displayGuiScreen(null);
				break;
			}
		}
	}

	@Override
	public void updateScreen() {
		float[] color1 = new float[3];
		float[] color2 = new float[3];

		for(int i = 0; i < 3; i++)
			color1[i] = (float) color1Sliders[i].sliderValue;

		for(int i = 0; i < 3; i++)
			color2[i] = (float) color2Sliders[i].sliderValue;

		Color colorRGB1 = new Color((int) (color1[0] * 255), (int) (color1[1] * 255), (int) (color1[2] * 255));
		Color colorRGB2 = new Color((int) (color2[0] * 255), (int) (color2[1] * 255), (int) (color2[2] * 255));

		currentCrest.color1 = colorRGB1.getRGB();
		currentCrest.color2 = colorRGB2.getRGB();

		super.updateScreen();
	}

	public void updateSliders(CrestData crest) {
		for(int i = 0; i < color1Sliders.length; i++) {
			GuiSlider slider = color1Sliders[i];
			slider.sliderValue = (crest.color1 >> 16 - i * 8 & 0xFF) / 255F;
		}
		for(int i = 0; i < color2Sliders.length; i++) {
			GuiSlider slider = color2Sliders[i];
			slider.sliderValue = (crest.color2 >> 16 - i * 8 & 0xFF) / 255F;
		}
	}

	public float zLevel() {
		return zLevel;
	}

	@Override
	public void drawDefaultBackground() {
		if(mc.theWorld != null)
            drawGradientRect(0, 0, width, height, 0x99000000, 0x77000000);
		else drawBackground(0);
	}
}
