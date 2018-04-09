package com.droi.sdk.core.priv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.droi.sdk.internal.DroiLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class CacheProxy {
    static final String TAG = "CacheProxy";
    static final int _UnlinkTimeDay = 3;
    static final int _UnlinkTimeHour = 0;
    static final String _address = "127.0.0.1";
    static final Boolean _cacheIsShared = Boolean.valueOf(true);
    static final int _chunkHighMark = ((_chunkSize * 8) * 1024);
    static final int _chunkLowMark = ((_chunkSize * 4) * 1024);
    static final int _chunkSize = getChunkSizeN();
    static final String _diskCacheRoot = "/mnt/sdcard/proxy/";
    static final int _objectHashTableSize = 32768;
    static final int _objectHighMark = 2048;
    static final int _objectLowMark = 1024;
    static final int _port = 0;
    private static config cfg = new config();
    private static Object csLock = new Object();
    private static Boolean isProxyRun = Boolean.valueOf(false);
    static final boolean isdebug = false;
    private static Thread proxy_thread;

    static class C08891 implements Runnable {
        C08891() {
        }

        public void run() {
            CacheProxy.setConfigN(CacheProxy.cfg);
            CacheProxy.startN();
        }
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(config.getAddress(), config.getPort()));
            try {
                URLConnection conn;
                if (CacheProxy.isProxyRun()) {
                    conn = new URL(urldisplay).openConnection(proxy);
                } else {
                    conn = new URL(urldisplay).openConnection();
                }
                return BitmapFactory.decodeStream(conn.getInputStream());
            } catch (Exception e) {
                DroiLog.m2870e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            this.bmImage.setImageBitmap(result);
        }
    }

    static class Task implements Callable<Integer> {
        private int code = 0;
        private String etag;
        private String last_modified;
        private String url;

        public Task(String u, String e, String l) {
            this.url = u;
            this.etag = e;
            this.last_modified = l;
        }

        public Integer call() throws Exception {
            HttpURLConnection conn = (HttpURLConnection) new URL(this.url).openConnection();
            try {
                conn.setRequestProperty("If-Modified-Since", this.last_modified);
                conn.connect();
                this.code = conn.getResponseCode();
                return Integer.valueOf(this.code);
            } finally {
                conn.disconnect();
            }
        }
    }

    public static class cache_state {
        private String etag = "";
        private String last_modified = "";
        private int ret = 0;

        public cache_state(int r) {
            this.ret = r;
        }

        public void set_state(String b, String c) {
            this.etag = b;
            this.last_modified = c;
        }
    }

    public static class config {
        int UnlinkTimeDay;
        int UnlinkTimeHour;
        String address;
        int chunkHighMark;
        int chunkLowMark;
        String diskCacheRoot;
        int objectHashTableSize;
        int objectHighMark;
        int objectLowMark;
        int port;

        public static void initConfig() {
            CacheProxy.cfg.address = CacheProxy._address;
            CacheProxy.cfg.port = 0;
            CacheProxy.cfg.chunkHighMark = CacheProxy._chunkHighMark;
            CacheProxy.cfg.chunkLowMark = CacheProxy._chunkLowMark;
            CacheProxy.cfg.objectHighMark = 2048;
            CacheProxy.cfg.objectLowMark = 1024;
            CacheProxy.cfg.objectHashTableSize = 32768;
            CacheProxy.cfg.diskCacheRoot = CacheProxy._diskCacheRoot;
            CacheProxy.cfg.UnlinkTimeDay = 3;
            CacheProxy.cfg.UnlinkTimeHour = 0;
            CacheProxy.setConfigN(CacheProxy.cfg);
        }

        public static String getAddress() {
            return CacheProxy.cfg.address;
        }

        public static int getPort() {
            return CacheProxy.cfg.port;
        }

        public static int getchunkhigh() {
            return CacheProxy.cfg.chunkHighMark;
        }

        public static int getchunklow() {
            return CacheProxy.cfg.chunkLowMark;
        }

        public static int getobjecthigh() {
            return CacheProxy.cfg.objectHighMark;
        }

        public static int getobjectlow() {
            return CacheProxy.cfg.objectLowMark;
        }

        public static int getobjecttable() {
            return CacheProxy.cfg.objectHashTableSize;
        }

        public static String getdiskcacheroot() {
            return CacheProxy.cfg.diskCacheRoot;
        }

        public static int getunlink_d() {
            return CacheProxy.cfg.UnlinkTimeDay;
        }

        public static int getunlink_h() {
            return CacheProxy.cfg.UnlinkTimeHour;
        }

        public static boolean setAddress(String addr) {
            if (addr.isEmpty()) {
                CacheProxy.cfg.address = CacheProxy._address;
                CacheProxy.setAddrN(CacheProxy._address);
                return false;
            }
            CacheProxy.cfg.address = addr;
            CacheProxy.setAddrN(addr);
            return true;
        }

        public static boolean setChunkSize(int high, int low) {
            int chunk = CacheProxy.getChunkSizeN();
            if (low > high || high <= (chunk * 2) * 1024 || high > (chunk * 16) * 1024) {
                CacheProxy.cfg.chunkHighMark = (chunk * 8) * 1024;
                CacheProxy.cfg.chunkLowMark = (chunk * 4) * 1024;
                return false;
            }
            CacheProxy.cfg.chunkHighMark = high;
            CacheProxy.cfg.chunkLowMark = low;
            return true;
        }

        public static boolean setObjectSize(int high, int low, int table) {
            if (low > high || high < 16 || high > 16384 || table < high * 8 || table > high * 32) {
                CacheProxy.cfg.objectHighMark = 2048;
                CacheProxy.cfg.objectHighMark = 1024;
                CacheProxy.cfg.objectHashTableSize = 32768;
                return false;
            }
            CacheProxy.cfg.objectHighMark = low;
            CacheProxy.cfg.objectHighMark = high;
            CacheProxy.cfg.objectHashTableSize = table;
            return true;
        }

        public static boolean setdiskCacheRoot(String path) {
            CacheProxy.cfg.diskCacheRoot = path;
            return true;
        }

        public static boolean setdiskCacheUnlinkTime(int day, int hour) {
            CacheProxy.cfg.UnlinkTimeHour = day;
            CacheProxy.cfg.UnlinkTimeHour = hour;
            return true;
        }
    }

    private static native void cleanCacheN();

    private static native int getBindPortN();

    private static native int getChunkSizeN();

    private static native cache_state getEtagAndLastmodified(String str);

    private static native void setAddrN(String str);

    private static native void setConfigN(config com_droi_sdk_core_priv_CacheProxy_config);

    private static native void setProxyPortN(int i);

    private static native void startN();

    private static native void stopN();

    private static void wait(int us) {
        try {
            Thread.sleep((long) us);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isProxyRun() {
        return isProxyRun.booleanValue();
    }

    public static boolean startProxy() {
        if (isProxyRun.booleanValue()) {
            return false;
        }
        proxy_thread = new Thread(new C08891());
        isProxyRun = Boolean.valueOf(true);
        proxy_thread.start();
        wait(1000);
        cfg.port = getBindPortN();
        return true;
    }

    public static boolean stopProxy() {
        if (!isProxyRun.booleanValue()) {
            return false;
        }
        stopN();
        isProxyRun = Boolean.valueOf(false);
        Thread thread = proxy_thread;
        Thread.interrupted();
        proxy_thread = null;
        return true;
    }

    public static void restartProxy() {
        if (isProxyRun.booleanValue()) {
            stopProxy();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startProxy();
        }
    }

    public static void cleanCache() {
        if (isProxyRun.booleanValue()) {
            cleanCacheN();
        }
    }

    public static boolean initBitmapCache(String folder) {
        config.initConfig();
        config.setdiskCacheRoot(folder);
        return startProxy();
    }

    public static byte[] getBitmapJNI(String url) {
        synchronized (csLock) {
            Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(config.getAddress(), config.getPort()));
        }
        byte[] buffer = null;
        try {
            URLConnection conn;
            if (isProxyRun()) {
                conn = new URL(url).openConnection(proxy);
            } else {
                conn = new URL(url).openConnection();
            }
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            copy(conn.getInputStream(), byteStream);
            buffer = byteStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            DroiLog.m2870e("Error", e.toString());
        }
        return buffer;
    }

    public static long copy(InputStream input, OutputStream output) {
        byte[] buffer = new byte[8192];
        long count = 0;
        while (true) {
            try {
                int n = input.read(buffer);
                if (n <= 0) {
                    break;
                }
                output.write(buffer, 0, n);
                count += (long) n;
            } catch (IOException e) {
            }
        }
        return count;
    }

    public static boolean isBitmapNewOrModified(String url) {
        synchronized (csLock) {
            cache_state cc = getEtagAndLastmodified(url);
        }
        if (cc.ret > 0) {
            return false;
        }
        if (cc.ret < 0) {
            return true;
        }
        if (cc.etag == "" || cc.last_modified == "") {
            return true;
        }
        int code = 0;
        try {
            code = new Task(url, cc.etag, cc.last_modified).call().intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (code != 304) {
            return true;
        }
        return false;
    }
}
