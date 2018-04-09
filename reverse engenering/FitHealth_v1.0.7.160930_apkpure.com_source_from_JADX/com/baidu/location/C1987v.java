package com.baidu.location;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public class C1987v implements UncaughtExceptionHandler, an, C1619j {
    C1987v(Context context) {
        aS();
    }

    private String m6083if(Throwable th) {
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    public void aS() {
        String str = null;
        try {
            File file = new File((Environment.getExternalStorageDirectory().getPath() + "/traces") + "/error_fs.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(280);
                if (1326 == randomAccessFile.readInt()) {
                    String str2;
                    byte[] bArr;
                    randomAccessFile.seek(308);
                    int readInt = randomAccessFile.readInt();
                    if (readInt <= 0 || readInt >= 2048) {
                        str2 = null;
                    } else {
                        bArr = new byte[readInt];
                        randomAccessFile.read(bArr, 0, readInt);
                        str2 = new String(bArr, 0, readInt);
                    }
                    randomAccessFile.seek(600);
                    readInt = randomAccessFile.readInt();
                    if (readInt > 0 && readInt < 2048) {
                        bArr = new byte[readInt];
                        randomAccessFile.read(bArr, 0, readInt);
                        str = new String(bArr, 0, readInt);
                    }
                    if (m6084for(str2, str)) {
                        randomAccessFile.seek(280);
                        randomAccessFile.writeInt(12346);
                    }
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    boolean m6084for(String str, String str2) {
        if (!ai.bf()) {
            return false;
        }
        try {
            HttpUriRequest httpPost = new HttpPost(C1974b.f5426W);
            List arrayList = new ArrayList();
            arrayList.add(new BasicNameValuePair("e0", str));
            arrayList.add(new BasicNameValuePair("e1", str2));
            httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "utf-8"));
            HttpClient defaultHttpClient = new DefaultHttpClient();
            defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.valueOf(an.f2195I));
            defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Integer.valueOf(an.f2195I));
            return defaultHttpClient.execute(httpPost).getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public void m6085if(File file, String str, String str2) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(280);
            randomAccessFile.writeInt(12346);
            randomAccessFile.seek(300);
            randomAccessFile.writeLong(System.currentTimeMillis());
            byte[] bytes = str.getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            randomAccessFile.seek(600);
            bytes = str2.getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            if (!m6084for(str, str2)) {
                randomAccessFile.seek(280);
                randomAccessFile.writeInt(1326);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String str;
        String f;
        Object obj;
        File file;
        String str2;
        RandomAccessFile randomAccessFile;
        File file2;
        File file3 = null;
        if (C1985t.e0) {
            try {
                str = m6083if(th);
                try {
                    String str3 = ap.bD().m5891try(false) + C1977g.m5942g().m5949f();
                    f = str3 != null ? Jni.m5811f(str3) : null;
                } catch (Exception e) {
                    Object obj2 = str;
                    obj = file;
                    f = null;
                    str2 = Environment.getExternalStorageDirectory().getPath() + "/traces";
                    file = new File(str2 + "/error_fs.dat");
                    if (file.exists()) {
                        randomAccessFile = new RandomAccessFile(file, "rw");
                        randomAccessFile.seek(300);
                        if (System.currentTimeMillis() - randomAccessFile.readLong() > 604800000) {
                            m6085if(file, f, str);
                        }
                        randomAccessFile.close();
                    } else {
                        file2 = new File(str2);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                        if (file.createNewFile()) {
                            file3 = file;
                        }
                        m6085if(file3, f, str);
                    }
                    Process.killProcess(Process.myPid());
                    return;
                }
            } catch (Exception e2) {
                file = null;
                obj = file;
                f = null;
                str2 = Environment.getExternalStorageDirectory().getPath() + "/traces";
                file = new File(str2 + "/error_fs.dat");
                if (file.exists()) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(300);
                    if (System.currentTimeMillis() - randomAccessFile.readLong() > 604800000) {
                        m6085if(file, f, str);
                    }
                    randomAccessFile.close();
                } else {
                    file2 = new File(str2);
                    if (file2.exists()) {
                        file2.mkdirs();
                    }
                    if (file.createNewFile()) {
                        file3 = file;
                    }
                    m6085if(file3, f, str);
                }
                Process.killProcess(Process.myPid());
                return;
            }
            try {
                str2 = Environment.getExternalStorageDirectory().getPath() + "/traces";
                file = new File(str2 + "/error_fs.dat");
                if (file.exists()) {
                    file2 = new File(str2);
                    if (file2.exists()) {
                        file2.mkdirs();
                    }
                    if (file.createNewFile()) {
                        file3 = file;
                    }
                    m6085if(file3, f, str);
                } else {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(300);
                    if (System.currentTimeMillis() - randomAccessFile.readLong() > 604800000) {
                        m6085if(file, f, str);
                    }
                    randomAccessFile.close();
                }
            } catch (Exception e3) {
            }
            Process.killProcess(Process.myPid());
            return;
        }
        Process.killProcess(Process.myPid());
    }
}
