package codyhuh.goodboy.common.entities;

import codyhuh.goodboy.common.entities.goals.TamedGoToToyGoal;
import codyhuh.goodboy.common.entities.goals.TamedRetrieveToyGoal;
import codyhuh.goodboy.common.entities.util.AbstractDog;
import codyhuh.goodboy.registry.ModEntities;
import codyhuh.goodboy.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Retriever extends AbstractDog {
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Retriever.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_RETRIEVING = SynchedEntityData.defineId(Retriever.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(Retriever.class, EntityDataSerializers.ITEM_STACK);

    public Retriever(EntityType<? extends TamableAnimal> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
        this.setTame(false);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
        this.setCanPickUpLoot(true);
        this.setDropChance(EquipmentSlot.MAINHAND, 1F);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new TamedGoToToyGoal(this));
        this.goalSelector.addGoal(2, new TamedRetrieveToyGoal(this));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false) {
            @Override
            public boolean canUse() {
                return super.canUse() && !getRetrieving();
            }
        });
    }

    public static AttributeSupplier.Builder createRetrieverAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 0);
        this.entityData.define(DATA_RETRIEVING, false);
        this.entityData.define(DATA_ITEM, ItemStack.EMPTY);
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

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getVariant());
        tag.putBoolean("Retreiving", this.getRetrieving());
        tag.put("item", getItem().save(new CompoundTag()));
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(tag.getInt("Variant"));
        setRetrieving(tag.getBoolean("Retrieving"));
        setItem(ItemStack.of(tag.getCompound("item")));
    }

    // todo ?
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    public ItemStack getItem() {
        return entityData.get(DATA_ITEM);
    }

    public void setItem(final ItemStack item) {
        this.entityData.set(DATA_ITEM, item);
    }

    @Override
    public void setItemSlot(EquipmentSlot p_21416_, ItemStack p_21417_) {
        if (p_21416_ == EquipmentSlot.MAINHAND) {
            setItem(p_21417_);
            return;
        }
        super.setItemSlot(p_21416_, p_21417_);
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
        return isTame() && pItemstack.is(ModItems.DOG_TOY.get()) && getItem().isEmpty();
    }

    @Override
    public boolean wantsToPickUp(ItemStack pStack) {
        return canTakeItem(pStack);
    }

    @Override
    public boolean equipItemIfPossible(ItemStack p_21541_) {
        if (this.canHoldItem(p_21541_)) {
            setItem(p_21541_.copy());
            this.playEquipSound(p_21541_);
            return true;
        }
        else {
            return false;
        }
    }

    // todo - move some of this logic to BaseDog
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

    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof Retriever retriever)) {
            return false;
        } else {
            if (!retriever.isTame()) {
                return false;
            } else if (retriever.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && retriever.isInLove();
            }
        }
    }
}
