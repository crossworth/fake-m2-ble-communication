package org.andengine.opengl.vbo.attribute;

import org.andengine.util.exception.AndEngineRuntimeException;
import org.andengine.util.system.SystemUtils;

public class VertexBufferObjectAttributesBuilder {
    private static final boolean WORAROUND_GLES2_GLVERTEXATTRIBPOINTER_MISSING = SystemUtils.isAndroidVersionOrLower(8);
    private int mIndex;
    private int mOffset;
    private final VertexBufferObjectAttribute[] mVertexBufferObjectAttributes;

    public VertexBufferObjectAttributesBuilder(int pCount) {
        this.mVertexBufferObjectAttributes = new VertexBufferObjectAttribute[pCount];
    }

    public VertexBufferObjectAttributesBuilder add(int pLocation, String pName, int pSize, int pType, boolean pNormalized) {
        if (WORAROUND_GLES2_GLVERTEXATTRIBPOINTER_MISSING) {
            this.mVertexBufferObjectAttributes[this.mIndex] = new VertexBufferObjectAttributeFix(pLocation, pName, pSize, pType, pNormalized, this.mOffset);
        } else {
            this.mVertexBufferObjectAttributes[this.mIndex] = new VertexBufferObjectAttribute(pLocation, pName, pSize, pType, pNormalized, this.mOffset);
        }
        switch (pType) {
            case 5121:
                this.mOffset += pSize * 1;
                break;
            case 5126:
                this.mOffset += pSize * 4;
                break;
            default:
                throw new IllegalArgumentException("Unexpected pType: '" + pType + "'.");
        }
        this.mIndex++;
        return this;
    }

    public VertexBufferObjectAttributes build() {
        if (this.mIndex == this.mVertexBufferObjectAttributes.length) {
            return new VertexBufferObjectAttributes(this.mOffset, this.mVertexBufferObjectAttributes);
        }
        throw new AndEngineRuntimeException("Not enough " + VertexBufferObjectAttribute.class.getSimpleName() + "s added to this " + getClass().getSimpleName() + ".");
    }
}
