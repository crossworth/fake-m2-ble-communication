package com.weibo.net;

import android.os.Bundle;
import com.tencent.open.SocialConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import oauth.signpost.OAuth;

public abstract class HttpHeaderFactory {
    public static final String CONST_HMAC_SHA1 = "HmacSHA1";
    public static final String CONST_OAUTH_VERSION = "1.0";
    public static final String CONST_SIGNATURE_METHOD = "HMAC-SHA1";

    public abstract void addAdditionalParams(WeiboParameters weiboParameters, WeiboParameters weiboParameters2);

    public abstract String generateSignature(String str, Token token) throws WeiboException;

    public abstract WeiboParameters generateSignatureList(WeiboParameters weiboParameters);

    public String getWeiboAuthHeader(String method, String url, WeiboParameters params, String app_key, String app_secret, Token token) throws WeiboException {
        long timestamp = System.currentTimeMillis() / 1000;
        WeiboParameters authParams = generateAuthParameters(timestamp + ((long) new Random().nextInt()), timestamp, token);
        authParams.add(OAuth.OAUTH_SIGNATURE, generateSignature(generateAuthSignature(method, generateSignatureParameters(authParams, params, url), url, token), token));
        addAdditionalParams(authParams, params);
        return "OAuth " + encodeParameters(authParams, SeparatorConstants.SEPARATOR_ADS_ID, true);
    }

    private String generateAuthSignature(String method, WeiboParameters signatureParams, String url, Token token) {
        StringBuffer base = new StringBuffer(method).append("&").append(encode(constructRequestURL(url))).append("&");
        base.append(encode(encodeParameters(signatureParams, "&", false)));
        return base.toString();
    }

    private WeiboParameters generateSignatureParameters(WeiboParameters authParams, WeiboParameters params, String url) throws WeiboException {
        WeiboParameters signatureParams = new WeiboParameters();
        signatureParams.addAll(authParams);
        signatureParams.add(SocialConstants.PARAM_SOURCE, Weibo.getAppKey());
        signatureParams.addAll(params);
        parseUrlParameters(url, signatureParams);
        return generateSignatureList(signatureParams);
    }

    private WeiboParameters generateAuthParameters(long nonce, long timestamp, Token token) {
        WeiboParameters authParams = new WeiboParameters();
        authParams.add("oauth_consumer_key", Weibo.getAppKey());
        authParams.add(OAuth.OAUTH_NONCE, String.valueOf(nonce));
        authParams.add(OAuth.OAUTH_SIGNATURE_METHOD, CONST_SIGNATURE_METHOD);
        authParams.add(OAuth.OAUTH_TIMESTAMP, String.valueOf(timestamp));
        authParams.add(OAuth.OAUTH_VERSION, "1.0");
        if (token != null) {
            authParams.add(OAuth.OAUTH_TOKEN, token.getToken());
        } else {
            authParams.add(SocialConstants.PARAM_SOURCE, Weibo.getAppKey());
        }
        return authParams;
    }

    public void parseUrlParameters(String url, WeiboParameters signatureBaseParams) throws WeiboException {
        int queryStart = url.indexOf("?");
        if (-1 != queryStart) {
            try {
                for (String query : url.substring(queryStart + 1).split("&")) {
                    String[] split = query.split("=");
                    if (split.length == 2) {
                        signatureBaseParams.add(URLDecoder.decode(split[0], "UTF-8"), URLDecoder.decode(split[1], "UTF-8"));
                    } else {
                        signatureBaseParams.add(URLDecoder.decode(split[0], "UTF-8"), "");
                    }
                }
            } catch (Exception e) {
                throw new WeiboException(e);
            }
        }
    }

    public static String encodeParameters(WeiboParameters postParams, String splitter, boolean quot) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < postParams.size(); i++) {
            if (buf.length() != 0) {
                if (quot) {
                    buf.append("\"");
                }
                buf.append(splitter);
            }
            buf.append(encode(postParams.getKey(i))).append("=");
            if (quot) {
                buf.append("\"");
            }
            buf.append(encode(postParams.getValue(i)));
        }
        if (buf.length() != 0 && quot) {
            buf.append("\"");
        }
        return buf.toString();
    }

    public static String encodeParameters(Bundle postParams, String split, boolean quot) {
        String splitter = split;
        StringBuffer buf = new StringBuffer();
        for (String key : postParams.keySet()) {
            if (buf.length() != 0) {
                if (quot) {
                    buf.append("\"");
                }
                buf.append(splitter);
            }
            buf.append(encode(key)).append("=");
            if (quot) {
                buf.append("\"");
            }
            buf.append(encode(postParams.getString(key)));
        }
        if (buf.length() != 0 && quot) {
            buf.append("\"");
        }
        return buf.toString();
    }

    public static String constructRequestURL(String url) {
        int index = url.indexOf("?");
        if (-1 != index) {
            url = url.substring(0, index);
        }
        int slashIndex = url.indexOf("/", 8);
        String baseURL = url.substring(0, slashIndex).toLowerCase();
        int colonIndex = baseURL.indexOf(":", 8);
        if (-1 != colonIndex) {
            if (baseURL.startsWith("http://") && baseURL.endsWith(":80")) {
                baseURL = baseURL.substring(0, colonIndex);
            } else if (baseURL.startsWith("https://") && baseURL.endsWith(":443")) {
                baseURL = baseURL.substring(0, colonIndex);
            }
        }
        return new StringBuilder(String.valueOf(baseURL)).append(url.substring(slashIndex)).toString();
    }

    public static String encode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        StringBuffer buf = new StringBuffer(encoded.length());
        int i = 0;
        while (i < encoded.length()) {
            char focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && i + 1 < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
            i++;
        }
        return buf.toString();
    }
}
