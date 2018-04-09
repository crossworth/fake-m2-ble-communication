package com.amap.api.services.proguard;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.zhuoyi.system.promotion.util.PromConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;

/* compiled from: CoreUtil */
public class C0390i {
    public static boolean m1595a(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static double m1590a(int i) {
        return ((double) i) / 111700.0d;
    }

    public static void m1596b(String str) throws AMapException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status") && jSONObject.has("infocode")) {
                String string = jSONObject.getString("status");
                int i = jSONObject.getInt("infocode");
                if (!string.equals("1") && string.equals("0")) {
                    switch (i) {
                        case 10001:
                            throw new AMapException(AMapException.AMAP_INVALID_USER_KEY);
                        case 10002:
                            throw new AMapException(AMapException.AMAP_SERVICE_NOT_AVAILBALE);
                        case 10003:
                            throw new AMapException(AMapException.AMAP_DAILY_QUERY_OVER_LIMIT);
                        case 10004:
                            throw new AMapException(AMapException.AMAP_ACCESS_TOO_FREQUENT);
                        case 10005:
                            throw new AMapException(AMapException.AMAP_INVALID_USER_IP);
                        case 10006:
                            throw new AMapException(AMapException.AMAP_INVALID_USER_DOMAIN);
                        case 10007:
                            throw new AMapException(AMapException.AMAP_SIGNATURE_ERROR);
                        case 10008:
                            throw new AMapException(AMapException.AMAP_INVALID_USER_SCODE);
                        case 10009:
                            throw new AMapException(AMapException.AMAP_USERKEY_PLAT_NOMATCH);
                        case 10010:
                            throw new AMapException(AMapException.AMAP_IP_QUERY_OVER_LIMIT);
                        case 10011:
                            throw new AMapException(AMapException.AMAP_NOT_SUPPORT_HTTPS);
                        case 10012:
                            throw new AMapException(AMapException.AMAP_INSUFFICIENT_PRIVILEGES);
                        case 10013:
                            throw new AMapException(AMapException.AMAP_USER_KEY_RECYCLED);
                        case 20000:
                            throw new AMapException(AMapException.AMAP_SERVICE_INVALID_PARAMS);
                        case 20001:
                            throw new AMapException(AMapException.AMAP_SERVICE_MISSING_REQUIRED_PARAMS);
                        case 20002:
                            throw new AMapException(AMapException.AMAP_SERVICE_ILLEGAL_REQUEST);
                        case PromConstants.PROM_SEND_PROM_DATA_CODE /*20003*/:
                            throw new AMapException(AMapException.AMAP_SERVICE_UNKNOWN_ERROR);
                        case 20800:
                            throw new AMapException(AMapException.AMAP_ROUTE_OUT_OF_SERVICE);
                        case 20801:
                            throw new AMapException(AMapException.AMAP_ROUTE_NO_ROADS_NEARBY);
                        case 20802:
                            throw new AMapException(AMapException.AMAP_ROUTE_FAIL);
                        case 20803:
                            throw new AMapException(AMapException.AMAP_OVER_DIRECTION_RANGE);
                        case 22000:
                            throw new AMapException(AMapException.AMAP_SERVICE_TABLEID_NOT_EXIST);
                        case 30000:
                            throw new AMapException(AMapException.AMAP_ENGINE_RESPONSE_ERROR);
                        case 30001:
                            throw new AMapException(AMapException.AMAP_ENGINE_RESPONSE_DATA_ERROR);
                        case 30002:
                            throw new AMapException(AMapException.AMAP_ENGINE_CONNECT_TIMEOUT);
                        case 30003:
                            throw new AMapException(AMapException.AMAP_ENGINE_RETURN_TIMEOUT);
                        case 32000:
                            throw new AMapException(AMapException.AMAP_ENGINE_TABLEID_NOT_EXIST);
                        case 32001:
                            throw new AMapException(AMapException.AMAP_ID_NOT_EXIST);
                        case 32002:
                            throw new AMapException(AMapException.AMAP_SERVICE_MAINTENANCE);
                        case 32200:
                            throw new AMapException(AMapException.AMAP_NEARBY_INVALID_USERID);
                        case 32201:
                            throw new AMapException(AMapException.AMAP_NEARBY_KEY_NOT_BIND);
                        default:
                            throw new AMapException(jSONObject.getString("info"));
                    }
                    C0390i.m1594a(e, "CoreUtil", "paseAuthFailurJson");
                    throw new AMapException("协议解析错误 - ProtocolException");
                }
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "CoreUtil", "paseAuthFailurJson");
            throw new AMapException("协议解析错误 - ProtocolException");
        }
    }

    public static double m1589a(double d) {
        return Double.parseDouble(new DecimalFormat("0.000000", new DecimalFormatSymbols(Locale.US)).format(d));
    }

    public static String m1591a(LatLonPoint latLonPoint) {
        if (latLonPoint == null) {
            return "";
        }
        double a = C0390i.m1589a(latLonPoint.getLongitude());
        return a + SeparatorConstants.SEPARATOR_ADS_ID + C0390i.m1589a(latLonPoint.getLatitude());
    }

    public static Date m1597c(String str) {
        Date date = null;
        if (!(str == null || str.trim().equals(""))) {
            try {
                date = new SimpleDateFormat("HHmm").parse(str);
            } catch (Throwable e) {
                C0390i.m1594a(e, "CoreUtil", "parseString2Time");
            }
        }
        return date;
    }

    public static String m1592a(Date date) {
        return date != null ? new SimpleDateFormat("HH:mm").format(date) : "";
    }

    public static Date m1598d(String str) {
        Date date = null;
        if (!(str == null || str.trim().equals(""))) {
            try {
                date = new SimpleDateFormat("HH:mm").parse(str);
            } catch (Throwable e) {
                C0390i.m1594a(e, "CoreUtil", "parseTime");
            }
        }
        return date;
    }

    public static String m1593a(List<LatLonPoint> list) {
        if (list == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            LatLonPoint latLonPoint = (LatLonPoint) list.get(i);
            double a = C0390i.m1589a(latLonPoint.getLongitude());
            stringBuffer.append(a).append(SeparatorConstants.SEPARATOR_ADS_ID).append(C0390i.m1589a(latLonPoint.getLatitude()));
            stringBuffer.append(";");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    public static void m1594a(Throwable th, String str, String str2) {
        bh a = bh.m4435a();
        if (a != null) {
            a.m4442c(th, str, str2);
        }
        th.printStackTrace();
    }
}
