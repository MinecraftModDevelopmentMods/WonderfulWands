package com.mcmoddev.wonderfulwands;

import com.mcmoddev.wonderfulwands.init.WonderfulBlocks;
import com.mcmoddev.wonderfulwands.init.WonderfulItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
	modid = WonderfulWands.MODID,
	name = WonderfulWands.NAME,
	version = WonderfulWands.VERSION,
	acceptedMinecraftVersions = WonderfulWands.MC_VERSION,
	certificateFingerprint = WonderfulWands.FINGERPRINT,
	updateJSON = WonderfulWands.UPDATE_JSON
)
public class WonderfulWands {

	//TODO Recipes need to be added.
	//TODO illusion blocks need adding back in.
	//TODO Find and catch the bugs. First one linked below. Line 62. (Hold right click the wand in game for the crash)
	/**
	 * {@link com.mcmoddev.wonderfulwands.common.entities.EntityLightWisp#writeToNBT(NBTTagCompound)}
	 */
	//TODO The second bug I know of is the wand of rails, it always places rails down... I'm not sure this is intended.

	public static final String MODID = "wonderfulwands";
	public static final String NAME = "Wonderful Wands";
	public static final String VERSION = "3.0.0";
	public static final String MC_VERSION = "[1.12,)";
	public static final String FINGERPRINT = "@FINGERPRINT@";
	public static final String UPDATE_JSON =
		"https://raw.githubusercontent.com/MinecraftModDevelopmentMods/WonderfulWands/master/update.json";
	public static final Logger LOGGER = LogManager.getLogger(NAME);

	@Mod.EventHandler
	public void onFingerprintViolation(final FMLFingerprintViolationEvent event) {
		FMLLog.bigWarning("Invalid fingerprint detected! The file " + event.getSource().getName()
			+ " may have been tampered with. This jar will NOT be supported by MMD!");
	}

