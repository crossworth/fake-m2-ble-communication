package org.andengine.opengl.util;

public class VertexUtils {
    public static float getVertex(float[] pVertices, int pVertexOffset, int pVertexStride, int pVertexIndex) {
        return pVertices[(pVertexIndex * pVertexStride) + pVertexOffset];
    }
}
