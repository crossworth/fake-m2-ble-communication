package com.sina.weibo.sdk.api.share;

import android.content.Context;
import android.os.Bundle;
import com.sina.weibo.sdk.api.WeiboMultiMessage;

public class ProvideMultiMessageForWeiboResponse extends BaseResponse {
    public WeiboMultiMessage multiMessage;

    public ProvideMultiMessageForWeiboResponse(Bundle bundle) {
        fromBundle(bundle);
    }

    public int getType() {
        return 2;
    }

    public void fromBundle(Bundle bundle) {
        super.fromBundle(bundle);
        this.multiMessage = new WeiboMultiMessage(bundle);
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putAll(this.multiMessage.toBundle(bundle));
    }

    final boolean check(Context context, VersionCheckHandler handler) {
        if (this.multiMessage == null) {
            return false;
        }
        if (handler != null) {
            handler.setPackageName(this.reqPackageName);
            if (!handler.check(context, this.multiMessage)) {
                return false;
            }
        }
        return this.multiMessage.checkArgs();
    }
}
