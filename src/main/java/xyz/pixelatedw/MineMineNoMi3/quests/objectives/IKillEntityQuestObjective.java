package xyz.pixelatedw.MineMineNoMi3.quests.objectives;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IKillEntityQuestObjective {
	public void onKillEntity(EntityPlayer player, EntityLivingBase target);
	public int getTargetKillQuantity();
	public int getCurrentKillQuantity();
}
