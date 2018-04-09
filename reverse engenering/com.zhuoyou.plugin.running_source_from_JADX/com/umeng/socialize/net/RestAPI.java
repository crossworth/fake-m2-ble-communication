package com.umeng.socialize.net;

import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.base.SocializeReseponse;

public class RestAPI {
    private static SocializeClient f4965a = new SocializeClient();

    public static ActionBarResponse queryShareId(ActionBarRequest actionBarRequest) {
        return (ActionBarResponse) f4965a.execute(actionBarRequest);
    }

    public static PlatformTokenUploadResponse uploadPlatformToken(PlatformTokenUploadReq platformTokenUploadReq) {
        return (PlatformTokenUploadResponse) f4965a.execute(platformTokenUploadReq);
    }

    public static UserInfoResponse getUserInfo(UserInfoRequest userInfoRequest) {
        return (UserInfoResponse) f4965a.execute(userInfoRequest);
    }

    public static SocializeReseponse doShare(SharePostRequest sharePostRequest) {
        return f4965a.execute(sharePostRequest);
    }

    public static UrlResponse uploadUrl(UrlRequest urlRequest) {
        return (UrlResponse) f4965a.execute(urlRequest);
    }
}
