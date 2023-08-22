package codyhuh.goodboy.common.entities.goals;

import codyhuh.goodboy.common.entities.Retriever;
import codyhuh.goodboy.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.pathfinder.Path;

import java.util.List;

// todo - fix retrievers fetch wonky-ness
public class TamedGoToToyGoal extends Goal {
    private final Retriever mob;
    private List<ItemEntity> items;
    private Path path;
    private ItemEntity item;

    public TamedGoToToyGoal(Retriever mob) {
        this.mob = mob;
    }

    @Override
    public boolean canContinueToUse() {
        return items != null && item != null && path != null;
    }

    @Override
    public boolean canUse() {
        if (!mob.item.isEmpty() || !mob.isTame() || mob.getOwner() == null) {
            return false;
        }

        items = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(24.0D), e -> {
            if (e.getOwner() != null) {
                return !e.getOwner().equals(mob.getUUID()) && e.getItem().is(ModItems.DOG_TOY.get());
            }
            else {
                return e.getItem().is(ModItems.DOG_TOY.get());
            }
        });

        return !items.isEmpty();
    }

    @Override
    public void tick() {
        if (!mob.getRetrieving()) {
            mob.setRetrieving(true);
        }

        if (path != null && item != null) {
            if (path.isDone() && mob.equipItemIfPossible(item.getItem())) {
                mob.setItem(item.getItem());
                stop();
            }
            return;
        }

        item = items.get(0);


        path = mob.getNavigation().createPath(item, 0);

        if (!mob.isOrderedToSit()) {
            mob.getNavigation().moveTo(path, 1.35D);
        }
    }

    @Override
    public void stop() {
        item = null;
        items = null;
        path = null;
        mob.setRetrieving(false);
    }
}
