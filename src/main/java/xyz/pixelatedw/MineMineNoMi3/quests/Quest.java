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
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.ITimedQuestObjective;

public abstract class Quest implements IObjectiveParent {
	
	private List<QuestObjective> objectives;
	private String title;
	private String description;
	private boolean isCompleted;
	private QuestProperties props;
	
	private Map<Class, List<QuestObjective>> objectiveMapping;
	
	public Quest(String title, String description) {
		this.title = title;
		this.description = description;
		this.objectives = new ArrayList<QuestObjective>();
		this.objectiveMapping = new ConcurrentHashMap<Class, List<QuestObjective>>();
	}
	
	protected void addObjective(QuestObjective objective) {
		this.objectives.add(objective.withParent(this));
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
	}
	
	public abstract String getQuestID();
	
	public String getClassType() {
		return this.getClass().getName();
	}
	
	public abstract void onQuestStart(EntityPlayer player);
	
	public abstract void onQuestFinish(EntityPlayer player);
	
	public void onCompleteObjective(QuestObjective objective) {
		if(this.getObjectives().stream().allMatch(q -> q.isCompleted())) {
			this.markAsCompleted();
		}
		if(isCompleted) {
			props.completeQuest(this);
		}
		if(!(objective instanceof SequentialQuestObjective))
			WyNetworkHelper.sendTo(new PacketQuestHint(), (EntityPlayerMP) props.getPlayer());
	}
	
	public void onUpdateObjective(QuestObjective objective) {
		for(Class<?> type : objective.getClass().getInterfaces()) {
			this.objectiveMapping.remove(type);
		}
		WyNetworkHelper.sendTo(new PacketQuestHint(), (EntityPlayerMP) props.getPlayer());
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
		for(List list : this.objectiveMapping.values()) list.clear();
		this.objectiveMapping.clear();
	}
	
	public Quest setParent(QuestProperties props) {
		this.props = props;
		return this;
	}
	
	public Stream<QuestObjective> getObjectivesByType(Class objectiveType, Predicate<QuestObjective> predicate){
		List<QuestObjective> objStream = this.objectiveMapping.get(objectiveType);
		if(objStream == null) {
			objStream = Collections.synchronizedList(this.objectives.stream()
				.<QuestObjective>flatMap(o -> o instanceof SequentialQuestObjective? ((SequentialQuestObjective)o).getObjectives().stream() : Stream.<QuestObjective>of(o))
				.filter(predicate)
				.filter(o -> (!o.isCompleted() || o.completionCanChange()) && !o.isLocked())
				.collect(Collectors.toList()));
			this.objectiveMapping.put(objectiveType, objStream);
		}
		return objStream.stream();
	}
}
