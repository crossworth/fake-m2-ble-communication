package com.baidu.location.p008c.p009a;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import com.baidu.location.C0455f;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0468j;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONObject;

final class C0379a extends C0335e {
    private static C0379a f392p = null;
    private String f393a;
    private String f394b;
    private String f395c;
    private String f396d;
    private SharedPreferences f397e;
    private Handler f398f;

    private C0379a() {
        this.f393a = null;
        this.f398f = null;
        this.f398f = new Handler();
        this.k = new HashMap();
    }

    private boolean m441a(String str, String str2) {
        File file = new File(str2);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            while (true) {
                int read = bufferedInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    httpURLConnection.disconnect();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    static C0379a m442b() {
        if (f392p == null) {
            f392p = new C0379a();
        }
        return f392p;
    }

    private Handler m443f() {
        return this.f398f;
    }

    private boolean m444g() {
        if (this.f395c == null || this.f393a == null) {
            return false;
        }
        String str = C0468j.m1029g() + File.separator + "download" + File.separator + this.f393a;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!m441a(this.f395c, str + File.separator + "data.zip")) {
            return false;
        }
        file = new File(C0468j.m1029g() + File.separator + "indoorinfo" + File.separator + this.f393a + "/");
        if (file.exists()) {
            file.delete();
            file.mkdirs();
        } else {
            file.mkdirs();
        }
        try {
            new C0388f().m506a(str + File.separator + "data.zip", C0468j.m1029g() + File.separator + "indoorinfo" + File.separator + this.f393a + "/");
            Editor edit = this.f397e.edit();
            edit.putString("indoor_roadnet_" + this.f393a, this.f396d);
            edit.commit();
            C0386d.m487a().m501b();
            return true;
        } catch (Exception e) {
            file.delete();
            return false;
        }
    }

    public void mo1741a() {
        this.k.clear();
        this.k.put("bldg", this.f393a);
        this.k.put("vernum", this.f394b);
        this.k.put("mb", Build.MODEL);
        this.k.put("cuid", C0459b.m980a().f841b);
        this.h = "http://loc.map.baidu.com/apigetindoordata.php";
    }

    void m446a(String str) {
        this.f397e = PreferenceManager.getDefaultSharedPreferences(C0455f.getServiceContext());
        this.f393a = str;
        this.f394b = this.f397e.getString("indoor_roadnet_" + str, "null");
        m443f().postDelayed(new C0380b(this), 1000);
    }

    public void mo1742a(boolean z) {
        if (z) {
            try {
                JSONObject jSONObject = new JSONObject(this.j);
                int i = jSONObject.getInt("error");
                if (i == 0) {
                    this.f395c = jSONObject.getString("downloadlink");
                    if (jSONObject.has("vernum")) {
                        this.f396d = jSONObject.getString("vernum");
                    }
                    m443f().post(new C0381c(this));
                }
                if (i == 1) {
                    C0386d.m487a().m501b();
                }
                if (i == -1 || i != -2) {
                }
            } catch (Exception e) {
            }
        }
    }

    void mo1746c() {
        Editor edit = this.f397e.edit();
        edit.putString("indoor_roadnet_" + this.f393a, "0");
        edit.commit();
    }
}
