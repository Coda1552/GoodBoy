package codyhuh.goodboy.client.renders.layers;

import codyhuh.goodboy.common.entities.Retriever;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemInMouthLayer<T extends Retriever, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public ItemInMouthLayer(RenderLayerParent<T, M> p_234846_, ItemInHandRenderer p_234847_) {
        super(p_234846_);
        this.itemInHandRenderer = p_234847_;
    }

    public void render(PoseStack poseStack, MultiBufferSource p_117205_, int p_117206_, T p_117207_, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) {
        ItemStack stack = p_117207_.getItem();

        if (!stack.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.1F, 0.0F, -0.05F);
            renderMouthWithItem(p_117207_, stack, poseStack, p_117205_, p_117206_, p_117212_, p_117213_);

            poseStack.popPose();
        }
    }

    // todo - fix item position/rotations
    protected void renderMouthWithItem(LivingEntity p_117185_, ItemStack p_117186_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_, float yaw, float pitch) {
        if (!p_117186_.isEmpty()) {
            p_117189_.pushPose();
            p_117189_.translate(0, 0.7, -0.2);
            p_117189_.mulPose(Vector3f.XP.rotationDegrees(Mth.wrapDegrees(pitch - 90.0F)));
            p_117189_.mulPose(Vector3f.ZP.rotationDegrees(45.0F));
            p_117189_.mulPose(Vector3f.YP.rotationDegrees(Mth.wrapDegrees(yaw + 180.0F)));
            p_117189_.translate(-0.2, 0.175, 0);
            this.itemInHandRenderer.renderItem(p_117185_, p_117186_, ItemTransforms.TransformType.GROUND, false, p_117189_, p_117190_, p_117191_);
            p_117189_.popPose();
        }
    }
}