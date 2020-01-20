package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyRenderHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.gui.extra.GUIButtonNoTexture;
import xyz.pixelatedw.MineMineNoMi3.gui.extra.GUIQuestList;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.QuestObjective;

public class GUIPlayerQuests extends GuiPage {

	private static final int QUEST_LIST = 0;
	private static final int QUEST_ITEM = 1;
	
	private GUIPlayer gui;
	private GUIQuestList questList;
	private QuestProperties props;
	
	private int screenIndex = QUEST_LIST;

	public GUIPlayerQuests(GUIPlayer gui) {
		super();
		this.gui = gui;
		this.props = QuestProperties.get(gui.player);
	}
	
	@Override
	public void initGui() {
		this.xCenter = gui.width / 2;
		this.yCenter = gui.height / 2;

		this.xStart = xCenter - 256;
		this.yStart = yCenter - 82;
		
		GuiButton btn = new GUIButtonNoTexture(1, this.xCenter - 115, this.yStart + 7, 15, 20, "");
		this.buttonList.add(btn);
		
		this.questList = new GUIQuestList(this, this.props);
		this.questList.registerScrollButtons(this.buttonList, 998, 999);
		
		if(questList.getSelectedQuest() != null) {
			btn.enabled = true;
			screenIndex = QUEST_ITEM;
		}else {
			btn.enabled = false;
			screenIndex = QUEST_LIST;
		}
		
		super.initGui();
	}
	
	public void onSelectQuest() {
		screenIndex = QUEST_ITEM;
		((GuiButton)this.buttonList.get(0)).enabled = true;
	}

	@Override
	public void render(int mx, int my, float f) {
		WyRenderHelper.startGlScissor(xStart + 138, yStart + 10, 236, 144);
		if(screenIndex == QUEST_LIST) {
			this.questList.drawScreen(mx, my, f);
		}else if(screenIndex == QUEST_ITEM){
			mc.renderEngine.bindTexture(ID.TEXTURE_GUI2);
			GL11.glColor4f(1,1, 1, 1);
			boolean hover = mx >= xCenter - 115 && mx <= xCenter - 100 && my >= yStart + 12 && my <= yStart + 30;
			drawTexturedModalRect(xCenter - 115, yStart + 12, 60,hover? 179 : 161, 15, 18);
			Quest selected = questList.getSelectedQuest();
			if(selected!=null) {
				List<String> title = fontRendererObj.listFormattedStringToWidth(selected.getTitle(), 110);
				drawRect(this.xCenter, this.yStart + 13, this.xCenter + 115, this.yStart + 33, 0x66000000);
				//fontRendererObj.drawSplitString(selected.getTitle(), this.xCenter + 4, this.yStart + 16, 100, 0xff444444);
				
				int index = 0;
				int titleSize = fontRendererObj.FONT_HEIGHT * title.size() + 2 * title.size();
				for(String titlePart : title) {
					int halfSize = fontRendererObj.getStringWidth(titlePart)/2;
					fontRendererObj.drawStringWithShadow(titlePart, this.xCenter + 115/2 - halfSize, this.yStart + 25 + 10 * (index++) - titleSize/2, 0xffffff00);
				}
				//fontRendererObj.drawSplitString(selected.getTitle(), this.xCenter + 4, this.yStart + 15, 100, 0xffffff00);
				
				drawRect(this.xCenter, this.yStart + 35, this.xCenter + 115, this.yStart + 151, 0x66000000);
				fontRendererObj.drawSplitString(selected.getDescription(), this.xCenter + 5, this.yStart + 41, 110, 0xff333333);
				fontRendererObj.drawSplitString(selected.getDescription(), this.xCenter + 4, this.yStart + 40, 110, 0xffeeeeee);
				
				fontRendererObj.drawStringWithShadow("Objectives",this.xCenter - 88, this.yStart + 35, 0xffffffff);
				
				int lastY = this.yStart + 45;
				int objectivesSize = selected.getObjectives().size();
				for(int i = 0 ; i < objectivesSize ; i++) {
					QuestObjective obj = selected.getObjectives().get(i);
					List<String> objective = fontRendererObj.listFormattedStringToWidth(obj.getTitle(), 110);
					//fontRendererObj.drawSplitString(selected.getObjectives().get(i).getTitle(), this.xCenter - 115, lastY + 4, 100, 0xffffffff);
					drawRect(this.xCenter - 115,lastY, this.xCenter - 2, lastY + 10 + fontRendererObj.FONT_HEIGHT * objective.size(), 0x66000000);
					if(mx >= this.xCenter - 115 && mx <= this.xCenter - 2 && my >= lastY && my <= lastY + 10 + fontRendererObj.FONT_HEIGHT * objective.size()) {
						//TODO: Draw the objective description
					}
					for(String objectivePart : objective) {
						fontRendererObj.drawStringWithShadow(obj.isCompleted()? EnumChatFormatting.STRIKETHROUGH + objectivePart : objectivePart, this.xCenter - 112, lastY + 4, obj.isCompleted()? 0xff00ff00 : 0xffffff88);
						lastY += fontRendererObj.FONT_HEIGHT + 2;
					}
					lastY += fontRendererObj.FONT_HEIGHT;
				}
				
				fontRendererObj.drawStringWithShadow("Rewards",this.xCenter - 82, lastY + 5, 0xffffffff);
			}
		}
		WyRenderHelper.endGlScissor();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 1) {
			screenIndex = QUEST_LIST;
			((GuiButton)this.buttonList.get(0)).enabled = false;
		}
	}

	@Override
	public String getPageName() {
		return "Quests";
	}
}
