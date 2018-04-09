package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: OfflineUpdateCityHandler */
public class bp extends cj<String, List<OfflineMapProvince>> {
    private Context f5355j;

    protected /* synthetic */ Object mo3002b(String str) throws AMapException {
        return m5736a(str);
    }

    protected /* synthetic */ Object mo3004b(byte[] bArr) throws AMapException {
        return mo3003a(bArr);
    }

    public bp(Context context, String str) {
        super(context, str);
        getClass();
        m972a(5000);
        getClass();
        m975b(50000);
    }

    public void m5738a(Context context) {
        this.f5355j = context;
    }

    protected List<OfflineMapProvince> mo3003a(byte[] bArr) throws AMapException {
        List<OfflineMapProvince> arrayList = new ArrayList();
        try {
            String str = new String(bArr, "utf-8");
            dj.m579a(str);
            if (!(str == null || "".equals(str))) {
                String optString = new JSONObject(str).optString("status");
                if (!(optString == null || optString.equals("") || optString.equals("0"))) {
                    arrayList = m5736a(str);
                }
            }
        } catch (Throwable th) {
            ee.m4243a(th, "OfflineUpdateCityHandler", "loadData jsonInit");
            th.printStackTrace();
        }
        return arrayList;
    }

    private void m5734c(String str) {
        OutputStream fileOutputStream;
        Throwable e;
        if (!dj.m589b(this.f5355j).equals("")) {
            File file = new File(dj.m589b(this.f5355j) + "offlinemapv4.png");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Throwable e2) {
                    ee.m4243a(e2, "OfflineUpdateCityHandler", "writeSD dirCreate");
                    e2.printStackTrace();
                }
            }
            if (b_() > 1048576) {
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        fileOutputStream.write(str.getBytes("utf-8"));
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (FileNotFoundException e4) {
                        e = e4;
                        try {
                            ee.m4243a(e, "OfflineUpdateCityHandler", "writeSD filenotfound");
                            e.printStackTrace();
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e32) {
                                    e32.printStackTrace();
                                }
                            }
                        } catch (Throwable th) {
                            e = th;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            throw e;
                        }
                    } catch (IOException e6) {
                        e = e6;
                        ee.m4243a(e, "OfflineUpdateCityHandler", "writeSD io");
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e322) {
                                e322.printStackTrace();
                            }
                        }
                    }
                } catch (FileNotFoundException e7) {
                    e = e7;
                    fileOutputStream = null;
                    ee.m4243a(e, "OfflineUpdateCityHandler", "writeSD filenotfound");
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e8) {
                    e = e8;
                    fileOutputStream = null;
                    ee.m4243a(e, "OfflineUpdateCityHandler", "writeSD io");
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th2) {
                    e = th2;
                    fileOutputStream = null;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw e;
                }
            }
        }
    }

    public long b_() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return 0;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public String mo1630a() {
        return "http://restapi.amap.com/v3/config/resource";
    }

    protected List<OfflineMapProvince> m5736a(String str) throws AMapException {
        List<OfflineMapProvince> list = null;
        try {
            if (this.f5355j != null) {
                m5734c(str);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "OfflineUpdateCityHandler", "loadData jsonInit");
            th.printStackTrace();
        }
        try {
            list = cf.m427b(str);
        } catch (Throwable th2) {
            ee.m4243a(th2, "OfflineUpdateCityHandler", "loadData parseJson");
            th2.printStackTrace();
        }
        return list;
    }

    public Map<String, String> mo1631b() {
        Map<String, String> hashMap = new HashMap();
        if (!TextUtils.isEmpty(MapsInitializer.KEY)) {
            dm.m611a(MapsInitializer.KEY);
        }
        hashMap.put("key", dl.m607f(this.f5355j));
        hashMap.put("opertype", "offlinemap_with_province_vfour");
        hashMap.put("plattype", "android");
        hashMap.put("product", C0273r.f695b);
        hashMap.put("version", "3.3.2");
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, "standard");
        hashMap.put("output", "json");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(dl.m607f(this.f5355j));
        stringBuffer.append("&opertype=offlinemap_with_province_vfour");
        stringBuffer.append("&plattype=android");
        stringBuffer.append("&product=").append(C0273r.f695b);
        stringBuffer.append("&version=").append("3.3.2");
        stringBuffer.append("&ext=standard");
        stringBuffer.append("&output=json");
        String d = dx.m727d(stringBuffer.toString());
        String a = dn.m616a();
        hashMap.put("ts", a);
        hashMap.put("scode", dn.m621a(this.f5355j, a, d));
        return hashMap;
    }
}
