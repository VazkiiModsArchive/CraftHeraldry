package vazkii.heraldry.content;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.heraldry.CraftHeraldry;
import vazkii.heraldry.core.data.CrestData;
import vazkii.heraldry.core.proxy.CommonProxy;
import vazkii.heraldry.lib.LibContent;
import vazkii.heraldry.lib.LibMisc;
import vazkii.heraldry.lib.LibResources;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHeraldry extends Item {

	IIcon iconScroll;
	IIcon iconBanner;
	IIcon iconWallBanner;

	public ItemHeraldry() {
		super();
		setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName(LibContent.HERALDRY_ITEM_NAME);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() == 0 ? LibContent.SCROLL_DISPLAY_NAME : par1ItemStack.getItemDamage() == 1 ? LibContent.BANNER_DISPLAY_NAME : LibContent.WALL_BANNER_DISPLAY_NAME;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		iconScroll = par1IconRegister.registerIcon(LibMisc.MOD_ID + ":" + LibResources.ICON_SCROLL);
		iconBanner = par1IconRegister.registerIcon(LibMisc.MOD_ID + ":" + LibResources.ICON_BANNER);
		iconWallBanner = par1IconRegister.registerIcon(LibMisc.MOD_ID + ":" + LibResources.ICON_WALL_BANNER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return par1 == 0 ? iconScroll : par1 == 1 ? iconBanner : iconWallBanner;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par1ItemStack.getItemDamage() == 0)
			par3EntityPlayer.openGui(CraftHeraldry.instance, 0, par2World, 0, 0, 0);

		return par1ItemStack;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if(par7 == ForgeDirection.DOWN.ordinal() || par1ItemStack.getItemDamage() == 0 || par1ItemStack.getItemDamage() > 2)
			return false;

		if(par7 == ForgeDirection.UP.ordinal() && par1ItemStack.getItemDamage() == 1) {
			boolean can = par3World.isAirBlock(par4, par5 + 1, par6) && par3World.isAirBlock(par4, par5 + 2, par6);
			if(can) {
				float yaw = par2EntityPlayer.rotationYaw;
				while(yaw < 0)
					yaw += 360F;

				int meta = Math.round((yaw % 360F - 90F) / 45F);
				if(meta < 0)
					meta = 6 - meta;
				if(meta > 7)
					meta = 6;

				par3World.setBlock(par4, par5 + 1, par6, CommonProxy.blockHeraldry, meta, 2);
				par3World.playSoundEffect(par4, par5, par6, "step.wood", 1F, 0.2F);
				if(!par3World.isRemote)
					par2EntityPlayer.swingItem();
				par1ItemStack.stackSize--;
				return true;
			}
		} else if(par1ItemStack.getItemDamage() == 2) {
			ForgeDirection direction = ForgeDirection.getOrientation(par7);
			boolean can = par3World.isSideSolid(par4, par5, par6, direction, false) && par3World.isAirBlock(par4 + direction.offsetX, par5 - 1, par6 + direction.offsetZ);
			if(can) {
				int meta = (par7 == 2 ? 3 : par7 == 3 ? 1 : par7 == 4 ? 2 : 0) * 2 + 8;

				par3World.setBlock(par4 + direction.offsetX, par5, par6 + direction.offsetZ, CommonProxy.blockHeraldry, meta, 2);
				par3World.playSoundEffect(par4, par5, par6, "step.wood", 1F, 0.2F);
				if(!par3World.isRemote)
					par2EntityPlayer.swingItem();
				par1ItemStack.stackSize--;
			}
		}
		return false;
	}

	public static void writeCrestData(ItemStack stack, CrestData data) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		data.writeToCmp(stack.getTagCompound());
	}

	public static CrestData readCrestData(ItemStack stack) {
		if(!stack.hasTagCompound())
			return new CrestData(0xFFFFFF, 0xFFFFFF, (short) -1);

		return CrestData.readFromCmp(stack.getTagCompound());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack.getItemDamage() == 0)
			par3List.add((readCrestData(par1ItemStack) == null ? "Blank, " : "") + "Right-Click to Edit");
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() == 0 && readCrestData(par1ItemStack) != null ? EnumRarity.rare : EnumRarity.common;
	}
}
