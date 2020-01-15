package xyz.pixelatedw.MineMineNoMi3.gui.extra;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.GuiScrollingList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.WyRenderHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityManager;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketAbilitySync;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestSync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.gui.GUIPlayerAbilities;
import xyz.pixelatedw.MineMineNoMi3.gui.GUIPlayerQuests;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;

public class GUIQuestList extends GuiScrollingList {

	private GUIPlayerQuests parent;
	private QuestProperties props;

	private List<Object> availableQuests = new ArrayList<Object>();
	private Quest currentQuest;
	private int selectedIndex = -1;
	
	public GUIQuestList(GUIPlayerQuests parent, QuestProperties props) {
		super(parent.mc, 110, 300, parent.yCenter - 70, parent.yCenter + 70, parent.xStart + 140,30);
		this.parent = parent;
		this.props = props;
		this.currentQuest = this.props.getCurrentQuest();
		availableQuests.clear();
		
		availableQuests.add("Quests");
		availableQuests.addAll(this.props.getQuests());
		availableQuests.add("Completed Quests");
		availableQuests.addAll(this.props.getCompletedQuests());
	}

	protected int getContentHeight() {
		//return Math.max((this.getSize()) * 30 + 1, this.bottom - this.top - 3);
		return Math.max((this.getSize()) * 30 + 1, this.listHeight);
	}

	protected int getSize() {
		return this.availableQuests.size();
	}

	protected void elementClicked(int index, boolean doubleClick) {
		boolean flag = false;
		if(this.availableQuests.get(index) instanceof Quest) {
			System.out.println("Selected quest" + ((Quest)this.availableQuests.get(index)).getTitle());
			this.selectedIndex = index;
		}

		if (flag) {
			WyNetworkHelper.sendToServer(new PacketQuestSync(props));
		}
	}

	@Override
	protected boolean isSelected(int index) {
		return availableQuests.get(index) instanceof Quest;
	}

	protected void drawSlot(int slotIndex, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		Object attr = this.availableQuests.get(slotIndex);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		if(attr instanceof Quest) {
			Quest quest = (Quest) attr;
			if(quest.isCompleted()) {
				//fontRenderer.drawSplitString(quest.getTitle(), this.left + 2, slotTop + 5, this.listWidth - 8, 0xFF11FF11);
				fontRenderer.drawSplitString(quest.getTitle(), this.left + 2, slotTop, this.listWidth - 8, 0xFF666666);
				fontRenderer.drawStringWithShadow("Completed", this.left + 2, slotTop + 19, 0xFF11FF11);
			}else {
				fontRenderer.drawSplitString(quest.getTitle(), this.left + 2, slotTop, this.listWidth - 8, 0xFFFFFF);
				fontRenderer.drawStringWithShadow(String.format("Progress: %2.0f%%", quest.getPercentage()*100), this.left + 2, slotTop + 19, 0xFF666666);
			}
		}else if(attr instanceof String) {
			GL11.glColor3f(1, 1, 1);
			Minecraft.getMinecraft().renderEngine.bindTexture(ID.TEXTURE_GUI2);
			GL11.glPushMatrix();
			GL11.glScalef(1.6f, 1, 1);
			parent.drawTexturedModalRect((int)(this.left/1.6f + 3f/1.6f), slotTop, 50, 230, 62, 27);
			GL11.glPopMatrix();
			fontRenderer.drawStringWithShadow((String)attr, this.left + (this.listWidth-6)/2 - fontRenderer.getStringWidth((String)attr)/2, slotTop + 9, 0xFFFFFF);
		}
		/*Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(I18n.format("ability."
				+ WyHelper.getFancyName(availableAbilities.get(slotIndex).getAttribute().getAttributeName())
				+ ".name"), this.left + 30, slotTop + 7, flag ? 0xFF0000 : 0xFFFFFF);

		WyRenderHelper.drawAbilityIcon(WyHelper.getFancyName(attr.getAbilityTexture()), this.left + 5, slotTop + 2,
				16, 16);
		GL11.glColor3f(1, 1, 1);*/
	}

	@Override
	protected void drawBackground() {
		//parent.drawRect(this.left, this.top, this.left + this.listWidth,this.bottom, 0xff000000);
	}
}
