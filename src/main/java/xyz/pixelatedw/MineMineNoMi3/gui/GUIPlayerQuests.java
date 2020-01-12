package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GUIPlayerQuests extends GuiPage {

	private GUIPlayer gui;

	public GUIPlayerQuests(GUIPlayer gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void render(int mx, int my, float r) {

	}

	@Override
	public List getButtons() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getPageName() {
		return "Quests";
	}
}
