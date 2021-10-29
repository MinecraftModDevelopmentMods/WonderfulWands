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

	@Config.LangKey(LANG + "altRecipesEnabled")
	@Config.Comment({"If true, then robes and wands will use different recipes than normal"})
	@Config.RequiresMcRestart
	public static boolean altRecipesEnabled = false;

	@Config.LangKey(LANG + "enableHats")
	@Config.Comment({"If true, magical hats will be craftable, if you wish to disable the "
		+ "infinite potion side effect that the Witches and Wizards hat have then please set "
		+ "'enableHatEffects' to false"})
	@Config.RequiresMcRestart
	public static boolean enableHatRecipes = true;

	@Config.LangKey(LANG + "enableHatEffects")
	@Config.Comment("If true, then the Wizard's Hat and Witch's Hat effects will be enabled (if "
		+ "false, the hats will still be craftable but the effects will not work). "
		+ "These hats are very powerful and you may want to disable them if you expect there to "
		+ "be troublemakers (aka griefers)")
	@Config.RequiresMcRestart
	public static boolean enableHatEffects = true;

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
