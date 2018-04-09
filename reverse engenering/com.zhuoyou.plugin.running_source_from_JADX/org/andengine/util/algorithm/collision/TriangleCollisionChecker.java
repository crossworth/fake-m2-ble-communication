package org.andengine.util.algorithm.collision;

import org.andengine.util.adt.transformation.Transformation;

public class TriangleCollisionChecker {
    public static final int TRIANGLE_VERTEX_COUNT = 3;
    private static final float[] VERTICES_CONTAINS_TMP = new float[6];

    public static boolean checkContains(float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX, float pY) {
        float v0x = pX3 - pX1;
        float v0y = pY3 - pY1;
        float v1x = pX2 - pX1;
        float v1y = pY2 - pY1;
        float v2x = pX - pX1;
        float v2y = pY - pY1;
        float dot00 = (v0x * v0x) + (v0y * v0y);
        float dot01 = (v0x * v1x) + (v0y * v1y);
        float dot02 = (v0x * v2x) + (v0y * v2y);
        float dot11 = (v1x * v1x) + (v1y * v1y);
        float dot12 = (v1x * v2x) + (v1y * v2y);
        float invDenom = 1.0f / ((dot00 * dot11) - (dot01 * dot01));
        float u = ((dot11 * dot02) - (dot01 * dot12)) * invDenom;
        float v = ((dot00 * dot12) - (dot01 * dot02)) * invDenom;
        return u > 0.0f && v > 0.0f && u + v < 1.0f;
    }

    public static boolean checkContains(float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, Transformation pTransformation, float pX, float pY) {
        VERTICES_CONTAINS_TMP[0] = pX1;
        VERTICES_CONTAINS_TMP[1] = pY1;
        VERTICES_CONTAINS_TMP[2] = pX2;
        VERTICES_CONTAINS_TMP[3] = pY2;
        VERTICES_CONTAINS_TMP[4] = pX3;
        VERTICES_CONTAINS_TMP[5] = pY3;
        pTransformation.transform(VERTICES_CONTAINS_TMP);
        return checkContains(VERTICES_CONTAINS_TMP[0], VERTICES_CONTAINS_TMP[1], VERTICES_CONTAINS_TMP[2], VERTICES_CONTAINS_TMP[3], VERTICES_CONTAINS_TMP[4], VERTICES_CONTAINS_TMP[5], pX, pY);
    }
}
