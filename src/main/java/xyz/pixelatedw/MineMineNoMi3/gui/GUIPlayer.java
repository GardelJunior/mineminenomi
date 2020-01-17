package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.items.AkumaNoMi;

public class GUIPlayer extends GuiScreen {
	public EntityPlayer player;
	private RenderItem renderItem;
	private ExtendedEntityData props;
	private List<GuiPage> pages;
	private GuiPage currentPage;
	private boolean alternativeInit;
	
	public GUIPlayer(EntityPlayer player) {
		this.player = player;
		this.props = ExtendedEntityData.get(player);
		this.pages = new ArrayList<GuiPage>(3);

		this.pages.add(new GUIPlayerStats(this));
		this.pages.add(new GUIPlayerAbilities(this));
		this.pages.add(new GUIPlayerQuests(this));

		this.currentPage = this.pages.get(this.props.getGuiPage());
	}
	
	public GUIPlayer(EntityPlayer player, int currentPage) {
		this(player);
		this.currentPage = this.pages.get(currentPage);
		this.alternativeInit = true;
	}

	@Override
	protected void keyTyped(char key, int action) {
		//ESC or E or R
		if(action == 1 || action==18 || action == 19) {
			if(!alternativeInit) props.setGuiPage(this.pages.indexOf(currentPage));
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		int xCenter = this.width / 2;
		int yCenter = this.height / 2;

		int xStart = xCenter - 128;
		int yStart = yCenter - 82;
		
		//Render the buttons behind the background
		for(int k = 0 ; k < this.buttonList.size() ; ++k) {
			if(this.buttonList.get(k) instanceof GuiButtonPage) {
				((GuiButtonPage)this.buttonList.get(k)).drawButton(mc, x, y);
			}
		}
		
		// Draw Background
		GL11.glColor3f(1, 1, 1);
		this.mc.renderEngine.bindTexture(ID.TEXTURE_GUI2);
		this.drawTexturedModalRect(xStart, yStart, 0, 0, 256, 161);
		this.currentPage.render(x,y,f);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
		RenderHelper.disableStandardItemLighting();
		for (int k = 0; k < this.labelList.size(); ++k) {
			((GuiLabel) this.labelList.get(k)).func_146159_a(this.mc, x, y);
		}
	}

	@Override
	public void initGui() {
		int xCenter = this.width / 2;
		int yCenter = this.height / 2;

		int xStart = xCenter - 256;
		int yStart = yCenter - 82;

		final int xTabbedStart = xStart + 40;
		final int yTabbedStart = yStart - 13;

		int btnId = 1;
		for(GuiPage page : this.pages) {
			GuiButtonPage button = new GuiButtonPage(btnId++, xTabbedStart + 50 * btnId, yTabbedStart, 50, 20, page.getPageName());
			if(page == currentPage) button.active = true;
			this.buttonList.add(button);
			page.initGui();
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if(button instanceof GuiButtonPage) {
			((GuiButtonPage)this.buttonList.get(this.pages.indexOf(this.currentPage))).active = false;
			((GuiButtonPage)button).active = true;
			this.currentPage = this.pages.get(button.id-1);
		}
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.currentPage.onMouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
