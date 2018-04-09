package org.andengine.entity.text.vbo;

import java.util.ArrayList;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.font.Letter;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.HighPerformanceVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.util.adt.list.IFloatList;

public class HighPerformanceTextVertexBufferObject extends HighPerformanceVertexBufferObject implements ITextVertexBufferObject {
    public HighPerformanceTextVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(Text pText) {
        float[] bufferData = this.mBufferData;
        float packedColor = pText.getColor().getABGRPackedFloat();
        int bufferDataOffset = 0;
        int charactersMaximum = pText.getCharactersMaximum();
        for (int i = 0; i < charactersMaximum; i++) {
            bufferData[(bufferDataOffset + 0) + 2] = packedColor;
            bufferData[(bufferDataOffset + 5) + 2] = packedColor;
            bufferData[(bufferDataOffset + 10) + 2] = packedColor;
            bufferData[(bufferDataOffset + 15) + 2] = packedColor;
            bufferData[(bufferDataOffset + 20) + 2] = packedColor;
            bufferData[(bufferDataOffset + 25) + 2] = packedColor;
            bufferDataOffset += 30;
        }
        setDirtyOnHardware();
    }

    public void onUpdateVertices(Text pText) {
        float[] bufferData = this.mBufferData;
        IFont font = pText.getFont();
        ArrayList<CharSequence> lines = pText.getLines();
        float lineHeight = font.getLineHeight();
        IFloatList lineWidths = pText.getLineWidths();
        float lineAlignmentWidth = pText.getLineAlignmentWidth();
        int charactersToDraw = 0;
        int bufferDataOffset = 0;
        int lineCount = lines.size();
        for (int row = 0; row < lineCount; row++) {
            float xBase;
            CharSequence line = (CharSequence) lines.get(row);
            switch (pText.getHorizontalAlign()) {
                case RIGHT:
                    xBase = lineAlignmentWidth - lineWidths.get(row);
                    break;
                case CENTER:
                    xBase = (lineAlignmentWidth - lineWidths.get(row)) * 0.5f;
                    break;
                default:
                    xBase = 0.0f;
                    break;
            }
            float yBase = ((float) row) * (pText.getLeading() + lineHeight);
            int lineLength = line.length();
            Letter previousLetter = null;
            for (int i = 0; i < lineLength; i++) {
                Letter letter = font.getLetter(line.charAt(i));
                if (previousLetter != null) {
                    xBase += (float) previousLetter.getKerning(letter.mCharacter);
                }
                if (!letter.isWhitespace()) {
                    float x = xBase + letter.mOffsetX;
                    float y = yBase + letter.mOffsetY;
                    float y2 = y + ((float) letter.mHeight);
                    float x2 = x + ((float) letter.mWidth);
                    float u = letter.mU;
                    float v = letter.mV;
                    float u2 = letter.mU2;
                    float v2 = letter.mV2;
                    bufferData[(bufferDataOffset + 0) + 0] = x;
                    bufferData[(bufferDataOffset + 0) + 1] = y;
                    bufferData[(bufferDataOffset + 0) + 3] = u;
                    bufferData[(bufferDataOffset + 0) + 4] = v;
                    bufferData[(bufferDataOffset + 5) + 0] = x;
                    bufferData[(bufferDataOffset + 5) + 1] = y2;
                    bufferData[(bufferDataOffset + 5) + 3] = u;
                    bufferData[(bufferDataOffset + 5) + 4] = v2;
                    bufferData[(bufferDataOffset + 10) + 0] = x2;
                    bufferData[(bufferDataOffset + 10) + 1] = y2;
                    bufferData[(bufferDataOffset + 10) + 3] = u2;
                    bufferData[(bufferDataOffset + 10) + 4] = v2;
                    bufferData[(bufferDataOffset + 15) + 0] = x2;
                    bufferData[(bufferDataOffset + 15) + 1] = y2;
                    bufferData[(bufferDataOffset + 15) + 3] = u2;
                    bufferData[(bufferDataOffset + 15) + 4] = v2;
                    bufferData[(bufferDataOffset + 20) + 0] = x2;
                    bufferData[(bufferDataOffset + 20) + 1] = y;
                    bufferData[(bufferDataOffset + 20) + 3] = u2;
                    bufferData[(bufferDataOffset + 20) + 4] = v;
                    bufferData[(bufferDataOffset + 25) + 0] = x;
                    bufferData[(bufferDataOffset + 25) + 1] = y;
                    bufferData[(bufferDataOffset + 25) + 3] = u;
                    bufferData[(bufferDataOffset + 25) + 4] = v;
                    bufferDataOffset += 30;
                    charactersToDraw++;
                }
                xBase += letter.mAdvance;
                previousLetter = letter;
            }
        }
        pText.setCharactersToDraw(charactersToDraw);
        setDirtyOnHardware();
    }
}
