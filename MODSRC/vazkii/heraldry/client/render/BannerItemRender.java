package vazkii.heraldry.client.render;

import org.lwjgl.opengl.GL11;

import vazkii.heraldry.client.model.ModelBanner;
import vazkii.heraldry.content.ItemHeraldry;
import vazkii.heraldry.core.data.CrestData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class BannerItemRender implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return item.getItemDamage() == 1 && ItemHeraldry.readCrestData(item).icon != -1 && (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(type == ItemRenderType.EQUIPPED) {
			GL11.glRotatef(112F, 0F, 1F, 0F);
			GL11.glTranslatef(1F, 0.5F, 0.25F);
			GL11.glRotatef(125F, 0F, 0F, 1F);
			GL11.glScalef(2F, 2F, 2F);
			GL11.glTranslatef(0.2F, -0.8F, 0.05F);
		} else {
			GL11.glTranslatef(1F, 2F, 1F);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			GL11.glRotatef(10F, 0F, 1F, 0F);
			GL11.glRotatef(-40F, 0F, 0F, 1F);
			GL11.glScalef(2F, 2F, 2F);
		}

		ModelBanner.instance.render(false, true, ItemHeraldry.readCrestData(item));
		GL11.glEnable(GL11.GL_LIGHTING);
	}

}
