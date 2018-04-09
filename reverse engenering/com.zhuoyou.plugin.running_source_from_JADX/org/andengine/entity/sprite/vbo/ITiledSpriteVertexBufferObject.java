package org.andengine.entity.sprite.vbo;

import org.andengine.entity.sprite.TiledSprite;

public interface ITiledSpriteVertexBufferObject extends ISpriteVertexBufferObject {
    void onUpdateColor(TiledSprite tiledSprite);

    void onUpdateTextureCoordinates(TiledSprite tiledSprite);

    void onUpdateVertices(TiledSprite tiledSprite);
}
