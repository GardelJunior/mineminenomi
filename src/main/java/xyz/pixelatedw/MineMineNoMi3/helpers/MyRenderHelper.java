package xyz.pixelatedw.MineMineNoMi3.helpers;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.network.ForgeNetworkHandler;

@SideOnly(Side.CLIENT)
public class MyRenderHelper {

	public static void bindTexture(ResourceLocation textureResource) {
		Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
	}

	public static void setColor(int argb) {
		byte a = (byte) (argb >> 24 & 0xff);
		byte r = (byte) (argb >> 16 & 0xff);
		byte g = (byte) (argb >> 8 & 0xff);
		byte b = (byte) (argb & 0xff);
		setColor(a, r, g, b);
	}

	public static void resetColor() {
		GL11.glColor4f(1, 1, 1, 1);
	}

	public static void setColor(float r, float g, float b) {
		GL11.glColor3f(r, g, b);
	}

	public static void setColor(float a, float r, float g, float b) {
		GL11.glColor4f(r, g, b, a);
	}

	public static boolean isMouseInside(int mouseX, int mouseY, int xStart, int yStart, int xEnd, int yEnd) {
		return mouseX >= xStart && mouseY >= yStart && mouseX <= xEnd && mouseY <= yEnd;
	}

	public static boolean isMouseInsideRect(int mouseX, int mouseY, int xStart, int yStart, int width, int height) {
		return mouseX >= xStart && mouseY >= yStart && mouseX <= xStart + width && mouseY <= yStart + height;
	}

	public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, float zLevel, int color1,int color2) {
		float f = (float) (color1 >> 24 & 255) / 255.0F;
		float f1 = (float) (color1 >> 16 & 255) / 255.0F;
		float f2 = (float) (color1 >> 8 & 255) / 255.0F;
		float f3 = (float) (color1 & 255) / 255.0F;
		float f4 = (float) (color2 >> 24 & 255) / 255.0F;
		float f5 = (float) (color2 >> 16 & 255) / 255.0F;
		float f6 = (float) (color2 >> 8 & 255) / 255.0F;
		float f7 = (float) (color2 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f1, f2, f3, f);
		tessellator.addVertex((double) x1, (double) y1, (double) zLevel);
		tessellator.addVertex((double) x1, (double) y2, (double) zLevel);
		tessellator.setColorRGBA_F(f5, f6, f7, f4);
		tessellator.addVertex((double) x2, (double) y2, (double) zLevel);
		tessellator.addVertex((double) x2, (double) y1, (double) zLevel);
		tessellator.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void renderCenteredMultilineString(String string, int x, int y, int width, int color, boolean withShadow) {
		renderCenteredMultilineString(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(string, width),x, y, width, color, withShadow);
	}

	public static void renderCenteredMultilineString(List<String> splitedString, int x, int y, int width, int color, boolean withShadow) {
		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		int lastY = y;
		for(int i = 0 ; i < splitedString.size() ; i++) {
			final String line = splitedString.get(i);
			int lineWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(line);
			if(withShadow) {
				fontRenderer.drawStringWithShadow(line, x + (width - lineWidth)/2, lastY , color);
			}else {
				fontRenderer.drawString(line, x + (width - lineWidth)/2, lastY , color);
			}
			lastY += fontRenderer.FONT_HEIGHT + 2;
		}
	}
}
