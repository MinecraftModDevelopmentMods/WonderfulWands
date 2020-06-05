package com.mcmoddev.wonderfulwands.common.blocks;

import net.minecraft.block.BlockRail;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 4/17/2016.
 */
public class BlockFeyRail extends BlockRail {

	public BlockFeyRail() {
		super();
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public List<ItemStack> getDrops(@Nonnull final IBlockAccess world, @Nonnull final BlockPos pos,
									@Nonnull final IBlockState state, final int fortune) {
		return Collections.emptyList();
	}

	@Override
	public boolean canSilkHarvest(@Nonnull final World world, @Nonnull final BlockPos pos,
								  @Nonnull final IBlockState state, @Nonnull final EntityPlayer player) {
		return false;
	}
}
