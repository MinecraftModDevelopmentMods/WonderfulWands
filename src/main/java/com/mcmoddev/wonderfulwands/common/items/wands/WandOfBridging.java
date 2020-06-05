package com.mcmoddev.wonderfulwands.common.items.wands;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WandOfBridging extends Wand {

	public static int cooldown = 10;

	public static int defaultCharges = 64;

	private final int limit = 32;

	private final Block bridgeBlock;

	public WandOfBridging() {
		super(defaultCharges);
		this.bridgeBlock = Blocks.COBBLESTONE;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used.
	 */
	@Override
	public EnumAction getItemUseAction(final ItemStack itemStack) {
		return EnumAction.BOW;
	}

	@Override
	public int getBaseRepairCost() {
		return 2;
	}

	@Override
	public int getMaxItemUseDuration(@Nonnull final ItemStack itemStack) {
		return 7200;
	}

	/**
	 * Callback for item usage, invoked when right-clicking on a block. If the item
	 * does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS.
	 */
	@Override
	public boolean onItemUse(final ItemStack itemStack, final EntityPlayer playerEntity, final World world,
							 final BlockPos coord, final EnumFacing blockFace,
							 final float par8, final float par9, final float par10) {
		return false;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull final World world, final EntityPlayer playerEntity,
													@Nonnull final EnumHand hand) {
		playerEntity.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, playerEntity.getHeldItemMainhand());
	}

	@Override
	public void onPlayerStoppedUsing(@Nonnull final ItemStack itemStack, @Nonnull final World world,
									 @Nonnull final EntityLivingBase playerEntity, final int timeRemain) {
		if (playerEntity instanceof EntityPlayer && !((EntityPlayer) playerEntity).capabilities.isCreativeMode) {
			if (isOutOfCharge(itemStack)) {
				// wand out of magic
				playSound(noChargeAttackSound, world, playerEntity);
				return;
			}
		}
		int blocksChanged = 0;

		Vec3d delta = playerEntity.getLookVec();
		delta = (new Vec3d(delta.x, 0, delta.z)).normalize();
		BlockPos coord = playerEntity.getPosition().down();
		Vec3d originPrime = new Vec3d(coord.getX(), coord.getY(), coord.getZ());

		int[] changeTracker = new int[3];
		Vec3d pos = originPrime.add(delta);
		for (int i = 0; i < limit; i++) {
			int blockDelta = 0;
			BlockPos block = new BlockPos(pos);


			blockDelta += placeBridgeBlock(world, block);
			blockDelta += placeBridgeBlock(world, block.north());
			blockDelta += placeBridgeBlock(world, block.south());
			blockDelta += placeBridgeBlock(world, block.east());
			blockDelta += placeBridgeBlock(world, block.west());

			changeTracker[0] = changeTracker[1];
			changeTracker[1] = changeTracker[2];
			changeTracker[2] = blockDelta;
			if (changeTracker[0] + changeTracker[1] + changeTracker[2] == 0) {
				// hit a wall, stop
				break;
			}
			blocksChanged += blockDelta;

			// move forward
			pos = pos.add(delta);
		}

		if (blocksChanged > 0) {
			playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, world, playerEntity);
			if (playerEntity instanceof EntityPlayer && !((EntityPlayer) playerEntity).capabilities.isCreativeMode) {
				itemStack.damageItem(1, playerEntity);
			}
		}
	}

	private int placeBridgeBlock(@Nonnull final World world, final BlockPos coord) {
		if (world.isRemote) {
			return 0;
		}
		if (world.isAirBlock(coord)) {
			world.setBlockState(coord, bridgeBlock.getDefaultState());
			return 1;
		}
		IBlockState current = world.getBlockState(coord);
		if (current.getMaterial().blocksMovement()) {
			return 0;
		}
		world.setBlockState(coord, bridgeBlock.getDefaultState());
		return 1;
	}
}
