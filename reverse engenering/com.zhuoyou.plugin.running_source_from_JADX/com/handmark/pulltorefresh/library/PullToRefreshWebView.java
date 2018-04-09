package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;

public class PullToRefreshWebView extends PullToRefreshBase<WebView> {
    private static final OnRefreshListener<WebView> defaultOnRefreshListener = new C10621();
    private final WebChromeClient defaultWebChromeClient = new C10632();

    static class C10621 implements OnRefreshListener<WebView> {
        C10621() {
        }

        public void onRefresh(PullToRefreshBase<WebView> refreshView) {
            ((WebView) refreshView.getRefreshableView()).reload();
        }
    }

    class C10632 extends WebChromeClient {
        C10632() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                PullToRefreshWebView.this.onRefreshComplete();
            }
        }
    }

    @TargetApi(9)
    final class InternalWebViewSDK9 extends WebView {
        static final int OVERSCROLL_FUZZY_THRESHOLD = 2;
        static final float OVERSCROLL_SCALE_FACTOR = 1.5f;

        public InternalWebViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshWebView.this, deltaX, scrollX, deltaY, scrollY, getScrollRange(), 2, OVERSCROLL_SCALE_FACTOR, isTouchEvent);
            return returnValue;
        }

        private int getScrollRange() {
            return (int) Math.max(0.0f, ((float) Math.floor((double) (((WebView) PullToRefreshWebView.this.mRefreshableView).getScale() * ((float) ((WebView) PullToRefreshWebView.this.mRefreshableView).getContentHeight())))) - ((float) ((getHeight() - getPaddingBottom()) - getPaddingTop())));
        }
    }

    public PullToRefreshWebView(Context context) {
        super(context);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, Mode mode) {
        super(context, mode);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected WebView createRefreshableView(Context context, AttributeSet attrs) {
        WebView webView;
        if (VERSION.SDK_INT >= 9) {
            webView = new InternalWebViewSDK9(context, attrs);
        } else {
            webView = new WebView(context, attrs);
        }
        webView.setId(C1064R.id.webview);
        return webView;
    }

    protected boolean isReadyForPullStart() {
        return ((WebView) this.mRefreshableView).getScrollY() == 0;
    }

    protected boolean isReadyForPullEnd() {
        return ((float) ((WebView) this.mRefreshableView).getScrollY()) >= ((float) Math.floor((double) (((WebView) this.mRefreshableView).getScale() * ((float) ((WebView) this.mRefreshableView).getContentHeight())))) - ((float) ((WebView) this.mRefreshableView).getHeight());
    }

    protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
        super.onPtrRestoreInstanceState(savedInstanceState);
        ((WebView) this.mRefreshableView).restoreState(savedInstanceState);
    }

    protected void onPtrSaveInstanceState(Bundle saveState) {
        super.onPtrSaveInstanceState(saveState);
        ((WebView) this.mRefreshableView).saveState(saveState);
    }
}
