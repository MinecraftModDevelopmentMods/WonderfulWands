package com.mcmoddev.wonderfulwands.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IllusoryBlock extends Block {

	private static final Map<Block, IllusoryBlock> blockLookup = new HashMap<>();

	public final String name;
	public static BlockPos pos;
	public static IBlockAccess access;

	public IllusoryBlock(final Block sourceBlock) {
		this(
			sourceBlock.getMapColor(sourceBlock.getDefaultState(), access, pos),
			"illusion_" + (sourceBlock.getTranslationKey().replace("tile.", "")),
			sourceBlock, sourceBlock.getTranslationKey().replace("tile.", "")
		);
	}


	public IllusoryBlock(final MapColor color, final String name, final Block sourceBlock) {
		this(color, name, sourceBlock, sourceBlock.getTranslationKey().replace("tile.", ""));
	}


	public IllusoryBlock(final MapColor color, final String name, final Block sourceBlock,
						 final String sourceBlockName) {
		this(color, name, sourceBlock, sourceBlockName, sourceBlockName);
	}

	public IllusoryBlock(final MapColor color, final String name, final Block sourceBlock, final String sourceBlockName,
						 final String sourceBlockUnlocalizedName) {
		super(Material.CARPET, color);
		this.setCreativeTab(CreativeTabs.MISC);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.CLOTH);
		this.name = name;
		this.setTranslationKey(sourceBlockUnlocalizedName);
		blockLookup.put(sourceBlock, this);
	}

	public static IllusoryBlock getIllusionForBlock(final Block block) {
		return blockLookup.get(block);
	}

	public static Map<Block, IllusoryBlock> getLookUpTable() {
		return Collections.unmodifiableMap(blockLookup);
	}


	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(@Nonnull final IBlockState state, @Nonnull final IBlockAccess world,
												 @Nonnull final BlockPos blockPos) {
		return NULL_AABB;
	}

	@Override
	public Item getItemDropped(@Nonnull final IBlockState state, @Nonnull final Random prng, final int fortune) {
		return null;
	}

	@Override
	public boolean canSilkHarvest(@Nonnull final World world, @Nonnull final BlockPos blockPos,
								  @Nonnull final IBlockState state, @Nonnull final EntityPlayer player) {
		return true;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(@Nonnull final IBlockState blockState) {
		return false;
	}

	@Override
	public boolean isFullCube(@Nonnull final IBlockState blockState) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}
}
