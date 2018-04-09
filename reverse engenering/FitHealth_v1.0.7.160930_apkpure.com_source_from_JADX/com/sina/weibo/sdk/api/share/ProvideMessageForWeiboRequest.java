package com.sina.weibo.sdk.api.share;

import android.content.Context;
import android.os.Bundle;

public class ProvideMessageForWeiboRequest extends BaseRequest {
    public ProvideMessageForWeiboRequest(Bundle bundle) {
        fromBundle(bundle);
    }

    public int getType() {
        return 2;
    }

    final boolean check(Context context, VersionCheckHandler handler) {
        return true;
    }
}
