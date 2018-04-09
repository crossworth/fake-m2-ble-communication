package org.andengine.entity.primitive.vbo;

import java.nio.FloatBuffer;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.LowMemoryVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class LowMemoryRectangleVertexBufferObject extends LowMemoryVertexBufferObject implements IRectangleVertexBufferObject {
    public LowMemoryRectangleVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(Rectangle pRectangle) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float packedColor = pRectangle.getColor().getABGRPackedFloat();
        bufferData.put(2, packedColor);
        bufferData.put(5, packedColor);
        bufferData.put(8, packedColor);
        bufferData.put(11, packedColor);
        setDirtyOnHardware();
    }

    public void onUpdateVertices(Rectangle pRectangle) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float x2 = pRectangle.getWidth();
        float y2 = pRectangle.getHeight();
        bufferData.put(0, 0.0f);
        bufferData.put(1, 0.0f);
        bufferData.put(3, 0.0f);
        bufferData.put(4, y2);
        bufferData.put(6, x2);
        bufferData.put(7, 0.0f);
        bufferData.put(9, x2);
        bufferData.put(10, y2);
        setDirtyOnHardware();
    }
}
