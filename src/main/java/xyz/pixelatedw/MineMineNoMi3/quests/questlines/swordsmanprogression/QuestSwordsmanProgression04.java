package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.SwordsmanAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestHint;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestSync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.lists.ListQuests;
import xyz.pixelatedw.MineMineNoMi3.quests.EnumQuestlines;
import xyz.pixelatedw.MineMineNoMi3.quests.IHitCounterQuest;
import xyz.pixelatedw.MineMineNoMi3.quests.IKillQuest;
import xyz.pixelatedw.MineMineNoMi3.quests.IProgressionQuest;

public class QuestSwordsmanProgression04 extends Quest implements IKillQuest, IProgressionQuest {

	public String getQuestID() {
		return "swordsmanprogression04";
	}

	public String getQuestName() {
		return "The Carnage";
	}

	
	 
	public String[] getQuestDescription() {
		return new String[] { "My sensei told me that for the next skill I would need a lot of stamina.",
				"I must train more with my sword. ", "# Objective:", " - Kill 50 mobs", "# Rewards:", " - Yakkudori", " - 200 doriki" };
	}

	public void startQuest(EntityPlayer player) {
		this.extraData = new NBTTagCompound();
		this.extraData.setBoolean("isCompleted",false);
		this.extraData.setLong("killedMobs",0);
		super.startQuest(player);
	}

	public void finishQuest(EntityPlayer player) {
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

		super.finishQuest(player);
	}

	public boolean canStart(EntityPlayer player) {
		ExtendedEntityData props = ExtendedEntityData.get(player);
		QuestProperties questProps = QuestProperties.get(player);

		boolean flag1 = !props.isSwordsman() || !questProps.hasQuestCompleted(ListQuests.swordsmanProgression03);

		if (flag1)
			return false;

		return true;
	}

	public double getMaxProgress() {
		return 50;
	}

	public boolean isPrimary() {
		return true;
	}

	public EnumQuestlines getQuestLine() {
		return EnumQuestlines.SWORDSMAN_PROGRESSION;
	}

	public boolean isRepeatable() {
		return false;
	}

	@Override
	public boolean isTarget(EntityPlayer player, EntityLivingBase target) {
		final Item heldItem = player.getHeldItem().getItem();
		if(target instanceof EntityMob && (heldItem instanceof ItemCoreWeapon || heldItem instanceof ItemSword)) {
			if (this.extraData.getLong("killedMobs") < 50) {
				this.extraData.setLong("killedMobs", this.extraData.getLong("killedMobs") + 1);
			}
			if(this.extraData.getLong("killedMobs") == 50 && !this.extraData.getBoolean("isCompleted")) {
				this.extraData.setBoolean("isCompleted",true);
				if(!player.worldObj.isRemote) {
					WyNetworkHelper.sendTo(new PacketQuestHint(), (EntityPlayerMP) player);
					WyHelper.sendMsgToPlayer(player, "§aThe quest §6[" + getQuestName() + "]§a has been completed! Time to back to the Dojo.");
				}
			}
			return true;
		}
		return false;
	}

}
