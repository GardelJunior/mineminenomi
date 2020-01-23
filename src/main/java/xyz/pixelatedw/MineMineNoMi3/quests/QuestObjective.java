package xyz.pixelatedw.MineMineNoMi3.quests;

import java.lang.reflect.Field;

import net.minecraft.nbt.NBTTagCompound;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestHint;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;

public abstract class QuestObjective {
	
	protected String id;
	protected String title;
	protected String description;
	protected boolean isCompleted;
	
	private boolean isLocked;
	
	protected IObjectiveParent questParent;
	
	public QuestObjective(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public QuestObjective withId(String id) {
		this.id = id;
		return this;
	}
	
	public boolean isCompleted() {
		return this.isCompleted;
	}
	
	public void markAsCompleted() {
		this.isCompleted = true;
		this.questParent.onCompleteObjective(this);
	}
	
	public float getPercentage() {
		return this.isCompleted()? 1 : 0;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public void saveToNBT(NBTTagCompound tag) {
		try {
			//Using reflection to serialize all the fields data
			for(Field field : getClass().getDeclaredFields()) {
				if(field.getDeclaredAnnotation(SyncField.class) != null) {
					boolean isAccessible = field.isAccessible();
					field.setAccessible(true);
					String fieldName = this.getId()+"_"+field.getName();
					
					if(field.getType().equals(String.class)) {
						tag.setString(fieldName, (String)field.get(this));
					} else if(field.getType().equals(int.class)) {
						tag.setInteger(fieldName, field.getInt(this));
					} else if(field.getType().equals(float.class)) {
						tag.setFloat(fieldName, field.getFloat(this));
					} else if(field.getType().equals(double.class)) {
						tag.setDouble(fieldName, field.getDouble(this));
					} else if(field.getType().equals(boolean.class)) {
						tag.setBoolean(fieldName, field.getBoolean(this));
					}
					
					field.setAccessible(isAccessible);
				}
			}
			tag.setBoolean(this.getId() + "_completed", isCompleted());
			tag.setBoolean(this.getId() + "_locked", isLocked());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromNBT(NBTTagCompound tag) {
		try {
			//Using reflection to load all the fields data
			for(Field field : getClass().getDeclaredFields()) {
				if(field.getDeclaredAnnotation(SyncField.class) != null) {
					boolean isAccessible = field.isAccessible();
					field.setAccessible(true);
					String fieldName = this.getId()+"_"+field.getName();
					
					if(field.getType().equals(String.class)) {
						field.set(this, tag.getString(fieldName));
					} else if(field.getType().equals(int.class)) {
						field.setInt(this, tag.getInteger(fieldName));
					} else if(field.getType().equals(float.class)) {
						field.setFloat(this, tag.getFloat(fieldName));
					} else if(field.getType().equals(double.class)) {
						field.setDouble(this, tag.getDouble(fieldName));
					} else if(field.getType().equals(boolean.class)) {
						field.setBoolean(this, tag.getBoolean(fieldName));
					}
					
					field.setAccessible(isAccessible);
				}
			}
			this.isCompleted = tag.getBoolean(this.getId() + "_completed");
			this.isLocked = tag.getBoolean(this.getId() + "_locked");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public QuestObjective withParent(IObjectiveParent parent) {
		this.questParent = parent;
		return this;
	}
}
