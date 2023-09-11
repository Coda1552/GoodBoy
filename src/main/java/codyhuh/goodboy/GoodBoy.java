package codyhuh.goodboy;

import codyhuh.goodboy.common.entities.Chihuahua;
import codyhuh.goodboy.common.entities.Retriever;
import codyhuh.goodboy.common.entities.util.AbstractDog;
import codyhuh.goodboy.registry.ModEntities;
import codyhuh.goodboy.registry.ModItems;
import codyhuh.goodboy.registry.ModStructureModifiers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
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
        ModStructureModifiers.STRUCTURE_MODIFIERS.register(bus);

        bus.addListener(this::registerAttributes);
        bus.addListener(this::registerSpawnPlacements);
    }

    private void registerSpawnPlacements(SpawnPlacementRegisterEvent e) {
        e.register(ModEntities.RETRIEVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractDog::checkDogSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    private void registerAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.RETRIEVER.get(), Retriever.createRetrieverAttributes().build());
        e.put(ModEntities.CHIHUAHUA.get(), Chihuahua.createChihuahuaAttributes().build());
    }
}
