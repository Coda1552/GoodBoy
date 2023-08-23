package codyhuh.goodboy;

import codyhuh.goodboy.common.entities.Chihuahua;
import codyhuh.goodboy.common.entities.Retriever;
import codyhuh.goodboy.registry.ModEntities;
import codyhuh.goodboy.registry.ModItems;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GoodBoy.MOD_ID)
public class GoodBoy {
    public static final String MOD_ID = "goodboy";

    public GoodBoy() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.ENTITY_TYPES.register(bus);
        ModItems.ITEMS.register(bus);

        bus.addListener(this::registerAttributes);
    }

    private void registerAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.RETRIEVER.get(), Retriever.createRetrieverAttributes().build());
        e.put(ModEntities.CHIHUAHUA.get(), Chihuahua.createChihuahuaAttributes().build());
    }
}
