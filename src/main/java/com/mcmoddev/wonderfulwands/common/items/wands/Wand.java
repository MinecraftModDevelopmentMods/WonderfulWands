package com.mcmoddev.wonderfulwands.common.items.wands;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class Wand extends Item {

	/**
	 * Vanilla Minecraft sound to play when you try to use a wand that has no charge left.
	 */
	public static SoundEvent noChargeAttackSound = SoundEvents.ENTITY_ITEM_PICKUP;

	public Wand(final int numCharges) {
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(numCharges + 1);
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Nonnull
	@Override
	public EnumAction getItemUseAction(@Nonnull final ItemStack itemStack) {
		return EnumAction.BLOCK;
	}

	private List<ItemStack> allowedItems = null;

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(@Nonnull final ItemStack itemStack, @Nonnull final ItemStack rawMaterial) {
		// repair with gold ingots
		if (allowedItems == null) allowedItems = OreDictionary.getOres("ingotGold");
		for (int i = 0; i < allowedItems.size(); i++) {
			if (allowedItems.get(i).getTranslationKey().equals(rawMaterial.getTranslationKey())) return true;
		}
		return false;
	}

	/**
	 * returns true if the wand is on its last damage point
	 */
	public boolean isOutOfCharge(@Nonnull final ItemStack itemStack) {
		return itemStack.getItemDamage() >= (itemStack.getMaxDamage() - 1);
	}

	/**
	 * plays a sound at the player location
	 */
	protected void playSound(SoundEvent sound, World world, Entity playerEntity) {
		playSound(world, playerEntity.getPositionVector().add(0, 1, 0), 12, sound);
	}

	public abstract int getBaseRepairCost();

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing,
									  float hitX, float hitY, float hitZ) {
		if (onItemUse(player.getHeldItemMainhand(), player, w, pos, facing, hitX, hitY, hitZ)) {
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	protected abstract boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float partialX, float partialY, float partialZ);


	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		StringBuilder sb = new StringBuilder();
		int max = stack.getMaxDamage() - 1;
		sb.append(max - stack.getItemDamage()).append('/').append(max);
		tooltip.add(sb.toString());
	}

	protected void playSound(World w, Vec3d position, double range, SoundEvent sound) {
		playSound(w, position, range, sound, 1.0F, 1.0F);
	}

	protected void playSound(World w, Vec3d position, double range, SoundEvent sound, float volume, float pitch) {
		if (w.isRemote) {
			return;
		}
		AxisAlignedBB area = new AxisAlignedBB(
			position.x - range, position.y - range, position.z - range,
			position.x + range, position.y + range, position.z + range
		);

		List<EntityPlayerMP> players = w.getEntitiesWithinAABB(EntityPlayerMP.class, area);
		SPacketCustomSound soundPacket = new SPacketCustomSound(sound.getRegistryName().toString(), SoundCategory.PLAYERS,
			position.x, position.y, position.z, volume, pitch);
		for (EntityPlayerMP player : players) {
			player.connection.sendPacket(soundPacket);
		}
	}

	protected void playFadedSound(World w, Vec3d position, double range, SoundEvent sound, float volume, float pitch) {
		if (w.isRemote) return;
		AxisAlignedBB area = new AxisAlignedBB(
			position.x - range, position.y - range, position.z - range,
			position.x + range, position.y + range, position.z + range
		);
		List<EntityPlayerMP> players = w.getEntitiesWithinAABB(EntityPlayerMP.class, area);
		for (EntityPlayerMP player : players) {
			float distSqr = (float) player.getPositionVector().squareDistanceTo(position);
			float localVolume = Math.min(volume, volume / distSqr);
			SPacketCustomSound soundPacket = new SPacketCustomSound(sound.getRegistryName().toString(), SoundCategory.PLAYERS,
				position.x, position.y, position.z, localVolume, pitch);
			player.connection.sendPacket(soundPacket);
		}
	}
}
