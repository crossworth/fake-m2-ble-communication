package org.andengine.opengl.texture.atlas.bitmap.source.decorator;

import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape;
import org.andengine.util.adt.array.ArrayUtils;

public class RadialGradientFillBitmapTextureAtlasSourceDecorator extends BaseShapeBitmapTextureAtlasSourceDecorator {
    private static final float[] POSITIONS_DEFAULT = new float[]{0.0f, 1.0f};
    protected final int[] mColors;
    protected final float[] mPositions;
    protected final RadialGradientDirection mRadialGradientDirection;

    public enum RadialGradientDirection {
        INSIDE_OUT,
        OUTSIDE_IN
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pFromColor, int pToColor, RadialGradientDirection pRadialGradientDirection) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pFromColor, pToColor, pRadialGradientDirection, null);
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pFromColor, int pToColor, RadialGradientDirection pRadialGradientDirection, TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        IBitmapTextureAtlasSource iBitmapTextureAtlasSource = pBitmapTextureAtlasSource;
        IBitmapTextureAtlasSourceDecoratorShape iBitmapTextureAtlasSourceDecoratorShape = pBitmapTextureAtlasSourceDecoratorShape;
        this(iBitmapTextureAtlasSource, iBitmapTextureAtlasSourceDecoratorShape, new int[]{pFromColor, pToColor}, POSITIONS_DEFAULT, pRadialGradientDirection, pTextureAtlasSourceDecoratorOptions);
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int[] pColors, float[] pPositions, RadialGradientDirection pRadialGradientDirection) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColors, pPositions, pRadialGradientDirection, null);
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int[] pColors, float[] pPositions, RadialGradientDirection pRadialGradientDirection, TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        super(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pTextureAtlasSourceDecoratorOptions);
        this.mColors = pColors;
        this.mPositions = pPositions;
        this.mRadialGradientDirection = pRadialGradientDirection;
        this.mPaint.setStyle(Style.FILL);
        float centerX = ((float) pBitmapTextureAtlasSource.getTextureWidth()) * 0.5f;
        float centerY = ((float) pBitmapTextureAtlasSource.getTextureHeight()) * 0.5f;
        float radius = Math.max(centerX, centerY);
        switch (pRadialGradientDirection) {
            case INSIDE_OUT:
                this.mPaint.setShader(new RadialGradient(centerX, centerY, radius, pColors, pPositions, TileMode.CLAMP));
                return;
            case OUTSIDE_IN:
                ArrayUtils.reverse(pColors);
                this.mPaint.setShader(new RadialGradient(centerX, centerY, radius, pColors, pPositions, TileMode.CLAMP));
                return;
            default:
                return;
        }
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator deepCopy() {
        return new RadialGradientFillBitmapTextureAtlasSourceDecorator(this.mBitmapTextureAtlasSource, this.mBitmapTextureAtlasSourceDecoratorShape, this.mColors, this.mPositions, this.mRadialGradientDirection, this.mTextureAtlasSourceDecoratorOptions);
    }
}
