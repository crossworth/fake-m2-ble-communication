package org.andengine.entity.primitive.vbo;

import org.andengine.entity.primitive.Mesh;
import org.andengine.opengl.vbo.IVertexBufferObject;

public interface IMeshVertexBufferObject extends IVertexBufferObject {
    float[] getBufferData();

    void onUpdateColor(Mesh mesh);

    void onUpdateVertices(Mesh mesh);
}
