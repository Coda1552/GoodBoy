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
import net.minecraft.world.entity.EquipmentSlot;
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
        ItemStack stack = p_117207_.getMainHandItem();

        if (!stack.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.1F, 0.0F, -0.05F);
            renderArmWithItem(p_117207_, stack, poseStack, p_117205_, p_117206_);

            poseStack.popPose();
        }
    }

    protected void renderArmWithItem(LivingEntity p_117185_, ItemStack p_117186_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_) {
        if (!p_117186_.isEmpty()) {
            p_117189_.pushPose();
            p_117189_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            p_117189_.mulPose(Vector3f.ZP.rotationDegrees(45.0F));
            p_117189_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            p_117189_.translate(-0.3F, 0.3F, -1.4F);
            this.itemInHandRenderer.renderItem(p_117185_, p_117186_, ItemTransforms.TransformType.GROUND, false, p_117189_, p_117190_, p_117191_);
            p_117189_.popPose();
        }
    }
}