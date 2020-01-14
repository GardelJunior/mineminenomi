package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

/*public class QuestSwordsmanProgression04 extends Quest implements IKillQuest, IProgressionQuest {

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
*/