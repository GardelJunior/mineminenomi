package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

import static net.minecraft.entity.SharedMonsterAttributes.attackDamage;

import java.util.Map.Entry;
import java.util.Optional;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.quest.givers.EntityDojoSensei;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.quests.DefaultSequentialQuestObjectives;
import xyz.pixelatedw.MineMineNoMi3.quests.EnumQuestlines;
import xyz.pixelatedw.MineMineNoMi3.quests.IObjectiveParent;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.SequentialQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IEntityInterationQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IKillEntityQuestObjective;

public class QuestSwordsmanProgression01 extends Quest {

	public QuestSwordsmanProgression01() {
		super(
			"Road to becoming the Best Swordsman",
			"I am beginning my journey to become the best swordsman in the world. I need to start somewhere, maybe in a dojo."
		);
		addObjective(
			new DefaultSequentialQuestObjectives(
				"objective_00",
				new FindAnDojoSenseiObjective(),
				new FindBetterWeaponObjective()
			)
		);
	}

	@Override
	public String getQuestID() {
		return "swordsmanprogression01";
	}

	@Override
	public void onQuestStart(EntityPlayer player) {
		
	}

	@Override
	public void onQuestFinish(EntityPlayer player) {
		QuestProperties props = QuestProperties.get(player);
		WyHelper.sendMsgToPlayer(player, "Quest Completa");
		props.addQuest(new QuestSwordsmanProgression02());
	}
	
	class FindAnDojoSenseiObjective extends QuestObjective implements IEntityInterationQuestObjective {

		public FindAnDojoSenseiObjective() {
			super("objective_01", "Find an Sensei", "Search for nearby dojo's and talk with an swordsman master.");
		}

		@Override
		public void onInteractWith(EntityPlayer player, EntityLivingBase target) {
			if(target instanceof EntityDojoSensei) {
				this.markAsCompleted();
			}
		}
	}
	
	class FindBetterWeaponObjective extends QuestObjective implements IEntityInterationQuestObjective {

		public FindBetterWeaponObjective() {
			super("objective_02", "Acquire an sword", "Return to the Dojo when you acquire a sword with damage greater than or equal to 7.");
		}

		@Override
		public void onInteractWith(EntityPlayer player, EntityLivingBase target) {
			if (!(target instanceof EntityDojoSensei)) return;

			final Optional<ItemStack> opHeldItem = Optional.ofNullable(player.getHeldItem());
			final QuestProperties questProps = QuestProperties.get(player);
			if(opHeldItem.isPresent()) {
				final ItemStack heldItem = opHeldItem.get();
				final Item heldItemType = heldItem.getItem();
				if(heldItemType instanceof ItemSword || heldItemType instanceof ItemCoreWeapon) {
					Optional<Entry> opEntry = heldItem.getAttributeModifiers().entries().stream()
							.filter(e -> ((Entry)e).getKey().equals(attackDamage.getAttributeUnlocalizedName()))
							.findFirst();
					if(opEntry.isPresent()) {
						AttributeModifier attrmodif = (AttributeModifier) opEntry.get().getValue();
						double damage = attrmodif.getAmount();

						if (damage >= 7) {
							markAsCompleted();
						}
					}
				}
			}
		}
	}
}
