package com.umeng.facebook.share.internal;

import android.os.Bundle;
import com.umeng.facebook.FacebookCallback;
import com.umeng.facebook.FacebookException;
import com.umeng.facebook.internal.AppCall;

public abstract class ResultProcessor {
    private FacebookCallback appCallback;

    public abstract void onSuccess(AppCall appCall, Bundle bundle);

    public ResultProcessor(FacebookCallback callback) {
        this.appCallback = callback;
    }

    public void onCancel(AppCall appCall) {
        if (this.appCallback != null) {
            this.appCallback.onCancel();
        }
    }

    public void onError(AppCall appCall, FacebookException error) {
        if (this.appCallback != null) {
            this.appCallback.onError(error);
        }
    }
}
