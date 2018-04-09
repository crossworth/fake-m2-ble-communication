package org.andengine.input.touch.detector;

import android.view.MotionEvent;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.math.MathUtils;

public class PinchZoomDetector extends BaseDetector {
    private static final float TRIGGER_PINCHZOOM_MINIMUM_DISTANCE_DEFAULT = 10.0f;
    private float mCurrentDistance;
    private float mInitialDistance;
    private final IPinchZoomDetectorListener mPinchZoomDetectorListener;
    private boolean mPinchZooming;

    public interface IPinchZoomDetectorListener {
        void onPinchZoom(PinchZoomDetector pinchZoomDetector, TouchEvent touchEvent, float f);

        void onPinchZoomFinished(PinchZoomDetector pinchZoomDetector, TouchEvent touchEvent, float f);

        void onPinchZoomStarted(PinchZoomDetector pinchZoomDetector, TouchEvent touchEvent);
    }

    public PinchZoomDetector(IPinchZoomDetectorListener pPinchZoomDetectorListener) {
        this.mPinchZoomDetectorListener = pPinchZoomDetectorListener;
    }

    public boolean isZooming() {
        return this.mPinchZooming;
    }

    public void reset() {
        if (this.mPinchZooming) {
            this.mPinchZoomDetectorListener.onPinchZoomFinished(this, null, getZoomFactor());
        }
        this.mInitialDistance = 0.0f;
        this.mCurrentDistance = 0.0f;
        this.mPinchZooming = false;
    }

    public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {
        MotionEvent motionEvent = pSceneTouchEvent.getMotionEvent();
        switch (motionEvent.getAction() & 255) {
            case 1:
            case 3:
            case 6:
                if (this.mPinchZooming) {
                    this.mPinchZooming = false;
                    this.mPinchZoomDetectorListener.onPinchZoomFinished(this, pSceneTouchEvent, getZoomFactor());
                    break;
                }
                break;
            case 2:
                if (this.mPinchZooming) {
                    if (!hasTwoOrMorePointers(motionEvent)) {
                        this.mPinchZooming = false;
                        this.mPinchZoomDetectorListener.onPinchZoomFinished(this, pSceneTouchEvent, getZoomFactor());
                        break;
                    }
                    this.mCurrentDistance = calculatePointerDistance(motionEvent);
                    if (this.mCurrentDistance > 10.0f) {
                        this.mPinchZoomDetectorListener.onPinchZoom(this, pSceneTouchEvent, getZoomFactor());
                        break;
                    }
                }
                break;
            case 5:
                if (!this.mPinchZooming && hasTwoOrMorePointers(motionEvent)) {
                    this.mInitialDistance = calculatePointerDistance(motionEvent);
                    this.mCurrentDistance = this.mInitialDistance;
                    if (this.mInitialDistance > 10.0f) {
                        this.mPinchZooming = true;
                        this.mPinchZoomDetectorListener.onPinchZoomStarted(this, pSceneTouchEvent);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    private float getZoomFactor() {
        return this.mCurrentDistance / this.mInitialDistance;
    }

    private static float calculatePointerDistance(MotionEvent pMotionEvent) {
        return MathUtils.distance(pMotionEvent.getX(0), pMotionEvent.getY(0), pMotionEvent.getX(1), pMotionEvent.getY(1));
    }

    private static boolean hasTwoOrMorePointers(MotionEvent pMotionEvent) {
        return pMotionEvent.getPointerCount() >= 2;
    }
}
