package org.andengine.util.algorithm.collision;

import org.andengine.opengl.util.VertexUtils;

public class ShapeCollisionChecker extends BaseCollisionChecker {
    public static boolean checkCollision(float[] pVerticesA, int pVertexCountA, float[] pVerticesB, int pVertexCountB) {
        return checkCollision(pVerticesA, pVertexCountA, 0, 1, 2, pVerticesB, pVertexCountB, 0, 1, 2);
    }

    public static boolean checkCollision(float[] pVerticesA, int pVertexCountA, int pVertexOffsetXA, int pVertexOffsetYA, int pVertexStrideA, float[] pVerticesB, int pVertexCountB, int pVertexOffsetXB, int pVertexOffsetYB, int pVertexStrideB) {
        for (int a = pVertexCountA - 2; a >= 0; a--) {
            if (checkCollisionSub(pVerticesA, pVertexOffsetXA, pVertexOffsetYA, pVertexStrideA, a, a + 1, pVerticesB, pVertexCountB, pVertexOffsetXB, pVertexOffsetYB, pVertexStrideB)) {
                return true;
            }
        }
        if (checkCollisionSub(pVerticesA, pVertexOffsetXA, pVertexOffsetYA, pVertexStrideA, pVertexCountA - 1, 0, pVerticesB, pVertexCountB, pVertexOffsetXB, pVertexOffsetYB, pVertexStrideB)) {
            return true;
        }
        if (checkContains(pVerticesA, pVertexCountA, VertexUtils.getVertex(pVerticesB, pVertexOffsetXB, pVertexStrideB, 0), VertexUtils.getVertex(pVerticesB, pVertexOffsetYB, pVertexStrideB, 0))) {
            return true;
        }
        if (checkContains(pVerticesB, pVertexCountB, VertexUtils.getVertex(pVerticesA, pVertexOffsetXA, pVertexStrideA, 0), VertexUtils.getVertex(pVerticesA, pVertexOffsetYA, pVertexStrideA, 0))) {
            return true;
        }
        return false;
    }

    private static boolean checkCollisionSub(float[] pVerticesA, int pVertexOffsetXA, int pVertexOffsetYA, int pVertexStrideA, int pVertexIndexA1, int pVertexIndexA2, float[] pVerticesB, int pVertexCountB, int pVertexOffsetXB, int pVertexOffsetYB, int pVertexStrideB) {
        float vertexA1X = VertexUtils.getVertex(pVerticesA, pVertexOffsetXA, pVertexStrideA, pVertexIndexA1);
        float vertexA1Y = VertexUtils.getVertex(pVerticesA, pVertexOffsetYA, pVertexStrideA, pVertexIndexA1);
        float vertexA2X = VertexUtils.getVertex(pVerticesA, pVertexOffsetXA, pVertexStrideA, pVertexIndexA2);
        float vertexA2Y = VertexUtils.getVertex(pVerticesA, pVertexOffsetYA, pVertexStrideA, pVertexIndexA2);
        for (int b = pVertexCountB - 2; b >= 0; b--) {
            if (LineCollisionChecker.checkLineCollision(vertexA1X, vertexA1Y, vertexA2X, vertexA2Y, VertexUtils.getVertex(pVerticesB, pVertexOffsetXB, pVertexStrideB, b), VertexUtils.getVertex(pVerticesB, pVertexOffsetYB, pVertexStrideB, b), VertexUtils.getVertex(pVerticesB, pVertexOffsetXB, pVertexStrideB, b + 1), VertexUtils.getVertex(pVerticesB, pVertexOffsetYB, pVertexStrideB, b + 1))) {
                return true;
            }
        }
        if (LineCollisionChecker.checkLineCollision(vertexA1X, vertexA1Y, vertexA2X, vertexA2Y, VertexUtils.getVertex(pVerticesB, pVertexOffsetXB, pVertexStrideB, pVertexCountB - 1), VertexUtils.getVertex(pVerticesB, pVertexOffsetYB, pVertexStrideB, pVertexCountB - 1), VertexUtils.getVertex(pVerticesB, pVertexOffsetXB, pVertexStrideB, 0), VertexUtils.getVertex(pVerticesB, pVertexOffsetYB, pVertexStrideB, 0))) {
            return true;
        }
        return false;
    }

    public static boolean checkContains(float[] pVertices, int pVertexCount, float pX, float pY) {
        return checkContains(pVertices, pVertexCount, 0, 1, 2, pX, pY);
    }

    public static boolean checkContains(float[] pVertices, int pVertexCount, int pVertexOffsetX, int pVertexOffsetY, int pVertexStride, float pX, float pY) {
        boolean odd = false;
        int j = pVertexCount - 1;
        for (int i = 0; i < pVertexCount; i++) {
            float vertexXI = VertexUtils.getVertex(pVertices, pVertexOffsetX, pVertexStride, i);
            float vertexYI = VertexUtils.getVertex(pVertices, pVertexOffsetY, pVertexStride, i);
            float vertexXJ = VertexUtils.getVertex(pVertices, pVertexOffsetX, pVertexStride, j);
            float vertexYJ = VertexUtils.getVertex(pVertices, pVertexOffsetY, pVertexStride, j);
            if (((vertexYI < pY && vertexYJ >= pY) || (vertexYJ < pY && vertexYI >= pY)) && (vertexXI <= pX || vertexXJ <= pX)) {
                odd ^= (((pY - vertexYI) / (vertexYJ - vertexYI)) * (vertexXJ - vertexXI)) + vertexXI < pX ? 1 : 0;
            }
            j = i;
        }
        return odd;
    }
}
