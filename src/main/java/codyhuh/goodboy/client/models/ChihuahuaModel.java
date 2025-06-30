package codyhuh.goodboy.client.models;

import codyhuh.goodboy.common.entities.Chihuahua;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ChihuahuaModel<T extends Chihuahua> extends AgeableHierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart rightBackLeg;
	private final ModelPart leftBackLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;

	public ChihuahuaModel(ModelPart base) {
		super(0.5F, 24.0F);
		this.root = base.getChild("root");
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.tail = body.getChild("tail");
		this.rightBackLeg = body.getChild("r_leg_2");
		this.rightFrontLeg = body.getChild("r_leg_1");
		this.leftBackLeg = body.getChild("l_leg_2");
		this.leftFrontLeg = body.getChild("l_leg_1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.947F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 2.0F));

		PartDefinition cube_r1 = tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.7418F, 0.0F, 0.0F));

		PartDefinition l_leg_2 = body.addOrReplaceChild("l_leg_2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.0F, 1.5F));

		PartDefinition l_leg_1 = body.addOrReplaceChild("l_leg_1", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.0F, -2.5F));

		PartDefinition r_leg_2 = body.addOrReplaceChild("r_leg_2", CubeListBuilder.create().texOffs(9, 21).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 2.0F, 1.5F));

		PartDefinition r_leg_1 = body.addOrReplaceChild("r_leg_1", CubeListBuilder.create().texOffs(14, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 2.0F, -2.5F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.0F, -3.625F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(19, 18).addBox(-4.0F, -9.0F, -1.625F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 12).addBox(1.0F, -9.0F, -1.625F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 21).addBox(-1.0F, -1.0F, -5.625F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -1.375F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);

		if (entity.isInSittingPose()) {
			this.root.y = 21.75F;

			this.head.y = -3.0F;

			this.tail.xRot = 0.872665F;

			this.rightBackLeg.xRot = 0.785398F;
			this.leftBackLeg.xRot = 0.785398F;
			this.rightFrontLeg.xRot = 0.785398F;
			this.leftFrontLeg.xRot = 0.785398F;

			this.rightBackLeg.y = 0.75F;
			this.rightBackLeg.x = -2.0F;
			this.rightBackLeg.z = 1.0F;

			this.rightFrontLeg.y += 0.25F;
			this.rightFrontLeg.z += 2.0F;

			this.leftBackLeg.y = 0.75F;
			this.leftBackLeg.x = 2.0F;
			this.leftBackLeg.z = 1.0F;

			this.leftFrontLeg.y += 0.25F;
			this.leftFrontLeg.z += 2.0F;

			this.body.xRot = -0.785398F;
		}
		else {
			this.rightBackLeg.xRot = Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;
			this.leftBackLeg.xRot = Mth.cos(limbSwing * 0.6F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;

			this.tail.xRot = ageInTicks;
			this.tail.yRot = Mth.cos(limbSwing * 0.6F) * 0.5F * limbSwingAmount;
		}

		if (this.young) this.applyStatic(BABY_TRANSFORM);
	}

	public static final AnimationDefinition BABY_TRANSFORM = AnimationDefinition.Builder.withLength(0.0F)
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.5F, 1.5F, 1.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	@Override
	public ModelPart root() {
		return root;
	}
}