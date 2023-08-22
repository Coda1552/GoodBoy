package codyhuh.goodboy.client;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.client.models.RetrieverModel;
import codyhuh.goodboy.client.renders.RetrieverRenderer;
import codyhuh.goodboy.registry.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GoodBoy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(ModModelLayers.RETRIEVER, RetrieverModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.RETRIEVER.get(), RetrieverRenderer::new);
        e.registerEntityRenderer(ModEntities.DOG_TOY.get(), ThrownItemRenderer::new);
    }
}
