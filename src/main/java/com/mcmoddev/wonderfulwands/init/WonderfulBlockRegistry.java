package com.mcmoddev.wonderfulwands.init;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.common.blocks.BlockFeyRail;
import com.mcmoddev.wonderfulwands.common.blocks.BlockFeyRailPowered;
import com.mcmoddev.wonderfulwands.common.blocks.BlockMageLight;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = WonderfulWands.MODID)
public class WonderfulBlockRegistry {

	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		//TODO Add the illusory blocks from previous versions.
		event.getRegistry().registerAll(
			nameBlock(new BlockFeyRail(), "fey_rail"),
			nameBlock(new BlockFeyRailPowered(), "fey_rail_powered"),
			nameBlock(new BlockMageLight(), "mage_light")
		);
	}

	private static Block nameBlock(Block block, String name) {
		block
			.setRegistryName(name)
			.setTranslationKey(WonderfulWands.MODID + "." + name)
			.setCreativeTab(WonderfulWands.TAB_BLOCKS);
		return block;
	}
}
