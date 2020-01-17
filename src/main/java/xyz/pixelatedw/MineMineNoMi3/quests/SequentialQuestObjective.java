package xyz.pixelatedw.MineMineNoMi3.quests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestHint;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.QuestSwordsmanProgression01;

public abstract class SequentialQuestObjective extends QuestObjective implements IObjectiveParent {
	private QuestObjective currentObjective;
	private List<QuestObjective> objectives;
	
	public SequentialQuestObjective(String id, QuestObjective... quests) {
		super(id, quests[0].title, quests[0].description);
		this.objectives = new ArrayList<QuestObjective>();
		for(QuestObjective obj : quests) {
			addObjective(obj);
		}
		this.currentObjective = this.objectives.stream().filter(o -> !o.isCompleted()).findFirst().orElse(null);
	}
	
	@Override
	public String getTitle() {
		if(currentObjective != null) {
			return currentObjective.getTitle();
		}
		return super.getTitle();
	}

	@Override
	public String getDescription() {
		if(currentObjective != null) {
			return currentObjective.getDescription();
		}
		return super.getDescription();
	}
	
	public void addObjective(QuestObjective objective) {
		this.objectives.add(objective.withParent(this));
	}
	
	public void onCompleteObjective(QuestObjective objective) {
		if(this.objectives.stream().allMatch(q -> q.isCompleted())) {
			this.isCompleted = true;
			this.currentObjective = null;
			this.questParent.onUpdateObjective(objective);
			this.questParent.onCompleteObjective(this);
		}else {
			QuestObjective nextObjective = this.objectives.stream().filter(o -> !o.isCompleted()).findFirst().orElse(null);
			currentObjective = nextObjective;
			this.questParent.onUpdateObjective(objective);
		}
	}
	
	@Override
	public void onUpdateObjective(QuestObjective objective) {
		QuestObjective validObjective = this.objectives.stream().filter(o -> !o.isCompleted()).findFirst().orElse(null);
		currentObjective = validObjective;
		this.questParent.onUpdateObjective(objective);
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		for(QuestObjective objective : objectives) objective.saveToNBT(tag);
		tag.setString(getId()+"_currentObjective", currentObjective!=null? currentObjective.getId() : "null");
		super.saveToNBT(tag);
	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		for(QuestObjective objective : objectives) objective.loadFromNBT(tag);
		this.currentObjective = this.objectives.stream().filter(o -> o.getId().equals(tag.getString(getId()+"_currentObjective"))).findFirst().orElse(null);
		super.loadFromNBT(tag);
	}

	public List<QuestObjective> getObjectives() {
		System.out.println(this.currentObjective);
		return this.currentObjective != null? Collections.singletonList(this.currentObjective) : Collections.EMPTY_LIST;
	}

	@Override
	public float getPercentage() {
		return this.objectives.stream().map(o -> o.getPercentage()).reduce(0f, Float::sum)/(float)this.objectives.size();
	}
}
