package com.mcmoddev.wonderfulwands.common.entities;

import com.mcmoddev.wonderfulwands.init.WonderfulBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Random;

public class EntityLightWisp extends net.minecraft.entity.EntityLivingBase {

	static final short LIFESPAN = 30 * 20;
	short lifeCounter = LIFESPAN;
	static final long UPDATE_INTERVAL = 8;
	private final double dt = 4.0 / UPDATE_INTERVAL;
	private BlockPos lastPos = new BlockPos(0, 1, 0);

	private long nextUpdateTime = 0;

	public EntityLightWisp(World w) {
		super(w);
		this.lifeCounter = LIFESPAN;
		this.isImmuneToFire = true;
		nextUpdateTime = w.rand.nextInt((int) UPDATE_INTERVAL);
	}

	public EntityLightWisp(World w, BlockPos startingPosition) {
		this(w);
		this.posX = startingPosition.getX() + 0.5;
		this.posY = startingPosition.getY();
		this.posZ = startingPosition.getZ() + 0.5;
	}

	@Override
	protected void damageEntity(DamageSource src, float amount) {
		// immune to wall suffocation
		if (DamageSource.IN_WALL.equals(src.damageType)) return;
		super.damageEntity(src, amount);
	}

	@Override
	public void readFromNBT(@Nonnull NBTTagCompound root) {
		super.readFromNBT(root);
		if (root.hasKey("t")) {
			this.lifeCounter = root.getShort("t");
		} else {
			this.lifeCounter = LIFESPAN;
		}
	}

	@Nonnull
	@Override
	//TODO When the Wand of Darkness Bane is used this method crashes the game fully. Wolf says it's trying to add
	//entity tracking but can't or something along those words (Not too sure about it myself so I'll disable the recipe)
	public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound root) {
		super.writeToNBT(root);
		root.setShort("t", this.lifeCounter);
		return root;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (world.isRemote) {
			return;
		}
		--lifeCounter;
		long time = this.world.getTotalWorldTime();
		if (time > nextUpdateTime) {
			nextUpdateTime = time + UPDATE_INTERVAL;
			BlockPos blockCoord = this.getPosition();
			int light = getLight(blockCoord);
			if (canPlace(blockCoord) && light < 8) {
				turnIntoMageLight();
			} else {
				wanderFrom(blockCoord, light);
			}
			lastPos = blockCoord;
		}
		if (lifeCounter <= 0) {
			world.removeEntity(this);
		}
	}

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ItemStack> getArmorInventoryList() {
		return Collections.EMPTY_LIST;
	}

	@Override
	@SuppressWarnings("NullableProblems")
	public ItemStack getItemStackFromSlot(@Nonnull final EntityEquipmentSlot slot) {
		return null;
	}

	@Override
	public void setItemStackToSlot(@Nonnull final EntityEquipmentSlot slot, @Nonnull final ItemStack itemStack) {
		// do nothing
	}

	@Nonnull
	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.LEFT;
	}

	private int getLight(final BlockPos coord) {
		if (coord.getY() < 0 || coord.getY() > 255) {
			return 16;
		}
		IBlockState bs = world.getBlockState(coord);
		if (bs.isFullBlock()) {
			return 16;
		}
		if (!(world.getChunk(coord).isLoaded())) {
			return 16;
		}
		return world.getChunk(coord).getLightFor(EnumSkyBlock.BLOCK, coord);
	}

	private boolean canPlace(final BlockPos coord) {
		return world.isAirBlock(coord);
	}


	public void turnIntoMageLight() {
		world.setBlockState(getPosition(), WonderfulBlocks.MAGE_LIGHT.getDefaultState());
		this.isDead = true;
		world.removeEntity(this);
	}


	public void wanderFrom(final BlockPos blockCoord, int light) {
		// first, check if neighbor is darker than here
		BlockPos target = null;
		BlockPos[] coords = new BlockPos[4];
		coords[0] = blockCoord.north();
		coords[1] = blockCoord.east();
		coords[2] = blockCoord.south();
		coords[3] = blockCoord.west();
		shuffle(coords, getRNG());
		for (int i = 0; i < coords.length; i++) {
			coords[i] = moveToGroundLevel(coords[i]);
			int l = getLight(coords[i]);
			if (l < light) {
				light = l;
				target = coords[i];
			}
		}
		// if target is still null, then we are stuck in a local minimum, look further afield
		if (target == null) {
			coords = new BlockPos[8];
			coords[0] = blockCoord.add(-12, 0, 0);
			coords[1] = blockCoord.add(-8, 0, -8);
			coords[2] = blockCoord.add(0, 0, -12);
			coords[3] = blockCoord.add(8, 0, -8);
			coords[4] = blockCoord.add(12, 0, 0);
			coords[5] = blockCoord.add(8, 0, 8);
			coords[6] = blockCoord.add(0, 0, 12);
			coords[7] = blockCoord.add(-8, 0, 8);
			shuffle(coords, getRNG());
			for (int i = 0; i < coords.length; i++) {
				coords[i] = moveToGroundLevel(coords[i]);
				int l = getLight(coords[i]);
				if (l < light) {
					light = l;
					target = coords[i];
				}
			}
		}

		// if target is still null, then we are stuck in a brightly lit area, make long jump in random direction
		if (target == null) {
			final float r = 24;
			float theta = this.getEntityWorld().rand.nextFloat() * 6.2832f;
			float dx = r * MathHelper.sin(theta);
			float dz = r * MathHelper.cos(theta);
			target = moveToGroundLevel(blockCoord.add(dx, 0, dz));
		}

		this.setPosition(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
	}

	private BlockPos moveToGroundLevel(BlockPos coord) {
		if (world.isAirBlock(coord)) {
			BlockPos down = coord.down();
			while (world.isAirBlock(down) && down.getY() > 0) {
				coord = down;
				down = coord.down();
			}
		} else {
			while (!world.isAirBlock(coord) && coord.getY() < 255) {
				coord = coord.up();
			}
		}
		return coord;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}


	private static void shuffle(Object[] in, Random prng) {
		for (int i = in.length - 1; i > 1; i--) {
			Object o = in[i];
			int r = prng.nextInt(i);
			in[i] = in[r];
			in[r] = o;
		}
	}
}
