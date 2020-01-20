package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.objectives;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.quest.givers.EntityDojoSensei;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IEntityInterationQuestObjective;

public class InteractWithSensei extends QuestObjective implements IEntityInterationQuestObjective {

	public InteractWithSensei(String title, String description) {
		super(title, description);
	}

	@Override
	public void onInteractWith(EntityPlayer player, EntityLivingBase target) {
		if(target instanceof EntityDojoSensei) {
			this.markAsCompleted();
		}
	}
}
