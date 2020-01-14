package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

/*public class QuestSwordsmanProgression05 extends Quest implements IKillQuest, IProgressionQuest {

	public String getQuestID() {
		return "swordsmanprogression05";
	}

	public String getQuestName() {
		return "Stronger than Capitains";
	}

	public String[] getQuestDescription() {
		return new String[] { " My sensei told me to fight at extreme ",
				"conditions, so.. 5 capitains will be enought? ", "", "", "", "", "" };
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
			abilityProps.addRacialAbility(SwordsmanAbilities.OTATSUMAKI);
			WyHelper.sendMsgToPlayer(player,"+-----------------+");
			WyHelper.sendMsgToPlayer(player,"§6Your Rewards:");
			WyHelper.sendMsgToPlayer(player," - Otatsumaki");
			WyHelper.sendMsgToPlayer(player," - 400 Doriki");
			ExtendedEntityData eed = ExtendedEntityData.get(player);
			eed.setDoriki(eed.getDoriki() + 400);
		}

		super.finishQuest(player);
	}

	public boolean canStart(EntityPlayer player) {
		ExtendedEntityData props = ExtendedEntityData.get(player);
		QuestProperties questProps = QuestProperties.get(player);

		boolean flag1 = !props.isSwordsman() || !questProps.hasQuestCompleted(ListQuests.swordsmanProgression04);

		if (flag1)
			return false;

		return true;
	}

	public double getMaxProgress() {
		return 5;
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
		if((target instanceof EntityPirateCaptain || target instanceof EntityMarineCaptain) && (heldItem instanceof ItemCoreWeapon || heldItem instanceof ItemSword)) {
			if (this.extraData.getLong("killedMobs") < 5) {
				this.extraData.setLong("killedMobs", this.extraData.getLong("killedMobs") + 1);
			}
			if(this.extraData.getLong("killedMobs") == 5 && !this.extraData.getBoolean("isCompleted")) {
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
*/