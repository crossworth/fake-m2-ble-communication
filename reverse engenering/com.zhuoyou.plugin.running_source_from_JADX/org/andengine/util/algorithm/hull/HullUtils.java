package org.andengine.util.algorithm.hull;

public class HullUtils {
    public static int indexOfLowestVertex(float[] pVertices, int pVertexCount, int pVertexOffsetY, int pVertexStride) {
        int lowestVertexIndex = 0;
        float lowestVertexY = pVertices[pVertexOffsetY];
        int lastVertexOffset = pVertexCount * pVertexStride;
        int currentVertexIndex = 1;
        int currentVertexOffsetY = pVertexStride + pVertexOffsetY;
        while (currentVertexOffsetY < lastVertexOffset) {
            float currentVertexY = pVertices[currentVertexOffsetY];
            if (currentVertexY < lowestVertexY) {
                lowestVertexIndex = currentVertexIndex;
                lowestVertexY = currentVertexY;
            }
            currentVertexIndex++;
            currentVertexOffsetY += pVertexStride;
        }
        return lowestVertexIndex;
    }

    public static void swap(float[] pVertices, int pVertexStride, int pVertexIndexA, int pVertexIndexB) {
        int vertexOffsetA = pVertexIndexA * pVertexStride;
        int vertexOffsetB = pVertexIndexB * pVertexStride;
        for (int i = pVertexStride - 1; i >= 0; i--) {
            float tmp = pVertices[vertexOffsetA + i];
            pVertices[vertexOffsetA + i] = pVertices[vertexOffsetB + i];
            pVertices[vertexOffsetB + i] = tmp;
        }
    }
}
