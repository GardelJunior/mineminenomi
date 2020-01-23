package xyz.pixelatedw.MineMineNoMi3.gui.extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.GuiScrollingList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestSync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.gui.GUIPlayerQuests;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;

public class GUIQuestList extends GuiScrollingList implements Observer {

	private GUIPlayerQuests parent;
	private QuestProperties props;

	private List<Object> availableQuests = new ArrayList<Object>();
	private Quest currentQuest;
	private int selectedIndex = -1;
	
	public GUIQuestList(GUIPlayerQuests parent, QuestProperties props) {
		super(parent.mc, 235, 300, parent.yCenter - 72, parent.yCenter + 72, parent.xStart + 139,38);
		this.parent = parent;
		this.props = props;
		
		this.currentQuest = this.props.getCurrentQuest();
		availableQuests.clear();
		
		availableQuests.add("Active Quests");
		availableQuests.addAll(this.props.getQuests());
		availableQuests.add("Completed Quests");
		availableQuests.addAll(this.props.getCompletedQuests());
		
		props.deleteObservers();
		props.addObserver(this);
	}

	protected int getContentHeight() {
		return Math.max((this.getSize()) * 38 + 1, this.bottom - this.top - 3);
		//return Math.max((this.getSize()) * 30 + 1, this.listHeight);
	}

	protected int getSize() {
		return this.availableQuests.size();
	}

	protected void elementClicked(int index, boolean doubleClick) {
		boolean flag = false;
		if(this.availableQuests.get(index) instanceof Quest) {
			System.out.println("Selected quest" + ((Quest)this.availableQuests.get(index)).getTitle());
			this.selectedIndex = index;
			parent.onSelectQuest();
		}

		if (flag) {
			WyNetworkHelper.sendToServer(new PacketQuestSync(props));
		}
	}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
	}

	protected void drawSlot(int slotIndex, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		Object attr = this.availableQuests.get(slotIndex);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		if(attr instanceof Quest) {
			Quest quest = (Quest) attr;
			parent.drawRect(left+2, slotTop-1, left + listWidth - 6, slotTop + slotHeight + 1, 0xff666666);
			parent.drawRect(left+3, slotTop, left + listWidth - 7, slotTop + slotHeight, 0xff444444);
			
			parent.drawRect(left+6, slotTop + 3, left + 36, slotTop + slotHeight - 3, 0xff333333);
			if(quest.isCompleted()) {
				parent.drawRect(left, slotTop-1, left + 2, slotTop + slotHeight + 1, 0xFF11FF11);
				fontRenderer.drawSplitString(quest.getTitle(), this.left + 39, slotTop + 4, this.listWidth - 47, 0xFFccffcc);
				fontRenderer.drawStringWithShadow("Completed", this.left + 39, slotTop + 26, 0xFF11FF11);
			}else {
				if(props.getCurrentQuest() == quest) {
					parent.drawRect(left, slotTop-1, left + 2, slotTop + slotHeight + 1, 0xffffff00);
				}else {
					parent.drawRect(left, slotTop-1, left + 2, slotTop + slotHeight + 1, 0xff999999);
				}
				fontRenderer.drawSplitString(quest.getTitle(), this.left + 39, slotTop + 4, this.listWidth - 47, 0xFFffffff);
				fontRenderer.drawStringWithShadow(String.format("Progress: %2.0f%%", quest.getPercentage()*100), this.left + 39, slotTop + 26, 0xFFcccccc);
			}
		}else if(attr instanceof String) {
			GL11.glColor3f(1, 1, 1);
			/*Minecraft.getMinecraft().renderEngine.bindTexture(ID.TEXTURE_GUI2);
			GL11.glPushMatrix();
			GL11.glScalef(1.6f, 1, 1);
			parent.drawTexturedModalRect((int)(this.left/1.6f - 62f/2 - 2 + this.listWidth/(2 * 1.6f)), slotTop, 50, 230, 62, 27);
			GL11.glPopMatrix();*/
			parent.drawRect(left, slotTop+1, left + listWidth - 6, slotTop + slotHeight - 1, 0xff333333);
			parent.drawRect(left, slotTop+2, left + listWidth - 6, slotTop + slotHeight - 2, 0xff000000);
			fontRenderer.drawStringWithShadow((String)attr, this.left + (this.listWidth-6)/2 - fontRenderer.getStringWidth((String)attr)/2, slotTop + 14, 0xFFFFFF00);
		}
	}

	@Override
	protected void drawBackground() {
		//parent.drawRect(this.left, this.top, this.left + this.listWidth,this.bottom, 0xff000000);
	}
	
	public Quest getSelectedQuest() {
		if(selectedIndex>-1) {
			return (Quest) this.availableQuests.get(selectedIndex);
		}else {
			return null;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.currentQuest = this.props.getCurrentQuest();
		availableQuests.clear();
		availableQuests.add("Active Quests");
		availableQuests.addAll(this.props.getQuests());
		availableQuests.add("Completed Quests");
		availableQuests.addAll(this.props.getCompletedQuests());
	}
}
