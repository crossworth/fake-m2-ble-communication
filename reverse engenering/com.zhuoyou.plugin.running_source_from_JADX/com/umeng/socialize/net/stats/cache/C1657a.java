package com.umeng.socialize.net.stats.cache;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: AtomicFile */
public class C1657a {
    private final File f4979a;
    private final File f4980b;

    public C1657a(File file) {
        this.f4979a = file;
        this.f4980b = new File(file.getPath() + ".bak");
    }

    public File m4509a() {
        return this.f4979a;
    }

    public void m4512b() {
        this.f4979a.delete();
        this.f4980b.delete();
    }

    public FileOutputStream m4510a(boolean z) throws IOException {
        if (this.f4979a.exists()) {
            if (this.f4980b.exists()) {
                this.f4979a.delete();
            } else if (this.f4979a.renameTo(this.f4980b)) {
                C1657a.m4507a(this.f4980b, this.f4979a);
            } else {
                Log.w("AtomicFile", "Couldn't rename file " + this.f4979a + " to backup file " + this.f4980b);
            }
        }
        try {
            return new FileOutputStream(this.f4979a, z);
        } catch (FileNotFoundException e) {
            if (this.f4979a.getParentFile().mkdirs()) {
                try {
                    return new FileOutputStream(this.f4979a, z);
                } catch (FileNotFoundException e2) {
                    throw new IOException("Couldn't create " + this.f4979a);
                }
            }
            throw new IOException("Couldn't create directory " + this.f4979a);
        }
    }

    private static void m4507a(File file, File file2) throws IOException {
        OutputStream fileOutputStream;
        Throwable th;
        InputStream inputStream = null;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            InputStream fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                inputStream = fileInputStream;
                inputStream.close();
                fileOutputStream.close();
                throw th;
            }
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                        Log.d("AtomicFile", read + "");
                    } else {
                        fileInputStream.close();
                        fileOutputStream.close();
                        Log.d("AtomicFile", "comsum time:" + (System.currentTimeMillis() - currentTimeMillis));
                        return;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream = fileInputStream;
                inputStream.close();
                fileOutputStream.close();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            inputStream.close();
            fileOutputStream.close();
            throw th;
        }
    }

    public void m4511a(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            C1657a.m4508c(fileOutputStream);
            try {
                fileOutputStream.close();
                this.f4980b.delete();
            } catch (Throwable e) {
                Log.w("AtomicFile", "finishWrite: Got exception:", e);
            }
        }
    }

    public void m4513b(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            C1657a.m4508c(fileOutputStream);
            try {
                fileOutputStream.close();
                this.f4979a.delete();
                this.f4980b.renameTo(this.f4979a);
            } catch (Throwable e) {
                Log.w("AtomicFile", "failWrite: Got exception:", e);
            }
        }
    }

    public FileInputStream m4514c() throws FileNotFoundException {
        if (this.f4980b.exists()) {
            this.f4979a.delete();
            this.f4980b.renameTo(this.f4979a);
        }
        return new FileInputStream(this.f4979a);
    }

    public byte[] m4515d() throws IOException {
        int i = 0;
        FileInputStream c = m4514c();
        try {
            Object obj = new byte[c.available()];
            while (true) {
                int read = c.read(obj, i, obj.length - i);
                if (read <= 0) {
                    break;
                }
                Object obj2;
                read += i;
                i = c.available();
                if (i > obj.length - read) {
                    obj2 = new byte[(i + read)];
                    System.arraycopy(obj, 0, obj2, 0, read);
                } else {
                    obj2 = obj;
                }
                obj = obj2;
                i = read;
            }
            return obj;
        } finally {
            c.close();
        }
    }

    static boolean m4508c(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getFD().sync();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
