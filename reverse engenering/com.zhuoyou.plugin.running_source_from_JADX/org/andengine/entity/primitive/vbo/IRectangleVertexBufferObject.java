package org.andengine.entity.primitive.vbo;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.IVertexBufferObject;

public interface IRectangleVertexBufferObject extends IVertexBufferObject {
    void onUpdateColor(Rectangle rectangle);

    void onUpdateVertices(Rectangle rectangle);
}
