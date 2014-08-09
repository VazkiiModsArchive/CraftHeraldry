package vazkii.heraldry.content;

import vazkii.heraldry.core.proxy.CommonProxy;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeHeldBanner implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		boolean foundScroll = false;
		boolean foundBanner = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(stack != null) {
				int dmg = stack.getItemDamage();
				if(stack.getItem() == CommonProxy.itemHeraldry && (dmg == 0 && !foundScroll) || (dmg == 1 && !foundBanner)) {
					foundScroll = foundScroll || dmg == 0;
					foundBanner = foundBanner || dmg == 1;
				}
				else return false; // Found an invalid item, breaking the recipe
			}
		}

		System.out.println(foundScroll + " " + foundBanner);
		return foundScroll && foundBanner;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		ItemStack scroll = null;
		ItemStack banner = null;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(stack != null) {
				int dmg = stack.getItemDamage();
				if(stack.getItem() == CommonProxy.itemHeraldry && (dmg == 0 && scroll == null) || (dmg == 1 && banner == null)) {
					if(dmg == 0)
						scroll = stack;
					else banner = stack;
				}
				else return null; // Found an invalid item, breaking the recipe
			}
		}

		if(scroll == null || banner == null)
			return null;

		ItemStack bannerCopy = banner.copy();
		ItemHeraldry.writeCrestData(bannerCopy, ItemHeraldry.readCrestData(scroll));
		
		return bannerCopy;
	}


	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	
}
