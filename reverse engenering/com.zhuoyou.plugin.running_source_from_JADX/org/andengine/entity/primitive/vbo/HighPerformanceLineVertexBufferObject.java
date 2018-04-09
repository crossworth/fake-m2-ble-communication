package org.andengine.entity.primitive.vbo;

import org.andengine.entity.primitive.Line;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.HighPerformanceVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class HighPerformanceLineVertexBufferObject extends HighPerformanceVertexBufferObject implements ILineVertexBufferObject {
    public HighPerformanceLineVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(Line pLine) {
        float[] bufferData = this.mBufferData;
        float packedColor = pLine.getColor().getABGRPackedFloat();
        bufferData[2] = packedColor;
        bufferData[5] = packedColor;
        setDirtyOnHardware();
    }

    public void onUpdateVertices(Line pLine) {
        float[] bufferData = this.mBufferData;
        bufferData[0] = 0.0f;
        bufferData[1] = 0.0f;
        bufferData[3] = pLine.getX2() - pLine.getX1();
        bufferData[4] = pLine.getY2() - pLine.getY1();
        setDirtyOnHardware();
    }
}
