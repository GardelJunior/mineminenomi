package xyz.pixelatedw.MineMineNoMi3.blocks.tileentities;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;

public class TileEntityCustomSpawner extends TileEntity {

	private String entityToSpawn = "Pig";
	private int spawnLimit = 5;
	private List<EntityLivingBase> spawnedEntities = new LinkedList<EntityLivingBase>();

	public TileEntityCustomSpawner setSpawnerMob(String toSpawn) {
		this.entityToSpawn = toSpawn;
		return this;
	}

	public TileEntityCustomSpawner setSpawnerLimit(int limit) {
		this.spawnLimit = limit;
		return this;
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			boolean maxSpawnedFlag = false;
			List<EntityLivingBase> nearPlayers = WyHelper.getEntitiesNear(this, 30, EntityPlayer.class);
			if (!nearPlayers.isEmpty()) {
				EntityLivingBase e = nearPlayers.get(0);

				if (e != null && e instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) e;

					if ((this.spawnedEntities.size() < this.spawnLimit)) {
						EntityLivingBase newSpawn = (EntityLivingBase) EntityList.createEntityByName(this.entityToSpawn,this.worldObj);
						if (newSpawn != null) {
							newSpawn.setLocationAndAngles(this.xCoord, this.yCoord, this.zCoord, 0, 0);
							((EntityLiving) newSpawn).onSpawnWithEgg((IEntityLivingData) null);
							this.worldObj.spawnEntityInWorld(newSpawn);
							this.spawnedEntities.add(newSpawn);
						}
					}
				}
			} else {
				if (this.spawnedEntities.size() == this.spawnLimit) {
					maxSpawnedFlag = true;
				}
			}

			if (maxSpawnedFlag) {
				for (EntityLivingBase elb : this.spawnedEntities) {
					elb.setDead();
				}
				this.spawnedEntities.clear();
				maxSpawnedFlag = false;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
		this.entityToSpawn = nbtTag.getString("entityToSpawn");
		this.spawnLimit = nbtTag.getInteger("spawnLimit");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		nbtTag.setInteger("spawnLimit", this.spawnLimit);
		nbtTag.setString("entityToSpawn", this.entityToSpawn);
	}

}
