package codyhuh.goodboy.common.entities;

import codyhuh.goodboy.common.entities.goals.TamedGoToToyGoal;
import codyhuh.goodboy.common.entities.goals.TamedRetrieveToyGoal;
import codyhuh.goodboy.registry.ModEntities;
import codyhuh.goodboy.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Retriever extends TamableAnimal {
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Retriever.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Retriever.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_RETRIEVING = SynchedEntityData.defineId(Retriever.class, EntityDataSerializers.BOOLEAN);
    public ItemStack item = ItemStack.EMPTY;

    public Retriever(EntityType<? extends TamableAnimal> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
        this.setTame(false);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
        this.setCanPickUpLoot(true);
        this.setDropChance(EquipmentSlot.MAINHAND, 1F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.15D));
        this.goalSelector.addGoal(3, new TamedGoToToyGoal(this));
        this.goalSelector.addGoal(3, new TamedRetrieveToyGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false) {
            @Override
            public boolean canUse() {
                return super.canUse() && !getRetrieving();
            }
        });
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createRetrieverAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
        this.entityData.define(DATA_VARIANT, 0);
        this.entityData.define(DATA_RETRIEVING, false);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public boolean getRetrieving() {
        return this.entityData.get(DATA_RETRIEVING);
    }

    public void setRetrieving(boolean retrieving) {
        this.entityData.set(DATA_RETRIEVING, retrieving);
    }

    private void returnToOwner() {
        Path path = getNavigation().createPath(getOwner(), 0);

        if (getNavigation().moveTo(path, 1.0D)) {

            if (path.isDone()) {
                playSound(SoundEvents.WOLF_PANT);

                ItemEntity item = EntityType.ITEM.create(level);

                item.moveTo(position());
                item.setItem(new ItemStack(ModItems.DOG_TOY.get()));

                level.addFreshEntity(item);
            }
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

        if (dataTag != null && dataTag.contains("Variant", 3)) {
            this.setVariant(dataTag.getInt("Variant"));
        }
        else {
            setVariant(random.nextInt(4));
        }

        return spawnDataIn;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Retriever dog = ModEntities.RETRIEVER.get().create(pLevel);

        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            dog.setOwnerUUID(uuid);
            dog.setTame(true);
        }
        dog.setVariant(random.nextInt(3));

        return dog;
    }

    protected void playStepSound(BlockPos p_30415_, BlockState p_30416_) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    public void addAdditionalSaveData(CompoundTag p_30418_) {
        super.addAdditionalSaveData(p_30418_);
        p_30418_.putByte("CollarColor", (byte)this.getCollarColor().getId());
        p_30418_.putInt("Variant", this.getVariant());
        p_30418_.putBoolean("Retreiving", this.getRetrieving());
        p_30418_.put("item", item.save(new CompoundTag()));
    }

    public void readAdditionalSaveData(CompoundTag p_30402_) {
        super.readAdditionalSaveData(p_30402_);
        if (p_30402_.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(p_30402_.getInt("CollarColor")));
        }
        setVariant(p_30402_.getInt("Variant"));
        setRetrieving(p_30402_.getBoolean("Retrieving"));
        item = ItemStack.of(p_30402_.getCompound("item"));
    }

    protected SoundEvent getAmbientSound() {
        if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource p_30424_) {
        return SoundEvents.WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected float getStandingEyeHeight(Pose p_30409_, EntityDimensions p_30410_) {
        return p_30410_.height * 0.8F;
    }

    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(final ItemStack item) {
        this.item = item;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        if (pSlot == EquipmentSlot.MAINHAND) {
            return this.getItem();
        }
        return super.getItemBySlot(pSlot);
    }

    @Override
    public boolean canTakeItem(ItemStack pItemstack) {
        return item.isEmpty();
    }

    @Override
    public boolean wantsToPickUp(ItemStack pStack) {
        return canTakeItem(pStack);
    }

    @Override
    public void onItemPickup(ItemEntity pItem) {
        super.onItemPickup(pItem);
    }

    @Override
    public boolean equipItemIfPossible(ItemStack p_21541_) {
        if (this.canHoldItem(p_21541_)) {
            item = p_21541_.copy();
            this.playEquipSound(p_21541_);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean hurt(DamageSource p_30386_, float p_30387_) {
        if (this.isInvulnerableTo(p_30386_)) {
            return false;
        }
        else {
            Entity entity = p_30386_.getEntity();
            if (!this.level.isClientSide) {
                this.setOrderedToSit(false);
            }

            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                p_30387_ = (p_30387_ + 1.0F) / 2.0F;
            }

            return super.hurt(p_30386_, p_30387_);
        }
    }

    public void setTame(boolean p_30443_) {
        super.setTame(p_30443_);
        if (p_30443_) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public InteractionResult mobInteract(Player p_30412_, InteractionHand p_30413_) {
        ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(p_30412_) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                    if (!p_30412_.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.gameEvent(GameEvent.EAT, this);
                    return InteractionResult.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    InteractionResult interactionresult = super.mobInteract(p_30412_, p_30413_);
                    if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(p_30412_)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return InteractionResult.SUCCESS;
                    }

                    return interactionresult;
                }

                DyeColor dyecolor = ((DyeItem)item).getDyeColor();
                if (dyecolor != this.getCollarColor()) {
                    this.setCollarColor(dyecolor);
                    if (!p_30412_.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    return InteractionResult.SUCCESS;
                }
            } else if (itemstack.is(Items.BONE)) {
                if (!p_30412_.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_30412_)) {
                    this.tame(p_30412_);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(p_30412_, p_30413_);
        }
    }

    public float getTailAngle() {
        return this.isTame() ? (-2.0F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * (float)Math.PI : -0.2F;
    }

    public boolean isFood(ItemStack p_30440_) {
        Item item = p_30440_.getItem();
        return item.isEdible() && p_30440_.getFoodProperties(this).isMeat();
    }

    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor p_30398_) {
        this.entityData.set(DATA_COLLAR_COLOR, p_30398_.getId());
    }

    public boolean canMate(Animal p_30392_) {
        if (p_30392_ == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(p_30392_ instanceof Wolf)) {
            return false;
        } else {
            Wolf wolf = (Wolf)p_30392_;
            if (!wolf.isTame()) {
                return false;
            } else if (wolf.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && wolf.isInLove();
            }
        }
    }

    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, this.getEyeHeight(), (this.getBbWidth() * 0.4F));
    }

    public static boolean checkRetrieverSpawnRules(EntityType<Wolf> p_218292_, LevelAccessor p_218293_, MobSpawnType p_218294_, BlockPos p_218295_, RandomSource p_218296_) {
        return p_218293_.getBlockState(p_218295_.below()).is(BlockTags.WOLVES_SPAWNABLE_ON) && isBrightEnoughToSpawn(p_218293_, p_218295_);
    }
}
