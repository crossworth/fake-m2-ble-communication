package com.isseiaoki.simplecropview.callback;

import android.graphics.Bitmap;

public interface CropCallback extends Callback {
    void onError();

    void onSuccess(Bitmap bitmap);
}
