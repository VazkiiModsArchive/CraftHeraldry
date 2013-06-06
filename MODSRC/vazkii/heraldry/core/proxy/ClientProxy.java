package vazkii.heraldry.core.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import vazkii.heraldry.CraftHeraldry;
import vazkii.heraldry.client.render.RenderTileBanner;
import vazkii.heraldry.content.TileEntityBanner;
import vazkii.heraldry.core.network.PacketPayload;
import vazkii.heraldry.lib.LibResources;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ClientProxy extends CommonProxy {

	public static List<String> iconNames = new ArrayList();

	@Override
	public void init() {
		super.init();

		readIconNames();

		// Preload textures to prevent a lagspike
		RenderEngine engine = Minecraft.getMinecraft().renderEngine;
		int current = GL11.glGetInteger(GL11.GL_TEXTURE_2D);
		engine.bindTexture(LibResources.ICON_SHEET_0);
		engine.bindTexture(LibResources.ICON_SHEET_1);

		// Reset, preventing any possible side effects
		ReflectionHelper.setPrivateValue(RenderEngine.class, engine, current, 10);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, current);
	}

	private void readIconNames() {
		try {
			InputStream stream = CraftHeraldry.class.getResourceAsStream(LibResources.ICON_NAMES);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			String line;
			while((line = reader.readLine()) != null)
				iconNames.add(line);

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("(CraftHeraldy) Failed to load icon names!", e);
		}
	}

	@Override
	public void recieveSyncPacket(PacketPayload payload) {
		World world = Minecraft.getMinecraft().theWorld;
		TileEntity tile = world.getBlockTileEntity(payload.x, payload.y, payload.z);
		if(tile != null && tile instanceof TileEntityBanner)
			((TileEntityBanner) tile).data = payload.data;
	}

	@Override
	void initContent() {
		super.initContent();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new RenderTileBanner());
	}
}
