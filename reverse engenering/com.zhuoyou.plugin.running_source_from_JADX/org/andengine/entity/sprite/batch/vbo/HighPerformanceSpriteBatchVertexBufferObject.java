package org.andengine.entity.sprite.batch.vbo;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.HighPerformanceVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class HighPerformanceSpriteBatchVertexBufferObject extends HighPerformanceVertexBufferObject implements ISpriteBatchVertexBufferObject {
    protected int mBufferDataOffset;

    public HighPerformanceSpriteBatchVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public int getBufferDataOffset() {
        return this.mBufferDataOffset;
    }

    public void setBufferDataOffset(int pBufferDataOffset) {
        this.mBufferDataOffset = pBufferDataOffset;
    }

    public void addWithPackedColor(ITextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, float pColorABGRPackedInt) {
        float[] bufferData = getBufferData();
        int bufferDataOffset = this.mBufferDataOffset;
        float x1 = pX1;
        float y1 = pY1;
        float x2 = pX2;
        float y2 = pY2;
        float x3 = pX3;
        float y3 = pY3;
        float x4 = pX4;
        float y4 = pY4;
        float u = pTextureRegion.getU();
        float v = pTextureRegion.getV();
        float u2 = pTextureRegion.getU2();
        float v2 = pTextureRegion.getV2();
        if (pTextureRegion.isRotated()) {
            bufferData[(bufferDataOffset + 0) + 0] = x1;
            bufferData[(bufferDataOffset + 0) + 1] = y1;
            bufferData[(bufferDataOffset + 0) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 0) + 3] = u2;
            bufferData[(bufferDataOffset + 0) + 4] = v;
            bufferData[(bufferDataOffset + 5) + 0] = x2;
            bufferData[(bufferDataOffset + 5) + 1] = y2;
            bufferData[(bufferDataOffset + 5) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 5) + 3] = u;
            bufferData[(bufferDataOffset + 5) + 4] = v;
            bufferData[(bufferDataOffset + 10) + 0] = x3;
            bufferData[(bufferDataOffset + 10) + 1] = y3;
            bufferData[(bufferDataOffset + 10) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 10) + 3] = u2;
            bufferData[(bufferDataOffset + 10) + 4] = v2;
            bufferData[(bufferDataOffset + 15) + 0] = x3;
            bufferData[(bufferDataOffset + 15) + 1] = y3;
            bufferData[(bufferDataOffset + 15) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 15) + 3] = u2;
            bufferData[(bufferDataOffset + 15) + 4] = v2;
            bufferData[(bufferDataOffset + 20) + 0] = x2;
            bufferData[(bufferDataOffset + 20) + 1] = y2;
            bufferData[(bufferDataOffset + 20) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 20) + 3] = u;
            bufferData[(bufferDataOffset + 20) + 4] = v;
            bufferData[(bufferDataOffset + 25) + 0] = x4;
            bufferData[(bufferDataOffset + 25) + 1] = y4;
            bufferData[(bufferDataOffset + 25) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 25) + 3] = u;
            bufferData[(bufferDataOffset + 25) + 4] = v2;
        } else {
            bufferData[(bufferDataOffset + 0) + 0] = x1;
            bufferData[(bufferDataOffset + 0) + 1] = y1;
            bufferData[(bufferDataOffset + 0) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 0) + 3] = u;
            bufferData[(bufferDataOffset + 0) + 4] = v;
            bufferData[(bufferDataOffset + 5) + 0] = x2;
            bufferData[(bufferDataOffset + 5) + 1] = y2;
            bufferData[(bufferDataOffset + 5) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 5) + 3] = u;
            bufferData[(bufferDataOffset + 5) + 4] = v2;
            bufferData[(bufferDataOffset + 10) + 0] = x3;
            bufferData[(bufferDataOffset + 10) + 1] = y3;
            bufferData[(bufferDataOffset + 10) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 10) + 3] = u2;
            bufferData[(bufferDataOffset + 10) + 4] = v;
            bufferData[(bufferDataOffset + 15) + 0] = x3;
            bufferData[(bufferDataOffset + 15) + 1] = y3;
            bufferData[(bufferDataOffset + 15) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 15) + 3] = u2;
            bufferData[(bufferDataOffset + 15) + 4] = v;
            bufferData[(bufferDataOffset + 20) + 0] = x2;
            bufferData[(bufferDataOffset + 20) + 1] = y2;
            bufferData[(bufferDataOffset + 20) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 20) + 3] = u;
            bufferData[(bufferDataOffset + 20) + 4] = v2;
            bufferData[(bufferDataOffset + 25) + 0] = x4;
            bufferData[(bufferDataOffset + 25) + 1] = y4;
            bufferData[(bufferDataOffset + 25) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 25) + 3] = u2;
            bufferData[(bufferDataOffset + 25) + 4] = v2;
        }
        this.mBufferDataOffset += 30;
    }

    public void addWithPackedColor(ITextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pColorABGRPackedInt) {
        float[] bufferData = getBufferData();
        int bufferDataOffset = this.mBufferDataOffset;
        float x1 = pX1;
        float y1 = pY1;
        float x2 = pX2;
        float y2 = pY2;
        float u = pTextureRegion.getU();
        float v = pTextureRegion.getV();
        float u2 = pTextureRegion.getU2();
        float v2 = pTextureRegion.getV2();
        if (pTextureRegion.isRotated()) {
            bufferData[(bufferDataOffset + 0) + 0] = x1;
            bufferData[(bufferDataOffset + 0) + 1] = y1;
            bufferData[(bufferDataOffset + 0) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 0) + 3] = u2;
            bufferData[(bufferDataOffset + 0) + 4] = v;
            bufferData[(bufferDataOffset + 5) + 0] = x1;
            bufferData[(bufferDataOffset + 5) + 1] = y2;
            bufferData[(bufferDataOffset + 5) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 5) + 3] = u;
            bufferData[(bufferDataOffset + 5) + 4] = v;
            bufferData[(bufferDataOffset + 10) + 0] = x2;
            bufferData[(bufferDataOffset + 10) + 1] = y1;
            bufferData[(bufferDataOffset + 10) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 10) + 3] = u2;
            bufferData[(bufferDataOffset + 10) + 4] = v2;
            bufferData[(bufferDataOffset + 15) + 0] = x2;
            bufferData[(bufferDataOffset + 15) + 1] = y1;
            bufferData[(bufferDataOffset + 15) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 15) + 3] = u2;
            bufferData[(bufferDataOffset + 15) + 4] = v2;
            bufferData[(bufferDataOffset + 20) + 0] = x1;
            bufferData[(bufferDataOffset + 20) + 1] = y2;
            bufferData[(bufferDataOffset + 20) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 20) + 3] = u;
            bufferData[(bufferDataOffset + 20) + 4] = v;
            bufferData[(bufferDataOffset + 25) + 0] = x2;
            bufferData[(bufferDataOffset + 25) + 1] = y2;
            bufferData[(bufferDataOffset + 25) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 25) + 3] = u;
            bufferData[(bufferDataOffset + 25) + 4] = v2;
        } else {
            bufferData[(bufferDataOffset + 0) + 0] = x1;
            bufferData[(bufferDataOffset + 0) + 1] = y1;
            bufferData[(bufferDataOffset + 0) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 0) + 3] = u;
            bufferData[(bufferDataOffset + 0) + 4] = v;
            bufferData[(bufferDataOffset + 5) + 0] = x1;
            bufferData[(bufferDataOffset + 5) + 1] = y2;
            bufferData[(bufferDataOffset + 5) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 5) + 3] = u;
            bufferData[(bufferDataOffset + 5) + 4] = v2;
            bufferData[(bufferDataOffset + 10) + 0] = x2;
            bufferData[(bufferDataOffset + 10) + 1] = y1;
            bufferData[(bufferDataOffset + 10) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 10) + 3] = u2;
            bufferData[(bufferDataOffset + 10) + 4] = v;
            bufferData[(bufferDataOffset + 15) + 0] = x2;
            bufferData[(bufferDataOffset + 15) + 1] = y1;
            bufferData[(bufferDataOffset + 15) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 15) + 3] = u2;
            bufferData[(bufferDataOffset + 15) + 4] = v;
            bufferData[(bufferDataOffset + 20) + 0] = x1;
            bufferData[(bufferDataOffset + 20) + 1] = y2;
            bufferData[(bufferDataOffset + 20) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 20) + 3] = u;
            bufferData[(bufferDataOffset + 20) + 4] = v2;
            bufferData[(bufferDataOffset + 25) + 0] = x2;
            bufferData[(bufferDataOffset + 25) + 1] = y2;
            bufferData[(bufferDataOffset + 25) + 2] = pColorABGRPackedInt;
            bufferData[(bufferDataOffset + 25) + 3] = u2;
            bufferData[(bufferDataOffset + 25) + 4] = v2;
        }
        this.mBufferDataOffset += 30;
    }
}
