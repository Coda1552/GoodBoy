package codyhuh.goodboy.client;

import codyhuh.goodboy.GoodBoy;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation RETRIEVER = create("retriever");
    public static final ModelLayerLocation CHIHUAHUA = create("chihuahua");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(new ResourceLocation(GoodBoy.MOD_ID, name), "main");
    }
}
