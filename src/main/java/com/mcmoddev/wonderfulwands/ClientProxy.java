package com.mcmoddev.wonderfulwands;

import com.mcmoddev.wonderfulwands.graphics.LightWispRenderer;
import com.mcmoddev.wonderfulwands.graphics.MagicMissileRenderer;
import com.mcmoddev.wonderfulwands.projectiles.EntityMagicMissile;
import com.mcmoddev.wonderfulwands.projectiles.EntityWandLightningBolt;
import com.mcmoddev.wonderfulwands.wizardrobes.TopHatRenderer;
import com.mcmoddev.wonderfulwands.wizardrobes.WitchHatRenderer;
import com.mcmoddev.wonderfulwands.wizardrobes.WizardHatRenderer;
import com.mcmoddev.wonderfulwands.entities.EntityLightWisp;
import com.mcmoddev.wonderfulwands.graphics.WandLightningBoltRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends Proxy{
	public static TopHatRenderer topHatRenderer;
	public static WizardHatRenderer wizardHatRenderer;
	public static WizardHatRenderer witchHatRenderer;
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        // client-only pre-init code
		// add entity renderers
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, (RenderManager rm)->{return new MagicMissileRenderer(rm);});
		RenderingRegistry.registerEntityRenderingHandler(EntityWandLightningBolt.class, (RenderManager rm)->{return new WandLightningBoltRenderer(rm);});
		RenderingRegistry.registerEntityRenderingHandler(EntityLightWisp.class, (RenderManager rm)->{return new LightWispRenderer(rm);});
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
