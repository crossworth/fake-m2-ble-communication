package com.droi.sdk.utility;

import android.graphics.Bitmap;
import com.droi.sdk.DroiError;

public interface BitmapBackgroundCallback {
    void result(boolean z, Bitmap bitmap, DroiError droiError);
}
