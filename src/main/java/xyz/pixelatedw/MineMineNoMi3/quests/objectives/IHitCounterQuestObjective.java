package xyz.pixelatedw.MineMineNoMi3.quests.objectives;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public interface IHitCounterQuestObjective {
	boolean onHitEntity(EntityPlayer player, EntityLivingBase target, DamageSource source);
}
