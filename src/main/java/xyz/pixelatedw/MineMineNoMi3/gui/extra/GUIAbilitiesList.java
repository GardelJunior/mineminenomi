package xyz.pixelatedw.MineMineNoMi3.gui.extra;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.GuiScrollingList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import xyz.pixelatedw.MineMineNoMi3.Values;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.WyRenderHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityManager;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketAbilitySync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.gui.GUIPlayerAbilities;

public class GUIAbilitiesList extends GuiScrollingList {

	private GUIPlayerAbilities parent;
	private AbilityProperties props;

	private List<Ability> availableAbilities = new ArrayList<Ability>();

	public GUIAbilitiesList(GUIPlayerAbilities parent, AbilityProperties abilityProps, Ability[] abilities) {
		super(parent.mc, 185, 300, (parent.yCenter - 10) / 2, (parent.yCenter + 200) / 2, (parent.xCenter + 32) / 2,
				56);
		this.parent = parent;
		this.props = abilityProps;
		availableAbilities.clear();

		for (int i = 0; i < abilities.length - 1; i++) {
			if (abilities[i] != null)
				this.availableAbilities.add(abilities[i]);
		}
	}

	protected int getContentHeight() {
		return (Math.max(1, this.getSize())) * 56 + 1;
	}

	protected int getSize() {
		return this.availableAbilities.size();
	}

	protected void elementClicked(int index, boolean doubleClick) {
		if (parent.selectedSlot > -1) {
			boolean flag = true;

			for (int i = 0; i < props.countAbilitiesInHotbar(); i++) {
				if (props.getAbilityFromSlot(i) != null && props.getAbilityFromSlot(i).getAttribute().getAttributeName()
						.equalsIgnoreCase(availableAbilities.get(index).getAttribute().getAttributeName())) {
					flag = false;
				}
			}

			if (flag) {
				props.setAbilityInSlot(parent.selectedSlot, AbilityManager.instance().getAbilityByName(
						WyHelper.getFancyName(availableAbilities.get(index).getAttribute().getAttributeName())));
				WyNetworkHelper.sendToServer(new PacketAbilitySync(props));
			}
		}
	}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	protected void drawBackground() {

	}

	protected void drawSlot(int slotIndex, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		boolean flag = false;
		AbilityAttribute attr = availableAbilities.get(slotIndex).getAttribute();
		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		for (int i = 0; i < props.countAbilitiesInHotbar(); i++) {
			final Ability a = props.getAbilityFromSlot(i);
			if (a != null && a.getAttribute().getAttributeName()
					.equalsIgnoreCase(availableAbilities.get(slotIndex).getAttribute().getAttributeName())) {
				flag = true;
				break;
			}
		}

		final String attribName = WyHelper.getFancyName(attr.getAttributeName());
		fontRenderer.drawStringWithShadow(I18n.format("ability." + attribName + ".name"),this.left + 30, slotTop + 7, flag ? 0xFF0000 : 0xFFFFFF);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(2);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(1f, 0, 0);
			GL11.glColor3f(.4f, .4f, .4f);
			GL11.glVertex2f(this.left+.5f, slotTop + 1);
			GL11.glVertex2f(this.left+.5f, slotTop + 56);
			GL11.glVertex2f(this.left+178.5f, slotTop + 1);
			GL11.glVertex2f(this.left+178.5f, slotTop + 56);
			GL11.glVertex2f(this.left, slotTop + 1);
			GL11.glVertex2f(this.left+178.5f, slotTop + 1);
			GL11.glVertex2f(this.left, slotTop + 56f);
			GL11.glVertex2f(this.left+178.5f, slotTop + 56f);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		float infoSpacing = 30;

		List<String> lines = fontRenderer.listFormattedStringToWidth(Values.abilityWebAppExtraParams.get(attribName)[1], 340);
		GL11.glPushMatrix();
		GL11.glScalef(.5f, .5f, 0);
		int hSpacing = 90;
		
		fontRenderer.drawStringWithShadow(String.format("Cooldown: %.1fs", attr.getAbilityCooldown()/20f),this.left*2 + 11, slotTop*2 + 43, 0x5555ff);
		if(attr.isPassive()) {
			fontRenderer.drawStringWithShadow("Defensive",this.left*2 + 11 + hSpacing, slotTop*2 + 43, 0xffff55);	
			hSpacing += 70;
		}
		if(attr.isPunch()) {
			fontRenderer.drawStringWithShadow("Melee",this.left*2 + 11 + hSpacing, slotTop*2 + 43, 0xff5555);	
			hSpacing += 80;
		}
		
		for(int i = 0 ; i < lines.size() ; i++) {
			fontRenderer.drawStringWithShadow(lines.get(i),
					this.left*2 + 11,(int)( slotTop * 2 + infoSpacing * 2 + 5 + 10*i), 0xaaaaaa);
		}
		GL11.glPopMatrix();
		GL11.glColor3f(1, 1, 1);
		
		WyRenderHelper.drawAbilityIcon(WyHelper.getFancyName(attr.getAbilityTexture()), this.left + 5, slotTop + 2, 16,16);
		GL11.glColor3f(1, 1, 1);
	}

}
