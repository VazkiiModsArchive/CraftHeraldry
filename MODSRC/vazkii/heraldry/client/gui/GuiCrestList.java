package vazkii.heraldry.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import vazkii.heraldry.client.render.HeraldryRender;
import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.core.proxy.ClientProxy;
import vazkii.heraldry.lib.LibResources;

public class GuiCrestList extends GuiSlot {

	GuiCrestCreator parent;

	public GuiCrestList(GuiCrestCreator parent) {
		super(Minecraft.getMinecraft(), 250, parent.height, 32, parent.height - 32, 36);
		this.parent = parent;
	}

	@Override
	protected int getSize() {
		return LibResources.ICON_COUNT;
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 36;
	}

	@Override
	protected void elementClicked(int i, boolean flag, int j, int k) {
		parent.currentCrest.icon = (short) i;
	}

	@Override
	protected boolean isSelected(int i) {
		return parent.currentCrest.icon == i;
	}

	@Override
	protected void drawBackground() {
		//parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator, int a, int b) {
		CrestData crest = new CrestData(0x000000, 0xFFFFFF, (short) i);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		HeraldryRender.renderCrest(crest, (j + 10) * 2, k * 2, parent.zLevel() + 0.1);
		GL11.glScalef(2F, 2F, 2F);

		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(ClientProxy.iconNames.get(i), j + 50, k + 6, 0xFFFFFF);
	}

	@Override
	protected void drawContainerBackground(Tessellator tess) {
		if(Minecraft.getMinecraft().theWorld == null)
			super.drawContainerBackground(tess);
		else {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        tess.startDrawingQuads();
	        GL11.glEnable(GL11.GL_BLEND);
	        tess.setColorRGBA_I(0x000000, 0x99);
	        tess.addVertex(0, bottom, 0.0D);
	        tess.addVertex(250, bottom, 0.0D);
	        tess.addVertex(250, top, 0.0D);
	        tess.addVertex(0, top, 0.0D);
	        tess.draw();

	        tess.startDrawingQuads();
	        tess.setColorRGBA_I(0x000000, 0xFF);
	        tess.addVertex(250, bottom + 32, 0.0D);
	        tess.addVertex(255, bottom + 32, 0.0D);
	        tess.addVertex(255, top - 32, 0.0D);
	        tess.addVertex(250, top - 32, 0.0D);
	        tess.draw();
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_BLEND);
		}
	}

}
