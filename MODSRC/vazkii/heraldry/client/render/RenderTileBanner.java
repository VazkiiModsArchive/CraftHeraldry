package vazkii.heraldry.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import vazkii.heraldry.client.model.ModelBanner;
import vazkii.heraldry.content.BlockHeraldry;
import vazkii.heraldry.content.TileEntityBanner;

public class RenderTileBanner extends TileEntitySpecialRenderer {

	ModelBanner model = new ModelBanner();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		TileEntityBanner banner = (TileEntityBanner) tileentity;

		GL11.glPushMatrix();
		GL11.glTranslated(d0, d1, d2);
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glScalef(1F, -1F, -1F);
		
		int meta = banner.getBlockMetadata();
		boolean hanging = BlockHeraldry.isHanging(meta);
		if(hanging) {
			GL11.glRotatef(BlockHeraldry.getOrientation(meta) * 45F, 0F, 1F, 0F);
			GL11.glTranslatef(-0.57F, 0.85F, 0F);
		} else GL11.glRotatef(BlockHeraldry.getOrientation(meta) * 45F, 0F, 1F, 0F);

		model.render(!hanging, banner.data);
		GL11.glScalef(1F, -1F, -1F);
		GL11.glPopMatrix();
	}

}
