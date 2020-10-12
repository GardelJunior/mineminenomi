package xyz.pixelatedw.MineMineNoMi3.api.abilities;

public class AbilityMeta {
	public Ability ability;
	public int cooldownTime;
	public boolean isLoading;
	public boolean isCharging;
	public boolean isUnlocked;
	
	public void unlock() {
		
	}
	
	public boolean canUnlock() {
		return true;
	}
}
