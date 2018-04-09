package com.zhuoyou.plugin.running.tools;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class LogCatHelper {
    private static final String[] TAG_LIST = new String[]{"chenxin", "zhuqichao", "hph", "yuanzi"};
    private static LogCatHelper instance = null;
    private int appid = Process.myPid();
    private String dirPath;
    private Thread logThread;

    @SuppressLint({"SimpleDateFormat"})
    private static class FormatDate {
        private FormatDate() {
        }

        public static String getFormatDate() {
            return new SimpleDateFormat("yyyyMMddHH").format(Long.valueOf(System.currentTimeMillis()));
        }

        public static String getFormatTime() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(System.currentTimeMillis()));
        }
    }

    private static class LogRunnable implements Runnable {
        private String cmds;
        private FileOutputStream fos;
        private String mPid;
        private Process mProcess;
        private BufferedReader mReader;

        public void run() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0075 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r6 = 0;
            r3 = java.lang.Runtime.getRuntime();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = r7.cmds;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r3 = r3.exec(r4);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r7.mProcess = r3;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r3 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r5 = r7.mProcess;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r5 = r5.getInputStream();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4.<init>(r5);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r5 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r3.<init>(r4, r5);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r7.mReader = r3;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
        L_0x0021:
            r3 = r7.mReader;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r2 = r3.readLine();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            if (r2 == 0) goto L_0x008e;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
        L_0x0029:
            r3 = r2.length();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            if (r3 == 0) goto L_0x0021;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
        L_0x002f:
            r3 = r7.fos;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            if (r3 == 0) goto L_0x0021;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
        L_0x0033:
            r3 = r7.mPid;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r3 = r2.contains(r3);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            if (r3 == 0) goto L_0x0021;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
        L_0x003b:
            r3 = r7.fos;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4.<init>();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r5 = com.zhuoyou.plugin.running.tools.LogCatHelper.FormatDate.getFormatTime();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r5 = " ";	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = r4.append(r2);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r5 = "\r\n";	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = r4.toString();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r4 = r4.getBytes();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r3.write(r4);	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            goto L_0x0021;
        L_0x0066:
            r0 = move-exception;
            r0.printStackTrace();	 Catch:{ Exception -> 0x0066, all -> 0x00bc }
            r3 = r7.mProcess;
            if (r3 == 0) goto L_0x0075;
        L_0x006e:
            r3 = r7.mProcess;
            r3.destroy();
            r7.mProcess = r6;
        L_0x0075:
            r3 = r7.mReader;	 Catch:{ Exception -> 0x00b7 }
            if (r3 == 0) goto L_0x0081;	 Catch:{ Exception -> 0x00b7 }
        L_0x0079:
            r3 = r7.mReader;	 Catch:{ Exception -> 0x00b7 }
            r3.close();	 Catch:{ Exception -> 0x00b7 }
            r3 = 0;	 Catch:{ Exception -> 0x00b7 }
            r7.mReader = r3;	 Catch:{ Exception -> 0x00b7 }
        L_0x0081:
            r3 = r7.fos;	 Catch:{ Exception -> 0x00b7 }
            if (r3 == 0) goto L_0x008d;	 Catch:{ Exception -> 0x00b7 }
        L_0x0085:
            r3 = r7.fos;	 Catch:{ Exception -> 0x00b7 }
            r3.close();	 Catch:{ Exception -> 0x00b7 }
            r3 = 0;	 Catch:{ Exception -> 0x00b7 }
            r7.fos = r3;	 Catch:{ Exception -> 0x00b7 }
        L_0x008d:
            return;
        L_0x008e:
            r3 = r7.mProcess;
            if (r3 == 0) goto L_0x0099;
        L_0x0092:
            r3 = r7.mProcess;
            r3.destroy();
            r7.mProcess = r6;
        L_0x0099:
            r3 = r7.mReader;	 Catch:{ Exception -> 0x00b2 }
            if (r3 == 0) goto L_0x00a5;	 Catch:{ Exception -> 0x00b2 }
        L_0x009d:
            r3 = r7.mReader;	 Catch:{ Exception -> 0x00b2 }
            r3.close();	 Catch:{ Exception -> 0x00b2 }
            r3 = 0;	 Catch:{ Exception -> 0x00b2 }
            r7.mReader = r3;	 Catch:{ Exception -> 0x00b2 }
        L_0x00a5:
            r3 = r7.fos;	 Catch:{ Exception -> 0x00b2 }
            if (r3 == 0) goto L_0x008d;	 Catch:{ Exception -> 0x00b2 }
        L_0x00a9:
            r3 = r7.fos;	 Catch:{ Exception -> 0x00b2 }
            r3.close();	 Catch:{ Exception -> 0x00b2 }
            r3 = 0;	 Catch:{ Exception -> 0x00b2 }
            r7.fos = r3;	 Catch:{ Exception -> 0x00b2 }
            goto L_0x008d;
        L_0x00b2:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x008d;
        L_0x00b7:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x008d;
        L_0x00bc:
            r3 = move-exception;
            r4 = r7.mProcess;
            if (r4 == 0) goto L_0x00c8;
        L_0x00c1:
            r4 = r7.mProcess;
            r4.destroy();
            r7.mProcess = r6;
        L_0x00c8:
            r4 = r7.mReader;	 Catch:{ Exception -> 0x00e1 }
            if (r4 == 0) goto L_0x00d4;	 Catch:{ Exception -> 0x00e1 }
        L_0x00cc:
            r4 = r7.mReader;	 Catch:{ Exception -> 0x00e1 }
            r4.close();	 Catch:{ Exception -> 0x00e1 }
            r4 = 0;	 Catch:{ Exception -> 0x00e1 }
            r7.mReader = r4;	 Catch:{ Exception -> 0x00e1 }
        L_0x00d4:
            r4 = r7.fos;	 Catch:{ Exception -> 0x00e1 }
            if (r4 == 0) goto L_0x00e0;	 Catch:{ Exception -> 0x00e1 }
        L_0x00d8:
            r4 = r7.fos;	 Catch:{ Exception -> 0x00e1 }
            r4.close();	 Catch:{ Exception -> 0x00e1 }
            r4 = 0;	 Catch:{ Exception -> 0x00e1 }
            r7.fos = r4;	 Catch:{ Exception -> 0x00e1 }
        L_0x00e0:
            throw r3;
        L_0x00e1:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x00e0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.tools.LogCatHelper.LogRunnable.run():void");
        }

        public LogRunnable(int pid, String dirPath) {
            this.mPid = "" + pid;
            try {
                File file = new File(dirPath, FormatDate.getFormatDate() + ".log");
                if (!file.exists()) {
                    file.createNewFile();
                }
                this.fos = new FileOutputStream(file, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String tags = "";
            for (String tag : LogCatHelper.TAG_LIST) {
                tags = tags + tag + " ";
            }
            if (TextUtils.isEmpty(tags)) {
                this.cmds = "logcat *:v | grep \"(" + this.mPid + ")\"";
            } else {
                this.cmds = "logcat -s " + tags + " | grep \"(" + this.mPid + ")\"";
            }
            Log.i("chenxin", "cmds:" + this.cmds);
        }
    }

    public static LogCatHelper getInstance(String path) {
        if (instance == null) {
            instance = new LogCatHelper(path);
        }
        return instance;
    }

    private LogCatHelper(String path) {
        if (TextUtils.isEmpty(path)) {
            this.dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DroiHealth" + File.separator + "Log";
        } else {
            this.dirPath = path;
        }
        File dir = new File(this.dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void start() {
        if (this.logThread == null) {
            this.logThread = new Thread(new LogRunnable(this.appid, this.dirPath));
        }
        this.logThread.start();
    }
}
