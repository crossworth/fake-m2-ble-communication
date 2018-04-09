package org.andengine.entity.primitive.vbo;

import org.andengine.entity.primitive.Line;
import org.andengine.opengl.vbo.IVertexBufferObject;

public interface ILineVertexBufferObject extends IVertexBufferObject {
    void onUpdateColor(Line line);

    void onUpdateVertices(Line line);
}
