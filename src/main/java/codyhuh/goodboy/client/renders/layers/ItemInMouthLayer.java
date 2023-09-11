package codyhuh.goodboy.client.renders.layers;

import codyhuh.goodboy.common.entities.Retriever;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
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
public class ItemInMouthLayer<T extends Retriever, M extends HierarchicalModel<T>> extends RenderLayer<T, M> {
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
    protected void renderMouthWithItem(LivingEntity entity, ItemStack item, PoseStack stack, MultiBufferSource buffer, int p_117191_, float yaw, float pitch) {
        if (!item.isEmpty()) {
            stack.pushPose();

            ModelPart modelpart = this.getParentModel().getAnyDescendantWithName("mouth").get();
            ModelPart.Cube cube = modelpart.getRandomCube(entity.getRandom());

            modelpart.translateAndRotate(stack);

            float x = Mth.lerp(-3.5F, cube.minX, 1.0F) / 16.0F;
            float y = Mth.lerp(11.0F, cube.minY, 1.0F) / 16.0F;
            float z = Mth.lerp(-10.0F, cube.minZ, 1.0F) / 16.0F;

            stack.translate(x, y, z);
            stack.mulPose(Vector3f.YP.rotationDegrees(Mth.wrapDegrees(yaw)));
            stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            stack.mulPose(Vector3f.ZP.rotationDegrees(-45.0F));

            this.itemInHandRenderer.renderItem(entity, item, ItemTransforms.TransformType.GROUND, false, stack, buffer, p_117191_);
            stack.popPose();
        }
    }
}