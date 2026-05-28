package com.example.yumemod.entity;// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.example.yumemod.animation.JellyfishAnimation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Jellyfish<T extends JellyfishEntity> extends HierarchicalModel<T>{
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("yumemod", "jellyfish"), "main");
	private final ModelPart root;
	private final ModelPart total;
	private final ModelPart body;
	private final ModelPart nematocysts;
	private final ModelPart bone4;
	private final ModelPart bone3;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart bone7;
	private final ModelPart bone8;
	private final ModelPart bone9;
	private final ModelPart bone10;

	public Jellyfish(ModelPart root) {
		this.total = root.getChild("total");
		this.body = this.total.getChild("body");
		this.nematocysts = this.total.getChild("nematocysts");
		this.bone4 = this.nematocysts.getChild("bone4");
		this.bone3 = this.nematocysts.getChild("bone3");
		this.bone5 = this.nematocysts.getChild("bone5");
		this.bone6 = this.nematocysts.getChild("bone6");
		this.bone7 = this.nematocysts.getChild("bone7");
		this.bone8 = this.nematocysts.getChild("bone8");
		this.bone9 = this.nematocysts.getChild("bone9");
		this.bone10 = this.nematocysts.getChild("bone10");
		this.root=root;
	}
	@Override
	public ModelPart root(){
		return this.root;
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition total = partdefinition.addOrReplaceChild("total", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = total.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-3.0F, -2.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(8, 4).addBox(1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 9).addBox(-2.0F, -2.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(2.0F, -2.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition nematocysts = total.addOrReplaceChild("nematocysts", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition bone4 = nematocysts.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bone4.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition bone3 = nematocysts.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, 2.0F));

		PartDefinition cube_r3 = bone3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -1.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, -2.0F, -0.1745F, 0.0F, -0.1745F));

		PartDefinition bone5 = nematocysts.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 1.0F));

		PartDefinition cube_r4 = bone5.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone6 = nematocysts.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = bone6.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition bone7 = nematocysts.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition cube_r6 = bone7.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -1.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.1745F, 0.0F, -0.1745F));

		PartDefinition bone8 = nematocysts.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition cube_r7 = bone8.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, -0.1745F, 0.0F, 0.1745F));

		PartDefinition bone9 = nematocysts.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 1.0F));

		PartDefinition cube_r8 = bone9.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -1.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition bone10 = nematocysts.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r9 = bone10.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -1.0F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.1745F, 0.0F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.jetSwimAnimationState,
				JellyfishAnimation.jet_swim,
				ageInTicks,
				1.0F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		total.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}