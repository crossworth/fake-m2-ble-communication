package org.andengine.opengl.texture.atlas.bitmap.source;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.andengine.opengl.texture.atlas.source.BaseTextureAtlasSource;
import org.andengine.util.FileUtils;
import org.andengine.util.StreamUtils;
import org.andengine.util.debug.Debug;

public class FileBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    private final File mFile;

    public static FileBitmapTextureAtlasSource create(File pFile) {
        return create(pFile, 0, 0);
    }

    public static FileBitmapTextureAtlasSource create(File pFile, int pTextureX, int pTextureY) {
        Throwable e;
        Throwable th;
        Options decodeOptions = new Options();
        decodeOptions.inJustDecodeBounds = true;
        InputStream in = null;
        try {
            InputStream in2 = new FileInputStream(pFile);
            try {
                BitmapFactory.decodeStream(in2, null, decodeOptions);
                StreamUtils.close(in2);
                in = in2;
            } catch (IOException e2) {
                e = e2;
                in = in2;
                try {
                    Debug.m4591e("Failed loading Bitmap in " + FileBitmapTextureAtlasSource.class.getSimpleName() + ". File: " + pFile, e);
                    StreamUtils.close(in);
                    return new FileBitmapTextureAtlasSource(pFile, pTextureX, pTextureY, decodeOptions.outWidth, decodeOptions.outHeight);
                } catch (Throwable th2) {
                    th = th2;
                    StreamUtils.close(in);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                in = in2;
                StreamUtils.close(in);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            Debug.m4591e("Failed loading Bitmap in " + FileBitmapTextureAtlasSource.class.getSimpleName() + ". File: " + pFile, e);
            StreamUtils.close(in);
            return new FileBitmapTextureAtlasSource(pFile, pTextureX, pTextureY, decodeOptions.outWidth, decodeOptions.outHeight);
        }
        return new FileBitmapTextureAtlasSource(pFile, pTextureX, pTextureY, decodeOptions.outWidth, decodeOptions.outHeight);
    }

    public static FileBitmapTextureAtlasSource createFromInternalStorage(Context pContext, String pFilePath, int pTextureX, int pTextureY) {
        return create(new File(FileUtils.getAbsolutePathOnInternalStorage(pContext, pFilePath)), pTextureX, pTextureY);
    }

    public static FileBitmapTextureAtlasSource createFromExternalStorage(Context pContext, String pFilePath, int pTextureX, int pTextureY) {
        return create(new File(FileUtils.getAbsolutePathOnExternalStorage(pContext, pFilePath)), pTextureX, pTextureY);
    }

    FileBitmapTextureAtlasSource(File pFile, int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight) {
        super(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
        this.mFile = pFile;
    }

    public FileBitmapTextureAtlasSource deepCopy() {
        return new FileBitmapTextureAtlasSource(this.mFile, this.mTextureX, this.mTextureY, this.mTextureWidth, this.mTextureHeight);
    }

    public Bitmap onLoadBitmap(Config pBitmapConfig) {
        Throwable e;
        Throwable th;
        Bitmap bitmap = null;
        Options decodeOptions = new Options();
        decodeOptions.inPreferredConfig = pBitmapConfig;
        InputStream in = null;
        try {
            InputStream in2 = new FileInputStream(this.mFile);
            try {
                bitmap = BitmapFactory.decodeStream(in2, null, decodeOptions);
                StreamUtils.close(in2);
                in = in2;
            } catch (IOException e2) {
                e = e2;
                in = in2;
                try {
                    Debug.m4591e("Failed loading Bitmap in " + getClass().getSimpleName() + ". File: " + this.mFile, e);
                    StreamUtils.close(in);
                    return bitmap;
                } catch (Throwable th2) {
                    th = th2;
                    StreamUtils.close(in);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                in = in2;
                StreamUtils.close(in);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            Debug.m4591e("Failed loading Bitmap in " + getClass().getSimpleName() + ". File: " + this.mFile, e);
            StreamUtils.close(in);
            return bitmap;
        }
        return bitmap;
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.mFile + ")";
    }
}
