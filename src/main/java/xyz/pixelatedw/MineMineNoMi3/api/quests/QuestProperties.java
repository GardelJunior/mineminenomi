package xyz.pixelatedw.MineMineNoMi3.api.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

public class QuestProperties implements IExtendedEntityProperties {

	public final static String EXT_QUESTPROP_NAME = ID.PROJECT_ID + "_QuestIEEP";
	private final EntityPlayer thePlayer;

	private boolean hasPrimaryActive = false;

	private List<Quest> quests = new ArrayList<Quest>();
	private List<Quest> completedQuests = new ArrayList<Quest>();
	
	private Quest currentQuest;
	
	public QuestProperties(EntityPlayer entity) {
		this.thePlayer = entity;
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
		}
	}
	
	public List<Quest> filterQuestByObjectives(Predicate<? super Quest> predicate){
		return this.quests.stream().filter(predicate).collect(Collectors.toList());
	}

	public void init(Entity entity, World world) {
	}

	public boolean addQuest(Quest quest) {
		quest.setParent(this);
		return this.quests.add(quest);
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
			quest.onQuestFinish(thePlayer);
			currentQuest = null;
			Quest removedQuest = removeQuest(quest);
			this.completedQuests.add(removedQuest); 
			WyNetworkHelper.sendTo(new PacketQuestSync(this), (EntityPlayerMP) thePlayer);
			return true;
		}
		return false;
	}
	
	public void setCurrentQuest(Quest questTemplate) {
		setCurrentQuest(questTemplate.getQuestID());
	}
	
	public void setCurrentQuest(String questID) {
		this.currentQuest = getQuest(questID);
		this.currentQuest.onQuestStart(thePlayer);
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
}
