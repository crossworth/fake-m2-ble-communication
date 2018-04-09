package com.amap.api.services.proguard;

import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.proguard.ba.C0369a;
import com.zhuoyou.plugin.database.DataBaseContants;

/* compiled from: ConfigableConst */
public class C0389h {
    public static final String[] f1539a = new String[]{"com.amap.api.services"};

    public static String m1585a() {
        if (ServiceSettings.getInstance().getProtocol() == 1) {
            return "http://restapi.amap.com/v3";
        }
        return "https://restapi.amap.com/v3";
    }

    public static String m1587b() {
        if (ServiceSettings.getInstance().getProtocol() == 1) {
            return "http://yuntuapi.amap.com";
        }
        return "https://yuntuapi.amap.com";
    }

    public static String m1588c() {
        return ServiceSettings.getInstance().getLanguage();
    }

    public static ba m1584a(boolean z) {
        String str = "getSDKInfo";
        ba baVar = null;
        try {
            baVar = new C0369a("sea", "3.3.0", "AMAP SDK Android Search 3.3.0").m1302a(f1539a).m1301a(z).m1303a();
        } catch (Throwable e) {
            C0390i.m1594a(e, "ConfigableConst", str);
        }
        return baVar;
    }

    public static ba m1586b(boolean z) {
        String str = "getCloudSDKInfo";
        ba baVar = null;
        try {
            baVar = new C0369a(DataBaseContants.TABLE_DELETE_NAME, "3.3.0", "AMAP SDK Android Search 3.3.0").m1302a(f1539a).m1301a(z).m1303a();
        } catch (Throwable e) {
            C0390i.m1594a(e, "ConfigableConst", str);
        }
        return baVar;
    }
}
