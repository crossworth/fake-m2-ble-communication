package org.andengine.entity.primitive.vbo;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.HighPerformanceVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class HighPerformanceRectangleVertexBufferObject extends HighPerformanceVertexBufferObject implements IRectangleVertexBufferObject {
    public HighPerformanceRectangleVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(Rectangle pRectangle) {
        float[] bufferData = this.mBufferData;
        float packedColor = pRectangle.getColor().getABGRPackedFloat();
        bufferData[2] = packedColor;
        bufferData[5] = packedColor;
        bufferData[8] = packedColor;
        bufferData[11] = packedColor;
        setDirtyOnHardware();
    }

    public void onUpdateVertices(Rectangle pRectangle) {
        float[] bufferData = this.mBufferData;
        float x2 = pRectangle.getWidth();
        float y2 = pRectangle.getHeight();
        bufferData[0] = 0.0f;
        bufferData[1] = 0.0f;
        bufferData[3] = 0.0f;
        bufferData[4] = y2;
        bufferData[6] = x2;
        bufferData[7] = 0.0f;
        bufferData[9] = x2;
        bufferData[10] = y2;
        setDirtyOnHardware();
    }
}
