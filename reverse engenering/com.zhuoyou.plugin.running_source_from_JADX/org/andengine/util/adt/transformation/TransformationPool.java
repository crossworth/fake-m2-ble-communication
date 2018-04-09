package org.andengine.util.adt.transformation;

import org.andengine.util.adt.pool.GenericPool;

public class TransformationPool {
    private static final GenericPool<Transformation> POOL = new C21671();

    static class C21671 extends GenericPool<Transformation> {
        C21671() {
        }

        protected Transformation onAllocatePoolItem() {
            return new Transformation();
        }
    }

    public static Transformation obtain() {
        return (Transformation) POOL.obtainPoolItem();
    }

    public static void recycle(Transformation pTransformation) {
        pTransformation.setToIdentity();
        POOL.recyclePoolItem(pTransformation);
    }
}
