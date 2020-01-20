package xyz.pixelatedw.MineMineNoMi3.quests;

public interface IObjectiveParent {
	public void onCompleteObjective(QuestObjective objective);
	public void onUpdateObjective(QuestObjective objective);
	public void subscribeObjectives(QuestObjective ...objectives);
	public void unsubscribeObjectives(QuestObjective ...objectives);
}
