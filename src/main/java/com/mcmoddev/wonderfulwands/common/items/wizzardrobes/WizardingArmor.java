package com.mcmoddev.wonderfulwands.common.items.wizzardrobes;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class WizardingArmor extends ItemArmor {

	public static final int[] robeDamageReduction = new int[]{1, 1, 1, 1}; // head, shoulders, knees, and toes
	public static final int robeEnchantibility = 40;
	public static final int robeMaxDamageFactor = 15;
	private static final String itemName = "robes";

	public static ItemArmor.ArmorMaterial WIZARD_ROBES = EnumHelper.addArmorMaterial("WIZARDCLOTH",
		"wizard_robes", 15, new int[]{1, 1, 1, 1},
		40, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);

	public static WizardingArmor[][] robes = new WizardingArmor[16][4]; // [color][slot]

	public static final EntityEquipmentSlot[] armorSlots = {
		EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET
	};

	public static final String[] colorSuffixes = {
		"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink",
		"lime", "yellow", "light_blue", "magenta", "orange", "white"
	};



	final private String color;

	public static final Map<EntityEquipmentSlot, String> slotName = new HashMap<>();

	static {
		slotName.put(EntityEquipmentSlot.HEAD, "helmet");
		slotName.put(EntityEquipmentSlot.CHEST, "chestplate");
		slotName.put(EntityEquipmentSlot.LEGS, "leggings");
		slotName.put(EntityEquipmentSlot.FEET, "boots");
	}

	public WizardingArmor(ArmorMaterial material, String color, EntityEquipmentSlot armorSlot) {
		super(material, material.ordinal(), armorSlot);
		//super(ArmorMaterial.CHAIN,1, armorSlot); // does render as chainmail.
		String name = itemName + "_" + color + "_" + slotName.get(armorSlot);
		this.setTranslationKey(WonderfulWands.MODID + "_" + name);
		this.setCreativeTab(WonderfulWands.TAB_ROBES);
		this.color = color;
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack rawMaterial) {
		// repair with string or wool
		return rawMaterial.getTranslationKey().equals(Items.STRING.getTranslationKey())
			|| rawMaterial.getTranslationKey().equals(Blocks.WOOL.getTranslationKey());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity e, EntityEquipmentSlot slot, String layer) {
		// layer will either be "overlay" or null (ignore this variable)
		return WonderfulWands.MODID + ":textures/models/armor/robes_" + color + "_layer_"
			+ (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png";
	}
}
