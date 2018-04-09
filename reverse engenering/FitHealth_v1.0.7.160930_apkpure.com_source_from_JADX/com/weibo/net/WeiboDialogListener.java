package com.weibo.net;

import android.os.Bundle;

public interface WeiboDialogListener {
    void onCancel();

    void onComplete(Bundle bundle);

    void onError(DialogError dialogError);

    void onWeiboException(WeiboException weiboException);
}
