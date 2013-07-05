package vazkii.heraldry.core.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import vazkii.heraldry.CraftHeraldry;
import vazkii.heraldry.content.BlockHeraldry;
import vazkii.heraldry.content.ItemHeraldry;
import vazkii.heraldry.content.TileEntityBanner;
import vazkii.heraldry.core.network.GuiHandler;
import vazkii.heraldry.core.network.PacketPayload;
import vazkii.heraldry.lib.LibContent;
import vazkii.heraldry.lib.LibMisc;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public static boolean preloadTextures = true;

	public static Item itemHeraldry;
	public static Block blockHeraldry;

	public void preInit(FMLPreInitializationEvent event) {
		initConfig(event);
		initContent();
	}

	public void init() {
		NetworkRegistry.instance().registerGuiHandler(CraftHeraldry.instance, new GuiHandler());
	}

	public void recieveSyncPacket(PacketPayload payload) {
		// NO-OP
	}

	private void initConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();

		LibContent.itemID = config.getItem(LibContent.HERALDRY_ITEM_NAME, LibContent.DEFAULT_ITEM_ID).getInt(LibContent.DEFAULT_ITEM_ID);
		LibContent.blockID = config.getBlock(LibContent.HERALDRY_BLOCK_NAME, LibContent.DEFAULT_BLOCK_ID).getInt(LibContent.DEFAULT_BLOCK_ID);

		config.save();
	}

	void initContent() {
		itemHeraldry = new ItemHeraldry(LibContent.itemID);

		blockHeraldry = new BlockHeraldry(LibContent.blockID);

		GameRegistry.registerTileEntity(TileEntityBanner.class, LibMisc.MOD_ID + "_" + LibContent.HERALDRY_BLOCK_NAME);

		GameRegistry.addShapedRecipe(new ItemStack(itemHeraldry, 1, 0),
				" P ", "SPG", " P ",
				'P', Item.paper,
				'S', Item.silk,
				'G', Item.goldNugget);

		GameRegistry.addShapedRecipe(new ItemStack(itemHeraldry, 1, 1),
				"SIS", " W ", "SPS",
				'S', Item.stick,
				'I', Item.ingotIron,
				'W', new ItemStack(Block.cloth, 0, Short.MAX_VALUE),
				'P', new ItemStack(Block.planks, 0, Short.MAX_VALUE));

		GameRegistry.addShapedRecipe(new ItemStack(itemHeraldry, 1, 2),
				"SPS", " I ", " W ",
				'S', Item.stick,
				'I', Item.ingotIron,
				'W', new ItemStack(Block.cloth, 0, Short.MAX_VALUE),
				'P', new ItemStack(Block.planks, 0, Short.MAX_VALUE));
	}
}
