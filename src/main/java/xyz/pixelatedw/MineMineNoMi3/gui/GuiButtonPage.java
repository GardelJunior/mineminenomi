package xyz.pixelatedw.MineMineNoMi3.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import xyz.pixelatedw.MineMineNoMi3.ID;

class GuiButtonPage extends GuiButton {

	public boolean active;
	
	public GuiButtonPage(int id, int x, int y, int width, int height, String text) {
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
