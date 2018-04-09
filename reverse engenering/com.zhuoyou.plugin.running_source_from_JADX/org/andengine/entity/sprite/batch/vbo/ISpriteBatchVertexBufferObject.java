package org.andengine.entity.sprite.batch.vbo;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.IVertexBufferObject;

public interface ISpriteBatchVertexBufferObject extends IVertexBufferObject {
    void addWithPackedColor(ITextureRegion iTextureRegion, float f, float f2, float f3, float f4, float f5);

    void addWithPackedColor(ITextureRegion iTextureRegion, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9);

    int getBufferDataOffset();

    void setBufferDataOffset(int i);
}
