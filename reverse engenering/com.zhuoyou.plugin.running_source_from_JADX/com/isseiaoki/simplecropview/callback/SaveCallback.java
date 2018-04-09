package com.isseiaoki.simplecropview.callback;

import android.net.Uri;

public interface SaveCallback extends Callback {
    void onError();

    void onSuccess(Uri uri);
}
