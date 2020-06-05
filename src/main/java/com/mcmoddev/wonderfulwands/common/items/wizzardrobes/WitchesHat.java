package com.mcmoddev.wonderfulwands.common.items.wizzardrobes;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.client.WitchHatRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Wizards and Witches hats are expensive head-slot items that are rendered in
 * 3D. A new hat can be combined with a potion to make the potion effect
 * permanent (wearing the hat continuously reapplies the potion effect).
 *
 * @author cybergnome
 */
public class WitchesHat extends WizardsHat {

	public WitchesHat() {
		super();
		this.setCreativeTab(WonderfulWands.TAB_ROBES);
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(final EntityLivingBase entityLiving,
									final ItemStack itemStack, final EntityEquipmentSlot armorSlot,
									final ModelBiped biped) {
		return new WitchHatRenderer();
	}
}
