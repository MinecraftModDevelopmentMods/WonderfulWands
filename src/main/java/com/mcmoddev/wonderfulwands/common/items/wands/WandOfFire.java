package com.mcmoddev.wonderfulwands.common.items.wands;

import com.mcmoddev.wonderfulwands.WonderfulWands;
import com.mcmoddev.wonderfulwands.common.projectiles.Fireball;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WandOfFire extends Wand {
	public static final String itemName = "wand_fire";

	public static int cooldown = 15;

	public static int defaultCharges = 64;

	public WandOfFire() {
		super(defaultCharges);
		this.setTranslationKey(WonderfulWands.MODID + "_" + itemName);
	}

	@Override
	public int getBaseRepairCost() {
		return 2;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return cooldown;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerEntity, EnumHand hand) {
		playerEntity.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, playerEntity.getHeldItemMainhand());
	}

	/**
	 * Callback for item usage, invoked when right-clicking on a block. If the item
	 * does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord,
							 EnumFacing blockFace, float par8, float par9, float par10) {
		return false;
	}


	/**
	 * Invoked when the player releases the right-click button
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack srcItemStack, World world, EntityLivingBase playerEntity,
									 int timeRemain) {
		super.onPlayerStoppedUsing(srcItemStack, world, playerEntity, timeRemain);
	}

	/**
	 * This method is invoked after the item has been used for an amount of time equal to the duration
	 * provided to the EntityPlayer.setItemInUse(stack, duration).
	 */
	@Override
	public ItemStack onItemUseFinish(ItemStack srcItemStack, World world, EntityLivingBase playerEntity) { //

		if (playerEntity instanceof EntityPlayer && !((EntityPlayer) playerEntity).capabilities.isCreativeMode) {
			if (isOutOfCharge(srcItemStack)) {
				// wand out of magic
				playSound(noChargeAttackSound, world, playerEntity);
				return srcItemStack;
			}
			srcItemStack.damageItem(1, playerEntity);
		}

		playSound(SoundEvents.ENTITY_FIREWORK_LAUNCH, world, playerEntity);

		if (!world.isRemote) {
			double vecX = (double) (-MathHelper.sin(playerEntity.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float) Math.PI));
			double vecY = (double) (-MathHelper.sin(playerEntity.rotationPitch / 180.0F * (float) Math.PI));
			double vecZ = (double) (MathHelper.cos(playerEntity.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float) Math.PI));

			double deltaX = (double) (-MathHelper.sin(playerEntity.rotationYaw / 180.0F * (float) Math.PI));
			double deltaZ = (double) (MathHelper.cos(playerEntity.rotationYaw / 180.0F * (float) Math.PI));
			Fireball fireball = new Fireball(world, playerEntity, playerEntity.posX + deltaX,
				playerEntity.posY + 1, playerEntity.posZ + deltaZ, vecX, vecY, vecZ);
			world.spawnEntity(fireball);
		}
		return srcItemStack;
	}
}
