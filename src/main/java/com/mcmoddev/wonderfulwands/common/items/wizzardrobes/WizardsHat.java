package com.mcmoddev.wonderfulwands.common.items.wizzardrobes;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.client.WizardHatRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * Wizards and Witches hats are expensive head-slot items that are rendered in
 * 3D. A new hat can be combined with a potion to make the potion effect
 * permanent (wearing the hat continuously reapplies the potion effect).
 *
 * @author cybergnome
 */
public class WizardsHat extends ItemArmor {

	public static final int potionApplyInterval = 19;
	public static final int potionDuration = 11 * 20;

	public WizardsHat() {
		super(WonderfulWands.NONARMOR, 0, EntityEquipmentSlot.HEAD);
		this.setCreativeTab(WonderfulWands.TAB_ROBES);
		// set values
		this.setMaxDamage(1);
		this.setHasSubtypes(true);
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	public void setPotionEffectID(ItemStack src, String potionEffectCode) {
		NBTTagCompound tag;
		if (src.hasTagCompound() == false) {
			tag = new NBTTagCompound();
		} else {
			tag = src.getTagCompound();
		}
		tag.setString("EffectID", potionEffectCode);
		src.setTagCompound(tag);
	}

	public String getPotionEffectID(ItemStack src) {
		if (src.hasTagCompound() == false) return null;
		NBTTagCompound tag = src.getTagCompound();
		if (tag.hasKey("EffectID")) {
			return tag.getString("EffectID");
		} else {
			return null;
		}
	}

	public void setPotionEffectLevel(ItemStack src, int level) {
		NBTTagCompound tag;
		if (src.hasTagCompound() == false) {
			tag = new NBTTagCompound();
		} else {
			tag = src.getTagCompound();
		}
		tag.setByte("EffectLvl", (byte) level);
		src.setTagCompound(tag);
	}

	public int getPotionEffectLevel(ItemStack src) {
		if (src.hasTagCompound() == false) return 0;
		NBTTagCompound tag = src.getTagCompound();
		if (tag.hasKey("EffectLvl")) {
			return tag.getByte("EffectLvl");
		} else {
			return 0;
		}
	}


	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped biped) {
		return new WizardHatRenderer();
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return WonderfulWands.MODID + ":textures/models/armor/empty_armor_layer_1.png";
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial) {
		// repair with string or wool
		return false;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack src) {
		super.onArmorTick(world, player, src);
		if (world.getTotalWorldTime() % (potionApplyInterval) == 0) {
			if (this.getPotionEffectID(src) == null) {
				if (player.getActivePotionEffects().isEmpty() == false) {
					// soak up a potion effect
					Collection<PotionEffect> c = player.getActivePotionEffects();
					PotionEffect[] effect = c.toArray(new PotionEffect[c.size()]);
					int i = world.rand.nextInt(effect.length);
					String potionCode = effect[i].getPotion().getRegistryName().toString();
					this.setPotionEffectID(src, potionCode);
					this.setPotionEffectLevel(src, effect[i].getAmplifier());
					player.removePotionEffect(effect[i].getPotion());
				}
			} else {
				Potion pot = Potion.getPotionFromResourceLocation(this.getPotionEffectID(src));
				if (pot == null) {
					return;
				}
				int level = this.getPotionEffectLevel(src);
				player.addPotionEffect(new PotionEffect(pot, potionDuration, level, false, false));
			}
		}
	}


	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String potionID = this.getPotionEffectID(stack);
		if (potionID != null && Potion.getPotionFromResourceLocation(potionID) != null) {
			tooltip.add(I18n.translateToLocal(Potion.getPotionFromResourceLocation(potionID).getName()));
		}
	}

}
