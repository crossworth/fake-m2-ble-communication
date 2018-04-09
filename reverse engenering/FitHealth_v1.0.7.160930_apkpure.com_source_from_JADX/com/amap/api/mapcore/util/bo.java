package com.amap.api.mapcore.util;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* compiled from: OfflineMapRemoveTask */
public class bo {
    private Context f278a;

    public bo(Context context) {
        this.f278a = context;
    }

    public void m338a(bg bgVar) {
        m337b(bgVar);
    }

    private boolean m337b(bg bgVar) {
        if (bgVar == null) {
            return false;
        }
        boolean a = m336a(bgVar.getAdcode(), this.f278a);
        if (a) {
            bgVar.m5711h();
            return a;
        }
        bgVar.m5710g();
        return false;
    }

    private boolean m336a(String str, Context context) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        List<String> b = bx.m366a(context).m377b(str);
        String a = dj.m570a(context);
        for (String str2 : b) {
            try {
                File file = new File(a + "vmap/" + str2);
                if (file.exists() && !cf.m428b(file)) {
                    cf.m424a("deleteDownload delete some thing wrong!");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
        try {
            cf.m431c(a + "vmap/");
            cf.m425a(str, context);
            return true;
        } catch (IOException e3) {
            e3.printStackTrace();
            return false;
        } catch (Exception e22) {
            e22.printStackTrace();
            return false;
        }
    }
}
