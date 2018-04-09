package com.zhuoyou.plugin.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;
import com.amap.api.maps.model.WeightedLatLng;
import com.zhuoyou.plugin.view.NewScrollView.ScrollListener;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NewTextView extends TextView implements ScrollListener {
    private static final int REFRESH = 1;
    private static final int SCROLL = 2;
    DecimalFormat floatNum = new DecimalFormat("0.0");
    DecimalFormat intNum = new DecimalFormat("0");
    private int locHeight;
    private double mCurValue;
    private int mFormat = 0;
    private double mGalValue;
    private WRHandler mHandler = new WRHandler(this);
    private Operator mOperator;
    private double mRate;
    private int mState = 0;
    private double mValue;
    private int rate = 1;
    public boolean refreshDisabled = false;
    private boolean refreshing;

    public interface Operator {
        int getWindowHeight();
    }

    private static class WRHandler extends Handler {
        WeakReference<NewTextView> mNewTextView;

        public WRHandler(NewTextView NTV) {
            this.mNewTextView = new WeakReference(NTV);
        }

        public void handleMessage(Message msg) {
            if (this.mNewTextView != null) {
                NewTextView newTextView = (NewTextView) this.mNewTextView.get();
                if (newTextView != null) {
                    switch (msg.what) {
                        case 1:
                            if (((double) newTextView.rate) * newTextView.mCurValue >= newTextView.mGalValue) {
                                newTextView.refreshing = false;
                                if (newTextView.mFormat == 0) {
                                    newTextView.setText(newTextView.intNum.format(newTextView.mGalValue));
                                } else if (newTextView.mFormat == 1) {
                                    newTextView.setText(newTextView.floatNum.format(newTextView.mGalValue));
                                }
                                newTextView.refreshDisabled = true;
                                return;
                            } else if (!newTextView.refreshDisabled) {
                                newTextView.refreshing = true;
                                if (newTextView.mFormat == 0) {
                                    newTextView.setText(newTextView.intNum.format(newTextView.mCurValue));
                                } else if (newTextView.mFormat == 1) {
                                    newTextView.setText(newTextView.floatNum.format(newTextView.mCurValue));
                                }
                                newTextView.mCurValue = newTextView.mCurValue + (newTextView.mRate * ((double) newTextView.rate));
                                newTextView.mHandler.sendEmptyMessageDelayed(1, 10);
                                return;
                            } else {
                                return;
                            }
                        case 2:
                            newTextView.doScroll(msg.arg1, msg.arg2);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    public NewTextView(Context context) {
        super(context);
    }

    public NewTextView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
    }

    public NewTextView(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
    }

    private boolean doPlus(int i) {
        if (isShown() && this.mOperator.getWindowHeight() + i > this.locHeight + 100 && this.mState == 1) {
            return true;
        }
        if (isShown() && i < this.locHeight && this.mState == 2) {
            return true;
        }
        return false;
    }

    private void doScroll(int i, int j) {
        if (this.mState != i || !this.refreshing || this.refreshDisabled) {
            this.mState = i;
            if (doPlus(j)) {
                this.rate = 1;
                this.mGalValue = this.mValue;
                this.mHandler.sendEmptyMessage(1);
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void onScrollChanged(int i, int j) {
        Message message = this.mHandler.obtainMessage();
        message.what = 2;
        message.arg1 = i;
        message.arg2 = j;
        this.mHandler.sendMessage(message);
    }

    public void setLocHeight(int i) {
        this.locHeight = i;
    }

    public void setValue(double value) {
        double temp = 0.0d;
        this.mFormat = 1;
        this.mCurValue = 0.0d;
        if (isShown()) {
            temp = value;
        }
        this.mGalValue = temp;
        this.mValue = value;
        if (value < 40.0d) {
            this.mRate = WeightedLatLng.DEFAULT_INTENSITY;
            return;
        }
        this.mRate = this.mValue / 40.0d;
        this.mRate = new BigDecimal(this.mRate).setScale(2, 4).doubleValue();
    }

    public void setValue(int value) {
        int temp = 0;
        this.mFormat = 0;
        this.mCurValue = 0.0d;
        if (isShown()) {
            temp = value;
        }
        this.mGalValue = (double) temp;
        this.mValue = (double) value;
        if (value < 40) {
            this.mRate = WeightedLatLng.DEFAULT_INTENSITY;
            return;
        }
        this.mRate = (double) ((int) (this.mValue / 40.0d));
        this.mRate = (double) new BigDecimal(this.mRate).setScale(2, 4).intValue();
    }

    public void setOperator(Operator operatorP) {
        this.mOperator = operatorP;
    }
}
