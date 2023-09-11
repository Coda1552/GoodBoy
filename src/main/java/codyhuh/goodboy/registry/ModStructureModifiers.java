package codyhuh.goodboy.registry;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.util.AddSpawnsStructureModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModStructureModifiers {
    public static final DeferredRegister<Codec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, GoodBoy.MOD_ID);

    public static final RegistryObject<Codec<AddSpawnsStructureModifier>> ADD_SPAWNS_MODIFIER = STRUCTURE_MODIFIERS.register("add_spawn", () -> AddSpawnsStructureModifier.CODEC);
}