	public static final CreativeTabs TAB_WANDS = new CreativeTabs("tab." + MODID + ".wands") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(WonderfulItems.WAND_GENERIC);
		}
	};

	public static final CreativeTabs TAB_ROBES = new CreativeTabs("tab." + MODID + ".armor") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(WonderfulItems.WIZARDS_HAT);
		}
	};

	public static final CreativeTabs TAB_BLOCKS = new CreativeTabs("tab." + MODID + ".blocks") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(WonderfulBlocks.MAGE_LIGHT);
		}
	};

	public static ItemArmor.ArmorMaterial NON_ARMOR = EnumHelper.addArmorMaterial("NONARMOR",
		"empty_armor", 10, new int[]{0, 0, 0, 0},
		0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LOGGER.info("Is this magical robe for me??");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		LOGGER.info("Ooo a wand too?!");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LOGGER.info("I'm a real Wizard!");
	}
}



	/* Below this line is all the old code from 1.11.2. It is to be cleaned up/removed, do not enable it.

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		IllusoryBlock illusion;
		illusion = new IllusoryBlock(Blocks.DIRT);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.GRASS);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.PLANKS.getMapColor(Blocks.PLANKS.getDefaultState()), "illusion_oak_planks", Blocks.PLANKS);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.BOOKSHELF);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.STONE);
		illusion.setTranslationKey("stone.stone");
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.COBBLESTONE.getMapColor(Blocks.COBBLESTONE.getDefaultState()), "illusion_cobblestone", Blocks.COBBLESTONE, "stonebrick");
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.BRICK_BLOCK);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.HAY_BLOCK.getMapColor(Blocks.HAY_BLOCK.getDefaultState()), "illusion_hay_block", Blocks.HAY_BLOCK);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.PUMPKIN);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.MELON_BLOCK);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.NETHER_BRICK.getMapColor(Blocks.NETHER_BRICK.getDefaultState()), "illusion_nether_brick", Blocks.NETHER_BRICK);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.NETHERRACK.getMapColor(Blocks.NETHERRACK.getDefaultState()), "illusion_netherrack", Blocks.NETHERRACK);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.END_STONE.getMapColor(Blocks.END_STONE.getDefaultState()), "illusion_end_stone", Blocks.END_STONE);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.COBBLESTONE.getMapColor(Blocks.PRISMARINE.getDefaultState()), "illusion_prismarine", Blocks.PRISMARINE, "prismarine.rough");
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.SOUL_SAND.getMapColor(Blocks.SOUL_SAND.getDefaultState()), "illusion_soul_sand", Blocks.SOUL_SAND);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.STONEBRICK.getMapColor(Blocks.STONEBRICK.getDefaultState()), "illusion_stonebrick", Blocks.STONEBRICK);
		registerBlock(illusion, illusion.name);
		illusion = new IllusoryBlock(Blocks.SAND);
		registerBlock(illusion, illusion.name);

		// recipes
		// Nonmagical
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wandGeneric), " g ", " s ", " g ", 'g', "nuggetGold", 's', "stickWood"));
		// Magic Missile
		addWandRecipe(wandOfMagicMissile, Items.GOLDEN_SWORD);
		// Fire
		addWandRecipe(wandOfFire, Items.FIRE_CHARGE);
		// Death
		addWandRecipe(wandOfDeath, new ItemStack(Items.SKULL, 1, 1));
		// Growth
		addWandRecipe(wandOfGrowth, Items.BONE);
		// Harvesting
		addWandRecipe(wandOfHarvesting, Items.SHEARS);
		// Healing
		addWandRecipe(wandOfHealing, Items.GHAST_TEAR);
		// Ice
		addWandRecipe(wandOfIce, Items.SNOWBALL);
		// Digging
		addWandRecipe(wandOfMining, Items.GOLDEN_PICKAXE);
		// Teleport
		addWandRecipe(wandOfTeleportation, Items.ENDER_EYE);
		// wand of light
		OreDictionary.registerOre("torch", Blocks.TORCH);
		addWandRecipe(wandOfLight, "torch");
		addWandRecipe(wandOfGreaterLight, new ItemStack(Blocks.REDSTONE_LAMP));
		// wand of storms
		addWandRecipe(wandOfStorms, new ItemStack(Blocks.WOOL, 1, 7));
		// wand of lightning
		addWandRecipe(wandOfLightning, "gemDiamond");
		// wand of climbing
		OreDictionary.registerOre("vine", Blocks.VINE);
		addWandRecipe(wandOfClimbing, "vine");
		// wand of bridging
		addWandRecipe(wandOfBridging, "blockIron");
		addWandRecipe(wandOfBridging, "blockSteel");
		// wand of illusions
		addWandRecipe(wandOfIllusions, Items.FERMENTED_SPIDER_EYE);
		// wand of railroads
		addWandRecipe(wandOfRails, Blocks.GOLDEN_RAIL);
		// wand of webs
		addWandRecipe(wandOfWebbing, Items.SLIME_BALL);
		// wand of levitation
		addWandRecipe(wandOfLevitation, Items.FEATHER);
		// wand of tunneling
		addWandRecipe(wandOfTunneling, Items.DIAMOND_PICKAXE);
	}

	private static void addWandRecipe(Wand output, Item specialItem) {
		if (altRecipes) {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem, 'e',
				"gemEmerald", 's', wandGeneric));
		} else {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
	}

	private static void addWandRecipe(Wand output, Block specialItem) {
		if (altRecipes) {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem, 'e',
				"gemEmerald", 's', wandGeneric));
		} else {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
	}

	private static void addWandRecipe(Wand output, ItemStack specialItem) {
		if (altRecipes) {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem, 'e',
				"gemEmerald", 's', wandGeneric));
		} else {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem, 'e',
				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
	}

	private static void addWandRecipe(Wand output, String specialItem_oreDictionary) {
		if (altRecipes) {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), " x ", " ex", "s  ", 'x', specialItem_oreDictionary, 'e',
				"gemEmerald", 's', wandGeneric));
		} else {
			GameRegistry.addRecipe(new ShapedOreRecipe(wandItemStack(output), "xex", " s ", " g ", 'x', specialItem_oreDictionary, 'e',
				"gemEmerald", 's', "stickWood", 'g', "nuggetGold"));
		}
	}

	private void registerItemRenders() {
		for (Map.Entry<Block, IllusoryBlock> entry : IllusoryBlock.getLookUpTable().entrySet()) {
			IllusoryBlock b = entry.getValue();
			Block r = entry.getKey();
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(net.minecraft.item.Item.getItemFromBlock(b), 0,
					new ModelResourceLocation(r.getRegistryName(), "inventory"));
		}

		IllusoryBlock pb = IllusoryBlock.getLookUpTable().get(Blocks.PLANKS);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(net.minecraft.item.Item.getItemFromBlock(pb), 0,
				new ModelResourceLocation(MODID + ":" + pb.name, "inventory"));
	}
	*/

