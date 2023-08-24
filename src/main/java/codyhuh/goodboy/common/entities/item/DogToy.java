package codyhuh.goodboy.common.entities.item;

import codyhuh.goodboy.registry.ModEntities;
import codyhuh.goodboy.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class DogToy extends ThrowableItemProjectile {

    public DogToy(EntityType<? extends DogToy> type, Level level) {
        super(type, level);
    }

    public DogToy(Level level, LivingEntity owner) {
        super(ModEntities.DOG_TOY.get(), owner, level);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);

        if (!this.level.isClientSide) {
            ItemEntity item = EntityType.ITEM.create(this.level);
            Entity owner = getOwner();
            item.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            item.setItem(new ItemStack(ModItems.DOG_TOY.get()));
            if (owner != null) {
                item.setThrower(owner.getUUID());
            }
            this.level.addFreshEntity(item);
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DOG_TOY.get();
    }
}
