package com.zhuoyou.plugin.download;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    private String appName;
    private byte[] bitmap;
    private Context context;
    private int fileSize;
    private AppInfo gameInfo;
    private String localfile;
    private Handler mHandler;
    Cursor mcursor = null;
    private int notification_flag;
    private String size;
    private String urlstr;
    private String version;

    public Downloader(Context context, String urlstr, String localfile, Handler mHandler, int notification_flag, String appName, byte[] bitmap, String size, String version) {
        this.urlstr = urlstr;
        this.localfile = localfile;
        this.mHandler = mHandler;
        this.notification_flag = notification_flag;
        this.appName = appName;
        this.bitmap = bitmap;
        this.size = size;
        this.context = context;
        this.version = version;
    }

    public boolean init() {
        try {
            Log.e("books", this.urlstr + "-urlstr--");
            HttpURLConnection connection = (HttpURLConnection) new URL(this.urlstr).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            int num = 0;
            while (num < 3) {
                this.fileSize = connection.getContentLength();
                Log.e("fileSize", "fileSize---" + this.fileSize);
                num++;
                if (this.fileSize > 0) {
                    break;
                }
            }
            if (this.fileSize <= 0) {
                return false;
            }
            File file = new File(this.localfile);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength((long) this.fileSize);
            accessFile.close();
            connection.disconnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void downlaod() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.zhuoyou.plugin.download.Downloader.downlaod():void. bs: [B:19:0x012a, B:61:0x0238]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r22 = this;
        r15 = 0;
        r0 = r22;
        r9 = r0.fileSize;
        r16 = 0;
        r19 = "sunlei";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "startPos:";
        r20 = r20.append(r21);
        r0 = r20;
        r20 = r0.append(r15);
        r21 = "--endPos:";
        r20 = r20.append(r21);
        r0 = r20;
        r20 = r0.append(r9);
        r20 = r20.toString();
        android.util.Log.e(r19, r20);
        r5 = 0;
        r13 = 0;
        r10 = 0;
        r18 = new java.net.URL;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r0.urlstr;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r18.<init>(r19);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r19 = r18.openConnection();	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r5 = r0;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r19 = 4000; // 0xfa0 float:5.605E-42 double:1.9763E-320;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r5.setConnectTimeout(r0);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r19 = "GET";	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r5.setRequestMethod(r0);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r19 = "Range";	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20.<init>();	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r21 = "bytes=";	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20 = r20.append(r21);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r20;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20 = r0.append(r15);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r21 = "-";	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20 = r20.append(r21);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r20;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20 = r0.append(r9);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20 = r20.toString();	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r1 = r20;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r5.setRequestProperty(r0, r1);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r14 = new java.io.RandomAccessFile;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r0.localfile;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r20 = "rwd";	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r1 = r20;	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r14.<init>(r0, r1);	 Catch:{ SocketTimeoutException -> 0x0281, InterruptedIOException -> 0x0216, Exception -> 0x0235 }
        r0 = (long) r15;
        r20 = r0;
        r0 = r20;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r14.seek(r0);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r10 = r5.getInputStream();	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r4 = new byte[r0];	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r11 = -1;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r6 = 0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x00a1:
        r11 = r10.read(r4);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = -1;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        if (r11 == r0) goto L_0x0146;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x00ab:
        r19 = 0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r14.write(r4, r0, r11);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r6 = r6 + r11;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r9 - r6;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19 + 1;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = (long) r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r16 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r6 * 100;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19 / r9;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = 100;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r1 = r20;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        if (r0 >= r1) goto L_0x00d0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x00c8:
        r19 = r6 * 100;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19 / r9;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19 % 5;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        if (r19 != 0) goto L_0x00a1;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x00d0:
        r12 = android.os.Message.obtain();	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = 2;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r12.what = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.<init>();	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.urlstr;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19.append(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = ",";	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19.append(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r1 = r16;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0.append(r1);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19.toString();	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r12.obj = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.notification_flag;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r12.arg1 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r6 * 100;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r19 / r9;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = 100;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r1 = r20;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        if (r0 < r1) goto L_0x0141;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x0115:
        r19 = 100;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x0117:
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r12.arg2 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.mHandler;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0.sendMessage(r12);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        goto L_0x00a1;
    L_0x0128:
        r7 = move-exception;
        r13 = r14;
    L_0x012a:
        r19 = java.lang.System.out;	 Catch:{ all -> 0x0262 }
        r20 = "sunlei-----the connect is timeout ......please checed the network!!!";	 Catch:{ all -> 0x0262 }
        r19.println(r20);	 Catch:{ all -> 0x0262 }
        if (r10 == 0) goto L_0x0136;
    L_0x0133:
        r10.close();	 Catch:{ Exception -> 0x0210 }
    L_0x0136:
        if (r13 == 0) goto L_0x013b;	 Catch:{ Exception -> 0x0210 }
    L_0x0138:
        r13.close();	 Catch:{ Exception -> 0x0210 }
    L_0x013b:
        if (r5 == 0) goto L_0x0140;	 Catch:{ Exception -> 0x0210 }
    L_0x013d:
        r5.disconnect();	 Catch:{ Exception -> 0x0210 }
    L_0x0140:
        return;
    L_0x0141:
        r19 = r6 * 100;
        r19 = r19 / r9;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        goto L_0x0117;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x0146:
        r19 = new com.zhuoyou.plugin.download.AppInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.<init>();	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r1 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r1.gameInfo = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.appName;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setAppName(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.localfile;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setLocalFile(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.urlstr;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setUrl(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.size;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setSize(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.bitmap;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setBitmap(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.version;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setVersion(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = 2;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setFlag(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.gameInfo;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.context;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.localfile;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r21 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r1 = r20;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r2 = r21;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r20 = r0.getAppPackage(r1, r2);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19.setAppPackageName(r20);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        if (r6 < r9) goto L_0x01f7;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x01d7:
        r12 = new android.os.Message;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r12.<init>();	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = 3;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r12.what = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.urlstr;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r12.obj = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r22;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r0.mHandler;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r19 = r0;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0 = r19;	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
        r0.sendMessage(r12);	 Catch:{ SocketTimeoutException -> 0x0128, InterruptedIOException -> 0x027e, Exception -> 0x027b, all -> 0x0278 }
    L_0x01f7:
        if (r10 == 0) goto L_0x01fc;
    L_0x01f9:
        r10.close();	 Catch:{ Exception -> 0x0209 }
    L_0x01fc:
        if (r14 == 0) goto L_0x0201;	 Catch:{ Exception -> 0x0209 }
    L_0x01fe:
        r14.close();	 Catch:{ Exception -> 0x0209 }
    L_0x0201:
        if (r5 == 0) goto L_0x0206;	 Catch:{ Exception -> 0x0209 }
    L_0x0203:
        r5.disconnect();	 Catch:{ Exception -> 0x0209 }
    L_0x0206:
        r13 = r14;
        goto L_0x0140;
    L_0x0209:
        r7 = move-exception;
        r7.printStackTrace();
        r13 = r14;
        goto L_0x0140;
    L_0x0210:
        r7 = move-exception;
        r7.printStackTrace();
        goto L_0x0140;
    L_0x0216:
        r7 = move-exception;
    L_0x0217:
        r19 = java.lang.System.out;	 Catch:{ all -> 0x0262 }
        r20 = "sunlei-----the InterruptedIOException ......please checed the network!!!";	 Catch:{ all -> 0x0262 }
        r19.println(r20);	 Catch:{ all -> 0x0262 }
        if (r10 == 0) goto L_0x0223;
    L_0x0220:
        r10.close();	 Catch:{ Exception -> 0x022f }
    L_0x0223:
        if (r13 == 0) goto L_0x0228;	 Catch:{ Exception -> 0x022f }
    L_0x0225:
        r13.close();	 Catch:{ Exception -> 0x022f }
    L_0x0228:
        if (r5 == 0) goto L_0x0140;	 Catch:{ Exception -> 0x022f }
    L_0x022a:
        r5.disconnect();	 Catch:{ Exception -> 0x022f }
        goto L_0x0140;
    L_0x022f:
        r7 = move-exception;
        r7.printStackTrace();
        goto L_0x0140;
    L_0x0235:
        r7 = move-exception;
    L_0x0236:
        if (r10 == 0) goto L_0x023b;
    L_0x0238:
        r10.close();	 Catch:{ IOException -> 0x025d }
    L_0x023b:
        if (r13 == 0) goto L_0x0240;	 Catch:{ IOException -> 0x025d }
    L_0x023d:
        r13.close();	 Catch:{ IOException -> 0x025d }
    L_0x0240:
        r5.disconnect();	 Catch:{ all -> 0x0262 }
        r7.printStackTrace();	 Catch:{ all -> 0x0262 }
        if (r10 == 0) goto L_0x024b;
    L_0x0248:
        r10.close();	 Catch:{ Exception -> 0x0257 }
    L_0x024b:
        if (r13 == 0) goto L_0x0250;	 Catch:{ Exception -> 0x0257 }
    L_0x024d:
        r13.close();	 Catch:{ Exception -> 0x0257 }
    L_0x0250:
        if (r5 == 0) goto L_0x0140;	 Catch:{ Exception -> 0x0257 }
    L_0x0252:
        r5.disconnect();	 Catch:{ Exception -> 0x0257 }
        goto L_0x0140;
    L_0x0257:
        r7 = move-exception;
        r7.printStackTrace();
        goto L_0x0140;
    L_0x025d:
        r8 = move-exception;
        r8.printStackTrace();	 Catch:{ all -> 0x0262 }
        goto L_0x0240;
    L_0x0262:
        r19 = move-exception;
    L_0x0263:
        if (r10 == 0) goto L_0x0268;
    L_0x0265:
        r10.close();	 Catch:{ Exception -> 0x0273 }
    L_0x0268:
        if (r13 == 0) goto L_0x026d;	 Catch:{ Exception -> 0x0273 }
    L_0x026a:
        r13.close();	 Catch:{ Exception -> 0x0273 }
    L_0x026d:
        if (r5 == 0) goto L_0x0272;	 Catch:{ Exception -> 0x0273 }
    L_0x026f:
        r5.disconnect();	 Catch:{ Exception -> 0x0273 }
    L_0x0272:
        throw r19;
    L_0x0273:
        r7 = move-exception;
        r7.printStackTrace();
        goto L_0x0272;
    L_0x0278:
        r19 = move-exception;
        r13 = r14;
        goto L_0x0263;
    L_0x027b:
        r7 = move-exception;
        r13 = r14;
        goto L_0x0236;
    L_0x027e:
        r7 = move-exception;
        r13 = r14;
        goto L_0x0217;
    L_0x0281:
        r7 = move-exception;
        goto L_0x012a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.download.Downloader.downlaod():void");
    }

    public String getUrlstr() {
        return this.urlstr;
    }

    public void setUrlstr(String urlstr) {
        this.urlstr = urlstr;
    }

    public String getLocalfile() {
        return this.localfile;
    }

    public void setLocalfile(String localfile) {
        this.localfile = localfile;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public Handler getmHandler() {
        return this.mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public int getNotification_flag() {
        return this.notification_flag;
    }

    public void setNotification_flag(int notification_flag) {
        this.notification_flag = notification_flag;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackage(Context context, String archiveFilePath) {
        PackageInfo info = context.getPackageManager().getPackageArchiveInfo(archiveFilePath, 1);
        if (info != null) {
            return info.applicationInfo.packageName;
        }
        return null;
    }
}
