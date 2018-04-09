package org.andengine.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import org.andengine.util.ActivityUtils;
import org.andengine.util.call.AsyncCallable;
import org.andengine.util.call.Callable;
import org.andengine.util.call.Callback;
import org.andengine.util.progress.ProgressCallable;

public abstract class BaseActivity extends Activity {
    public void toastOnUIThread(CharSequence pText) {
        toastOnUIThread(pText, 1);
    }

    public void toastOnUIThread(final CharSequence pText, final int pDuration) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(this, pText, pDuration).show();
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(BaseActivity.this, pText, pDuration).show();
                }
            });
        }
    }

    protected <T> void doAsync(int pTitleResourceID, int pMessageResourceID, Callable<T> pCallable, Callback<T> pCallback) {
        doAsync(pTitleResourceID, pMessageResourceID, (Callable) pCallable, (Callback) pCallback, null);
    }

    protected <T> void doAsync(int pTitleResourceID, int pMessageResourceID, Callable<T> pCallable, Callback<T> pCallback, Callback<Exception> pExceptionCallback) {
        ActivityUtils.doAsync((Context) this, pTitleResourceID, pMessageResourceID, (Callable) pCallable, (Callback) pCallback, (Callback) pExceptionCallback);
    }

    protected <T> void doProgressAsync(int pTitleResourceID, int pIconResourceID, ProgressCallable<T> pCallable, Callback<T> pCallback) {
        doProgressAsync(pTitleResourceID, pIconResourceID, pCallable, pCallback, null);
    }

    protected <T> void doProgressAsync(int pTitleResourceID, int pIconResourceID, ProgressCallable<T> pCallable, Callback<T> pCallback, Callback<Exception> pExceptionCallback) {
        ActivityUtils.doProgressAsync((Context) this, pTitleResourceID, pIconResourceID, (ProgressCallable) pCallable, (Callback) pCallback, (Callback) pExceptionCallback);
    }

    protected <T> void doAsync(int pTitleResourceID, int pMessageResourceID, AsyncCallable<T> pAsyncCallable, Callback<T> pCallback, Callback<Exception> pExceptionCallback) {
        ActivityUtils.doAsync((Context) this, pTitleResourceID, pMessageResourceID, (AsyncCallable) pAsyncCallable, (Callback) pCallback, (Callback) pExceptionCallback);
    }
}
