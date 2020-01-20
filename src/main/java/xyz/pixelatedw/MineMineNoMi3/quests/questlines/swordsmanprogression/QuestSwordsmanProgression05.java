package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.SwordsmanAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestHint;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarineCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirateCaptain;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.lists.ListQuests;
import xyz.pixelatedw.MineMineNoMi3.quests.EnumQuestlines;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.SyncField;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IKillEntityQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.objectives.InteractWithSensei;

public class QuestSwordsmanProgression05 extends Quest {

	public QuestSwordsmanProgression05() {
		super("Stronger than Capitains", " My sensei told me to fight at extreme conditions, so.. 5 capitains will be enought?");
		addSequentialObjectives(
			new Kill5Capitains(),
			new InteractWithSensei("Go talk with sensei", "")
		);
	}

	public String getQuestID() {
		return "swordsmanprogression05";
	}

	@Override
	public void onQuestStart(EntityPlayer player) {
		
	}
	
	@Override
	public void onQuestFinish(EntityPlayer player) {
		AbilityProperties abilityProps = AbilityProperties.get(player);

		if (MainConfig.enableQuestProgression) {
			abilityProps.addRacialAbility(SwordsmanAbilities.OTATSUMAKI);
			WyHelper.sendMsgToPlayer(player,"+-----------------+");
			WyHelper.sendMsgToPlayer(player,"§6Your Rewards:");
			WyHelper.sendMsgToPlayer(player," - Otatsumaki");
			WyHelper.sendMsgToPlayer(player," - 400 Doriki");
			ExtendedEntityData eed = ExtendedEntityData.get(player);
			eed.setDoriki(eed.getDoriki() + 400);
		}
	}
	
	class Kill5Capitains extends QuestObjective implements IKillEntityQuestObjective {

		@SyncField
		private int killQtd = 0;
		
		public Kill5Capitains() {
			super("Kill 5 capitains", "");
		}

		@Override
		public void onKillEntity(EntityPlayer player, EntityLivingBase target) {
			final Item heldItem = player.getHeldItem().getItem();
			if((target instanceof EntityPirateCaptain || target instanceof EntityMarineCaptain) && (heldItem instanceof ItemCoreWeapon || heldItem instanceof ItemSword)) {
				if(killQtd < getTargetKillQuantity()) killQtd++;
				if(killQtd == getTargetKillQuantity()) this.markAsCompleted();
			}
		}

		@Override
		public int getTargetKillQuantity() {
			return 5;
		}

		@Override
		public int getCurrentKillQuantity() {
			return killQtd;
		}
	}
}