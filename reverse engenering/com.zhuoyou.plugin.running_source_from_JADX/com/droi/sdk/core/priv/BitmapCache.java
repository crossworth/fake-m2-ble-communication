package com.droi.sdk.core.priv;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.LruCache;
import com.droi.sdk.core.SocketLock;
import com.droi.sdk.core.TaskDispatcher;
import com.droi.sdk.internal.DroiLog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class BitmapCache {
    private static final String CacheWriterThread = "Droi_Cache_Writer";
    public static final String LOG_TAG = "BitmapCache";
    private static boolean cacheClearing = false;
    private final long cachedThreshold = FileDescriptorHelper.f2835a;
    private final long halfCachedThreshold = 5242880;
    private LruCache<String, byte[]> mCache;
    private String mCachePath;
    private long mCachedFileSize = 0;
    private ConcurrentHashMap<String, CachedFile> mCachedFileTable = new ConcurrentHashMap();
    private String mCachedFileTableName;
    private ConcurrentHashMap<String, ArrayList<String>> mmRelations = new ConcurrentHashMap();

    class C08885 implements Runnable {
        C08885() {
        }

        public void run() {
            synchronized (this) {
                for (CachedFile cf : BitmapCache.this.mCachedFileTable.values()) {
                    if (BitmapCache.this.mCachedFileSize < 5242880) {
                        DroiLog.m2868d(BitmapCache.LOG_TAG, "[clearCacheOnDisk] -- It's decreased to " + BitmapCache.this.mCachedFileSize);
                        break;
                    }
                    if (cf.fileSize > 0) {
                        cf.decreaseHit();
                    }
                    DroiLog.m2868d(BitmapCache.LOG_TAG, "[clearCacheOnDisk] file: " + cf.hashCode + " hit:" + cf.hit + " size: " + cf.fileSize);
                    if (cf.hit.intValue() <= 0 && cf.fileSize > 0) {
                        BitmapCache.this.mmRelations.remove(Integer.valueOf(cf.hashCode.indexOf("-")));
                        BitmapCache.this.mCachedFileTable.remove(cf.hashCode);
                        if (new File(BitmapCache.this.mCachePath + "/" + cf.hashCode).delete()) {
                            DroiLog.m2868d(BitmapCache.LOG_TAG, "[clearCacheOnDisk] deleted " + BitmapCache.this.mCachePath + "/" + cf.hashCode);
                            BitmapCache.access$022(BitmapCache.this, cf.fileSize);
                        } else {
                            DroiLog.m2868d(BitmapCache.LOG_TAG, "[clearCacheOnDisk] try to delete " + BitmapCache.this.mCachePath + "/" + cf.hashCode + " but fail");
                        }
                    }
                }
                BitmapCache.cacheClearing = false;
                DroiLog.m2868d(BitmapCache.LOG_TAG, "[clearCacheOnDisk] -- now: " + BitmapCache.this.mCachedFileSize);
            }
        }
    }

    protected static class CachedFile implements Serializable {
        protected long fileSize;
        protected String hashCode;
        protected Integer hit = Integer.valueOf(2);
        private final Integer magicHit = Integer.valueOf(5);

        public CachedFile(String hashcode, long size) {
            this.hashCode = hashcode;
            this.fileSize = size;
        }

        public Integer increaseHit() {
            if (this.hit.intValue() < this.magicHit.intValue()) {
                this.hit = Integer.valueOf(this.hit.intValue() + 2);
            }
            return this.hit;
        }

        public Integer decreaseHit() {
            Integer num = this.hit;
            this.hit = Integer.valueOf(this.hit.intValue() - 1);
            return num;
        }

        public Integer getHit() {
            return this.hit;
        }
    }

    static /* synthetic */ long access$014(BitmapCache x0, long x1) {
        long j = x0.mCachedFileSize + x1;
        x0.mCachedFileSize = j;
        return j;
    }

    static /* synthetic */ long access$022(BitmapCache x0, long x1) {
        long j = x0.mCachedFileSize - x1;
        x0.mCachedFileSize = j;
        return j;
    }

    public BitmapCache() {
        int cacheSize = ((int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 12;
        this.mCache = new LruCache<String, byte[]>(cacheSize) {
            protected int sizeOf(String key, byte[] bitmap) {
                return (bitmap.length + 1023) / 1024;
            }
        };
        DroiLog.m2871i(LOG_TAG, "Init bitmap memory cache. size is " + Integer.toString(cacheSize));
        this.mCachePath = DroiStorageFinder.getSharedPath() + PersistSettings.IMG_CACHE_FOLDER;
        this.mCachedFileTableName = Integer.toHexString(this.mCachePath.toUpperCase(Locale.getDefault()).hashCode());
        CacheProxy.initBitmapCache(this.mCachePath);
        File cacheFolder = new File(this.mCachePath);
        if (cacheFolder.mkdirs() || cacheFolder.isDirectory()) {
            try {
                restoreCachedFileTable();
            } catch (Exception e) {
            }
            restoreCachedFile(cacheFolder);
            DroiLog.m2868d(LOG_TAG, "Init cached file size " + this.mCachedFileSize);
        }
    }

    protected void finalize() {
        storeCachedTable();
    }

    public boolean isBitmapCached(String url) {
        boolean newOrModified;
        try {
            url = new URL(url).toURI().normalize().toString();
        } catch (URISyntaxException e) {
        } catch (MalformedURLException e2) {
        }
        SocketLock sl = new SocketLock(localSocketNameByURI(url, ""));
        try {
            sl.lock();
            newOrModified = CacheProxy.isBitmapNewOrModified(url);
            sl.release();
        } catch (Exception e3) {
            DroiLog.m2870e(LOG_TAG, "There is an exception in isBitmapNewOrModified.");
            e3.printStackTrace();
            newOrModified = true;
            sl.release();
        }
        if (newOrModified) {
            return false;
        }
        return true;
    }

    private static String localSocketNameByURI(String uri, String addition) {
        return "DroiImageCache- " + uri.hashCode() + " - " + addition;
    }

    public byte[] getBitmap(String url, int preferredWidth, int preferredHeight) {
        boolean newOrModified;
        try {
            url = new URL(url).toURI().normalize().toString();
        } catch (URISyntaxException e) {
        } catch (MalformedURLException e2) {
        }
        String mainHashCode = Integer.toHexString(url.toUpperCase(Locale.getDefault()).hashCode());
        boolean rescaled = true;
        if (preferredWidth == 0 || preferredHeight == 0) {
            preferredWidth = 0;
            preferredHeight = 0;
            rescaled = false;
        }
        String sizeHashCode = String.format(Locale.getDefault(), "%d-%d", new Object[]{Integer.valueOf(preferredWidth), Integer.valueOf(preferredHeight)});
        sizeHashCode = String.format(Locale.getDefault(), "%s-%s", new Object[]{mainHashCode, Integer.toHexString(sizeHashCode.hashCode())});
        SocketLock sl = new SocketLock(localSocketNameByURI(url, sizeHashCode));
        sl.lock();
        boolean fromCache = false;
        try {
            newOrModified = CacheProxy.isBitmapNewOrModified(url);
        } catch (Exception e3) {
            DroiLog.m2870e(LOG_TAG, "There is an exception in isBitmapNewOrModified.");
            newOrModified = true;
        }
        byte[] res = getFromCache(newOrModified, mainHashCode, sizeHashCode);
        if (res == null) {
            SocketLock fileLocker = new SocketLock(localSocketNameByURI(url, ""));
            fileLocker.lock();
            try {
                res = CacheProxy.getBitmapJNI(url);
            } catch (Exception e4) {
                DroiLog.m2870e(LOG_TAG, "There is an exception.");
                res = null;
            }
            fileLocker.release();
            if (res != null) {
                Bitmap resBitmap = BitmapFactory.decodeByteArray(res, 0, res.length);
                if (resBitmap != null) {
                    Object tmp = processNewData(res, preferredWidth, preferredHeight, sizeHashCode, resBitmap, new ByteArrayOutputStream());
                    if (!tmp.equals(res)) {
                        rescaled = true;
                    }
                    res = tmp;
                } else {
                    DroiLog.m2870e(LOG_TAG, "BitmapFactory.decodeByteArray failed.");
                    res = null;
                }
            }
        } else {
            fromCache = this.mCache.get(sizeHashCode) != null;
        }
        CachedFile c = (CachedFile) this.mCachedFileTable.get(sizeHashCode);
        if (c != null) {
            c.increaseHit();
        }
        if (!(res == null || fromCache || !rescaled)) {
            addRelation(mainHashCode, sizeHashCode, res);
        }
        if (this.mCachedFileSize > FileDescriptorHelper.f2835a) {
            clearCacheOnDisk();
        }
        sl.release();
        return res;
    }

    private void removeFilesFromDisk(final String mainHashCode) {
        String[] list = new File(this.mCachePath).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.matches(mainHashCode);
            }
        });
        if (list != null && list.length > 0) {
            final String[] fileNames = list;
            TaskDispatcher writer = TaskDispatcher.getDispatcher(CacheWriterThread);
            if (writer == null) {
                DroiLog.m2870e(LOG_TAG, "getDispatcher failed");
            } else {
                writer.enqueueTask(new Runnable() {
                    public void run() {
                        for (String fileName : fileNames) {
                            File file = new File(fileName);
                            if (file.delete()) {
                                BitmapCache.access$022(BitmapCache.this, file.length());
                                BitmapCache.this.mCachedFileTable.remove(file.getName());
                            } else {
                                DroiLog.m2868d(BitmapCache.LOG_TAG, "removeFrom Disk , failure to delete file" + fileName);
                            }
                        }
                    }
                });
            }
        }
    }

    private void writeToDisk(final byte[] bitmap, final String filePath, final String fileName) {
        TaskDispatcher writer = TaskDispatcher.getDispatcher(CacheWriterThread);
        if (writer == null) {
            DroiLog.m2870e(LOG_TAG, "getDispatcher failed");
            return;
        }
        writer.killTask(fileName);
        writer.enqueueOnceTimerTask(new Runnable() {
            public void run() {
                FileNotFoundException e;
                Throwable th;
                IOException e2;
                SocketLock sl = new SocketLock(BitmapCache.localSocketNameByURI(filePath, ""));
                sl.lock();
                FileOutputStream fileOutputStream = null;
                try {
                    FileOutputStream os = new FileOutputStream(new File(filePath));
                    try {
                        os.write(bitmap);
                        os.flush();
                        os.close();
                        fileOutputStream = null;
                        if (BitmapCache.this.mCachedFileTable.contains(fileName)) {
                            BitmapCache.this.mCachedFileTable.replace(fileName, new CachedFile(fileName, (long) bitmap.length));
                        } else {
                            BitmapCache.this.mCachedFileTable.put(fileName, new CachedFile(fileName, (long) bitmap.length));
                        }
                        BitmapCache.access$014(BitmapCache.this, (long) bitmap.length);
                        DroiLog.m2871i(BitmapCache.LOG_TAG, "[writeToFile] Write file succeeded. file path is " + fileName);
                        FileDescriptorHelper.closeQuietly(null);
                    } catch (FileNotFoundException e3) {
                        e = e3;
                        fileOutputStream = os;
                        try {
                            e.printStackTrace();
                            FileDescriptorHelper.closeQuietly(fileOutputStream);
                            sl.release();
                        } catch (Throwable th2) {
                            th = th2;
                            FileDescriptorHelper.closeQuietly(fileOutputStream);
                            throw th;
                        }
                    } catch (IOException e4) {
                        e2 = e4;
                        fileOutputStream = os;
                        e2.printStackTrace();
                        FileDescriptorHelper.closeQuietly(fileOutputStream);
                        sl.release();
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream = os;
                        FileDescriptorHelper.closeQuietly(fileOutputStream);
                        throw th;
                    }
                } catch (FileNotFoundException e5) {
                    e = e5;
                    e.printStackTrace();
                    FileDescriptorHelper.closeQuietly(fileOutputStream);
                    sl.release();
                } catch (IOException e6) {
                    e2 = e6;
                    e2.printStackTrace();
                    FileDescriptorHelper.closeQuietly(fileOutputStream);
                    sl.release();
                }
                sl.release();
            }
        }, 1000, fileName);
    }

    private byte[] processNewData(byte[] rawData, int preferredWidth, int preferredHeight, String sizeHashCode, Bitmap newBitmap, ByteArrayOutputStream byteStream) {
        if (rawData == null) {
            return null;
        }
        boolean rescaled = false;
        if (!(preferredWidth == 0 || preferredHeight == 0 || (preferredWidth == newBitmap.getWidth() && preferredHeight == newBitmap.getHeight()))) {
            newBitmap = ThumbnailUtils.extractThumbnail(newBitmap, preferredWidth, preferredHeight);
            byteStream.reset();
            newBitmap.compress(CompressFormat.PNG, 0, byteStream);
            rawData = byteStream.toByteArray();
            rescaled = true;
        }
        if (rescaled && rawData != null) {
            writeToDisk(rawData, this.mCachePath + "/" + sizeHashCode, sizeHashCode);
        }
        if (byteStream != null) {
            try {
                byteStream.close();
            } catch (IOException e) {
            }
        }
        return rawData;
    }

    private byte[] getFromCache(boolean newOrModified, String mainHashCode, String sizeHashCode) {
        FileNotFoundException e;
        Throwable th;
        byte[] res = null;
        if (newOrModified) {
            synchronized (this) {
                ArrayList<String> allItems = (ArrayList) this.mmRelations.get(mainHashCode);
                if (allItems != null) {
                    Iterator i$ = allItems.iterator();
                    while (i$.hasNext()) {
                        this.mCache.remove((String) i$.next());
                    }
                }
                removeFilesFromDisk(mainHashCode);
                this.mmRelations.remove(mainHashCode);
            }
        } else {
            byte[] bitmap = (byte[]) this.mCache.get(sizeHashCode);
            if (bitmap != null) {
                res = bitmap;
            }
            if (res == null) {
                DroiLog.m2871i(LOG_TAG, "Check whether there is any cached file in disk for file " + sizeHashCode);
                File c = new File(this.mCachePath + "/" + sizeHashCode);
                if (c.exists()) {
                    FileInputStream input = null;
                    ByteArrayOutputStream byteStream = null;
                    try {
                        FileInputStream input2 = new FileInputStream(c);
                        try {
                            ByteArrayOutputStream byteStream2 = new ByteArrayOutputStream();
                            try {
                                copy(input2, byteStream2);
                                res = byteStream2.toByteArray();
                                FileDescriptorHelper.closeQuietly(input2);
                                FileDescriptorHelper.closeQuietly(byteStream2);
                                byteStream = byteStream2;
                                input = input2;
                            } catch (FileNotFoundException e2) {
                                e = e2;
                                byteStream = byteStream2;
                                input = input2;
                                try {
                                    e.printStackTrace();
                                    FileDescriptorHelper.closeQuietly(input);
                                    FileDescriptorHelper.closeQuietly(byteStream);
                                    DroiLog.m2871i(LOG_TAG, "Use cached file from disk");
                                    return res;
                                } catch (Throwable th2) {
                                    th = th2;
                                    FileDescriptorHelper.closeQuietly(input);
                                    FileDescriptorHelper.closeQuietly(byteStream);
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                byteStream = byteStream2;
                                input = input2;
                                FileDescriptorHelper.closeQuietly(input);
                                FileDescriptorHelper.closeQuietly(byteStream);
                                throw th;
                            }
                        } catch (FileNotFoundException e3) {
                            e = e3;
                            input = input2;
                            e.printStackTrace();
                            FileDescriptorHelper.closeQuietly(input);
                            FileDescriptorHelper.closeQuietly(byteStream);
                            DroiLog.m2871i(LOG_TAG, "Use cached file from disk");
                            return res;
                        } catch (Throwable th4) {
                            th = th4;
                            input = input2;
                            FileDescriptorHelper.closeQuietly(input);
                            FileDescriptorHelper.closeQuietly(byteStream);
                            throw th;
                        }
                    } catch (FileNotFoundException e4) {
                        e = e4;
                        e.printStackTrace();
                        FileDescriptorHelper.closeQuietly(input);
                        FileDescriptorHelper.closeQuietly(byteStream);
                        DroiLog.m2871i(LOG_TAG, "Use cached file from disk");
                        return res;
                    }
                    DroiLog.m2871i(LOG_TAG, "Use cached file from disk");
                }
            } else {
                DroiLog.m2871i(LOG_TAG, "Use cached file from LruCache");
            }
        }
        return res;
    }

    private static long copy(InputStream input, OutputStream output) {
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

    private void restoreCachedFileTable() throws Exception {
        ObjectInputStream ois;
        Throwable th;
        FileInputStream fis = null;
        ObjectInputStream ois2 = null;
        try {
            FileInputStream fis2 = new FileInputStream(this.mCachePath + "/" + this.mCachedFileTableName);
            try {
                ois = new ObjectInputStream(fis2);
            } catch (Throwable th2) {
                th = th2;
                fis = fis2;
                FileDescriptorHelper.closeQuietly(fis);
                FileDescriptorHelper.closeQuietly(ois2);
                throw th;
            }
            try {
                this.mCachedFileTable = (ConcurrentHashMap) ois.readObject();
                FileDescriptorHelper.closeQuietly(fis2);
                FileDescriptorHelper.closeQuietly(ois);
            } catch (Throwable th3) {
                th = th3;
                ois2 = ois;
                fis = fis2;
                FileDescriptorHelper.closeQuietly(fis);
                FileDescriptorHelper.closeQuietly(ois2);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            FileDescriptorHelper.closeQuietly(fis);
            FileDescriptorHelper.closeQuietly(ois2);
            throw th;
        }
    }

    private void restoreCachedFile(File dir) {
        if (dir.exists()) {
            for (String sizeHashCode : dir.list()) {
                DroiLog.m2868d(LOG_TAG, "[restoreCachedFile] file: " + sizeHashCode);
                if (sizeHashCode.compareTo(this.mCachedFileTableName) != 0) {
                    CachedFile cf = (CachedFile) this.mCachedFileTable.get(sizeHashCode);
                    if (cf == null || cf.hit.intValue() <= 0) {
                        File c = new File(this.mCachePath + "/" + sizeHashCode);
                        if (!c.isDirectory()) {
                            DroiLog.m2868d(LOG_TAG, "[restoreCachedFile] Try to delete but " + c.delete());
                        }
                    } else {
                        DroiLog.m2868d(LOG_TAG, "[restoreCachedFile] Recover file:" + cf.hashCode + " size:" + cf.fileSize);
                        this.mCachedFileSize += cf.fileSize;
                    }
                }
            }
        }
    }

    private void clearCacheOnDisk() {
        if (this.mCachedFileSize >= FileDescriptorHelper.f2835a) {
            if (cacheClearing) {
                DroiLog.m2868d(LOG_TAG, "[clearCacheOnDisk]Someone is clearing cache");
                return;
            }
            cacheClearing = true;
            DroiLog.m2868d(LOG_TAG, "[clearCacheOnDisk] ++ now: " + this.mCachedFileSize);
            TaskDispatcher writer = TaskDispatcher.getDispatcher(CacheWriterThread);
            if (writer == null) {
                DroiLog.m2870e(LOG_TAG, "getDispatcher failed");
            } else {
                writer.enqueueTask(new C08885());
            }
        }
    }

    private void addRelation(String mainHashCode, String sizeHashCode, byte[] res) {
        synchronized (this) {
            ArrayList<String> relation = (ArrayList) this.mmRelations.get(mainHashCode);
            if (relation == null) {
                relation = new ArrayList();
                this.mmRelations.put(mainHashCode, relation);
            }
            relation.add(sizeHashCode);
            this.mCache.put(sizeHashCode, res);
        }
    }

    public boolean storeCachedTable() {
        FileNotFoundException e;
        Throwable th;
        IOException e2;
        boolean result = true;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            ObjectOutputStream oos2;
            FileOutputStream fos2 = new FileOutputStream(this.mCachePath + "/" + this.mCachedFileTableName);
            try {
                oos2 = new ObjectOutputStream(fos2);
            } catch (FileNotFoundException e3) {
                e = e3;
                fos = fos2;
                result = false;
                try {
                    e.printStackTrace();
                    FileDescriptorHelper.closeQuietly(fos);
                    FileDescriptorHelper.closeQuietly(oos);
                    return result;
                } catch (Throwable th2) {
                    th = th2;
                    FileDescriptorHelper.closeQuietly(fos);
                    FileDescriptorHelper.closeQuietly(oos);
                    throw th;
                }
            } catch (IOException e4) {
                e2 = e4;
                fos = fos2;
                result = false;
                e2.printStackTrace();
                FileDescriptorHelper.closeQuietly(fos);
                FileDescriptorHelper.closeQuietly(oos);
                return result;
            } catch (Throwable th3) {
                th = th3;
                fos = fos2;
                FileDescriptorHelper.closeQuietly(fos);
                FileDescriptorHelper.closeQuietly(oos);
                throw th;
            }
            try {
                oos2.writeObject(this.mCachedFileTable);
                FileDescriptorHelper.closeQuietly(fos2);
                FileDescriptorHelper.closeQuietly(oos2);
                oos = oos2;
                fos = fos2;
            } catch (FileNotFoundException e5) {
                e = e5;
                oos = oos2;
                fos = fos2;
                result = false;
                e.printStackTrace();
                FileDescriptorHelper.closeQuietly(fos);
                FileDescriptorHelper.closeQuietly(oos);
                return result;
            } catch (IOException e6) {
                e2 = e6;
                oos = oos2;
                fos = fos2;
                result = false;
                e2.printStackTrace();
                FileDescriptorHelper.closeQuietly(fos);
                FileDescriptorHelper.closeQuietly(oos);
                return result;
            } catch (Throwable th4) {
                th = th4;
                oos = oos2;
                fos = fos2;
                FileDescriptorHelper.closeQuietly(fos);
                FileDescriptorHelper.closeQuietly(oos);
                throw th;
            }
        } catch (FileNotFoundException e7) {
            e = e7;
            result = false;
            e.printStackTrace();
            FileDescriptorHelper.closeQuietly(fos);
            FileDescriptorHelper.closeQuietly(oos);
            return result;
        } catch (IOException e8) {
            e2 = e8;
            result = false;
            e2.printStackTrace();
            FileDescriptorHelper.closeQuietly(fos);
            FileDescriptorHelper.closeQuietly(oos);
            return result;
        }
        return result;
    }
}
