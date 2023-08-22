package codyhuh.goodboy.common.items;

import codyhuh.goodboy.common.entities.item.DogToy;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class DogToyItem extends Item {

    public DogToyItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean useOnRelease(ItemStack p_41464_) {
        return super.useOnRelease(p_41464_);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int time) {
        ItemStack itemstack = user.getItemInHand(user.getUsedItemHand());

        if (user instanceof Player player) {
            int i = this.getUseDuration(stack) - time;

            if (i < 0) return;

            float f = getPowerForTime(i);

            if (!(f < 0.1F)) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (!level.isClientSide) {
                    DogToy toy = new DogToy(level, player);

                    toy.setItem(itemstack);
                    toy.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    level.addFreshEntity(toy);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);

        return super.use(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 200;
    }

    public UseAnim getUseAnimation(ItemStack p_43105_) {
        return UseAnim.BOW;
    }

    public static float getPowerForTime(int power) {
        float f = (float)power / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
}
