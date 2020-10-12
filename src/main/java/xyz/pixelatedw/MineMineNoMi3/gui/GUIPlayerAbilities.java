package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.AchievementPage;
import scala.actors.threadpool.Arrays;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.WyRenderHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketAbilitySync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.gui.extra.GUIAbilitiesList;
import xyz.pixelatedw.MineMineNoMi3.gui.extra.GUIButtonNoTexture;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;

public class GUIPlayerAbilities extends GuiPage {

	private static final ResourceLocation texture = new ResourceLocation("textures/gui/achievement/achievement_background.png");
	
	private float grabX, grabY;
	private boolean isGrabbing = false;
	
	private int windowX, windowY, windowWidth = 256, windowHeight = 160;
	private int camWGrid = (windowWidth/16);
	private int camHGrid = (windowHeight/16);
	
	private int canvasX, canvasY, canvasWidth = 3000, canvasHeight = 2000;
	private int canvasWGrid = (canvasWidth / 16);
	private int canvasHGrid = (canvasHeight / 16);
	
	private final int rowSpacing = 24;
	private final int colSpacing = 24;

	protected GUIPlayer gui;
	protected ExtendedEntityData props;
	protected AbilityProperties abilityProps;
	private GUIAbilitiesList devilFruitsAbilitiesList, racialAbilitiesList, hakiAbilitiesList;

	public int selectedSlot = -1;
	public int selectedItem = -1;

	public GUIPlayerAbilities(GUIPlayer gui) {
		super();
		this.gui = gui;
		this.props = ExtendedEntityData.get(gui.player);
		this.abilityProps = AbilityProperties.get(gui.player);
	}

	@Override
	public void initGui() {
		this.xCenter = gui.width / 2;
		this.yCenter = gui.height / 2;

		this.xStart = xCenter - 128;
		this.yStart = yCenter - 82;

		for (int i = 0; i < 8; i++) {
			GL11.glEnable(GL11.GL_BLEND);
			this.buttonList.add(new GUIButtonNoTexture(i, (xCenter + 25 + (i * 46)) / 2, yCenter + 45, 21, 21, ""));
		}
		
		super.initGui();
	}

	@Override
	public void render(int mx, int my, float f) {
		int xCenter = gui.width / 2;
		int yCenter = gui.height / 2;

		int xStart = xCenter - 128;
		int yStart = yCenter - 82;
		
		//this.mc.getTextureManager().bindTexture(ID.TEXTURE_COMBATMODE);
		
		int camXGrid = (int) (windowX/16);
		int camYGrid = (int) (windowY/16);
		
		if(Mouse.isButtonDown(0)) {
			if(!isGrabbing) {
				//TODO: Checar se mouse dentro do canvas
				isGrabbing = true;
				this.grabX = mx;
				this.grabY = my;
			}else {
				float diffX = this.grabX-mx;
				float diffY = this.grabY-my;
				float moveX = diffX < -16? -16 : diffX > 16? 16 : diffX; //Clamp(-16,diffX,16)
				float moveY = diffY < -16? -16 : diffY > 16? 16 : diffY; //Clamp(-16,diffY,16)
				
				if(windowX + moveX >= canvasX && windowX + 256 + moveX <= canvasX + canvasWidth) windowX += moveX;
				if(windowY + moveY >= canvasY && windowY + 160 + moveY <= canvasY + canvasHeight) windowY += moveY;

				this.grabX = mx;
				this.grabY = my;
			}
		}else {
			if(isGrabbing) isGrabbing = false;
		}

		IIcon icon  = Blocks.planks.getIcon(0, 0);
		this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		WyRenderHelper.startGlScissor(xStart + 10, yStart + 10, 236, 145);
		GL11.glColor3f(.6f, .6f, .2f);
		for(int i = 0 ; i < canvasHGrid ; i++) {
			for(int j = 0 ; j < canvasWGrid ; j++) {
				if(i >= camYGrid - 1  && i <= camYGrid + camHGrid + 1) {
					if(j >= camXGrid - 1 && j <= camXGrid + camWGrid + 1){
						this.drawTexturedModelRectFromIcon(xStart - (int)this.windowX + 16 * j, yStart - (int)this.windowY + 16 * i, icon, 16, 16);
					}
				}
			}
		}
		WyRenderHelper.endGlScissor();
        

		//this.drawScreen(mx, my, f);
	}

	public void actionPerformed(GuiButton button) {
		if (button.id >= 0 && button.id < 8) {
			if (this.selectedSlot == button.id) {
				abilityProps.setAbilityInSlot(this.selectedSlot, null);
				WyNetworkHelper.sendToServer(new PacketAbilitySync(abilityProps));
			} else {
				this.selectedSlot = button.id;
			}
		} else {
			switch (button.id) {
			case 8:
				this.selectedItem = 0;
				break;
			case 9:
				this.selectedItem = 1;
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public String getPageName() {
		return "Abilities";
	}
}
