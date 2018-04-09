package com.zhuoyou.plugin.rank;

import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.util.Log;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public class ImageUtils {
    private static final int SOFT_CACHE_CAPACITY = 40;
    private static final ConcurrentHashMap<String, SoftReference<Drawable>> sSoftBitmapCache = new ConcurrentHashMap(40);
    private final int hardCachedSize = 2097152;
    private final LruCache<String, Drawable> sHardBitmapCache = new LruCache<String, Drawable>(2097152) {
        protected void entryRemoved(boolean evicted, String key, Drawable oldValue, Drawable newValue) {
            Log.v("tag", "hard cache is full , push to soft cache");
            ImageUtils.sSoftBitmapCache.put(key, new SoftReference(oldValue));
        }
    };

    public boolean putDrawable(String key, Drawable drawable) {
        if (drawable == null) {
            return false;
        }
        synchronized (this.sHardBitmapCache) {
            this.sHardBitmapCache.put(key, drawable);
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.drawable.Drawable getDrawable(java.lang.String r6) {
        /*
        r5 = this;
        r3 = r5.sHardBitmapCache;
        monitor-enter(r3);
        r2 = r5.sHardBitmapCache;	 Catch:{ all -> 0x002a }
        r0 = r2.get(r6);	 Catch:{ all -> 0x002a }
        r0 = (android.graphics.drawable.Drawable) r0;	 Catch:{ all -> 0x002a }
        if (r0 == 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r3);	 Catch:{ all -> 0x002a }
    L_0x000e:
        return r0;
    L_0x000f:
        monitor-exit(r3);	 Catch:{ all -> 0x002a }
        r3 = sSoftBitmapCache;
        monitor-enter(r3);
        r2 = sSoftBitmapCache;	 Catch:{ all -> 0x0027 }
        r1 = r2.get(r6);	 Catch:{ all -> 0x0027 }
        r1 = (java.lang.ref.SoftReference) r1;	 Catch:{ all -> 0x0027 }
        if (r1 == 0) goto L_0x0039;
    L_0x001d:
        r0 = r1.get();	 Catch:{ all -> 0x0027 }
        r0 = (android.graphics.drawable.Drawable) r0;	 Catch:{ all -> 0x0027 }
        if (r0 == 0) goto L_0x002d;
    L_0x0025:
        monitor-exit(r3);	 Catch:{ all -> 0x0027 }
        goto L_0x000e;
    L_0x0027:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0027 }
        throw r2;
    L_0x002a:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x002a }
        throw r2;
    L_0x002d:
        r2 = "tag";
        r4 = "soft reference 已经被回收";
        android.util.Log.e(r2, r4);	 Catch:{ all -> 0x0027 }
        r2 = sSoftBitmapCache;	 Catch:{ all -> 0x0027 }
        r2.remove(r6);	 Catch:{ all -> 0x0027 }
    L_0x0039:
        monitor-exit(r3);	 Catch:{ all -> 0x0027 }
        r0 = 0;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.rank.ImageUtils.getDrawable(java.lang.String):android.graphics.drawable.Drawable");
    }
}
