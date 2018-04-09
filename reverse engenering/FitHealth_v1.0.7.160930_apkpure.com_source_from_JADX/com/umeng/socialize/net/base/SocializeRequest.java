package com.umeng.socialize.net.base;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.common.ImageFormat;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.utils.AesHelper;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.net.utils.URequest.FilePair;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public abstract class SocializeRequest extends URequest {
    public static final int REQUEST_ANALYTIC = 1;
    public static final int REQUEST_API = 2;
    public static final int REQUEST_SOCIAL = 0;
    protected Context mContext;
    private boolean mEncrypt = true;
    private Map<String, FilePair> mFileMap = new HashMap();
    private RequestMethod mMethod;
    protected int mOpId;
    private Map<String, String> mParams = new HashMap();
    private int mReqType = 1;
    protected Class<? extends SocializeReseponse> mResponseClz;

    protected enum FILE_TYPE {
        IMAGE,
        VEDIO
    }

    protected enum RequestMethod {
        GET {
            public String toString() {
                return SocializeRequest.GET;
            }
        },
        POST {
            public String toString() {
                return SocializeRequest.POST;
            }
        };
    }

    protected abstract String getPath();

    public SocializeRequest(Context context, String str, Class<? extends SocializeReseponse> cls, int i, RequestMethod requestMethod) {
        super("");
        this.mResponseClz = cls;
        this.mOpId = i;
        this.mContext = context;
        this.mMethod = requestMethod;
        AesHelper.setPassword(SocializeUtils.getAppkey(context));
    }

    public void setEncrypt(boolean z) {
        this.mEncrypt = z;
    }

    public void setReqType(int i) {
        this.mReqType = i;
    }

    public void addStringParams(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            this.mParams.put(str, str2);
        }
    }

    public void addFileParams(byte[] bArr, FILE_TYPE file_type, String str) {
        if (FILE_TYPE.IMAGE == file_type) {
            String checkFormat = ImageFormat.checkFormat(bArr);
            if (TextUtils.isEmpty(checkFormat)) {
                checkFormat = "png";
            }
            if (TextUtils.isEmpty(str)) {
                str = System.currentTimeMillis() + "";
            }
            this.mFileMap.put(SocializeProtocolConstants.PROTOCOL_KEY_IMAGE, new FilePair(str + "" + checkFormat, bArr));
        }
    }

    public void addMediaParams(UMediaObject uMediaObject) {
        if (uMediaObject != null) {
            if (uMediaObject.isUrlMedia()) {
                for (Entry entry : uMediaObject.toUrlExtraParams().entrySet()) {
                    addStringParams((String) entry.getKey(), entry.getValue().toString());
                }
            } else {
                byte[] toByte = uMediaObject.toByte();
                if (toByte != null) {
                    addFileParams(toByte, FILE_TYPE.IMAGE, null);
                }
            }
            try {
                if (uMediaObject instanceof BaseMediaObject) {
                    BaseMediaObject baseMediaObject = (BaseMediaObject) uMediaObject;
                    CharSequence title = baseMediaObject.getTitle();
                    CharSequence thumb = baseMediaObject.getThumb();
                    if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(thumb)) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, title);
                        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_THUMB, thumb);
                        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, jSONObject.toString());
                    }
                }
            } catch (Exception e) {
                Log.m3250e("can`t add qzone title & thumb. " + e.getMessage());
            }
        }
    }

    public Map<String, Object> getBodyPair() {
        Map<String, Object> baseQuery = SocializeNetUtils.getBaseQuery(this.mContext);
        if (!TextUtils.isEmpty(Config.EntityKey)) {
            baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
        }
        if (!TextUtils.isEmpty(Config.SessionId)) {
            baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, Config.SessionId);
        }
        baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE, Integer.valueOf(this.mReqType));
        baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_OPID, Integer.valueOf(this.mOpId));
        baseQuery.put("uid", Config.UID);
        baseQuery.putAll(this.mParams);
        String mapTostring = mapTostring(baseQuery);
        Log.m3251e("xxxxx", "raw=" + mapTostring);
        Log.m3254i("--->", "unencrypt string: " + mapTostring);
        if (mapTostring != null) {
            try {
                mapTostring = AesHelper.encryptNoPadding(mapTostring, "UTF-8");
                baseQuery.clear();
                baseQuery.put("ud_post", mapTostring);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.m3250e("xxxxx send~~=" + baseQuery);
        return baseQuery;
    }

    public Map<String, FilePair> getFilePair() {
        return this.mFileMap;
    }

    public JSONObject toJson() {
        return null;
    }

    public String toGetUrl() {
        Map baseQuery = SocializeNetUtils.getBaseQuery(this.mContext);
        if (!TextUtils.isEmpty(Config.EntityKey)) {
            baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
        }
        if (!TextUtils.isEmpty(Config.SessionId)) {
            baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, Config.SessionId);
        }
        baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE, Integer.valueOf(this.mReqType));
        baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_OPID, Integer.valueOf(this.mOpId));
        baseQuery.put("uid", Config.UID);
        baseQuery.putAll(this.mParams);
        return SocializeNetUtils.generateGetURL(getBaseUrl(), baseQuery);
    }

    public void setBaseUrl(String str) {
        try {
            super.setBaseUrl(new URL(new URL(str), getPath()).toString());
        } catch (Throwable e) {
            throw new SocializeException("Can not generate correct url in SocializeRequest [" + getBaseUrl() + "]", e);
        }
    }

    public void onPrepareRequest() {
        addStringParams("pcv", SocializeConstants.PROTOCOL_VERSON);
        String deviceId = DeviceConfig.getDeviceId(this.mContext);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, deviceId);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_MD5IMEI, AesHelper.md5(deviceId));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DE, Build.MODEL);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_MAC, DeviceConfig.getMac(this.mContext));
        addStringParams("android_id", DeviceConfig.getAndroidID(this.mContext));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_SHARE_NUM, DeviceConfig.getDeviceSN());
        addStringParams("os", "Android");
        addStringParams("en", DeviceConfig.getNetworkAccessMode(this.mContext)[0]);
        addStringParams("uid", null);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, SocializeConstants.SDK_VERSION);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DT, String.valueOf(System.currentTimeMillis()));
        if (getHttpMethod() == POST && this.mParams != null && !this.mParams.isEmpty()) {
            Set<String> keySet = this.mParams.keySet();
            Builder builder = new Builder();
            for (String deviceId2 : keySet) {
                if (this.mParams.get(deviceId2) != null) {
                    builder.appendQueryParameter(deviceId2, (String) this.mParams.get(deviceId2));
                }
            }
            this.mBaseUrl += "?" + builder.build().getEncodedQuery();
        }
    }

    private String mapTostring(Map<String, Object> map) {
        String str = null;
        if (!this.mParams.isEmpty()) {
            try {
                str = new JSONObject(map).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    protected String getHttpMethod() {
        switch (this.mMethod) {
            case POST:
                return POST;
            default:
                return GET;
        }
    }
}
