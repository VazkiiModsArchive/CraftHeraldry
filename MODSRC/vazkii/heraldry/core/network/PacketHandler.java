package vazkii.heraldry.core.network;

import vazkii.heraldry.lib.LibMisc;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(PacketChangeBanner.class, PacketChangeBanner.class, 0, Side.SERVER);
    }
}
