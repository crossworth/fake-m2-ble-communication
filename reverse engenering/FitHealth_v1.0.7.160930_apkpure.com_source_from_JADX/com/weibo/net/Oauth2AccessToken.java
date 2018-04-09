package com.weibo.net;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class Oauth2AccessToken extends Token {
    public Oauth2AccessToken(String rltString) {
        if (rltString != null && rltString.indexOf("{") >= 0) {
            try {
                JSONObject json = new JSONObject(rltString);
                setToken(json.optString("access_token"));
                setExpiresIn((long) json.optInt("expires_in"));
                setRefreshToken(json.optString(SocializeProtocolConstants.PROTOCOL_KEY_REFRESH_TOKEN));
            } catch (JSONException e) {
            }
        }
    }

    public Oauth2AccessToken(String token, String secret) {
        super(token, secret);
    }
}
