package org.andengine.entity.sprite.vbo;

import java.nio.FloatBuffer;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.LowMemoryVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class LowMemoryUniformColorSpriteVertexBufferObject extends LowMemoryVertexBufferObject implements IUniformColorSpriteVertexBufferObject {
    public LowMemoryUniformColorSpriteVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(Sprite pSprite) {
    }

    public void onUpdateVertices(Sprite pSprite) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float x2 = pSprite.getWidth();
        float y2 = pSprite.getHeight();
        bufferData.put(0, 0.0f);
        bufferData.put(1, 0.0f);
        bufferData.put(4, 0.0f);
        bufferData.put(5, y2);
        bufferData.put(8, x2);
        bufferData.put(9, 0.0f);
        bufferData.put(12, x2);
        bufferData.put(13, y2);
        setDirtyOnHardware();
    }

    public void onUpdateTextureCoordinates(Sprite pSprite) {
        float u;
        float u2;
        float v;
        float v2;
        FloatBuffer bufferData = this.mFloatBuffer;
        ITextureRegion textureRegion = pSprite.getTextureRegion();
        if (pSprite.isFlippedVertical()) {
            if (pSprite.isFlippedHorizontal()) {
                u = textureRegion.getU2();
                u2 = textureRegion.getU();
                v = textureRegion.getV2();
                v2 = textureRegion.getV();
            } else {
                u = textureRegion.getU();
                u2 = textureRegion.getU2();
                v = textureRegion.getV2();
                v2 = textureRegion.getV();
            }
        } else if (pSprite.isFlippedHorizontal()) {
            u = textureRegion.getU2();
            u2 = textureRegion.getU();
            v = textureRegion.getV();
            v2 = textureRegion.getV2();
        } else {
            u = textureRegion.getU();
            u2 = textureRegion.getU2();
            v = textureRegion.getV();
            v2 = textureRegion.getV2();
        }
        if (textureRegion.isRotated()) {
            bufferData.put(2, u2);
            bufferData.put(3, v);
            bufferData.put(6, u);
            bufferData.put(7, v);
            bufferData.put(10, u2);
            bufferData.put(11, v2);
            bufferData.put(14, u);
            bufferData.put(15, v2);
        } else {
            bufferData.put(2, u);
            bufferData.put(3, v);
            bufferData.put(6, u);
            bufferData.put(7, v2);
            bufferData.put(10, u2);
            bufferData.put(11, v);
            bufferData.put(14, u2);
            bufferData.put(15, v2);
        }
        setDirtyOnHardware();
    }
}
