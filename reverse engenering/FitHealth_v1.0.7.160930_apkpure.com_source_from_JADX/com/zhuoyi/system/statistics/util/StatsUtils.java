package com.zhuoyi.system.statistics.util;

import android.content.Context;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.protocol.GetTokenReq;
import com.zhuoyi.system.network.protocol.GetTokenResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.network.util.NetworkAddressUtil;
import com.zhuoyi.system.network.util.NetworkAddressUtil.InitServerAddrResponse;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.FileConstants;
import com.zhuoyi.system.util.constant.Session;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.util.EncodingUtils;

public class StatsUtils {
    public static final String TAG = "StatUtils";
    private static Context mContext = null;
    private static StatsUtils mInstance = null;

    public interface TokenCallBack {
        void onTokenResponse(String str);
    }

    public static StatsUtils getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new StatsUtils();
        }
        return mInstance;
    }

    private StatsUtils() {
    }

    private void initService(final TokenCallBack callback) {
        NetworkAddressUtil.getInstance(mContext).initNetworkAddress(new InitServerAddrResponse() {
            public void onServerAddrResponseSuccess(Session session) {
                StatsUtils.this.sendGetTokenReq(session, callback);
            }

            public void onServerAddrResponseError() {
                callback.onTokenResponse("");
                Logger.error("Get token error");
            }
        });
    }

    public void saveTimeStamp(String fileName, String timeStamp) {
        try {
            FileOutputStream fout = mContext.openFileOutput(fileName, 0);
            fout.write(timeStamp.getBytes());
            fout.close();
        } catch (Exception e) {
        }
    }

    public String getStatsPath() {
        return "/sdcard/" + FileConstants.FILE_ROOT + "/" + StatsConstants.STATS_DIR;
    }

    public String getTimeStamp(String fileName) {
        String ret;
        try {
            FileInputStream fin = mContext.openFileInput(fileName);
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer);
            ret = EncodingUtils.getString(buffer, "UTF-8");
            if (ret.equals(StatsConstants.TIME_STAMP_UPLOAD_SUCCESS)) {
                ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                saveTimeStamp(fileName, ret);
            }
            fin.close();
            return ret;
        } catch (Exception e) {
            ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            saveTimeStamp(fileName, ret);
            return ret;
        }
    }

    private void sendGetTokenReq(Session session, final TokenCallBack callback) {
        Logger.debug(TAG, "sendGetTokenReq");
        HTTPConnection.getInstance().sendRequest(session.getStatisNetworkAddr(), new GetTokenReq(), new NetworkCallback() {
            public void onResponse(Boolean result, ZyCom_Message respMessage) {
                Logger.debug(StatsUtils.TAG, "Get token onResponse");
                if (result.booleanValue() && respMessage.head.code == AttributeUitl.getMessageCode(GetTokenResp.class)) {
                    GetTokenResp resp = respMessage.message;
                    if (resp.getErrorCode() == 0) {
                        Logger.debug("token = " + resp.getToken());
                        callback.onTokenResponse(resp.getToken());
                        return;
                    }
                    callback.onTokenResponse("");
                    Logger.error("Get token error");
                    return;
                }
                callback.onTokenResponse("");
                Logger.error("Get token error");
            }
        });
    }

    public void getStatsToken(TokenCallBack callback) {
        Logger.debug(TAG, "start to get token");
        initService(callback);
    }
}
