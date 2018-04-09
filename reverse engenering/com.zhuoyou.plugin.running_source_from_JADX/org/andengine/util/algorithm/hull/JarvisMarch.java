package org.andengine.util.algorithm.hull;

import org.andengine.opengl.util.VertexUtils;
import org.andengine.util.math.MathConstants;
import org.andengine.util.math.MathUtils;

public class JarvisMarch implements IHullAlgorithm {
    public int computeHull(float[] pVertices, int pVertexCount, int pVertexOffsetX, int pVertexOffsetY, int pVertexStride) {
        return jarvisMarch(pVertices, pVertexCount, pVertexOffsetX, pVertexOffsetY, pVertexStride);
    }

    private static int jarvisMarch(float[] pVertices, int pVertexCount, int pVertexOffsetX, int pVertexOffsetY, int pVertexStride) {
        int firstHullVertexIndex = HullUtils.indexOfLowestVertex(pVertices, pVertexCount, pVertexOffsetY, pVertexStride);
        float firstHullVertexX = VertexUtils.getVertex(pVertices, pVertexOffsetX, pVertexStride, firstHullVertexIndex);
        float firstHullVertexY = VertexUtils.getVertex(pVertices, pVertexOffsetY, pVertexStride, firstHullVertexIndex);
        int hullVertexCount = 0;
        int currentHullVertexIndex = firstHullVertexIndex;
        float currentHullVertexAngle = 0.0f;
        do {
            HullUtils.swap(pVertices, pVertexStride, hullVertexCount, currentHullVertexIndex);
            float currentHullPointVertexX = VertexUtils.getVertex(pVertices, pVertexOffsetX, pVertexStride, hullVertexCount);
            float currentHullPointVertexY = VertexUtils.getVertex(pVertices, pVertexOffsetY, pVertexStride, hullVertexCount);
            hullVertexCount++;
            int nextHullVertexIndex = 0;
            float nextHullVertexAngle = MathConstants.PI_TWICE;
            for (int j = hullVertexCount; j < pVertexCount; j++) {
                float possibleNextHullVertexAngle;
                float possibleNextHullVertexX = VertexUtils.getVertex(pVertices, pVertexOffsetX, pVertexStride, j);
                float possibleNextHullVertexY = VertexUtils.getVertex(pVertices, pVertexOffsetY, pVertexStride, j);
                if (currentHullPointVertexX != possibleNextHullVertexX || currentHullPointVertexY != possibleNextHullVertexY) {
                    possibleNextHullVertexAngle = MathUtils.atan2(possibleNextHullVertexY - currentHullPointVertexY, possibleNextHullVertexX - currentHullPointVertexX);
                    if (possibleNextHullVertexAngle < 0.0f) {
                        possibleNextHullVertexAngle += MathConstants.PI_TWICE;
                    }
                    if (possibleNextHullVertexAngle >= currentHullVertexAngle && possibleNextHullVertexAngle <= nextHullVertexAngle) {
                        nextHullVertexIndex = j;
                        nextHullVertexAngle = possibleNextHullVertexAngle;
                    }
                }
            }
            if (hullVertexCount > 1) {
                possibleNextHullVertexAngle = MathUtils.atan2(firstHullVertexY - currentHullPointVertexY, firstHullVertexX - currentHullPointVertexX);
                if (possibleNextHullVertexAngle < 0.0f) {
                    possibleNextHullVertexAngle += MathConstants.PI_TWICE;
                }
                if (possibleNextHullVertexAngle >= currentHullVertexAngle && possibleNextHullVertexAngle <= nextHullVertexAngle) {
                    break;
                }
            }
            currentHullVertexAngle = nextHullVertexAngle;
            currentHullVertexIndex = nextHullVertexIndex;
        } while (currentHullVertexIndex > 0);
        return hullVertexCount;
    }
}
