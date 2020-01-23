package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

abstract class GuiPage extends GuiScreen {
	
	public int xStart,yStart;
	public int xCenter,yCenter;
	
	public GuiPage() {
		this.mc = Minecraft.getMinecraft();
		this.fontRendererObj = this.mc.fontRenderer;
	}
	
	public List getButtons() {
		return this.buttonList;
	}
	
	public void onMouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
	}
	
	public void onUpdate() {
		
	}
	
	public abstract void render(int mouseX, int mouseY, float f);
	
	public abstract String getPageName();
}
