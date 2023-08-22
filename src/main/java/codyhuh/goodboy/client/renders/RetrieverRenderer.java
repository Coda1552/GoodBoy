package codyhuh.goodboy.client.renders;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.client.ModModelLayers;
import codyhuh.goodboy.client.models.RetrieverModel;
import codyhuh.goodboy.client.renders.layers.ItemInMouthLayer;
import codyhuh.goodboy.common.entities.Retriever;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class RetrieverRenderer extends MobRenderer<Retriever, RetrieverModel<Retriever>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/retriever/retriever_golden.png"));
        hashMap.put(1, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/retriever/retriever_black.png"));
        hashMap.put(2, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/retriever/retriever_pale.png"));
    });

    public RetrieverRenderer(EntityRendererProvider.Context context) {
        super(context, new RetrieverModel<>(context.bakeLayer(ModModelLayers.RETRIEVER)), 0.6F);
        addLayer(new ItemInMouthLayer<>(this, context.getItemInHandRenderer()));
    }

    public float getBob(Retriever p_116528_, float p_116529_) {
        return p_116528_.getTailAngle();
    }

    @Override
    public ResourceLocation getTextureLocation(Retriever p_114482_) {
        return TEXTURES.getOrDefault(p_114482_.getVariant(), TEXTURES.get(0));
    }
}
