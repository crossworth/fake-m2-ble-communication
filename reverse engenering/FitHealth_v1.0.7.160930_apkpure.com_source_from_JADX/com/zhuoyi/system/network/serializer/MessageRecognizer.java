package com.zhuoyi.system.network.serializer;

import com.zhuoyi.system.network.protocol.DownloadLogExtensionReq;
import com.zhuoyi.system.network.protocol.DownloadLogExtensionResp;
import com.zhuoyi.system.network.protocol.GetAdsLogReq;
import com.zhuoyi.system.network.protocol.GetAdsLogResp;
import com.zhuoyi.system.network.protocol.GetAdsReq;
import com.zhuoyi.system.network.protocol.GetAdsResp;
import com.zhuoyi.system.network.protocol.GetApkCommandReq;
import com.zhuoyi.system.network.protocol.GetApkCommandResp;
import com.zhuoyi.system.network.protocol.GetApkReq;
import com.zhuoyi.system.network.protocol.GetApkResp;
import com.zhuoyi.system.network.protocol.GetCommonConfigReq;
import com.zhuoyi.system.network.protocol.GetCommonConfigResp;
import com.zhuoyi.system.network.protocol.GetDefinedApkReq;
import com.zhuoyi.system.network.protocol.GetDefinedApkResp;
import com.zhuoyi.system.network.protocol.GetDesktopAdReq;
import com.zhuoyi.system.network.protocol.GetDesktopAdResp;
import com.zhuoyi.system.network.protocol.GetPushInAppReq;
import com.zhuoyi.system.network.protocol.GetPushInAppResp;
import com.zhuoyi.system.network.protocol.GetPushReq;
import com.zhuoyi.system.network.protocol.GetPushResp;
import com.zhuoyi.system.network.protocol.GetSDKDownloadReq;
import com.zhuoyi.system.network.protocol.GetSDKDownloadResp;
import com.zhuoyi.system.network.protocol.GetSaleStatisticsReq;
import com.zhuoyi.system.network.protocol.GetSaleStatisticsResp;
import com.zhuoyi.system.network.protocol.GetShortcutNewReq;
import com.zhuoyi.system.network.protocol.GetShortcutNewResp;
import com.zhuoyi.system.network.protocol.GetSilentReq;
import com.zhuoyi.system.network.protocol.GetSilentResp;
import com.zhuoyi.system.network.protocol.GetTokenReq;
import com.zhuoyi.system.network.protocol.GetTokenResp;
import com.zhuoyi.system.network.protocol.GetZoneServerReq;
import com.zhuoyi.system.network.protocol.GetZoneServerResp;
import java.util.HashMap;
import java.util.Map;

public class MessageRecognizer {
    private static Map<Integer, Class> m_MessageClasses = new HashMap();

    static {
        m_MessageClasses.put(Integer.valueOf(198001), GetZoneServerReq.class);
        m_MessageClasses.put(Integer.valueOf(298001), GetZoneServerResp.class);
        m_MessageClasses.put(Integer.valueOf(114002), GetAdsLogReq.class);
        m_MessageClasses.put(Integer.valueOf(214002), GetAdsLogResp.class);
        m_MessageClasses.put(Integer.valueOf(111001), GetAdsReq.class);
        m_MessageClasses.put(Integer.valueOf(211001), GetAdsResp.class);
        m_MessageClasses.put(Integer.valueOf(111007), GetApkCommandReq.class);
        m_MessageClasses.put(Integer.valueOf(211007), GetApkCommandResp.class);
        m_MessageClasses.put(Integer.valueOf(111005), GetApkReq.class);
        m_MessageClasses.put(Integer.valueOf(211005), GetApkResp.class);
        m_MessageClasses.put(Integer.valueOf(111009), GetCommonConfigReq.class);
        m_MessageClasses.put(Integer.valueOf(211009), GetCommonConfigResp.class);
        m_MessageClasses.put(Integer.valueOf(111011), GetDesktopAdReq.class);
        m_MessageClasses.put(Integer.valueOf(211011), GetDesktopAdResp.class);
        m_MessageClasses.put(Integer.valueOf(111014), GetPushInAppReq.class);
        m_MessageClasses.put(Integer.valueOf(211014), GetPushInAppResp.class);
        m_MessageClasses.put(Integer.valueOf(111006), GetPushReq.class);
        m_MessageClasses.put(Integer.valueOf(211006), GetPushResp.class);
        m_MessageClasses.put(Integer.valueOf(111008), GetShortcutNewReq.class);
        m_MessageClasses.put(Integer.valueOf(211008), GetShortcutNewResp.class);
        m_MessageClasses.put(Integer.valueOf(114007), DownloadLogExtensionReq.class);
        m_MessageClasses.put(Integer.valueOf(214007), DownloadLogExtensionResp.class);
        m_MessageClasses.put(Integer.valueOf(114001), GetSaleStatisticsReq.class);
        m_MessageClasses.put(Integer.valueOf(214001), GetSaleStatisticsResp.class);
        m_MessageClasses.put(Integer.valueOf(114005), GetTokenReq.class);
        m_MessageClasses.put(Integer.valueOf(214005), GetTokenResp.class);
        m_MessageClasses.put(Integer.valueOf(111015), GetDefinedApkReq.class);
        m_MessageClasses.put(Integer.valueOf(211015), GetDefinedApkResp.class);
        m_MessageClasses.put(Integer.valueOf(111016), GetSilentReq.class);
        m_MessageClasses.put(Integer.valueOf(211016), GetSilentResp.class);
        m_MessageClasses.put(Integer.valueOf(111017), GetSDKDownloadReq.class);
        m_MessageClasses.put(Integer.valueOf(211017), GetSDKDownloadResp.class);
    }

    public static Class getClassByCode(int code) {
        if (m_MessageClasses.containsKey(Integer.valueOf(code))) {
            return (Class) m_MessageClasses.get(Integer.valueOf(code));
        }
        return null;
    }

    public static boolean addClass(Class cls) {
        SignalCode sc = AttributeUitl.getMessageAttribute(cls);
        if (sc == null || m_MessageClasses.containsKey(Integer.valueOf(sc.messageCode()))) {
            return false;
        }
        m_MessageClasses.put(Integer.valueOf(sc.messageCode()), cls);
        return true;
    }
}
