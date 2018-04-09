package com.umeng.socialize.net.base;

import android.content.Context;
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
import org.json.JSONObject;

public abstract class SocializeRequest extends URequest {
    public static final int REQUEST_ANALYTIC = 1;
    public static final int REQUEST_API = 2;
    public static final int REQUEST_SOCIAL = 0;
    private static final String TAG = "SocializeRequest";
    protected Context mContext;
    private Map<String, FilePair> mFileMap = new HashMap();
    private RequestMethod mMethod;
    protected int mOpId;
    protected Map<String, String> mParams = new HashMap();
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
        }
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
                    CharSequence asUrlImage = baseMediaObject.getThumbImage().asUrlImage();
                    if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(asUrlImage)) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, title);
                        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_THUMB, asUrlImage);
                        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, jSONObject.toString());
                    }
                }
            } catch (Exception e) {
                Log.m4548e("can`t add qzone title & thumb. " + e.getMessage());
            }
        }
    }

    public Map<String, Object> getBodyPair() {
        Map<String, Object> buildParams = buildParams();
        String mapTostring = mapTostring(buildParams);
        Log.m4552i(TAG, this.mBaseUrl + ": unencrypt string: " + mapTostring);
        if (mapTostring != null) {
            try {
                mapTostring = AesHelper.encryptNoPadding(mapTostring, "UTF-8");
                buildParams.clear();
                buildParams.put("ud_post", mapTostring);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buildParams;
    }

    public Map<String, FilePair> getFilePair() {
        return this.mFileMap;
    }

    public JSONObject toJson() {
        return null;
    }

    public String toGetUrl() {
        return SocializeNetUtils.generateEncryptGetURL(getBaseUrl(), buildParams());
    }

    protected Map<String, Object> buildParams() {
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
        return baseQuery;
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
        addStringParams(SocializeConstants.USHARETYPE, Config.shareType);
        String deviceId = DeviceConfig.getDeviceId(this.mContext);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, deviceId);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_MD5IMEI, AesHelper.md5(deviceId));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DE, Build.MODEL);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_MAC, DeviceConfig.getMac(this.mContext));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_OS, SocializeConstants.OS);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_EN, DeviceConfig.getNetworkAccessMode(this.mContext)[0]);
        addStringParams("uid", null);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, SocializeConstants.SDK_VERSION);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DT, String.valueOf(System.currentTimeMillis()));
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
