package org.andengine.entity.text.vbo;

import org.andengine.entity.text.Text;
import org.andengine.opengl.vbo.IVertexBufferObject;

public interface ITextVertexBufferObject extends IVertexBufferObject {
    void onUpdateColor(Text text);

    void onUpdateVertices(Text text);
}
