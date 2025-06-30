package codyhuh.goodboy.registry;

import codyhuh.goodboy.GoodBoy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GoodBoy.MOD_ID);

    public static final RegistryObject<SoundEvent> CHIHUAHUA_GROWL = add("entity.chihuahua.growl");
    public static final RegistryObject<SoundEvent> CHIHUAHUA_BARK = add("entity.chihuahua.bark");

    private static RegistryObject<SoundEvent> add(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GoodBoy.MOD_ID, name)));
    }
}
