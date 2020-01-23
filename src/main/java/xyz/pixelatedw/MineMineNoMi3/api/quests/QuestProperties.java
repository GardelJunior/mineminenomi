package xyz.pixelatedw.MineMineNoMi3.api.quests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestSync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.SequentialQuestObjective;

public class QuestProperties extends Observable implements IExtendedEntityProperties {

	public final static String EXT_QUESTPROP_NAME = ID.PROJECT_ID + "_QuestIEEP";
	private final EntityPlayer thePlayer;

	private boolean hasPrimaryActive = false;

	private List<Quest> quests = new ArrayList<Quest>();
	private List<Quest> completedQuests = new ArrayList<Quest>();
	
	private Map<Class, List<QuestObjective>> eventSubscriptions;
	
	private Quest currentQuest;
	
	public QuestProperties(EntityPlayer entity) {
		this.thePlayer = entity;
		this.eventSubscriptions = new WeakHashMap<Class, List<QuestObjective>>();
	}
	
	public EntityPlayer getPlayer() {
		return this.thePlayer;
	}

	public static final void register(EntityPlayer entity) {
		entity.registerExtendedProperties(QuestProperties.EXT_QUESTPROP_NAME, new QuestProperties(entity));
	}

	public static final QuestProperties get(EntityPlayer entity) {
		return (QuestProperties) entity.getExtendedProperties(EXT_QUESTPROP_NAME);
	}
	
	public List<Quest> getCompletedQuests(){
		return this.completedQuests;
	}
	
	public List<Quest> getQuests(){
		return this.quests;
	}
	
	public void subscribeObjectives(List<QuestObjective> objectives) {
		subscribeObjectives(objectives.toArray(new QuestObjective[objectives.size()]));
	}
	
	public void unsubscribeObjectives(List<QuestObjective> objectives) {
		unsubscribeObjectives(objectives.toArray(new QuestObjective[objectives.size()]));
	}
	
	public void subscribeObjectives(QuestObjective... objectives) {
		flattenObjectives(objectives).forEach(objective -> {
			System.out.println("Subscribing for " + objective.getTitle());
			System.out.println("A classe " + objective.getClass().getTypeName() +" contem " + objective.getClass().getInterfaces().length +" interfaces!");
			for(Class interfac : objective.getClass().getInterfaces()) {
				List<QuestObjective> typeQuestObjective = this.eventSubscriptions.get(interfac);
				System.out.println("Subscribing the interface " + interfac.getTypeName());
				if(typeQuestObjective == null) {
					typeQuestObjective = new ArrayList<QuestObjective>();
					typeQuestObjective.add(objective);
					this.eventSubscriptions.put(interfac, typeQuestObjective);
					System.out.println("Registering");
				}else {
					typeQuestObjective.add(objective);
					System.out.println("Just adding");
				}
			}
		});
	}
	
	public void unsubscribeObjectives(QuestObjective... objectives) {
		flattenObjectives(objectives).forEach(objective -> {
			for(Class interfac : objective.getClass().getInterfaces()) {
				List<QuestObjective> typeQuestObjective = this.eventSubscriptions.get(interfac);
				if(typeQuestObjective != null) {
					typeQuestObjective.removeIf(obj -> obj.equals(obj));
				}
			}
		});
	}

	public <T> List<T> getObjectivesByType(Class type) {
		return Optional.ofNullable(eventSubscriptions.get(type)).orElse(Collections.EMPTY_LIST);
	}
	
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagList questsTag = new NBTTagList();
		NBTTagList completedQuestsTag = new NBTTagList();
		
		quests.forEach(quest -> quest.saveToNBT(questsTag));
		completedQuests.forEach(quest -> quest.saveToNBT(completedQuestsTag));
		
		NBTTagCompound props = new NBTTagCompound();
		
		props.setTag("quests", questsTag);
		props.setTag("completedQuests", completedQuestsTag);
		props.setString("currentQuestID", currentQuest != null? currentQuest.getQuestID() : "null");
		
