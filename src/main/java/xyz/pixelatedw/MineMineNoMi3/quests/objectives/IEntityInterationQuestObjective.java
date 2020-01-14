package xyz.pixelatedw.MineMineNoMi3.quests.objectives;

import java.lang.reflect.Type;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IEntityInterationQuestObjective {
	public void onInteractWith(EntityPlayer player, EntityLivingBase target);
}
