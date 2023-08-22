package codyhuh.goodboy.registry;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.common.items.DogToyItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GoodBoy.MOD_ID);

    public static final RegistryObject<Item> RETRIEVER_SPAWN_EGG = ITEMS.register("retriever_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.RETRIEVER, 0xf3dc7f, 0xc99b4e, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DOG_TOY = ITEMS.register("dog_toy", () -> new DogToyItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
}

