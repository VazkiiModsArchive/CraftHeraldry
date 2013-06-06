package vazkii.heraldry;

import vazkii.heraldry.core.network.PacketHandler;
import vazkii.heraldry.core.proxy.CommonProxy;
import vazkii.heraldry.lib.LibMisc;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION)
@NetworkMod(serverSideRequired = true, channels = { LibMisc.PACKET_CHANNEL }, packetHandler = PacketHandler.class)
public class CraftHeraldry {

	@Instance(LibMisc.MOD_ID)
	public static CraftHeraldry instance;

	@SidedProxy(serverSide = LibMisc.COMMON_PROXY, clientSide = LibMisc.CLIENT_PROXY)
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;

		proxy.preInit(event);
	}

	@Init
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

}
