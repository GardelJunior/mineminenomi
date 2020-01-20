package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.SwordsmanAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.quest.givers.EntityDojoSensei;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.quests.DefaultSequentialQuestObjectives;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.SyncField;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IEntityInterationQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IHitCounterQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.objectives.InteractWithSensei;

public class QuestSwordsmanProgression03 extends Quest {

	public QuestSwordsmanProgression03(String title, String description) {
		super("The Fruits of Training", "For my last test I need to deal 25 critical hits. I need to focus on my movement and the enemy to successfully deal them.");
		addSequentialObjectives(
			new Do25CriticalHitsObjective(),
			new InteractWithSensei("Go talk with the sensei", "")
		);
	}

	public String getQuestID() {
		return "swordsmanprogression03";
	}
	
	@Override
	public void onQuestStart(EntityPlayer player) {
		
	}
	
	@Override
	public void onQuestFinish(EntityPlayer player) {
		AbilityProperties abilityProps = AbilityProperties.get(player);

		if (MainConfig.enableQuestProgression) {
			abilityProps.addRacialAbility(SwordsmanAbilities.SANBYAKUROKUJU_POUND_HO);
			WyHelper.sendMsgToPlayer(player,"+-----------------+");
			WyHelper.sendMsgToPlayer(player,"§6Your Rewards:");
			WyHelper.sendMsgToPlayer(player," - Sanbyakurokuju Pound Ho");
			WyHelper.sendMsgToPlayer(player," - 100 Doriki");
			ExtendedEntityData eed = ExtendedEntityData.get(player);
			eed.setDoriki(eed.getDoriki() + 100);
		}
	}

	class Do25CriticalHitsObjective extends QuestObjective implements IHitCounterQuestObjective {

		@SyncField
		private int hits = 0;
		
		public Do25CriticalHitsObjective() {
			super("Do 25 critical hits", "");
		}

		@Override
		public void onHitEntity(EntityPlayer player, EntityLivingBase target, DamageSource source) {
			ItemStack heldItem = player.getHeldItem();

			boolean wasCritialHit = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater()
					&& !player.isRiding() && heldItem != null
					&& (heldItem.getItem() instanceof ItemCoreWeapon || heldItem.getItem() instanceof ItemSword);

			
			
			if (wasCritialHit) {
				if(hits < 25) 
					hits++;
				if(hits == 25) 
					this.markAsCompleted();
			}
		}

		@Override
		public String getTitle() {
			return super.getTitle() + " (" + hits +"/25)";
		}
	}
}