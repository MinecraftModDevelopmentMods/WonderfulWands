package com.mcmoddev.wonderfulwands.init;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = WonderfulWands.MODID)
@Mod.EventBusSubscriber(modid = WonderfulWands.MODID)
public class WonderfulConfig {

	private static final String LANG = WonderfulWands.MODID + ".config.";

	@Config.LangKey(LANG + "altRecipes")
	@Config.Comment({"If true, then robes and wands will use different recipes than normal"})
	@Config.RequiresMcRestart
	public static boolean altRecipes = false;

	@Config.LangKey(LANG + "enableHats")
	@Config.Comment("If true, then the Wizard's Hat and Witch's Hat items will be craftable (if "
		+ "false, the hats will not be craftable). These hats are very powerful and you "
		+ "may want to disable them if you expect there to be troublemakers (aka "
		+ "griefers)")
	public static boolean enableHats = true;

	@Mod.EventBusSubscriber(modid = WonderfulWands.MODID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(WonderfulWands.MODID)) {
				ConfigManager.sync(WonderfulWands.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
