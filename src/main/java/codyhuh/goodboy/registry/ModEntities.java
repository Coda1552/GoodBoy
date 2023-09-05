package codyhuh.goodboy.registry;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.common.entities.Chihuahua;
import codyhuh.goodboy.common.entities.Retriever;
import codyhuh.goodboy.common.entities.item.DogToy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GoodBoy.MOD_ID);

    public static final RegistryObject<EntityType<Retriever>> RETRIEVER = ENTITY_TYPES.register("retriever", () -> EntityType.Builder.of(Retriever::new, MobCategory.CREATURE).sized(0.7F, 0.9F).build("retiever"));
    // todo - remove noSummon()
    public static final RegistryObject<EntityType<Chihuahua>> CHIHUAHUA = ENTITY_TYPES.register("chihuahua", () -> EntityType.Builder.of(Chihuahua::new, MobCategory.CREATURE).noSummon().sized(0.3F, 0.4F).build("chihuahua"));

    public static final RegistryObject<EntityType<DogToy>> DOG_TOY = ENTITY_TYPES.register("dog_toy", () -> EntityType.Builder.<DogToy>of(DogToy::new, MobCategory.MISC).sized(0.25F, 0.25F).build("dog_toy"));
}
