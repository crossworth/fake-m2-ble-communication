package org.andengine.opengl.font;

import java.util.List;
import org.andengine.entity.text.AutoWrap;
import org.andengine.util.TextUtils;
import org.andengine.util.exception.MethodNotYetImplementedException;

public class FontUtils {
    private static final int UNSPECIFIED = -1;

    public enum MeasureDirection {
        FORWARDS,
        BACKWARDS
    }

    public static float measureText(IFont pFont, CharSequence pText) {
        return measureText(pFont, pText, null);
    }

    public static float measureText(IFont pFont, CharSequence pText, int pStart, int pEnd) {
        return measureText(pFont, pText, pStart, pEnd, null);
    }

    public static float measureText(IFont pFont, CharSequence pText, float[] pWidths) {
        return measureText(pFont, pText, 0, pText.length(), pWidths);
    }

    public static float measureText(IFont pFont, CharSequence pText, int pStart, int pEnd, float[] pWidths) {
        int textLength = pEnd - pStart;
        if (pStart == pEnd) {
            return 0.0f;
        }
        if (textLength == 1) {
            return (float) pFont.getLetter(pText.charAt(pStart)).mWidth;
        }
        Letter previousLetter = null;
        float width = 0.0f;
        int pos = pStart;
        int i = 0;
        while (pos < pEnd) {
            Letter letter = pFont.getLetter(pText.charAt(pos));
            if (previousLetter != null) {
                width += (float) previousLetter.getKerning(letter.mCharacter);
            }
            previousLetter = letter;
            if (pos == pEnd - 1) {
                width += letter.mOffsetX + ((float) letter.mWidth);
            } else {
                width += letter.mAdvance;
            }
            if (pWidths != null) {
                pWidths[i] = width;
            }
            pos++;
            i++;
        }
        return width;
    }

    public static int breakText(IFont pFont, CharSequence pText, MeasureDirection pMeasureDirection, float pWidthMaximum, float[] pMeasuredWidth) {
        throw new MethodNotYetImplementedException();
    }

    public static <L extends List<CharSequence>> L splitLines(CharSequence pText, L pResult) {
        return TextUtils.split(pText, '\n', pResult);
    }

    public static <L extends List<CharSequence>> L splitLines(IFont pFont, CharSequence pText, L pResult, AutoWrap pAutoWrap, float pAutoWrapWidth) {
        switch (pAutoWrap) {
            case LETTERS:
                return splitLinesByLetters(pFont, pText, pResult, pAutoWrapWidth);
            case WORDS:
                return splitLinesByWords(pFont, pText, pResult, pAutoWrapWidth);
            case CJK:
                return splitLinesByCJK(pFont, pText, pResult, pAutoWrapWidth);
            default:
                throw new IllegalArgumentException("Unexpected " + AutoWrap.class.getSimpleName() + ": '" + pAutoWrap + "'.");
        }
    }

    private static <L extends List<CharSequence>> L splitLinesByLetters(IFont pFont, CharSequence pText, L pResult, float pAutoWrapWidth) {
        int textLength = pText.length();
        int lineStart = 0;
        int lineEnd = 0;
        int lastNonWhitespace = 0;
        boolean charsAvailable = false;
        int i = 0;
        while (i < textLength) {
            if (pText.charAt(i) != ' ') {
                if (charsAvailable) {
                    lastNonWhitespace = i + 1;
                } else {
                    charsAvailable = true;
                    lineStart = i;
                    lastNonWhitespace = lineStart + 1;
                    lineEnd = lastNonWhitespace;
                }
            }
            if (charsAvailable) {
                float lookaheadLineWidth = measureText(pFont, pText, lineStart, lastNonWhitespace);
                if (i == textLength + -1) {
                    if (lookaheadLineWidth <= pAutoWrapWidth) {
                        pResult.add(pText.subSequence(lineStart, lastNonWhitespace));
                    } else {
                        pResult.add(pText.subSequence(lineStart, lineEnd));
                        if (lineStart != i) {
                            pResult.add(pText.subSequence(i, lastNonWhitespace));
                        }
                    }
                } else if (lookaheadLineWidth <= pAutoWrapWidth) {
                    lineEnd = lastNonWhitespace;
                } else {
                    pResult.add(pText.subSequence(lineStart, lineEnd));
                    i = lineEnd - 1;
                    charsAvailable = false;
                }
            }
            i++;
        }
        return pResult;
    }

