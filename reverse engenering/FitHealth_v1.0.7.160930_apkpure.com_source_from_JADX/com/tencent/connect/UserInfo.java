package com.tencent.connect;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.stat.DeviceInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.HttpUtils;

/* compiled from: ProGuard */
public class UserInfo extends BaseApi {
    public static final String GRAPH_OPEN_ID = "oauth2.0/m_me";

    public UserInfo(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public UserInfo(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
    }

    public void getUserInfo(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, this.mContext, "user/get_simple_userinfo", composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }

    public void getVipUserInfo(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, this.mContext, "user/get_vip_info", composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }

    public void getVipUserRichInfo(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, this.mContext, "user/get_vip_rich_info", composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }

    public void getTenPayAddr(IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString(DeviceInfo.TAG_VERSION, "1");
        HttpUtils.requestAsync(this.mToken, this.mContext, "cft_info/get_tenpay_addr", composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void getOpenId(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, this.mContext, GRAPH_OPEN_ID, composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }
}
