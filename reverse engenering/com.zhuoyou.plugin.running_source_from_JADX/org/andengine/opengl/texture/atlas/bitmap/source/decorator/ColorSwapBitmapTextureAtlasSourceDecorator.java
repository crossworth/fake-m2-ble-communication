package org.andengine.opengl.texture.atlas.bitmap.source.decorator;

import android.graphics.AvoidXfermode;
import android.graphics.AvoidXfermode.Mode;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape;
import org.andengine.util.color.Color;

public class ColorSwapBitmapTextureAtlasSourceDecorator extends BaseShapeBitmapTextureAtlasSourceDecorator {
    private static final int TOLERANCE_DEFAULT = 0;
    protected final int mColorKeyColorARGBPackedInt;
    protected final int mColorSwapColorARGBPackedInt;
    protected final int mTolerance;

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, Color pColorKeyColor, Color pColorSwapColor) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColor.getARGBPackedInt(), pColorSwapColor.getARGBPackedInt());
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColorARGBPackedInt, int pColorSwapColorARGBPackedInt) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColorARGBPackedInt, 0, pColorSwapColorARGBPackedInt, null);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, Color pColorKeyColor, Color pColorSwapColor, TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColor.getARGBPackedInt(), pColorSwapColor.getARGBPackedInt(), pTextureAtlasSourceDecoratorOptions);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColorARGBPackedInt, int pColorSwapColorARGBPackedInt, TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColorARGBPackedInt, 0, pColorSwapColorARGBPackedInt, pTextureAtlasSourceDecoratorOptions);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, Color pColorKeyColor, int pTolerance, Color pColorSwapColor) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColor.getARGBPackedInt(), pTolerance, pColorSwapColor.getARGBPackedInt());
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColorARGBPackedInt, int pTolerance, int pColorSwapColorARGBPackedInt) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColorARGBPackedInt, pTolerance, pColorSwapColorARGBPackedInt, null);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, Color pColorKeyColor, int pTolerance, Color pColorSwapColor, TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColor.getARGBPackedInt(), pTolerance, pColorSwapColor.getARGBPackedInt(), pTextureAtlasSourceDecoratorOptions);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColorARGBPackedInt, int pTolerance, int pColorSwapColorARGBPackedInt, TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        super(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pTextureAtlasSourceDecoratorOptions);
        this.mColorKeyColorARGBPackedInt = pColorKeyColorARGBPackedInt;
        this.mTolerance = pTolerance;
        this.mColorSwapColorARGBPackedInt = pColorSwapColorARGBPackedInt;
        this.mPaint.setXfermode(new AvoidXfermode(pColorKeyColorARGBPackedInt, pTolerance, Mode.TARGET));
        this.mPaint.setColor(pColorSwapColorARGBPackedInt);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator deepCopy() {
        return new ColorSwapBitmapTextureAtlasSourceDecorator(this.mBitmapTextureAtlasSource, this.mBitmapTextureAtlasSourceDecoratorShape, this.mColorKeyColorARGBPackedInt, this.mTolerance, this.mColorSwapColorARGBPackedInt, this.mTextureAtlasSourceDecoratorOptions);
    }
}
