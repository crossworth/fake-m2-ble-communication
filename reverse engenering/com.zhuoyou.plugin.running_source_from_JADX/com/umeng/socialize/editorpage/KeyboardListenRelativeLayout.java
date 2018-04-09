package com.umeng.socialize.editorpage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class KeyboardListenRelativeLayout extends RelativeLayout {
    public static final byte KEYBOARD_STATE_HIDE = (byte) -2;
    public static final byte KEYBOARD_STATE_INIT = (byte) -1;
    public static final byte KEYBOARD_STATE_SHOW = (byte) -3;
    private boolean mHasInit = false;
    private boolean mHasKeyboard = false;
    private int mHeight;
    private IOnKeyboardStateChangedListener mOnKeyboardStateChangedListener;

    public interface IOnKeyboardStateChangedListener {
        void onKeyboardStateChanged(int i);
    }

    public KeyboardListenRelativeLayout(Context context) {
        super(context);
    }

    public KeyboardListenRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardListenRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnKeyboardStateChangedListener(IOnKeyboardStateChangedListener onKeyboardStateChangedListener) {
        this.mOnKeyboardStateChangedListener = onKeyboardStateChangedListener;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mHasInit) {
            this.mHeight = this.mHeight < b ? b : this.mHeight;
        } else {
            this.mHasInit = true;
            this.mHeight = b;
            if (this.mOnKeyboardStateChangedListener != null) {
                this.mOnKeyboardStateChangedListener.onKeyboardStateChanged(-1);
            }
        }
        if (this.mHasInit && this.mHeight > b) {
            this.mHasKeyboard = true;
            if (this.mOnKeyboardStateChangedListener != null) {
                this.mOnKeyboardStateChangedListener.onKeyboardStateChanged(-3);
            }
        }
        if (this.mHasInit && this.mHasKeyboard && this.mHeight == b) {
            this.mHasKeyboard = false;
            if (this.mOnKeyboardStateChangedListener != null) {
                this.mOnKeyboardStateChangedListener.onKeyboardStateChanged(-2);
            }
        }
    }
}
