package com.droi.sdk.core;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.DroiProgressCallback;
import com.droi.sdk.core.DroiHttpRequest.C0828e;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.core.priv.C0939m.C0919e;
import com.droi.sdk.core.priv.C0939m.C0920f;
import com.droi.sdk.core.priv.C0939m.C0921g;
import com.droi.sdk.core.priv.C0939m.C0922h;
import com.droi.sdk.core.priv.C0939m.C0923i;
import com.droi.sdk.core.priv.C0939m.C0925k;
import com.droi.sdk.core.priv.C0939m.C0926l;
import com.droi.sdk.core.priv.C0939m.C0937w;
import com.droi.sdk.core.priv.C0940n;
import com.droi.sdk.core.priv.C0944p;
import com.droi.sdk.core.priv.DroiStorageFinder;
import com.droi.sdk.core.priv.FileDescriptorHelper;
import com.droi.sdk.core.priv.TaskDispatcherPool;
import com.droi.sdk.internal.DroiLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

@DroiObjectName("_File")
public final class DroiFile extends DroiObject {
    public static final Creator<DroiFile> CREATOR = new Creator<DroiFile>() {
        public DroiFile createFromParcel(Parcel parcel) {
            DroiObject droiObject = (DroiObject) DroiObject.CREATOR.createFromParcel(parcel);
            if (droiObject instanceof DroiFile) {
                DroiFile droiFile = (DroiFile) droiObject;
                String readString = parcel.readString();
                if (readString != null) {
                    droiFile.file = new File(readString);
                }
                droiFile.mimeType = parcel.readString();
                droiFile.isContentDirty = parcel.readInt() != 0;
                int readInt = parcel.readInt();
                if (readInt <= 0) {
                    return droiFile;
                }
                droiFile.data = new byte[readInt];
                parcel.readByteArray(droiFile.data);
                return droiFile;
            }
            DroiLog.m2870e(DroiFile.LOG_TAG, "Can not create DroiFile from parcel. Type mismatch.");
            return null;
        }

        public DroiFile[] newArray(int i) {
            return new DroiFile[i];
        }
    };
    private static final byte[] KEY = new byte[]{(byte) 90, (byte) 90, (byte) 60, (byte) 12, (byte) 34, (byte) 56, (byte) 78, (byte) 90};
    private static final String LOG_TAG = "DROI_FILE";
    private static final int MAX_AVAILABLE_FILE_SIZE = 14680064;
    private static TaskDispatcherPool TASK_POOL = new TaskDispatcherPool(0, 3);
    @DroiExpose
    private String Fid;
    @DroiExpose
    private String MD5;
    @DroiExpose
    private String Name;
    @DroiExpose
    private int Size;
    private byte[] data;
    private File file;
    private boolean isContentDirty;
    private String mimeType;
    private DroiProgressCallback saveProcessCallback;

    private class DroiFileLoadRunnable implements Runnable {
        final /* synthetic */ DroiFile f2507a;
        private DroiCallback<byte[]> callback;
        private String cdn;
        private DroiError error;
        private final String fid;
        private final boolean localStorage;
        private final Object locker = new Object();
        private String name;
        private boolean needDownload;
        private DroiProgressCallback progressCallback;
        private byte[] result;

        public DroiFileLoadRunnable(DroiFile droiFile, String str, String str2, boolean z, DroiCallback<byte[]> droiCallback, DroiProgressCallback droiProgressCallback, boolean z2) {
            this.f2507a = droiFile;
            this.fid = str;
            this.name = str2;
            this.localStorage = z;
            this.callback = droiCallback;
            this.progressCallback = droiProgressCallback;
            this.error = new DroiError();
            this.needDownload = z2;
        }

