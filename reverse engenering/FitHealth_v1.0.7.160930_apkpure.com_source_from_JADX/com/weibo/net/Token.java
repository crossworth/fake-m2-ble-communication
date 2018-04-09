package com.weibo.net;

import javax.crypto.spec.SecretKeySpec;
import oauth.signpost.OAuth;

public class Token {
    private long mExpiresIn = 0;
    protected String mOauth_Token_Secret = "";
    private String mOauth_verifier = "";
    private String mRefreshToken = "";
    protected SecretKeySpec mSecretKeySpec;
    private String mToken = "";
    protected String[] responseStr = null;

    public String getToken() {
        return this.mToken;
    }

    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    public void setRefreshToken(String mRefreshToken) {
        this.mRefreshToken = mRefreshToken;
    }

    public long getExpiresIn() {
        return this.mExpiresIn;
    }

    public void setExpiresIn(long mExpiresIn) {
        this.mExpiresIn = mExpiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        if (expiresIn != null && !expiresIn.equals("0")) {
            setExpiresIn(System.currentTimeMillis() + (Long.parseLong(expiresIn) * 1000));
        }
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public void setVerifier(String verifier) {
        this.mOauth_verifier = verifier;
    }

    public String getVerifier() {
        return this.mOauth_verifier;
    }

    public String getSecret() {
        return this.mOauth_Token_Secret;
    }

    public Token(String rltString) {
        this.responseStr = rltString.split("&");
        this.mOauth_Token_Secret = getParameter(OAuth.OAUTH_TOKEN_SECRET);
        this.mToken = getParameter(OAuth.OAUTH_TOKEN);
    }

    public Token(String token, String secret) {
        this.mToken = token;
        this.mOauth_Token_Secret = secret;
    }

    public String getParameter(String parameter) {
        for (String str : this.responseStr) {
            if (str.startsWith(new StringBuilder(String.valueOf(parameter)).append('=').toString())) {
                return str.split("=")[1].trim();
            }
        }
        return null;
    }

    protected void setSecretKeySpec(SecretKeySpec secretKeySpec) {
        this.mSecretKeySpec = secretKeySpec;
    }

    protected SecretKeySpec getSecretKeySpec() {
        return this.mSecretKeySpec;
    }
}
