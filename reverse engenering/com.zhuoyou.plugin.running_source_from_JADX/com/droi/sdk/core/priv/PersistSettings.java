package com.droi.sdk.core.priv;

import android.content.Context;
import com.droi.sdk.internal.DroiLog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class PersistSettings {
    public static final String CONFIG = "config";
    public static final String DEV_CONFIG = "dev_config";
    public static final String GLOBAL_CONFIG = "global_config";
    public static final String IMG_CACHE_FOLDER = "/cache";
    public static final String KEY_AM_REALTIME_INTERVAL = "am_realtime_interval";
    public static final String KEY_AM_SCHEDULE_INTERVAL = "am_interval";
    public static final String KEY_AM_SCHEDULE_WIFI_ONLY = "am_wifionly";
    public static final String KEY_DEBUG_MODE = "debug_mode";
    public static final String KEY_DISABLE_ENCRYPT = "disable_encrypt";
    public static final String KEY_INSTALLATION_ID = "inst_id";
    public static final String KEY_IPLIST = "iplist";
    public static final String KEY_KL_TIMESTAMP = "kl_time";
    public static final String KEY_OAUTH_KEYS = "oauth_keys";
    public static final String KEY_PACKAGENAME = "pkgname";
    public static final String KEY_PREFERENCE = "preference";
    public static final String KEY_SERVER = "server";
    public static final String KEY_SERVER_CHUNK_SIZE = "svr_chunk_size";
    public static final String KEY_SERVER_DOWNLOAD_CHUNK_SIZE = "svr_down_chunk_size";
    public static final String KEY_SERVER_INFO_VER = "svr_info_ver";
    public static final String KEY_SERVER_MAX_SIZE = "svr_max_size";
    public static final String KEY_UID_FROM_FREEMEOS = "svr_from_fos";
    public static final String KEY_UID_L = "uid_l";
    public static final String KEY_UID_U = "uid_u";
    private static final String f2838a = "PERSIST_SETTINGS";
    private static final String f2839b = "/data.dat";
    private static final String f2840c = "/data.dat";
    private static final String f2841d = "/dev_data.json";
    private static HashMap<String, PersistSettings> f2842e;
    private static PersistSettings f2843h;
    private File f2844f;
    private JSONObject f2845g;

    private PersistSettings(File file) {
        this.f2844f = file;
        if (this.f2844f != null) {
            this.f2845g = m2650a(this.f2844f);
            if (this.f2845g == null) {
                this.f2845g = new JSONObject();
            }
        }
    }

    private JSONObject m2650a(File file) {
        FileInputStream fileInputStream;
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[4096];
            while (true) {
                int read = fileInputStream.read(bArr, 0, 4096);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            JSONObject jSONObject = new JSONObject(byteArrayOutputStream.toString());
            if (fileInputStream == null) {
                return jSONObject;
            }
            fileInputStream.close();
            return jSONObject;
        } catch (Exception e) {
            DroiLog.m2873w(f2838a, e);
            return null;
        } catch (Throwable th) {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    private synchronized boolean m2651a(File file, JSONObject jSONObject) {
        FileOutputStream fileOutputStream;
        boolean z = false;
        synchronized (this) {
            if (file != null) {
                try {
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write((jSONObject == null ? "" : jSONObject.toString()).getBytes());
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    z = true;
                } catch (Exception e) {
                    DroiLog.m2873w(f2838a, e);
                } catch (Throwable th) {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
            }
        }
        return z;
    }

    public static void initialize(Context context) {
        try {
            if (C0944p.m2802e(context)) {
                File file = new File(DroiStorageFinder.getSharedPath(context));
                register(DEV_CONFIG, new File(file, f2841d));
                register(GLOBAL_CONFIG, new File(file, "/data.dat"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            register(CONFIG, new File(new File(DroiStorageFinder.getPrivatePath(context)), "/data.dat"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static PersistSettings instance(String str) {
        if (f2842e == null) {
            throw new RuntimeException("Need to call initialize first for PersistSettings.");
        } else if (f2842e.keySet().contains(str)) {
            return (PersistSettings) f2842e.get(str);
        } else {
            if (f2843h == null) {
                f2843h = new PersistSettings(null);
            }
            return f2843h;
        }
    }

    public static void register(String str, File file) {
        if (f2842e == null) {
            f2842e = new HashMap();
        }
        if (!f2842e.containsKey(str)) {
            f2842e.put(str, new PersistSettings(file));
        }
    }

    public boolean getBoolean(String str, boolean z) {
        if (this != f2843h) {
            try {
                z = this.f2845g.getBoolean(str);
            } catch (Exception e) {
            }
        }
        return z;
    }

    public double getDouble(String str, double d) {
        if (this != f2843h) {
            try {
                d = this.f2845g.getDouble(str);
            } catch (JSONException e) {
            }
        }
        return d;
    }

    public int getInt(String str, int i) {
        if (this != f2843h) {
            try {
                i = this.f2845g.getInt(str);
            } catch (JSONException e) {
            }
        }
        return i;
    }

    public long getLong(String str, long j) {
        if (this != f2843h) {
            try {
                j = this.f2845g.getLong(str);
            } catch (JSONException e) {
            }
        }
        return j;
    }

    public String getString(String str, String str2) {
        if (this != f2843h) {
            try {
                str2 = this.f2845g.getString(str);
            } catch (JSONException e) {
            }
        }
        return str2;
    }

    public void remove(String str) {
        if (this != f2843h) {
            try {
                this.f2845g.remove(str);
                m2651a(this.f2844f, this.f2845g);
            } catch (Exception e) {
                DroiLog.m2873w(f2838a, e);
            }
        }
    }

    public boolean setBoolean(String str, boolean z) {
        if (this == f2843h) {
            return true;
        }
        try {
            this.f2845g.put(str, z);
            m2651a(this.f2844f, this.f2845g);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setDouble(String str, double d) {
        if (this == f2843h) {
            return true;
        }
        try {
            this.f2845g.put(str, d);
            m2651a(this.f2844f, this.f2845g);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public boolean setInt(String str, int i) {
        if (this == f2843h) {
            return true;
        }
        try {
            this.f2845g.put(str, i);
            m2651a(this.f2844f, this.f2845g);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public boolean setLong(String str, long j) {
        if (this == f2843h) {
            return true;
        }
        try {
            this.f2845g.put(str, j);
            m2651a(this.f2844f, this.f2845g);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public boolean setString(String str, String str2) {
        if (this == f2843h) {
            return true;
        }
        try {
            this.f2845g.put(str, str2);
            m2651a(this.f2844f, this.f2845g);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}
