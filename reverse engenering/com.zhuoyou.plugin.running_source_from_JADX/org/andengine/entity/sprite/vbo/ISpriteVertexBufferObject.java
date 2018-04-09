package org.andengine.entity.sprite.vbo;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.IVertexBufferObject;

public interface ISpriteVertexBufferObject extends IVertexBufferObject {
    void onUpdateColor(Sprite sprite);

    void onUpdateTextureCoordinates(Sprite sprite);

    void onUpdateVertices(Sprite sprite);
}