        private DroiError m2468a(File file, long j, String str) throws IOException {
            Closeable c0923i;
            DroiError droiError = new DroiError();
            droiError.setCode(DroiError.ERROR);
            C0940n a = C0940n.m2775a(null, droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
            try {
                c0923i = new C0923i(a, file);
                c0923i.f2997c = str;
                c0923i.f2998d = 0;
                long j2 = 0;
                while (c0923i.m2727a(droiError)) {
                    j2 += (long) a.f3069d;
                    if (j2 > j) {
                        j2 = j;
                    }
                    if (this.progressCallback != null) {
                        this.progressCallback.progress(this.f2507a, j2, j);
                    }
                }
                if (c0923i.f2999e <= 0 || c0923i.f2998d < c0923i.f2999e) {
                    FileDescriptorHelper.closeQuietly(c0923i);
                    return droiError;
                }
                droiError.setCode(0);
                droiError.setAppendedMessage(null);
                FileDescriptorHelper.closeQuietly(c0923i);
                return droiError;
            } catch (Exception e) {
                droiError.setAppendedMessage(e.toString());
                DroiLog.m2873w(DroiFile.LOG_TAG, e);
            } catch (Throwable th) {
                FileDescriptorHelper.closeQuietly(c0923i);
            }
        }

        private byte[] m2469a(DroiError droiError) throws IOException {
            byte[] readFile;
            File file;
            Throwable th;
            byte[] bArr = null;
            if (droiError == null) {
                droiError = new DroiError();
            }
            File a;
            if (this.localStorage) {
                a = DroiFile.m2494d(this.name, this.fid);
                if (!a.exists()) {
                    File b = DroiFile.m2492c(this.name, this.fid);
                    if (!b.exists()) {
                        if (droiError != null) {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage("cache not found");
                        }
                        a = null;
                    } else if (!b.renameTo(a)) {
                        if (droiError != null) {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage("can not move v1 to v2 cache");
                        }
                        return null;
                    }
                }
                if (a == null) {
                    return null;
                }
                readFile = FileDescriptorHelper.readFile(a);
                if (!C0944p.m2786a(FileDescriptorHelper.calculateHash(readFile)).equals(this.f2507a.MD5)) {
                    DroiLog.m2870e(DroiFile.LOG_TAG, "md5 check fail.");
                    if (droiError != null) {
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage("md5 check fail.");
                    }
                    readFile = null;
                    a = null;
                }
                if (a == null) {
                    return null;
                }
                this.cdn = a.toString();
                file = a;
            } else {
                File a2 = DroiFile.m2494d(this.name, this.fid);
                if (a2.exists()) {
                    bArr = FileDescriptorHelper.readFile(a2);
                } else {
                    a = DroiFile.m2492c(this.name, this.fid);
                    if (a.exists()) {
                        if (a.renameTo(a2)) {
                            bArr = FileDescriptorHelper.readFile(a2);
                        } else {
                            DroiLog.m2874w(DroiFile.LOG_TAG, "can not rename v1 to v2 cache.");
                        }
                    }
                }
                if (bArr == null || bArr.length <= 0 || C0944p.m2786a(FileDescriptorHelper.calculateHash(bArr)).equals(this.f2507a.MD5)) {
                    readFile = bArr;
                    a = a2;
                } else {
                    DroiLog.m2874w(DroiFile.LOG_TAG, "local cache md5 is not match.");
                    a2.delete();
                    readFile = null;
                    a = null;
                }
                if (!(this.needDownload && r2 != null && r2.exists())) {
                    C0926l b2 = m2470b(droiError);
                    if (!droiError.isOk()) {
                        return null;
                    }
                    Map map = (Map) b2.f3017d.get(0);
                    int intValue = ((Integer) map.get("Status")).intValue();
                    String str = (String) map.get("CDN");
                    int intValue2 = ((Integer) map.get("Size")).intValue();
                    if (str != null && str.length() > 0) {
                        this.cdn = str;
                        if (this.needDownload) {
                            OkHttpClient a3 = C0828e.m2524a();
                            Builder builder = new Builder();
                            builder.url(str).get();
                            Response execute = a3.newCall(builder.build()).execute();
                            if (execute.code() != 200) {
                                droiError.setCode(DroiError.ERROR);
                                droiError.setAppendedMessage("Download fail. http status: " + execute.code());
                                return null;
                            }
                            ResponseBody body = execute.body();
                            try {
                                InputStream byteStream = body.byteStream();
                                if (this.progressCallback != null) {
                                    this.progressCallback.progress(this.f2507a, 0, (long) intValue2);
                                }
                                file = DroiFile.m2494d(this.name, this.fid);
                                Closeable closeable = null;
                                try {
                                    Closeable fileOutputStream = new FileOutputStream(file);
                                    try {
                                        byte[] bArr2 = new byte[8192];
                                        int i = 0;
                                        while (true) {
                                            int read = byteStream.read(bArr2);
                                            if (read <= 0) {
                                                break;
                                            }
                                            fileOutputStream.write(bArr2, 0, read);
                                            int i2 = i + read;
                                            if (this.progressCallback != null) {
                                                this.progressCallback.progress(this.f2507a, (long) i2, (long) intValue2);
                                                i = i2;
                                            } else {
                                                i = i2;
                                            }
                                        }
                                        FileDescriptorHelper.closeQuietly(fileOutputStream);
                                        a = file;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        closeable = fileOutputStream;
                                        FileDescriptorHelper.closeQuietly(closeable);
                                        throw th;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    FileDescriptorHelper.closeQuietly(closeable);
                                    throw th;
                                }
                            } finally {
                                if (body != null) {
                                    body.close();
                                }
                            }
                        }
                    } else if (intValue != 2) {
                        if (intValue == 1 || intValue == 3) {
                            droiError.setCode(DroiError.FILE_NOT_READY);
                        } else if (intValue == 6) {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage("Remote file corrupted. Please upload again.");
                        } else {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage("Download fail. status: " + intValue);
                        }
                        return null;
                    }
                    file = a;
                }
                file = a;
            }
            if (!this.needDownload) {
                droiError.setCode(this.cdn != null ? 0 : DroiError.ERROR);
                droiError.setAppendedMessage(null);
                return null;
            } else if (file == null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("Local cache not found.");
                return null;
            } else {
                int length = (int) file.length();
                if (this.localStorage && this.progressCallback != null) {
                    this.progressCallback.progress(this.f2507a, 0, (long) length);
                }
                if (readFile == null) {
                    readFile = FileDescriptorHelper.readFile(file);
                    if (!C0944p.m2786a(C0944p.m2800d(readFile)).equals(this.f2507a.MD5)) {
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage("The file checksum is wrong.");
                        readFile = null;
                    }
                }
                if (!this.localStorage || this.progressCallback == null) {
                    return readFile;
                }
                this.progressCallback.progress(this.f2507a, (long) length, (long) length);
                return readFile;
            }
        }

        private C0926l m2470b(DroiError droiError) {
            if (droiError == null) {
                droiError = new DroiError();
            }
            C0940n a = C0940n.m2775a(null, droiError);
            if (!droiError.isOk()) {
                return null;
            }
            C0925k c0925k = new C0925k();
            c0925k.f3013b = this.fid;
            c0925k.f3012a = a.f3066a;
            return C0939m.m2760a(c0925k, droiError);
        }

        public void enqueueAsync() {
            DroiFile.TASK_POOL.enqueueTask(this);
        }

        public void enqueueSync() {
            synchronized (this.locker) {
                DroiFile.TASK_POOL.enqueueTask(this);
                try {
                    this.locker.wait();
                } catch (Exception e) {
                    DroiLog.m2873w(DroiFile.LOG_TAG, e);
                }
            }
        }

        public void run() {
            this.result = null;
            this.error.setCode(0);
            this.error.setAppendedMessage(null);
            try {
                this.result = m2469a(this.error);
                synchronized (this.locker) {
                    this.locker.notifyAll();
                }
            } catch (Exception e) {
                this.result = null;
                this.error.setCode(DroiError.ERROR);
                this.error.setAppendedMessage(e.toString());
                DroiLog.m2873w(DroiFile.LOG_TAG, e);
                this.result = null;
                synchronized (this.locker) {
                    this.locker.notifyAll();
                }
            } catch (Throwable th) {
                synchronized (this.locker) {
                    this.locker.notifyAll();
                }
            }
            if (this.callback != null) {
                this.callback.result(this.result, this.error);
            }
        }
    }

    private abstract class DroiFileSaveRunnable implements Runnable {
        protected Object f2509a = new Object();
        protected DroiError f2510b = new DroiError(DroiError.UNKNOWN_ERROR, "Unexpected error.");
        protected String f2511c;
        protected DroiCallback<Boolean> f2512d;
        protected TaskDispatcher f2513e = TaskDispatcher.currentTaskDispatcher();
        final /* synthetic */ DroiFile f2514f;
        private String name;

        class C08151 implements Runnable {
            final /* synthetic */ DroiFileSaveRunnable f2508a;

            C08151(DroiFileSaveRunnable droiFileSaveRunnable) {
                this.f2508a = droiFileSaveRunnable;
            }

            public void run() {
                if (this.f2508a.f2514f.isLocalStorage()) {
                    this.f2508a.f2514f.Fid = this.f2508a.f2511c;
                }
                DroiCallback droiCallback = this.f2508a.f2512d;
                boolean z = this.f2508a.f2511c != null && this.f2508a.f2510b.isOk();
                droiCallback.result(Boolean.valueOf(z), this.f2508a.f2510b);
            }
        }

        public DroiFileSaveRunnable(DroiFile droiFile) {
            this.f2514f = droiFile;
        }

        protected abstract String mo1889a(DroiError droiError) throws IOException;

        public String enqueueAsync() {
            return DroiFile.TASK_POOL.enqueueTask(this);
        }

        public void enqueueSync() {
            if (DroiFile.TASK_POOL.enqueueTask(this) != null) {
                synchronized (this.f2509a) {
                    try {
                        this.f2509a.wait();
                    } catch (Exception e) {
                        DroiLog.m2873w(DroiFile.LOG_TAG, e);
                    }
                }
            }
        }

        public String getName() {
            return this.name;
        }

        public void run() {
            this.f2511c = null;
            this.f2510b.setCode(0);
            this.f2510b.setAppendedMessage(null);
            try {
                this.f2511c = mo1889a(this.f2510b);
                synchronized (this.f2509a) {
                    this.f2509a.notifyAll();
                }
            } catch (Exception e) {
                this.f2511c = null;
                this.f2510b.setCode(DroiError.ERROR);
                this.f2510b.setAppendedMessage(e.toString());
                DroiLog.m2873w(DroiFile.LOG_TAG, e);
                synchronized (this.f2509a) {
                    this.f2509a.notifyAll();
                }
            } catch (Throwable th) {
                synchronized (this.f2509a) {
                    this.f2509a.notifyAll();
                }
            }
            if (this.f2512d != null) {
                this.f2513e.enqueueTask(new C08151(this));
            }
        }

        public void setCallback(DroiCallback<Boolean> droiCallback) {
            this.f2512d = droiCallback;
        }

        public void setName(String str) {
            this.name = str;
        }
    }

    public interface DroiGetUrlWithFlagCallback<Uri> {
        void result(Uri uri, boolean z, DroiError droiError);
    }

    private class FileTransformSaveRunnable extends DroiFileSaveRunnable {
        private final byte[] dataFrom;
        private final File fileFrom;
        final /* synthetic */ DroiFile f2515g;
        private String id;
        private final boolean localStorage;
        private final String mimeType;
        private Date modified;
        private final String name;

        public FileTransformSaveRunnable(DroiFile droiFile, File file, String str, boolean z, String str2) {
            this.f2515g = droiFile;
            super(droiFile);
            this.name = str;
            this.fileFrom = file;
            this.dataFrom = null;
            this.mimeType = str2;
            this.localStorage = z;
        }

        public FileTransformSaveRunnable(DroiFile droiFile, byte[] bArr, String str, boolean z, String str2) {
            this.f2515g = droiFile;
            super(droiFile);
            this.name = str;
            this.fileFrom = null;
            this.dataFrom = bArr;
            this.mimeType = str2;
            this.localStorage = z;
        }

        private void m2474a(File file, byte[] bArr, String str, String str2) throws IOException {
            File a = DroiFile.m2494d(str, str2);
            if (file != null) {
                FileDescriptorHelper.copy(file, a, null);
            } else {
                FileDescriptorHelper.copy(bArr, a, null);
            }
        }

        private String m2475b(DroiError droiError) throws IOException {
            Throwable th;
            Closeable closeable = null;
            if (droiError == null) {
                droiError = new DroiError();
            }
            C0940n a = C0940n.m2775a(null, droiError);
            if (!droiError.isOk()) {
                return null;
            }
            int length;
            C0919e c0919e;
            if (this.fileFrom != null) {
                length = (int) this.fileFrom.length();
                c0919e = new C0919e(a, this.fileFrom, this.name, this.mimeType);
            } else {
                C0919e c0919e2 = new C0919e(a, this.dataFrom, this.name, this.mimeType);
                length = this.dataFrom.length;
                c0919e = c0919e2;
            }
            if (this.f2515g.saveProcessCallback != null) {
                this.f2515g.saveProcessCallback.progress(this.f2515g, 0, (long) length);
            }
            C0920f a2 = C0939m.m2758a(c0919e, droiError);
            if (!droiError.isOk()) {
                return null;
            }
            if (a2.f2985a != 0 || a2.f2988d.size() == 0) {
                droiError.setCode(a2.f2985a);
                droiError.setAppendedMessage("Commit file fail. " + (a2 != null ? a2.f2986b : "no reason."));
                return null;
            }
            HashMap hashMap = (HashMap) a2.f2988d.get(0);
            int intValue = Integer.getInteger(hashMap.get("Code").toString(), -2).intValue();
            String obj = hashMap.get("FID").toString();
            if (intValue == 0) {
                return String.valueOf(obj);
            }
            try {
                C0937w c0937w = this.fileFrom != null ? new C0937w(a, this.fileFrom) : new C0937w(a, this.dataFrom);
                try {
                    c0937w.f3047a = obj;
                    c0937w.f3048b = intValue == 2 ? Integer.getInteger(hashMap.get("NeedBlock").toString()).intValue() : 0;
                    droiError.setCode(0);
                    while (c0937w.m2746a(droiError)) {
                        if (this.f2515g.saveProcessCallback != null) {
                            this.f2515g.saveProcessCallback.progress(this.f2515g, (long) c0937w.m2747b(), (long) length);
                        }
                    }
                    if (droiError.isOk()) {
                        FileDescriptorHelper.closeQuietly(c0937w);
                        if (c0937w.f3048b >= c0937w.f3050d) {
                            c0919e.f2981a = obj;
                            int i = 0;
                            while (true) {
                                intValue = i + 1;
                                if (i >= 5) {
                                    break;
                                }
                                C0920f a3 = C0939m.m2758a(c0919e, droiError);
                                if (droiError.isOk()) {
                                    hashMap = (HashMap) a3.f2988d.get(0);
                                    int parseInt = Integer.parseInt(hashMap.get("Code").toString());
                                    if (a3.f2985a == 0 && parseInt == 0) {
                                        m2474a(this.fileFrom, this.dataFrom, this.name, obj);
                                        return obj;
                                    } else if (parseInt != -12) {
                                        droiError.setCode(DroiError.ERROR);
                                        droiError.setAppendedMessage("upload fail. " + hashMap.get("Message"));
                                        return null;
                                    } else {
                                        i = intValue;
                                    }
                                } else {
                                    i = intValue;
                                }
                            }
                        }
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage("Upload fail. Not finished.");
                        return null;
                    }
                    FileDescriptorHelper.closeQuietly(c0937w);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    closeable = c0937w;
                    FileDescriptorHelper.closeQuietly(closeable);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                FileDescriptorHelper.closeQuietly(closeable);
                throw th;
            }
        }

        private String m2476c(DroiError droiError) throws IOException {
            this.id = this.f2515g.getObjectId();
            File a = DroiFile.m2494d(this.name, this.id);
            if (a == null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("Make local file fail.");
                return null;
            }
            long length = this.fileFrom != null ? this.f2515g.file.length() : (long) this.dataFrom.length;
            if (this.f2515g.saveProcessCallback != null) {
                this.f2515g.saveProcessCallback.progress(this.f2515g, 0, length);
            }
            if (this.fileFrom != null) {
                FileDescriptorHelper.copy(this.fileFrom, a, null);
            } else {
                FileDescriptorHelper.copy(this.dataFrom, a, null);
            }
            if (this.f2515g.saveProcessCallback != null) {
                this.f2515g.saveProcessCallback.progress(this.f2515g, length, length);
            }
            return this.id;
        }

        protected String mo1889a(DroiError droiError) throws IOException {
            if (this.fileFrom != null) {
                this.fileFrom.length();
            } else {
                long length = (long) this.dataFrom.length;
            }
            return this.localStorage ? m2476c(droiError) : m2475b(droiError);
        }
    }

    private DroiFile() {
        this.file = null;
        this.data = null;
        this.mimeType = null;
        this.isContentDirty = false;
    }

    public DroiFile(File file) {
        this(file, null);
    }

    public DroiFile(File file, String str) {
        this.file = null;
        this.data = null;
        this.mimeType = null;
        this.file = file;
        this.Name = file.getName();
        this.mimeType = str;
        this.isContentDirty = true;
    }

    public DroiFile(String str, byte[] bArr) {
        this(str, bArr, null);
    }

    public DroiFile(String str, byte[] bArr, String str2) {
        this.file = null;
        this.data = null;
        this.mimeType = null;
        this.data = bArr;
        this.mimeType = str2;
        if (str == null) {
            str = "DroiFile-" + UUID.randomUUID().toString();
        }
        this.Name = str;
        this.isContentDirty = true;
    }

    public DroiFile(byte[] bArr) {
        this(null, bArr, null);
    }

    public DroiFile(byte[] bArr, String str) {
        this(null, bArr, str);
    }

    private Uri m2478a(String str, String str2, DroiError droiError) {
        File d = m2494d(str, str2);
        if (droiError == null) {
            droiError = new DroiError();
        }
        if (d.exists()) {
            return Uri.fromFile(d);
        }
        File c = m2492c(str, str2);
        if (!c.exists()) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Local cache not found");
            return null;
        } else if (c.renameTo(d)) {
            return Uri.fromFile(d);
        } else {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("move v1 to v2 cache fail.");
            return null;
        }
    }

    private DroiError m2480a(String str) {
        boolean isLocalStorage = isLocalStorage();
        DroiError droiError = new DroiError();
        if (!isLocalStorage) {
            C0940n a = C0940n.m2775a(null, droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
            C0921g c0921g = new C0921g();
            c0921g.f2989a = a.f3066a;
            c0921g.f2990b = str;
            C0922h a2 = C0939m.m2759a(c0921g, droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
            if (a2.f2991a != 0) {
                droiError.setCode(a2.f2991a);
                droiError.setAppendedMessage(a2.f2992b);
                return droiError;
            } else if (a2.f2994d == null || a2.f2994d.size() == 0) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("No result for files.");
                return droiError;
            } else {
                int parseInt = Integer.parseInt(((HashMap) a2.f2994d.get(0)).get("Code").toString());
                if (parseInt != 0) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("Delete fail. ErrorCode: " + parseInt);
                    return droiError;
                }
            }
        }
        if (isLocalStorage) {
            str = getObjectId();
        }
        File c = m2492c(this.Name, str);
        if (!(c.exists() && c.delete())) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Delete fail.");
        }
        c = m2494d(this.Name, str);
        if (c.exists() && c.delete()) {
            droiError.setCode(0);
            droiError.setAppendedMessage(null);
        }
        return droiError;
    }

