package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.ArrayList;
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
	private EntityPlayer player;
	private RenderItem renderItem;
	private ExtendedEntityData props;
	private List<IGuiPage> pages;
	private IGuiPage currentPage;

	public GUIPlayer(EntityPlayer player) {
		this.player = player;
		this.props = ExtendedEntityData.get(player);
		this.pages = new ArrayList<IGuiPage>(3);

		this.pages.add(new StatsGuiPlayer(this));
		this.pages.add(new AbilitiesGuiPlayer(this));
		this.pages.add(new QuestsGuiPlayer(this));

		this.currentPage = this.pages.get(this.props.getGuiPage());
	}

	@Override
	protected void keyTyped(char key, int action) {
		//ESC or E or R
		if(action == 1 || action==18 || action == 19) {
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
			if(this.buttonList.get(k) instanceof GuiPageButton) {
				((GuiPageButton)this.buttonList.get(k)).drawButton(mc, x, y);
			}
		}
		
		// Draw Background
		GL11.glColor3f(1, 1, 1);
		this.mc.renderEngine.bindTexture(ID.TEXTURE_GUI2);
		this.drawTexturedModalRect(xStart, yStart, 0, 0, 256, 161);
		this.currentPage.render();
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
		for(IGuiPage page : this.pages) {
			GuiPageButton button = new GuiPageButton(btnId++, xTabbedStart + 50 * btnId, yTabbedStart, 50, 20, page.getPageName());
			if(page == currentPage) button.active = true;
			this.buttonList.add(button);
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if(button instanceof GuiPageButton) {
			((GuiPageButton)this.buttonList.get(this.pages.indexOf(this.currentPage))).active = false;
			((GuiPageButton)button).active = true;
			this.currentPage = this.pages.get(button.id-1);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	private void drawItemStack(ItemStack itemStack, int x, int y, String string) {
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if (itemStack != null)
			font = itemStack.getItem().getFontRenderer(itemStack);
		if (font == null)
			font = fontRendererObj;
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), itemStack, x, y);
		itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), itemStack, x, y - 0, string);
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		RenderHelper.disableStandardItemLighting();
	}

	class GuiPageButton extends GuiButton {

		public boolean active;
		
		public GuiPageButton(int id, int x, int y, int width, int height, String text) {
			super(id, x, y, width, height, text);
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			mc.renderEngine.bindTexture(ID.TEXTURE_GUI2);
			if (this.active)
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			else if(this.field_146123_n)
				GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
			else
				GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
			
			this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int k = this.getHoverState(this.field_146123_n);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 192, 48, this.height);
			int halfString = mc.fontRenderer.getStringWidth(displayString)/2;
			int l = 0xffffffff;
			if (!this.active) {
				l = 0xff000000;
				if (this.field_146123_n) l = 0xffcccccc;
				mc.fontRenderer.drawString(this.displayString, this.xPosition + 24 - halfString, this.yPosition + (this.height - 8) / 2, l);
			} else {
				mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + 24 - halfString, this.yPosition + (this.height - 8) / 2, l);
			}
		}
	}

	interface IGuiPage {
		public void render();
		public List getButtons();
		public String getPageName();
	}

	class StatsGuiPlayer implements IGuiPage {

		private GUIPlayer gui;

		public StatsGuiPlayer(GUIPlayer gui) {
			this.gui = gui;
		}

		@Override
		public void render() {
			int xCenter = gui.width / 2;
			int yCenter = gui.height / 2;

			int xStart = xCenter - 128;
			int yStart = yCenter - 82;

			int xStartScaled = xStart;
			int yStartScaled = yStart + 10;

			// Render Player
			gui.drawRect(xStart + 15, yStart + 20, xStart + 70, yStart + 90, 0xff000000);
			GuiInventory.func_147046_a(xStartScaled + 42, yStartScaled + 75, 30, 0, 0, gui.mc.thePlayer);

			// Render Username
			String username = gui.mc.thePlayer.getDisplayName();
			gui.drawRect(xStartScaled + 15, yStartScaled + 85, xStartScaled + 70, yStartScaled + 95, 0xff000000);
			int usrNameStartX = gui.mc.fontRenderer.getStringWidth(username) / 2;
			GL11.glPushMatrix();
			GL11.glScalef(.5f, .5f, 1);
			gui.mc.fontRenderer.drawStringWithShadow(username, xStartScaled * 2 + 84 - usrNameStartX,yStartScaled * 2 + 176, 0xffffffff);
			GL11.glPopMatrix();

			// Render Stats
			this.drawAttribute(xStartScaled + 75, yStartScaled + 10, "Doriki", String.valueOf(props.getDoriki()),0xff000000,true, 76, 177);
			this.drawAttribute(xStartScaled + 75, yStartScaled + 40, "Belly", String.valueOf(props.getBelly()),0xff000000,true, 93, 177);
			this.drawAttribute(xStartScaled + 75, yStartScaled + 70, "Bounty", String.valueOf(props.getBounty()),0xff000000,true, 110, 177);

			// Render Info
			final String factionName = I18n.format(ID.LANG_GUI_FACTION);
			final String raceName = I18n.format(ID.LANG_GUI_RACE);
			final String styleName = I18n.format(ID.LANG_GUI_STYLE);
			final String factionValue = I18n.format("faction." + WyHelper.getFancyName(props.getFaction().toLowerCase()) + ".name");
			final String raceValue = I18n.format("race." + props.getRace().toLowerCase() + ".name");
			final String styleValue = I18n.format("style." + props.getFightStyle().toLowerCase() + ".name");
			
			this.drawAttribute(xStartScaled + 160, yStartScaled + 10, factionName, factionValue, 0xff000000,false, 0, 0);
			this.drawAttribute(xStartScaled + 160, yStartScaled + 40, raceName, raceValue, 0xff000000,false, 0, 0);
			this.drawAttribute(xStartScaled + 160, yStartScaled + 70, styleName, styleValue, 0xff000000,false, 0, 0);
			
			final String usedFruit = props.getUsedFruit();
			
			if (!usedFruit.equalsIgnoreCase("n/a") && !usedFruit.equals("null")) {
				final ItemStack yamiFruit = new ItemStack(GameRegistry.findItem(ID.PROJECT_ID, "yamiyaminomi"));
				final String yamiType = ((AkumaNoMi)yamiFruit.getItem()).getType().getName();
				
				// Render Devil Fruits
				drawRect(xStart + 15, yStart + 110, xStart + 50, yStart + 145, 0xff000000);
				drawRect(xStart + 55, yStart + 115, xStart + 125, yStart + 125, 0x33000000);
				drawRect(xStart + 55, yStart + 130, xStart + 125, yStart + 140, 0x33000000);
				GL11.glColor3f(1, 1, 1);
				
				if (!usedFruit.equals("yamidummy")) {
					ItemStack df = DevilFruitsHelper.getDevilFruitItem(props.getUsedFruit());
					GL11.glPushMatrix();
					GL11.glScalef(2, 2, 1);
					drawItemStack(df,xStart/2 + 15/2 + 2, yStart/2 + 110/2 + 1, "");
					GL11.glScalef(.25f, .25f, 1);
					final String fruitType = ((AkumaNoMi)df.getItem()).getType().getName();
					final int nameHalfWidth = mc.fontRenderer.getStringWidth(df.getDisplayName())/2;
					final int typeHalfWidth = mc.fontRenderer.getStringWidth(fruitType)/2;
					mc.fontRenderer.drawStringWithShadow(df.getDisplayName(), xStart*2 + 55*2 + 70 - nameHalfWidth, yStart*2 + 118*2, 0xffffffff);
					mc.fontRenderer.drawStringWithShadow(fruitType, xStart*2 + 55*2 + 70 - typeHalfWidth, yStart*2 + 133*2, 0xffffffff);
					GL11.glPopMatrix();
					if(props.hasYamiPower()) {
						drawRect(xStart + 135, yStart + 110, xStart + 170, yStart + 145, 0xff000000);
						drawRect(xStart + 175, yStart + 115, xStart + 240, yStart + 125, 0x33000000);
						drawRect(xStart + 175, yStart + 130, xStart + 240, yStart + 140, 0x33000000);
						GL11.glColor3f(1, 1, 1);
						GL11.glPushMatrix();
						GL11.glScalef(2, 2, 1);
						drawItemStack(yamiFruit, xStart/2 + 135/2 + 2, yStart/2 + 110/2 + 1, "");
						GL11.glScalef(.25f, .25f, 1);
						final int name2HalfWidth = mc.fontRenderer.getStringWidth(yamiFruit.getDisplayName())/2;
						final int type2HalfWidth = mc.fontRenderer.getStringWidth(yamiType)/2;
						mc.fontRenderer.drawStringWithShadow(yamiFruit.getDisplayName(), xStart*2 + 175*2 + 70 - name2HalfWidth, yStart*2 + 118*2, 0xffffffff);
						mc.fontRenderer.drawStringWithShadow(yamiType, xStart*2 + 175*2 + 70 - type2HalfWidth, yStart*2 + 133*2, 0xffffffff);
						GL11.glPopMatrix();
					}
				} else {
					GL11.glPushMatrix();
					GL11.glScalef(2, 2, 1);
					drawItemStack(yamiFruit, xStart/2 + 15/2 + 2, yStart/2 + 110/2 + 1, "");
					GL11.glPopMatrix();
				}
			}
			
		}

		private void drawAttribute(int posX, int posY, String attributeName, String value, int attributeColor, boolean drawIcon, int u,int v) {
			gui.mc.renderEngine.bindTexture(ID.TEXTURE_GUI2);
			if(drawIcon) {
				gui.drawRect(posX, posY, posX + 25, posY + 25, 0xff000000);
				gui.drawRect(posX + 30, posY + 11, posX + 80, posY + 21, 0x33000000);
				GL11.glColor3f(1, 1, 1);
				gui.drawTexturedModalRect(posX + 2, posY + 4, u, v, 18, 18);
			}else {
				gui.drawRect(posX, posY + 11, posX + 80, posY + 21, 0x33000000);
			}
			GL11.glColor3f(1, 1, 1);

			final int attribHalfSize = gui.mc.fontRenderer.getStringWidth(attributeName) / 2;
			final int valueHalfSize = gui.mc.fontRenderer.getStringWidth(value) / 2;
			final int xPadding = drawIcon? 55 : 40;
			
			gui.mc.fontRenderer.drawStringWithShadow(attributeName, posX + xPadding - attribHalfSize, posY + 1, 0xffffff00);
			gui.mc.fontRenderer.drawStringWithShadow(value, posX + xPadding - valueHalfSize, posY + 12, 0xffeeeeee);
		}

		@Override
		public List getButtons() {
			return null;
		}

		@Override
		public String getPageName() {
			return "Stats";
		}
	}

	class AbilitiesGuiPlayer implements IGuiPage {

		private GUIPlayer gui;

		public AbilitiesGuiPlayer(GUIPlayer gui) {
			this.gui = gui;
		}

		@Override
		public void render() {

		}

		@Override
		public List getButtons() {
			return null;
		}

		@Override
		public String getPageName() {
			return "Abilities";
		}
	}

	class QuestsGuiPlayer implements IGuiPage {

		private GUIPlayer gui;

		public QuestsGuiPlayer(GUIPlayer gui) {
			this.gui = gui;
		}

		@Override
		public void render() {

		}

		@Override
		public List getButtons() {
			return null;
		}

		@Override
		public String getPageName() {
			return "Quests";
		}
	}
}
