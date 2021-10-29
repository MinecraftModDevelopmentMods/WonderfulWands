package com.mcmoddev.wonderfulwands.util;

import com.google.gson.JsonObject;
import com.mcmoddev.wonderfulwands.init.WonderfulConfig;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class HatRecipesEnabled implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> WonderfulConfig.enableHatRecipes;
	}
}
