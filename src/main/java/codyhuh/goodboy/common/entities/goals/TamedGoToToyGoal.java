package codyhuh.goodboy.common.entities.goals;

import codyhuh.goodboy.common.entities.Retriever;
import codyhuh.goodboy.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;
import java.util.Objects;

public class TamedGoToToyGoal extends Goal {
    private final Retriever mob;
    private ItemEntity item;

    public TamedGoToToyGoal(Retriever mob) {
        this.mob = mob;
    }

    @Override
    public boolean canContinueToUse() {
        return item != null && item.isAlive() && !mob.isOrderedToSit() && mob.distanceToSqr(item) > 1;
    }

    @Override
    public boolean canUse() {
        if (!mob.getItem().isEmpty() || !mob.isTame() || mob.getOwner() == null || mob.isOrderedToSit()) {
            return false;
        }

        List<ItemEntity> items = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(48.0D), e -> {
            return e.getOwner() != null && Objects.requireNonNull(mob.getOwnerUUID()).equals(e.getOwner().getUUID()) && e.getItem().is(ModItems.DOG_TOY.get());
        });

        item = items.isEmpty() ? null : items.get(0);

        return item != null && mob.distanceToSqr(item) > 16;
    }

    @Override
    public void tick() {
        if (!mob.getRetrieving()) {
            mob.setRetrieving(true);
        }

        mob.getNavigation().moveTo(item, 1.35D);
        mob.getLookControl().setLookAt(item);

        if (mob.distanceToSqr(item) <= 4 && mob.equipItemIfPossible(item.getItem()) == item.getItem()) {
            item.remove(Entity.RemovalReason.DISCARDED);
            stop();
        }
    }

    @Override
    public void stop() {
        item = null;
    }
}
