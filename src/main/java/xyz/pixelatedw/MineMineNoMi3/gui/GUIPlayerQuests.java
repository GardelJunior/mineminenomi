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
import xyz.pixelatedw.MineMineNoMi3.helpers.MyRenderHelper;
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

		backButton = new GUIButtonNoTexture(1, xCenter - 117, this.yStart + 7, 22, 21, "");
		startButton = new GUIButtonNoTexture(2, xCenter - 117, this.yStart + 141, 234, 15, "");

		this.buttonList.add(backButton);
		this.buttonList.add(startButton);

		this.questList = new GUIQuestList(this, this.props);
		this.questList.registerScrollButtons(this.buttonList, 998, 999);

		if (questList.getSelectedQuest() == null) {
			backButton.enabled = false;
			startButton.enabled = false;
			screenIndex = QUEST_LIST;
		}

		super.initGui();
	}

	public void onSelectQuest() {
		screenIndex = QUEST_ITEM;
		this.mc.getSoundHandler()
				.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		backButton.enabled = true;
		startButton.enabled = questList.getSelectedQuest() != props.getCurrentQuest() && !questList.getSelectedQuest().isCompleted() && props.getCurrentQuest() == null;
	}

	@Override
	public void render(int mx, int my, float f) {
		WyRenderHelper.startGlScissor(xStart + 138, yStart + 10, 236, 144);
		switch (screenIndex) {
		case QUEST_LIST:
			renderQuestList(mx, my, f);
			break;
		case QUEST_ITEM:
			renderQuestItem(mx, my, f);
			break;
		}
		WyRenderHelper.endGlScissor();
	}

	public void renderQuestList(int mx, int my, float f) {
		this.questList.drawScreen(mx, my, f);
	}

	public void renderQuestItem(int mx, int my, float f) {
		Quest selected = questList.getSelectedQuest();
		if (selected != null) {
			MyRenderHelper.bindTexture(ID.TEXTURE_GUI2);
			boolean isHoveringBackBtn = MyRenderHelper.isMouseInsideRect(mx, my, xCenter - 115, yStart + 12, 15, 18);
			boolean isSelectedCurrentQuest = selected.equals(props.getCurrentQuest());

			final int xStarting = xCenter - 117;
			final int xEnding = xCenter + 117;

			// Divisor line color
			drawRect(xCenter - 118, yStart, xCenter + 118, yStart + 155, 0xff888888);
			// Button bg
			drawRect(xStarting, yStart + 11, xCenter - 95, yStart + 32,
					isHoveringBackBtn ? 0xff555555 : 0xff444444);
			// Title bg
			drawRect(xStarting + 23, yStart + 11, xEnding, yStart + 32, 0xff333333);
			MyRenderHelper.resetColor();
			drawTexturedModalRect(xCenter - 115, yStart + 13, 60, isHoveringBackBtn ? 179 : 161, 15, 18);

			// Description BG
			drawRect(xStarting, yStart + 33, xEnding, yStart + 80, 0xff444444);

			// Objective bg
			drawRect(xStarting, yStart + 81, xCenter, yStart + 95, 0xff222222);
			drawRect(xStarting, yStart + 96, xCenter, yStart + 140, 0xff333333);

			drawRect(xCenter + 1, yStart + 81, xEnding, yStart + 95, 0xff222222);
			drawRect(xCenter + 1, yStart + 96, xEnding, yStart + 140, 0xff333333);

			drawRect(xCenter - 113, yStart + 36, xCenter - 75, yStart + 76, 0xff222222);

			List<String> title = fontRendererObj.listFormattedStringToWidth(selected.getTitle(), 210);

			int index = 0;
			int titleSize = (fontRendererObj.FONT_HEIGHT + 2) * title.size();
			for (String titlePart : title) {
				int halfSize = fontRendererObj.getStringWidth(titlePart) / 2;
				fontRendererObj.drawStringWithShadow(titlePart, this.xCenter + 11 - halfSize,this.yStart + 23 + 10 * (index++) - titleSize / 2, 0xffffff00);
			}
			fontRendererObj.drawSplitString(selected.getDescription(), this.xCenter - 70, this.yStart + 38, 190,
					0xff333333);
			fontRendererObj.drawSplitString(selected.getDescription(), this.xCenter - 70, this.yStart + 37, 190,
					0xffffffff);

			fontRendererObj.drawStringWithShadow("Objectives", this.xCenter - 88, this.yStart + 84, 0xffffff00);

			int lastY = this.yStart + 98;
			int objectivesSize = selected.getObjectives().size();
			for (int i = 0; i < objectivesSize; i++) {
				QuestObjective obj = selected.getObjectives().get(i);
				List<String> objective = fontRendererObj.listFormattedStringToWidth(obj.getTitle(), 110);
				drawRect(this.xCenter - 115, lastY, this.xCenter - 2,lastY + (fontRendererObj.FONT_HEIGHT + 2) * objective.size(), 0x66000000);

				for (String objectivePart : objective) {
					fontRendererObj.drawStringWithShadow(
							obj.isCompleted() ? EnumChatFormatting.STRIKETHROUGH + objectivePart : objectivePart,
							this.xCenter - 112, lastY + 2, obj.isCompleted() ? 0xff00ff00 : 0xffffff88);
					lastY += fontRendererObj.FONT_HEIGHT + 2;
				}

				if (mx >= this.xCenter - 115 && mx <= this.xCenter - 2 && my >= lastY
						&& my <= lastY + 10 + fontRendererObj.FONT_HEIGHT * objective.size()) {
				}
				lastY += fontRendererObj.FONT_HEIGHT;
			}

			fontRendererObj.drawStringWithShadow("Rewards", this.xCenter + 38, this.yStart + 84, 0xffffff00);

			drawRect(xStarting, yStart + 141, xEnding, yStart + 153, 0xff444444);
			if (isSelectedCurrentQuest) {
				final String text = "Progress: " + selected.getPercentage() * 100 + "%";
				drawRect(xStarting, yStart + 141, xStarting + (int) (234 * selected.getPercentage()),yStart + 153, 0xff005500);
				fontRendererObj.drawStringWithShadow(text, this.xCenter - fontRendererObj.getStringWidth(text) / 2,this.yStart + 143, 0xffffffff);
			} else if (selected.isCompleted()) {
				drawRect(xStarting, yStart + 141, xEnding, yStart + 153, 0xff005500);
				fontRendererObj.drawStringWithShadow("Quest Complete",this.xCenter - fontRendererObj.getStringWidth("Quest Complete") / 2, this.yStart + 143,0xff00ff00);
			} else if(props.getCurrentQuest() != null) {
				drawRect(xStarting, yStart + 141, xEnding, yStart + 153, 0xff666666);
				fontRendererObj.drawStringWithShadow("Another Quest In Progress",this.xCenter - fontRendererObj.getStringWidth("Another Quest In Progress") / 2, this.yStart + 143,0xffff5500);
			} else {
				boolean h = false;
				if (mx >= xStarting && mx <= xEnding && my >= yStart + 141 && my <= yStart + 153) {
					h = true;
				}
				final String startQuest = EnumChatFormatting.BOLD + "Start Quest";
				drawRect(xStarting, yStart + 141, xEnding, yStart + 153, h ? 0xff666666 : 0xff444444);
				fontRendererObj.drawStringWithShadow(startQuest,
						this.xCenter - fontRendererObj.getStringWidth(startQuest) / 2,
						this.yStart + 143, h ? 0xffffff33 : 0xffffffaa);
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 1) {
			screenIndex = QUEST_LIST;
			backButton.enabled = false;
			startButton.enabled = false;
		}
		if (button.id == 2) {
			startButton.enabled = false;
			WyNetworkHelper.sendToServer(new PacketStartQuest(questList.getSelectedQuest().getQuestID()));
		}
	}

	@Override
	public String getPageName() {
		return "Quests";
	}
}
