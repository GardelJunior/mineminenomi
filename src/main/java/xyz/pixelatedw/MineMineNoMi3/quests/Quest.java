package xyz.pixelatedw.MineMineNoMi3.quests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestHint;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestSync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.ITimedQuestObjective;

public abstract class Quest implements IObjectiveParent {
	
	private int sequentialIndexer = 0;
	private int objectiveIndexer = 0;
	
	private List<QuestObjective> objectives;
	private String title;
	private String description;
	private boolean isCompleted;
	private QuestProperties props;
	
	private float percentage;
	
	public Quest(String title, String description) {
		this.title = title;
		this.description = description;
		this.objectives = new ArrayList<QuestObjective>();
		this.percentage = 0;
	}
	
	protected void addObjective(QuestObjective objective) {
		this.objectives.add(objective.withParent(this).withId("obj_"+objectiveIndexer++));
	}
	
	protected void addSequentialObjectives(QuestObjective...objectives) {
		for(QuestObjective qo : objectives) qo.withId("obj_"+objectiveIndexer++);
		final SequentialQuestObjective sqo = new DefaultSequentialQuestObjectives(objectives);
		this.objectives.add(sqo.withParent(this).withId("seq_obj_"+(sequentialIndexer++)));
	}
	
	public void saveToNBT(NBTTagList tag) {
		NBTTagCompound questTag = new NBTTagCompound();
		questTag.setString("quest_id", getQuestID());
		questTag.setString("quest_class", getClassType());
		for(QuestObjective obj : objectives) obj.saveToNBT(questTag);
		tag.appendTag(questTag);
	}
	
	public void loadFromNBT(NBTTagCompound tag) {
		this.isCompleted = tag.getBoolean("completed");
		for(QuestObjective obj : objectives) obj.loadFromNBT(tag);
		this.percentage = calcPercentage();
	}
	
	public abstract String getQuestID();
	
	public String getClassType() {
		return this.getClass().getName();
	}
	
	public abstract void onQuestStart(EntityPlayer player);
	
	public abstract void onQuestFinish(EntityPlayer player);
	
	public void onCompleteObjective(QuestObjective objective) {
		props.unsubscribeObjectives(objective);
		percentage = calcPercentage();
		if(allQuestsAreCompleted()) markAsCompleted();
		if(isCompleted()) {
			props.completeQuest(this);
		}else {
			if(!(objective instanceof SequentialQuestObjective))
				WyNetworkHelper.sendTo(new PacketQuestHint(), (EntityPlayerMP) props.getPlayer());
			WyNetworkHelper.sendTo(new PacketQuestSync(props), (EntityPlayerMP) props.getPlayer());
		}
	}
	
	public void onUpdateObjective(QuestObjective objective) {
		this.percentage = calcPercentage();
		WyNetworkHelper.sendTo(new PacketQuestHint(), (EntityPlayerMP) props.getPlayer());
		WyNetworkHelper.sendTo(new PacketQuestSync(props), (EntityPlayerMP) this.props.getPlayer());
	}
	
	private boolean allQuestsAreCompleted() {
		return getObjectives().stream().allMatch(o -> o.isCompleted());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestObjective> getObjectives() {
		return objectives;
	}
	
	public boolean isCompleted() {
		return this.isCompleted;
	}
	
	public void markAsCompleted() {
		this.isCompleted = true;
	}
	
	public Quest setParent(QuestProperties props) {
		this.props = props;
		return this;
	}
	
	public float getPercentage() {
		return this.percentage;
	}
	
	public boolean checkForStartConditions(EntityPlayer player) {
		return true;
	}
	
	private float calcPercentage() {
		return this.objectives.stream().map(o -> o.getPercentage()).reduce(0f, Float::sum)/(float)this.objectives.size();
	}

	@Override
	public void subscribeObjectives(QuestObjective... objectives) {
		props.subscribeObjectives(objectives);
	}

	@Override
	public void unsubscribeObjectives(QuestObjective... objectives) {
		props.unsubscribeObjectives(objectives);
	}
}
