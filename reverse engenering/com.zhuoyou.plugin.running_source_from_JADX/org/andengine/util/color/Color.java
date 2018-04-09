package org.andengine.util.color;

import org.andengine.util.color.constants.ColorConstants;

public class Color {
    public static final int ABGR_PACKED_ALPHA_CLEAR = 16777215;
    public static final int ABGR_PACKED_ALPHA_SHIFT = 24;
    public static final int ABGR_PACKED_BLUE_CLEAR = -16711681;
    public static final int ABGR_PACKED_BLUE_SHIFT = 16;
    public static final int ABGR_PACKED_GREEN_CLEAR = -65281;
    public static final int ABGR_PACKED_GREEN_SHIFT = 8;
    public static final int ABGR_PACKED_RED_CLEAR = -256;
    public static final int ABGR_PACKED_RED_SHIFT = 0;
    public static final int ARGB_PACKED_ALPHA_CLEAR = 16777215;
    public static final int ARGB_PACKED_ALPHA_SHIFT = 24;
    public static final int ARGB_PACKED_BLUE_CLEAR = -256;
    public static final int ARGB_PACKED_BLUE_SHIFT = 0;
    public static final int ARGB_PACKED_GREEN_CLEAR = -65281;
    public static final int ARGB_PACKED_GREEN_SHIFT = 8;
    public static final int ARGB_PACKED_RED_CLEAR = -16711681;
    public static final int ARGB_PACKED_RED_SHIFT = 16;
    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final float BLACK_ABGR_PACKED_FLOAT = BLACK.getABGRPackedFloat();
    public static final int BLACK_ABGR_PACKED_INT = BLACK.getABGRPackedInt();
    public static final int BLACK_ARGB_PACKED_INT = BLACK.getARGBPackedInt();
    public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    public static final float BLUE_ABGR_PACKED_FLOAT = BLUE.getABGRPackedFloat();
    public static final int BLUE_ABGR_PACKED_INT = BLUE.getABGRPackedInt();
    public static final int BLUE_ARGB_PACKED_INT = BLUE.getARGBPackedInt();
    public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f, 1.0f);
    public static final float CYAN_ABGR_PACKED_FLOAT = CYAN.getABGRPackedFloat();
    public static final int CYAN_ABGR_PACKED_INT = CYAN.getABGRPackedInt();
    public static final int CYAN_ARGB_PACKED_INT = CYAN.getARGBPackedInt();
    public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    public static final float GREEN_ABGR_PACKED_FLOAT = GREEN.getABGRPackedFloat();
    public static final int GREEN_ABGR_PACKED_INT = GREEN.getABGRPackedInt();
    public static final int GREEN_ARGB_PACKED_INT = GREEN.getARGBPackedInt();
    public static final Color PINK = new Color(1.0f, 0.0f, 1.0f, 1.0f);
    public static final float PINK_ABGR_PACKED_FLOAT = PINK.getABGRPackedFloat();
    public static final int PINK_ABGR_PACKED_INT = PINK.getABGRPackedInt();
    public static final int PINK_ARGB_PACKED_INT = PINK.getARGBPackedInt();
    public static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static final float RED_ABGR_PACKED_FLOAT = RED.getABGRPackedFloat();
    public static final int RED_ABGR_PACKED_INT = RED.getABGRPackedInt();
    public static final int RED_ARGB_PACKED_INT = RED.getARGBPackedInt();
    public static final Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final float TRANSPARENT_ABGR_PACKED_FLOAT = TRANSPARENT.getABGRPackedFloat();
    public static final int TRANSPARENT_ABGR_PACKED_INT = TRANSPARENT.getABGRPackedInt();
    public static final int TRANSPARENT_ARGB_PACKED_INT = TRANSPARENT.getARGBPackedInt();
    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final float WHITE_ABGR_PACKED_FLOAT = WHITE.getABGRPackedFloat();
    public static final int WHITE_ABGR_PACKED_INT = WHITE.getABGRPackedInt();
    public static final int WHITE_ARGB_PACKED_INT = WHITE.getARGBPackedInt();
    public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f, 1.0f);
    public static final float YELLOW_ABGR_PACKED_FLOAT = YELLOW.getABGRPackedFloat();
    public static final int YELLOW_ABGR_PACKED_INT = YELLOW.getABGRPackedInt();
    public static final int YELLOW_ARGB_PACKED_INT = YELLOW.getARGBPackedInt();
    private float mABGRPackedFloat;
    private int mABGRPackedInt;
    private float mAlpha;
    private float mBlue;
    private float mGreen;
    private float mRed;

    public Color(Color pColor) {
        set(pColor);
    }

    public Color(float pRed, float pGreen, float pBlue) {
        this(pRed, pGreen, pBlue, 1.0f);
    }

    public Color(float pRed, float pGreen, float pBlue, float pAlpha) {
        set(pRed, pGreen, pBlue, pAlpha);
    }

    public final float getRed() {
        return this.mRed;
    }

    public final float getGreen() {
        return this.mGreen;
    }

    public final float getBlue() {
        return this.mBlue;
    }

    public final float getAlpha() {
        return this.mAlpha;
    }

    public final void setRed(float pRed) {
        this.mRed = pRed;
        packABGRRed();
    }

    public final boolean setRedChecking(float pRed) {
        if (this.mRed == pRed) {
            return false;
        }
        this.mRed = pRed;
        packABGRRed();
        return true;
    }

    public final void setGreen(float pGreen) {
        this.mGreen = pGreen;
        packABGRGreen();
    }

    public final boolean setGreenChecking(float pGreen) {
        if (this.mGreen == pGreen) {
            return false;
        }
        this.mGreen = pGreen;
        packABGRGreen();
        return true;
    }

    public final void setBlue(float pBlue) {
        this.mBlue = pBlue;
        packABGRBlue();
    }

    public final boolean setBlueChecking(float pBlue) {
        if (this.mBlue == pBlue) {
            return false;
        }
        this.mBlue = pBlue;
        packABGRBlue();
        return true;
    }

    public final void setAlpha(float pAlpha) {
        this.mAlpha = pAlpha;
        packABGRAlpha();
    }

    public final boolean setAlphaChecking(float pAlpha) {
        if (this.mAlpha == pAlpha) {
            return false;
        }
        this.mAlpha = pAlpha;
        packABGRAlpha();
        return true;
    }

    public final void set(float pRed, float pGreen, float pBlue) {
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
        packABGR();
    }

    public final boolean setChecking(float pRed, float pGreen, float pBlue) {
        if (this.mRed == pRed && this.mGreen == pGreen && this.mBlue == pBlue) {
            return false;
        }
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
        packABGR();
        return true;
    }

    public final void set(float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
        this.mAlpha = pAlpha;
        packABGR();
    }

    public final boolean setChecking(float pRed, float pGreen, float pBlue, float pAlpha) {
        if (this.mAlpha == pAlpha && this.mRed == pRed && this.mGreen == pGreen && this.mBlue == pBlue) {
            return false;
        }
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
        this.mAlpha = pAlpha;
        packABGR();
        return true;
    }

    public final void set(Color pColor) {
        this.mRed = pColor.mRed;
        this.mGreen = pColor.mGreen;
        this.mBlue = pColor.mBlue;
        this.mAlpha = pColor.mAlpha;
        this.mABGRPackedInt = pColor.mABGRPackedInt;
        this.mABGRPackedFloat = pColor.mABGRPackedFloat;
    }

    public final boolean setChecking(Color pColor) {
        if (this.mABGRPackedInt == pColor.mABGRPackedInt) {
            return false;
        }
        this.mRed = pColor.mRed;
        this.mGreen = pColor.mGreen;
        this.mBlue = pColor.mBlue;
        this.mAlpha = pColor.mAlpha;
        this.mABGRPackedInt = pColor.mABGRPackedInt;
        this.mABGRPackedFloat = pColor.mABGRPackedFloat;
        return true;
    }

    public final int getABGRPackedInt() {
        return this.mABGRPackedInt;
    }

    public final float getABGRPackedFloat() {
        return this.mABGRPackedFloat;
    }

    public final int getARGBPackedInt() {
        return ColorUtils.convertRGBAToARGBPackedInt(this.mRed, this.mGreen, this.mBlue, this.mAlpha);
    }

    public final void reset() {
        set(WHITE);
    }

    public int hashCode() {
        return this.mABGRPackedInt;
    }

    public boolean equals(Object pObject) {
        if (this == pObject) {
            return true;
        }
        if (pObject == null || getClass() != pObject.getClass()) {
            return false;
        }
        return equals((Color) pObject);
    }

    public String toString() {
        return "[Red: " + this.mRed + ", Green: " + this.mGreen + ", Blue: " + this.mBlue + ", Alpha: " + this.mAlpha + "]";
    }

    public boolean equals(Color pColor) {
        return this.mABGRPackedInt == pColor.mABGRPackedInt;
    }

    private final void packABGRRed() {
        this.mABGRPackedInt = (this.mABGRPackedInt & -256) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * this.mRed)) << -256);
        this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
    }

    private final void packABGRGreen() {
        this.mABGRPackedInt = (this.mABGRPackedInt & -65281) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * this.mGreen)) << -65281);
        this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
    }

    private final void packABGRBlue() {
        this.mABGRPackedInt = (this.mABGRPackedInt & -16711681) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * this.mBlue)) << -16711681);
        this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
    }

    private final void packABGRAlpha() {
        this.mABGRPackedInt = (this.mABGRPackedInt & 16777215) | (((int) (ColorConstants.COLOR_FACTOR_INT_TO_FLOAT * this.mAlpha)) << 24);
        this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
    }

    private final void packABGR() {
        this.mABGRPackedInt = ColorUtils.convertRGBAToABGRPackedInt(this.mRed, this.mGreen, this.mBlue, this.mAlpha);
        this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
    }

    public final void mix(Color pColorA, float pPercentageA, Color pColorB, float pPercentageB) {
        set((pColorA.mRed * pPercentageA) + (pColorB.mRed * pPercentageB), (pColorA.mGreen * pPercentageA) + (pColorB.mGreen * pPercentageB), (pColorA.mBlue * pPercentageA) + (pColorB.mBlue * pPercentageB), (pColorA.mAlpha * pPercentageA) + (pColorB.mAlpha * pPercentageB));
    }
}
