package com.umeng.socialize.media;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;
import com.umeng.socialize.common.ImageFormat;
import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.AesHelper;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.BitmapUtils;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class UMImage extends BaseMediaObject {
    private ConfiguredConvertor f5475i = null;
    private ConvertConfig f5476j;
    private WeakReference<Bitmap> f5477k = new WeakReference(null);

    static class ConvertConfig {
        private static final String f3325a = "/umeng_cache/";
        private String f3326b = "";
        public float mImageSizeLimit = 2048.0f;

        public ConvertConfig(Context context) {
            try {
                this.f3326b = context.getCacheDir().getCanonicalPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public File getCache() throws IOException {
            String canonicalPath;
            if (DeviceConfig.isSdCardWrittenable()) {
                canonicalPath = Environment.getExternalStorageDirectory().getCanonicalPath();
            } else if (TextUtils.isEmpty(this.f3326b)) {
                throw new IOException("dirpath is unknow");
            } else {
                canonicalPath = this.f3326b;
            }
            File file = new File(canonicalPath + f3325a);
            if (!(file == null || file.exists())) {
                file.mkdirs();
            }
            return file;
        }

        public File generateCacheFile(String str) throws IOException {
            BitmapUtils.cleanCache();
            File file = new File(getCache(), str);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            return file;
        }
    }

    interface IImageConvertor {
        byte[] asBinary();

        Bitmap asBitmap();

        File asFile();

        String asUrl();
    }

    static abstract class ConfiguredConvertor implements IImageConvertor {
        public ConvertConfig config = null;

        ConfiguredConvertor() {
        }

        public void setConfig(ConvertConfig convertConfig) {
            this.config = convertConfig;
        }
    }

    static class BinaryConvertor extends ConfiguredConvertor {
        private ConvertConfig f5468a = new ConvertConfig();
        private byte[] f5469b;

        public BinaryConvertor(byte[] bArr) {
            this.f5469b = bArr;
        }

        public File asFile() {
            try {
                return m6108a(this.f5469b, this.f5468a.generateCacheFile(getFileName()));
            } catch (IOException e) {
                Log.m3250e("Sorry cannot setImage..[" + e.toString() + "]");
                return null;
            }
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return this.f5469b;
        }

        public Bitmap asBitmap() {
            if (this.f5469b != null) {
                return BitmapFactory.decodeByteArray(this.f5469b, 0, this.f5469b.length);
            }
            return null;
        }

        public String getFileName() {
            return AesHelper.md5(String.valueOf(System.currentTimeMillis()));
        }

        private File m6108a(byte[] bArr, File file) {
            BufferedOutputStream bufferedOutputStream;
            Exception e;
            Throwable th;
            BufferedOutputStream bufferedOutputStream2 = null;
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                try {
                    bufferedOutputStream.write(bArr);
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    try {
                        e.printStackTrace();
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException e4) {
                            }
                        }
                        return file;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream2 = bufferedOutputStream;
                        if (bufferedOutputStream2 != null) {
                            try {
                                bufferedOutputStream2.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (Exception e6) {
                e = e6;
                bufferedOutputStream = null;
                e.printStackTrace();
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                return file;
            } catch (Throwable th3) {
                th = th3;
                if (bufferedOutputStream2 != null) {
                    bufferedOutputStream2.close();
                }
                throw th;
            }
            return file;
        }
    }

    static class BitmapConvertor extends ConfiguredConvertor {
        private Bitmap f5470a;

        public BitmapConvertor(Bitmap bitmap) {
            this.f5470a = bitmap;
        }

        public File asFile() {
            OutputStream fileOutputStream;
            Exception e;
            Throwable th;
            try {
                long currentTimeMillis = System.currentTimeMillis();
                File generateCacheFile = this.config.generateCacheFile(AesHelper.md5(this.f5470a.toString()));
                fileOutputStream = new FileOutputStream(generateCacheFile);
                try {
                    int rowBytes = (this.f5470a.getRowBytes() * this.f5470a.getHeight()) / 1024;
                    Log.m3247d("### bitmap size = " + rowBytes + " KB");
                    int i = 100;
                    if (((float) rowBytes) > this.config.mImageSizeLimit) {
                        i = (int) (((float) 100) * (this.config.mImageSizeLimit / ((float) rowBytes)));
                    }
                    Log.m3247d("### 压缩质量 : " + i);
                    if (!this.f5470a.isRecycled()) {
                        this.f5470a.compress(CompressFormat.JPEG, i, fileOutputStream);
                    }
                    Log.m3247d("##save bitmap " + generateCacheFile.getAbsolutePath());
                    Log.m3247d("#### 图片序列化耗时 : " + (System.currentTimeMillis() - currentTimeMillis) + " ms.");
                    if (fileOutputStream == null) {
                        return generateCacheFile;
                    }
                    try {
                        fileOutputStream.close();
                        return generateCacheFile;
                    } catch (IOException e2) {
                        return generateCacheFile;
                    }
                } catch (Exception e3) {
                    e = e3;
                    try {
                        Log.m3250e("Sorry cannot setImage..[" + e.toString() + "]");
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e4) {
                            }
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (Exception e6) {
                e = e6;
                fileOutputStream = null;
                Log.m3250e("Sorry cannot setImage..[" + e.toString() + "]");
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return BitmapUtils.bitmap2Bytes(this.f5470a);
        }

        public Bitmap asBitmap() {
            return this.f5470a;
        }
    }

    static class FileConvertor extends ConfiguredConvertor {
        private File f5471a;

        public FileConvertor(File file) {
            this.f5471a = file;
        }

        public File asFile() {
            return this.f5471a;
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return m6109a(this.f5471a);
        }

        public Bitmap asBitmap() {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(this.f5471a.toString(), options);
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, BitmapUtils.MAX_WIDTH, BitmapUtils.MAX_HEIGHT);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(this.f5471a.getAbsolutePath(), options);
        }

        private byte[] m6109a(File file) {
            if (file == null || !file.getAbsoluteFile().exists()) {
                return null;
            }
            byte[] b = m6110b(file);
            if (b == null || b.length <= 0) {
                return null;
            }
            if (ImageFormat.FORMAT_NAMES[1].equals(ImageFormat.checkFormat(b))) {
                return b;
            }
            return UMImage.m6114b(b);
        }

        private static byte[] m6110b(File file) {
            InputStream fileInputStream;
            ByteArrayOutputStream byteArrayOutputStream;
            Exception e;
            Throwable th;
            byte[] bArr = null;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr2 = new byte[4096];
                        while (true) {
                            int read = fileInputStream.read(bArr2);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr2, 0, read);
                        }
                        bArr = byteArrayOutputStream.toByteArray();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                            }
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    } catch (Exception e3) {
                        e = e3;
                        try {
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e4) {
                                }
                            }
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            return bArr;
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e5) {
                                    throw th;
                                }
                            }
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            throw th;
                        }
                    }
                } catch (Exception e6) {
                    e = e6;
                    byteArrayOutputStream = bArr;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    return bArr;
                } catch (Throwable th3) {
                    byteArrayOutputStream = bArr;
                    th = th3;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e7) {
                e = e7;
                byteArrayOutputStream = bArr;
                fileInputStream = bArr;
                e.printStackTrace();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                return bArr;
            } catch (Throwable th32) {
                byteArrayOutputStream = bArr;
                fileInputStream = bArr;
                th = th32;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                throw th;
            }
            return bArr;
        }
    }

    static class ResConvertor extends ConfiguredConvertor {
        private Context f5472a;
        private int f5473b = 0;

        public ResConvertor(Context context, int i) {
            this.f5472a = context;
            this.f5473b = i;
        }

        public File asFile() {
            IOException e;
            Throwable th;
            FileOutputStream fileOutputStream = null;
            FileInputStream createInputStream;
            FileOutputStream fileOutputStream2;
            try {
                createInputStream = this.f5472a.getResources().openRawResourceFd(this.f5473b).createInputStream();
                try {
                    File generateCacheFile = this.config.generateCacheFile(AesHelper.md5(createInputStream.toString()));
                    fileOutputStream2 = new FileOutputStream(generateCacheFile);
                    try {
                        byte[] bArr = new byte[4096];
                        while (createInputStream.read(bArr) != -1) {
                            fileOutputStream2.write(bArr);
                        }
                        fileOutputStream2.flush();
                        if (createInputStream != null) {
                            try {
                                createInputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                return generateCacheFile;
                            }
                        }
                        if (fileOutputStream2 == null) {
                            return generateCacheFile;
                        }
                        fileOutputStream2.close();
                        return generateCacheFile;
                    } catch (IOException e3) {
                        e = e3;
                        try {
                            e.printStackTrace();
                            if (createInputStream != null) {
                                try {
                                    createInputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                    return null;
                                }
                            }
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStream2;
                            if (createInputStream != null) {
                                try {
                                    createInputStream.close();
                                } catch (IOException e22) {
                                    e22.printStackTrace();
                                    throw th;
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            throw th;
                        }
                    }
                } catch (IOException e5) {
                    e4 = e5;
                    fileOutputStream2 = null;
                    e4.printStackTrace();
                    if (createInputStream != null) {
                        createInputStream.close();
                    }
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    if (createInputStream != null) {
                        createInputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e6) {
                e4 = e6;
                createInputStream = null;
                fileOutputStream2 = null;
                e4.printStackTrace();
                if (createInputStream != null) {
                    createInputStream.close();
                }
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                createInputStream = null;
                if (createInputStream != null) {
                    createInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            Drawable drawable;
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Resources resources = this.f5472a.getResources();
            if (VERSION.SDK_INT >= 21) {
                drawable = resources.getDrawable(this.f5473b, null);
            } else {
                drawable = resources.getDrawable(this.f5473b);
            }
            UMImage.m6111a(drawable).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

        public Bitmap asBitmap() {
            return BitmapFactory.decodeResource(this.f5472a.getResources(), this.f5473b);
        }
    }

    static class UrlConvertor extends ConfiguredConvertor {
        private String f5474a = null;

        public UrlConvertor(String str) {
            this.f5474a = str;
        }

        public File asFile() {
            File generateCacheFile;
            Exception e;
            try {
                generateCacheFile = this.config.generateCacheFile(AesHelper.md5(this.f5474a));
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(generateCacheFile);
                    fileOutputStream.write(asBinary());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return generateCacheFile;
                }
            } catch (Exception e3) {
                Exception exception = e3;
                generateCacheFile = null;
                e = exception;
                e.printStackTrace();
                return generateCacheFile;
            }
            return generateCacheFile;
        }

        public String asUrl() {
            return this.f5474a;
        }

        public byte[] asBinary() {
            return SocializeNetUtils.getNetData(this.f5474a);
        }

        public Bitmap asBitmap() {
            byte[] asBinary = asBinary();
            if (asBinary != null) {
                return BitmapFactory.decodeByteArray(asBinary, 0, asBinary.length);
            }
            return null;
        }
    }

    public UMImage(Context context, File file) {
        m6112a(context, file);
    }

    public UMImage(Context context, String str) {
        super(str);
        m6112a((Context) new WeakReference(context).get(), str);
    }

    public UMImage(Context context, int i) {
        m6112a(context, Integer.valueOf(i));
    }

    public UMImage(Context context, byte[] bArr) {
        m6112a(context, bArr);
    }

    public UMImage(Context context, Bitmap bitmap) {
        m6112a(context, bitmap);
    }

    private void m6112a(Context context, Object obj) {
        if (obj instanceof File) {
            this.f5475i = new FileConvertor((File) obj);
        } else if (obj instanceof String) {
            this.f5475i = new UrlConvertor((String) obj);
        } else if (obj instanceof Integer) {
            this.f5475i = new ResConvertor(context, ((Integer) obj).intValue());
        } else if (obj instanceof byte[]) {
            this.f5475i = new BinaryConvertor((byte[]) obj);
        } else if (obj instanceof Bitmap) {
            this.f5475i = new BitmapConvertor((Bitmap) obj);
        } else {
            throw new RuntimeException("Don't support type");
        }
        this.f5475i.setConfig(new ConvertConfig(context));
    }

    public byte[] toByte() {
        return asBinImage();
    }

    public final Map<String, Object> toUrlExtraParams() {
        Map<String, Object> hashMap = new HashMap();
        if (isUrlMedia()) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.a);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
        }
        return hashMap;
    }

    private static byte[] m6114b(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        byte[] bArr2 = null;
        try {
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, BitmapUtils.getBitmapOptions(bArr));
            byteArrayOutputStream = new ByteArrayOutputStream();
            if (decodeByteArray != null) {
                try {
                    decodeByteArray.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
                    decodeByteArray.recycle();
                    System.gc();
                } catch (Exception e) {
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e2) {
                        }
                    }
                    return bArr2;
                } catch (Throwable th2) {
                    th = th2;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e3) {
                        }
                    }
                    throw th;
                }
            }
            bArr2 = byteArrayOutputStream.toByteArray();
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e4) {
                }
            }
        } catch (Exception e5) {
            byteArrayOutputStream = null;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            return bArr2;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            byteArrayOutputStream = null;
            th = th4;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            throw th;
        }
        return bArr2;
    }

    public MediaType getMediaType() {
        return MediaType.IMAGE;
    }

    public void resize(int i, int i2) {
    }

    public boolean isMultiMedia() {
        return true;
    }

    public File asFileImage() {
        Log.m3250e("xxxxx convor=" + this.f5475i.asFile());
        return this.f5475i == null ? null : this.f5475i.asFile();
    }

    public String asUrlImage() {
        return this.f5475i == null ? null : this.f5475i.asUrl();
    }

    public byte[] asBinImage() {
        return this.f5475i == null ? null : this.f5475i.asBinary();
    }

    public Bitmap asBitmap() {
        return this.f5475i == null ? null : this.f5475i.asBitmap();
    }

    static Bitmap m6111a(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return createBitmap;
    }
}
