package com.mcmoddev.wonderfulwands.common.items.wizzardrobes;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.client.TopHatRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * 3D rendered head item that just looks cool.
 *
 * @author cybergnome
 */
public class TopHat extends ItemArmor {

	public TopHat() {
		super(WonderfulWands.NON_ARMOR, 0, EntityEquipmentSlot.HEAD);
		this.setCreativeTab(WonderfulWands.TAB_ROBES);
		this.setMaxDamage(100);
	}


	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(@Nonnull final EntityLivingBase entityLiving, @Nonnull final ItemStack itemStack,
									@Nonnull final EntityEquipmentSlot armorSlot, @Nonnull final ModelBiped biped) {
		return new TopHatRenderer();
	}

	@Override
	public String getArmorTexture(@Nonnull final ItemStack stack, @Nonnull final Entity entity,
								  @Nonnull final EntityEquipmentSlot slot, @Nonnull final String type) {
		return WonderfulWands.MODID + ":textures/models/armor/empty_armor_layer_1.png";
	}
}
