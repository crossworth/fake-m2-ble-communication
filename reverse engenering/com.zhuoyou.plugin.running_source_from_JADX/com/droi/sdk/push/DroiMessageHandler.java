package com.droi.sdk.push;

import android.content.Context;

public abstract class DroiMessageHandler {
    public abstract void onHandleCustomMessage(Context context, String str);
}
