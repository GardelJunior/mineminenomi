package xyz.pixelatedw.MineMineNoMi3.lists;

import java.util.HashMap;

import xyz.pixelatedw.MineMineNoMi3.api.WyRegistry;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.QuestSwordsmanProgression01;

public class ListQuests {

	public static HashMap<String, Quest> allQuests = new HashMap<String, Quest>();

	// Quest Line : Swordsman Progression
	public static Quest swordsmanProgression01 = new QuestSwordsmanProgression01();

	// Quest Line : Sniper Progression
	// public static Quest sniperProgression01 = new QuestSniperProgression01();
	// public static Quest sniperProgression02 = new QuestSniperProgression02();

	// Bounties
	// Low Level
	// public static Quest bountyLowLevel01 = new BountyLowLevel01();
	// public static Quest bountyLowLevel02 = new BountyLowLevel02();
	// public static Quest bountyLowLevel03 = new BountyLowLevel03();

	// Ponehlyphs
	// public static Quest poneglyphChallengeCrocodile = new
	// QuestPoneglyphChallengeCrocodile();

	public static void init() {
		// Quest Line : Swordsman Progression
		registerQuest(swordsmanProgression01);
		/*
		 * registerQuest(swordsmanProgression02); registerQuest(swordsmanProgression03);
		 * registerQuest(swordsmanProgression04); registerQuest(swordsmanProgression05);
		 * registerQuest(swordsmanProgression06);
		 * 
		 * // Quest Line : Sniper Progression registerQuest(sniperProgression01);
		 * registerQuest(sniperProgression02);
		 * 
		 * // Bounties registerQuest(bountyLowLevel01); registerQuest(bountyLowLevel02);
		 * registerQuest(bountyLowLevel03);
		 * 
		 * // Poneglyphs registerQuest(poneglyphChallengeCrocodile);
		 */

	}

	private static void registerQuest(Quest quest) {
		WyRegistry.registerName("quest." + quest.getQuestID() + ".name", quest.getTitle());
		WyRegistry.registerName("quest." + quest.getQuestID() + ".desc", quest.getDescription());
		allQuests.put(quest.getQuestID(), quest);
	}

}
