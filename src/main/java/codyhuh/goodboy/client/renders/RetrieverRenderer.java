package codyhuh.goodboy.client.renders;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.client.ModModelLayers;
import codyhuh.goodboy.client.models.RetrieverModel;
import codyhuh.goodboy.client.renders.layers.DogCollarLayer;
import codyhuh.goodboy.client.renders.layers.ItemInMouthLayer;
import codyhuh.goodboy.common.entities.Retriever;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class RetrieverRenderer extends MobRenderer<Retriever, RetrieverModel<Retriever>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/retriever/golden.png"));
        hashMap.put(1, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/retriever/black.png"));
        hashMap.put(2, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/retriever/pale.png"));
    });

    public RetrieverRenderer(EntityRendererProvider.Context context) {
        super(context, new RetrieverModel<>(context.bakeLayer(ModModelLayers.RETRIEVER)), 0.6F);
        addLayer(new ItemInMouthLayer<>(this, context.getItemInHandRenderer()));
        addLayer(new DogCollarLayer<>(this, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/retriever/collar.png")));
    }

    public float getBob(Retriever p_116528_, float p_116529_) {
        return p_116528_.getTailAngle();
    }

    @Override
    public void render(Retriever entity, float p_115456_, float p_115457_, PoseStack stack, MultiBufferSource p_115459_, int p_115460_) {
        if (entity.isBaby()) {
            stack.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(entity, p_115456_, p_115457_, stack, p_115459_, p_115460_);
    }

    @Override
    public ResourceLocation getTextureLocation(Retriever p_114482_) {
        return TEXTURES.getOrDefault(p_114482_.getVariant(), TEXTURES.get(0));
    }
}
