package com.tencent.p032t;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.HttpUtils;
import com.umeng.socialize.editorpage.ShareActivity;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ProGuard */
public class Weibo extends BaseApi {
    public Weibo(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
    }

    public Weibo(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public void getWeiboInfo(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, this.mContext, "user/get_info", composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }

    public void sendText(String str, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str2 = "content";
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str2, str);
        HttpUtils.requestAsync(this.mToken, this.mContext, "t/add_t", composeCGIParams, "POST", new TempRequestListener(iUiListener));
    }

    public void sendPicText(String str, String str2, IUiListener iUiListener) {
        Object tempRequestListener = new TempRequestListener(iUiListener);
        try {
            InputStream fileInputStream = new FileInputStream(str2);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.close();
            fileInputStream.close();
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            Bundle composeCGIParams = composeCGIParams();
            String str3 = "content";
            if (str == null) {
                str = "";
            }
            composeCGIParams.putString(str3, str);
            composeCGIParams.putByteArray(ShareActivity.KEY_PIC, toByteArray);
            HttpUtils.requestAsync(this.mToken, this.mContext, "t/add_pic_t", composeCGIParams, "POST", tempRequestListener);
        } catch (IOException e) {
            tempRequestListener.onIOException(e);
        }
    }

    public void nickTips(String str, int i, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str2 = "match";
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str2, str);
        composeCGIParams.putString("reqnum", i + "");
        HttpUtils.requestAsync(this.mToken, this.mContext, Constants.GRAPH_NICK_TIPS, composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void atFriends(int i, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("reqnum", i + "");
        HttpUtils.requestAsync(this.mToken, this.mContext, Constants.GRAPH_INTIMATE_FRIENDS, composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void deleteText(String str, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("id", str);
        HttpUtils.requestAsync(this.mToken, this.mContext, "t/del_t", composeCGIParams, "POST", new TempRequestListener(iUiListener));
    }
}
