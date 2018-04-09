package org.andengine.entity.sprite;

import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;

public interface IAnimationData {
    public static final int LOOP_CONTINUOUS = -1;

    int calculateCurrentFrameIndex(long j);

    IAnimationData deepCopy() throws DeepCopyNotSupportedException;

    long getAnimationDuration();

    int getFirstFrameIndex();

    int getFrameCount();

    long[] getFrameDurations();

    int[] getFrames();

    int getLoopCount();

    void set(long j, int i);

    void set(long j, int i, int i2);

    void set(long j, int i, boolean z);

    void set(IAnimationData iAnimationData);

    void set(long[] jArr);

    void set(long[] jArr, int i);

    void set(long[] jArr, int i, int i2);

    void set(long[] jArr, int i, int i2, int i3);

    void set(long[] jArr, int i, int i2, boolean z);

    void set(long[] jArr, boolean z);

    void set(long[] jArr, int[] iArr);

    void set(long[] jArr, int[] iArr, int i);

    void set(long[] jArr, int[] iArr, boolean z);
}