    private String m2485a(boolean z, DroiError droiError) {
        FileTransformSaveRunnable fileTransformSaveRunnable;
        if (this.file != null) {
            fileTransformSaveRunnable = new FileTransformSaveRunnable(this, this.file, this.Name, z, this.mimeType);
        } else {
            fileTransformSaveRunnable = new FileTransformSaveRunnable(this, this.data, this.Name, z, this.mimeType);
        }
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        if (currentTaskDispatcher == null || currentTaskDispatcher.name() == TaskDispatcher.MainThreadName) {
            fileTransformSaveRunnable.enqueueSync();
        } else {
            fileTransformSaveRunnable.run();
        }
        if (droiError != null) {
            droiError.copy(fileTransformSaveRunnable.b);
        }
        return fileTransformSaveRunnable.c;
    }

    private static File m2492c(String str, String str2) {
        File file = new File(DroiStorageFinder.getPrivatePath(), String.format("files/%s/%s", new Object[]{str2.substring(0, 2), str}));
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    private static File m2494d(String str, String str2) {
        File file = new File(DroiStorageFinder.getPrivatePath(), String.format("files/%s/%s-%s", new Object[]{str2.substring(0, 2), str2, str}));
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    public DroiError delete() {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final AtomicReference atomicReference = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception"));
        if (dispatcher == currentTaskDispatcher) {
            atomicReference.set(m2480a(this.Fid));
        } else {
            final Object obj = new Object();
            Runnable c08095 = new Runnable(this) {
                final /* synthetic */ DroiFile f2488c;

                public void run() {
                    try {
                        atomicReference.set(this.f2488c.m2480a(this.f2488c.Fid));
                        synchronized (obj) {
                            obj.notify();
                        }
                    } catch (Throwable th) {
                        synchronized (obj) {
                            obj.notify();
                        }
                    }
                }
            };
            synchronized (obj) {
                if (dispatcher.enqueueTask(c08095)) {
                    try {
                        obj.wait();
                    } catch (Exception e) {
                        DroiLog.m2873w(LOG_TAG, e);
                    }
                }
            }
        }
        DroiError droiError = (DroiError) atomicReference.get();
        if (droiError == null) {
            DroiLog.m2870e(LOG_TAG, "Can not remove file.");
            return new DroiError(DroiError.ERROR, "Can not remove file. unknown error");
        } else if (droiError.isOk()) {
            return super.delete();
        } else {
            DroiLog.m2874w(LOG_TAG, "Delete file fail. " + droiError.toString());
            return droiError;
        }
    }

    public String deleteInBackground(final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(TaskDispatcher.BackgroundThreadName);
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        String uuid = UUID.randomUUID().toString();
        dispatcher.enqueueTask(new Runnable(this) {
            final /* synthetic */ DroiFile f2493c;

            public void run() {
                final DroiError delete = this.f2493c.delete();
                if (droiCallback != null) {
                    currentTaskDispatcher.enqueueTask(new Runnable(this) {
                        final /* synthetic */ C08116 f2490b;

                        public void run() {
                            droiCallback.result(Boolean.valueOf(delete.isOk()), delete);
                        }
                    });
                }
            }
        }, uuid);
        return uuid;
    }

    public byte[] get(DroiError droiError) {
        if (this.data != null) {
            return this.data;
        }
        if (this.MD5 != null && this.Name != null && this.Size != 0) {
            boolean isLocalStorage = isLocalStorage();
            if (isLocalStorage || !(this.Fid == null || this.Fid.isEmpty())) {
                DroiFileLoadRunnable droiFileLoadRunnable = new DroiFileLoadRunnable(this, isLocalStorage ? getObjectId() : this.Fid, this.Name, isLocalStorage(), null, null, true);
                droiFileLoadRunnable.enqueueSync();
                if (droiError != null) {
                    droiError.copy(droiFileLoadRunnable.error);
                }
                this.data = droiFileLoadRunnable.result;
                return this.data;
            } else if (droiError == null) {
                return null;
            } else {
                droiError.setCode(DroiError.INVALID_PARAMETER);
                droiError.setAppendedMessage("Can not fetch DroiFile.");
                return null;
            }
        } else if (droiError == null) {
            return null;
        } else {
            droiError.setCode(DroiError.INVALID_PARAMETER);
            droiError.setAppendedMessage("DroiFile object fields empty.");
            return null;
        }
    }

    public void getInBackground(@NonNull DroiCallback<byte[]> droiCallback) {
        getInBackground(droiCallback, null);
    }

    public void getInBackground(@NonNull final DroiCallback<byte[]> droiCallback, DroiProgressCallback droiProgressCallback) {
        if (this.data != null) {
            if (droiCallback != null) {
                droiCallback.result(this.data, new DroiError());
            }
        } else if (this.MD5 != null && this.Name != null && this.Size != 0) {
            boolean isLocalStorage = isLocalStorage();
            if (isLocalStorage || !(this.Fid == null || this.Fid.isEmpty())) {
                String objectId = isLocalStorage ? getObjectId() : this.Fid;
                final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
                new DroiFileLoadRunnable(this, objectId, this.Name, isLocalStorage, new DroiCallback<byte[]>(this) {
                    final /* synthetic */ DroiFile f2465c;

                    public void result(final byte[] bArr, final DroiError droiError) {
                        if (bArr != null) {
                            this.f2465c.data = bArr;
                        }
                        if (droiCallback != null) {
                            currentTaskDispatcher.enqueueTask(new Runnable(this) {
                                final /* synthetic */ C08021 f2458c;

                                public void run() {
                                    droiCallback.result(bArr, droiError);
                                }
                            });
                        }
                    }
                }, droiProgressCallback, true).enqueueAsync();
                return;
            }
            DroiError droiError = new DroiError(DroiError.INVALID_PARAMETER, "Can not fetch DroiFile.");
            if (droiCallback != null) {
                droiCallback.result(null, droiError);
            }
        } else if (droiCallback != null) {
            droiCallback.result(null, new DroiError(DroiError.INVALID_PARAMETER, "DroiFile object fields empty."));
        }
    }

    public String getMd5() {
        return this.MD5;
    }

    public String getName() {
        return this.Name;
    }

    public int getSize() {
        return this.Size;
    }

    public Uri getUri() {
        return getUri(null, null);
    }

    public Uri getUri(AtomicBoolean atomicBoolean) {
        return getUri(atomicBoolean, null);
    }

    public Uri getUri(AtomicBoolean atomicBoolean, DroiError droiError) {
        if (droiError == null) {
            droiError = new DroiError();
        }
        if (this.isContentDirty || this.Fid == null) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Content dirty or no fid.");
            return null;
        }
        if (atomicBoolean == null) {
            atomicBoolean = new AtomicBoolean(false);
        }
        if (isLocalStorage()) {
            atomicBoolean.set(true);
            return m2478a(this.Name, getObjectId(), droiError);
        }
        DroiFileLoadRunnable droiFileLoadRunnable = new DroiFileLoadRunnable(this, this.Fid, this.Name, isLocalStorage(), null, null, false);
        droiFileLoadRunnable.enqueueSync();
        if (droiFileLoadRunnable.cdn != null) {
            droiError.setCode(0);
            return Uri.parse(droiFileLoadRunnable.cdn);
        }
        atomicBoolean.set(true);
        return m2478a(this.Name, this.Fid, droiError);
    }

    public boolean getUriInBackground(final DroiCallback<Uri> droiCallback) {
        String name = TaskDispatcher.currentTaskDispatcher().name();
        final AtomicReference atomicReference = new AtomicReference();
        final DroiError droiError = new DroiError(DroiError.UNKNOWN_ERROR, "May due to exception.");
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiFile f2462d;

            public void run() {
                atomicReference.set(this.f2462d.getUri(atomicBoolean, droiError));
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiFile f2506d;

            public void run() {
                if (droiCallback != null) {
                    droiCallback.result((Uri) atomicReference.get(), droiError);
                }
            }
        }, name).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public boolean getUriWithFlagInBackground(DroiGetUrlWithFlagCallback droiGetUrlWithFlagCallback) {
        String name = TaskDispatcher.currentTaskDispatcher().name();
        final AtomicReference atomicReference = new AtomicReference();
        final DroiError droiError = new DroiError(DroiError.UNKNOWN_ERROR, "May due to exception.");
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        final DroiGetUrlWithFlagCallback droiGetUrlWithFlagCallback2 = droiGetUrlWithFlagCallback;
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiFile f2502d;

            public void run() {
                atomicReference.set(this.f2502d.getUri(atomicBoolean, droiError));
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiFile f2498e;

            public void run() {
                if (droiGetUrlWithFlagCallback2 != null) {
                    droiGetUrlWithFlagCallback2.result((Uri) atomicReference.get(), atomicBoolean.get(), droiError);
                }
            }
        }, name).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public boolean isContentDirty() {
        return this.isContentDirty;
    }

    public DroiError save() {
        if (this.file == null && this.data == null) {
            DroiLog.m2874w(LOG_TAG, "File content is empty. (No update)");
            return new DroiError();
        }
        int length = this.file != null ? (int) this.file.length() : this.data.length;
        String a = this.file != null ? C0944p.m2786a(FileDescriptorHelper.calculateHash(this.file)) : C0944p.m2786a(FileDescriptorHelper.calculateHash(this.data));
        if (a.equals(this.MD5)) {
            return new DroiError();
        }
        DroiError droiError = new DroiError();
        String a2 = m2485a(isLocalStorage(), droiError);
        if (!droiError.isOk()) {
            return droiError;
        }
        this.Fid = a2;
        this.Size = length;
        this.MD5 = a;
        this.isContentDirty = false;
        return super.save();
    }

    public String saveInBackground(final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(TaskDispatcher.BackgroundThreadName);
        final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        String uuid = UUID.randomUUID().toString();
        dispatcher.enqueueTask(new Runnable(this) {
            final /* synthetic */ DroiFile f2485c;

            public void run() {
                final DroiError save = this.f2485c.save();
                if (droiCallback != null) {
                    currentTaskDispatcher.enqueueTask(new Runnable(this) {
                        final /* synthetic */ C08084 f2482b;

                        public void run() {
                            droiCallback.result(Boolean.valueOf(save.isOk()), save);
                        }
                    });
                }
            }
        }, uuid);
        return uuid;
    }

    public String saveInBackground(DroiCallback<Boolean> droiCallback, DroiProgressCallback droiProgressCallback) {
        this.saveProcessCallback = droiProgressCallback;
        return saveInBackground(droiCallback);
    }

    public DroiError update(File file) {
        return update(file, null);
    }

    public DroiError update(File file, String str) {
        if (file == null) {
            return new DroiError(DroiError.INVALID_PARAMETER, null);
        }
        this.file = file;
        this.data = null;
        this.mimeType = str;
        this.Name = file.getName();
        String str2 = this.Fid;
        this.isContentDirty = true;
        DroiError save = save();
        if (!save.isOk() || str2 == null) {
            return save;
        }
        DroiError a = m2480a(str2);
        if (a.isOk()) {
            return save;
        }
        DroiLog.m2874w(LOG_TAG, "Delete fail. " + a.toString());
        return save;
    }

    public DroiError update(String str, byte[] bArr) {
        return update(str, bArr, null);
    }

    public DroiError update(String str, byte[] bArr, String str2) {
        if (bArr == null) {
            return new DroiError(DroiError.INVALID_PARAMETER, null);
        }
        this.file = null;
        this.data = bArr;
        this.mimeType = str2;
        if (str == null) {
            str = "DroiFile-" + UUID.randomUUID().toString();
        }
        this.Name = str;
        String str3 = this.Fid;
        this.isContentDirty = true;
        DroiError save = save();
        if (!save.isOk() || str3 == null) {
            return save;
        }
        DroiError a = m2480a(str3);
        if (a.isOk()) {
            return save;
        }
        DroiLog.m2874w(LOG_TAG, "Delete fail. " + a.toString());
        return save;
    }

    public DroiError update(byte[] bArr) {
        return update(null, bArr, null);
    }

    public DroiError update(byte[] bArr, String str) {
        return update(null, bArr, str);
    }

    public String updateInBackground(File file, DroiCallback<Boolean> droiCallback) {
        return updateInBackground(file, null, (DroiCallback) droiCallback);
    }

    public String updateInBackground(File file, String str, DroiCallback<Boolean> droiCallback) {
        if (file != null) {
            TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
            final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            String uuid = UUID.randomUUID().toString();
            final File file2 = file;
            final String str2 = str;
            final DroiCallback<Boolean> droiCallback2 = droiCallback;
            dispatcher.enqueueTask(new Runnable(this) {
                final /* synthetic */ DroiFile f2472e;

                public void run() {
                    final DroiError update = this.f2472e.update(file2, str2);
                    if (droiCallback2 != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08042 f2467b;

                            public void run() {
                                droiCallback2.result(Boolean.valueOf(update.isOk()), update);
                            }
                        });
                    }
                }
            }, uuid);
            return uuid;
        } else if (droiCallback == null) {
            return null;
        } else {
            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.INVALID_PARAMETER, null));
            return null;
        }
    }

    public String updateInBackground(String str, byte[] bArr, DroiCallback<Boolean> droiCallback) {
        return updateInBackground(str, bArr, null, droiCallback);
    }

    public String updateInBackground(String str, byte[] bArr, String str2, DroiCallback<Boolean> droiCallback) {
        if (bArr != null) {
            TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
            final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            String uuid = UUID.randomUUID().toString();
            final String str3 = str;
            final byte[] bArr2 = bArr;
            final String str4 = str2;
            final DroiCallback<Boolean> droiCallback2 = droiCallback;
            dispatcher.enqueueTask(new Runnable(this) {
                final /* synthetic */ DroiFile f2480f;

                public void run() {
                    final DroiError update = this.f2480f.update(str3, bArr2, str4);
                    if (droiCallback2 != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08063 f2474b;

                            public void run() {
                                droiCallback2.result(Boolean.valueOf(update.isOk()), update);
                            }
                        });
                    }
                }
            }, uuid);
            return uuid;
        } else if (droiCallback == null) {
            return null;
        } else {
            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.INVALID_PARAMETER, null));
            return null;
        }
    }

    public String updateInBackground(byte[] bArr, DroiCallback<Boolean> droiCallback) {
        return updateInBackground(null, bArr, null, droiCallback);
    }

    public String updateInBackground(byte[] bArr, String str, DroiCallback<Boolean> droiCallback) {
        return updateInBackground(null, bArr, str, droiCallback);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 0;
        super.writeToParcel(parcel, i);
        parcel.writeString(this.file == null ? null : this.file.getPath());
        parcel.writeString(this.mimeType);
        parcel.writeInt(this.isContentDirty ? 1 : 0);
        if (this.data != null) {
            i2 = this.data.length;
        }
        parcel.writeInt(i2);
        parcel.writeByteArray(this.data);
    }
}
