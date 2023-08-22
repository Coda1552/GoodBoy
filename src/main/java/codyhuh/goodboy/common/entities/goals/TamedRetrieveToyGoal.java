package codyhuh.goodboy.common.entities.goals;

import codyhuh.goodboy.common.entities.Retriever;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Path;

public class TamedRetrieveToyGoal extends Goal {
    private final Retriever mob;
    private Path path;

    public TamedRetrieveToyGoal(Retriever mob) {
        this.mob = mob;
    }

    @Override
    public boolean canContinueToUse() {
        return path != null;
    }

    @Override
    public boolean canUse() {
        return !mob.item.isEmpty() && mob.isTame() && mob.getOwner() != null;
    }

    @Override
    public void tick() {
        if (!mob.getRetrieving()) {
            mob.setRetrieving(true);
        }

        if (path != null) {
            if (path.isDone()) {
                mob.spawnAtLocation(mob.getItem());
                mob.setItem(ItemStack.EMPTY);
                mob.level.addParticle(ParticleTypes.HEART, mob.getX(), mob.getY() + 0.6D, mob.getZ(), 0.0D, 0.0D, 0.0D);
                stop();
            }
            return;
        }

        if (mob.getOwner() != null && !mob.isOrderedToSit()) {
            path = mob.getNavigation().createPath(mob.getOwner(), 0);
            mob.getNavigation().moveTo(path, 1.35D);
        }

    }

    @Override
    public void stop() {
        path = null;
        mob.setRetrieving(false);
    }
}
