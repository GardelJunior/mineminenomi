package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.SwordsmanAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestHint;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.lists.ListQuests;
import xyz.pixelatedw.MineMineNoMi3.quests.EnumQuestlines;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.SyncField;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IKillEntityQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.objectives.InteractWithSensei;

public class QuestSwordsmanProgression04 extends Quest {

	public QuestSwordsmanProgression04(String title, String description) {
		super("The Carnage", "My sensei told me that for the next skill I would need a lot of stamina. I must train more with my sword. ");
		addSequentialObjectives(
			new Kill50MobsObjective(),
			new InteractWithSensei("Go talk with the sensei", "")
		);
	}

	public String getQuestID() {
		return "swordsmanprogression04";
	}

	@Override
	public void onQuestStart(EntityPlayer player) {
		
	}

	@Override
	public void onQuestFinish(EntityPlayer player) {
		AbilityProperties abilityProps = AbilityProperties.get(player);

		if (MainConfig.enableQuestProgression) {
			abilityProps.addRacialAbility(SwordsmanAbilities.YAKKODORI);
			WyHelper.sendMsgToPlayer(player,"+-----------------+");
			WyHelper.sendMsgToPlayer(player,"§6Your Rewards:");
			WyHelper.sendMsgToPlayer(player," - Yakkodori");
			WyHelper.sendMsgToPlayer(player," - 200 Doriki");
			ExtendedEntityData eed = ExtendedEntityData.get(player);
			eed.setDoriki(eed.getDoriki() + 200);
		}
	}
	
	
	class Kill50MobsObjective extends QuestObjective implements IKillEntityQuestObjective {

		@SyncField
		private int killQtd = 0;
		
		public Kill50MobsObjective() {
			super("Kill 50 mobs", "");
		}

		@Override
		public void onKillEntity(EntityPlayer player, EntityLivingBase target) {
			final Item heldItem = player.getHeldItem().getItem();
			if(target instanceof EntityMob && (heldItem instanceof ItemCoreWeapon || heldItem instanceof ItemSword)) {
				if (killQtd < 50)
					killQtd++;
				if(killQtd == 50) 
					this.markAsCompleted();
			}
		}
		
		@Override
		public String getTitle() {
			return super.getTitle() + "(" + getCurrentKillQuantity() + "/"+ getTargetKillQuantity() +")";
		}

		@Override
		public int getTargetKillQuantity() {
			return 50;
		}

		@Override
		public int getCurrentKillQuantity() {
			return killQtd;
		}
	}
}