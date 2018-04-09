package com.umeng.socialize.media;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;
import com.umeng.socialize.Config;
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
    private ConfiguredConvertor f4948e = null;
    private UMImage f4949f;

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
        private ConvertConfig f4939a = new ConvertConfig();
        private byte[] f4940b;

        public BinaryConvertor(byte[] bArr) {
            this.f4940b = bArr;
        }

        public File asFile() {
            try {
                return m4498a(this.f4940b, this.f4939a.generateCacheFile(getFileName()));
            } catch (IOException e) {
                Log.m4548e("Sorry cannot setImage..[" + e.toString() + "]");
                return null;
            }
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return this.f4940b;
        }

        public Bitmap asBitmap() {
            if (this.f4940b != null) {
                return BitmapFactory.decodeByteArray(this.f4940b, 0, this.f4940b.length);
            }
            return null;
        }

        public String getFileName() {
            return AesHelper.md5(String.valueOf(System.currentTimeMillis()));
        }

        private File m4498a(byte[] bArr, File file) {
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
        private Bitmap f4941a;

        public BitmapConvertor(Bitmap bitmap) {
            this.f4941a = bitmap;
        }

        public File asFile() {
            OutputStream fileOutputStream;
            Exception e;
            Throwable th;
            try {
                long currentTimeMillis = System.currentTimeMillis();
                File generateCacheFile = this.config.generateCacheFile(AesHelper.md5(this.f4941a.toString()));
                fileOutputStream = new FileOutputStream(generateCacheFile);
                try {
                    int rowBytes = (this.f4941a.getRowBytes() * this.f4941a.getHeight()) / 1024;
                    Log.m4545d("### bitmap size = " + rowBytes + " KB");
                    int i = 100;
                    if (((float) rowBytes) > this.config.mImageSizeLimit) {
                        i = (int) (((float) 100) * (this.config.mImageSizeLimit / ((float) rowBytes)));
                    }
                    Log.m4545d("### 压缩质量 : " + i);
                    if (!this.f4941a.isRecycled()) {
                        this.f4941a.compress(CompressFormat.JPEG, i, fileOutputStream);
                    }
                    Log.m4545d("##save bitmap " + generateCacheFile.getAbsolutePath());
                    Log.m4545d("#### 图片序列化耗时 : " + (System.currentTimeMillis() - currentTimeMillis) + " ms.");
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
                        Log.m4548e("Sorry cannot setImage..[" + e.toString() + "]");
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
                Log.m4548e("Sorry cannot setImage..[" + e.toString() + "]");
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
            return BitmapUtils.bitmap2Bytes(this.f4941a);
        }

        public Bitmap asBitmap() {
            return this.f4941a;
        }
    }

    static class ConvertConfig {
        private static final String f4942a = "/umeng_cache/";
        private String f4943b = "";
        public float mImageSizeLimit = 2048.0f;

        public ConvertConfig(Context context) {
            try {
                this.f4943b = context.getCacheDir().getCanonicalPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public File getCache() throws IOException {
            String canonicalPath;
            if (DeviceConfig.isSdCardWrittenable()) {
                canonicalPath = Environment.getExternalStorageDirectory().getCanonicalPath();
            } else if (TextUtils.isEmpty(this.f4943b)) {
                throw new IOException("dirpath is unknow");
            } else {
                canonicalPath = this.f4943b;
            }
            File file = new File(canonicalPath + f4942a);
            if (!(file == null || file.exists())) {
                file.mkdirs();
            }
            Log.m4548e("xxxxxx dirFile=" + file);
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

    static class FileConvertor extends ConfiguredConvertor {
        private File f4944a;

        public FileConvertor(File file) {
            this.f4944a = file;
        }

        public File asFile() {
            return this.f4944a;
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return m4499a(this.f4944a);
        }

        public Bitmap asBitmap() {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(this.f4944a.toString(), options);
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, BitmapUtils.MAX_WIDTH, BitmapUtils.MAX_HEIGHT);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(this.f4944a.getAbsolutePath(), options);
        }

        private byte[] m4499a(File file) {
            if (file == null || !file.getAbsoluteFile().exists()) {
                return null;
            }
            byte[] b = m4500b(file);
            if (b == null || b.length <= 0) {
                return null;
            }
            if (ImageFormat.FORMAT_NAMES[1].equals(ImageFormat.checkFormat(b))) {
                return b;
            }
            return UMImage.m4504b(b);
        }

        private static byte[] m4500b(File file) {
            InputStream fileInputStream;
            Exception e;
            Throwable th;
            byte[] bArr = null;
            ByteArrayOutputStream byteArrayOutputStream;
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
        private Context f4945a;
        private int f4946b = 0;

        public ResConvertor(Context context, int i) {
            this.f4945a = context;
            this.f4946b = i;
        }

        public File asFile() {
            FileInputStream createInputStream;
            FileOutputStream fileOutputStream;
            IOException e;
            Throwable th;
            try {
                createInputStream = this.f4945a.getResources().openRawResourceFd(this.f4946b).createInputStream();
                try {
                    File generateCacheFile = this.config.generateCacheFile(AesHelper.md5(createInputStream.toString()));
                    fileOutputStream = new FileOutputStream(generateCacheFile);
                    try {
                        byte[] bArr = new byte[4096];
                        while (createInputStream.read(bArr) != -1) {
                            fileOutputStream.write(bArr);
                        }
                        fileOutputStream.flush();
                        if (createInputStream != null) {
                            try {
                                createInputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                return generateCacheFile;
                            }
                        }
                        if (fileOutputStream == null) {
                            return generateCacheFile;
                        }
                        fileOutputStream.close();
                        return generateCacheFile;
                    } catch (IOException e3) {
                        e = e3;
                        try {
                            Log.m4548e("xxxxxx e=" + e);
                            e.printStackTrace();
                            if (createInputStream != null) {
                                try {
                                    createInputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                    return null;
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
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
                    fileOutputStream = null;
                    Log.m4548e("xxxxxx e=" + e4);
                    e4.printStackTrace();
                    if (createInputStream != null) {
                        createInputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
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
                fileOutputStream = null;
                Log.m4548e("xxxxxx e=" + e4);
                e4.printStackTrace();
                if (createInputStream != null) {
                    createInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                createInputStream = null;
                fileOutputStream = null;
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
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (Config.isLoadImgByCompress) {
                Options options = new Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                BitmapFactory.decodeStream(this.f4945a.getResources().openRawResource(this.f4946b), null, options).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
            Drawable drawable;
            Resources resources = this.f4945a.getResources();
            if (VERSION.SDK_INT >= 21) {
                drawable = resources.getDrawable(this.f4946b, null);
            } else {
                drawable = resources.getDrawable(this.f4946b);
            }
            UMImage.m4501a(drawable).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

        public Bitmap asBitmap() {
            if (!Config.isLoadImgByCompress) {
                return BitmapFactory.decodeResource(this.f4945a.getResources(), this.f4946b);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Options options = new Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return BitmapFactory.decodeStream(this.f4945a.getResources().openRawResource(this.f4946b), null, options);
        }
    }

    static class UrlConvertor extends ConfiguredConvertor {
        private String f4947a = null;

        public UrlConvertor(String str) {
            this.f4947a = str;
        }

        public File asFile() {
            File generateCacheFile;
            Exception e;
            try {
                generateCacheFile = this.config.generateCacheFile(AesHelper.md5(this.f4947a));
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(generateCacheFile);
                    fileOutputStream.write(asBinary());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e = e2;
                    Log.m4548e("xxxxxx   e=" + e);
                    e.printStackTrace();
                    return generateCacheFile;
                }
            } catch (Exception e3) {
                Exception exception = e3;
                generateCacheFile = null;
                e = exception;
                Log.m4548e("xxxxxx   e=" + e);
                e.printStackTrace();
                return generateCacheFile;
            }
            return generateCacheFile;
        }

        public String asUrl() {
            return this.f4947a;
        }

        public byte[] asBinary() {
            return SocializeNetUtils.getNetData(this.f4947a);
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
        m4502a(context, file);
    }

    public UMImage(Context context, String str) {
        super(str);
        m4502a((Context) new WeakReference(context).get(), str);
    }

    public UMImage(Context context, int i) {
        m4502a(context, Integer.valueOf(i));
    }

    public UMImage(Context context, byte[] bArr) {
        m4502a(context, bArr);
    }

    public UMImage(Context context, Bitmap bitmap) {
        m4502a(context, bitmap);
    }

    private void m4502a(Context context, Object obj) {
        if (obj instanceof File) {
            this.f4948e = new FileConvertor((File) obj);
        } else if (obj instanceof String) {
            this.f4948e = new UrlConvertor((String) obj);
        } else if (obj instanceof Integer) {
            this.f4948e = new ResConvertor(context, ((Integer) obj).intValue());
        } else if (obj instanceof byte[]) {
            this.f4948e = new BinaryConvertor((byte[]) obj);
        } else if (obj instanceof Bitmap) {
            this.f4948e = new BitmapConvertor((Bitmap) obj);
        } else {
            throw new RuntimeException("Don't support type");
        }
        this.f4948e.setConfig(new ConvertConfig(context));
    }

    public byte[] toByte() {
        return asBinImage();
    }

    public void setThumb(UMImage uMImage) {
        this.f4949f = uMImage;
    }

    public UMImage getThumbImage() {
        return this.f4949f;
    }

    public final Map<String, Object> toUrlExtraParams() {
        Map<String, Object> hashMap = new HashMap();
        if (isUrlMedia()) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.a);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
        }
        return hashMap;
    }

    private static byte[] m4504b(byte[] bArr) {
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

    public File asFileImage() {
        return this.f4948e == null ? null : this.f4948e.asFile();
    }

    public String asUrlImage() {
        return this.f4948e == null ? null : this.f4948e.asUrl();
    }

    public byte[] asBinImage() {
        return this.f4948e == null ? null : this.f4948e.asBinary();
    }

    public Bitmap asBitmap() {
        return this.f4948e == null ? null : this.f4948e.asBitmap();
    }

    static Bitmap m4501a(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return createBitmap;
    }
}
