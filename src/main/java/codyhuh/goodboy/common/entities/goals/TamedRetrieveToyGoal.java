package codyhuh.goodboy.common.entities.goals;

import codyhuh.goodboy.common.entities.Retriever;
import codyhuh.goodboy.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Path;

import java.util.List;

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
