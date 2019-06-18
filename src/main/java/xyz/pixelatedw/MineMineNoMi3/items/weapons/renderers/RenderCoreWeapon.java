package xyz.pixelatedw.MineMineNoMi3.items.weapons.renderers;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;

public class RenderCoreWeapon implements IItemRenderer
{

	public RenderCoreWeapon()
	{

	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type)
		{

			case EQUIPPED:
				return true;
			case EQUIPPED_FIRST_PERSON:
				return true;
			default:
				return false;

		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data)
	{
		GL11.glPushMatrix();

		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();

		EntityLivingBase entity = data[1] != null ? (EntityLivingBase)data[1] : null;
		if(entity == null)
			return;
		
		ExtendedEntityData props = ExtendedEntityData.get(entity);
		IIcon iicon = entity.getItemIcon(itemStack, 0);

		if (iicon == null)
		{
			GL11.glPopMatrix();
			return;
		}

		texturemanager.bindTexture(texturemanager.getResourceLocation(itemStack.getItemSpriteNumber()));
		TextureUtil.func_152777_a(false, false, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		float f4 = 0.0F;
		float f5 = 0.3F;

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-f4, -f5, 0.0F);
		float f6 = 1.8F;
		GL11.glScalef(f6, f6, f6);
		GL11.glRotatef(-5.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(345.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.47F, 0.15F, 0.0F);

		boolean isFirstPerson = false;
		if (data[1] != null && data[1] instanceof EntityPlayer)
		{
			if (!((EntityPlayer) data[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F)))
			{
				GL11.glTranslatef(-0.01F, 0.01F, 0F);
			}
			else
			{
				GL11.glTranslatef(0.15F, 0.11F, -0.05F);
				GL11.glScalef(0.6F, 0.6F, 0.6F);
				isFirstPerson = true;
			}
		}
		else
		{
			GL11.glTranslatef(0.05F, -0.05F, 0.0F);
		}

		RenderManager.instance.itemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);	
		GL11.glPopMatrix();
	}
}