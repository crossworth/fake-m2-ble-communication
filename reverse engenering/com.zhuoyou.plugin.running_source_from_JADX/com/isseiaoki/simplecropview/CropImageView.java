package com.isseiaoki.simplecropview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.isseiaoki.simplecropview.animation.SimpleValueAnimator;
import com.isseiaoki.simplecropview.animation.SimpleValueAnimatorListener;
import com.isseiaoki.simplecropview.animation.ValueAnimatorV14;
import com.isseiaoki.simplecropview.animation.ValueAnimatorV8;
import com.isseiaoki.simplecropview.callback.Callback;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.isseiaoki.simplecropview.util.Logger;
import com.isseiaoki.simplecropview.util.Utils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CropImageView extends ImageView {
    private static final int DEBUG_TEXT_SIZE_IN_DP = 15;
    private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 100;
    private static final float DEFAULT_INITIAL_FRAME_SCALE = 1.0f;
    private static final int FRAME_STROKE_WEIGHT_IN_DP = 1;
    private static final int GUIDE_STROKE_WEIGHT_IN_DP = 1;
    private static final int HANDLE_SIZE_IN_DP = 14;
    private static final int MIN_FRAME_SIZE_IN_DP = 50;
    private static final String TAG = CropImageView.class.getSimpleName();
    private static final int TRANSLUCENT_BLACK = -1157627904;
    private static final int TRANSLUCENT_WHITE = -1140850689;
    private static final int TRANSPARENT = 0;
    private static final int WHITE = -1;
    private final Interpolator DEFAULT_INTERPOLATOR;
    private float mAngle;
    private int mAnimationDurationMillis;
    private SimpleValueAnimator mAnimator;
    private int mBackgroundColor;
    private PointF mCenter;
    private CompressFormat mCompressFormat;
    private int mCompressQuality;
    private CropCallback mCropCallback;
    private CropMode mCropMode;
    private PointF mCustomRatio;
    private ExecutorService mExecutor;
    private int mExifRotation;
    private int mFrameColor;
    private RectF mFrameRect;
    private float mFrameStrokeWeight;
    private int mGuideColor;
    private ShowMode mGuideShowMode;
    private float mGuideStrokeWeight;
    private int mHandleColor;
    private ShowMode mHandleShowMode;
    private int mHandleSize;
    private Handler mHandler;
    private RectF mImageRect;
    private float mImgHeight;
    private float mImgWidth;
    private float mInitialFrameScale;
    private int mInputImageHeight;
    private int mInputImageWidth;
    private Interpolator mInterpolator;
    private boolean mIsAnimating;
    private boolean mIsAnimationEnabled;
    private boolean mIsCropEnabled;
    private boolean mIsCropping;
    private boolean mIsDebug;
    private boolean mIsEnabled;
    private boolean mIsHandleShadowEnabled;
    private boolean mIsInitialized;
    private boolean mIsLoading;
    private boolean mIsRotating;
    private float mLastX;
    private float mLastY;
    private LoadCallback mLoadCallback;
    private Matrix mMatrix;
    private float mMinFrameSize;
    private int mOutputHeight;
    private int mOutputImageHeight;
    private int mOutputImageWidth;
    private int mOutputMaxHeight;
    private int mOutputMaxWidth;
    private int mOutputWidth;
    private int mOverlayColor;
    private Paint mPaintBitmap;
    private Paint mPaintDebug;
    private Paint mPaintFrame;
    private Paint mPaintTranslucent;
    private SaveCallback mSaveCallback;
    private Uri mSaveUri;
    private float mScale;
    private boolean mShowGuide;
    private boolean mShowHandle;
    private Uri mSourceUri;
    private TouchArea mTouchArea;
    private int mTouchPadding;
    private int mViewHeight;
    private int mViewWidth;

    class C10724 implements Runnable {
        C10724() {
        }

        public void run() {
            CropImageView.this.mIsLoading = true;
            CropImageView.this.mExifRotation = Utils.getExifOrientation(CropImageView.this.getContext(), CropImageView.this.mSourceUri);
            int maxSize = Utils.getMaxSize();
            int requestSize = Math.max(CropImageView.this.mViewWidth, CropImageView.this.mViewHeight);
            if (requestSize == 0) {
                requestSize = maxSize;
            }
            try {
                final Bitmap sampledBitmap = Utils.decodeSampledBitmapFromUri(CropImageView.this.getContext(), CropImageView.this.mSourceUri, requestSize);
                CropImageView.this.mInputImageWidth = Utils.sInputImageWidth;
                CropImageView.this.mInputImageHeight = Utils.sInputImageHeight;
                CropImageView.this.mHandler.post(new Runnable() {
                    public void run() {
                        CropImageView.this.mAngle = (float) CropImageView.this.mExifRotation;
                        CropImageView.this.setImageBitmap(sampledBitmap);
                        if (CropImageView.this.mLoadCallback != null) {
                            CropImageView.this.mLoadCallback.onSuccess();
                        }
                        CropImageView.this.mIsLoading = false;
                    }
                });
            } catch (OutOfMemoryError e) {
                Logger.m3300e("OOM Error: " + e.getMessage(), e);
                CropImageView.this.postErrorOnMainThread(CropImageView.this.mLoadCallback);
                CropImageView.this.mIsLoading = false;
            } catch (Exception e2) {
                Logger.m3300e("An unexpected error has occurred: " + e2.getMessage(), e2);
                CropImageView.this.postErrorOnMainThread(CropImageView.this.mLoadCallback);
                CropImageView.this.mIsLoading = false;
            }
        }
    }

    class C10756 implements Runnable {
        C10756() {
        }

        public void run() {
            Bitmap cropped;
            if (CropImageView.this.mSourceUri == null) {
                cropped = CropImageView.this.getCroppedBitmap();
            } else {
                cropped = CropImageView.this.decodeRegion();
                if (CropImageView.this.mCropMode == CropMode.CIRCLE) {
                    Bitmap circle = CropImageView.this.getCircularBitmap(cropped);
                    if (cropped != CropImageView.this.getBitmap()) {
                        cropped.recycle();
                    }
                    cropped = circle;
                }
            }
            if (cropped != null) {
                cropped = CropImageView.this.scaleBitmapIfNeeded(cropped);
                final Bitmap tmp = cropped;
                CropImageView.this.mOutputImageWidth = tmp.getWidth();
                CropImageView.this.mOutputImageHeight = tmp.getHeight();
                CropImageView.this.mHandler.post(new Runnable() {
                    public void run() {
                        if (CropImageView.this.mCropCallback != null) {
                            CropImageView.this.mCropCallback.onSuccess(tmp);
                        }
                        if (CropImageView.this.mIsDebug) {
                            CropImageView.this.invalidate();
                        }
                    }
                });
            } else {
                CropImageView.this.postErrorOnMainThread(CropImageView.this.mCropCallback);
            }
            if (CropImageView.this.mSaveUri == null) {
                CropImageView.this.postErrorOnMainThread(CropImageView.this.mSaveCallback);
                return;
            }
            CropImageView.this.saveToFile(cropped, CropImageView.this.mSaveUri);
            CropImageView.this.mIsCropping = false;
        }
    }

    public enum CropMode {
        FIT_IMAGE(0),
        RATIO_4_3(1),
        RATIO_3_4(2),
        SQUARE(3),
        RATIO_16_9(4),
        RATIO_9_16(5),
        FREE(6),
        CUSTOM(7),
        CIRCLE(8),
        CIRCLE_SQUARE(9);
        
        private final int ID;

        private CropMode(int id) {
            this.ID = id;
        }

        public int getId() {
            return this.ID;
        }
    }

    public enum RotateDegrees {
        ROTATE_90D(90),
        ROTATE_180D(180),
        ROTATE_270D(270),
        ROTATE_M90D(-90),
        ROTATE_M180D(-180),
        ROTATE_M270D(-270);
        
        private final int VALUE;

        private RotateDegrees(int value) {
            this.VALUE = value;
        }

        public int getValue() {
            return this.VALUE;
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator CREATOR = new C10771();
        float angle;
        int animationDuration;
        int backgroundColor;
        CompressFormat compressFormat;
        int compressQuality;
        float customRatioX;
        float customRatioY;
        int exifRotation;
        int frameColor;
        float frameStrokeWeight;
        int guideColor;
        ShowMode guideShowMode;
        float guideStrokeWeight;
        int handleColor;
        ShowMode handleShowMode;
        int handleSize;
        Bitmap image;
        float initialFrameScale;
        int inputImageHeight;
        int inputImageWidth;
        boolean isAnimationEnabled;
        boolean isCropEnabled;
        boolean isDebug;
        boolean isHandleShadowEnabled;
        float minFrameSize;
        CropMode mode;
        int outputHeight;
        int outputImageHeight;
        int outputImageWidth;
        int outputMaxHeight;
        int outputMaxWidth;
        int outputWidth;
        int overlayColor;
        Uri saveUri;
        boolean showGuide;
        boolean showHandle;
        Uri sourceUri;
        int touchPadding;

        static class C10771 implements Creator {
            C10771() {
            }

            public SavedState createFromParcel(Parcel inParcel) {
                return new SavedState(inParcel);
            }

            public SavedState[] newArray(int inSize) {
                return new SavedState[inSize];
            }
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            boolean z;
            boolean z2 = true;
            super(in);
            this.image = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
            this.mode = (CropMode) in.readSerializable();
            this.backgroundColor = in.readInt();
            this.overlayColor = in.readInt();
            this.frameColor = in.readInt();
            this.guideShowMode = (ShowMode) in.readSerializable();
            this.handleShowMode = (ShowMode) in.readSerializable();
            this.showGuide = in.readInt() != 0;
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.showHandle = z;
            this.handleSize = in.readInt();
            this.touchPadding = in.readInt();
            this.minFrameSize = in.readFloat();
            this.customRatioX = in.readFloat();
            this.customRatioY = in.readFloat();
            this.frameStrokeWeight = in.readFloat();
            this.guideStrokeWeight = in.readFloat();
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.isCropEnabled = z;
            this.handleColor = in.readInt();
            this.guideColor = in.readInt();
            this.initialFrameScale = in.readFloat();
            this.angle = in.readFloat();
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.isAnimationEnabled = z;
            this.animationDuration = in.readInt();
            this.exifRotation = in.readInt();
            this.sourceUri = (Uri) in.readParcelable(Uri.class.getClassLoader());
            this.saveUri = (Uri) in.readParcelable(Uri.class.getClassLoader());
            this.compressFormat = (CompressFormat) in.readSerializable();
            this.compressQuality = in.readInt();
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.isDebug = z;
            this.outputMaxWidth = in.readInt();
            this.outputMaxHeight = in.readInt();
            this.outputWidth = in.readInt();
            this.outputHeight = in.readInt();
            if (in.readInt() == 0) {
                z2 = false;
            }
            this.isHandleShadowEnabled = z2;
            this.inputImageWidth = in.readInt();
            this.inputImageHeight = in.readInt();
            this.outputImageWidth = in.readInt();
            this.outputImageHeight = in.readInt();
        }

        public void writeToParcel(Parcel out, int flag) {
            int i;
            int i2 = 1;
            super.writeToParcel(out, flag);
            out.writeParcelable(this.image, flag);
            out.writeSerializable(this.mode);
            out.writeInt(this.backgroundColor);
            out.writeInt(this.overlayColor);
            out.writeInt(this.frameColor);
            out.writeSerializable(this.guideShowMode);
            out.writeSerializable(this.handleShowMode);
            out.writeInt(this.showGuide ? 1 : 0);
            if (this.showHandle) {
                i = 1;
            } else {
                i = 0;
            }
            out.writeInt(i);
            out.writeInt(this.handleSize);
            out.writeInt(this.touchPadding);
            out.writeFloat(this.minFrameSize);
            out.writeFloat(this.customRatioX);
            out.writeFloat(this.customRatioY);
            out.writeFloat(this.frameStrokeWeight);
            out.writeFloat(this.guideStrokeWeight);
            if (this.isCropEnabled) {
                i = 1;
            } else {
                i = 0;
            }
            out.writeInt(i);
            out.writeInt(this.handleColor);
            out.writeInt(this.guideColor);
            out.writeFloat(this.initialFrameScale);
            out.writeFloat(this.angle);
            if (this.isAnimationEnabled) {
                i = 1;
            } else {
                i = 0;
            }
            out.writeInt(i);
            out.writeInt(this.animationDuration);
            out.writeInt(this.exifRotation);
            out.writeParcelable(this.sourceUri, flag);
            out.writeParcelable(this.saveUri, flag);
            out.writeSerializable(this.compressFormat);
            out.writeInt(this.compressQuality);
            if (this.isDebug) {
                i = 1;
            } else {
                i = 0;
            }
            out.writeInt(i);
            out.writeInt(this.outputMaxWidth);
            out.writeInt(this.outputMaxHeight);
            out.writeInt(this.outputWidth);
            out.writeInt(this.outputHeight);
            if (!this.isHandleShadowEnabled) {
                i2 = 0;
            }
            out.writeInt(i2);
            out.writeInt(this.inputImageWidth);
            out.writeInt(this.inputImageHeight);
            out.writeInt(this.outputImageWidth);
            out.writeInt(this.outputImageHeight);
        }
    }

    public enum ShowMode {
        SHOW_ALWAYS(1),
        SHOW_ON_TOUCH(2),
        NOT_SHOW(3);
        
        private final int ID;

        private ShowMode(int id) {
            this.ID = id;
        }

        public int getId() {
            return this.ID;
        }
    }

    private enum TouchArea {
        OUT_OF_BOUNDS,
        CENTER,
        LEFT_TOP,
        RIGHT_TOP,
        LEFT_BOTTOM,
        RIGHT_BOTTOM
    }

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mViewWidth = 0;
        this.mViewHeight = 0;
        this.mScale = 1.0f;
        this.mAngle = 0.0f;
        this.mImgWidth = 0.0f;
        this.mImgHeight = 0.0f;
        this.mIsInitialized = false;
        this.mMatrix = null;
        this.mCenter = new PointF();
        this.mIsRotating = false;
        this.mIsAnimating = false;
        this.mAnimator = null;
        this.DEFAULT_INTERPOLATOR = new DecelerateInterpolator();
        this.mInterpolator = this.DEFAULT_INTERPOLATOR;
        this.mLoadCallback = null;
        this.mCropCallback = null;
        this.mSaveCallback = null;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSourceUri = null;
        this.mSaveUri = null;
        this.mExifRotation = 0;
        this.mOutputWidth = 0;
        this.mOutputHeight = 0;
        this.mIsDebug = false;
        this.mIsCropping = false;
        this.mCompressFormat = CompressFormat.PNG;
        this.mCompressQuality = 100;
        this.mInputImageWidth = 0;
        this.mInputImageHeight = 0;
        this.mOutputImageWidth = 0;
        this.mOutputImageHeight = 0;
        this.mIsLoading = false;
        this.mTouchArea = TouchArea.OUT_OF_BOUNDS;
        this.mCropMode = CropMode.SQUARE;
        this.mGuideShowMode = ShowMode.SHOW_ALWAYS;
        this.mHandleShowMode = ShowMode.SHOW_ALWAYS;
        this.mTouchPadding = 0;
        this.mShowGuide = true;
        this.mShowHandle = true;
        this.mIsCropEnabled = true;
        this.mIsEnabled = true;
        this.mCustomRatio = new PointF(1.0f, 1.0f);
        this.mFrameStrokeWeight = 2.0f;
        this.mGuideStrokeWeight = 2.0f;
        this.mIsAnimationEnabled = true;
        this.mAnimationDurationMillis = 100;
        this.mIsHandleShadowEnabled = true;
        this.mExecutor = Executors.newSingleThreadExecutor();
        float density = getDensity();
        this.mHandleSize = (int) (14.0f * density);
        this.mMinFrameSize = 50.0f * density;
        this.mFrameStrokeWeight = density * 1.0f;
        this.mGuideStrokeWeight = density * 1.0f;
        this.mPaintFrame = new Paint();
        this.mPaintTranslucent = new Paint();
        this.mPaintBitmap = new Paint();
        this.mPaintBitmap.setFilterBitmap(true);
        this.mPaintDebug = new Paint();
        this.mPaintDebug.setAntiAlias(true);
        this.mPaintDebug.setStyle(Style.STROKE);
        this.mPaintDebug.setColor(-1);
        this.mPaintDebug.setTextSize(15.0f * density);
        this.mMatrix = new Matrix();
        this.mScale = 1.0f;
        this.mBackgroundColor = 0;
        this.mFrameColor = -1;
        this.mOverlayColor = TRANSLUCENT_BLACK;
        this.mHandleColor = -1;
        this.mGuideColor = TRANSLUCENT_WHITE;
        handleStyleable(context, attrs, defStyle, density);
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.image = getBitmap();
        ss.mode = this.mCropMode;
        ss.backgroundColor = this.mBackgroundColor;
        ss.overlayColor = this.mOverlayColor;
        ss.frameColor = this.mFrameColor;
        ss.guideShowMode = this.mGuideShowMode;
        ss.handleShowMode = this.mHandleShowMode;
        ss.showGuide = this.mShowGuide;
        ss.showHandle = this.mShowHandle;
        ss.handleSize = this.mHandleSize;
        ss.touchPadding = this.mTouchPadding;
        ss.minFrameSize = this.mMinFrameSize;
        ss.customRatioX = this.mCustomRatio.x;
        ss.customRatioY = this.mCustomRatio.y;
        ss.frameStrokeWeight = this.mFrameStrokeWeight;
        ss.guideStrokeWeight = this.mGuideStrokeWeight;
        ss.isCropEnabled = this.mIsCropEnabled;
        ss.handleColor = this.mHandleColor;
        ss.guideColor = this.mGuideColor;
        ss.initialFrameScale = this.mInitialFrameScale;
        ss.angle = this.mAngle;
        ss.isAnimationEnabled = this.mIsAnimationEnabled;
        ss.animationDuration = this.mAnimationDurationMillis;
        ss.exifRotation = this.mExifRotation;
        ss.sourceUri = this.mSourceUri;
        ss.saveUri = this.mSaveUri;
        ss.compressFormat = this.mCompressFormat;
        ss.compressQuality = this.mCompressQuality;
        ss.isDebug = this.mIsDebug;
        ss.outputMaxWidth = this.mOutputMaxWidth;
        ss.outputMaxHeight = this.mOutputMaxHeight;
        ss.outputWidth = this.mOutputWidth;
        ss.outputHeight = this.mOutputHeight;
        ss.isHandleShadowEnabled = this.mIsHandleShadowEnabled;
        ss.inputImageWidth = this.mInputImageWidth;
        ss.inputImageHeight = this.mInputImageHeight;
        ss.outputImageWidth = this.mOutputImageWidth;
        ss.outputImageHeight = this.mOutputImageHeight;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mCropMode = ss.mode;
        this.mBackgroundColor = ss.backgroundColor;
        this.mOverlayColor = ss.overlayColor;
        this.mFrameColor = ss.frameColor;
        this.mGuideShowMode = ss.guideShowMode;
        this.mHandleShowMode = ss.handleShowMode;
        this.mShowGuide = ss.showGuide;
        this.mShowHandle = ss.showHandle;
        this.mHandleSize = ss.handleSize;
        this.mTouchPadding = ss.touchPadding;
        this.mMinFrameSize = ss.minFrameSize;
        this.mCustomRatio = new PointF(ss.customRatioX, ss.customRatioY);
        this.mFrameStrokeWeight = ss.frameStrokeWeight;
        this.mGuideStrokeWeight = ss.guideStrokeWeight;
        this.mIsCropEnabled = ss.isCropEnabled;
        this.mHandleColor = ss.handleColor;
        this.mGuideColor = ss.guideColor;
        this.mInitialFrameScale = ss.initialFrameScale;
        this.mAngle = ss.angle;
        this.mIsAnimationEnabled = ss.isAnimationEnabled;
        this.mAnimationDurationMillis = ss.animationDuration;
        this.mExifRotation = ss.exifRotation;
        this.mSourceUri = ss.sourceUri;
        this.mSaveUri = ss.saveUri;
        this.mCompressFormat = ss.compressFormat;
        this.mCompressQuality = ss.compressQuality;
        this.mIsDebug = ss.isDebug;
        this.mOutputMaxWidth = ss.outputMaxWidth;
        this.mOutputMaxHeight = ss.outputMaxHeight;
        this.mOutputWidth = ss.outputWidth;
        this.mOutputHeight = ss.outputHeight;
        this.mIsHandleShadowEnabled = ss.isHandleShadowEnabled;
        this.mInputImageWidth = ss.inputImageWidth;
        this.mInputImageHeight = ss.inputImageHeight;
        this.mOutputImageWidth = ss.outputImageWidth;
        this.mOutputImageHeight = ss.outputImageHeight;
        setImageBitmap(ss.image);
        requestLayout();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);
        this.mViewWidth = (viewWidth - getPaddingLeft()) - getPaddingRight();
        this.mViewHeight = (viewHeight - getPaddingTop()) - getPaddingBottom();
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getDrawable() != null) {
            setupLayout(this.mViewWidth, this.mViewHeight);
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(this.mBackgroundColor);
        if (this.mIsInitialized) {
            setMatrix();
            Bitmap bm = getBitmap();
            if (bm != null) {
                canvas.drawBitmap(bm, this.mMatrix, this.mPaintBitmap);
                drawCropFrame(canvas);
            }
            if (this.mIsDebug) {
                drawDebugInfo(canvas);
            }
        }
    }

    protected void onDetachedFromWindow() {
        this.mExecutor.shutdown();
        super.onDetachedFromWindow();
    }

    private void handleStyleable(Context context, AttributeSet attrs, int defStyle, float mDensity) {
        int i = 0;
        TypedArray ta = context.obtainStyledAttributes(attrs, C1078R.styleable.scv_CropImageView, defStyle, 0);
        this.mCropMode = CropMode.SQUARE;
        try {
            ShowMode mode;
            Drawable drawable = ta.getDrawable(C1078R.styleable.scv_CropImageView_scv_img_src);
            if (drawable != null) {
                setImageDrawable(drawable);
            }
            for (CropMode mode2 : CropMode.values()) {
                if (ta.getInt(C1078R.styleable.scv_CropImageView_scv_crop_mode, 3) == mode2.getId()) {
                    this.mCropMode = mode2;
                    break;
                }
            }
            this.mBackgroundColor = ta.getColor(C1078R.styleable.scv_CropImageView_scv_background_color, 0);
            this.mOverlayColor = ta.getColor(C1078R.styleable.scv_CropImageView_scv_overlay_color, TRANSLUCENT_BLACK);
            this.mFrameColor = ta.getColor(C1078R.styleable.scv_CropImageView_scv_frame_color, -1);
            this.mHandleColor = ta.getColor(C1078R.styleable.scv_CropImageView_scv_handle_color, -1);
            this.mGuideColor = ta.getColor(C1078R.styleable.scv_CropImageView_scv_guide_color, TRANSLUCENT_WHITE);
            for (ShowMode mode3 : ShowMode.values()) {
                if (ta.getInt(C1078R.styleable.scv_CropImageView_scv_guide_show_mode, 1) == mode3.getId()) {
                    this.mGuideShowMode = mode3;
                    break;
                }
            }
            ShowMode[] values = ShowMode.values();
            int length = values.length;
            while (i < length) {
                mode3 = values[i];
                if (ta.getInt(C1078R.styleable.scv_CropImageView_scv_handle_show_mode, 1) == mode3.getId()) {
                    this.mHandleShowMode = mode3;
                    break;
                }
                i++;
            }
            setGuideShowMode(this.mGuideShowMode);
            setHandleShowMode(this.mHandleShowMode);
            this.mHandleSize = ta.getDimensionPixelSize(C1078R.styleable.scv_CropImageView_scv_handle_size, (int) (14.0f * mDensity));
            this.mTouchPadding = ta.getDimensionPixelSize(C1078R.styleable.scv_CropImageView_scv_touch_padding, 0);
            this.mMinFrameSize = (float) ta.getDimensionPixelSize(C1078R.styleable.scv_CropImageView_scv_min_frame_size, (int) (50.0f * mDensity));
            this.mFrameStrokeWeight = (float) ta.getDimensionPixelSize(C1078R.styleable.scv_CropImageView_scv_frame_stroke_weight, (int) (1.0f * mDensity));
            this.mGuideStrokeWeight = (float) ta.getDimensionPixelSize(C1078R.styleable.scv_CropImageView_scv_guide_stroke_weight, (int) (1.0f * mDensity));
            this.mIsCropEnabled = ta.getBoolean(C1078R.styleable.scv_CropImageView_scv_crop_enabled, true);
            this.mInitialFrameScale = constrain(ta.getFloat(C1078R.styleable.scv_CropImageView_scv_initial_frame_scale, 1.0f), 0.01f, 1.0f, 1.0f);
            this.mIsAnimationEnabled = ta.getBoolean(C1078R.styleable.scv_CropImageView_scv_animation_enabled, true);
            this.mAnimationDurationMillis = ta.getInt(C1078R.styleable.scv_CropImageView_scv_animation_duration, 100);
            this.mIsHandleShadowEnabled = ta.getBoolean(C1078R.styleable.scv_CropImageView_scv_handle_shadow_enabled, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    private void drawDebugInfo(Canvas canvas) {
        FontMetrics fontMetrics = this.mPaintDebug.getFontMetrics();
        this.mPaintDebug.measureText("W");
        int textHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
        int x = (int) (this.mImageRect.left + ((((float) this.mHandleSize) * 0.5f) * getDensity()));
        int y = (int) ((this.mImageRect.top + ((float) textHeight)) + ((((float) this.mHandleSize) * 0.5f) * getDensity()));
        StringBuilder builder = new StringBuilder();
        builder.append("LOADED FROM: ").append(this.mSourceUri != null ? "Uri" : "Bitmap");
        canvas.drawText(builder.toString(), (float) x, (float) y, this.mPaintDebug);
        builder = new StringBuilder();
        if (this.mSourceUri == null) {
            builder.append("INPUT_IMAGE_SIZE: ").append((int) this.mImgWidth).append("x").append((int) this.mImgHeight);
            y += textHeight;
            canvas.drawText(builder.toString(), (float) x, (float) y, this.mPaintDebug);
            builder = new StringBuilder();
        } else {
            y += textHeight;
            canvas.drawText("INPUT_IMAGE_SIZE: " + this.mInputImageWidth + "x" + this.mInputImageHeight, (float) x, (float) y, this.mPaintDebug);
            builder = new StringBuilder();
        }
        builder.append("LOADED_IMAGE_SIZE: ").append(getBitmap().getWidth()).append("x").append(getBitmap().getHeight());
        y += textHeight;
        canvas.drawText(builder.toString(), (float) x, (float) y, this.mPaintDebug);
        builder = new StringBuilder();
        if (this.mOutputImageWidth > 0 && this.mOutputImageHeight > 0) {
            builder.append("OUTPUT_IMAGE_SIZE: ").append(this.mOutputImageWidth).append("x").append(this.mOutputImageHeight);
            y += textHeight;
            canvas.drawText(builder.toString(), (float) x, (float) y, this.mPaintDebug);
            y += textHeight;
            canvas.drawText("EXIF ROTATION: " + this.mExifRotation, (float) x, (float) y, this.mPaintDebug);
            canvas.drawText("CURRENT_ROTATION: " + ((int) this.mAngle), (float) x, (float) (y + textHeight), this.mPaintDebug);
        }
    }

    private void drawCropFrame(Canvas canvas) {
        if (this.mIsCropEnabled && !this.mIsRotating) {
            drawOverlay(canvas);
            drawFrame(canvas);
            if (this.mShowGuide) {
                drawGuidelines(canvas);
            }
            if (this.mShowHandle) {
                drawHandles(canvas);
            }
        }
    }

    private void drawOverlay(Canvas canvas) {
        this.mPaintTranslucent.setAntiAlias(true);
        this.mPaintTranslucent.setFilterBitmap(true);
        this.mPaintTranslucent.setColor(this.mOverlayColor);
        this.mPaintTranslucent.setStyle(Style.FILL);
        Path path = new Path();
        RectF overlayRect = new RectF((float) Math.floor((double) this.mImageRect.left), (float) Math.floor((double) this.mImageRect.top), (float) Math.ceil((double) this.mImageRect.right), (float) Math.ceil((double) this.mImageRect.bottom));
        if (this.mIsAnimating || !(this.mCropMode == CropMode.CIRCLE || this.mCropMode == CropMode.CIRCLE_SQUARE)) {
            path.addRect(overlayRect, Direction.CW);
            path.addRect(this.mFrameRect, Direction.CCW);
            canvas.drawPath(path, this.mPaintTranslucent);
            return;
        }
        path.addRect(overlayRect, Direction.CW);
        PointF circleCenter = new PointF((this.mFrameRect.left + this.mFrameRect.right) / 2.0f, (this.mFrameRect.top + this.mFrameRect.bottom) / 2.0f);
        path.addCircle(circleCenter.x, circleCenter.y, (this.mFrameRect.right - this.mFrameRect.left) / 2.0f, Direction.CCW);
        canvas.drawPath(path, this.mPaintTranslucent);
    }

    private void drawFrame(Canvas canvas) {
        this.mPaintFrame.setAntiAlias(true);
        this.mPaintFrame.setFilterBitmap(true);
        this.mPaintFrame.setStyle(Style.STROKE);
        this.mPaintFrame.setColor(this.mFrameColor);
        this.mPaintFrame.setStrokeWidth(this.mFrameStrokeWeight);
        canvas.drawRect(this.mFrameRect, this.mPaintFrame);
    }

    private void drawGuidelines(Canvas canvas) {
        this.mPaintFrame.setColor(this.mGuideColor);
        this.mPaintFrame.setStrokeWidth(this.mGuideStrokeWeight);
        float h1 = this.mFrameRect.left + ((this.mFrameRect.right - this.mFrameRect.left) / 3.0f);
        float h2 = this.mFrameRect.right - ((this.mFrameRect.right - this.mFrameRect.left) / 3.0f);
        float v1 = this.mFrameRect.top + ((this.mFrameRect.bottom - this.mFrameRect.top) / 3.0f);
        float v2 = this.mFrameRect.bottom - ((this.mFrameRect.bottom - this.mFrameRect.top) / 3.0f);
        canvas.drawLine(h1, this.mFrameRect.top, h1, this.mFrameRect.bottom, this.mPaintFrame);
        canvas.drawLine(h2, this.mFrameRect.top, h2, this.mFrameRect.bottom, this.mPaintFrame);
        canvas.drawLine(this.mFrameRect.left, v1, this.mFrameRect.right, v1, this.mPaintFrame);
        canvas.drawLine(this.mFrameRect.left, v2, this.mFrameRect.right, v2, this.mPaintFrame);
    }

    private void drawHandles(Canvas canvas) {
        if (this.mIsHandleShadowEnabled) {
            drawHandleShadows(canvas);
        }
        this.mPaintFrame.setStyle(Style.FILL);
        this.mPaintFrame.setColor(this.mHandleColor);
        canvas.drawCircle(this.mFrameRect.left, this.mFrameRect.top, (float) this.mHandleSize, this.mPaintFrame);
        canvas.drawCircle(this.mFrameRect.right, this.mFrameRect.top, (float) this.mHandleSize, this.mPaintFrame);
        canvas.drawCircle(this.mFrameRect.left, this.mFrameRect.bottom, (float) this.mHandleSize, this.mPaintFrame);
        canvas.drawCircle(this.mFrameRect.right, this.mFrameRect.bottom, (float) this.mHandleSize, this.mPaintFrame);
    }

    private void drawHandleShadows(Canvas canvas) {
        this.mPaintFrame.setStyle(Style.FILL);
        this.mPaintFrame.setColor(TRANSLUCENT_BLACK);
        RectF rect = new RectF(this.mFrameRect);
        rect.offset(0.0f, 1.0f);
        canvas.drawCircle(rect.left, rect.top, (float) this.mHandleSize, this.mPaintFrame);
        canvas.drawCircle(rect.right, rect.top, (float) this.mHandleSize, this.mPaintFrame);
        canvas.drawCircle(rect.left, rect.bottom, (float) this.mHandleSize, this.mPaintFrame);
        canvas.drawCircle(rect.right, rect.bottom, (float) this.mHandleSize, this.mPaintFrame);
    }

    private void setMatrix() {
        this.mMatrix.reset();
        this.mMatrix.setTranslate(this.mCenter.x - (this.mImgWidth * 0.5f), this.mCenter.y - (this.mImgHeight * 0.5f));
        this.mMatrix.postScale(this.mScale, this.mScale, this.mCenter.x, this.mCenter.y);
        this.mMatrix.postRotate(this.mAngle, this.mCenter.x, this.mCenter.y);
    }

    private void setupLayout(int viewW, int viewH) {
        if (viewW != 0 && viewH != 0) {
            setCenter(new PointF(((float) getPaddingLeft()) + (((float) viewW) * 0.5f), ((float) getPaddingTop()) + (((float) viewH) * 0.5f)));
            setScale(calcScale(viewW, viewH, this.mAngle));
            setMatrix();
            this.mImageRect = calcImageRect(new RectF(0.0f, 0.0f, this.mImgWidth, this.mImgHeight), this.mMatrix);
            this.mFrameRect = calcFrameRect(this.mImageRect);
            this.mIsInitialized = true;
            invalidate();
        }
    }

    private float calcScale(int viewW, int viewH, float angle) {
        this.mImgWidth = (float) getDrawable().getIntrinsicWidth();
        this.mImgHeight = (float) getDrawable().getIntrinsicHeight();
        if (this.mImgWidth <= 0.0f) {
            this.mImgWidth = (float) viewW;
        }
        if (this.mImgHeight <= 0.0f) {
            this.mImgHeight = (float) viewH;
        }
        float viewRatio = ((float) viewW) / ((float) viewH);
        float imgRatio = getRotatedWidth(angle) / getRotatedHeight(angle);
        if (imgRatio >= viewRatio) {
            return ((float) viewW) / getRotatedWidth(angle);
        }
        if (imgRatio < viewRatio) {
            return ((float) viewH) / getRotatedHeight(angle);
        }
        return 1.0f;
    }

    private RectF calcImageRect(RectF rect, Matrix matrix) {
        RectF applied = new RectF();
        matrix.mapRect(applied, rect);
        return applied;
    }

    private RectF calcFrameRect(RectF imageRect) {
        float imgRatio = imageRect.width() / imageRect.height();
        float frameRatio = getRatioX(imageRect.width()) / getRatioY(imageRect.height());
        float l = imageRect.left;
        float t = imageRect.top;
        float r = imageRect.right;
        float b = imageRect.bottom;
        if (frameRatio >= imgRatio) {
            l = imageRect.left;
            r = imageRect.right;
            float hy = (imageRect.top + imageRect.bottom) * 0.5f;
            float hh = (imageRect.width() / frameRatio) * 0.5f;
            t = hy - hh;
            b = hy + hh;
        } else if (frameRatio < imgRatio) {
            t = imageRect.top;
            b = imageRect.bottom;
            float hx = (imageRect.left + imageRect.right) * 0.5f;
            float hw = (imageRect.height() * frameRatio) * 0.5f;
            l = hx - hw;
            r = hx + hw;
        }
        float w = r - l;
        float h = b - t;
        float cx = l + (w / 2.0f);
        float cy = t + (h / 2.0f);
        float sw = w * this.mInitialFrameScale;
        float sh = h * this.mInitialFrameScale;
        return new RectF(cx - (sw / 2.0f), cy - (sh / 2.0f), (sw / 2.0f) + cx, (sh / 2.0f) + cy);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mIsInitialized || !this.mIsCropEnabled || !this.mIsEnabled || this.mIsRotating || this.mIsAnimating || this.mIsLoading || this.mIsCropping) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                onDown(event);
                return true;
            case 1:
                getParent().requestDisallowInterceptTouchEvent(false);
                onUp(event);
                return true;
            case 2:
                onMove(event);
                if (this.mTouchArea != TouchArea.OUT_OF_BOUNDS) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return true;
            case 3:
                getParent().requestDisallowInterceptTouchEvent(false);
                onCancel();
                return true;
            default:
                return false;
        }
    }

    private void onDown(MotionEvent e) {
        invalidate();
        this.mLastX = e.getX();
        this.mLastY = e.getY();
        checkTouchArea(e.getX(), e.getY());
    }

    private void onMove(MotionEvent e) {
        float diffX = e.getX() - this.mLastX;
        float diffY = e.getY() - this.mLastY;
        switch (this.mTouchArea) {
            case CENTER:
                moveFrame(diffX, diffY);
                break;
            case LEFT_TOP:
                moveHandleLT(diffX, diffY);
                break;
            case RIGHT_TOP:
                moveHandleRT(diffX, diffY);
                break;
            case LEFT_BOTTOM:
                moveHandleLB(diffX, diffY);
                break;
            case RIGHT_BOTTOM:
                moveHandleRB(diffX, diffY);
                break;
        }
        invalidate();
        this.mLastX = e.getX();
        this.mLastY = e.getY();
    }

    private void onUp(MotionEvent e) {
        if (this.mGuideShowMode == ShowMode.SHOW_ON_TOUCH) {
            this.mShowGuide = false;
        }
        if (this.mHandleShowMode == ShowMode.SHOW_ON_TOUCH) {
            this.mShowHandle = false;
        }
        this.mTouchArea = TouchArea.OUT_OF_BOUNDS;
        invalidate();
    }

    private void onCancel() {
        this.mTouchArea = TouchArea.OUT_OF_BOUNDS;
        invalidate();
    }

    private void checkTouchArea(float x, float y) {
        if (isInsideCornerLeftTop(x, y)) {
            this.mTouchArea = TouchArea.LEFT_TOP;
            if (this.mHandleShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowHandle = true;
            }
            if (this.mGuideShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowGuide = true;
            }
        } else if (isInsideCornerRightTop(x, y)) {
            this.mTouchArea = TouchArea.RIGHT_TOP;
            if (this.mHandleShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowHandle = true;
            }
            if (this.mGuideShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowGuide = true;
            }
        } else if (isInsideCornerLeftBottom(x, y)) {
            this.mTouchArea = TouchArea.LEFT_BOTTOM;
            if (this.mHandleShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowHandle = true;
            }
            if (this.mGuideShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowGuide = true;
            }
        } else if (isInsideCornerRightBottom(x, y)) {
            this.mTouchArea = TouchArea.RIGHT_BOTTOM;
            if (this.mHandleShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowHandle = true;
            }
            if (this.mGuideShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowGuide = true;
            }
        } else if (isInsideFrame(x, y)) {
            if (this.mGuideShowMode == ShowMode.SHOW_ON_TOUCH) {
                this.mShowGuide = true;
            }
            this.mTouchArea = TouchArea.CENTER;
        } else {
            this.mTouchArea = TouchArea.OUT_OF_BOUNDS;
        }
    }

    private boolean isInsideFrame(float x, float y) {
        if (this.mFrameRect.left > x || this.mFrameRect.right < x || this.mFrameRect.top > y || this.mFrameRect.bottom < y) {
            return false;
        }
        this.mTouchArea = TouchArea.CENTER;
        return true;
    }

    private boolean isInsideCornerLeftTop(float x, float y) {
        float dx = x - this.mFrameRect.left;
        float dy = y - this.mFrameRect.top;
        return sq((float) (this.mHandleSize + this.mTouchPadding)) >= (dx * dx) + (dy * dy);
    }

    private boolean isInsideCornerRightTop(float x, float y) {
        float dx = x - this.mFrameRect.right;
        float dy = y - this.mFrameRect.top;
        return sq((float) (this.mHandleSize + this.mTouchPadding)) >= (dx * dx) + (dy * dy);
    }

    private boolean isInsideCornerLeftBottom(float x, float y) {
        float dx = x - this.mFrameRect.left;
        float dy = y - this.mFrameRect.bottom;
        return sq((float) (this.mHandleSize + this.mTouchPadding)) >= (dx * dx) + (dy * dy);
    }

    private boolean isInsideCornerRightBottom(float x, float y) {
        float dx = x - this.mFrameRect.right;
        float dy = y - this.mFrameRect.bottom;
        return sq((float) (this.mHandleSize + this.mTouchPadding)) >= (dx * dx) + (dy * dy);
    }

    private void moveFrame(float x, float y) {
        RectF rectF = this.mFrameRect;
        rectF.left += x;
        rectF = this.mFrameRect;
        rectF.right += x;
        rectF = this.mFrameRect;
        rectF.top += y;
        rectF = this.mFrameRect;
        rectF.bottom += y;
        checkMoveBounds();
    }

    private void moveHandleLT(float diffX, float diffY) {
        RectF rectF;
        if (this.mCropMode == CropMode.FREE) {
            rectF = this.mFrameRect;
            rectF.left += diffX;
            rectF = this.mFrameRect;
            rectF.top += diffY;
            if (isWidthTooSmall()) {
                float offsetX = this.mMinFrameSize - getFrameW();
                rectF = this.mFrameRect;
                rectF.left -= offsetX;
            }
            if (isHeightTooSmall()) {
                float offsetY = this.mMinFrameSize - getFrameH();
                rectF = this.mFrameRect;
                rectF.top -= offsetY;
            }
            checkScaleBounds();
            return;
        }
        float dx = diffX;
        float dy = (getRatioY() * diffX) / getRatioX();
        rectF = this.mFrameRect;
        rectF.left += dx;
        rectF = this.mFrameRect;
        rectF.top += dy;
        if (isWidthTooSmall()) {
            offsetX = this.mMinFrameSize - getFrameW();
            rectF = this.mFrameRect;
            rectF.left -= offsetX;
            offsetY = (getRatioY() * offsetX) / getRatioX();
            rectF = this.mFrameRect;
            rectF.top -= offsetY;
        }
        if (isHeightTooSmall()) {
            offsetY = this.mMinFrameSize - getFrameH();
            rectF = this.mFrameRect;
            rectF.top -= offsetY;
            offsetX = (getRatioX() * offsetY) / getRatioY();
            rectF = this.mFrameRect;
            rectF.left -= offsetX;
        }
        if (!isInsideHorizontal(this.mFrameRect.left)) {
            float ox = this.mImageRect.left - this.mFrameRect.left;
            rectF = this.mFrameRect;
            rectF.left += ox;
            float oy = (getRatioY() * ox) / getRatioX();
            rectF = this.mFrameRect;
            rectF.top += oy;
        }
        if (!isInsideVertical(this.mFrameRect.top)) {
            oy = this.mImageRect.top - this.mFrameRect.top;
            rectF = this.mFrameRect;
            rectF.top += oy;
            ox = (getRatioX() * oy) / getRatioY();
            rectF = this.mFrameRect;
            rectF.left += ox;
        }
    }

    private void moveHandleRT(float diffX, float diffY) {
        RectF rectF;
        if (this.mCropMode == CropMode.FREE) {
            rectF = this.mFrameRect;
            rectF.right += diffX;
            rectF = this.mFrameRect;
            rectF.top += diffY;
            if (isWidthTooSmall()) {
                float offsetX = this.mMinFrameSize - getFrameW();
                rectF = this.mFrameRect;
                rectF.right += offsetX;
            }
            if (isHeightTooSmall()) {
                float offsetY = this.mMinFrameSize - getFrameH();
                rectF = this.mFrameRect;
                rectF.top -= offsetY;
            }
            checkScaleBounds();
            return;
        }
        float dx = diffX;
        float dy = (getRatioY() * diffX) / getRatioX();
        rectF = this.mFrameRect;
        rectF.right += dx;
        rectF = this.mFrameRect;
        rectF.top -= dy;
        if (isWidthTooSmall()) {
            offsetX = this.mMinFrameSize - getFrameW();
            rectF = this.mFrameRect;
            rectF.right += offsetX;
            offsetY = (getRatioY() * offsetX) / getRatioX();
            rectF = this.mFrameRect;
            rectF.top -= offsetY;
        }
        if (isHeightTooSmall()) {
            offsetY = this.mMinFrameSize - getFrameH();
            rectF = this.mFrameRect;
            rectF.top -= offsetY;
            offsetX = (getRatioX() * offsetY) / getRatioY();
            rectF = this.mFrameRect;
            rectF.right += offsetX;
        }
        if (!isInsideHorizontal(this.mFrameRect.right)) {
            float ox = this.mFrameRect.right - this.mImageRect.right;
            rectF = this.mFrameRect;
            rectF.right -= ox;
            float oy = (getRatioY() * ox) / getRatioX();
            rectF = this.mFrameRect;
            rectF.top += oy;
        }
        if (!isInsideVertical(this.mFrameRect.top)) {
            oy = this.mImageRect.top - this.mFrameRect.top;
            rectF = this.mFrameRect;
            rectF.top += oy;
            ox = (getRatioX() * oy) / getRatioY();
            rectF = this.mFrameRect;
            rectF.right -= ox;
        }
    }

    private void moveHandleLB(float diffX, float diffY) {
        RectF rectF;
        if (this.mCropMode == CropMode.FREE) {
            rectF = this.mFrameRect;
            rectF.left += diffX;
            rectF = this.mFrameRect;
            rectF.bottom += diffY;
            if (isWidthTooSmall()) {
                float offsetX = this.mMinFrameSize - getFrameW();
                rectF = this.mFrameRect;
                rectF.left -= offsetX;
            }
            if (isHeightTooSmall()) {
                float offsetY = this.mMinFrameSize - getFrameH();
                rectF = this.mFrameRect;
                rectF.bottom += offsetY;
            }
            checkScaleBounds();
            return;
        }
        float dx = diffX;
        float dy = (getRatioY() * diffX) / getRatioX();
        rectF = this.mFrameRect;
        rectF.left += dx;
        rectF = this.mFrameRect;
        rectF.bottom -= dy;
        if (isWidthTooSmall()) {
            offsetX = this.mMinFrameSize - getFrameW();
            rectF = this.mFrameRect;
            rectF.left -= offsetX;
            offsetY = (getRatioY() * offsetX) / getRatioX();
            rectF = this.mFrameRect;
            rectF.bottom += offsetY;
        }
        if (isHeightTooSmall()) {
            offsetY = this.mMinFrameSize - getFrameH();
            rectF = this.mFrameRect;
            rectF.bottom += offsetY;
            offsetX = (getRatioX() * offsetY) / getRatioY();
            rectF = this.mFrameRect;
            rectF.left -= offsetX;
        }
        if (!isInsideHorizontal(this.mFrameRect.left)) {
            float ox = this.mImageRect.left - this.mFrameRect.left;
            rectF = this.mFrameRect;
            rectF.left += ox;
            float oy = (getRatioY() * ox) / getRatioX();
            rectF = this.mFrameRect;
            rectF.bottom -= oy;
        }
        if (!isInsideVertical(this.mFrameRect.bottom)) {
            oy = this.mFrameRect.bottom - this.mImageRect.bottom;
            rectF = this.mFrameRect;
            rectF.bottom -= oy;
            ox = (getRatioX() * oy) / getRatioY();
            rectF = this.mFrameRect;
            rectF.left += ox;
        }
    }

    private void moveHandleRB(float diffX, float diffY) {
        RectF rectF;
        if (this.mCropMode == CropMode.FREE) {
            rectF = this.mFrameRect;
            rectF.right += diffX;
            rectF = this.mFrameRect;
            rectF.bottom += diffY;
            if (isWidthTooSmall()) {
                float offsetX = this.mMinFrameSize - getFrameW();
                rectF = this.mFrameRect;
                rectF.right += offsetX;
            }
            if (isHeightTooSmall()) {
                float offsetY = this.mMinFrameSize - getFrameH();
                rectF = this.mFrameRect;
                rectF.bottom += offsetY;
            }
            checkScaleBounds();
            return;
        }
        float dx = diffX;
        float dy = (getRatioY() * diffX) / getRatioX();
        rectF = this.mFrameRect;
        rectF.right += dx;
        rectF = this.mFrameRect;
        rectF.bottom += dy;
        if (isWidthTooSmall()) {
            offsetX = this.mMinFrameSize - getFrameW();
            rectF = this.mFrameRect;
            rectF.right += offsetX;
            offsetY = (getRatioY() * offsetX) / getRatioX();
            rectF = this.mFrameRect;
            rectF.bottom += offsetY;
        }
        if (isHeightTooSmall()) {
            offsetY = this.mMinFrameSize - getFrameH();
            rectF = this.mFrameRect;
            rectF.bottom += offsetY;
            offsetX = (getRatioX() * offsetY) / getRatioY();
            rectF = this.mFrameRect;
            rectF.right += offsetX;
        }
        if (!isInsideHorizontal(this.mFrameRect.right)) {
            float ox = this.mFrameRect.right - this.mImageRect.right;
            rectF = this.mFrameRect;
            rectF.right -= ox;
            float oy = (getRatioY() * ox) / getRatioX();
            rectF = this.mFrameRect;
            rectF.bottom -= oy;
        }
        if (!isInsideVertical(this.mFrameRect.bottom)) {
            oy = this.mFrameRect.bottom - this.mImageRect.bottom;
            rectF = this.mFrameRect;
            rectF.bottom -= oy;
            ox = (getRatioX() * oy) / getRatioY();
            rectF = this.mFrameRect;
            rectF.right -= ox;
        }
    }

    private void checkScaleBounds() {
        float lDiff = this.mFrameRect.left - this.mImageRect.left;
        float rDiff = this.mFrameRect.right - this.mImageRect.right;
        float tDiff = this.mFrameRect.top - this.mImageRect.top;
        float bDiff = this.mFrameRect.bottom - this.mImageRect.bottom;
        if (lDiff < 0.0f) {
            RectF rectF = this.mFrameRect;
            rectF.left -= lDiff;
        }
        if (rDiff > 0.0f) {
            rectF = this.mFrameRect;
            rectF.right -= rDiff;
        }
        if (tDiff < 0.0f) {
            rectF = this.mFrameRect;
            rectF.top -= tDiff;
        }
        if (bDiff > 0.0f) {
            rectF = this.mFrameRect;
            rectF.bottom -= bDiff;
        }
    }

    private void checkMoveBounds() {
        float diff = this.mFrameRect.left - this.mImageRect.left;
        if (diff < 0.0f) {
            RectF rectF = this.mFrameRect;
            rectF.left -= diff;
            rectF = this.mFrameRect;
            rectF.right -= diff;
        }
        diff = this.mFrameRect.right - this.mImageRect.right;
        if (diff > 0.0f) {
            rectF = this.mFrameRect;
            rectF.left -= diff;
            rectF = this.mFrameRect;
            rectF.right -= diff;
        }
        diff = this.mFrameRect.top - this.mImageRect.top;
        if (diff < 0.0f) {
            rectF = this.mFrameRect;
            rectF.top -= diff;
            rectF = this.mFrameRect;
            rectF.bottom -= diff;
        }
        diff = this.mFrameRect.bottom - this.mImageRect.bottom;
        if (diff > 0.0f) {
            rectF = this.mFrameRect;
            rectF.top -= diff;
            rectF = this.mFrameRect;
            rectF.bottom -= diff;
        }
    }

    private boolean isInsideHorizontal(float x) {
        return this.mImageRect.left <= x && this.mImageRect.right >= x;
    }

    private boolean isInsideVertical(float y) {
        return this.mImageRect.top <= y && this.mImageRect.bottom >= y;
    }

    private boolean isWidthTooSmall() {
        return getFrameW() < this.mMinFrameSize;
    }

    private boolean isHeightTooSmall() {
        return getFrameH() < this.mMinFrameSize;
    }

    private void recalculateFrameRect(int durationMillis) {
        if (this.mImageRect != null) {
            if (this.mIsAnimating) {
                getAnimator().cancelAnimation();
            }
            final RectF currentRect = new RectF(this.mFrameRect);
            final RectF newRect = calcFrameRect(this.mImageRect);
            final float diffL = newRect.left - currentRect.left;
            final float diffT = newRect.top - currentRect.top;
            final float diffR = newRect.right - currentRect.right;
            final float diffB = newRect.bottom - currentRect.bottom;
            if (this.mIsAnimationEnabled) {
                SimpleValueAnimator animator = getAnimator();
                animator.addAnimatorListener(new SimpleValueAnimatorListener() {
                    public void onAnimationStarted() {
                        CropImageView.this.mIsAnimating = true;
                    }

                    public void onAnimationUpdated(float scale) {
                        CropImageView.this.mFrameRect = new RectF(currentRect.left + (diffL * scale), currentRect.top + (diffT * scale), currentRect.right + (diffR * scale), currentRect.bottom + (diffB * scale));
                        CropImageView.this.invalidate();
                    }

                    public void onAnimationFinished() {
                        CropImageView.this.mFrameRect = newRect;
                        CropImageView.this.invalidate();
                        CropImageView.this.mIsAnimating = false;
                    }
                });
                animator.startAnimation((long) durationMillis);
                return;
            }
            this.mFrameRect = calcFrameRect(this.mImageRect);
            invalidate();
        }
    }

    private float getRatioX(float w) {
        switch (this.mCropMode) {
            case FIT_IMAGE:
                return this.mImageRect.width();
            case RATIO_4_3:
                return 4.0f;
            case RATIO_3_4:
                return 3.0f;
            case RATIO_16_9:
                return 16.0f;
            case RATIO_9_16:
                return 9.0f;
            case SQUARE:
            case CIRCLE:
            case CIRCLE_SQUARE:
                return 1.0f;
            case CUSTOM:
                return this.mCustomRatio.x;
            default:
                return w;
        }
    }

    private float getRatioY(float h) {
        switch (this.mCropMode) {
            case FIT_IMAGE:
                return this.mImageRect.height();
            case RATIO_4_3:
                return 3.0f;
            case RATIO_3_4:
                return 4.0f;
            case RATIO_16_9:
                return 9.0f;
            case RATIO_9_16:
                return 16.0f;
            case SQUARE:
            case CIRCLE:
            case CIRCLE_SQUARE:
                return 1.0f;
            case CUSTOM:
                return this.mCustomRatio.y;
            default:
                return h;
        }
    }

    private float getRatioX() {
        switch (this.mCropMode) {
            case FIT_IMAGE:
                return this.mImageRect.width();
            case RATIO_4_3:
                return 4.0f;
            case RATIO_3_4:
                return 3.0f;
            case RATIO_16_9:
                return 16.0f;
            case RATIO_9_16:
                return 9.0f;
            case CUSTOM:
                return this.mCustomRatio.x;
            default:
                return 1.0f;
        }
    }

    private float getRatioY() {
        switch (this.mCropMode) {
            case FIT_IMAGE:
                return this.mImageRect.height();
            case RATIO_4_3:
                return 3.0f;
            case RATIO_3_4:
                return 4.0f;
            case RATIO_16_9:
                return 9.0f;
            case RATIO_9_16:
                return 16.0f;
            case CUSTOM:
                return this.mCustomRatio.y;
            default:
                return 1.0f;
        }
    }

    private float getDensity() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    private float sq(float value) {
        return value * value;
    }

    private float constrain(float val, float min, float max, float defaultVal) {
        if (val < min || val > max) {
            return defaultVal;
        }
        return val;
    }

    private void postErrorOnMainThread(final Callback callback) {
        if (callback != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                callback.onError();
            } else {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        callback.onError();
                    }
                });
            }
        }
    }

    private Bitmap getBitmap() {
        Drawable d = getDrawable();
        if (d == null || !(d instanceof BitmapDrawable)) {
            return null;
        }
        return ((BitmapDrawable) d).getBitmap();
    }

    private float getRotatedWidth(float angle) {
        return getRotatedWidth(angle, this.mImgWidth, this.mImgHeight);
    }

    private float getRotatedWidth(float angle, float width, float height) {
        return angle % 180.0f == 0.0f ? width : height;
    }

    private float getRotatedHeight(float angle) {
        return getRotatedHeight(angle, this.mImgWidth, this.mImgHeight);
    }

    private float getRotatedHeight(float angle, float width, float height) {
        return angle % 180.0f == 0.0f ? height : width;
    }

    private Bitmap getRotatedBitmap(Bitmap bitmap) {
        Matrix rotateMatrix = new Matrix();
        rotateMatrix.setRotate(this.mAngle, (float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, true);
    }

    private SimpleValueAnimator getAnimator() {
        setupAnimatorIfNeeded();
        return this.mAnimator;
    }

    private void setupAnimatorIfNeeded() {
        if (this.mAnimator != null) {
            return;
        }
        if (VERSION.SDK_INT < 14) {
            this.mAnimator = new ValueAnimatorV8(this.mInterpolator);
        } else {
            this.mAnimator = new ValueAnimatorV14(this.mInterpolator);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Bitmap decodeRegion() {
        /*
        r14 = this;
        r10 = 0;
        r1 = 0;
        r4 = 0;
        r9 = r14.getContext();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = r9.getContentResolver();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r11 = r14.mSourceUri;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r4 = r9.openInputStream(r11);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = 0;
        r2 = android.graphics.BitmapRegionDecoder.newInstance(r4, r9);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r7 = r2.getWidth();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r6 = r2.getHeight();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r0 = r14.calcCropRect(r7, r6);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = r14.mAngle;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1));
        if (r9 == 0) goto L_0x0063;
    L_0x0028:
        r5 = new android.graphics.Matrix;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r5.<init>();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = r14.mAngle;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = -r9;
        r5.setRotate(r9);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r8 = new android.graphics.RectF;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r8.<init>();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = new android.graphics.RectF;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9.<init>(r0);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r5.mapRect(r8, r9);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = r8.left;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1));
        if (r9 >= 0) goto L_0x0086;
    L_0x0046:
        r9 = (float) r7;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r11 = r9;
    L_0x0048:
        r9 = r8.top;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1));
        if (r9 >= 0) goto L_0x0088;
    L_0x004e:
        r9 = (float) r6;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
    L_0x004f:
        r8.offset(r11, r9);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r0 = new android.graphics.Rect;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = r8.left;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = (int) r9;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r11 = r8.top;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r11 = (int) r11;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r12 = r8.right;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r12 = (int) r12;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r13 = r8.bottom;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r13 = (int) r13;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r0.<init>(r9, r11, r12, r13);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
    L_0x0063:
        r9 = new android.graphics.BitmapFactory$Options;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9.<init>();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r1 = r2.decodeRegion(r0, r9);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = r14.mAngle;	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1));
        if (r9 == 0) goto L_0x0082;
    L_0x0072:
        r8 = r14.getRotatedBitmap(r1);	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        r9 = r14.getBitmap();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
        if (r1 == r9) goto L_0x0081;
    L_0x007c:
        if (r1 == r8) goto L_0x0081;
    L_0x007e:
        r1.recycle();	 Catch:{ IOException -> 0x008a, OutOfMemoryError -> 0x00a9, Exception -> 0x00c8 }
    L_0x0081:
        r1 = r8;
    L_0x0082:
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r4);
    L_0x0085:
        return r1;
    L_0x0086:
        r11 = r10;
        goto L_0x0048;
    L_0x0088:
        r9 = r10;
        goto L_0x004f;
    L_0x008a:
        r3 = move-exception;
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e7 }
        r9.<init>();	 Catch:{ all -> 0x00e7 }
        r10 = "An error occurred while cropping the image: ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x00e7 }
        r10 = r3.getMessage();	 Catch:{ all -> 0x00e7 }
        r9 = r9.append(r10);	 Catch:{ all -> 0x00e7 }
        r9 = r9.toString();	 Catch:{ all -> 0x00e7 }
        com.isseiaoki.simplecropview.util.Logger.m3300e(r9, r3);	 Catch:{ all -> 0x00e7 }
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r4);
        goto L_0x0085;
    L_0x00a9:
        r3 = move-exception;
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e7 }
        r9.<init>();	 Catch:{ all -> 0x00e7 }
        r10 = "OOM Error: ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x00e7 }
        r10 = r3.getMessage();	 Catch:{ all -> 0x00e7 }
        r9 = r9.append(r10);	 Catch:{ all -> 0x00e7 }
        r9 = r9.toString();	 Catch:{ all -> 0x00e7 }
        com.isseiaoki.simplecropview.util.Logger.m3300e(r9, r3);	 Catch:{ all -> 0x00e7 }
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r4);
        goto L_0x0085;
    L_0x00c8:
        r3 = move-exception;
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e7 }
        r9.<init>();	 Catch:{ all -> 0x00e7 }
        r10 = "An unexpected error has occurred: ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x00e7 }
        r10 = r3.getMessage();	 Catch:{ all -> 0x00e7 }
        r9 = r9.append(r10);	 Catch:{ all -> 0x00e7 }
        r9 = r9.toString();	 Catch:{ all -> 0x00e7 }
        com.isseiaoki.simplecropview.util.Logger.m3300e(r9, r3);	 Catch:{ all -> 0x00e7 }
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r4);
        goto L_0x0085;
    L_0x00e7:
        r9 = move-exception;
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r4);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.isseiaoki.simplecropview.CropImageView.decodeRegion():android.graphics.Bitmap");
    }

    private Rect calcCropRect(int originalImageWidth, int originalImageHeight) {
        float scaleToOriginal = getRotatedWidth(this.mAngle, (float) originalImageWidth, (float) originalImageHeight) / this.mImageRect.width();
        float offsetX = this.mImageRect.left * scaleToOriginal;
        float offsetY = this.mImageRect.top * scaleToOriginal;
        int left = Math.round((this.mFrameRect.left * scaleToOriginal) - offsetX);
        int top = Math.round((this.mFrameRect.top * scaleToOriginal) - offsetY);
        int right = Math.round((this.mFrameRect.right * scaleToOriginal) - offsetX);
        int bottom = Math.round((this.mFrameRect.bottom * scaleToOriginal) - offsetY);
        return new Rect(Math.max(left, 0), Math.max(top, 0), Math.min(right, Math.round(getRotatedWidth(this.mAngle, (float) originalImageWidth, (float) originalImageHeight))), Math.min(bottom, Math.round(getRotatedHeight(this.mAngle, (float) originalImageWidth, (float) originalImageHeight))));
    }

    private Bitmap scaleBitmapIfNeeded(Bitmap cropped) {
        int width = cropped.getWidth();
        int height = cropped.getHeight();
        int outWidth = 0;
        int outHeight = 0;
        float imageRatio = getRatioX(this.mFrameRect.width()) / getRatioY(this.mFrameRect.height());
        if (this.mOutputWidth > 0) {
            outWidth = this.mOutputWidth;
            outHeight = Math.round(((float) this.mOutputWidth) / imageRatio);
        } else if (this.mOutputHeight > 0) {
            outHeight = this.mOutputHeight;
            outWidth = Math.round(((float) this.mOutputHeight) * imageRatio);
        } else if (this.mOutputMaxWidth > 0 && this.mOutputMaxHeight > 0 && (width > this.mOutputMaxWidth || height > this.mOutputMaxHeight)) {
            if (((float) this.mOutputMaxWidth) / ((float) this.mOutputMaxHeight) >= imageRatio) {
                outHeight = this.mOutputMaxHeight;
                outWidth = Math.round(((float) this.mOutputMaxHeight) * imageRatio);
            } else {
                outWidth = this.mOutputMaxWidth;
                outHeight = Math.round(((float) this.mOutputMaxWidth) / imageRatio);
            }
        }
        if (outWidth <= 0 || outHeight <= 0) {
            return cropped;
        }
        Bitmap scaled = Utils.getScaledBitmap(cropped, outWidth, outHeight);
        if (!(cropped == getBitmap() || cropped == scaled)) {
            cropped.recycle();
        }
        return scaled;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveToFile(android.graphics.Bitmap r5, final android.net.Uri r6) {
        /*
        r4 = this;
        r1 = 0;
        r2 = r4.getContext();	 Catch:{ IOException -> 0x0024 }
        r2 = r2.getContentResolver();	 Catch:{ IOException -> 0x0024 }
        r1 = r2.openOutputStream(r6);	 Catch:{ IOException -> 0x0024 }
        if (r1 == 0) goto L_0x0016;
    L_0x000f:
        r2 = r4.mCompressFormat;	 Catch:{ IOException -> 0x0024 }
        r3 = r4.mCompressQuality;	 Catch:{ IOException -> 0x0024 }
        r5.compress(r2, r3, r1);	 Catch:{ IOException -> 0x0024 }
    L_0x0016:
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r1);
    L_0x0019:
        r2 = r4.mHandler;
        r3 = new com.isseiaoki.simplecropview.CropImageView$3;
        r3.<init>(r6);
        r2.post(r3);
        return;
    L_0x0024:
        r0 = move-exception;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0044 }
        r2.<init>();	 Catch:{ all -> 0x0044 }
        r3 = "An error occurred while saving the image: ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0044 }
        r2 = r2.append(r6);	 Catch:{ all -> 0x0044 }
        r2 = r2.toString();	 Catch:{ all -> 0x0044 }
        com.isseiaoki.simplecropview.util.Logger.m3300e(r2, r0);	 Catch:{ all -> 0x0044 }
        r2 = r4.mSaveCallback;	 Catch:{ all -> 0x0044 }
        r4.postErrorOnMainThread(r2);	 Catch:{ all -> 0x0044 }
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r1);
        goto L_0x0019;
    L_0x0044:
        r2 = move-exception;
        com.isseiaoki.simplecropview.util.Utils.closeQuietly(r1);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.isseiaoki.simplecropview.CropImageView.saveToFile(android.graphics.Bitmap, android.net.Uri):void");
    }

    public Bitmap getImageBitmap() {
        return getBitmap();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
    }

    public void setImageResource(int resId) {
        this.mIsInitialized = false;
        super.setImageResource(resId);
        updateLayout();
    }

    public void setImageDrawable(Drawable drawable) {
        this.mIsInitialized = false;
        super.setImageDrawable(drawable);
        updateLayout();
    }

    public void setImageURI(Uri uri) {
        this.mIsInitialized = false;
        super.setImageURI(uri);
        updateLayout();
    }

    private void updateLayout() {
        resetImageInfo();
        if (getDrawable() != null) {
            setupLayout(this.mViewWidth, this.mViewHeight);
        }
    }

    private void resetImageInfo() {
        if (!this.mIsLoading) {
            this.mSourceUri = null;
            this.mSaveUri = null;
            this.mInputImageWidth = 0;
            this.mInputImageHeight = 0;
            this.mOutputImageWidth = 0;
            this.mOutputImageHeight = 0;
            this.mAngle = (float) this.mExifRotation;
        }
    }

    public void startLoad(Uri sourceUri, LoadCallback callback) {
        this.mLoadCallback = callback;
        this.mSourceUri = sourceUri;
        if (sourceUri == null) {
            postErrorOnMainThread(this.mLoadCallback);
            throw new IllegalStateException("Source Uri must not be null.");
        } else {
            this.mExecutor.submit(new C10724());
        }
    }

    public void rotateImage(RotateDegrees degrees, int durationMillis) {
        if (this.mIsRotating) {
            getAnimator().cancelAnimation();
        }
        final float currentAngle = this.mAngle;
        final float newAngle = this.mAngle + ((float) degrees.getValue());
        final float angleDiff = newAngle - currentAngle;
        final float currentScale = this.mScale;
        final float newScale = calcScale(this.mViewWidth, this.mViewHeight, newAngle);
        if (this.mIsAnimationEnabled) {
            final float scaleDiff = newScale - currentScale;
            SimpleValueAnimator animator = getAnimator();
            animator.addAnimatorListener(new SimpleValueAnimatorListener() {
                public void onAnimationStarted() {
                    CropImageView.this.mIsRotating = true;
                }

                public void onAnimationUpdated(float scale) {
                    CropImageView.this.mAngle = currentAngle + (angleDiff * scale);
                    CropImageView.this.mScale = currentScale + (scaleDiff * scale);
                    CropImageView.this.setMatrix();
                    CropImageView.this.invalidate();
                }

                public void onAnimationFinished() {
                    CropImageView.this.mAngle = newAngle % 360.0f;
                    CropImageView.this.mScale = newScale;
                    CropImageView.this.setupLayout(CropImageView.this.mViewWidth, CropImageView.this.mViewHeight);
                    CropImageView.this.mIsRotating = false;
                }
            });
            animator.startAnimation((long) durationMillis);
            return;
        }
        this.mAngle = newAngle % 360.0f;
        this.mScale = newScale;
        setupLayout(this.mViewWidth, this.mViewHeight);
    }

    public void rotateImage(RotateDegrees degrees) {
        rotateImage(degrees, this.mAnimationDurationMillis);
    }

    public Bitmap getCroppedBitmap() {
        Bitmap source = getBitmap();
        if (source == null) {
            return null;
        }
        Bitmap rotated = getRotatedBitmap(source);
        Rect cropRect = calcCropRect(source.getWidth(), source.getHeight());
        Bitmap cropped = Bitmap.createBitmap(rotated, cropRect.left, cropRect.top, cropRect.width(), cropRect.height(), null, false);
        if (!(rotated == cropped || rotated == source)) {
            rotated.recycle();
        }
        if (this.mCropMode == CropMode.CIRCLE) {
            Bitmap circle = getCircularBitmap(cropped);
            if (cropped != getBitmap()) {
                cropped.recycle();
            }
            cropped = circle;
        }
        return cropped;
    }

    public Bitmap getCircularBitmap(Bitmap square) {
        if (square == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(square.getWidth(), square.getHeight(), Config.ARGB_8888);
        Rect rect = new Rect(0, 0, square.getWidth(), square.getHeight());
        Canvas canvas = new Canvas(output);
        int halfWidth = square.getWidth() / 2;
        int halfHeight = square.getHeight() / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawCircle((float) halfWidth, (float) halfHeight, (float) Math.min(halfWidth, halfHeight), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(square, rect, rect, paint);
        return output;
    }

    public void startCrop(Uri saveUri, CropCallback cropCallback, SaveCallback saveCallback) {
        this.mSaveUri = saveUri;
        this.mCropCallback = cropCallback;
        this.mSaveCallback = saveCallback;
        if (this.mIsCropping) {
            postErrorOnMainThread(this.mCropCallback);
            postErrorOnMainThread(this.mSaveCallback);
            return;
        }
        this.mIsCropping = true;
        this.mExecutor.submit(new C10756());
    }

    public RectF getActualCropRect() {
        float offsetX = this.mImageRect.left / this.mScale;
        float offsetY = this.mImageRect.top / this.mScale;
        return new RectF((this.mFrameRect.left / this.mScale) - offsetX, (this.mFrameRect.top / this.mScale) - offsetY, (this.mFrameRect.right / this.mScale) - offsetX, (this.mFrameRect.bottom / this.mScale) - offsetY);
    }

    public void setCropMode(CropMode mode, int durationMillis) {
        if (mode == CropMode.CUSTOM) {
            setCustomRatio(1, 1);
            return;
        }
        this.mCropMode = mode;
        recalculateFrameRect(durationMillis);
    }

    public void setCropMode(CropMode mode) {
        setCropMode(mode, this.mAnimationDurationMillis);
    }

    public void setCustomRatio(int ratioX, int ratioY, int durationMillis) {
        if (ratioX != 0 && ratioY != 0) {
            this.mCropMode = CropMode.CUSTOM;
            this.mCustomRatio = new PointF((float) ratioX, (float) ratioY);
            recalculateFrameRect(durationMillis);
        }
    }

    public void setCustomRatio(int ratioX, int ratioY) {
        setCustomRatio(ratioX, ratioY, this.mAnimationDurationMillis);
    }

    public void setOverlayColor(int overlayColor) {
        this.mOverlayColor = overlayColor;
        invalidate();
    }

    public void setFrameColor(int frameColor) {
        this.mFrameColor = frameColor;
        invalidate();
    }

    public void setHandleColor(int handleColor) {
        this.mHandleColor = handleColor;
        invalidate();
    }

    public void setGuideColor(int guideColor) {
        this.mGuideColor = guideColor;
        invalidate();
    }

    public void setBackgroundColor(int bgColor) {
        this.mBackgroundColor = bgColor;
        invalidate();
    }

    public void setMinFrameSizeInDp(int minDp) {
        this.mMinFrameSize = ((float) minDp) * getDensity();
    }

    public void setMinFrameSizeInPx(int minPx) {
        this.mMinFrameSize = (float) minPx;
    }

    public void setHandleSizeInDp(int handleDp) {
        this.mHandleSize = (int) (((float) handleDp) * getDensity());
    }

    public void setTouchPaddingInDp(int paddingDp) {
        this.mTouchPadding = (int) (((float) paddingDp) * getDensity());
    }

    public void setGuideShowMode(ShowMode mode) {
        this.mGuideShowMode = mode;
        switch (mode) {
            case SHOW_ALWAYS:
                this.mShowGuide = true;
                break;
            case NOT_SHOW:
            case SHOW_ON_TOUCH:
                this.mShowGuide = false;
                break;
        }
        invalidate();
    }

    public void setHandleShowMode(ShowMode mode) {
        this.mHandleShowMode = mode;
        switch (mode) {
            case SHOW_ALWAYS:
                this.mShowHandle = true;
                break;
            case NOT_SHOW:
            case SHOW_ON_TOUCH:
                this.mShowHandle = false;
                break;
        }
        invalidate();
    }

    public void setFrameStrokeWeightInDp(int weightDp) {
        this.mFrameStrokeWeight = ((float) weightDp) * getDensity();
        invalidate();
    }

    public void setGuideStrokeWeightInDp(int weightDp) {
        this.mGuideStrokeWeight = ((float) weightDp) * getDensity();
        invalidate();
    }

    public void setCropEnabled(boolean enabled) {
        this.mIsCropEnabled = enabled;
        invalidate();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mIsEnabled = enabled;
    }

    public void setInitialFrameScale(float initialScale) {
        this.mInitialFrameScale = constrain(initialScale, 0.01f, 1.0f, 1.0f);
    }

    public void setAnimationEnabled(boolean enabled) {
        this.mIsAnimationEnabled = enabled;
    }

    public void setAnimationDuration(int durationMillis) {
        this.mAnimationDurationMillis = durationMillis;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
        this.mAnimator = null;
        setupAnimatorIfNeeded();
    }

    public void setDebug(boolean debug) {
        this.mIsDebug = debug;
        invalidate();
    }

    public void setLoggingEnabled(boolean enabled) {
        Logger.enabled = enabled;
    }

    public void setLoadCallback(LoadCallback callback) {
        this.mLoadCallback = callback;
    }

    public void setCropCallback(CropCallback callback) {
        this.mCropCallback = callback;
    }

    public void setSaveCallback(SaveCallback callback) {
        this.mSaveCallback = callback;
    }

    public void setOutputWidth(int outputWidth) {
        this.mOutputWidth = outputWidth;
        this.mOutputHeight = 0;
    }

    public void setOutputHeight(int outputHeight) {
        this.mOutputHeight = outputHeight;
        this.mOutputWidth = 0;
    }

    public void setOutputMaxSize(int maxWidth, int maxHeight) {
        this.mOutputMaxWidth = maxWidth;
        this.mOutputMaxHeight = maxHeight;
    }

    public void setCompressFormat(CompressFormat format) {
        this.mCompressFormat = format;
    }

    public void setCompressQuality(int quality) {
        this.mCompressQuality = quality;
    }

    public void setHandleShadowEnabled(boolean handleShadowEnabled) {
        this.mIsHandleShadowEnabled = handleShadowEnabled;
    }

    private void setScale(float mScale) {
        this.mScale = mScale;
    }

    private void setCenter(PointF mCenter) {
        this.mCenter = mCenter;
    }

    private float getFrameW() {
        return this.mFrameRect.right - this.mFrameRect.left;
    }

    private float getFrameH() {
        return this.mFrameRect.bottom - this.mFrameRect.top;
    }
}
