package xyz.pixelatedw.MineMineNoMi3.quests.objectives;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public interface ITimedQuestObjective {
	void onTimePassEvent(EntityPlayer player);
}
