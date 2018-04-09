package org.andengine.entity.sprite;

import java.util.Arrays;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.time.TimeConstants;

public class AnimationData implements IAnimationData {
    private long mAnimationDuration;
    private int mFirstFrameIndex;
    private int mFrameCount;
    private long[] mFrameDurations;
    private long[] mFrameEndsInNanoseconds;
    private int[] mFrames;
    private int mLoopCount;

    public AnimationData(long pFrameDurationEach, int pFrameCount) {
        set(pFrameDurationEach, pFrameCount);
    }

    public AnimationData(long pFrameDurationEach, int pFrameCount, boolean pLoop) {
        set(pFrameDurationEach, pFrameCount, pLoop);
    }

    public AnimationData(long pFrameDurationEach, int pFrameCount, int pLoopCount) {
        set(pFrameDurationEach, pFrameCount, pLoopCount);
    }

    public AnimationData(long[] pFrameDurations) {
        set(pFrameDurations);
    }

    public AnimationData(long[] pFrameDurations, boolean pLoop) {
        set(pFrameDurations, pLoop);
    }

    public AnimationData(long[] pFrameDurations, int pLoopCount) {
        set(pFrameDurations, pLoopCount);
    }

    public AnimationData(long[] pFrameDurations, int pFirstFrameIndex, int pLastFrameIndex, boolean pLoop) {
        set(pFrameDurations, pFirstFrameIndex, pLastFrameIndex, pLoop);
    }

    public AnimationData(long[] pFrameDurations, int[] pFrames, int pLoopCount) {
        set(pFrameDurations, pFrames, pLoopCount);
    }

    public AnimationData(long[] pFrameDurations, int pFirstFrameIndex, int pLastFrameIndex, int pLoopCount) {
        set(pFrameDurations, pFirstFrameIndex, pLastFrameIndex, pLoopCount);
    }

    public AnimationData(IAnimationData pAnimationData) {
        set(pAnimationData);
    }

    public IAnimationData deepCopy() throws DeepCopyNotSupportedException {
        return new AnimationData((IAnimationData) this);
    }

    public int[] getFrames() {
        return this.mFrames;
    }

    public long[] getFrameDurations() {
        return this.mFrameDurations;
    }

    public int getLoopCount() {
        return this.mLoopCount;
    }

    public int getFrameCount() {
        return this.mFrameCount;
    }

    public int getFirstFrameIndex() {
        return this.mFirstFrameIndex;
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public int calculateCurrentFrameIndex(long pAnimationProgress) {
        long[] frameEnds = this.mFrameEndsInNanoseconds;
        int frameCount = this.mFrameCount;
        for (int i = 0; i < frameCount; i++) {
            if (frameEnds[i] > pAnimationProgress) {
                return i;
            }
        }
        return frameCount - 1;
    }

    public void set(long pFrameDurationEach, int pFrameCount) {
        set(pFrameDurationEach, pFrameCount, true);
    }

    public void set(long pFrameDurationEach, int pFrameCount, boolean pLoop) {
        set(pFrameDurationEach, pFrameCount, pLoop ? -1 : 0);
    }

    public void set(long pFrameDurationEach, int pFrameCount, int pLoopCount) {
        set(fillFrameDurations(pFrameDurationEach, pFrameCount), pLoopCount);
    }

    public void set(long[] pFrameDurations) {
        set(pFrameDurations, true);
    }

    public void set(long[] pFrameDurations, boolean pLoop) {
        set(pFrameDurations, pLoop ? -1 : 0);
    }

    public void set(long[] pFrameDurations, int pLoopCount) {
        set(pFrameDurations, 0, pFrameDurations.length - 1, pLoopCount);
    }

    public void set(long[] pFrameDurations, int pFirstFrameIndex, int pLastFrameIndex) {
        set(pFrameDurations, pFirstFrameIndex, pLastFrameIndex, true);
    }

    public void set(long[] pFrameDurations, int pFirstFrameIndex, int pLastFrameIndex, boolean pLoop) {
        set(pFrameDurations, pFirstFrameIndex, pLastFrameIndex, pLoop ? -1 : 0);
    }

    public void set(long[] pFrameDurations, int pFirstFrameIndex, int pLastFrameIndex, int pLoopCount) {
        set(pFrameDurations, (pLastFrameIndex - pFirstFrameIndex) + 1, null, pFirstFrameIndex, pLoopCount);
        if (pFirstFrameIndex + 1 > pLastFrameIndex) {
            throw new IllegalArgumentException("An animation needs at least two tiles to animate between.");
        }
    }

    public void set(long[] pFrameDurations, int[] pFrames) {
        set(pFrameDurations, pFrames, true);
    }

    public void set(long[] pFrameDurations, int[] pFrames, boolean pLoop) {
        set(pFrameDurations, pFrames, pLoop ? -1 : 0);
    }

    public void set(long[] pFrameDurations, int[] pFrames, int pLoopCount) {
        set(pFrameDurations, pFrames.length, pFrames, 0, pLoopCount);
    }

    public void set(IAnimationData pAnimationData) {
        set(pAnimationData.getFrameDurations(), pAnimationData.getFrameCount(), pAnimationData.getFrames(), pAnimationData.getFirstFrameIndex(), pAnimationData.getLoopCount());
    }

    private void set(long[] pFrameDurations, int pFrameCount, int[] pFrames, int pFirstFrameIndex, int pLoopCount) {
        if (pFrameDurations.length != pFrameCount) {
            throw new IllegalArgumentException("pFrameDurations does not equal pFrameCount!");
        }
        this.mFrameDurations = pFrameDurations;
        this.mFrameCount = pFrameCount;
        this.mFrames = pFrames;
        this.mFirstFrameIndex = pFirstFrameIndex;
        this.mLoopCount = pLoopCount;
        if (this.mFrameEndsInNanoseconds == null || this.mFrameCount > this.mFrameEndsInNanoseconds.length) {
            this.mFrameEndsInNanoseconds = new long[this.mFrameCount];
        }
        long[] frameEndsInNanoseconds = this.mFrameEndsInNanoseconds;
        MathUtils.arraySumInto(this.mFrameDurations, frameEndsInNanoseconds, TimeConstants.NANOSECONDS_PER_MILLISECOND);
        this.mAnimationDuration = frameEndsInNanoseconds[this.mFrameCount - 1];
    }

    private static long[] fillFrameDurations(long pFrameDurationEach, int pFrameCount) {
        long[] frameDurations = new long[pFrameCount];
        Arrays.fill(frameDurations, pFrameDurationEach);
        return frameDurations;
    }
}