    private static <L extends List<CharSequence>> L splitLinesByWords(IFont pFont, CharSequence pText, L pResult, float pAutoWrapWidth) {
        int textLength = pText.length();
        if (textLength != 0) {
            float spaceWidth = pFont.getLetter(' ').mAdvance;
            int lastWordEnd = -1;
            int lineStart = -1;
            int lineEnd = -1;
            float lineWidthRemaining = pAutoWrapWidth;
            boolean firstWordInLine = true;
            int i = 0;
            while (i < textLength) {
                int spacesSkipped = 0;
                while (i < textLength && pText.charAt(i) == ' ') {
                    i++;
                    spacesSkipped++;
                }
                int wordStart = i;
                if (lineStart == -1) {
                    lineStart = wordStart;
                }
                while (i < textLength && pText.charAt(i) != ' ') {
                    i++;
                }
                int wordEnd = i;
                if (wordStart != wordEnd) {
                    float widthNeeded;
                    float wordWidth = measureText(pFont, pText, wordStart, wordEnd);
                    if (firstWordInLine) {
                        widthNeeded = wordWidth;
                    } else {
                        widthNeeded = (((float) spacesSkipped) * spaceWidth) + wordWidth;
                    }
                    if (widthNeeded <= lineWidthRemaining) {
                        if (firstWordInLine) {
                            firstWordInLine = false;
                        } else {
                            lineWidthRemaining -= getAdvanceCorrection(pFont, pText, lastWordEnd - 1);
                        }
                        lineWidthRemaining -= widthNeeded;
                        lastWordEnd = wordEnd;
                        lineEnd = wordEnd;
                        if (wordEnd == textLength) {
                            pResult.add(pText.subSequence(lineStart, lineEnd));
                            break;
                        }
                    } else if (firstWordInLine) {
                        if (wordWidth < pAutoWrapWidth) {
                            lineWidthRemaining = pAutoWrapWidth - wordWidth;
                            if (wordEnd == textLength) {
                                pResult.add(pText.subSequence(wordStart, wordEnd));
                                break;
                            }
                        }
                        pResult.add(pText.subSequence(wordStart, wordEnd));
                        lineWidthRemaining = pAutoWrapWidth;
                        firstWordInLine = true;
                        lastWordEnd = -1;
                        lineStart = -1;
                        lineEnd = -1;
                    } else {
                        pResult.add(pText.subSequence(lineStart, lineEnd));
                        if (wordEnd == textLength) {
                            pResult.add(pText.subSequence(wordStart, wordEnd));
                            break;
                        }
                        lineWidthRemaining = pAutoWrapWidth - wordWidth;
                        firstWordInLine = false;
                        lastWordEnd = wordEnd;
                        lineStart = wordStart;
                        lineEnd = wordEnd;
                    }
                } else if (!firstWordInLine) {
                    pResult.add(pText.subSequence(lineStart, lineEnd));
                }
            }
        }
        return pResult;
    }

    private static <L extends List<CharSequence>> L splitLinesByCJK(IFont pFont, CharSequence pText, L pResult, float pAutoWrapWidth) {
        int textLength = pText.length();
        int lineStart = 0;
        int lineEnd = 0;
        while (lineStart < textLength && pText.charAt(lineStart) == ' ') {
            lineStart++;
            lineEnd++;
        }
        int i = lineEnd;
        while (i < textLength) {
            lineStart = lineEnd;
            boolean charsAvailable = true;
            while (i < textLength) {
                int j = lineEnd;
                while (j < textLength && pText.charAt(j) == ' ') {
                    j++;
                }
                if (j == textLength) {
                    if (lineStart == lineEnd) {
                        charsAvailable = false;
                    }
                    i = textLength;
                } else {
                    lineEnd++;
                    if (measureText(pFont, pText, lineStart, lineEnd) > pAutoWrapWidth) {
                        if (lineStart < lineEnd - 1) {
                            lineEnd--;
                        }
                        pResult.add(pText.subSequence(lineStart, lineEnd));
                        charsAvailable = false;
                        i = lineEnd;
                    } else {
                        i = lineEnd;
                    }
                }
                if (charsAvailable) {
                    pResult.add(pText.subSequence(lineStart, lineEnd));
                }
            }
            if (charsAvailable) {
                pResult.add(pText.subSequence(lineStart, lineEnd));
            }
        }
        return pResult;
    }

    private static float getAdvanceCorrection(IFont pFont, CharSequence pText, int pIndex) {
        Letter lastWordLastLetter = pFont.getLetter(pText.charAt(pIndex));
        return (-(lastWordLastLetter.mOffsetX + ((float) lastWordLastLetter.mWidth))) + lastWordLastLetter.mAdvance;
    }
}
