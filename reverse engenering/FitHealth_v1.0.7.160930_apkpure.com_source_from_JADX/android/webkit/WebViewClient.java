package android.webkit;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;

public class WebViewClient {
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }

    public void onPageFinished(WebView view, String url) {
    }

    public void onLoadResource(WebView view, String url) {
    }

    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        cancelMsg.sendToTarget();
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
    }

    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        dontResend.sendToTarget();
    }

    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.cancel();
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        handler.cancel();
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return false;
    }

    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
    }
}
