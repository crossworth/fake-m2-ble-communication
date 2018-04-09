package com.droi.sdk.selfupdate.util;

import android.util.SparseArray;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.core.priv.TaskDispatcherPool;
import com.droi.sdk.internal.DroiLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class C1049c {
    private static C1049c f3480a = null;
    private static Object f3481b = new Object();
    private String f3482c;
    private TaskDispatcherPool f3483d = new TaskDispatcherPool(0, 5);
    private SparseArray<String> f3484e = new SparseArray();

    public interface C1036b {
        void mo1941a(String str, float f);

        void mo1942a(String str, long j);

        void mo1943a(String str, DroiError droiError);

        void mo1944a(String str, String str2);
    }

    private class C1048a implements Runnable {
        final /* synthetic */ C1049c f3474a;
        private String f3475b;
        private String f3476c;
        private String f3477d;
        private C1036b f3478e;
        private String f3479f;

        public C1048a(C1049c c1049c, String str, String str2, String str3, C1036b c1036b, String str4) {
            this.f3474a = c1049c;
            this.f3477d = str;
            this.f3475b = str2;
            this.f3476c = str3;
            this.f3478e = c1036b;
            this.f3479f = str4;
            if (this.f3478e == null) {
                DroiLog.m2870e("DroiDownloadFile", "CALLBACK NULL!!");
            }
        }

        public void run() {
            try {
                DroiLog.m2868d("DroiDownloadFile", "Download task runnbale - " + this.f3475b);
                m3281a();
            } catch (Exception e) {
                if (this.f3478e != null) {
                    this.f3478e.mo1943a(this.f3475b, new DroiError(DroiError.ERROR, e.toString()));
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void m3281a() {
            /*
            r25 = this;
            r3 = com.droi.sdk.selfupdate.util.C1049c.f3481b;
            monitor-enter(r3);
            r0 = r25;
            r2 = r0.f3474a;	 Catch:{ all -> 0x0127 }
            r2 = r2.f3484e;	 Catch:{ all -> 0x0127 }
            r0 = r25;
            r4 = r0.f3479f;	 Catch:{ all -> 0x0127 }
            r4 = r4.hashCode();	 Catch:{ all -> 0x0127 }
            r2 = r2.get(r4);	 Catch:{ all -> 0x0127 }
            if (r2 != 0) goto L_0x0024;
        L_0x001b:
            r2 = "DroiDownloadFile";
            r4 = "Task canceled.";
            com.droi.sdk.internal.DroiLog.m2868d(r2, r4);	 Catch:{ all -> 0x0127 }
            monitor-exit(r3);	 Catch:{ all -> 0x0127 }
        L_0x0023:
            return;
        L_0x0024:
            monitor-exit(r3);	 Catch:{ all -> 0x0127 }
            r14 = new java.io.File;
            r0 = r25;
            r2 = r0.f3477d;
            r0 = r25;
            r3 = r0.f3475b;
            r3 = r3.hashCode();
            r3 = java.lang.Integer.toHexString(r3);
            r14.<init>(r2, r3);
            r3 = 0;
            r4 = 0;
            r2 = r14.exists();
            if (r2 == 0) goto L_0x0156;
        L_0x0043:
            r2 = 0;
            r6 = r4;
            r5 = r2;
            r4 = r3;
        L_0x0047:
            r3 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x012a, IOException -> 0x0276 }
            r2 = "rw";
            r3.<init>(r14, r2);	 Catch:{ FileNotFoundException -> 0x012a, IOException -> 0x0276 }
            r8 = r14.length();	 Catch:{ FileNotFoundException -> 0x012a, IOException -> 0x0139 }
            r3.seek(r8);	 Catch:{ FileNotFoundException -> 0x012a, IOException -> 0x0139 }
            r6 = r14.length();	 Catch:{ FileNotFoundException -> 0x012a, IOException -> 0x0139 }
            r5 = 1;
            r4 = r3;
            r2 = r6;
        L_0x005c:
            if (r5 == 0) goto L_0x028a;
        L_0x005e:
            r8 = r4;
        L_0x005f:
            r4 = new okhttp3.OkHttpClient$Builder;
            r4.<init>();
            r6 = 15;
            r5 = java.util.concurrent.TimeUnit.SECONDS;
            r4 = r4.connectTimeout(r6, r5);
            r6 = 15;
            r5 = java.util.concurrent.TimeUnit.SECONDS;
            r4 = r4.readTimeout(r6, r5);
            r6 = r4.build();
            r4 = new okhttp3.Request$Builder;
            r4.<init>();
            r0 = r25;
            r5 = r0.f3475b;
            r4 = r4.url(r5);
            r10 = 0;
            r5 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1));
            if (r5 <= 0) goto L_0x00a9;
        L_0x008b:
            r5 = "Range";
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r9 = "bytes=";
            r7 = r7.append(r9);
            r7 = r7.append(r2);
            r9 = "-";
            r7 = r7.append(r9);
            r7 = r7.toString();
            r4.addHeader(r5, r7);
        L_0x00a9:
            r9 = 0;
            r4 = r4.build();	 Catch:{ IOException -> 0x026e }
            r4 = r6.newCall(r4);	 Catch:{ IOException -> 0x026e }
            r4 = r4.execute();	 Catch:{ IOException -> 0x026e }
            r5 = r4.isSuccessful();	 Catch:{ IOException -> 0x026e }
            if (r5 != 0) goto L_0x0283;
        L_0x00bc:
            r10 = 0;
            r5 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1));
            if (r5 <= 0) goto L_0x0283;
        L_0x00c2:
            r2 = 0;
            r8.setLength(r2);	 Catch:{ IOException -> 0x026e }
            r2 = 0;
            r8.seek(r2);	 Catch:{ IOException -> 0x026e }
            r4 = 0;
            r2 = new okhttp3.Request$Builder;	 Catch:{ IOException -> 0x026e }
            r2.<init>();	 Catch:{ IOException -> 0x026e }
            r0 = r25;
            r3 = r0.f3475b;	 Catch:{ IOException -> 0x026e }
            r2 = r2.url(r3);	 Catch:{ IOException -> 0x026e }
            r2 = r2.build();	 Catch:{ IOException -> 0x026e }
            r2 = r6.newCall(r2);	 Catch:{ IOException -> 0x026e }
            r2 = r2.execute();	 Catch:{ IOException -> 0x026e }
        L_0x00e7:
            r0 = r25;
            r3 = r0.f3478e;	 Catch:{ IOException -> 0x026e }
            if (r3 == 0) goto L_0x00f8;
        L_0x00ed:
            r0 = r25;
            r3 = r0.f3478e;	 Catch:{ IOException -> 0x026e }
            r0 = r25;
            r6 = r0.f3475b;	 Catch:{ IOException -> 0x026e }
            r3.mo1942a(r6, r4);	 Catch:{ IOException -> 0x026e }
        L_0x00f8:
            r3 = r2.isSuccessful();	 Catch:{ IOException -> 0x026e }
            if (r3 != 0) goto L_0x0174;
        L_0x00fe:
            r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x026e }
            r2.<init>();	 Catch:{ IOException -> 0x026e }
            r3 = "Connect to ";
            r2 = r2.append(r3);	 Catch:{ IOException -> 0x026e }
            r0 = r25;
            r3 = r0.f3475b;	 Catch:{ IOException -> 0x026e }
            r2 = r2.append(r3);	 Catch:{ IOException -> 0x026e }
            r3 = " failed";
            r2 = r2.append(r3);	 Catch:{ IOException -> 0x026e }
            r2 = r2.toString();	 Catch:{ IOException -> 0x026e }
        L_0x011b:
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r8);
        L_0x011e:
            if (r2 == 0) goto L_0x0267;
        L_0x0120:
            r0 = r25;
            r0.m3283a(r2);
            goto L_0x0023;
        L_0x0127:
            r2 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0127 }
            throw r2;
        L_0x012a:
            r2 = move-exception;
            r3 = "DroiDownloadFile";
            com.droi.sdk.internal.DroiLog.m2873w(r3, r2);
            r2 = "Create RandomAccessFile failed";
            r0 = r25;
            r0.m3283a(r2);
            goto L_0x0023;
        L_0x0139:
            r2 = move-exception;
        L_0x013a:
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r3);
            r14.delete();	 Catch:{ IOException -> 0x0147 }
            r14.createNewFile();	 Catch:{ IOException -> 0x0147 }
            r4 = r3;
            r2 = r6;
            goto L_0x005c;
        L_0x0147:
            r3 = move-exception;
            r3 = "DroiDownloadFile";
            com.droi.sdk.internal.DroiLog.m2873w(r3, r2);
            r2 = "Create createNewFile failed";
            r0 = r25;
            r0.m3283a(r2);
            goto L_0x0023;
        L_0x0156:
            r14.createNewFile();	 Catch:{ IOException -> 0x0165 }
            r4 = new java.io.RandomAccessFile;	 Catch:{ IOException -> 0x0165 }
            r2 = "rw";
            r4.<init>(r14, r2);	 Catch:{ IOException -> 0x0165 }
            r2 = 0;
            r8 = r4;
            goto L_0x005f;
        L_0x0165:
            r2 = move-exception;
            r3 = "DroiDownloadFile";
            com.droi.sdk.internal.DroiLog.m2873w(r3, r2);
            r2 = "Create createNewFile/RandomAccessFile failed";
            r0 = r25;
            r0.m3283a(r2);
            goto L_0x0023;
        L_0x0174:
            r2 = r2.body();	 Catch:{ IOException -> 0x026e }
            r16 = r2.contentLength();	 Catch:{ IOException -> 0x026e }
            r6 = 0;
            r3 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1));
            if (r3 != 0) goto L_0x0189;
        L_0x0182:
            r2 = 0;
            r8.setLength(r2);	 Catch:{ IOException -> 0x026e }
            r2 = r9;
            goto L_0x011b;
        L_0x0189:
            r2 = r2.byteStream();	 Catch:{ IOException -> 0x026e }
            r3 = java.nio.channels.Channels.newChannel(r2);	 Catch:{ IOException -> 0x026e }
            r2 = r8.getChannel();	 Catch:{ IOException -> 0x026e }
            r10 = 0;
            r11 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
            r15 = (float) r4;
            r6 = r4 + r16;
            r0 = (float) r6;
            r18 = r0;
        L_0x019e:
            r6 = (long) r10;
            r6 = (r6 > r16 ? 1 : (r6 == r16 ? 0 : -1));
            if (r6 >= 0) goto L_0x0280;
        L_0x01a3:
            r6 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
            r12 = r2.transferFrom(r3, r4, r6);	 Catch:{ IOException -> 0x0213 }
            r6 = 0;
            r6 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1));
            if (r6 != 0) goto L_0x01c0;
        L_0x01af:
            r4 = r10;
        L_0x01b0:
            r4 = (long) r4;	 Catch:{ IOException -> 0x0213 }
            r4 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1));
            if (r4 >= 0) goto L_0x027a;
        L_0x01b5:
            r4 = "byteCopied is zero";
        L_0x01b7:
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r3);	 Catch:{ IOException -> 0x0272 }
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r2);	 Catch:{ IOException -> 0x0272 }
            r2 = r4;
            goto L_0x011b;
        L_0x01c0:
            r6 = (long) r10;
            r6 = r6 + r12;
            r6 = (int) r6;
            r12 = r12 + r4;
            r0 = r25;
            r4 = r0.f3478e;	 Catch:{ IOException -> 0x0213 }
            if (r4 == 0) goto L_0x027d;
        L_0x01ca:
            r4 = (float) r6;	 Catch:{ IOException -> 0x0213 }
            r4 = r4 + r15;
            r4 = r4 / r18;
            r5 = r11 - r4;
            r5 = java.lang.Math.abs(r5);	 Catch:{ IOException -> 0x0213 }
            r0 = (double) r5;	 Catch:{ IOException -> 0x0213 }
            r20 = r0;
            r22 = 4576918229304087675; // 0x3f847ae147ae147b float:89128.96 double:0.01;
            r5 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1));
            if (r5 <= 0) goto L_0x027d;
        L_0x01e0:
            r0 = r25;
            r5 = r0.f3478e;	 Catch:{ IOException -> 0x0213 }
            r0 = r25;
            r7 = r0.f3475b;	 Catch:{ IOException -> 0x0213 }
            r5.mo1941a(r7, r4);	 Catch:{ IOException -> 0x0213 }
        L_0x01eb:
            r5 = com.droi.sdk.selfupdate.util.C1049c.f3481b;	 Catch:{ IOException -> 0x0213 }
            monitor-enter(r5);	 Catch:{ IOException -> 0x0213 }
            r0 = r25;
            r7 = r0.f3474a;	 Catch:{ all -> 0x0210 }
            r7 = r7.f3484e;	 Catch:{ all -> 0x0210 }
            r0 = r25;
            r10 = r0.f3479f;	 Catch:{ all -> 0x0210 }
            r10 = r10.hashCode();	 Catch:{ all -> 0x0210 }
            r7 = r7.get(r10);	 Catch:{ all -> 0x0210 }
            if (r7 != 0) goto L_0x020b;
        L_0x0206:
            r9 = "Task is cancelled.";
            monitor-exit(r5);	 Catch:{ all -> 0x0210 }
            r4 = r6;
            goto L_0x01b0;
        L_0x020b:
            monitor-exit(r5);	 Catch:{ all -> 0x0210 }
            r11 = r4;
            r10 = r6;
            r4 = r12;
            goto L_0x019e;
        L_0x0210:
            r4 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x0210 }
            throw r4;	 Catch:{ IOException -> 0x0213 }
        L_0x0213:
            r4 = move-exception;
            r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0234 }
            r5.<init>();	 Catch:{ all -> 0x0234 }
            r6 = "IOException is ";
            r5 = r5.append(r6);	 Catch:{ all -> 0x0234 }
            r4 = r4.toString();	 Catch:{ all -> 0x0234 }
            r4 = r5.append(r4);	 Catch:{ all -> 0x0234 }
            r4 = r4.toString();	 Catch:{ all -> 0x0234 }
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r3);	 Catch:{ IOException -> 0x0272 }
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r2);	 Catch:{ IOException -> 0x0272 }
            r2 = r4;
            goto L_0x011b;
        L_0x0234:
            r4 = move-exception;
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r3);	 Catch:{ IOException -> 0x023c }
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r2);	 Catch:{ IOException -> 0x023c }
            throw r4;	 Catch:{ IOException -> 0x023c }
        L_0x023c:
            r2 = move-exception;
            r3 = r2;
            r2 = r9;
        L_0x023f:
            r4 = "DroiDownloadFile";
            com.droi.sdk.internal.DroiLog.m2873w(r4, r3);	 Catch:{ all -> 0x0262 }
            if (r2 != 0) goto L_0x025d;
        L_0x0246:
            r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0262 }
            r2.<init>();	 Catch:{ all -> 0x0262 }
            r4 = "IOException is ";
            r2 = r2.append(r4);	 Catch:{ all -> 0x0262 }
            r3 = r3.toString();	 Catch:{ all -> 0x0262 }
            r2 = r2.append(r3);	 Catch:{ all -> 0x0262 }
            r2 = r2.toString();	 Catch:{ all -> 0x0262 }
        L_0x025d:
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r8);
            goto L_0x011e;
        L_0x0262:
            r2 = move-exception;
            com.droi.sdk.selfupdate.util.C1049c.m3289b(r8);
            throw r2;
        L_0x0267:
            r0 = r25;
            r0.m3282a(r14);
            goto L_0x0023;
        L_0x026e:
            r2 = move-exception;
            r3 = r2;
            r2 = r9;
            goto L_0x023f;
        L_0x0272:
            r2 = move-exception;
            r3 = r2;
            r2 = r4;
            goto L_0x023f;
        L_0x0276:
            r2 = move-exception;
            r3 = r4;
            goto L_0x013a;
        L_0x027a:
            r4 = r9;
            goto L_0x01b7;
        L_0x027d:
            r4 = r11;
            goto L_0x01eb;
        L_0x0280:
            r4 = r10;
            goto L_0x01b0;
        L_0x0283:
            r24 = r4;
            r4 = r2;
            r2 = r24;
            goto L_0x00e7;
        L_0x028a:
            r6 = r2;
            goto L_0x0047;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.selfupdate.util.c.a.a():void");
        }

        private void m3283a(String str) {
            m3284b();
            if (this.f3478e != null) {
                this.f3478e.mo1943a(this.f3475b, new DroiError(DroiError.ERROR, str));
            }
            DroiLog.m2870e("DroiDownloadFile", str);
        }

        private void m3282a(File file) {
            Closeable channel;
            Closeable channel2;
            Exception e;
            Throwable th;
            Exception exception;
            Closeable closeable = null;
            m3284b();
            if (!file.renameTo(new File(this.f3476c))) {
                DroiLog.m2868d("DroiDownloadFile", "Copy file using FileChannel");
                DroiError droiError = new DroiError();
                try {
                    File file2 = new File(this.f3476c);
                    file2.getParentFile().mkdirs();
                    file2.createNewFile();
                    channel = new FileInputStream(file).getChannel();
                    try {
                        channel2 = new FileOutputStream(file2).getChannel();
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        closeable = channel;
                        channel = null;
                        try {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage(e.toString());
                            DroiLog.m2873w("DroiDownloadFile", e);
                            C1049c.m3289b(closeable);
                            C1049c.m3289b(channel);
                            if (!droiError.isOk()) {
                                if (this.f3478e != null) {
                                    this.f3478e.mo1943a(this.f3475b, droiError);
                                    return;
                                }
                                return;
                            }
                            if (this.f3478e != null) {
                                this.f3478e.mo1944a(this.f3475b, this.f3476c);
                            }
                            DroiLog.m2868d("DroiDownloadFile", "Download finished");
                        } catch (Throwable th2) {
                            th = th2;
                            Closeable closeable2 = channel;
                            channel = closeable;
                            closeable = closeable2;
                            C1049c.m3289b(channel);
                            C1049c.m3289b(closeable);
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        try {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage(e.toString());
                            DroiLog.m2873w("DroiDownloadFile", e);
                            C1049c.m3289b(channel);
                            C1049c.m3289b(closeable);
                            if (droiError.isOk()) {
                                if (this.f3478e != null) {
                                    this.f3478e.mo1943a(this.f3475b, droiError);
                                    return;
                                }
                                return;
                            }
                            if (this.f3478e != null) {
                                this.f3478e.mo1944a(this.f3475b, this.f3476c);
                            }
                            DroiLog.m2868d("DroiDownloadFile", "Download finished");
                        } catch (Throwable th3) {
                            th = th3;
                            C1049c.m3289b(channel);
                            C1049c.m3289b(closeable);
                            throw th;
                        }
                    }
                    try {
                        channel2.transferFrom(channel, 0, channel.size());
                        C1049c.m3289b(channel);
                        try {
                            file.delete();
                            C1049c.m3289b(null);
                            C1049c.m3289b(channel2);
                        } catch (Exception e4) {
                            exception = e4;
                            channel = channel2;
                            e = exception;
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage(e.toString());
                            DroiLog.m2873w("DroiDownloadFile", e);
                            C1049c.m3289b(closeable);
                            C1049c.m3289b(channel);
                            if (droiError.isOk()) {
                                if (this.f3478e != null) {
                                    this.f3478e.mo1943a(this.f3475b, droiError);
                                    return;
                                }
                                return;
                            }
                            if (this.f3478e != null) {
                                this.f3478e.mo1944a(this.f3475b, this.f3476c);
                            }
                            DroiLog.m2868d("DroiDownloadFile", "Download finished");
                        } catch (Exception e42) {
                            exception = e42;
                            channel = null;
                            closeable = channel2;
                            e = exception;
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage(e.toString());
                            DroiLog.m2873w("DroiDownloadFile", e);
                            C1049c.m3289b(channel);
                            C1049c.m3289b(closeable);
                            if (droiError.isOk()) {
                                if (this.f3478e != null) {
                                    this.f3478e.mo1943a(this.f3475b, droiError);
                                    return;
                                }
                                return;
                            }
                            if (this.f3478e != null) {
                                this.f3478e.mo1944a(this.f3475b, this.f3476c);
                            }
                            DroiLog.m2868d("DroiDownloadFile", "Download finished");
                        } catch (Throwable th4) {
                            Throwable th5 = th4;
                            channel = null;
                            closeable = channel2;
                            th = th5;
                            C1049c.m3289b(channel);
                            C1049c.m3289b(closeable);
                            throw th;
                        }
                    } catch (Exception e5) {
                        closeable = channel;
                        channel = channel2;
                        e = e5;
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage(e.toString());
                        DroiLog.m2873w("DroiDownloadFile", e);
                        C1049c.m3289b(closeable);
                        C1049c.m3289b(channel);
                        if (droiError.isOk()) {
                            if (this.f3478e != null) {
                                this.f3478e.mo1943a(this.f3475b, droiError);
                                return;
                            }
                            return;
                        }
                        if (this.f3478e != null) {
                            this.f3478e.mo1944a(this.f3475b, this.f3476c);
                        }
                        DroiLog.m2868d("DroiDownloadFile", "Download finished");
                    } catch (Exception e52) {
                        closeable = channel2;
                        e = e52;
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage(e.toString());
                        DroiLog.m2873w("DroiDownloadFile", e);
                        C1049c.m3289b(channel);
                        C1049c.m3289b(closeable);
                        if (droiError.isOk()) {
                            if (this.f3478e != null) {
                                this.f3478e.mo1943a(this.f3475b, droiError);
                                return;
                            }
                            return;
                        }
                        if (this.f3478e != null) {
                            this.f3478e.mo1944a(this.f3475b, this.f3476c);
                        }
                        DroiLog.m2868d("DroiDownloadFile", "Download finished");
                    } catch (Throwable th6) {
                        closeable = channel2;
                        th = th6;
                        C1049c.m3289b(channel);
                        C1049c.m3289b(closeable);
                        throw th;
                    }
                } catch (FileNotFoundException e6) {
                    e = e6;
                    channel = null;
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                    DroiLog.m2873w("DroiDownloadFile", e);
                    C1049c.m3289b(closeable);
                    C1049c.m3289b(channel);
                    if (droiError.isOk()) {
                        if (this.f3478e != null) {
                            this.f3478e.mo1943a(this.f3475b, droiError);
                            return;
                        }
                        return;
                    }
                    if (this.f3478e != null) {
                        this.f3478e.mo1944a(this.f3475b, this.f3476c);
                    }
                    DroiLog.m2868d("DroiDownloadFile", "Download finished");
                } catch (IOException e7) {
                    e = e7;
                    channel = null;
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage(e.toString());
                    DroiLog.m2873w("DroiDownloadFile", e);
                    C1049c.m3289b(channel);
                    C1049c.m3289b(closeable);
                    if (droiError.isOk()) {
                        if (this.f3478e != null) {
                            this.f3478e.mo1943a(this.f3475b, droiError);
                            return;
                        }
                        return;
                    }
                    if (this.f3478e != null) {
                        this.f3478e.mo1944a(this.f3475b, this.f3476c);
                    }
                    DroiLog.m2868d("DroiDownloadFile", "Download finished");
                } catch (Throwable th7) {
                    th = th7;
                    channel = null;
                    C1049c.m3289b(channel);
                    C1049c.m3289b(closeable);
                    throw th;
                }
                if (droiError.isOk()) {
                    if (this.f3478e != null) {
                        this.f3478e.mo1943a(this.f3475b, droiError);
                        return;
                    }
                    return;
                }
            }
            if (this.f3478e != null) {
                this.f3478e.mo1944a(this.f3475b, this.f3476c);
            }
            DroiLog.m2868d("DroiDownloadFile", "Download finished");
        }

        private void m3284b() {
            synchronized (C1049c.f3481b) {
                this.f3474a.f3484e.remove(this.f3479f.hashCode());
            }
        }
    }

    public static C1049c m3286a() {
        if (f3480a != null) {
            return f3480a;
        }
        synchronized (f3481b) {
            if (f3480a != null) {
                C1049c c1049c = f3480a;
                return c1049c;
            }
            f3480a = new C1049c();
            return f3480a;
        }
    }

    public int m3290a(String str, String str2, C1036b c1036b) {
        if (str == null || str2 == null) {
            return 0;
        }
        DroiLog.m2868d("DroiDownloadFile", "<<< Download file task - " + str);
        synchronized (f3481b) {
            if (this.f3484e.get(str.hashCode()) != null) {
                DroiLog.m2868d("DroiDownloadFile", "Task exists. ignored.");
                return 0;
            }
            String enqueueTask = this.f3483d.enqueueTask(new C1048a(this, this.f3482c, str, str2, c1036b, str), str);
            this.f3484e.put(enqueueTask.hashCode(), enqueueTask);
            DroiLog.m2868d("DroiDownloadFile", ">>> Download file task");
            return enqueueTask.hashCode();
        }
    }

    private C1049c() {
        File file = new File(CorePriv.getContext().getApplicationInfo().dataDir + "/droi/downloadcache");
        file.mkdirs();
        this.f3482c = file.getAbsolutePath();
    }

    private static void m3289b(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                DroiLog.m2873w("DroiDownloadFile", e);
            }
        }
    }
}
