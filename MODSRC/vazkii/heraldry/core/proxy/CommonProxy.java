package vazkii.heraldry.core.proxy;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vazkii.heraldry.CraftHeraldry;
import vazkii.heraldry.content.BlockHeraldry;
import vazkii.heraldry.content.ItemHeraldry;
import vazkii.heraldry.content.TileEntityBanner;
import vazkii.heraldry.core.network.GuiHandler;
import vazkii.heraldry.core.network.PacketHandler;
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
		initContent();
	}

	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(CraftHeraldry.instance, new GuiHandler());
		PacketHandler.init();
	}

	void initContent() {
		itemHeraldry = new ItemHeraldry();
		blockHeraldry = new BlockHeraldry();

		GameRegistry.registerItem(itemHeraldry, LibContent.HERALDRY_ITEM_NAME);
		GameRegistry.registerBlock(blockHeraldry, LibContent.HERALDRY_BLOCK_NAME);

		GameRegistry.registerTileEntity(TileEntityBanner.class, LibMisc.MOD_ID + "_" + LibContent.HERALDRY_BLOCK_NAME);

		GameRegistry.addShapedRecipe(new ItemStack(itemHeraldry, 1, 0),
				" P ", "SPG", " P ",
				'P', Items.paper,
				'S', Items.string,
				'G', Items.gold_nugget);

		GameRegistry.addShapedRecipe(new ItemStack(itemHeraldry, 1, 1),
				"SIS", " W ", "SPS",
				'S', Items.stick,
				'I', Items.iron_ingot,
				'W', new ItemStack(Blocks.wool, 0, Short.MAX_VALUE),
				'P', new ItemStack(Blocks.planks, 0, Short.MAX_VALUE));

		GameRegistry.addShapedRecipe(new ItemStack(itemHeraldry, 1, 2),
				"SPS", " I ", " W ",
				'S', Items.stick,
				'I', Items.iron_ingot,
				'W', new ItemStack(Blocks.wool, 0, Short.MAX_VALUE),
				'P', new ItemStack(Blocks.planks, 0, Short.MAX_VALUE));
	}
}
