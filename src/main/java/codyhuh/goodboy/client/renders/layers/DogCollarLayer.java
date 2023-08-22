package codyhuh.goodboy.client.renders.layers;

import codyhuh.goodboy.common.entities.util.AbstractDog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class DogCollarLayer<T extends AbstractDog, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ResourceLocation collarTexture;

    public DogCollarLayer(RenderLayerParent<T, M> p_117707_, ResourceLocation collarLocation) {
        super(p_117707_);
        this.collarTexture = collarLocation;
    }

    public void render(PoseStack p_117720_, MultiBufferSource p_117721_, int p_117722_, T entity, float p_117724_, float p_117725_, float p_117726_, float p_117727_, float p_117728_, float p_117729_) {
        if (entity.isTame() && !entity.isInvisible()) {
            float[] afloat = entity.getCollarColor().getTextureDiffuseColors();
            renderColoredCutoutModel(this.getParentModel(), collarTexture, p_117720_, p_117721_, p_117722_, entity, afloat[0], afloat[1], afloat[2]);
        }
    }
}
