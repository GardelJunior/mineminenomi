package xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression;

/*public class QuestSwordsmanProgression02 extends Quest implements ITimedQuestObjective, IKillQuest, IProgressionQuest {

	private final int KILL_QTY = 25;
	private final int PROGRESS_PER_KILL = 24000 / KILL_QTY;
	
	@Override
	public String getQuestID() {
		return "swordsmanprogression02";
	}

	@Override
	public String getQuestName() {
		return "Staying Alive";
	}

	@Override
	public String[] getQuestDescription() {
		return new String[] { 
				"He agreed to train me, the following days will ",
				"be spent training my strength and stamina.", 
				"# Objectives: ", 
				" - Survive this night ", 
				" - Kill 25 mobs", 
				"# Rewards",
				" - Shi Shishi Sonson + 50 doriki" };
	}

	@Override
	public void startQuest(EntityPlayer player) {
		this.extraData = new NBTTagCompound();
		this.extraData.setLong("currentDays", (int) (player.worldObj.getWorldTime()));
		this.extraData.setLong("killedMobs", (int) 0);
		this.extraData.setBoolean("isCompleted", false);

		super.startQuest(player);
	}

	@Override
	public void finishQuest(EntityPlayer player) {
		AbilityProperties abilityProps = AbilityProperties.get(player);
		WyHelper.sendMsgToPlayer(player, I18n.format("quest." + this.getQuestID() + ".completed"));
		
		if(MainConfig.enableQuestProgression) {
			WyHelper.sendMsgToPlayer(player,"+-----------------+");
			WyHelper.sendMsgToPlayer(player,"§6Your Rewards:");
			WyHelper.sendMsgToPlayer(player," - Shi Shishi Sonson");
			WyHelper.sendMsgToPlayer(player," - 50 Doriki");
			abilityProps.addRacialAbility(SwordsmanAbilities.SHI_SHISHI_SONSON);
			ExtendedEntityData eed = ExtendedEntityData.get(player);
			eed.setDoriki(eed.getDoriki() + 50);
		}
		

		
		 * if(extraDays) WyHelper.sendMsgToPlayer(player,
		 * "<Swordsman Master> Almost thought you died there kid, I'm glad that you survived but there's no time to rest, hope you're ready for your next trial !"
		 * ); else WyHelper.sendMsgToPlayer(player,
		 * "<Swordsman Master> Seems like it was too easy for you ?");
		 
		super.finishQuest(player);
	}

	@Override
	public boolean canStart(EntityPlayer player) {
		ExtendedEntityData props = ExtendedEntityData.get(player);
		QuestProperties questProps = QuestProperties.get(player);

		boolean flag1 = !props.isSwordsman() || !questProps.hasQuestCompleted(ListQuests.swordsmanProgression01);

		if (flag1)
			return false;

		if (!player.worldObj.isDaytime()) {
			WyHelper.sendMsgToPlayer(player,
					"<Swordsman Master> There is no point in starting this trial now it's too late, come back in the morning and we'll talk then.");
			return false;
		}

		return true;
	}

	@Override
	public double getMaxProgress() {
		return 48000;
	}

	@Override
	public boolean isFinished(EntityPlayer player) {
		try {
			final long currentDays = this.extraData.getLong("currentDays");
			final long currentKills = this.extraData.getLong("killedMobs");
			final long worldTime = player.worldObj.getWorldTime();
			
			if ((worldTime >= currentDays + 24000) && (currentKills * PROGRESS_PER_KILL >= 24000))
				return true;
		} catch (Exception e) {
			WyHelper.sendMsgToPlayer(player,
					"There was a major problem with this quest, please contact the mod owner asap, it has been completed however so enjoy the rest of the storyline !");
			System.err.println("Checking different objects to check for nulls \n" + "Extra Data, Stored as NBT - "
					+ this.extraData + "\n" + "Player - " + player.getDisplayName() + "\n" + "Logic done on - "
					+ (player.worldObj.isRemote ? "Client" : "Server") + "\n");
			e.printStackTrace();
			return true;
		}

		return false;
	}

	@Override
	public void alterProgress(EntityPlayer player, double progress) {
		super.alterProgress(player, progress);

		if (this.isFinished(player)) {
			this.finishQuest(player);
		}
	}

	@Override
	public boolean isPrimary() {
		return true;
	}

	@Override
	public EnumQuestlines getQuestLine() {
		return EnumQuestlines.SWORDSMAN_PROGRESSION;
	}

	@Override
	public boolean isRepeatable() {
		return false;
	}

	@Override
	public void onTimePassEvent(EntityPlayer player) {
		final long currentDays = this.extraData.getLong("currentDays");
		final long currentKills = this.extraData.getLong("killedMobs");
		final long worldTime = player.worldObj.getWorldTime();
		
		if (!this.isFinished(player)) {
			this.setProgress(player, (Math.min((worldTime - currentDays), 24000) + (currentKills * PROGRESS_PER_KILL)));
		}else if(!this.extraData.getBoolean("isCompleted")) {
			this.extraData.setBoolean("isCompleted",true);
			player.worldObj.playSound(player.posX, player.posY, player.posZ, ID.PROJECT_ID+":quest-log", 1, 1, false);
			if(!player.worldObj.isRemote) {
				WyHelper.sendMsgToPlayer(player, "§aThe quest §6[" + getQuestName() + "]§a has been completed! Time to back to the Dojo.");
			}
		}
	}

	@Override
	public boolean isTarget(EntityPlayer player, EntityLivingBase target) {
		if(!player.worldObj.isDaytime()) {
			final Item heldItem = player.getHeldItem().getItem();
			if(target instanceof EntityMob && (heldItem instanceof ItemCoreWeapon || heldItem instanceof ItemSword)) {
				if (this.extraData.getLong("killedMobs") < KILL_QTY) {
					this.extraData.setLong("killedMobs", this.extraData.getLong("killedMobs") + 1);
				}
			}
		}
		return false;
	}
}*/
