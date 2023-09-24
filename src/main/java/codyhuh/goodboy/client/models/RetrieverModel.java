package codyhuh.goodboy.client.models;

import codyhuh.goodboy.common.entities.Retriever;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class RetrieverModel<T extends Retriever> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart rightBackLeg;
	private final ModelPart leftBackLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart leftEar;
	private final ModelPart rightEar;

	public RetrieverModel(ModelPart base) {
		//super(true, 12.0F, 1.5F);
		this.root = base.getChild("root");
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.tail = body.getChild("tail");
		this.rightBackLeg = body.getChild("r_leg_2");
		this.rightFrontLeg = body.getChild("r_leg_1");
		this.leftBackLeg = body.getChild("l_leg2");
		this.leftFrontLeg = body.getChild("l_leg1");
		this.leftEar = head.getChild("l_ear");
		this.rightEar = head.getChild("r_ear");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(25, 0).addBox(-4.0F, -5.0F, -3.0F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.0F, -5.0F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(1, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.5F, -4.5F));

		PartDefinition r_ear = head.addOrReplaceChild("r_ear", CubeListBuilder.create(), PartPose.offset(-4.0F, -5.0F, 0.0F));

		PartDefinition cube_r1 = r_ear.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 34).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition l_ear = head.addOrReplaceChild("l_ear", CubeListBuilder.create(), PartPose.offset(3.0F, -5.0F, 0.0F));

		PartDefinition cube_r2 = l_ear.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(30, 34).mirror().addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(0, 45).addBox(-3.0F, 3.0F, -6.0F, 6.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 21).addBox(-1.0F, 3.0F, 0.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 21).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 7.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition r_leg_1 = body.addOrReplaceChild("r_leg_1", CubeListBuilder.create().texOffs(0, 21).addBox(-0.75F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, -4.0F));

		PartDefinition r_leg_2 = body.addOrReplaceChild("r_leg_2", CubeListBuilder.create().texOffs(22, 34).addBox(-0.75F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 3.0F, 6.0F));

		PartDefinition l_leg2 = body.addOrReplaceChild("l_leg2", CubeListBuilder.create().texOffs(22, 21).addBox(-1.25F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 3.0F, 6.0F));

		PartDefinition l_leg1 = body.addOrReplaceChild("l_leg1", CubeListBuilder.create().texOffs(14, 21).addBox(-1.25F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 3.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);

		this.body.y = 0.0F;
		this.head.y = -4.0F;

		if (entity.isInSittingPose()) {
			this.tail.xRot = -0.5F;
			this.tail.yRot = 0.0F;

			this.rightBackLeg.xRot = -1.5708F;
			this.leftBackLeg.xRot = -1.5708F;
			this.rightFrontLeg.xRot = -1.5708F;
			this.leftFrontLeg.xRot = -1.5708F;

			this.rightBackLeg.yRot = 0.5708F;
			this.leftBackLeg.yRot = -0.5708F;
			this.rightFrontLeg.yRot = 0.15F;
			this.leftFrontLeg.yRot = -0.15F;

			this.body.y = 6.0F;
			this.head.y = 2.0F;
		}
		else {
			this.rightBackLeg.xRot = Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;
			this.leftBackLeg.xRot = Mth.cos(limbSwing * 0.6F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;

			this.rightBackLeg.yRot = 0.0F;
			this.leftBackLeg.yRot = 0.0F;
			this.rightFrontLeg.yRot = 0.0F;
			this.leftFrontLeg.yRot = 0.0F;

			this.tail.xRot = ageInTicks;
			this.tail.yRot = Mth.cos(limbSwing * 0.6F) * 0.5F * limbSwingAmount;
		}
	}

/*
	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(body);
	}
*/

	@Override
	public ModelPart root() {
		return root;
	}
}