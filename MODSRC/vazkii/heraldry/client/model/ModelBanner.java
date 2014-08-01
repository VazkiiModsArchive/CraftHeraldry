package vazkii.heraldry.client.model;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.heraldry.client.render.HeraldryRender;
import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.lib.LibResources;

public class ModelBanner extends ModelBase {

	ModelRenderer BannerHold;
	ModelRenderer BannerHold2;
	ModelRenderer BannerPillar;
	ModelRenderer BannerCloth;
	ModelRenderer BannerBar;

	public ModelBanner() {
		textureWidth = 64;
		textureHeight = 32;

		BannerHold = new ModelRenderer(this, 0, 0);
		BannerHold.addBox(0F, 0F, 0F, 2, 1, 14);
		BannerHold.setRotationPoint(-1F, 23F, -7F);
		BannerHold.setTextureSize(64, 32);
		BannerHold.mirror = true;
		BannerHold2 = new ModelRenderer(this, 0, 0);
		BannerHold2.addBox(0F, 0F, 0F, 2, 1, 14);
		BannerHold2.setRotationPoint(-7F, 23F, 1F);
		BannerHold2.setTextureSize(64, 32);
		BannerHold2.mirror = true;
		BannerHold2.rotateAngleY = 1.570796F;
		BannerPillar = new ModelRenderer(this, 32, 0);
		BannerPillar.addBox(0F, 0F, 0F, 2, 28, 2);
		BannerPillar.setRotationPoint(-1F, -5F, -1F);
		BannerPillar.setTextureSize(64, 32);
		BannerPillar.mirror = true;
		BannerCloth = new ModelRenderer(this, 40, 0);
		BannerCloth.addBox(0F, 0F, 0F, 0, 20, 12);
		BannerCloth.setRotationPoint(2F, -2F, -6F);
		BannerCloth.setTextureSize(64, 32);
		BannerCloth.mirror = true;
		BannerBar = new ModelRenderer(this, 0, 15);
		BannerBar.addBox(0F, 0F, 0F, 1, 2, 14);
		BannerBar.setRotationPoint(1F, -4F, -7F);
		BannerBar.setTextureSize(64, 32);
		BannerBar.mirror = true;
	}

	public void render(boolean full, CrestData crest) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(LibResources.MODEL_BANNER));
		if(full) {
			BannerHold.render(0.0625F);
			BannerHold2.render(0.0625F);
			BannerPillar.render(0.0625F);
		}

		BannerBar.render(0.0625F);

		if(crest != null && crest.icon != -1) {
			GL11.glPushMatrix();
			float scale = 92F;
			float dimScale = 1F / scale;
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glRotatef(180F, 0F, 1F, 0F);
			GL11.glTranslatef(-0.35F, 0F, -0.158F);
			GL11.glScalef(dimScale, dimScale, dimScale);
			HeraldryRender.renderCrest(crest, 0, 2, 0, false);
			Color color = new Color(crest.color1);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			GL11.glPopMatrix();
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(LibResources.MODEL_BANNER));

		BannerCloth.render(0.0625F);
		GL11.glColor3f(1F, 1F, 1F);
	}
}
