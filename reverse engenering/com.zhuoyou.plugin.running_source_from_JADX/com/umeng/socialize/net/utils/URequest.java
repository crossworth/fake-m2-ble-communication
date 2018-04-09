package com.umeng.socialize.net.utils;

import com.tencent.connect.common.Constants;
import java.util.Map;
import org.json.JSONObject;

public abstract class URequest {
    protected static String GET = Constants.HTTP_GET;
    protected static String POST = Constants.HTTP_POST;
    protected String mBaseUrl;
    protected MIME mMimeType;

    public static class FilePair {
        byte[] mBinaryData;
        String mFileName;

        public FilePair(String str, byte[] bArr) {
            this.mFileName = str;
            this.mBinaryData = bArr;
        }
    }

    protected enum MIME {
        DEFAULT("application/x-www-form-urlencoded;charset=utf-8"),
        JSON("application/json;charset=utf-8");
        
        private String mimeType;

        private MIME(String str) {
            this.mimeType = str;
        }

        public String toString() {
            return this.mimeType;
        }
    }

    public abstract String toGetUrl();

    public abstract JSONObject toJson();

    protected String getHttpMethod() {
        return POST;
    }

    public URequest(String str) {
        this.mBaseUrl = str;
    }

    public void setBaseUrl(String str) {
        this.mBaseUrl = str;
    }

    public String getBaseUrl() {
        return this.mBaseUrl;
    }

    public Map<String, Object> getBodyPair() {
        return null;
    }

    public Map<String, FilePair> getFilePair() {
        return null;
    }

    public void onPrepareRequest() {
    }
}
