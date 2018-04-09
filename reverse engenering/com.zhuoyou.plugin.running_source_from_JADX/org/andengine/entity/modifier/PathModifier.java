package org.andengine.entity.modifier;

import android.util.FloatMath;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.SequenceModifier;
import org.andengine.util.modifier.SequenceModifier.ISubSequenceModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class PathModifier extends EntityModifier {
    private final Path mPath;
    private IPathModifierListener mPathModifierListener;
    private final SequenceModifier<IEntity> mSequenceModifier;

    class C20501 implements ISubSequenceModifierListener<IEntity> {
        C20501() {
        }

        public void onSubSequenceStarted(IModifier<IEntity> iModifier, IEntity pEntity, int pIndex) {
            if (PathModifier.this.mPathModifierListener != null) {
                PathModifier.this.mPathModifierListener.onPathWaypointStarted(PathModifier.this, pEntity, pIndex);
            }
        }

        public void onSubSequenceFinished(IModifier<IEntity> iModifier, IEntity pEntity, int pIndex) {
            if (PathModifier.this.mPathModifierListener != null) {
                PathModifier.this.mPathModifierListener.onPathWaypointFinished(PathModifier.this, pEntity, pIndex);
            }
        }
    }

    class C20512 implements IEntityModifierListener {
        C20512() {
        }

        public void onModifierStarted(IModifier<IEntity> iModifier, IEntity pEntity) {
            PathModifier.this.onModifierStarted(pEntity);
            if (PathModifier.this.mPathModifierListener != null) {
                PathModifier.this.mPathModifierListener.onPathStarted(PathModifier.this, pEntity);
            }
        }

        public void onModifierFinished(IModifier<IEntity> iModifier, IEntity pEntity) {
            PathModifier.this.onModifierFinished(pEntity);
            if (PathModifier.this.mPathModifierListener != null) {
                PathModifier.this.mPathModifierListener.onPathFinished(PathModifier.this, pEntity);
            }
        }
    }

    public interface IPathModifierListener {
        void onPathFinished(PathModifier pathModifier, IEntity iEntity);

        void onPathStarted(PathModifier pathModifier, IEntity iEntity);

        void onPathWaypointFinished(PathModifier pathModifier, IEntity iEntity, int i);

        void onPathWaypointStarted(PathModifier pathModifier, IEntity iEntity, int i);
    }

    public static class Path {
        private int mIndex;
        private float mLength;
        private boolean mLengthChanged = false;
        private final float[] mXs;
        private final float[] mYs;

        public Path(int pLength) {
            this.mXs = new float[pLength];
            this.mYs = new float[pLength];
            this.mIndex = 0;
            this.mLengthChanged = false;
        }

        public Path(float[] pCoordinatesX, float[] pCoordinatesY) throws IllegalArgumentException {
            if (pCoordinatesX.length != pCoordinatesY.length) {
                throw new IllegalArgumentException("Coordinate-Arrays must have the same length.");
            }
            this.mXs = pCoordinatesX;
            this.mYs = pCoordinatesY;
            this.mIndex = pCoordinatesX.length;
            this.mLengthChanged = true;
        }

        public Path(Path pPath) {
            int size = pPath.getSize();
            this.mXs = new float[size];
            this.mYs = new float[size];
            System.arraycopy(pPath.mXs, 0, this.mXs, 0, size);
            System.arraycopy(pPath.mYs, 0, this.mYs, 0, size);
            this.mIndex = pPath.mIndex;
            this.mLengthChanged = pPath.mLengthChanged;
            this.mLength = pPath.mLength;
        }

        public Path deepCopy() {
            return new Path(this);
        }

        public Path to(float pX, float pY) {
            this.mXs[this.mIndex] = pX;
            this.mYs[this.mIndex] = pY;
            this.mIndex++;
            this.mLengthChanged = true;
            return this;
        }

        public float[] getCoordinatesX() {
            return this.mXs;
        }

        public float[] getCoordinatesY() {
            return this.mYs;
        }

        public int getSize() {
            return this.mXs.length;
        }

        public float getLength() {
            if (this.mLengthChanged) {
                updateLength();
            }
            return this.mLength;
        }

        public float getSegmentLength(int pSegmentIndex) {
            float[] coordinatesX = this.mXs;
            float[] coordinatesY = this.mYs;
            int nextSegmentIndex = pSegmentIndex + 1;
            float dx = coordinatesX[pSegmentIndex] - coordinatesX[nextSegmentIndex];
            float dy = coordinatesY[pSegmentIndex] - coordinatesY[nextSegmentIndex];
            return FloatMath.sqrt((dx * dx) + (dy * dy));
        }

        private void updateLength() {
            float length = 0.0f;
            for (int i = this.mIndex - 2; i >= 0; i--) {
                length += getSegmentLength(i);
            }
            this.mLength = length;
        }
    }

    public PathModifier(float pDuration, Path pPath) {
        this(pDuration, pPath, null, null, EaseLinear.getInstance());
    }

    public PathModifier(float pDuration, Path pPath, IEaseFunction pEaseFunction) {
        this(pDuration, pPath, null, null, pEaseFunction);
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifierListener pEntityModiferListener) {
        this(pDuration, pPath, pEntityModiferListener, null, EaseLinear.getInstance());
    }

    public PathModifier(float pDuration, Path pPath, IPathModifierListener pPathModifierListener) {
        this(pDuration, pPath, null, pPathModifierListener, EaseLinear.getInstance());
    }

    public PathModifier(float pDuration, Path pPath, IPathModifierListener pPathModifierListener, IEaseFunction pEaseFunction) {
        this(pDuration, pPath, null, pPathModifierListener, pEaseFunction);
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifierListener pEntityModiferListener, IEaseFunction pEaseFunction) {
        this(pDuration, pPath, pEntityModiferListener, null, pEaseFunction);
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifierListener pEntityModiferListener, IPathModifierListener pPathModifierListener) throws IllegalArgumentException {
        this(pDuration, pPath, pEntityModiferListener, pPathModifierListener, EaseLinear.getInstance());
    }

    public PathModifier(float pDuration, Path pPath, IEntityModifierListener pEntityModiferListener, IPathModifierListener pPathModifierListener, IEaseFunction pEaseFunction) throws IllegalArgumentException {
        super(pEntityModiferListener);
        int pathSize = pPath.getSize();
        if (pathSize < 2) {
            throw new IllegalArgumentException("Path needs at least 2 waypoints!");
        }
        this.mPath = pPath;
        this.mPathModifierListener = pPathModifierListener;
        MoveModifier[] moveModifiers = new MoveModifier[(pathSize - 1)];
        float[] coordinatesX = pPath.getCoordinatesX();
        float[] coordinatesY = pPath.getCoordinatesY();
        float velocity = pPath.getLength() / pDuration;
        int modifierCount = moveModifiers.length;
        for (int i = 0; i < modifierCount; i++) {
            moveModifiers[i] = new MoveModifier(pPath.getSegmentLength(i) / velocity, coordinatesX[i], coordinatesX[i + 1], coordinatesY[i], coordinatesY[i + 1], null, pEaseFunction);
        }
        this.mSequenceModifier = new SequenceModifier(new C20501(), new C20512(), moveModifiers);
    }

    protected PathModifier(PathModifier pPathModifier) throws DeepCopyNotSupportedException {
        this.mPath = pPathModifier.mPath.deepCopy();
        this.mSequenceModifier = pPathModifier.mSequenceModifier.deepCopy();
    }

    public PathModifier deepCopy() throws DeepCopyNotSupportedException {
        return new PathModifier(this);
    }

    public Path getPath() {
        return this.mPath;
    }

    public boolean isFinished() {
        return this.mSequenceModifier.isFinished();
    }

    public float getSecondsElapsed() {
        return this.mSequenceModifier.getSecondsElapsed();
    }

    public float getDuration() {
        return this.mSequenceModifier.getDuration();
    }

    public IPathModifierListener getPathModifierListener() {
        return this.mPathModifierListener;
    }

    public void setPathModifierListener(IPathModifierListener pPathModifierListener) {
        this.mPathModifierListener = pPathModifierListener;
    }

    public void reset() {
        this.mSequenceModifier.reset();
    }

    public float onUpdate(float pSecondsElapsed, IEntity pEntity) {
        return this.mSequenceModifier.onUpdate(pSecondsElapsed, pEntity);
    }
}
