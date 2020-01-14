package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

/*
public class QuestSwordsmanProgression03 extends Quest implements IHitCounterQuestObjective, IProgressionQuest {

	public String getQuestID() {
		return "swordsmanprogression03";
	}

	public String getQuestName() {
		return "The Fruits of Training";
	}

	public String[] getQuestDescription() {
		return new String[] { " For my last test I need to deal 25 critical hits.",
				"I need to focus on my movement and the enemy to", "successfully deal them.", "", "", "", "" };
	}

	public void startQuest(EntityPlayer player) {
		this.extraData = new NBTTagCompound();
		this.extraData.setBoolean("isCompleted",false);
		this.extraData.setInteger("hits",0);
		super.startQuest(player);
	}

	public void finishQuest(EntityPlayer player) {
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

		super.finishQuest(player);
	}

	public boolean canStart(EntityPlayer player) {
		ExtendedEntityData props = ExtendedEntityData.get(player);
		QuestProperties questProps = QuestProperties.get(player);

		boolean flag1 = !props.isSwordsman() || !questProps.hasQuestCompleted(ListQuests.swordsmanProgression02);

		if (flag1)
			return false;

		return true;
	}

	public double getMaxProgress() {
		return 25;
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

	public boolean checkHit(EntityPlayer player, EntityLivingBase target, DamageSource source) {
		ItemStack heldItem = player.getHeldItem();

		boolean flag = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater()
				&& !player.isRiding() && heldItem != null
				&& (heldItem.getItem() instanceof ItemCoreWeapon || heldItem.getItem() instanceof ItemSword);

		if (flag) {
			if(this.extraData.getInteger("hits") < 25) {
				this.extraData.setInteger("hits",this.extraData.getInteger("hits")+1);
			}
			if(this.extraData.getInteger("hits") >= 25 && !this.extraData.getBoolean("isCompleted")) {
				this.extraData.setBoolean("isCompleted",true);
				WyNetworkHelper.sendTo(new PacketQuestHint(), (EntityPlayerMP) player);
				WyHelper.sendMsgToPlayer(player, "§aThe quest §6[" + getQuestName() + "]§a has been completed! Time to back to the Dojo.");
			}
			return true;
		}

		return false;
	}

}
*/