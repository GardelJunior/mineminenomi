package xyz.pixelatedw.MineMineNoMi3.quests;

import xyz.pixelatedw.MineMineNoMi3.lists.ListQuests;

public enum EnumQuestlines {

	ARTOFWEATHER_PROGRESSION("artofweatherprogression"),
	MEDIC_PROGRESSION("medicprogression"),
	SNIPER_PROGRESSION("sniperprogression"),
	SWORDSMAN_PROGRESSION("swordsmanprogression");

	String questlineName;
	Quest[] quests;

	private EnumQuestlines(String questlineName, Quest... quests) {
		this.questlineName = questlineName;
		this.quests = quests;
	}

	public String getQuestlineName() {
		return this.questlineName;
	}

	public Quest[] getQuests() {
		return this.quests;
	}
}
