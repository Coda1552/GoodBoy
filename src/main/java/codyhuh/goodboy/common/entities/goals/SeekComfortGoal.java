package codyhuh.goodboy.common.entities.goals;

import codyhuh.goodboy.common.entities.Chihuahua;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

import java.util.EnumSet;

public class SeekComfortGoal extends MoveToBlockGoal {
    private final Chihuahua chihuahua;

    public SeekComfortGoal(Chihuahua p_25135_, double p_25136_, int p_25137_) {
        super(p_25135_, p_25136_, p_25137_, 6);
        this.chihuahua = p_25135_;
        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    public boolean canUse() {
        return this.chihuahua.isTame() && !this.chihuahua.isOrderedToSit() && super.canUse();
    }

    public void start() {
        super.start();
        this.chihuahua.setInSittingPose(false);
    }

    protected int nextStartTick(PathfinderMob p_25140_) {
        return 40;
    }

    public void stop() {
        super.stop();
    }

    public void tick() {
        super.tick();
        this.chihuahua.setInSittingPose(false);
    }

    protected boolean isValidTarget(LevelReader p_25142_, BlockPos p_25143_) {
        return (p_25142_.isEmptyBlock(p_25143_.above()) && p_25142_.getBlockState(p_25143_).is(BlockTags.BEDS)) || p_25142_.getBlockState(p_25143_).is(BlockTags.WOOL_CARPETS);
    }
}
