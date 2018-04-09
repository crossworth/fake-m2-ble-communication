package org.andengine.entity.primitive.vbo;

import java.nio.FloatBuffer;
import org.andengine.entity.primitive.Line;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.LowMemoryVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class LowMemoryLineVertexBufferObject extends LowMemoryVertexBufferObject implements ILineVertexBufferObject {
    public LowMemoryLineVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(Line pLine) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float packedColor = pLine.getColor().getABGRPackedFloat();
        bufferData.put(2, packedColor);
        bufferData.put(5, packedColor);
        setDirtyOnHardware();
    }

    public void onUpdateVertices(Line pLine) {
        FloatBuffer bufferData = this.mFloatBuffer;
        bufferData.put(0, 0.0f);
        bufferData.put(1, 0.0f);
        bufferData.put(3, pLine.getX2() - pLine.getX1());
        bufferData.put(4, pLine.getY2() - pLine.getY1());
        setDirtyOnHardware();
    }
}
