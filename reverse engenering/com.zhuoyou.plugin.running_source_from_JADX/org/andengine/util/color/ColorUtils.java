package org.andengine.util.color;

import android.graphics.Color;
import org.andengine.util.color.constants.ColorConstants;

public class ColorUtils {
    private static final float[] HSV_TO_COLOR = new float[3];
    private static final int HSV_TO_COLOR_HUE_INDEX = 0;
    private static final int HSV_TO_COLOR_SATURATION_INDEX = 1;
    private static final int HSV_TO_COLOR_VALUE_INDEX = 2;
    private static final int INT_BITS_TO_FLOAT_MASK = -1;

    public static final int convertHSVToARGBPackedInt(float pHue, float pSaturation, float pValue) {
        HSV_TO_COLOR[0] = pHue;
        HSV_TO_COLOR[1] = pSaturation;
        HSV_TO_COLOR[2] = pValue;
        return Color.HSVToColor(HSV_TO_COLOR);
    }

    public static final Color convertHSVToColor(float pHue, float pSaturation, float pValue) {
        return convertARGBPackedIntToColor(convertHSVToARGBPackedInt(pHue, pSaturation, pValue));
    }

    public static Color convertARGBPackedIntToColor(int pARGBPackedInt) {
        return new Color(extractRedFromARGBPackedInt(pARGBPackedInt), extractGreenFromARGBPackedInt(pARGBPackedInt), extractBlueFromARGBPackedInt(pARGBPackedInt), extractAlphaFromARGBPackedInt(pARGBPackedInt));
    }

    public static Color convertABGRPackedIntToColor(int pABGRPackedInt) {
        float alpha = extractAlphaFromABGRPackedInt(pABGRPackedInt);
        float blue = extractBlueFromABGRPackedInt(pABGRPackedInt);
        return new Color(extractRedFromABGRPackedInt(pABGRPackedInt), extractGreenFromABGRPackedInt(pABGRPackedInt), blue, alpha);
    }

    public static final int convertRGBAToARGBPackedInt(float pRed, float pGreen, float pBlue, float pAlpha) {
        return (((((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pAlpha)) << 24) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pRed)) << 16)) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pGreen)) << 8)) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pBlue)) << 0);
    }

    public static final float convertRGBAToARGBPackedFloat(float pRed, float pGreen, float pBlue, float pAlpha) {
        return convertPackedIntToPackedFloat(convertRGBAToARGBPackedInt(pRed, pGreen, pBlue, pAlpha));
    }

    public static final int convertRGBAToABGRPackedInt(float pRed, float pGreen, float pBlue, float pAlpha) {
        return (((((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pAlpha)) << 24) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pBlue)) << 16)) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pGreen)) << 8)) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * pRed)) << 0);
    }

    public static final float convertRGBAToABGRPackedFloat(float pRed, float pGreen, float pBlue, float pAlpha) {
        return convertPackedIntToPackedFloat(convertRGBAToABGRPackedInt(pRed, pGreen, pBlue, pAlpha));
    }

    public static final float convertPackedIntToPackedFloat(int pPackedInt) {
        return Float.intBitsToFloat(pPackedInt & -1);
    }

    public static float extractRedFromABGRPackedInt(int pABGRPackedInt) {
        return ((float) ((pABGRPackedInt >> 0) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }

    public static float extractGreenFromABGRPackedInt(int pABGRPackedInt) {
        return ((float) ((pABGRPackedInt >> 8) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }

    public static float extractBlueFromABGRPackedInt(int pABGRPackedInt) {
        return ((float) ((pABGRPackedInt >> 16) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }

    public static float extractAlphaFromABGRPackedInt(int pABGRPackedInt) {
        return ((float) ((pABGRPackedInt >> 24) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }

    public static float extractBlueFromARGBPackedInt(int pARGBPackedInt) {
        return ((float) ((pARGBPackedInt >> 0) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }

    public static float extractGreenFromARGBPackedInt(int pARGBPackedInt) {
        return ((float) ((pARGBPackedInt >> 8) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }

    public static float extractRedFromARGBPackedInt(int pARGBPackedInt) {
        return ((float) ((pARGBPackedInt >> 16) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }

    public static float extractAlphaFromARGBPackedInt(int pARGBPackedInt) {
        return ((float) ((pARGBPackedInt >> 24) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT;
    }
}
