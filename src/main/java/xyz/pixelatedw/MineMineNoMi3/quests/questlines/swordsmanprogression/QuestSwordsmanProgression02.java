package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.SwordsmanAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.lists.ListQuests;
import xyz.pixelatedw.MineMineNoMi3.quests.DefaultSequentialQuestObjectives;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.SequentialQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.SyncField;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IKillEntityQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.ITimedQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.objectives.InteractWithSensei;

public class QuestSwordsmanProgression02 extends Quest {

	public QuestSwordsmanProgression02() {
		super("Staying Alive", "");
		addSequentialObjectives(
			new AwaitForTheNightObjective(),
			new Kill25MonstersObjective(),
			new AwaitForTheSunriseObjective(),
			new InteractWithSensei("Go talk with sensei", "")
		);
	}
	
	@Override
	public String getQuestID() {
		return "swordsmanprogression02";
	}

	@Override
	public void onQuestStart(EntityPlayer player) {
		
	}

	@Override
	public void onQuestFinish(EntityPlayer player) {
		AbilityProperties abilityProps = AbilityProperties.get(player);
		ExtendedEntityData eed = ExtendedEntityData.get(player);
		
		if(MainConfig.enableQuestProgression) {
			WyHelper.sendMsgToPlayer(player,"+-----------------+");
			WyHelper.sendMsgToPlayer(player,EnumChatFormatting.GREEN + "Your Rewards:");
			WyHelper.sendMsgToPlayer(player," - Shi Shishi Sonson");
			WyHelper.sendMsgToPlayer(player," - 50 Doriki");
			abilityProps.addRacialAbility(SwordsmanAbilities.SHI_SHISHI_SONSON);
			eed.setDoriki(eed.getDoriki() + 50);
		}
	}
	
	
	class AwaitForTheNightObjective extends QuestObjective implements ITimedQuestObjective {
		public AwaitForTheNightObjective() {
			super("Await for the night", "To show to your master that you are a stronger man, survive this entire night");
		}
		
		@Override
		public void onTimePassEvent(EntityPlayer player) {
			int dayTime = (int) (player.worldObj.getWorldTime() % 24000);
			if((dayTime >= 12786 && dayTime <= 13000) || !player.worldObj.isDaytime()) {
				this.markAsCompleted();
			}
		}
	}
	
	class Kill25MonstersObjective extends QuestObjective implements IKillEntityQuestObjective {
		
		@SyncField
		private int killQty = 0;
		
		public Kill25MonstersObjective() {
			super("Kill 25 Mobs", "Kill 25 mobs before the night ends.");
		}
		
		@Override
		public String getTitle() {
			return this.title + " (" + getCurrentKillQuantity() + "/" + getTargetKillQuantity() + ")";
			
		}
		
		@Override
		public void onKillEntity(EntityPlayer player, EntityLivingBase target) {
			if(!player.worldObj.isDaytime()) {
				final Item heldItem = player.getHeldItem().getItem();
				if(target instanceof EntityMob && (heldItem instanceof ItemCoreWeapon || heldItem instanceof ItemSword)) {
					if(!isCompleted) {
						killQty++;
						if(killQty == getTargetKillQuantity()) 
							markAsCompleted();
					}
				}
			}
		}
		
		@Override
		public int getTargetKillQuantity() {
			return 25;
		}
		
		@Override
		public int getCurrentKillQuantity() {
			return killQty;
		}
		
		@Override
		public float getPercentage() {
			return getCurrentKillQuantity()/(float)getTargetKillQuantity();
		}
	}
	
	class AwaitForTheSunriseObjective extends QuestObjective implements ITimedQuestObjective {
		public AwaitForTheSunriseObjective() {
			super("Await for the sunrise", "To complete the challenge fight until the sun rises");
		}

		@Override
		public void onTimePassEvent(EntityPlayer player) {
			int dayTime = (int) (player.worldObj.getWorldTime() % 24000);
			if((dayTime >= 23031 && dayTime <= 23200) || player.worldObj.isDaytime()) {
				this.markAsCompleted();
			}
		}
	}
}
