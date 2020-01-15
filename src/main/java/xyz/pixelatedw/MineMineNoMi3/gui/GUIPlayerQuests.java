package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xyz.pixelatedw.MineMineNoMi3.api.WyRenderHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.gui.extra.GUIQuestList;

public class GUIPlayerQuests extends GuiPage {

	private GUIPlayer gui;
	private GUIQuestList questList;
	private QuestProperties props;

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
		
		this.questList = new GUIQuestList(this, this.props);
		this.questList.registerScrollButtons(this.buttonList, 998, 999);
		
		super.initGui();
	}

	@Override
	public void render(int mx, int my, float f) {
		WyRenderHelper.startGlScissor(xStart + 5, yStart + 12, 203, 300);
		this.questList.drawScreen(mx, my, f);
		WyRenderHelper.endGlScissor();
	}

	@Override
	public String getPageName() {
		return "Quests";
	}
}
