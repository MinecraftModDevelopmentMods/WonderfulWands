package com.mcmoddev.wonderfulwands;

import com.mcmoddev.wonderfulwands.client.LightWispRenderer;
import com.mcmoddev.wonderfulwands.client.MagicMissileRenderer;
import com.mcmoddev.wonderfulwands.client.WandLightningBoltRenderer;
import com.mcmoddev.wonderfulwands.common.entities.EntityLightWisp;
import com.mcmoddev.wonderfulwands.common.items.wizardrobes.TopHatRenderer;
import com.mcmoddev.wonderfulwands.common.items.wizardrobes.WitchHatRenderer;
import com.mcmoddev.wonderfulwands.common.items.wizardrobes.WizardHatRenderer;
import com.mcmoddev.wonderfulwands.common.projectiles.EntityMagicMissile;
import com.mcmoddev.wonderfulwands.common.projectiles.EntityWandLightningBolt;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends Proxy {
	public static TopHatRenderer topHatRenderer;
	public static WizardHatRenderer wizardHatRenderer;
	public static WizardHatRenderer witchHatRenderer;

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		// client-only pre-init code
		// add entity renderers
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, (RenderManager rm) -> {
			return new MagicMissileRenderer(rm);
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWandLightningBolt.class, (RenderManager rm) -> {
			return new WandLightningBoltRenderer(rm);
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityLightWisp.class, (RenderManager rm) -> {
			return new LightWispRenderer(rm);
		});
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		// client-only init code

		// hat renderers
		topHatRenderer = new TopHatRenderer();
		wizardHatRenderer = new WizardHatRenderer();
		witchHatRenderer = new WitchHatRenderer();

		//

	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		// client-only post-init code
	}

}
