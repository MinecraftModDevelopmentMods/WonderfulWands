package com.mcmoddev.wonderfulwands.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 4/17/2016.
 */
public class BlockFeyRailPowered extends BlockRailPowered {
	public BlockFeyRailPowered() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE,
			BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty(POWERED, Boolean.TRUE));
	}

	@Override
	protected void updateState(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock) {
		// disable by redstone instead of powered by redstone
		boolean wasPowered = state.getValue(POWERED);
		boolean isPowered = !worldIn.isBlockPowered(pos);

		if (isPowered != wasPowered) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, isPowered), 3);
			worldIn.notifyNeighborsOfStateChange(pos.down(), this, isPowered);

			if (state.getValue(SHAPE).isAscending()) {
				worldIn.notifyNeighborsOfStateChange(pos.up(), this, isPowered);
			}
		}
	}

	@Override
	public void onMinecartPass(World world, net.minecraft.entity.item.EntityMinecart cart, BlockPos pos) {
		double maxSpeed = cart.getCurrentCartSpeedCapOnRail();
		double dx = cart.motionX;
		double dz = cart.motionZ;
		double speed = Math.sqrt(dx * dx + dz * dz);
		if (speed > 0) {
			if (world.getBlockState(pos).getValue(POWERED)) {
				// act as an accelerator
				cart.motionX = maxSpeed * dx / speed;
				cart.motionZ = maxSpeed * dz / speed;
			} else {
				// act as a break
				double newSpeed = Math.max(0, speed - (0.0625 * maxSpeed));
				cart.motionX = newSpeed * dx / speed;
				cart.motionZ = newSpeed * dz / speed;
			}
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Collections.emptyList();
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}
}
