package com.mcmoddev.wonderfulwands.wizardrobes;

import net.minecraft.util.ResourceLocation;
import com.mcmoddev.wonderfulwands.WonderfulWands;

public class WitchHatRenderer extends WizardHatRenderer {
	public WitchHatRenderer(){
		super();
		super.hatTexture = new ResourceLocation(WonderfulWands.MODID+":textures/witchblack.png");
	}
}
