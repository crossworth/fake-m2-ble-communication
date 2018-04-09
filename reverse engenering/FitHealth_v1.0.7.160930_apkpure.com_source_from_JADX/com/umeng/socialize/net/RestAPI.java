package com.umeng.socialize.net;

import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.base.SocializeReseponse;

public class RestAPI {
    private static SocializeClient f3327a = new SocializeClient();

    public static ActionBarResponse queryShareId(ActionBarRequest actionBarRequest) {
        return (ActionBarResponse) f3327a.execute(actionBarRequest);
    }

    public static ExpiresInResponse queryExpire(ExpiresInRequest expiresInRequest) {
        return (ExpiresInResponse) f3327a.execute(expiresInRequest);
    }

    public static GetPlatformKeyResponse queryPlatformKey(GetPlatformKeyRequest getPlatformKeyRequest) {
        return (GetPlatformKeyResponse) f3327a.execute(getPlatformKeyRequest);
    }

    public static PlatformTokenUploadResponse uploadPlatformToken(PlatformTokenUploadReq platformTokenUploadReq) {
        return (PlatformTokenUploadResponse) f3327a.execute(platformTokenUploadReq);
    }

    public static UpdatePlatformKeyResponse updatePlatformKey(UpdatePlatformKeyRequest updatePlatformKeyRequest) {
        return (UpdatePlatformKeyResponse) f3327a.execute(updatePlatformKeyRequest);
    }

    public static UploadImageResponse uploadImage(UploadImageRequest uploadImageRequest) {
        return (UploadImageResponse) f3327a.execute(uploadImageRequest);
    }

    public static UserInfoResponse getUserInfo(UserInfoRequest userInfoRequest) {
        return (UserInfoResponse) f3327a.execute(userInfoRequest);
    }

    public static SocializeReseponse deleteOAuth(ShareDeleteOauthRequest shareDeleteOauthRequest) {
        return f3327a.execute(shareDeleteOauthRequest);
    }

    public static ShareFriendsResponse queryFriendsList(ShareFriendsRequest shareFriendsRequest) {
        return (ShareFriendsResponse) f3327a.execute(shareFriendsRequest);
    }

    public static ShareMultiResponse doShare(ShareMultiReqeust shareMultiReqeust) {
        return (ShareMultiResponse) f3327a.execute(shareMultiReqeust);
    }

    public static SocializeReseponse doShare(SharePostRequest sharePostRequest) {
        return f3327a.execute(sharePostRequest);
    }

    public static ShareMultiFollowResponse doFollow(ShareMultiFollowRequest shareMultiFollowRequest) {
        return (ShareMultiFollowResponse) f3327a.execute(shareMultiFollowRequest);
    }

    public static UrlResponse uploadUrl(UrlRequest urlRequest) {
        return (UrlResponse) f3327a.execute(urlRequest);
    }
}
