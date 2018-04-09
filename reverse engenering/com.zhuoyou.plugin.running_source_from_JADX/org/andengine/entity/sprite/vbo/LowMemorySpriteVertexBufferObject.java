package org.andengine.entity.sprite.vbo;

import java.nio.FloatBuffer;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.LowMemoryVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class LowMemorySpriteVertexBufferObject extends LowMemoryVertexBufferObject implements ISpriteVertexBufferObject {
    public LowMemorySpriteVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(Sprite pSprite) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float packedColor = pSprite.getColor().getABGRPackedFloat();
        bufferData.put(2, packedColor);
        bufferData.put(7, packedColor);
        bufferData.put(12, packedColor);
        bufferData.put(17, packedColor);
        setDirtyOnHardware();
    }

    public void onUpdateVertices(Sprite pSprite) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float x2 = pSprite.getWidth();
        float y2 = pSprite.getHeight();
        bufferData.put(0, 0.0f);
        bufferData.put(1, 0.0f);
        bufferData.put(5, 0.0f);
        bufferData.put(6, y2);
        bufferData.put(10, x2);
        bufferData.put(11, 0.0f);
        bufferData.put(15, x2);
        bufferData.put(16, y2);
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
            bufferData.put(3, u2);
            bufferData.put(4, v);
            bufferData.put(8, u);
            bufferData.put(9, v);
            bufferData.put(13, u2);
            bufferData.put(14, v2);
            bufferData.put(18, u);
            bufferData.put(19, v2);
        } else {
            bufferData.put(3, u);
            bufferData.put(4, v);
            bufferData.put(8, u);
            bufferData.put(9, v2);
            bufferData.put(13, u2);
            bufferData.put(14, v);
            bufferData.put(18, u2);
            bufferData.put(19, v2);
        }
        setDirtyOnHardware();
    }
}
