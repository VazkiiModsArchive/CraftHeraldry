package vazkii.heraldry;

import vazkii.heraldry.core.network.PacketHandler;
import vazkii.heraldry.core.proxy.CommonProxy;
import vazkii.heraldry.lib.LibMisc;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION)
public class CraftHeraldry {

	@Instance(LibMisc.MOD_ID)
	public static CraftHeraldry instance;

	@SidedProxy(serverSide = LibMisc.COMMON_PROXY, clientSide = LibMisc.CLIENT_PROXY)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;

		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

}
