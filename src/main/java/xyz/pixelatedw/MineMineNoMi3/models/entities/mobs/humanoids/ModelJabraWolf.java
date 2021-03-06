package xyz.pixelatedw.MineMineNoMi3.models.entities.mobs.humanoids;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelJabraWolf extends ModelBiped
{
	// fields
	ModelRenderer head;
	ModelRenderer muzzle;
	ModelRenderer hairclip_1;
	ModelRenderer hair_1;
	ModelRenderer hairclip_2;
	ModelRenderer hair_2;
	ModelRenderer hairclip_3;
	ModelRenderer hair_3;
	ModelRenderer hairclip_4;
	ModelRenderer hair_4;
	ModelRenderer hairclip_5;
	ModelRenderer hair_5;
	ModelRenderer hairclip_6;
	ModelRenderer hair_6;
	ModelRenderer hairclip_7;
	ModelRenderer hair_7;
	ModelRenderer body_1;
	ModelRenderer body_2;
	ModelRenderer body_3;
	ModelRenderer right_beard;
	ModelRenderer left_beard;
	ModelRenderer right_foot_1;
	ModelRenderer right_foot_2;
	ModelRenderer leftl_foot_1;
	ModelRenderer left_foot_2;
	ModelRenderer tail_1;
	ModelRenderer tail_2;
	ModelRenderer tail_3;
	ModelRenderer right_arm_1;
	ModelRenderer right_arm_2;
	ModelRenderer right_hand_1;
	ModelRenderer right_hand_2;
	ModelRenderer left_arm_1;
	ModelRenderer left_arm_2;
	ModelRenderer left_hand_1;
	ModelRenderer left_hand_2;
	ModelRenderer right_ear;
	ModelRenderer left_ear;
	ModelRenderer right_foot_3;
	ModelRenderer left_foot_3;
	ModelRenderer right_foot_4;
	ModelRenderer left_foot_4;

	public ModelJabraWolf()
	{
		textureWidth = 128;
		textureHeight = 64;

		this.bipedHeadwear = new ModelRenderer(this, 1, 1);
		this.bipedHeadwear.addBox(0F, 0F, 0F, 0, 0, 0);
		this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		this.bipedHeadwear.setTextureSize(64, 32);
		this.bipedHeadwear.mirror = true;
		setRotation(this.bipedHeadwear, 0F, 0F, 0F);
		this.bipedHead = new ModelRenderer(this, 1, 1);
		this.bipedHead.addBox(0F, 0F, 0F, 0, 0, 0);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		this.bipedHead.setTextureSize(64, 32);
		this.bipedHead.mirror = true;
		setRotation(this.bipedHead, 0F, 0F, 0F);
		
		head = new ModelRenderer(this, 80, 0);
		head.addBox(0F, 0F, 0F, 5, 5, 6);
		head.setRotationPoint(-2.5F, -10F, -1F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		muzzle = new ModelRenderer(this, 65, 0);
		muzzle.addBox(0F, 0F, 0F, 3, 2, 4);
		muzzle.setRotationPoint(-1.5F, -7F, -5F);
		muzzle.setTextureSize(128, 64);
		muzzle.mirror = true;
		setRotation(muzzle, 0F, 0F, 0F);
		hairclip_1 = new ModelRenderer(this, 34, 0);
		hairclip_1.addBox(0F, 0F, 0F, 1, 1, 1);
		hairclip_1.setRotationPoint(-0.5F, -8.5F, 5F);
		hairclip_1.setTextureSize(128, 64);
		hairclip_1.mirror = true;
		setRotation(hairclip_1, -0.2617994F, 0F, 0F);
		hair_1 = new ModelRenderer(this, 34, 4);
		hair_1.addBox(0F, 0F, 0F, 2, 2, 2);
		hair_1.setRotationPoint(-1F, -8.7F, 5.7F);
		hair_1.setTextureSize(128, 64);
		hair_1.mirror = true;
		setRotation(hair_1, -0.418879F, 0F, 0F);
		hairclip_2 = new ModelRenderer(this, 34, 0);
		hairclip_2.addBox(0F, 0F, 0F, 1, 1, 1);
		hairclip_2.setRotationPoint(-0.5F, -7.3F, 7.2F);
		hairclip_2.setTextureSize(128, 64);
		hairclip_2.mirror = true;
		setRotation(hairclip_2, -0.837758F, 0F, 0F);
		hair_2 = new ModelRenderer(this, 34, 4);
		hair_2.addBox(0F, 0F, 0F, 2, 2, 2);
		hair_2.setRotationPoint(-1F, -7F, 8.5F);
		hair_2.setTextureSize(128, 64);
		hair_2.mirror = true;
		setRotation(hair_2, -1.082104F, 0F, 0F);
		hairclip_3 = new ModelRenderer(this, 34, 0);
		hairclip_3.addBox(0F, 0F, 0F, 1, 1, 1);
		hairclip_3.setRotationPoint(-0.5F, -5.2F, 8.8F);
		hairclip_3.setTextureSize(128, 64);
		hairclip_3.mirror = true;
		setRotation(hairclip_3, -1.047198F, 0F, 0F);
		hair_3 = new ModelRenderer(this, 34, 4);
		hair_3.addBox(0F, 0F, 0F, 2, 2, 2);
		hair_3.setRotationPoint(-1F, -4.5F, 9.8F);
		hair_3.setTextureSize(128, 64);
		hair_3.mirror = true;
		setRotation(hair_3, -1.43117F, 0F, 0F);
		hairclip_4 = new ModelRenderer(this, 34, 0);
		hairclip_4.addBox(0F, 0F, 0F, 1, 1, 1);
		hairclip_4.setRotationPoint(-0.5F, -2.7F, 8.6F);
		hairclip_4.setTextureSize(128, 64);
		hairclip_4.mirror = true;
		setRotation(hairclip_4, 0F, 0F, 0F);
		hair_4 = new ModelRenderer(this, 34, 4);
		hair_4.addBox(0F, 0F, 0F, 2, 2, 2);
		hair_4.setRotationPoint(-1F, -2F, 8.1F);
		hair_4.setTextureSize(128, 64);
		hair_4.mirror = true;
		setRotation(hair_4, 0F, 0F, 0F);
		hairclip_5 = new ModelRenderer(this, 34, 0);
		hairclip_5.addBox(0F, 0F, 0F, 1, 1, 1);
		hairclip_5.setRotationPoint(-0.5F, -0.3F, 8.6F);
		hairclip_5.setTextureSize(128, 64);
		hairclip_5.mirror = true;
		setRotation(hairclip_5, 0F, 0F, 0F);
		hair_5 = new ModelRenderer(this, 34, 4);
		hair_5.addBox(0F, 0F, 0F, 2, 2, 2);
		hair_5.setRotationPoint(-1F, 0.5F, 8.1F);
		hair_5.setTextureSize(128, 64);
		hair_5.mirror = true;
		setRotation(hair_5, 0F, 0F, 0F);
		hairclip_6 = new ModelRenderer(this, 34, 0);
		hairclip_6.addBox(0F, 0F, 0F, 1, 1, 1);
		hairclip_6.setRotationPoint(-0.5F, 2.2F, 8.6F);
		hairclip_6.setTextureSize(128, 64);
		hairclip_6.mirror = true;
		setRotation(hairclip_6, 0F, 0F, 0F);
		hair_6 = new ModelRenderer(this, 34, 4);
		hair_6.addBox(0F, 0F, 0F, 2, 2, 2);
		hair_6.setRotationPoint(-1F, 3F, 8.1F);
		hair_6.setTextureSize(128, 64);
		hair_6.mirror = true;
		setRotation(hair_6, 0F, 0F, 0F);
		hairclip_7 = new ModelRenderer(this, 34, 0);
		hairclip_7.addBox(0F, 0F, 0F, 1, 1, 1);
		hairclip_7.setRotationPoint(-0.5F, 4.7F, 8.6F);
		hairclip_7.setTextureSize(128, 64);
		hairclip_7.mirror = true;
		setRotation(hairclip_7, 0F, 0F, 0F);
		hair_7 = new ModelRenderer(this, 34, 4);
		hair_7.addBox(0F, 0F, 0F, 2, 2, 2);
		hair_7.setRotationPoint(-1F, 5.1F, 9.1F);
		hair_7.setTextureSize(128, 64);
		hair_7.mirror = true;
		setRotation(hair_7, -0.7853982F, 0F, 0F);
		body_1 = new ModelRenderer(this, 0, 0);
		body_1.addBox(0F, 0F, 0F, 9, 17, 5);
		body_1.setRotationPoint(-4.5F, -5F, 0F);
		body_1.setTextureSize(128, 64);
		body_1.mirror = true;
		setRotation(body_1, 0F, 0F, 0F);
		body_2 = new ModelRenderer(this, 0, 46);
		body_2.addBox(0F, 0F, 0F, 9, 12, 5);
		body_2.setRotationPoint(2.5F, -7F, 0F);
		body_2.setTextureSize(128, 64);
		body_2.mirror = true;
		setRotation(body_2, 0F, 0F, 0.5235988F);
		body_3 = new ModelRenderer(this, 0, 28);
		body_3.addBox(0F, 0F, 0F, 9, 12, 5);
		body_3.setRotationPoint(-10.3F, -2.7F, 0F);
		body_3.setTextureSize(128, 64);
		body_3.mirror = true;
		setRotation(body_3, 0F, 0F, -0.5235988F);
		right_beard = new ModelRenderer(this, 44, 0);
		right_beard.addBox(0F, 0F, 0F, 1, 6, 0);
		right_beard.setRotationPoint(-2.5F, -6F, -4.1F);
		right_beard.setTextureSize(128, 64);
		right_beard.mirror = true;
		setRotation(right_beard, 0F, 0F, 0F);
		left_beard = new ModelRenderer(this, 47, 0);
		left_beard.addBox(0F, 0F, 0F, 1, 6, 0);
		left_beard.setRotationPoint(1.5F, -6F, -4.1F);
		left_beard.setTextureSize(128, 64);
		left_beard.mirror = true;
		setRotation(left_beard, 0F, 0F, 0F);
		right_foot_1 = new ModelRenderer(this, 34, 10);
		right_foot_1.addBox(0.5F, 12F, -3.5F, 2, 1, 3);
		right_foot_1.setRotationPoint(-4.5F, 11F, 1.2F);
		right_foot_1.setTextureSize(128, 64);
		right_foot_1.mirror = true;
		setRotation(right_foot_1, 0F, 0F, 0F);
		right_foot_2 = new ModelRenderer(this, 34, 15);
		right_foot_2.addBox(0.5F, 7.5F, 5F, 2, 4, 1);
		right_foot_2.setRotationPoint(-4.5F, 11F, 1.2F);
		right_foot_2.setTextureSize(128, 64);
		right_foot_2.mirror = true;
		setRotation(right_foot_2, -0.5235988F, 0F, 0F);
		leftl_foot_1 = new ModelRenderer(this, 34, 10);
		leftl_foot_1.addBox(0.5F, 12F, -3.5F, 2, 1, 3);
		leftl_foot_1.setRotationPoint(1.5F, 11F, 1.2F);
		leftl_foot_1.setTextureSize(128, 64);
		leftl_foot_1.mirror = true;
		setRotation(leftl_foot_1, 0F, 0F, 0F);
		left_foot_2 = new ModelRenderer(this, 34, 15);
		left_foot_2.addBox(0.5F, 7.5F, 5F, 2, 4, 1);
		left_foot_2.setRotationPoint(1.5F, 11F, 1.2F);
		left_foot_2.setTextureSize(128, 64);
		left_foot_2.mirror = true;
		setRotation(left_foot_2, -0.5235988F, 0F, 0F);
		tail_1 = new ModelRenderer(this, 34, 21);
		tail_1.addBox(0F, 0F, 0F, 3, 3, 15);
		tail_1.setRotationPoint(-1.5F, 8F, 5F);
		tail_1.setTextureSize(128, 64);
		tail_1.mirror = true;
		setRotation(tail_1, -0.6108652F, 0F, 0F);
		tail_2 = new ModelRenderer(this, 34, 40);
		tail_2.addBox(0F, 0F, 0F, 4, 4, 11);
		tail_2.setRotationPoint(-2F, 9.6F, 8F);
		tail_2.setTextureSize(128, 64);
		tail_2.mirror = true;
		setRotation(tail_2, -0.6108652F, 0F, 0F);
		tail_3 = new ModelRenderer(this, 71, 21);
		tail_3.addBox(0F, 0F, 0F, 5, 5, 9);
		tail_3.setRotationPoint(-2.5F, 9.8F, 9F);
		tail_3.setTextureSize(128, 64);
		tail_3.mirror = true;
		setRotation(tail_3, -0.6108652F, 0F, 0F);
		right_arm_1 = new ModelRenderer(this, 103, 0);
		right_arm_1.addBox(0F, 0F, 0F, 3, 7, 3);
		right_arm_1.setRotationPoint(-9F, -1F, 1F);
		right_arm_1.setTextureSize(128, 64);
		right_arm_1.mirror = true;
		setRotation(right_arm_1, 0F, 0F, 0.1745329F);
		right_arm_2 = new ModelRenderer(this, 103, 0);
		right_arm_2.addBox(-2.3F, 7F, 0F, 3, 7, 3);
		right_arm_2.setRotationPoint(-9F, -1F, 1F);
		right_arm_2.setTextureSize(128, 64);
		right_arm_2.mirror = true;
		setRotation(right_arm_2, 0F, 0F, -0.1745329F);
		right_hand_1 = new ModelRenderer(this, 52, 11);
		right_hand_1.addBox(-2.3F, 14F, 1F, 3, 3, 1);
		right_hand_1.setRotationPoint(-9F, -1F, 1F);
		right_hand_1.setTextureSize(128, 64);
		right_hand_1.mirror = true;
		setRotation(right_hand_1, 0F, 0F, -0.1745329F);
		right_hand_2 = new ModelRenderer(this, 55, 16);
		right_hand_2.addBox(-5F, 13F, 1F, 1, 2, 1);
		right_hand_2.setRotationPoint(-9F, -1F, 1F);
		right_hand_2.setTextureSize(128, 64);
		right_hand_2.mirror = true;
		setRotation(right_hand_2, 0F, 0F, -0.5235988F);
		left_arm_1 = new ModelRenderer(this, 103, 0);
		left_arm_1.addBox(0F, 0F, 0F, 3, 7, 3);
		left_arm_1.setRotationPoint(6F, 0F, 1F);
		left_arm_1.setTextureSize(128, 64);
		left_arm_1.mirror = true;
		setRotation(left_arm_1, 0F, 0F, -0.1745329F);
		left_arm_2 = new ModelRenderer(this, 103, 0);
		left_arm_2.addBox(2.3F, 6F, 0F, 3, 7, 3);
		left_arm_2.setRotationPoint(6F, 0F, 1F);
		left_arm_2.setTextureSize(128, 64);
		left_arm_2.mirror = true;
		setRotation(left_arm_2, 0F, 0F, 0.1745329F);
		left_hand_1 = new ModelRenderer(this, 52, 11);
		left_hand_1.addBox(2.3F, 13F, 1F, 3, 3, 1);
		left_hand_1.setRotationPoint(6F, 0F, 1F);
		left_hand_1.setTextureSize(128, 64);
		left_hand_1.mirror = true;
		setRotation(left_hand_1, 0F, 0F, 0.1745329F);
		left_hand_2 = new ModelRenderer(this, 55, 16);
		left_hand_2.addBox(6.3F, 11F, 1F, 1, 2, 1);
		left_hand_2.setRotationPoint(6F, 0F, 1F);
		left_hand_2.setTextureSize(128, 64);
		left_hand_2.mirror = true;
		setRotation(left_hand_2, 0F, 0F, 0.5235988F);
		right_ear = new ModelRenderer(this, 65, 16);
		right_ear.addBox(0F, 0F, 0F, 1, 2, 0);
		right_ear.setRotationPoint(-3F, -11.5F, 0F);
		right_ear.setTextureSize(128, 64);
		right_ear.mirror = true;
		setRotation(right_ear, 0F, 0F, -0.3490659F);
		left_ear = new ModelRenderer(this, 65, 16);
		left_ear.addBox(0F, 0F, 0F, 1, 2, 0);
		left_ear.setRotationPoint(2F, -11.7F, 0F);
		left_ear.setTextureSize(128, 64);
		left_ear.mirror = true;
		setRotation(left_ear, 0F, 0F, 0.3490659F);
		right_foot_3 = new ModelRenderer(this, 41, 15);
		right_foot_3.addBox(0.5F, 7F, -1F, 2, 3, 2);
		right_foot_3.setRotationPoint(-4.5F, 11F, 1.2F);
		right_foot_3.setTextureSize(128, 64);
		right_foot_3.mirror = true;
		setRotation(right_foot_3, 0.1396263F, 0F, 0F);
		left_foot_3 = new ModelRenderer(this, 41, 15);
		left_foot_3.addBox(0.5F, 7F, -1F, 2, 3, 2);
		left_foot_3.setRotationPoint(1.5F, 11F, 1.2F);
		left_foot_3.setTextureSize(128, 64);
		left_foot_3.mirror = true;
		setRotation(left_foot_3, 0.1396263F, 0F, 0F);
		right_foot_4 = new ModelRenderer(this, 52, 0);
		right_foot_4.addBox(0F, 0F, 0F, 3, 7, 3);
		right_foot_4.setRotationPoint(-4.5F, 11F, 1.2F);
		right_foot_4.setTextureSize(128, 64);
		right_foot_4.mirror = true;
		setRotation(right_foot_4, -0.1047198F, 0F, 0F);
		left_foot_4 = new ModelRenderer(this, 52, 0);
		left_foot_4.addBox(0F, 0F, 0F, 3, 7, 3);
		left_foot_4.setRotationPoint(1.5F, 11F, 1.2F);
		left_foot_4.setTextureSize(128, 64);
		left_foot_4.mirror = true;
		setRotation(left_foot_4, -0.1047198F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);

		this.bipedLeftLeg.isHidden = true;
		this.bipedRightLeg.isHidden = true;
		this.bipedLeftArm.isHidden = true;
		this.bipedRightArm.isHidden = true;
		this.bipedBody.isHidden = true;
		this.bipedHead.isHidden = true;
		
		// int i = (int) (MathHelper.cos(f * 0.6662F) * 1.4F * f1);

		/*
		 * setRotation(right_foot_4, 6.0F, 0F, 0F); setRotation(right_foot_3,
		 * -6.31F, 0F, 0F); setRotation(right_foot_2, -6.8F, 0F, 0F);
		 * 
		 * setRotation(left_foot_4, 6.0F, 0F, 0F); setRotation(left_foot_3,
		 * -6.31F, 0F, 0F); setRotation(left_foot_2, -6.8F, 0F, 0F);
		 */

		head.render(f5);
		muzzle.render(f5);
		hairclip_1.render(f5);
		hair_1.render(f5);
		hairclip_2.render(f5);
		hair_2.render(f5);
		hairclip_3.render(f5);
		hair_3.render(f5);
		hairclip_4.render(f5);
		hair_4.render(f5);
		hairclip_5.render(f5);
		hair_5.render(f5);
		hairclip_6.render(f5);
		hair_6.render(f5);
		hairclip_7.render(f5);
		hair_7.render(f5);
		body_1.render(f5);
		body_2.render(f5);
		body_3.render(f5);
		right_beard.render(f5);
		left_beard.render(f5);
		right_foot_1.render(f5);
		right_foot_2.render(f5);
		leftl_foot_1.render(f5);
		left_foot_2.render(f5);
		tail_1.render(f5);
		tail_2.render(f5);
		tail_3.render(f5);
		right_arm_1.render(f5);
		right_arm_2.render(f5);
		right_hand_1.render(f5);
		right_hand_2.render(f5);
		left_arm_1.render(f5);
		left_arm_2.render(f5);
		left_hand_1.render(f5);
		left_hand_2.render(f5);
		right_ear.render(f5);
		left_ear.render(f5);
		right_foot_3.render(f5);
		left_foot_3.render(f5);
		right_foot_4.render(f5);
		left_foot_4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);

		leftl_foot_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		left_foot_2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1 + 5.8F;
		left_foot_3.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1 + 6.31F;
		left_foot_4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1 + 6.1F;

		left_arm_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		left_arm_2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		left_hand_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		left_hand_2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;

		right_foot_1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		right_foot_2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1 + 5.8F;
		right_foot_3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1 + 6.31F;
		right_foot_4.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1 + 6.1F;

		right_arm_1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		right_arm_2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		right_hand_1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		right_hand_2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;

	}

}
