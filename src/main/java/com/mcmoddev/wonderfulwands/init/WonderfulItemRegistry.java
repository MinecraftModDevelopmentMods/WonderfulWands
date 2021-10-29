package com.mcmoddev.wonderfulwands.init;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfBridging;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfClimbing;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfDeath;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfFire;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfGreaterLight;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfGrowth;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfHarvesting;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfHealing;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfIce;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfIllusions;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfLevitation;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfLight;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfLightning;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfMagicMissile;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfMining;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfRails;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfStorms;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfTeleportation;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfTunneling;
import com.mcmoddev.wonderfulwands.common.items.wands.WandOfWebbing;
import com.mcmoddev.wonderfulwands.common.items.wizzardrobes.TopHat;
import com.mcmoddev.wonderfulwands.common.items.wizzardrobes.WitchesHat;
import com.mcmoddev.wonderfulwands.common.items.wizzardrobes.WizardingArmor;
import com.mcmoddev.wonderfulwands.common.items.wizzardrobes.WizardsHat;
import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = WonderfulWands.MODID)
public class WonderfulItemRegistry {

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
			nameWand(new Item(), "wand_generic"),
			nameWand(new WandOfMagicMissile(), "wand_of_magic_missile"),
			nameWand(new WandOfDeath(), "wand_of_death"),
			nameWand(new WandOfFire(), "wand_of_fire"),
			nameWand(new WandOfGrowth(), "wand_of_growth"),
			nameWand(new WandOfHarvesting(), "wand_of_harvesting"),
			nameWand(new WandOfHealing(), "wand_of_healing"),
			nameWand(new WandOfIce(), "wand_of_ice"),
			nameWand(new WandOfMining(), "wand_of_mining"),
			nameWand(new WandOfTeleportation(), "wand_of_teleportation"),
			nameWand(new WandOfLight(), "wand_of_light"),
			nameWand(new WandOfGreaterLight(), "wand_of_greater_light"),
			nameWand(new WandOfStorms(), "wand_of_storms"),
			nameWand(new WandOfLightning(), "wand_of_lightning"),
			nameWand(new WandOfBridging(), "wand_of_bridging"),
			nameWand(new WandOfClimbing(), "wand_of_climbing"),
			nameWand(new WandOfIllusions(), "wand_of_illusions"),
			nameWand(new WandOfRails(), "wand_of_rails"),
			nameWand(new WandOfWebbing(), "wand_of_webbing"),
			nameWand(new WandOfLevitation(), "wand_of_levitation"),
			nameWand(new WandOfTunneling(), "wand_of_tunneling"),

			nameItem(new TopHat(), "top_hat"),
			nameItem(new WitchesHat(), "witches_hat"),
			nameItem(new WizardsHat(), "wizards_hat"),

			nameBlockItem(WonderfulBlocks.FEY_RAIL),
			nameBlockItem(WonderfulBlocks.FEY_RAIL_POWERED),
			nameBlockItem(WonderfulBlocks.MAGE_LIGHT)
		);

		for (int colorIndex = 0; colorIndex < 16; colorIndex++) {
			int slotIndex = 0;
			for (int i = 0; i < 4; i++) {
				EntityEquipmentSlot armorSlot = WizardingArmor.armorSlots[slotIndex];
				String color = WizardingArmor.colorSuffixes[colorIndex];
				WizardingArmor robe = new WizardingArmor(WizardingArmor.WIZARD_ROBES, color, armorSlot);
				event.getRegistry().registerAll(
					registerRobes(robe, "robes_" + color + "_" + WizardingArmor.slotName.get(armorSlot))
				);
				WizardingArmor.robes[colorIndex][slotIndex] = robe;
				slotIndex++;
			}
		}
	}

	private static Item nameItem(Item item, String name) {
		item
			.setRegistryName(name)
			.setTranslationKey(WonderfulWands.MODID + "." + name)
			.setCreativeTab(WonderfulWands.TAB_ROBES);
		return item;
	}

	private static Item nameWand(Item item, String name) {
		item
			.setRegistryName(name)
			.setTranslationKey(WonderfulWands.MODID + "." + name)
			.setCreativeTab(WonderfulWands.TAB_WANDS);
		return item;
	}

	private static ItemBlock nameBlockItem(Block block) {
		ItemBlock item = new ItemBlock(block);
		ResourceLocation name = block.getRegistryName();
		item.setRegistryName(name);
		return item;
	}

	private static ItemArmor registerRobes(WizardingArmor item, String name) {
		item
			.setRegistryName(name)
			.setTranslationKey(WonderfulWands.MODID + "." + name)
			.setCreativeTab(WonderfulWands.TAB_ROBES);
		return item;
	}
}
