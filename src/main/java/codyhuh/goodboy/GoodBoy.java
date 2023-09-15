package codyhuh.goodboy;

import codyhuh.goodboy.common.entities.Chihuahua;
import codyhuh.goodboy.common.entities.Retriever;
import codyhuh.goodboy.common.entities.util.AbstractDog;
import codyhuh.goodboy.registry.ModEntities;
import codyhuh.goodboy.registry.ModItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;

@Mod(GoodBoy.MOD_ID)
public class GoodBoy {
    public static final String MOD_ID = "goodboy";
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(Registry.PROCESSOR_LIST_REGISTRY, new ResourceLocation("minecraft", "empty"));

    public GoodBoy() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModEntities.ENTITY_TYPES.register(bus);
        ModItems.ITEMS.register(bus);

        bus.addListener(this::registerAttributes);
        bus.addListener(this::registerSpawnPlacements);
        forgeBus.addListener(this::addNewVillageBuilding);
    }

    private void registerSpawnPlacements(SpawnPlacementRegisterEvent e) {
        e.register(ModEntities.RETRIEVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractDog::checkDogSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    private void registerAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.RETRIEVER.get(), Retriever.createRetrieverAttributes().build());
        e.put(ModEntities.CHIHUAHUA.get(), Chihuahua.createChihuahuaAttributes().build());
    }

    // todo - fix spawning
    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry, ResourceLocation poolRL, String nbtPieceRL, int weight) {
        Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) return;

        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece);
        }

        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
    }

    public void addNewVillageBuilding(final ServerAboutToStartEvent event) {
        Registry<StructureTemplatePool> templatePoolRegistry = event.getServer().registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).orElseThrow();
        Registry<StructureProcessorList> processorListRegistry = event.getServer().registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY).orElseThrow();

        addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/common/cats"), "goodboy:village/retriever_golden", 5);
        addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/common/cats"), "goodboy:village/retriever_pale", 5);
        addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("minecraft:village/common/cats"), "goodboy:village/retriever_black", 5);
    }
}
