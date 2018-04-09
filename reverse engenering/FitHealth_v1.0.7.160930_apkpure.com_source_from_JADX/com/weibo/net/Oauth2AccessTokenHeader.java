package com.weibo.net;

public class Oauth2AccessTokenHeader extends HttpHeaderFactory {
    public String getWeiboAuthHeader(String method, String url, WeiboParameters params, String app_key, String app_secret, Token token) throws WeiboException {
        if (token == null) {
            return null;
        }
        return "OAuth2 " + token.getToken();
    }

    public WeiboParameters generateSignatureList(WeiboParameters bundle) {
        return null;
    }

    public String generateSignature(String data, Token token) throws WeiboException {
        return "";
    }

    public void addAdditionalParams(WeiboParameters des, WeiboParameters src) {
    }
}
