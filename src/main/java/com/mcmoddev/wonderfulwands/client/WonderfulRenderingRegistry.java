package com.mcmoddev.wonderfulwands.client;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.client.entities.LightWispRenderer;
import com.mcmoddev.wonderfulwands.client.entities.MagicMissileRenderer;
import com.mcmoddev.wonderfulwands.client.entities.WandLightningBoltRenderer;
import com.mcmoddev.wonderfulwands.common.entities.EntityLightWisp;
import com.mcmoddev.wonderfulwands.common.items.wizzardrobes.WizardingArmor;
import com.mcmoddev.wonderfulwands.common.projectiles.EntityBoltLightning;
import com.mcmoddev.wonderfulwands.common.projectiles.EntityMagicMissile;
import com.mcmoddev.wonderfulwands.init.WonderfulBlocks;
import com.mcmoddev.wonderfulwands.init.WonderfulItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = WonderfulWands.MODID, value = Side.CLIENT)
public class WonderfulRenderingRegistry {

	@SubscribeEvent
	public static void onRegisterRenders(ModelRegistryEvent event) {
		initModel(WonderfulItems.WAND_GENERIC);
		initModel(WonderfulItems.WAND_OF_MAGIC_MISSILE);
		initModel(WonderfulItems.WAND_OF_DEATH);
		initModel(WonderfulItems.WAND_OF_FIRE);
		initModel(WonderfulItems.WAND_OF_GROWTH);
		initModel(WonderfulItems.WAND_OF_HARVESTING);
		initModel(WonderfulItems.WAND_OF_HEALING);
		initModel(WonderfulItems.WAND_OF_ICE);
		initModel(WonderfulItems.WAND_OF_MINING);
		initModel(WonderfulItems.WAND_OF_TELEPORTATION);
		initModel(WonderfulItems.WAND_OF_LIGHT);
		initModel(WonderfulItems.WAND_OF_GREATER_LIGHT);
		initModel(WonderfulItems.WAND_OF_STORMS);
		initModel(WonderfulItems.WAND_OF_LIGHTNING);
		initModel(WonderfulItems.WAND_OF_BRIDGING);
		initModel(WonderfulItems.WAND_OF_CLIMBING);
		initModel(WonderfulItems.WAND_OF_ILLUSIONS);
		initModel(WonderfulItems.WAND_OF_RAILS);
		initModel(WonderfulItems.WAND_OF_WEBBING);
		initModel(WonderfulItems.WAND_OF_LEVITATION);
		initModel(WonderfulItems.WAND_OF_TUNNELING);
		initModel(WonderfulItems.TOP_HAT);
		initModel(WonderfulItems.WITCHES_HAT);
		initModel(WonderfulItems.WIZARDS_HAT);

		initModel(Item.getItemFromBlock(WonderfulBlocks.FEY_RAIL));
		initModel(Item.getItemFromBlock(WonderfulBlocks.FEY_RAIL_POWERED));
		initModel(Item.getItemFromBlock(WonderfulBlocks.MAGE_LIGHT));

		RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, MagicMissileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBoltLightning.class, WandLightningBoltRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLightWisp.class, LightWispRenderer::new);

		new TopHatRenderer();
		new WitchHatRenderer();
		new WizardHatRenderer();

		registerArmorRenders();
	}

	private static void registerArmorRenders() {
		for (int color = 0; color < WizardingArmor.robes.length; color++) {
			for (int slot = 0; slot < WizardingArmor.robes[0].length; slot++) {
				initModel(WizardingArmor.robes[color][slot], WonderfulWands.MODID + ":robes_"
					+ WizardingArmor.colorSuffixes[color] + "_"
					+ WizardingArmor.slotName.get(WizardingArmor.armorSlots[slot]));
			}
		}
	}

	private static void initModel(Item item, String itemName) {
		ModelLoader.setCustomModelResourceLocation(item, 0,
			new ModelResourceLocation(itemName, "inventory"));
	}

	private static void initModel(final Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0,
			new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
	}
}
