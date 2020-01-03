package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

import java.util.Random;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarineCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirateCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.quest.givers.EntityDojoSensei;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;
import xyz.pixelatedw.MineMineNoMi3.lists.ListQuests;
import xyz.pixelatedw.MineMineNoMi3.quests.EnumQuestlines;
import xyz.pixelatedw.MineMineNoMi3.quests.IHitCounterQuest;
import xyz.pixelatedw.MineMineNoMi3.quests.IKillQuest;
import xyz.pixelatedw.MineMineNoMi3.quests.IProgressionQuest;

public class QuestSwordsmanProgression06 extends Quest implements IKillQuest, IProgressionQuest {

	public String getQuestID() {
		return "swordsmanprogression06";
	}

	public String getQuestName() {
		return "Surpassing the Master";
	}

	public String[] getQuestDescription() {
		return new String[] { " My sensei challenge me to an duel, ",
				"if i win, i will get his sword ", "and my training will be done.", "", "", "", "" };
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
			WyHelper.sendMsgToPlayer(player,"+-----------------+");
			WyHelper.sendMsgToPlayer(player,"§6Your Rewards:");
			WyHelper.sendMsgToPlayer(player," - Sword");
			WyHelper.sendMsgToPlayer(player," - 1500 Doriki");
			
			Item[] randomSword = new Item[] {ListMisc.NidaiKitetsu, ListMisc.SandaiKitetsu, ListMisc.Shusui, ListMisc.Kikoku, ListMisc.WadoIchimonji};
			Item sword = randomSword[new Random().nextInt(randomSword.length)];
			if(!player.inventory.addItemStackToInventory(new ItemStack(sword))) {
				if(!player.worldObj.isRemote) {
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(sword)));
				}
			}
			
			ExtendedEntityData eed = ExtendedEntityData.get(player);
			eed.setDoriki(eed.getDoriki() + 1500);
		}

		super.finishQuest(player);
	}

	public boolean canStart(EntityPlayer player) {
		ExtendedEntityData props = ExtendedEntityData.get(player);
		QuestProperties questProps = QuestProperties.get(player);

		boolean flag1 = !props.isSwordsman() || !questProps.hasQuestCompleted(ListQuests.swordsmanProgression05);

		if (flag1)
			return false;

		return true;
	}

	public double getMaxProgress() {
		return 1;
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
		if(target instanceof EntityDojoSensei && (heldItem instanceof ItemCoreWeapon || heldItem instanceof ItemSword)) {
			if(!player.worldObj.isRemote) {
				WyNetworkHelper.sendTo(new PacketQuestHint(), (EntityPlayerMP) player);
				WyHelper.sendMsgToPlayer(player, "§aThe quest §6[" + getQuestName() + "]§a has been completed! Time to back to the Dojo.");
			}
			return true;
		}
		return false;
	}

}
