package com.weibo.net;

import android.text.TextUtils;
import com.tencent.open.SocialConstants;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import oauth.signpost.OAuth;

public class XAuthHeader extends HttpHeaderFactory {
    public WeiboParameters generateSignatureList(WeiboParameters bundle) {
        if (bundle == null || bundle.size() == 0) {
            return null;
        }
        WeiboParameters pp = new WeiboParameters();
        String key = "oauth_consumer_key";
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_NONCE;
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_SIGNATURE_METHOD;
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_TIMESTAMP;
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_VERSION;
        pp.add(key, bundle.getValue(key));
        pp.add(SocialConstants.PARAM_SOURCE, Weibo.getAppKey());
        pp.add("x_auth_mode", "client_auth");
        key = "x_auth_password";
        pp.add(key, bundle.getValue(key));
        key = "x_auth_username";
        pp.add(key, bundle.getValue(key));
        return pp;
    }

    public String generateSignature(String data, Token token) throws WeiboException {
        try {
            Mac mac = Mac.getInstance(HttpHeaderFactory.CONST_HMAC_SHA1);
            mac.init(new SecretKeySpec(new StringBuilder(String.valueOf(HttpHeaderFactory.encode(Weibo.getAppSecret()))).append("&").toString().getBytes(), HttpHeaderFactory.CONST_HMAC_SHA1));
            return String.valueOf(Utility.base64Encode(mac.doFinal(data.getBytes())));
        } catch (Exception e) {
            throw new WeiboException(e);
        } catch (Exception e2) {
            throw new WeiboException(e2);
        }
    }

    public void addAdditionalParams(WeiboParameters des, WeiboParameters src) {
        if (!TextUtils.isEmpty(src.getValue("x_auth_password")) && !TextUtils.isEmpty(src.getValue("x_auth_username"))) {
            des.add("x_auth_password", src.getValue("x_auth_password"));
            des.add("x_auth_username", src.getValue("x_auth_username"));
            des.add("x_auth_mode", "client_auth");
        }
    }
}
