package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyRenderHelper;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketStartQuest;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
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
	
	private GuiButton backButton, startButton;
	
	private int screenIndex = QUEST_ITEM;

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
		
		backButton = new GUIButtonNoTexture(1, this.xCenter - 117, this.yStart + 7, 22, 21, "");
		startButton = new GUIButtonNoTexture(2, this.xCenter - 117, this.yStart + 141, 234, 15, "");
		
		this.buttonList.add(backButton);
		this.buttonList.add(startButton);
		
		this.questList = new GUIQuestList(this, this.props);
		this.questList.registerScrollButtons(this.buttonList, 998, 999);
		
		if(questList.getSelectedQuest() == null) {
			backButton.enabled = false;
			startButton.enabled = false;
			screenIndex = QUEST_LIST;
		}
		
		super.initGui();
	}
	
	public void onSelectQuest() {
		screenIndex = QUEST_ITEM;
		this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		backButton.enabled = true;
		startButton.enabled = questList.getSelectedQuest() != props.getCurrentQuest();
	}

	@Override
	public void render(int mx, int my, float f) {
		WyRenderHelper.startGlScissor(xStart + 138, yStart + 10, 236, 144);
		if(screenIndex == QUEST_LIST) {
			this.questList.drawScreen(mx, my, f);
		}else if(screenIndex == QUEST_ITEM){
			mc.renderEngine.bindTexture(ID.TEXTURE_GUI2);
			boolean hover = mx >= xCenter - 115 && mx <= xCenter - 100 && my >= yStart + 12 && my <= yStart + 30;
			//Divisor line color
			drawRect(xCenter - 118, yStart, xCenter + 118, yStart + 155, 0xff888888);
			//Button bg
			drawRect(xCenter - 117, yStart+11, xCenter - 117 +22, yStart + 32, hover? 0xff555555 : 0xff444444);
			//Title bg
			drawRect(xCenter - 117 + 23, yStart+11, xCenter + 117, yStart + 32, 0xff333333);
			GL11.glColor4f(1,1,1,1);
			drawTexturedModalRect(xCenter - 115, yStart + 13, 60,hover? 179 : 161, 15, 18);
			
			Quest selected = questList.getSelectedQuest();
			if(selected!=null) {
				//Description BG
				drawRect(xCenter - 117, yStart+33, xCenter + 117, yStart + 80, 0xff444444);
				
				//Objective bg
				drawRect(xCenter - 117, yStart+81, xCenter, yStart + 95, 0xff222222);
				drawRect(xCenter - 117, yStart+96, xCenter, yStart + 140, 0xff333333);
				
				drawRect(xCenter+1, yStart+81, xCenter+117, yStart + 95, 0xff222222);
				drawRect(xCenter+1, yStart+96, xCenter+117, yStart + 140, 0xff333333);
				
				drawRect(xCenter - 113, yStart + 36, xCenter - 75, yStart + 76, 0xff222222);
				
				//Botao
				//drawRect(xCenter + 20, yStart+70, xCenter+80 + 15, yStart + 85, 0xffffffff);
				//drawRect(xCenter + 21, yStart+71, xCenter+80 + 14, yStart + 84, 0xff555555);
				
				List<String> title = fontRendererObj.listFormattedStringToWidth(selected.getTitle(), 210);
				
				int index = 0;
				int titleSize = (fontRendererObj.FONT_HEIGHT + 2) * title.size();
				for(String titlePart : title) {
					int halfSize = fontRendererObj.getStringWidth(titlePart)/2;
					fontRendererObj.drawStringWithShadow(titlePart, this.xCenter - 94 + 211/2 - halfSize, this.yStart + 23 + 10 * (index++) - titleSize/2, 0xffffff00);
				}
				fontRendererObj.drawSplitString(selected.getDescription(), this.xCenter - 70, this.yStart + 38, 190, 0xff333333);
				fontRendererObj.drawSplitString(selected.getDescription(), this.xCenter - 70, this.yStart + 37, 190, 0xffffffff);
				
				fontRendererObj.drawStringWithShadow("Objectives",this.xCenter - 88, this.yStart + 84, 0xffffff00);
				
				int lastY = this.yStart + 98;
				int objectivesSize = selected.getObjectives().size();
				for(int i = 0 ; i < objectivesSize ; i++) {
					QuestObjective obj = selected.getObjectives().get(i);
					List<String> objective = fontRendererObj.listFormattedStringToWidth(obj.getTitle(), 110);
					//fontRendererObj.drawSplitString(selected.getObjectives().get(i).getTitle(), this.xCenter - 115, lastY + 4, 100, 0xffffffff);
					drawRect(this.xCenter - 115,lastY, this.xCenter - 2, lastY + (fontRendererObj.FONT_HEIGHT + 2) * objective.size(), 0x66000000);
					
					for(String objectivePart : objective) {
						fontRendererObj.drawStringWithShadow(obj.isCompleted()? EnumChatFormatting.STRIKETHROUGH + objectivePart : objectivePart, this.xCenter - 112, lastY + 2, obj.isCompleted()? 0xff00ff00 : 0xffffff88);
						lastY += fontRendererObj.FONT_HEIGHT + 2;
					}
					lastY += fontRendererObj.FONT_HEIGHT;
					if(mx >= this.xCenter - 115 && mx <= this.xCenter - 2 && my >= lastY && my <= lastY + 10 + fontRendererObj.FONT_HEIGHT * objective.size()) {
					}
				}
				
				fontRendererObj.drawStringWithShadow("Rewards",this.xCenter + 38, this.yStart + 84, 0xffffff00);

				if(selected == props.getCurrentQuest()) {
					final String text = "Progress: " + selected.getPercentage() * 100 + "%";
					drawRect(xCenter-117, yStart+141, xCenter+117, yStart + 153, 0xff444444);
					drawRect(xCenter-117, yStart+141, xCenter-117+(int)(234 * selected.getPercentage()), yStart + 153, 0xff005500);
					fontRendererObj.drawStringWithShadow(text,this.xCenter - fontRendererObj.getStringWidth(text)/2, this.yStart + 143, 0xffffffff);
				}else if(selected.isCompleted()) {
					drawRect(xCenter-117, yStart+141, xCenter+117, yStart + 153, 0xff444444);
					drawRect(xCenter-117, yStart+141, xCenter+117, yStart + 153, 0xff005500);
					fontRendererObj.drawStringWithShadow("Quest Completed",this.xCenter - fontRendererObj.getStringWidth("Quest Completed")/2, this.yStart + 143, 0xff00ff00);
				} else {
					boolean h = false;
					if(mx >= xCenter- 117 && mx <= xCenter+117 && my >= yStart+141 && my <= yStart + 153) {
						h = true;
					}
					drawRect(xCenter-117, yStart+141, xCenter+117, yStart + 153, h? 0xff666666 : 0xff444444);
					fontRendererObj.drawStringWithShadow(EnumChatFormatting.BOLD+"Start Quest",this.xCenter - fontRendererObj.getStringWidth(EnumChatFormatting.BOLD+"Start Quest")/2, this.yStart + 143, h? 0xffffff33:0xffffffaa);
				}
			}
		}
		WyRenderHelper.endGlScissor();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 1) {
			screenIndex = QUEST_LIST;
			backButton.enabled = false;
			startButton.enabled = false;
		}
		if(button.id == 2) {
			startButton.enabled = false;
			WyNetworkHelper.sendToServer(new PacketStartQuest(questList.getSelectedQuest().getQuestID()));
		}
	}

	@Override
	public String getPageName() {
		return "Quests";
	}
}
