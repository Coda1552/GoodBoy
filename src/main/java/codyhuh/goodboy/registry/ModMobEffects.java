package codyhuh.goodboy.registry;

import codyhuh.goodboy.GoodBoy;
import codyhuh.goodboy.common.mobeffects.ChihuahuasMightEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GoodBoy.MOD_ID);

    public static final RegistryObject<MobEffect> CHIHUAHUAS_MIGHT = MOB_EFFECTS.register("chihuahuas_might", () -> new ChihuahuasMightEffect().addAttributeModifier(Attributes.ATTACK_DAMAGE, "8c04f13a-7107-440b-bc34-4781eac8ae7f", 0.0D, AttributeModifier.Operation.MULTIPLY_BASE));
}
