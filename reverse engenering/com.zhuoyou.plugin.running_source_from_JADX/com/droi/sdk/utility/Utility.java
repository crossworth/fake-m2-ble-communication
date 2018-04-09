package com.droi.sdk.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiRunnable;
import com.droi.sdk.core.DroiTask;
import com.droi.sdk.core.TaskDispatcher;
import com.droi.sdk.core.priv.BitmapCache;
import com.droi.sdk.core.priv.TaskDispatcherPool;
import com.droi.sdk.internal.DroiLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class Utility {
    private static final String LOG_TAG = "UtilitySDK";
    private static Utility mSingleton = null;
    private BitmapCache mBitmapCache = null;
    private boolean mInitCacheProxy = false;
    private TaskDispatcherPool mPool = new TaskDispatcherPool(0, 10);

    public static void initialize(Context context) {
        Core.initialize(context);
        if (mSingleton == null) {
            mSingleton = new Utility();
        }
    }

    public static Bitmap getBitmap(String url, int preferredWidth, int preferredHeight, DroiError error) {
        if (mSingleton == null) {
            if (error == null) {
                return null;
            }
            error.setCode(DroiError.CORE_NOT_INITIALIZED);
            return null;
        } else if (mSingleton.initCacheProxy()) {
            final AtomicReference<Bitmap> result = new AtomicReference();
            if (TaskDispatcher.currentTaskDispatcher() != TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName)) {
                result.set(mSingleton.getBitmapPriv(url, preferredWidth, preferredHeight, error));
            } else {
                final String str = url;
                final int i = preferredWidth;
                final int i2 = preferredHeight;
                final DroiError droiError = error;
                DroiTask.create(new DroiRunnable() {
                    public void run() {
                        result.set(Utility.mSingleton.getBitmapPriv(str, i, i2, droiError));
                    }
                }).runAndWait(TaskDispatcher.BackgroundThreadName);
            }
            return (Bitmap) result.get();
        } else if (error == null) {
            return null;
        } else {
            error.setCode(DroiError.CORE_NOT_INITIALIZED);
            return null;
        }
    }

    public static void cancelBitmapInBackgroundTask(String taskID, DroiError error) {
        if (mSingleton == null) {
            if (error != null) {
                error.setCode(DroiError.CORE_NOT_INITIALIZED);
            }
        } else if (mSingleton.initCacheProxy()) {
            mSingleton.mPool.cancelTask(taskID);
        } else if (error != null) {
            error.setCode(DroiError.CORE_NOT_INITIALIZED);
        }
    }

    public static String getBitmapInBackground(String url, int preferredWidth, int preferredHeight, BitmapBackgroundCallback callback) {
        if (mSingleton != null && mSingleton.initCacheProxy()) {
            return mSingleton.getBitmapInBackgroundPriv(url, preferredWidth, preferredHeight, callback);
        }
        return null;
    }

    public static boolean isBitmapCached(String url, DroiError error) {
        if (mSingleton == null) {
            if (error == null) {
                return false;
            }
            error.setCode(DroiError.CORE_NOT_INITIALIZED);
            return false;
        } else if (mSingleton.initCacheProxy()) {
            if (error != null) {
                error.setCode(0);
            }
            if (mSingleton.mBitmapCache == null && mSingleton.mBitmapCache == null) {
                synchronized (mSingleton) {
                    if (mSingleton.mBitmapCache == null) {
                        mSingleton.mBitmapCache = new BitmapCache();
                    }
                }
            }
            if (mSingleton.mBitmapCache != null) {
                return mSingleton.mBitmapCache.isBitmapCached(url);
            }
            return false;
        } else if (error == null) {
            return false;
        } else {
            error.setCode(DroiError.CORE_NOT_INITIALIZED);
            return false;
        }
    }

    private static void loadLibraryFromJar(String path) throws IOException {
        String name = new File(path).getName();
        if (name.startsWith("lib") && name.endsWith(".so")) {
            boolean loaded = false;
            try {
                System.loadLibrary(name.substring(3, name.length() - 3));
                loaded = true;
            } catch (UnsatisfiedLinkError e) {
            }
            if (loaded) {
                return;
            }
        }
        if (path.startsWith("/")) {
            String[] parts = path.split("/");
            String filename = parts.length > 1 ? parts[parts.length - 1] : null;
            String prefix = "";
            String suffix = null;
            if (filename != null) {
                parts = filename.split("\\.", 2);
                prefix = parts[0];
                suffix = parts.length > 1 ? "." + parts[parts.length - 1] : null;
            }
            if (filename == null || prefix.length() < 3) {
                throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
            }
            File temp = File.createTempFile(prefix, suffix);
            temp.deleteOnExit();
            if (temp.exists()) {
                byte[] buffer = new byte[1024];
                InputStream is = Utility.class.getResourceAsStream(path);
                if (is == null) {
                    throw new FileNotFoundException("File " + path + " was not found inside JAR.");
                }
                OutputStream os = new FileOutputStream(temp);
                while (true) {
                    try {
                        int readBytes = is.read(buffer);
                        if (readBytes == -1) {
                            break;
                        }
                        os.write(buffer, 0, readBytes);
                    } finally {
                        os.close();
                        is.close();
                    }
                }
                System.load(temp.getAbsolutePath());
                return;
            }
            throw new FileNotFoundException("File " + temp.getAbsolutePath() + " does not exist.");
        }
        throw new IllegalArgumentException("The path has to be absolute (start with '/').");
    }

    private boolean initCacheProxy() {
        if (this.mInitCacheProxy) {
            return true;
        }
        try {
            System.loadLibrary("cacheproxy");
            this.mInitCacheProxy = true;
        } catch (Exception e) {
            DroiLog.m2870e(LOG_TAG, "Load so fail. " + e);
        }
        return this.mInitCacheProxy;
    }

    private Bitmap getBitmapPriv(String url, int preferredWidth, int preferredHeight, DroiError error) {
        Bitmap res = null;
        if (this.mBitmapCache == null) {
            synchronized (this) {
                if (this.mBitmapCache == null) {
                    this.mBitmapCache = new BitmapCache();
                }
            }
        }
        byte[] result = this.mBitmapCache.getBitmap(url, preferredWidth, preferredHeight);
        if (result != null) {
            res = BitmapFactory.decodeByteArray(result, 0, result.length);
        }
        if (error != null) {
            error.setCode(0);
        }
        return res;
    }

    private String getBitmapInBackgroundPriv(String url, int preferredWidth, int preferredHeight, BitmapBackgroundCallback callback) {
        final String str = url;
        final int i = preferredWidth;
        final int i2 = preferredHeight;
        final BitmapBackgroundCallback bitmapBackgroundCallback = callback;
        return this.mPool.enqueueTaskAtFrontOfQueue(new Runnable() {
            public void run() {
                DroiError err = new DroiError();
                Bitmap res = Utility.getBitmap(str, i, i2, err);
                if (bitmapBackgroundCallback != null) {
                    bitmapBackgroundCallback.result(res != null, res, err);
                }
            }
        });
    }
}
