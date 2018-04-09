package com.sina.weibo.sdk.demo;

import com.sina.weibo.sdk.exception.WeiboException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface RequestListener {
    void onComplete(String str);

    void onComplete4binary(ByteArrayOutputStream byteArrayOutputStream);

    void onError(WeiboException weiboException);

    void onIOException(IOException iOException);
}
