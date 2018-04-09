package org.andengine.entity.primitive.vbo;

import org.andengine.entity.primitive.Mesh;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.HighPerformanceVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class HighPerformanceMeshVertexBufferObject extends HighPerformanceVertexBufferObject implements IMeshVertexBufferObject {
    private final int mVertexCount;

    public HighPerformanceMeshVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, float[] pBufferData, int pVertexCount, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pBufferData, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
        this.mVertexCount = pVertexCount;
    }

    public void onUpdateColor(Mesh pMesh) {
        float[] bufferData = this.mBufferData;
        float packedColor = pMesh.getColor().getABGRPackedFloat();
        for (int i = 0; i < this.mVertexCount; i++) {
            bufferData[(i * 3) + 2] = packedColor;
        }
        setDirtyOnHardware();
    }

    public void onUpdateVertices(Mesh pMesh) {
        setDirtyOnHardware();
    }
}
