package com.droi.sdk.core.priv;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiHttpRequest;
import com.droi.sdk.core.DroiHttpRequest.Request;
import com.droi.sdk.core.DroiHttpRequest.Response;
import com.droi.sdk.core.DroiRunnable;
import com.droi.sdk.core.DroiTask;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.core.TaskDispatcher;
import com.droi.sdk.internal.DroiLog;
import com.tencent.connect.common.Constants;
import com.tyd.aidlservice.internal.Tutil;
import com.umeng.socialize.handler.TwitterPreferences;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.HttpResponseCode;

public class C0939m {
    public static final String f3061a = "X-Droi-AppID";
    public static final String f3062b = "X-Droi-Platform-Key";
    public static final String f3063c = "X-Droi-DeviceID";
    private static final String f3064d = "REMOTE_SERVICE_HELPER";

    private interface C0915a {
        void mo1910a(JSONObject jSONObject);
    }

    private interface C0916b {
        String mo1911a();
    }

    public static class C0917c implements C0915a {
        public int f2975a;
        public String f2976b;
        public JSONArray f2977c;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f2975a = jSONObject.getInt("Code");
                if (jSONObject.has("Ticket")) {
                    this.f2976b = jSONObject.getString("Ticket");
                }
                if (jSONObject.has("Result")) {
                    this.f2977c = jSONObject.getJSONArray("Result");
                }
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
                this.f2975a = -1;
            }
        }
    }

    public static class C0918d implements C0915a {
        public int f2978a;
        public String f2979b;
        public JSONObject f2980c;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f2978a = jSONObject.getInt("Code");
                if (jSONObject.has("Ticket")) {
                    this.f2979b = jSONObject.getString("Ticket");
                }
                if (jSONObject.has("Result")) {
                    this.f2980c = jSONObject.getJSONObject("Result");
                }
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
                this.f2978a = -1;
            }
        }
    }

    public static class C0919e implements C0916b {
        public String f2981a;
        public HashMap<String, Object> f2982b;
        public ArrayList<String> f2983c;
        private final C0940n f2984d;

        public C0919e(C0940n c0940n, File file, String str, String str2) {
            this.f2984d = c0940n;
            m2718a(file, str, str2);
        }

        public C0919e(C0940n c0940n, byte[] bArr, String str, String str2) {
            this.f2984d = c0940n;
            m2720a(bArr, str, str2);
        }

        private void m2717a(File file) {
            Throwable th;
            this.f2983c = new ArrayList();
            int i = this.f2984d.f3068c;
            byte[] bArr = new byte[8192];
            Closeable closeable = null;
            Closeable fileInputStream;
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                fileInputStream = new FileInputStream(file);
                int i2 = 8192;
                int i3 = 0;
                while (true) {
                    try {
                        i2 = fileInputStream.read(bArr, 0, i2);
                        if (i2 <= 0) {
                            break;
                        }
                        instance.update(bArr, 0, i2);
                        i3 += i2;
                        if (i3 >= i) {
                            this.f2983c.add(C0944p.m2786a(instance.digest()));
                            instance.reset();
                            i2 = 8192;
                            i3 = 0;
                        } else {
                            i2 = this.f2984d.f3068c - i3;
                            if (i2 > 8192) {
                                i2 = 8192;
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                if (i3 > 0) {
                    this.f2983c.add(C0944p.m2786a(instance.digest()));
                }
                FileDescriptorHelper.closeQuietly(fileInputStream);
            } catch (Exception e2) {
                fileInputStream = null;
                try {
                    throw new RuntimeException("No builtin MD5 algorithm.");
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    closeable = fileInputStream;
                    th = th3;
                    FileDescriptorHelper.closeQuietly(closeable);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                FileDescriptorHelper.closeQuietly(closeable);
                throw th;
            }
        }

        private void m2718a(File file, String str, String str2) {
            Object obj;
            this.f2982b = new HashMap();
            this.f2982b.put("Path", file.getPath());
            HashMap hashMap = this.f2982b;
            String str3 = "Type";
            if (str2 == null) {
                obj = "";
            }
            hashMap.put(str3, obj);
            this.f2982b.put("Size", Long.valueOf(file.length()));
            this.f2982b.put("ModifyTime", C0895b.m2661a(new Date(file.lastModified())));
            this.f2982b.put("CreatedTime", C0895b.m2661a(new Date()));
            this.f2982b.put("FileDescription", str);
            this.f2982b.put("MD5", C0944p.m2786a(FileDescriptorHelper.calculateHash(file)));
            m2717a(file);
        }

        private void m2719a(byte[] bArr) {
            this.f2983c = new ArrayList();
            long length = (long) bArr.length;
            int i = this.f2984d.f3068c;
            byte[] bArr2 = new byte[8192];
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                int i2 = 8192;
                int i3 = 0;
                int i4 = 0;
                while (((long) i3) < length) {
                    int i5 = (int) (length - ((long) i3));
                    if (i2 <= i5) {
                        i5 = i2;
                    }
                    instance.update(bArr, i3, i5);
                    i4 += i5;
                    i2 = i3 + i5;
                    if (i4 >= i) {
                        this.f2983c.add(C0944p.m2786a(instance.digest()));
                        instance.reset();
                        i3 = i2;
                        i4 = 0;
                        i2 = 8192;
                    } else {
                        i5 = this.f2984d.f3068c - i4;
                        if (i5 > 8192) {
                            i5 = 8192;
                        }
                        i3 = i2;
                        i2 = i5;
                    }
                }
                if (i4 > 0) {
                    this.f2983c.add(C0944p.m2786a(instance.digest()));
                }
                FileDescriptorHelper.closeQuietly(null);
            } catch (Exception e) {
                throw new RuntimeException("No builtin MD5 algorithm.");
            } catch (Throwable th) {
                FileDescriptorHelper.closeQuietly(null);
            }
        }

        private void m2720a(byte[] bArr, String str, String str2) {
            Object obj;
            String a = C0895b.m2661a(new Date());
            this.f2982b = new HashMap();
            this.f2982b.put("Path", str);
            HashMap hashMap = this.f2982b;
            String str3 = "Type";
            if (str2 == null) {
                obj = "";
            }
            hashMap.put(str3, obj);
            this.f2982b.put("Size", Integer.valueOf(bArr.length));
            this.f2982b.put("ModifyTime", a);
            this.f2982b.put("CreatedTime", a);
            this.f2982b.put("FileDescription", str);
            this.f2982b.put("MD5", C0944p.m2786a(FileDescriptorHelper.calculateHash(bArr)));
            m2719a(bArr);
        }

        public String mo1911a() {
            JSONObject jSONObject = new JSONObject();
            DroiUser currentUser = DroiUser.getCurrentUser();
            Object sessionToken = currentUser != null ? currentUser.getSessionToken() : null;
            try {
                jSONObject.put("ConfVer", this.f2984d.f3066a);
                if (sessionToken != null) {
                    jSONObject.put("AccessToken", sessionToken);
                }
                JSONObject jSONObject2 = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(jSONObject2);
                jSONObject.put("Files", jSONArray);
                if (!(this.f2981a == null || "0".equals(this.f2981a))) {
                    jSONObject2.put("FID", this.f2981a);
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject2.put("Metadata", jSONObject3);
                for (String str : this.f2982b.keySet()) {
                    jSONObject3.put(str, this.f2982b.get(str));
                }
                JSONArray jSONArray2 = new JSONArray();
                jSONObject2.put("Hashes", jSONArray2);
                Iterator it = this.f2983c.iterator();
                while (it.hasNext()) {
                    jSONArray2.put((String) it.next());
                }
                return jSONObject.toString();
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
                return null;
            }
        }

        public ArrayList<String> m2722b() {
            return this.f2983c;
        }
    }

    public static class C0920f implements C0915a {
        public int f2985a;
        public String f2986b;
        public int f2987c;
        public ArrayList<HashMap<String, Object>> f2988d;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f2985a = jSONObject.getInt("Code");
                this.f2986b = jSONObject.getString("Message");
                this.f2987c = jSONObject.getInt("ExpiresIn");
                this.f2988d = new ArrayList();
                if (jSONObject.has("Files")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("Files");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        HashMap hashMap = new HashMap();
                        Iterator keys = jSONObject2.keys();
                        while (keys.hasNext()) {
                            String str = (String) keys.next();
                            hashMap.put(str, jSONObject2.get(str));
                        }
                        this.f2988d.add(hashMap);
                    }
                }
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
            }
        }
    }

    public static class C0921g implements C0916b {
        public int f2989a;
        public String f2990b;

        public String mo1911a() {
            String str = null;
            try {
                DroiUser currentUser = DroiUser.getCurrentUser();
                Object sessionToken = currentUser != null ? currentUser.getSessionToken() : null;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ConfVer", this.f2989a);
                if (sessionToken != null) {
                    jSONObject.put("AccessToken", sessionToken);
                }
                JSONArray jSONArray = new JSONArray();
                jSONObject.put("Files", jSONArray);
                JSONObject jSONObject2 = new JSONObject();
                jSONArray.put(jSONObject2);
                jSONObject2.put("FID", this.f2990b);
                str = jSONObject.toString();
            } catch (Exception e) {
            }
            return str;
        }
    }

    public static class C0922h implements C0915a {
        public int f2991a;
        public String f2992b;
        public int f2993c;
        public ArrayList<HashMap<String, Object>> f2994d;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f2991a = jSONObject.getInt("Code");
                this.f2992b = jSONObject.getString("Message");
                this.f2993c = jSONObject.getInt("ExpiresIn");
                this.f2994d = new ArrayList();
                JSONArray jSONArray = jSONObject.getJSONArray("Files");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    HashMap hashMap = new HashMap();
                    this.f2994d.add(hashMap);
                    Iterator keys = jSONObject2.keys();
                    while (keys.hasNext()) {
                        String str = (String) keys.next();
                        hashMap.put(str, jSONObject2.get(str));
                    }
                }
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
            }
        }
    }

    public static class C0923i implements C0916b, Closeable {
        public final int f2995a;
        public final int f2996b;
        public String f2997c;
        public int f2998d;
        public int f2999e = -1;
        public byte[] f3000f = new byte[(this.f2996b + 1024)];
        public final C0934t<C0923i> f3001g = new C0934t();
        public final C0924j f3002h = new C0924j();
        public final File f3003i;
        public final FileOutputStream f3004j;

        public C0923i(C0940n c0940n, File file) throws FileNotFoundException {
            this.f2995a = c0940n.f3066a;
            this.f2996b = c0940n.f3069d;
            this.f3003i = file;
            this.f3004j = new FileOutputStream(file);
        }

        public String mo1911a() {
            String str = null;
            try {
                DroiUser currentUser = DroiUser.getCurrentUser();
                Object sessionToken = currentUser != null ? currentUser.getSessionToken() : null;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ConfVer", this.f2995a);
                if (sessionToken != null) {
                    jSONObject.put("AccessToken", sessionToken);
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("File", jSONObject2);
                jSONObject2.put("FID", this.f2997c);
                jSONObject2.put("Block", this.f2998d);
                str = jSONObject.toString();
            } catch (Exception e) {
            }
            return str;
        }

        public boolean m2727a(DroiError droiError) throws IOException {
            if (this.f2999e >= 0 && this.f2998d >= this.f2999e) {
                return false;
            }
            int a = C0939m.m2749a(this.f3001g, droiError);
            if (a == 0) {
                return false;
            }
            int a2 = this.f3002h.m2728a(this.f3000f);
            if (a2 == 0) {
                if (droiError == null) {
                    return false;
                }
                droiError.setCode(DroiError.ERROR);
                return false;
            } else if (this.f3002h.f3005a == 0 || this.f3002h.f3006b == null) {
                if (this.f2999e < 0) {
                    this.f2999e = this.f3002h.f3010f / this.f2996b;
                    if (this.f3002h.f3010f % this.f2996b != 0) {
                        this.f2999e++;
                    }
                }
                this.f2998d = this.f3002h.f3008d + 1;
                this.f3004j.write(this.f3000f, a2, a - a2);
                return true;
            } else if (droiError == null) {
                return false;
            } else {
                droiError.setCode(this.f3002h.f3005a);
                droiError.setAppendedMessage(this.f3002h.f3006b);
                return false;
            }
        }

        public void close() throws IOException {
            this.f3004j.close();
        }
    }

    public static class C0924j implements C0915a {
        public int f3005a;
        public String f3006b;
        public int f3007c;
        public int f3008d;
        public String f3009e;
        public int f3010f;
        public ByteBuffer f3011g = ByteBuffer.allocate(8);

        public int m2728a(byte[] bArr) {
            this.f3011g.position(0);
            int i = (int) this.f3011g.order(ByteOrder.LITTLE_ENDIAN).put(bArr, 0, 8).getLong(0);
            try {
                mo1910a(new JSONObject(new String(bArr, 8, i)));
                return i + 8;
            } catch (JSONException e) {
                return 0;
            }
        }

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f3005a = jSONObject.getInt("Code");
                this.f3006b = jSONObject.getString("Message");
                this.f3007c = jSONObject.getInt("ExpiresIn");
                if (jSONObject.has("File")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("File");
                    this.f3009e = jSONObject2.getString("FID");
                    this.f3008d = jSONObject2.getInt("Block");
                    this.f3010f = jSONObject2.getInt("TotalSize");
                }
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
            }
        }
    }

    public static class C0925k implements C0916b {
        public int f3012a;
        public String f3013b;

        public String mo1911a() {
            String str = null;
            try {
                DroiUser currentUser = DroiUser.getCurrentUser();
                Object sessionToken = currentUser != null ? currentUser.getSessionToken() : null;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ConfVer", this.f3012a);
                if (sessionToken != null) {
                    jSONObject.put("AccessToken", sessionToken);
                }
                JSONArray jSONArray = new JSONArray();
                jSONObject.put("Files", jSONArray);
                JSONObject jSONObject2 = new JSONObject();
                jSONArray.put(jSONObject2);
                jSONObject2.put("FID", this.f3013b);
                str = jSONObject.toString();
            } catch (Exception e) {
            }
            return str;
        }
    }

    public static class C0926l implements C0915a {
        public int f3014a;
        public String f3015b;
        public int f3016c;
        public ArrayList<HashMap<String, Object>> f3017d;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f3014a = jSONObject.getInt("Code");
                this.f3015b = jSONObject.getString("Message");
                this.f3016c = jSONObject.getInt("ExpiresIn");
                this.f3017d = new ArrayList();
                JSONArray jSONArray = jSONObject.getJSONArray("Files");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    HashMap hashMap = new HashMap();
                    this.f3017d.add(hashMap);
                    hashMap.put("CDN", jSONObject2.get("CDN"));
                    hashMap.put("Code", jSONObject2.get("Code"));
                    hashMap.put("Message", jSONObject2.get("Message"));
                    hashMap.put("FID", jSONObject2.get("FID"));
                    hashMap.put("Status", jSONObject2.get("Status"));
                    jSONObject2 = jSONObject2.getJSONObject("Metadata");
                    if (jSONObject2 != null) {
                        hashMap.put("Path", jSONObject2.get("Path"));
                        hashMap.put("Type", jSONObject2.get("Type"));
                        hashMap.put("Size", jSONObject2.get("Size"));
                        hashMap.put("ModifyTime", jSONObject2.get("ModifyTime"));
                        hashMap.put("CreatedTime", jSONObject2.get("CreatedTime"));
                        hashMap.put("FileDescription", jSONObject2.get("FileDescription"));
                        hashMap.put("MD5", jSONObject2.get("MD5"));
                    }
                }
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
            }
        }
    }

    private static class C0927m implements C0916b {
        public String f3018a = CorePriv.f2828b;

        private C0927m() {
        }

        public String mo1911a() {
            return String.format("{\"AppID\" : \"%s\"}", new Object[]{this.f3018a});
        }
    }

    private static class C0928n implements C0916b {
        private C0928n() {
        }

        public String mo1911a() {
            return "{}";
        }
    }

    public static class C0929o implements C0915a {
        public int f3019a;
        public String f3020b;
        public int f3021c;
        public int f3022d;
        public int f3023e;
        public int f3024f;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f3019a = jSONObject.getInt("Code");
                this.f3020b = jSONObject.getString("Message");
                JSONObject jSONObject2 = jSONObject.getJSONObject("Conf");
                this.f3021c = jSONObject2.getInt("ConfVer");
                this.f3022d = jSONObject2.getInt("MaxSize");
                this.f3023e = jSONObject2.getInt("ChunkSize");
                this.f3024f = jSONObject2.getInt("DownloadChunkSize");
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
            }
        }
    }

    public static class C0930p implements C0916b {
        private final String f3025a;
        private String f3026b;
        private final String f3027c;

        public C0930p(String str) {
            this.f3025a = "anonymous";
            this.f3027c = str;
            this.f3026b = null;
        }

        public C0930p(String str, String str2, String str3) {
            this.f3025a = "general";
            this.f3027c = str3;
            this.f3026b = String.format("{\"UserId\":\"%s\", \"Password\":\"%s\"}", new Object[]{str, str2});
        }

        public C0930p(String str, String str2, String str3, String str4, String str5) {
            this.f3025a = "oauth";
            this.f3027c = str5;
            JSONObject jSONObject = new JSONObject();
            if (str2 != null) {
                try {
                    jSONObject.put("OpenId", str2);
                } catch (Exception e) {
                    DroiLog.m2873w(C0939m.f3064d, e);
                    return;
                }
            }
            if (str4 != null) {
                jSONObject.put("AccessToken", str4);
            }
            if (str3 != null) {
                jSONObject.put("Code", str3);
            }
            jSONObject.put("AuthProvider", str);
            this.f3026b = jSONObject.toString();
        }

        public String mo1911a() {
            if (this.f3026b != null) {
                return String.format("{\"Type\":\"%s\",\"InstallationId\":\"%s\",\"Value\":%s}", new Object[]{this.f3025a, this.f3027c, this.f3026b});
            }
            return String.format("{\"Type\":\"%s\",\"InstallationId\":\"%s\"}", new Object[]{this.f3025a, this.f3027c});
        }
    }

    public static class C0931q implements C0915a {
        public int f3028a;
        public String f3029b;
        public String f3030c;
        public long f3031d;
        public JSONObject f3032e;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f3028a = jSONObject.getInt("Code");
                if (this.f3028a == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("Result");
                    this.f3030c = jSONObject2.getString(TwitterPreferences.TOKEN);
                    this.f3031d = jSONObject2.getLong("expireAt");
                    this.f3032e = jSONObject2.getJSONObject("data");
                } else if (jSONObject.has("Ticket")) {
                    this.f3029b = jSONObject.getString("Ticket");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public boolean m2737a() {
            return (this.f3028a != 0 || this.f3030c == null || this.f3030c.isEmpty() || this.f3032e == null || this.f3031d <= new Date().getTime()) ? false : true;
        }
    }

    public static class C0932r implements C0916b {
        private String f3033a;
        private String f3034b;

        public C0932r(String str, String str2) {
            this.f3033a = str;
            this.f3034b = str2;
        }

        public String mo1911a() {
            return String.format("{\"UserObjectId\":\"%s\", \"Token\":\"%s\"}", new Object[]{this.f3034b, this.f3033a});
        }
    }

    public static class C0933s implements C0915a {
        private int f3035a = -1;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f3035a = jSONObject.getInt("Code");
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
                this.f3035a = -1;
            }
        }

        public boolean m2740a() {
            return this.f3035a == 0;
        }
    }

    private static class C0934t<T extends C0916b> implements C0916b {
        public C0927m f3036a = new C0927m();
        public T f3037b = this;

        private C0934t() {
        }

        public String mo1911a() {
            return String.format("{\"Head\":%s, \"Body\":%s}", new Object[]{this.f3036a.mo1911a(), this.f3037b.mo1911a()});
        }
    }

    public static class C0935u implements C0916b {
        private final String f3038a;
        private final JSONObject f3039b;
        private final String f3040c;
        private final String f3041d;

        public C0935u(String str, String str2, String str3, JSONObject jSONObject) {
            this.f3038a = str;
            this.f3040c = str2;
            this.f3041d = str3;
            this.f3039b = jSONObject;
        }

        public C0935u(String str, String str2, JSONObject jSONObject) {
            this(str, str2, null, jSONObject);
        }

        public String mo1911a() {
            JSONArray jSONArray = new JSONArray();
            try {
                jSONArray.put(new JSONObject().put("update", C0911l.m2710d(DroiUser.class)));
                jSONArray.put(new JSONObject().put(C0896c.f2868c, this.f3040c));
                jSONArray.put(new JSONObject().put("_Value", this.f3039b));
                jSONArray.put(new JSONObject().put("Type", this.f3038a));
                jSONArray.put(new JSONObject().put("InstallationId", CorePriv.getInstallationId()));
                if (this.f3038a.equals("oauth")) {
                    jSONArray.put(new JSONObject().put("AuthData", new JSONObject(this.f3041d)));
                }
                return jSONArray.toString();
            } catch (JSONException e) {
                return null;
            }
        }
    }

    public static class C0936v implements C0915a {
        public static final int f3042a = -1006;
        public int f3043b;
        public String f3044c;
        public String f3045d;
        public long f3046e;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f3043b = jSONObject.getInt("Code");
                if (this.f3043b == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("Result");
                    this.f3045d = jSONObject2.getString(TwitterPreferences.TOKEN);
                    this.f3046e = jSONObject2.getLong("expireAt");
                    return;
                }
                this.f3044c = jSONObject.getString("Ticket");
            } catch (JSONException e) {
                this.f3043b = -1;
            }
        }

        public boolean m2744a() {
            return this.f3043b == 0;
        }
    }

    public static class C0937w implements C0916b, Closeable {
        public String f3047a;
        public int f3048b;
        public final FileInputStream f3049c;
        public final int f3050d;
        public final C0940n f3051e;
        public final byte[] f3052f;
        public final byte[] f3053g;
        public final byte[] f3054h;
        public int f3055i;
        public final C0934t<C0937w> f3056j;
        public byte[] f3057k;

        public C0937w(C0940n c0940n, File file) throws FileNotFoundException {
            this.f3049c = new FileInputStream(file);
            this.f3054h = null;
            this.f3052f = new byte[8192];
            this.f3053g = new byte[c0940n.f3068c];
            this.f3056j = new C0934t();
            this.f3056j.f3037b = this;
            this.f3056j.f3036a = new C0927m();
            this.f3056j.f3036a.f3018a = CorePriv.f2828b;
            this.f3051e = c0940n;
            long length = file.length();
            int i = (int) (length / ((long) c0940n.f3068c));
            if (length % ((long) c0940n.f3068c) != 0) {
                i++;
            }
            this.f3050d = i;
        }

        public C0937w(C0940n c0940n, byte[] bArr) {
            this.f3049c = null;
            this.f3054h = bArr;
            this.f3055i = 0;
            this.f3052f = new byte[8192];
            this.f3053g = new byte[c0940n.f3068c];
            this.f3056j = new C0934t();
            this.f3056j.f3037b = this;
            this.f3056j.f3036a = new C0927m();
            this.f3056j.f3036a.f3018a = CorePriv.f2828b;
            this.f3051e = c0940n;
            long length = (long) bArr.length;
            int i = (int) (length / ((long) c0940n.f3068c));
            if (length % ((long) c0940n.f3068c) != 0) {
                i++;
            }
            this.f3050d = i;
        }

        public String mo1911a() {
            String str = null;
            try {
                DroiUser currentUser = DroiUser.getCurrentUser();
                Object sessionToken = currentUser != null ? currentUser.getSessionToken() : null;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ConfVer", this.f3051e.f3066a);
                if (sessionToken != null) {
                    jSONObject.put("AccessToken", sessionToken);
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("FID", this.f3047a);
                jSONObject2.put("Block", this.f3048b);
                jSONObject.put("File", jSONObject2);
                str = jSONObject.toString();
            } catch (Exception e) {
            }
            return str;
        }

        public boolean m2746a(DroiError droiError) throws IOException {
            if (this.f3048b >= this.f3050d) {
                return false;
            }
            int i;
            if (droiError == null) {
                droiError = new DroiError();
            }
            int i2;
            if (this.f3049c != null) {
                i2 = 8192;
                i = 0;
                while (true) {
                    i2 = this.f3049c.read(this.f3052f, 0, i2);
                    if (i2 <= 0) {
                        break;
                    }
                    System.arraycopy(this.f3052f, 0, this.f3053g, i, i2);
                    i += i2;
                    if (i >= this.f3051e.f3068c) {
                        break;
                    }
                    i2 = this.f3051e.f3068c - i;
                    if (i2 > 8192) {
                        i2 = 8192;
                    }
                }
            } else {
                i2 = this.f3054h.length - this.f3055i;
                if (i2 >= this.f3051e.f3068c) {
                    i2 = this.f3051e.f3068c;
                }
                i = i2;
            }
            Object bytes = this.f3056j.mo1911a().getBytes();
            Object array = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong((long) bytes.length).array();
            if (this.f3057k == null || (bytes.length + 8) + i >= this.f3057k.length) {
                this.f3057k = new byte[(((bytes.length + 8) + this.f3051e.f3068c) + 32)];
            }
            System.arraycopy(array, 0, this.f3057k, 0, 8);
            System.arraycopy(bytes, 0, this.f3057k, 8, bytes.length);
            if (this.f3049c != null) {
                System.arraycopy(this.f3053g, 0, this.f3057k, bytes.length + 8, i);
            } else {
                System.arraycopy(this.f3054h, this.f3055i, this.f3057k, bytes.length + 8, i);
            }
            this.f3055i += i;
            C0938x a = C0939m.m2765a(this.f3057k, (bytes.length + i) + 8, droiError);
            if (!droiError.isOk()) {
                return false;
            }
            if (a.f3058a != 0) {
                String str = "Upload fail. ";
                if (a != null) {
                    str = str + a.f3059b;
                }
                if (droiError == null) {
                    return false;
                }
                droiError.setCode(a.f3058a);
                droiError.setAppendedMessage(str);
                return false;
            }
            this.f3048b++;
            return true;
        }

        public int m2747b() {
            return this.f3055i;
        }

        public void close() throws IOException {
            this.f3049c.close();
        }
    }

    public static class C0938x implements C0915a {
        public int f3058a;
        public String f3059b;
        public int f3060c;

        public void mo1910a(JSONObject jSONObject) {
            try {
                this.f3058a = jSONObject.getInt("Code");
                this.f3059b = jSONObject.getString("Message");
                this.f3060c = jSONObject.getInt("ExpiresIn");
            } catch (Exception e) {
                DroiLog.m2869e(C0939m.f3064d, e);
            }
        }
    }

    public static int m2749a(C0934t<C0923i> c0934t, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            return C0939m.m2750a(C0899e.f2918w, (C0934t) c0934t, ((C0923i) c0934t.f3037b).f3000f, droiError);
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return 0;
    }

    private static int m2750a(String str, C0934t<?> c0934t, byte[] bArr, DroiError droiError) {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName);
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final AtomicReference atomicReference = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception."));
        final C0934t<?> c0934t2 = c0934t;
        final String str2 = str;
        final byte[] bArr2 = bArr;
        DroiRunnable c09143 = new DroiRunnable() {
            public void run() {
                DroiHttpRequest instance = DroiHttpRequest.instance();
                String a = c0934t2.mo1911a();
                Request make = Request.make(str2, a == null ? null : a.getBytes());
                C0939m.m2768a(make);
                Response request = instance.request(make);
                if (request == null) {
                    DroiLog.m2870e(C0939m.f3064d, "No response");
                    atomicReference.set(new DroiError(DroiError.ERROR, "no response"));
                    return;
                }
                int statusCode = request.getStatusCode();
                int errorCode = request.getErrorCode();
                int droiStatusCode = request.getDroiStatusCode();
                DroiError droiError = new DroiError();
                C0939m.m2774b(statusCode, errorCode, droiStatusCode, request.getDrid(), droiError);
                Object data = request.getData();
                if (data != null && data.length != 0) {
                    System.arraycopy(data, 0, bArr2, 0, data.length);
                    atomicReference.set(droiError);
                    atomicInteger.set(data.length);
                }
            }
        };
        if (TaskDispatcher.currentTaskDispatcher() != dispatcher) {
            c09143.run();
        } else {
            DroiTask.create(c09143).runAndWait("TaskDispatcher_DroiBackgroundThread");
        }
        if (droiError != null) {
            droiError.copy((DroiError) atomicReference.get());
        }
        return atomicInteger.get();
    }

    public static DroiError m2751a() {
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (currentUser == null) {
            return new DroiError(DroiError.USER_NOT_AUTHORIZED, "AutoAnonymous is not enabled.");
        }
        if (!currentUser.isAuthorized()) {
            if (!currentUser.isAnonymous()) {
                return new DroiError(DroiError.USER_NOT_AUTHORIZED, null);
            }
            if (!DroiUser.isAutoAnonymousUserEnabled()) {
                return new DroiError(DroiError.USER_NOT_AUTHORIZED, "AutoAnonymous is not enabled.");
            }
            DroiError droiError = new DroiError();
            DroiUser.loginWithAnonymous(droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
        }
        return new DroiError();
    }

    private static <T extends C0915a> T m2752a(JSONObject jSONObject, Class<T> cls) {
        try {
            C0915a c0915a = (C0915a) cls.newInstance();
            c0915a.mo1910a(jSONObject);
            return c0915a;
        } catch (Exception e) {
            DroiLog.m2873w(f3064d, e);
            return null;
        }
    }

    public static C0917c m2753a(int i, String str, String str2, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (!a.isOk()) {
            if (droiError != null) {
                droiError.copy(a);
            }
            return null;
        } else if (str == null || str2 == null) {
            return null;
        } else {
            if (droiError == null) {
                droiError = new DroiError();
            }
            JSONObject jSONObject = new JSONObject();
            String b;
            try {
                jSONObject.put("Type", i);
                jSONObject.put("Target", str);
                jSONObject.put("Token", str2);
                b = C0939m.m2772b(C0899e.f2909n, jSONObject.toString(), droiError);
                if (droiError.isOk()) {
                    return (C0917c) C0939m.m2752a(new JSONObject(b), C0917c.class);
                }
                DroiLog.m2870e(f3064d, "getGroup fail. " + droiError.toString());
                return null;
            } catch (Exception e) {
                b = "Generator json fail. " + e;
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(b);
                DroiLog.m2870e(f3064d, b);
                return null;
            }
        }
    }

    public static C0918d m2754a(String str, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            String b = C0939m.m2772b(C0899e.f2903h, String.format("{\"Token\": \"%s\"}", new Object[]{str}), droiError);
            if (!droiError.isOk()) {
                return null;
            }
            try {
                return (C0918d) C0939m.m2752a(new JSONObject(b), C0918d.class);
            } catch (JSONException e) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                }
                return null;
            }
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return null;
    }

    public static C0918d m2755a(String str, String str2, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            String b = C0939m.m2772b(C0899e.f2902g, String.format("{\"Token\": \"%s\", \"PinCode\":\"%s\"}", new Object[]{str, str2}), droiError);
            if (!droiError.isOk()) {
                return null;
            }
            try {
                return (C0918d) C0939m.m2752a(new JSONObject(b), C0918d.class);
            } catch (JSONException e) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                }
                return null;
            }
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return null;
    }

    public static C0918d m2756a(String str, String str2, String str3, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (!a.isOk()) {
            if (droiError != null) {
                droiError.copy(a);
            }
            return null;
        } else if (str2 == null || str3 == null) {
            return null;
        } else {
            if (str == null) {
                str = "";
            }
            String f = C0944p.m2804f(str);
            String f2 = C0944p.m2804f(str2);
            if (droiError == null) {
                droiError = new DroiError();
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("Old", f);
                jSONObject.put("New", f2);
                jSONObject.put("Token", str3);
                f = C0939m.m2772b(C0899e.f2905j, jSONObject.toString(), droiError);
                if (droiError.isOk()) {
                    return (C0918d) C0939m.m2752a(new JSONObject(f), C0918d.class);
                }
                DroiLog.m2870e(f3064d, "Change password fail. " + droiError.toString());
                return null;
            } catch (JSONException e) {
                f = "Generator json fail. " + e;
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(f);
                DroiLog.m2870e(f3064d, f);
                return null;
            }
        }
    }

    public static C0918d m2757a(JSONObject jSONObject, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (!a.isOk()) {
            if (droiError != null) {
                droiError.copy(a);
            }
            return null;
        } else if (jSONObject == null) {
            if (droiError != null) {
                droiError.setCode(DroiError.INVALID_PARAMETER);
                droiError.setAppendedMessage(null);
            }
            return null;
        } else {
            try {
                jSONObject.put("Action", 1);
            } catch (Exception e) {
                DroiLog.m2869e(f3064d, e);
            }
            String b = C0939m.m2772b(C0899e.f2904i, jSONObject.toString(), droiError);
            if (!droiError.isOk()) {
                return null;
            }
            try {
                return (C0918d) C0939m.m2752a(new JSONObject(b), C0918d.class);
            } catch (JSONException e2) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e2.toString());
                }
                return null;
            }
        }
    }

    public static C0920f m2758a(C0919e c0919e, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            C0934t c0934t = new C0934t();
            c0934t.f3036a = new C0927m();
            c0934t.f3036a.f3018a = CorePriv.f2828b;
            c0934t.f3037b = c0919e;
            String b = C0939m.m2772b(C0899e.f2913r, c0934t.mo1911a(), droiError);
            if (b == null) {
                return null;
            }
            try {
                return (C0920f) C0939m.m2752a(new JSONObject(b), C0920f.class);
            } catch (Exception e) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                }
                return null;
            }
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return null;
    }

    public static C0922h m2759a(C0921g c0921g, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            C0934t c0934t = new C0934t();
            c0934t.f3036a = new C0927m();
            c0934t.f3036a.f3018a = CorePriv.f2828b;
            c0934t.f3037b = c0921g;
            String b = C0939m.m2772b(C0899e.f2915t, c0934t.mo1911a(), droiError);
            if (b == null) {
                return null;
            }
            try {
                return (C0922h) C0939m.m2752a(new JSONObject(b), C0922h.class);
            } catch (Exception e) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                }
                return null;
            }
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return null;
    }

    public static C0926l m2760a(C0925k c0925k, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            C0934t c0934t = new C0934t();
            c0934t.f3036a = new C0927m();
            c0934t.f3036a.f3018a = CorePriv.f2828b;
            c0934t.f3037b = c0925k;
            String b = C0939m.m2772b(C0899e.f2917v, c0934t.mo1911a(), droiError);
            if (b == null) {
                return null;
            }
            try {
                return (C0926l) C0939m.m2752a(new JSONObject(b), C0926l.class);
            } catch (JSONException e) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                }
                return null;
            }
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return null;
    }

    public static C0929o m2761a(DroiError droiError) {
        C0934t c0934t = new C0934t();
        c0934t.f3036a = new C0927m();
        c0934t.f3036a.f3018a = CorePriv.f2828b;
        c0934t.f3037b = new C0928n();
        String b = C0939m.m2772b(C0899e.f2912q, c0934t.mo1911a(), droiError);
        if (b == null) {
            return null;
        }
        try {
            return (C0929o) C0939m.m2752a(new JSONObject(b), C0929o.class);
        } catch (JSONException e) {
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
            }
            return null;
        }
    }

    public static C0931q m2762a(C0930p c0930p, DroiError droiError) {
        String b = C0939m.m2772b(C0899e.f2899d, c0930p.mo1911a(), droiError);
        if (b == null) {
            return null;
        }
        try {
            return (C0931q) C0939m.m2752a(new JSONObject(b), C0931q.class);
        } catch (JSONException e) {
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
            }
            return null;
        }
    }

    public static C0933s m2763a(C0932r c0932r, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            String b = C0939m.m2772b(C0899e.f2900e, c0932r.mo1911a(), droiError);
            if (b == null) {
                return null;
            }
            try {
                return (C0933s) C0939m.m2752a(new JSONObject(b), C0933s.class);
            } catch (JSONException e) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                }
                return null;
            }
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return null;
    }

    public static C0936v m2764a(C0935u c0935u, DroiError droiError) {
        String b = C0939m.m2772b(C0899e.f2898c, c0935u.mo1911a(), droiError);
        if (b == null) {
            return null;
        }
        try {
            return (C0936v) C0939m.m2752a(new JSONObject(b), C0936v.class);
        } catch (JSONException e) {
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
            }
            return null;
        }
    }

    public static C0938x m2765a(byte[] bArr, int i, DroiError droiError) {
        if (droiError == null) {
            droiError = new DroiError();
        }
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            DroiHttpRequest instance = DroiHttpRequest.instance();
            if (!(bArr == null || bArr.length == i)) {
                bArr = Arrays.copyOf(bArr, i);
            }
            Request make = Request.make(C0899e.f2914s, bArr);
            C0939m.m2768a(make);
            Response request = instance.request(make);
            if (request == null) {
                DroiLog.m2870e(f3064d, "No response");
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("No response");
                return null;
            }
            C0939m.m2774b(request.getStatusCode(), request.getErrorCode(), request.getDroiStatusCode(), request.getDrid(), droiError);
            if (!droiError.isOk()) {
                return null;
            }
            byte[] data = request.getData();
            if (data == null || data.length == 0) {
                return null;
            }
            try {
                return (C0938x) C0939m.m2752a(new JSONObject(new String(data)), C0938x.class);
            } catch (Exception e) {
                DroiLog.m2869e(f3064d, e);
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
                return null;
            }
        }
        droiError.copy(a);
        return null;
    }

    public static String m2766a(String str, String str2, String str3, Map<String, String> map, DroiError droiError) {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName);
        final AtomicReference atomicReference = new AtomicReference(null);
        final AtomicReference atomicReference2 = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by exception."));
        final String str4 = str;
        final String str5 = str3;
        final Map<String, String> map2 = map;
        DroiRunnable c09121 = new DroiRunnable() {
            public void run() {
                Object obj = null;
                DroiHttpRequest instance = DroiHttpRequest.instance();
                Request make = Request.make(str4, str5 == null ? null : str5.getBytes());
                C0939m.m2768a(make);
                if (map2 != null) {
                    for (String str : map2.keySet()) {
                        make.addHeader(str, (String) map2.get(str));
                    }
                }
                Response request = instance.request(make);
                if (request == null) {
                    DroiLog.m2870e(C0939m.f3064d, "No response");
                    atomicReference2.set(new DroiError(DroiError.ERROR, "no response"));
                    return;
                }
                int statusCode = request.getStatusCode();
                int errorCode = request.getErrorCode();
                int droiStatusCode = request.getDroiStatusCode();
                DroiError droiError = new DroiError();
                C0939m.m2774b(statusCode, errorCode, droiStatusCode, request.getDrid(), droiError);
                if (request.getData() != null) {
                    obj = new String(request.getData());
                }
                if (CorePriv.getDeviceId() == null && !C0944p.m2802e(CorePriv.getContext())) {
                    PersistSettings instance2 = PersistSettings.instance(PersistSettings.CONFIG);
                    long GetKlKeyUID_u = Tutil.GetKlKeyUID_u();
                    long GetKlKeyUID_l = Tutil.GetKlKeyUID_l();
                    if (!(GetKlKeyUID_u == 0 || GetKlKeyUID_l == 0)) {
                        instance2.setLong(PersistSettings.KEY_UID_U, GetKlKeyUID_u);
                        instance2.setLong(PersistSettings.KEY_UID_L, GetKlKeyUID_l);
                    }
                }
                atomicReference2.set(droiError);
                atomicReference.set(obj);
            }
        };
        if (TaskDispatcher.currentTaskDispatcher() != dispatcher) {
            c09121.run();
        } else {
            DroiTask.create(c09121).runAndWait("TaskDispatcher_DroiBackgroundThread");
        }
        if (droiError != null) {
            droiError.copy((DroiError) atomicReference2.get());
        }
        return (String) atomicReference.get();
    }

    public static void m2768a(Request request) {
        if (CorePriv.f2828b != null) {
            request.addHeader("X-Droi-AppID", CorePriv.f2828b);
        }
        if (CorePriv.f2829c != null) {
            request.addHeader(f3062b, CorePriv.f2829c);
        }
        if (CorePriv.getDeviceId() != null) {
            request.addHeader(f3063c, CorePriv.getDeviceId());
        }
        DroiLog.m2868d(f3064d, "AID: " + CorePriv.f2828b);
        DroiLog.m2868d(f3064d, "PID: " + CorePriv.f2829c);
        DroiLog.m2868d(f3064d, "DID: " + CorePriv.getDeviceId());
    }

    public static byte[] m2769a(final Request request, DroiError droiError) {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName);
        final AtomicReference atomicReference = new AtomicReference(null);
        final AtomicReference atomicReference2 = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception."));
        DroiRunnable c09132 = new DroiRunnable() {
            public void run() {
                DroiHttpRequest instance = DroiHttpRequest.instance();
                C0939m.m2768a(request);
                Response request = instance.request(request);
                if (request == null) {
                    DroiLog.m2870e(C0939m.f3064d, "No response");
                    atomicReference2.set(new DroiError(DroiError.ERROR, "no response"));
                    return;
                }
                int statusCode = request.getStatusCode();
                int errorCode = request.getErrorCode();
                int droiStatusCode = request.getDroiStatusCode();
                DroiError droiError = new DroiError();
                C0939m.m2774b(statusCode, errorCode, droiStatusCode, request.getDrid(), droiError);
                atomicReference2.set(droiError);
                atomicReference.set(request.getData());
            }
        };
        if (TaskDispatcher.currentTaskDispatcher() != dispatcher) {
            c09132.run();
        } else {
            DroiTask.create(c09132).runAndWait("TaskDispatcher_DroiBackgroundThread");
        }
        if (droiError != null) {
            droiError.copy((DroiError) atomicReference2.get());
        }
        return (byte[]) atomicReference.get();
    }

    public static C0918d m2770b(String str, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (a.isOk()) {
            String b = C0939m.m2772b(C0899e.f2901f, String.format("{\"Token\": \"%s\"}", new Object[]{str}), droiError);
            if (!droiError.isOk()) {
                return null;
            }
            try {
                return (C0918d) C0939m.m2752a(new JSONObject(b), C0918d.class);
            } catch (JSONException e) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                }
                return null;
            }
        }
        if (droiError != null) {
            droiError.copy(a);
        }
        return null;
    }

    public static C0918d m2771b(JSONObject jSONObject, DroiError droiError) {
        DroiError a = C0939m.m2751a();
        if (!a.isOk()) {
            if (droiError != null) {
                droiError.copy(a);
            }
            return null;
        } else if (jSONObject == null) {
            if (droiError != null) {
                droiError.setCode(DroiError.INVALID_PARAMETER);
                droiError.setAppendedMessage(null);
            }
            return null;
        } else {
            try {
                jSONObject.put("Action", 0);
            } catch (Exception e) {
                DroiLog.m2869e(f3064d, e);
            }
            String b = C0939m.m2772b(C0899e.f2904i, jSONObject.toString(), droiError);
            if (!droiError.isOk()) {
                return null;
            }
            try {
                return (C0918d) C0939m.m2752a(new JSONObject(b), C0918d.class);
            } catch (JSONException e2) {
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e2.toString());
                }
                return null;
            }
        }
    }

    public static String m2772b(String str, String str2, DroiError droiError) {
        return C0939m.m2766a(str, Constants.HTTP_POST, str2, null, droiError);
    }

    public static String m2773b(String str, String str2, String str3, DroiError droiError) {
        return C0939m.m2766a(str, str2, str3, null, droiError);
    }

    private static void m2774b(int i, int i2, int i3, String str, DroiError droiError) {
        DroiLog.m2868d(f3064d, "HttpStatus: " + i + ", DroiStatus: " + i3 + ", Drid: " + str + ", ErrorCode: " + i2);
        if (droiError == null) {
            DroiLog.m2870e(f3064d, "DroiError obj not found.");
        } else if (i == 200 && i2 == 0 && i3 >= 0) {
            droiError.setCode(0);
            droiError.setAppendedMessage(null);
        } else {
            droiError.setTicket(str);
            if (i == 0 || i == 200) {
                if (i2 == com.tyd.aidlservice.internal.Constants.NO_NETWORK) {
                    droiError.setCode(DroiError.NETWORK_NOT_AVAILABLE);
                } else if (i2 == com.tyd.aidlservice.internal.Constants.IP_LIST_ERROR || i2 == com.tyd.aidlservice.internal.Constants.NETWORK_ERROR) {
                    droiError.setCode(DroiError.SERVER_NOT_REACHABLE);
                } else if (i2 == com.tyd.aidlservice.internal.Constants.APP_ID_ERROR || i2 == com.tyd.aidlservice.internal.Constants.APP_ID_INVALID) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("Application id is not valid");
                } else if (i3 != 0) {
                    droiError.setCode(DroiError.INTERNAL_SERVER_ERROR);
                } else {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(String.format("HttpStatus: %d, errorCode: %d, status: %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)}));
                }
            } else if (i == 404) {
                droiError.setCode(DroiError.SERVICE_NOT_FOUND);
            } else if (i == HttpResponseCode.FORBIDDEN || i == 405) {
                droiError.setCode(DroiError.SERVICE_NOT_ALLOWED);
            } else if (i == 509) {
                droiError.setCode(DroiError.BANDWIDTH_LIMIT_EXCEED);
            } else {
                droiError.setCode(DroiError.HTTP_SERVER_ERROR);
                droiError.setAppendedMessage("status: " + i);
            }
        }
    }
}
