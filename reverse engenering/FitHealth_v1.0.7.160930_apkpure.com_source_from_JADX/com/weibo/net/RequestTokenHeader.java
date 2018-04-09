package com.weibo.net;

import com.tencent.open.SocialConstants;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import oauth.signpost.OAuth;

public class RequestTokenHeader extends HttpHeaderFactory {
    public WeiboParameters generateSignatureList(WeiboParameters bundle) {
        if (bundle == null || bundle.size() == 0) {
            return null;
        }
        WeiboParameters pp = new WeiboParameters();
        String key = OAuth.OAUTH_CALLBACK;
        pp.add(key, bundle.getValue(key));
        key = "oauth_consumer_key";
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_NONCE;
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_SIGNATURE_METHOD;
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_TIMESTAMP;
        pp.add(key, bundle.getValue(key));
        key = OAuth.OAUTH_VERSION;
        pp.add(key, bundle.getValue(key));
        key = SocialConstants.PARAM_SOURCE;
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
    }
}
