package vazkii.heraldry.client.render;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.lib.LibResources;

public final class HeraldryRender {

	public static void renderCrest(CrestData crest, double x, double y, double z) {
		renderCrest(crest, x, y, z, true);
	}

	public static void renderCrest(CrestData crest, double x, double y, double z, boolean bg) {
		GL11.glPushMatrix();

		int ssx = crest.icon % 32;
		int ssy = crest.icon / 32;
		float scale = 1 / 32F;
		if(bg) {
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(LibResources.ICON_SHEET_0));
			int color = crest.color1;
			Color colorRGB = new Color(color);
			GL11.glColor3f(colorRGB.getRed() / 255F, colorRGB.getGreen() / 255F, colorRGB.getBlue() / 255F);
			drawTexturedQuad(x, y, z - 0.01, 64, 64, ssx * scale, (ssx + 1) * scale, ssy * scale, (ssy + 1) * scale);
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(LibResources.ICON_SHEET_1));
		int color1 = crest.color2;
		Color colorRGB1 = new Color(color1);
		if(!bg)
			GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glColor3f(colorRGB1.getRed() / 255F, colorRGB1.getGreen() / 255F, colorRGB1.getBlue() / 255F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedQuad(x, y, z + 2, 64, 64, ssx * scale, (ssx + 1) * scale, ssy * scale, (ssy + 1) * scale);
		GL11.glPopMatrix();
	}

	public static void drawTexturedQuad(double x, double y, double z, int width, int height, float minU, float maxU, float minV, float maxV) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, z, minU, maxV);
		tess.addVertexWithUV(x + width, y + height, z, maxU, maxV);
		tess.addVertexWithUV(x + width, y, z, maxU, minV);
		tess.addVertexWithUV(x, y, z, minU, minV);
		tess.draw();
	}

}
