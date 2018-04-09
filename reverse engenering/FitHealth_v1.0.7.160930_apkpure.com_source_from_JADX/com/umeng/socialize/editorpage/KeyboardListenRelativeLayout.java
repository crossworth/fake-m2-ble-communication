package com.umeng.socialize.editorpage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class KeyboardListenRelativeLayout extends RelativeLayout {
    public static final byte f3258a = (byte) -3;
    public static final byte f3259b = (byte) -2;
    public static final byte f3260c = (byte) -1;
    private boolean f3261d = false;
    private boolean f3262e = false;
    private int f3263f;
    private IOnKeyboardStateChangedListener f3264g;

    public interface IOnKeyboardStateChangedListener {
        void mo2173a(int i);
    }

    public KeyboardListenRelativeLayout(Context context) {
        super(context);
    }

    public KeyboardListenRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public KeyboardListenRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void m3197a(IOnKeyboardStateChangedListener iOnKeyboardStateChangedListener) {
        this.f3264g = iOnKeyboardStateChangedListener;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.f3261d) {
            this.f3263f = this.f3263f < i4 ? i4 : this.f3263f;
        } else {
            this.f3261d = true;
            this.f3263f = i4;
            if (this.f3264g != null) {
                this.f3264g.mo2173a(-1);
            }
        }
        if (this.f3261d && this.f3263f > i4) {
            this.f3262e = true;
            if (this.f3264g != null) {
                this.f3264g.mo2173a(-3);
            }
        }
        if (this.f3261d && this.f3262e && this.f3263f == i4) {
            this.f3262e = false;
            if (this.f3264g != null) {
                this.f3264g.mo2173a(-2);
            }
        }
    }
}
