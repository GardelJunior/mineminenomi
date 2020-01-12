package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.items.AkumaNoMi;

public class GUIPlayerStats extends GuiPage {

	private GUIPlayer gui;

	public GUIPlayerStats(GUIPlayer gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void render(int mx, int my, float f) {
		int xCenter = gui.width / 2;
		int yCenter = gui.height / 2;

		int xStart = xCenter - 128;
		int yStart = yCenter - 82;

		int xStartScaled = xStart;
		int yStartScaled = yStart + 10;

		ExtendedEntityData props = ExtendedEntityData.get(gui.player);
		
		// Render Player
		drawRect(xStart + 15, yStart + 20, xStart + 70, yStart + 90, 0xff000000);
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
			if (!usedFruit.equals("yamidummy")) {
				ItemStack df = DevilFruitsHelper.getDevilFruitItem(props.getUsedFruit());
				drawDevilFruit(df, xStart + 15, yStart + 110);
				if(props.hasYamiPower()) {
					drawDevilFruit(yamiFruit, xStart + 135, yStart + 110);
				}
			} else {
				drawDevilFruit(yamiFruit, xStart + 15, yStart + 110);
			}
		}
		
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
	
	private void drawDevilFruit(ItemStack item, int posX, int posY) {
		drawRect(posX, posY, posX + 35, posY + 35, 0xff000000);
		drawRect(posX + 40, posY + 5, posX + 105, posY + 15, 0x33000000);
		drawRect(posX + 40, posY + 20, posX + 105, posY + 30, 0x33000000);
		
		final String fruitType = ((AkumaNoMi)item.getItem()).getType().getName();
		final int nameHalfWidth = mc.fontRenderer.getStringWidth(item.getDisplayName())/2;
		final int typeHalfWidth = mc.fontRenderer.getStringWidth(fruitType)/2;
		
		GL11.glColor3f(1, 1, 1);
		GL11.glPushMatrix();
			GL11.glScalef(2, 2, 1);
			drawItemStack(item, posX/2 + 1, posY/2 + 1, "");
			GL11.glScalef(.25f, .25f, 1);
			mc.fontRenderer.drawStringWithShadow(item.getDisplayName(), posX*2 + 150 - nameHalfWidth, posY*2 + 16, 0xffffffff);
			mc.fontRenderer.drawStringWithShadow(fruitType, posX*2 + 150 - typeHalfWidth, posY*2 + 46, 0xffffffff);
		GL11.glPopMatrix();
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
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getPageName() {
		return "Stats";
	}
}
