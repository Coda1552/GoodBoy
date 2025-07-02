package codyhuh.goodboy.common.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.Map;

public class ChihuahuasMightEffect extends MobEffect {

    public ChihuahuasMightEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xc72f49);
    }

    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        double per = 0.1D * (amplifier + 1);

        if (amplifier == 0) {
            return per + 0.1D;
        }
        else {
            return per + 0.05D;
        }
    }
}
