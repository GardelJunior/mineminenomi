package xyz.pixelatedw.MineMineNoMi3.api.quests;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.debug.WyDebug;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestSync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;

public class QuestManager {

	private static QuestManager instance;

	public static QuestManager getInstance() {
		if (instance == null)
			instance = new QuestManager();
		return instance;
	}

	public Quest startQuest(EntityPlayer player, Quest quest) {
		Quest nQuest = null;
		QuestProperties questProps = QuestProperties.get(player);

		if (questProps.hasQuestCompleted(quest)) {
			WyHelper.sendMsgToPlayer(player, quest.getTitle() + " was completed and cannot be started again!");
			return null;
		}

		if (questProps.hasQuest(quest)) {
			WyHelper.sendMsgToPlayer(player, quest.getTitle() + " is already in progress!");
			return null;
		}

		try {
			nQuest = quest.getClass().newInstance();
			questProps.addQuest(nQuest);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return nQuest;
	}

	public Quest getQuestByNameFromList(HashMap<String, Quest> list, String questId) {
		for (Quest q : list.values()) {
			if (q.getQuestID().toLowerCase().equals(questId.toLowerCase()))
				return q;
		}
		return null;
	}
}