		compound.setTag(EXT_QUESTPROP_NAME, props);
	}

	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_QUESTPROP_NAME);
		NBTTagList questList = props.getTagList("quests", 10);
		
		this.completedQuests.clear();
		this.quests.clear();
		this.eventSubscriptions.clear();
		this.currentQuest = null;
		
		for(int i = 0 ; i < questList.tagCount() ; i++) {
			NBTTagCompound questTag = questList.getCompoundTagAt(i);
			try {
				Quest quest = (Quest) Class.forName(questTag.getString("quest_class")).newInstance();
				quest.loadFromNBT(questTag);
				quest.setParent(this);
				this.quests.add(quest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		NBTTagList completedQuestList = props.getTagList("completedQuests", 10);
		for(int i = 0 ; i < completedQuestList.tagCount() ; i++) {
			NBTTagCompound questTag = completedQuestList.getCompoundTagAt(i);
			try {
				Quest quest = (Quest) Class.forName(questTag.getString("quest_class")).newInstance();
				quest.loadFromNBT(questTag);
				quest.setParent(this);
				quest.markAsCompleted();
				this.completedQuests.add(quest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String questID = props.getString("currentQuestID");
		if(!questID.equals("null")) {
			this.currentQuest = this.quests.stream().filter(quest -> quest.getQuestID().equals(questID)).findFirst().orElse(null);
			if(!thePlayer.worldObj.isRemote) 
				subscribeObjectives(this.currentQuest.getObjectives());
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public List<Quest> filterQuestByObjectives(Predicate<? super Quest> predicate){
		return this.quests.stream().filter(predicate).collect(Collectors.toList());
	}

	public void init(Entity entity, World world) {
	}

	public boolean addQuest(Quest quest) {
		quest.setParent(this);
		this.quests.add(quest);
		WyNetworkHelper.sendTo(new PacketQuestSync(this), (EntityPlayerMP) thePlayer);
		return true;
	}

	public Quest removeQuest(Quest questTemplate) {
		Quest theQuest = getQuest(questTemplate);
		if(theQuest != null) {
			this.quests.remove(theQuest);
			return theQuest;
		}
		return null;
	}

	public boolean hasQuest(Quest questTemplate) {
		return hasQuest(questTemplate.getQuestID());
	}
	
	public boolean hasQuest(String questID) {
		return this.quests.stream().anyMatch(q -> q.getQuestID().equals(questID));
	}
	
	public boolean completeQuest(Quest quest) {
		if(hasQuest(quest)) {
			currentQuest = null;
			unsubscribeObjectives(quest.getObjectives());
			quest.onQuestFinish(thePlayer);
			Quest removedQuest = removeQuest(quest);
			completedQuests.add(removedQuest); 
			WyNetworkHelper.sendTo(new PacketQuestSync(this), (EntityPlayerMP) thePlayer);
			return true;
		}
		return false;
	}
	
	public void setCurrentQuest(Quest questTemplate) {
		setCurrentQuest(questTemplate.getQuestID());
	}
	
	public void setCurrentQuest(String questID) {
		this.eventSubscriptions.clear();
		this.currentQuest = getQuest(questID);
		if(this.currentQuest != null) {
			this.currentQuest.onQuestStart(thePlayer);
			this.subscribeObjectives(this.currentQuest.getObjectives());
			WyNetworkHelper.sendTo(new PacketQuestSync(this), (EntityPlayerMP) thePlayer);
		}
	}

	public Quest getCurrentQuest() {
		return currentQuest;
	}

	public boolean hasQuestCompleted(Quest questTemplate) {
		return this.completedQuests.stream().anyMatch(q -> q.getQuestID().equals(questTemplate.getQuestID()));
	}

	public boolean hasQuestCompleted(String questID) {
		return this.completedQuests.stream().anyMatch(q -> q.getQuestID().equals(questID.toLowerCase()));
	}

	public Quest getQuest(Quest questTemplate) {
		return getQuest(questTemplate.getQuestID());
	}
	
	public Quest getQuest(String questID) {
		return this.quests.stream().filter(q -> q.getQuestID().equals(questID)).findFirst().orElse(null);
	}
	
	public Quest getCompletedQuest(Quest questTemplate) {
		return getCompletedQuest(questTemplate.getQuestID());
	}
	
	public Quest getCompletedQuest(String questID) {
		return this.completedQuests.stream().filter(q -> q.getQuestID().equals(questID)).findFirst().orElse(null);
	}

	public boolean hasPrimary() {
		return this.hasPrimaryActive;
	}

	public int questsInProgress() {
		return this.quests.size();
	}

	public void clearQuestTracker() {
		this.quests.clear();
	}

	public void clearCompletedQuests() {
		this.completedQuests.clear();
	}
	
	private Stream<QuestObjective> flattenObjectives(QuestObjective... objectives){
		return Stream.<QuestObjective>of(objectives).<QuestObjective>flatMap(o -> {
			if(o instanceof SequentialQuestObjective)
				return ((SequentialQuestObjective)o).getObjectives().stream();
			return Stream.of(o);
		});
	}
}
