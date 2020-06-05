package com.mcmoddev.wonderfulwands.init;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.common.entities.EntityLightWisp;
import com.mcmoddev.wonderfulwands.common.projectiles.EntityBoltLightning;
import com.mcmoddev.wonderfulwands.common.projectiles.EntityMagicMissile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = WonderfulWands.MODID)
public class WonderfulEntityRegistry {

	@SubscribeEvent
	public static void onRegister(RegistryEvent.Register<EntityEntry> event) {
		event.getRegistry().registerAll(
			EntityEntryBuilder.create()
				.entity(EntityMagicMissile.class)
				.id(new ResourceLocation(WonderfulWands.MODID, "magic_missle"), 0)
				.name(WonderfulWands.MODID + ".magic_missle")
				.factory(EntityMagicMissile::new)
				.tracker(128, 1, true)
				.build(),

			EntityEntryBuilder.create()
				.entity(EntityBoltLightning.class)
				.id(new ResourceLocation(WonderfulWands.MODID, "lightning_bolt"), 1)
				.name(WonderfulWands.MODID + ".lightning_bolt")
				.factory(EntityBoltLightning::new)
				.tracker(128, 1, false)
				.build(),

			EntityEntryBuilder.create()
				.entity(EntityLightWisp.class)
				.id(new ResourceLocation(WonderfulWands.MODID, "light_wisp"), 2)
				.name(WonderfulWands.MODID + ".light_wisp")
				.factory(EntityLightWisp::new)
				.tracker(128, 1, true)
				.build()
		);
	}
}
