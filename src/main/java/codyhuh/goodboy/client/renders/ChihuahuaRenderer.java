package codyhuh.goodboy.client.renders;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.client.ModModelLayers;
import codyhuh.goodboy.client.models.ChihuahuaModel;
import codyhuh.goodboy.client.renders.layers.DogCollarLayer;
import codyhuh.goodboy.common.entities.Chihuahua;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ChihuahuaRenderer extends MobRenderer<Chihuahua, ChihuahuaModel<Chihuahua>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/chihuahua/brown.png"));
        hashMap.put(1, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/chihuahua/black.png"));
    });

    public ChihuahuaRenderer(EntityRendererProvider.Context context) {
        super(context, new ChihuahuaModel<>(context.bakeLayer(ModModelLayers.CHIHUAHUA)), 0.6F);
        addLayer(new DogCollarLayer<>(this, new ResourceLocation(GoodBoy.MOD_ID, "textures/entity/chihuahua/collar.png")));
        shadowRadius = 0.25F;
    }

    public float getBob(Chihuahua p_116528_, float p_116529_) {
        return p_116528_.getTailAngle();
    }

    @Override
    public ResourceLocation getTextureLocation(Chihuahua p_114482_) {
        return TEXTURES.getOrDefault(p_114482_.getVariant(), TEXTURES.get(0));
    }
}
