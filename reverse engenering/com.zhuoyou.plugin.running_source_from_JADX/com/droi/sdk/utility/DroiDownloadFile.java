package com.droi.sdk.utility;

import android.util.SparseArray;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.core.priv.TaskDispatcherPool;
import com.droi.sdk.internal.DroiLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class DroiDownloadFile {
    private static final String LOG_TAG = "DroiDownloadFile";
    private static DroiDownloadFile mInstance = null;
    private static Object mLock = new Object();
    private String mCacheFolder;
    private TaskDispatcherPool mPool = new TaskDispatcherPool(0, 5);
    private SparseArray<String> mTasks = new SparseArray();

    public interface DroiDownloadFileEventListener {
        void onFailed(String str, DroiError droiError);

        void onFinished(String str, String str2);

        void onProgress(String str, float f);

        void onStart(String str, long j);
    }

    private class DownloadTask implements Runnable {
        private String mCacheFolder;
        private DroiDownloadFileEventListener mCallback;
        private String mDestinationFileName;
        private String mTaskName;
        private String mUrl;

        public DownloadTask(String cacheFolder, String url, String destinationFileName, DroiDownloadFileEventListener callback, String taskName) {
            this.mCacheFolder = cacheFolder;
            this.mUrl = url;
            this.mDestinationFileName = destinationFileName;
            this.mCallback = callback;
            this.mTaskName = taskName;
            if (this.mCallback == null) {
                DroiLog.m2870e(DroiDownloadFile.LOG_TAG, "CALLBACK NULL!!");
            }
        }

        public void run() {
            try {
                DroiLog.m2868d(DroiDownloadFile.LOG_TAG, "Download task runnbale - " + this.mUrl);
                runWithCatch();
            } catch (Exception e) {
                if (this.mCallback != null) {
                    this.mCallback.onFailed(this.mUrl, new DroiError(DroiError.ERROR, e.toString()));
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void runWithCatch() {
            /*
            r32 = this;
            r7 = com.droi.sdk.utility.DroiDownloadFile.mLock;
            monitor-enter(r7);
            r0 = r32;
            r6 = com.droi.sdk.utility.DroiDownloadFile.this;	 Catch:{ all -> 0x0141 }
            r6 = r6.mTasks;	 Catch:{ all -> 0x0141 }
            r0 = r32;
            r0 = r0.mTaskName;	 Catch:{ all -> 0x0141 }
            r27 = r0;
            r27 = r27.hashCode();	 Catch:{ all -> 0x0141 }
            r0 = r27;
            r6 = r6.get(r0);	 Catch:{ all -> 0x0141 }
            if (r6 != 0) goto L_0x002a;
        L_0x001f:
            r6 = "DroiDownloadFile";
            r27 = "Task canceled.";
            r0 = r27;
            com.droi.sdk.internal.DroiLog.m2868d(r6, r0);	 Catch:{ all -> 0x0141 }
            monitor-exit(r7);	 Catch:{ all -> 0x0141 }
        L_0x0029:
            return;
        L_0x002a:
            monitor-exit(r7);	 Catch:{ all -> 0x0141 }
            r11 = new java.io.File;
            r0 = r32;
            r6 = r0.mCacheFolder;
            r0 = r32;
            r7 = r0.mUrl;
            r7 = r7.hashCode();
            r7 = java.lang.Integer.toHexString(r7);
            r11.<init>(r6, r7);
            r23 = 0;
            r4 = 0;
            r6 = r11.exists();
            if (r6 == 0) goto L_0x0176;
        L_0x004a:
            r18 = 0;
            r24 = r23;
        L_0x004e:
            r23 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x0144, IOException -> 0x0157 }
            r6 = "rw";
            r0 = r23;
            r0.<init>(r11, r6);	 Catch:{ FileNotFoundException -> 0x0144, IOException -> 0x0157 }
            r6 = r11.length();	 Catch:{ FileNotFoundException -> 0x029f, IOException -> 0x029c }
            r0 = r23;
            r0.seek(r6);	 Catch:{ FileNotFoundException -> 0x029f, IOException -> 0x029c }
            r4 = r11.length();	 Catch:{ FileNotFoundException -> 0x029f, IOException -> 0x029c }
            r18 = 1;
        L_0x0066:
            if (r18 == 0) goto L_0x02a2;
        L_0x0068:
            r6 = new okhttp3.OkHttpClient$Builder;
            r6.<init>();
            r30 = 15;
            r7 = java.util.concurrent.TimeUnit.SECONDS;
            r0 = r30;
            r6 = r6.connectTimeout(r0, r7);
            r30 = 15;
            r7 = java.util.concurrent.TimeUnit.SECONDS;
            r0 = r30;
            r6 = r6.readTimeout(r0, r7);
            r14 = r6.build();
            r6 = new okhttp3.Request$Builder;
            r6.<init>();
            r0 = r32;
            r7 = r0.mUrl;
            r25 = r6.url(r7);
            r6 = 0;
            r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r6 <= 0) goto L_0x00bc;
        L_0x0098:
            r6 = "Range";
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r27 = "bytes=";
            r0 = r27;
            r7 = r7.append(r0);
            r7 = r7.append(r4);
            r27 = "-";
            r0 = r27;
            r7 = r7.append(r0);
            r7 = r7.toString();
            r0 = r25;
            r0.addHeader(r6, r7);
        L_0x00bc:
            r19 = 0;
            r6 = r25.build();	 Catch:{ IOException -> 0x01ad }
            r6 = r14.newCall(r6);	 Catch:{ IOException -> 0x01ad }
            r26 = r6.execute();	 Catch:{ IOException -> 0x01ad }
            r6 = r26.isSuccessful();	 Catch:{ IOException -> 0x01ad }
            if (r6 != 0) goto L_0x00ff;
        L_0x00d0:
            r6 = 0;
            r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r6 <= 0) goto L_0x00ff;
        L_0x00d6:
            r6 = 0;
            r0 = r23;
            r0.setLength(r6);	 Catch:{ IOException -> 0x01ad }
            r6 = 0;
            r0 = r23;
            r0.seek(r6);	 Catch:{ IOException -> 0x01ad }
            r4 = 0;
            r6 = new okhttp3.Request$Builder;	 Catch:{ IOException -> 0x01ad }
            r6.<init>();	 Catch:{ IOException -> 0x01ad }
            r0 = r32;
            r7 = r0.mUrl;	 Catch:{ IOException -> 0x01ad }
            r6 = r6.url(r7);	 Catch:{ IOException -> 0x01ad }
            r6 = r6.build();	 Catch:{ IOException -> 0x01ad }
            r6 = r14.newCall(r6);	 Catch:{ IOException -> 0x01ad }
            r26 = r6.execute();	 Catch:{ IOException -> 0x01ad }
        L_0x00ff:
            r0 = r32;
            r6 = r0.mCallback;	 Catch:{ IOException -> 0x01ad }
            if (r6 == 0) goto L_0x0110;
        L_0x0105:
            r0 = r32;
            r6 = r0.mCallback;	 Catch:{ IOException -> 0x01ad }
            r0 = r32;
            r7 = r0.mUrl;	 Catch:{ IOException -> 0x01ad }
            r6.onStart(r7, r4);	 Catch:{ IOException -> 0x01ad }
        L_0x0110:
            r6 = r26.isSuccessful();	 Catch:{ IOException -> 0x01ad }
            if (r6 != 0) goto L_0x0197;
        L_0x0116:
            r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01ad }
            r6.<init>();	 Catch:{ IOException -> 0x01ad }
            r7 = "Connect to ";
            r6 = r6.append(r7);	 Catch:{ IOException -> 0x01ad }
            r0 = r32;
            r7 = r0.mUrl;	 Catch:{ IOException -> 0x01ad }
            r6 = r6.append(r7);	 Catch:{ IOException -> 0x01ad }
            r7 = " failed";
            r6 = r6.append(r7);	 Catch:{ IOException -> 0x01ad }
            r19 = r6.toString();	 Catch:{ IOException -> 0x01ad }
        L_0x0133:
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r23);
        L_0x0136:
            if (r19 == 0) goto L_0x0295;
        L_0x0138:
            r0 = r32;
            r1 = r19;
            r0.downloadFailed(r1);
            goto L_0x0029;
        L_0x0141:
            r6 = move-exception;
            monitor-exit(r7);	 Catch:{ all -> 0x0141 }
            throw r6;
        L_0x0144:
            r16 = move-exception;
            r23 = r24;
        L_0x0147:
            r6 = "DroiDownloadFile";
            r0 = r16;
            com.droi.sdk.internal.DroiLog.m2873w(r6, r0);
            r6 = "Create RandomAccessFile failed";
            r0 = r32;
            r0.downloadFailed(r6);
            goto L_0x0029;
        L_0x0157:
            r16 = move-exception;
            r23 = r24;
        L_0x015a:
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r23);
            r11.delete();	 Catch:{ IOException -> 0x0165 }
            r11.createNewFile();	 Catch:{ IOException -> 0x0165 }
            goto L_0x0066;
        L_0x0165:
            r17 = move-exception;
            r6 = "DroiDownloadFile";
            r0 = r16;
            com.droi.sdk.internal.DroiLog.m2873w(r6, r0);
            r6 = "Create createNewFile failed";
            r0 = r32;
            r0.downloadFailed(r6);
            goto L_0x0029;
        L_0x0176:
            r11.createNewFile();	 Catch:{ IOException -> 0x0186 }
            r23 = new java.io.RandomAccessFile;	 Catch:{ IOException -> 0x0186 }
            r6 = "rw";
            r0 = r23;
            r0.<init>(r11, r6);	 Catch:{ IOException -> 0x0186 }
            r4 = 0;
            goto L_0x0068;
        L_0x0186:
            r16 = move-exception;
            r6 = "DroiDownloadFile";
            r0 = r16;
            com.droi.sdk.internal.DroiLog.m2873w(r6, r0);
            r6 = "Create createNewFile/RandomAccessFile failed";
            r0 = r32;
            r0.downloadFailed(r6);
            goto L_0x0029;
        L_0x0197:
            r10 = r26.body();	 Catch:{ IOException -> 0x01ad }
            r28 = r10.contentLength();	 Catch:{ IOException -> 0x01ad }
            r6 = 0;
            r6 = (r28 > r6 ? 1 : (r28 == r6 ? 0 : -1));
            if (r6 != 0) goto L_0x01d3;
        L_0x01a5:
            r6 = 0;
            r0 = r23;
            r0.setLength(r6);	 Catch:{ IOException -> 0x01ad }
            goto L_0x0133;
        L_0x01ad:
            r16 = move-exception;
            r6 = "DroiDownloadFile";
            r0 = r16;
            com.droi.sdk.internal.DroiLog.m2873w(r6, r0);	 Catch:{ all -> 0x020b }
            if (r19 != 0) goto L_0x01ce;
        L_0x01b7:
            r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x020b }
            r6.<init>();	 Catch:{ all -> 0x020b }
            r7 = "IOException is ";
            r6 = r6.append(r7);	 Catch:{ all -> 0x020b }
            r7 = r16.toString();	 Catch:{ all -> 0x020b }
            r6 = r6.append(r7);	 Catch:{ all -> 0x020b }
            r19 = r6.toString();	 Catch:{ all -> 0x020b }
        L_0x01ce:
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r23);
            goto L_0x0136;
        L_0x01d3:
            r6 = r10.byteStream();	 Catch:{ IOException -> 0x01ad }
            r3 = java.nio.channels.Channels.newChannel(r6);	 Catch:{ IOException -> 0x01ad }
            r2 = r23.getChannel();	 Catch:{ IOException -> 0x01ad }
            r20 = 0;
            r21 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
            r8 = (float) r4;
            r6 = r4 + r28;
            r9 = (float) r6;
        L_0x01e7:
            r0 = r20;
            r6 = (long) r0;
            r6 = (r6 > r28 ? 1 : (r6 == r28 ? 0 : -1));
            if (r6 >= 0) goto L_0x01fa;
        L_0x01ee:
            r6 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
            r12 = r2.transferFrom(r3, r4, r6);	 Catch:{ IOException -> 0x026a }
            r6 = 0;
            r6 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1));
            if (r6 != 0) goto L_0x0210;
        L_0x01fa:
            r0 = r20;
            r6 = (long) r0;	 Catch:{ IOException -> 0x026a }
            r6 = (r6 > r28 ? 1 : (r6 == r28 ? 0 : -1));
            if (r6 >= 0) goto L_0x0203;
        L_0x0201:
            r19 = "byteCopied is zero";
        L_0x0203:
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r3);	 Catch:{ IOException -> 0x01ad }
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r2);	 Catch:{ IOException -> 0x01ad }
            goto L_0x0133;
        L_0x020b:
            r6 = move-exception;
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r23);
            throw r6;
        L_0x0210:
            r0 = r20;
            r6 = (long) r0;
            r6 = r6 + r12;
            r0 = (int) r6;
            r20 = r0;
            r4 = r4 + r12;
            r0 = r32;
            r6 = r0.mCallback;	 Catch:{ IOException -> 0x026a }
            if (r6 == 0) goto L_0x0244;
        L_0x021e:
            r0 = r20;
            r6 = (float) r0;	 Catch:{ IOException -> 0x026a }
            r15 = r6 + r8;
            r22 = r15 / r9;
            r6 = r21 - r22;
            r6 = java.lang.Math.abs(r6);	 Catch:{ IOException -> 0x026a }
            r6 = (double) r6;	 Catch:{ IOException -> 0x026a }
            r30 = 4576918229304087675; // 0x3f847ae147ae147b float:89128.96 double:0.01;
            r6 = (r6 > r30 ? 1 : (r6 == r30 ? 0 : -1));
            if (r6 <= 0) goto L_0x0244;
        L_0x0235:
            r0 = r32;
            r6 = r0.mCallback;	 Catch:{ IOException -> 0x026a }
            r0 = r32;
            r7 = r0.mUrl;	 Catch:{ IOException -> 0x026a }
            r0 = r22;
            r6.onProgress(r7, r0);	 Catch:{ IOException -> 0x026a }
            r21 = r22;
        L_0x0244:
            r7 = com.droi.sdk.utility.DroiDownloadFile.mLock;	 Catch:{ IOException -> 0x026a }
            monitor-enter(r7);	 Catch:{ IOException -> 0x026a }
            r0 = r32;
            r6 = com.droi.sdk.utility.DroiDownloadFile.this;	 Catch:{ all -> 0x0267 }
            r6 = r6.mTasks;	 Catch:{ all -> 0x0267 }
            r0 = r32;
            r0 = r0.mTaskName;	 Catch:{ all -> 0x0267 }
            r27 = r0;
            r27 = r27.hashCode();	 Catch:{ all -> 0x0267 }
            r0 = r27;
            r6 = r6.get(r0);	 Catch:{ all -> 0x0267 }
            if (r6 != 0) goto L_0x028a;
        L_0x0263:
            r19 = "Task is cancelled.";
            monitor-exit(r7);	 Catch:{ all -> 0x0267 }
            goto L_0x01fa;
        L_0x0267:
            r6 = move-exception;
            monitor-exit(r7);	 Catch:{ all -> 0x0267 }
            throw r6;	 Catch:{ IOException -> 0x026a }
        L_0x026a:
            r16 = move-exception;
            r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x028d }
            r6.<init>();	 Catch:{ all -> 0x028d }
            r7 = "IOException is ";
            r6 = r6.append(r7);	 Catch:{ all -> 0x028d }
            r7 = r16.toString();	 Catch:{ all -> 0x028d }
            r6 = r6.append(r7);	 Catch:{ all -> 0x028d }
            r19 = r6.toString();	 Catch:{ all -> 0x028d }
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r3);	 Catch:{ IOException -> 0x01ad }
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r2);	 Catch:{ IOException -> 0x01ad }
            goto L_0x0133;
        L_0x028a:
            monitor-exit(r7);	 Catch:{ all -> 0x0267 }
            goto L_0x01e7;
        L_0x028d:
            r6 = move-exception;
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r3);	 Catch:{ IOException -> 0x01ad }
            com.droi.sdk.utility.DroiDownloadFile.closeQuietly(r2);	 Catch:{ IOException -> 0x01ad }
            throw r6;	 Catch:{ IOException -> 0x01ad }
        L_0x0295:
            r0 = r32;
            r0.downloadFinished(r11);
            goto L_0x0029;
        L_0x029c:
            r16 = move-exception;
            goto L_0x015a;
        L_0x029f:
            r16 = move-exception;
            goto L_0x0147;
        L_0x02a2:
            r24 = r23;
            goto L_0x004e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.utility.DroiDownloadFile.DownloadTask.runWithCatch():void");
        }

        private void downloadFailed(String msg) {
            removeTask();
            if (this.mCallback != null) {
                this.mCallback.onFailed(this.mUrl, new DroiError(DroiError.ERROR, msg));
            }
            DroiLog.m2870e(DroiDownloadFile.LOG_TAG, msg);
        }

        private void downloadFinished(File src) {
            removeTask();
            if (!src.renameTo(new File(this.mDestinationFileName))) {
                DroiLog.m2868d(DroiDownloadFile.LOG_TAG, "Copy file using FileChannel");
                FileChannel fileChannel = null;
                FileChannel fileChannel2 = null;
                DroiError error = new DroiError();
                try {
                    File dest = new File(this.mDestinationFileName);
                    dest.getParentFile().mkdirs();
                    dest.createNewFile();
                    fileChannel = new FileInputStream(src).getChannel();
                    fileChannel2 = new FileOutputStream(dest).getChannel();
                    fileChannel2.transferFrom(fileChannel, 0, fileChannel.size());
                    DroiDownloadFile.closeQuietly(fileChannel);
                    fileChannel = null;
                    src.delete();
                } catch (Exception e) {
                    error.setCode(DroiError.ERROR);
                    error.setAppendedMessage(e.toString());
                    DroiLog.m2873w(DroiDownloadFile.LOG_TAG, e);
                } catch (Exception e2) {
                    error.setCode(DroiError.ERROR);
                    error.setAppendedMessage(e2.toString());
                    DroiLog.m2873w(DroiDownloadFile.LOG_TAG, e2);
                } finally {
                    DroiDownloadFile.closeQuietly(fileChannel);
                    DroiDownloadFile.closeQuietly(fileChannel2);
                }
                if (!error.isOk()) {
                    if (this.mCallback != null) {
                        this.mCallback.onFailed(this.mUrl, error);
                        return;
                    }
                    return;
                }
            }
            if (this.mCallback != null) {
                this.mCallback.onFinished(this.mUrl, this.mDestinationFileName);
            }
            DroiLog.m2868d(DroiDownloadFile.LOG_TAG, "Download finished");
        }

        private void removeTask() {
            synchronized (DroiDownloadFile.mLock) {
                DroiDownloadFile.this.mTasks.remove(this.mTaskName.hashCode());
            }
        }
    }

    public static DroiDownloadFile instance() {
        if (mInstance != null) {
            return mInstance;
        }
        synchronized (mLock) {
            if (mInstance != null) {
                DroiDownloadFile droiDownloadFile = mInstance;
                return droiDownloadFile;
            }
            mInstance = new DroiDownloadFile();
            return mInstance;
        }
    }

    public int downloadFile(String url, String destinationFileName, DroiDownloadFileEventListener callback) {
        if (url == null || destinationFileName == null) {
            return 0;
        }
        DroiLog.m2868d(LOG_TAG, "<<< Download file task - " + url);
        String taskName = url;
        synchronized (mLock) {
            if (this.mTasks.get(taskName.hashCode()) != null) {
                DroiLog.m2868d(LOG_TAG, "Task exists. ignored.");
                return 0;
            }
            taskName = this.mPool.enqueueTask(new DownloadTask(this.mCacheFolder, url, destinationFileName, callback, taskName), taskName);
            this.mTasks.put(taskName.hashCode(), taskName);
            DroiLog.m2868d(LOG_TAG, ">>> Download file task");
            return taskName.hashCode();
        }
    }

    public void cancelDownloadTask(int task) {
        String taskName = (String) this.mTasks.get(task);
        if (taskName != null) {
            synchronized (mLock) {
                this.mTasks.remove(task);
                this.mPool.cancelTask(taskName);
            }
        }
    }

    public void clearTemporalFiles() {
        File cacheDir = new File(this.mCacheFolder);
        if (cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    private DroiDownloadFile() {
        File path = new File(CorePriv.getContext().getApplicationInfo().dataDir + "/droi/downloadcache");
        path.mkdirs();
        this.mCacheFolder = path.getAbsolutePath();
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                DroiLog.m2873w(LOG_TAG, e);
            }
        }
    }
}
