package codyhuh.goodboy.common.entities.goals;

import codyhuh.goodboy.common.entities.Retriever;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class TamedRetrieveToyGoal extends Goal {
    private final Retriever mob;

    public TamedRetrieveToyGoal(Retriever mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = mob.getOwner();
        return !mob.getItem().isEmpty() && mob.isTame() && owner != null && !owner.isSpectator() && !mob.isOrderedToSit();
    }

    @Override
    public void tick() {
        LivingEntity owner = Objects.requireNonNull(mob.getOwner());

        mob.getNavigation().moveTo(owner, 1.35);
        mob.getLookControl().setLookAt(owner);

        if (mob.distanceToSqr(owner) <= 4) {
            mob.spawnAtLocation(mob.getItem());
            mob.setItem(ItemStack.EMPTY);
            mob.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            ((ServerLevel) mob.level()).sendParticles(ParticleTypes.HEART, mob.getX(), mob.getY() + 1.25D, mob.getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);

            stop();
        }
    }

    @Override
    public void stop() {
        mob.setRetrieving(false);
    }
}
