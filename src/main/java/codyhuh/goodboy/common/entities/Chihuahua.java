package codyhuh.goodboy.common.entities;

import codyhuh.goodboy.common.entities.util.AbstractDog;
import codyhuh.goodboy.registry.ModEntities;
import codyhuh.goodboy.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Chihuahua extends AbstractDog {
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Chihuahua.class, EntityDataSerializers.INT);

    public Chihuahua(EntityType<? extends AbstractDog> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createChihuahuaAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.315D).add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.ATTACK_DAMAGE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(this, Mob.class, false, e -> e.getBbHeight() > 1.5F && e.getBbWidth() > 1.5F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    // todo - chnage to something more unique maybe?
    @Override
    public Item getTameItem() {
        return Items.BONE;
    }

    @Override
    public void tick() {
        super.tick();
        if (getTarget() != null) System.out.println(getTarget());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Chihuahua dog = ModEntities.CHIHUAHUA.get().create(pLevel);

        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            dog.setOwnerUUID(uuid);
            dog.setTame(true);
        }
        if (random.nextFloat() <= 0.2F) {
            dog.setVariant(1);
        }
        else {
            dog.setVariant(0);
        }

        return dog;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(tag.getInt("Variant"));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CHIHUAHUA_GROWL.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_30424_) {
        return ModSounds.CHIHUAHUA_BARK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CHIHUAHUA_BARK.get();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

        if (dataTag != null && dataTag.contains("Variant", 3)) {
            this.setVariant(dataTag.getInt("Variant"));
        }
        else {
            if (random.nextFloat() <= 0.2F) {
                setVariant(1);
            }
            else {
                setVariant(0);
            }
        }

        return spawnDataIn;
    }

    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof Chihuahua chihuahua)) {
            return false;
        } else {
            if (!chihuahua.isTame()) {
                return false;
            } else if (chihuahua.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && chihuahua.isInLove();
            }
        }
    }
}
