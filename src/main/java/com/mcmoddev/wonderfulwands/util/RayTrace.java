package com.mcmoddev.wonderfulwands.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public abstract class RayTrace {
	public static RayTraceResult rayTraceBlocksAndEntities(World w, double maxRange, EntityLivingBase source) {
		double rangeSqr = maxRange * maxRange;
		Vec3d rayOrigin = new Vec3d(source.posX, source.posY + source.getEyeHeight(), source.posZ);
		Vec3d rayDirection = source.getLookVec();
		BlockPos srcPos = source.getPosition();
		AxisAlignedBB aoi = new AxisAlignedBB(srcPos.getX() - maxRange, srcPos.getY() - maxRange, srcPos.getZ() - maxRange,
			srcPos.getX() + maxRange, srcPos.getY() + maxRange, srcPos.getZ() + maxRange);
		List<Entity> entities = w.getEntitiesWithinAABBExcludingEntity(source, aoi);
		double closestDistSqr = Double.MAX_VALUE;
		Entity closestEntity = null;
		for (Entity e : entities) {
			double distSqr = e.getDistanceSq(source.posX, source.posY, source.posZ);
			if (distSqr < rangeSqr) {
				// e is within range
				AxisAlignedBB box = e.getEntityBoundingBox();
				if (rayIntersectsBoundingBox(rayOrigin, rayDirection, box)) {
					// e is in cross-hairs
					if (distSqr < closestDistSqr) {
						// e is closest entity in line of fire
						closestDistSqr = distSqr;
						closestEntity = e;
					}
				}
			}
		}
		if (closestEntity == null) {
			return w.rayTraceBlocks(rayOrigin, rayOrigin.add(mul(rayDirection, maxRange)), true, false, false);
		} else {
			Vec3d pos = new Vec3d(closestEntity.posX, closestEntity.posY + closestEntity.getEyeHeight(), closestEntity.posZ);
			RayTraceResult entityCollision = new RayTraceResult(closestEntity, pos);
			return entityCollision;
		}
	}

	public static boolean rayIntersectsBoundingBox(Vec3d rayOrigin, Vec3d rayDirection, AxisAlignedBB box) {
		if (box == null) return false;
		// algorithm from http://gamedev.stackexchange.com/questions/18436/most-efficient-aabb-vs-ray-collision-algorithms
		Vec3d inverse = new Vec3d(1.0 / rayDirection.x, 1.0 / rayDirection.y, 1.0 / rayDirection.z);
		double t1 = (box.minX - rayOrigin.x) * inverse.x;
		double t2 = (box.maxX - rayOrigin.x) * inverse.x;
		double t3 = (box.minY - rayOrigin.y) * inverse.y;
		double t4 = (box.maxY - rayOrigin.y) * inverse.y;
		double t5 = (box.minZ - rayOrigin.z) * inverse.z;
		double t6 = (box.maxZ - rayOrigin.z) * inverse.z;

		double tmin = max(max(min(t1, t2), min(t3, t4)), min(t5, t6));
		double tmax = min(min(max(t1, t2), max(t3, t4)), max(t5, t6));

		// if tmax < 0, ray (line) is intersecting AABB, but whole AABB is behing us
		if (tmax < 0) {
			return false;
		}

		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax) {
			return false;
		}

		return true;
	}

	private static Vec3d mul(Vec3d a, double b) {
		return new Vec3d(a.x * b, a.y * b, a.z * b);
	}

	private static double max(double a, double b) {
		return Math.max(a, b);
	}

	private static double min(double a, double b) {
		return Math.min(a, b);
	}
}
