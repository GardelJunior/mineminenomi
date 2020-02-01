package xyz.pixelatedw.MineMineNoMi3.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
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

		this.xStart = xCenter - 256;
		this.yStart = yCenter - 82;

		for (int i = 0; i < 8; i++) {
			GL11.glEnable(GL11.GL_BLEND);
			this.buttonList.add(new GUIButtonNoTexture(i, (xCenter + 25 + (i * 46)) / 2, yCenter + 45, 21, 21, ""));
		}
		
		this.devilFruitsAbilitiesList = new GUIAbilitiesList(this, abilityProps, abilityProps.getDevilFruitAbilities());
        this.devilFruitsAbilitiesList.registerScrollButtons(this.buttonList, 998, 999);
   
        this.racialAbilitiesList = new GUIAbilitiesList(this, abilityProps, abilityProps.getRacialAbilities());
        this.racialAbilitiesList.registerScrollButtons(this.buttonList, 998, 999);
        
        this.hakiAbilitiesList = new GUIAbilitiesList(this, abilityProps, abilityProps.getHakiAbilities());
        this.hakiAbilitiesList.registerScrollButtons(this.buttonList, 998, 999);
        
        if(abilityProps.getRacialAbilities()[0] != null){
        	selectedItem = 0;
        	this.buttonList.add(new GUIButtonNoTexture(8, xCenter - 115, (int)(yStart * 0.6f + 55 * 0.6f), 21, 21, ""));
        }
        if(props.getUsedFruit() != null && !props.getUsedFruit().toLowerCase().equals("n/a")){
        	this.buttonList.add(new GUIButtonNoTexture(9, xCenter - 115, (int)(yStart * 0.6f + 95 * 0.6f), 21, 21, ""));
        }
        System.out.println("Selected Icon:" + selectedItem);
	}

	@Override
	public void render(int mx, int my, float f) {
		this.mc.getTextureManager().bindTexture(ID.TEXTURE_COMBATMODE);

		GL11.glEnable(GL11.GL_BLEND);

		for (int i = 0; i < 8; i++) {
			if (i == selectedSlot) {
				this.drawTexturedModalRect((xCenter + 25 + (i * 46)) / 2, yCenter + 45, 48, 0, 23, 23);
			} else {
				this.drawTexturedModalRect((xCenter + 25 + (i * 46)) / 2, yCenter + 45, 0, 0, 23, 23);
			}
		}
		
		WyRenderHelper.startGlScissor((xCenter - 110) / 2, (yCenter - 10) / 2, 280, 105);
		switch(this.selectedItem) {
			case 0: this.racialAbilitiesList.drawScreen(mx, my, f); break;
			case 1: this.devilFruitsAbilitiesList.drawScreen(mx, my, f); break;
			default: break;
		}
		
		WyRenderHelper.endGlScissor();
		
		for(int i = 0; i < 8; i++){
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            if(abilityProps.getAbilityFromSlot(i) != null)
            	WyRenderHelper.drawAbilityIcon(WyHelper.getFancyName(abilityProps.getAbilityFromSlot(i).getAttribute().getAbilityTexture()), (xCenter + 25 + (i * 46)) / 2 + 4, yCenter + 49, 16, 16);
        }
		
		if(abilityProps.getRacialAbilities()[0] != null){
			this.mc.getTextureManager().bindTexture(ID.TEXTURE_GUI2);
			GL11.glPushMatrix();
			GL11.glScalef(1, 1.4f, 1);
			this.drawTexturedModalRect(xCenter - 111, (int)(yStart * 0.6f + 27 * 0.6f), selectedItem==0? 150 : 113, 240, 20, 20);
			GL11.glPopMatrix();
			WyRenderHelper.drawAbilityIcon(abilityProps.getRacialAbilities()[0].getAttribute().getAttributeName(), xCenter - 108, yStart + 20, 16, 16);	
		}
		if(props.getUsedFruit() != null && !props.getUsedFruit().toLowerCase().equals("n/a")){
			this.mc.getTextureManager().bindTexture(ID.TEXTURE_GUI2);
			GL11.glPushMatrix();
			GL11.glScalef(1, 1.4f, 1);
			this.drawTexturedModalRect(xCenter - 111, (int)(yStart * 0.6f + 54 * 0.6f), selectedItem==1? 150 : 113, 240, 20, 20);
			GL11.glPopMatrix();
			if(props.hasYamiPower()) {
				WyRenderHelper.drawDevilFruitIcon("yamiyaminomi", xCenter - 108, yStart + 43, 16, 16);
			}else{
				ItemStack df = DevilFruitsHelper.getDevilFruitItem(props.getUsedFruit());
				WyRenderHelper.drawDevilFruitIcon(df.getUnlocalizedName().replace("item.", ""), 
						xCenter - 108, yStart + 43, 16, 16);
			}
		}
		
		this.drawScreen(mx, my, f);
	}

	public void actionPerformed(GuiButton button) {
		if (button.id >= 0 && button.id < 8) {
			if(this.selectedSlot == button.id) {
	    		abilityProps.setAbilityInSlot(this.selectedSlot, null);
				WyNetworkHelper.sendToServer(new PacketAbilitySync(abilityProps));
			}else {
				this.selectedSlot = button.id;
			}
		}else {
			switch(button.id) {
				case 8: this.selectedItem = 0;
					break;
				case 9: this.selectedItem = 1;
					break;
				default: break;
			}
		}
	}

	@Override
	public String getPageName() {
		return "Abilities";
	}
}
