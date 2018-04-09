package com.isseiaoki.simplecropview.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import com.umeng.socialize.common.SocializeConstants;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    private static final int SIZE_DEFAULT = 2048;
    private static final int SIZE_LIMIT = 4096;
    private static final String TAG = Utils.class.getSimpleName();
    public static int sInputImageHeight = 0;
    public static int sInputImageWidth = 0;

    public static int getExifRotation(android.content.Context r9, android.net.Uri r10) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0035 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = 0;
        r6 = 0;
        r0 = 1;
        r2 = new java.lang.String[r0];
        r0 = "orientation";
        r2[r8] = r0;
        r0 = r9.getContentResolver();	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        r3 = 0;	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        r4 = 0;	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        r5 = 0;	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        r1 = r10;	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        if (r6 == 0) goto L_0x001d;	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
    L_0x0017:
        r0 = r6.moveToFirst();	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        if (r0 != 0) goto L_0x0024;
    L_0x001d:
        if (r6 == 0) goto L_0x0022;
    L_0x001f:
        r6.close();
    L_0x0022:
        r0 = r8;
    L_0x0023:
        return r0;
    L_0x0024:
        r0 = 0;
        r0 = r6.getInt(r0);	 Catch:{ RuntimeException -> 0x002f, all -> 0x0037 }
        if (r6 == 0) goto L_0x0023;
    L_0x002b:
        r6.close();
        goto L_0x0023;
    L_0x002f:
        r7 = move-exception;
        if (r6 == 0) goto L_0x0035;
    L_0x0032:
        r6.close();
    L_0x0035:
        r0 = r8;
        goto L_0x0023;
    L_0x0037:
        r0 = move-exception;
        if (r6 == 0) goto L_0x003d;
    L_0x003a:
        r6.close();
    L_0x003d:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.isseiaoki.simplecropview.util.Utils.getExifRotation(android.content.Context, android.net.Uri):int");
    }

    public static int getExifRotation(File file) {
        int i = 0;
        if (file != null) {
            try {
                i = getRotateDegreeFromOrientation(new ExifInterface(file.getAbsolutePath()).getAttributeInt("Orientation", 0));
            } catch (IOException e) {
                Logger.m3300e("An error occurred while getting the exif data: " + e.getMessage(), e);
            }
        }
        return i;
    }

    public static int getExifOrientation(Context context, Uri uri) {
        if (uri.getAuthority().toLowerCase().endsWith(SocializeConstants.KEY_PLATFORM)) {
            return getExifRotation(context, uri);
        }
        return getExifRotation(getFileFromUri(context, uri));
    }

    public static int getRotateDegreeFromOrientation(int orientation) {
        switch (orientation) {
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    public static Matrix getMatrixFromExifOrientation(int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case 2:
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.postRotate(180.0f);
                break;
            case 4:
                matrix.postScale(1.0f, -1.0f);
                break;
            case 5:
                matrix.postRotate(90.0f);
                matrix.postScale(1.0f, -1.0f);
                break;
            case 6:
                matrix.postRotate(90.0f);
                break;
            case 7:
                matrix.postRotate(-90.0f);
                matrix.postScale(1.0f, -1.0f);
                break;
            case 8:
                matrix.postRotate(-90.0f);
                break;
        }
        return matrix;
    }

    public static int getExifOrientationFromAngle(int angle) {
        switch (angle % 360) {
            case 90:
                return 6;
            case 180:
                return 3;
            case 270:
                return 8;
            default:
                return 1;
        }
    }

    @TargetApi(19)
    public static Uri ensureUriPermission(Context context, Intent intent) {
        Uri uri = intent.getData();
        if (VERSION.SDK_INT >= 19) {
            context.getContentResolver().takePersistableUriPermission(uri, intent.getFlags() & 1);
        }
        return uri;
    }

    @TargetApi(19)
    public static File getFileFromUri(Context context, Uri uri) {
        boolean isKitkat;
        String filePath = null;
        if (VERSION.SDK_INT >= 19) {
            isKitkat = true;
        } else {
            isKitkat = false;
        }
        if (isKitkat && DocumentsContract.isDocumentUri(context, uri)) {
            String[] split;
            if (isExternalStorageDocument(uri)) {
                split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    filePath = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                filePath = getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else if (isMediaDocument(uri)) {
                String type = DocumentsContract.getDocumentId(uri).split(":")[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                filePath = getDataColumn(context, contentUri, "_id=?", new String[]{split[1]});
            } else if (isGoogleDriveDocument(uri)) {
                return getGoogleDriveFile(context, uri);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            filePath = isGooglePhotosUri(uri) ? uri.getLastPathSegment() : getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        if (filePath != null) {
            return new File(filePath);
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data", "_display_name"}, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex;
                if (uri.toString().startsWith("content://com.google.android.gallery3d")) {
                    columnIndex = cursor.getColumnIndex("_display_name");
                } else {
                    columnIndex = cursor.getColumnIndex("_data");
                }
                if (columnIndex != -1) {
                    String string = cursor.getString(columnIndex);
                    return string;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isGoogleDriveDocument(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    private static File getGoogleDriveFile(Context context, Uri uri) {
        FileOutputStream output;
        Throwable th;
        if (uri == null) {
            return null;
        }
        FileInputStream input = null;
        FileOutputStream output2 = null;
        String filePath = new File(context.getCacheDir(), "tmp").getAbsolutePath();
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd == null) {
                closeQuietly(null);
                closeQuietly(null);
                return null;
            }
            FileInputStream input2 = new FileInputStream(pfd.getFileDescriptor());
            try {
                output = new FileOutputStream(filePath);
            } catch (IOException e) {
                input = input2;
                closeQuietly(input);
                closeQuietly(output2);
                return null;
            } catch (Throwable th2) {
                th = th2;
                input = input2;
                closeQuietly(input);
                closeQuietly(output2);
                throw th;
            }
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int read = input2.read(bytes);
                    if (read != -1) {
                        output.write(bytes, 0, read);
                    } else {
                        File file = new File(filePath);
                        closeQuietly(input2);
                        closeQuietly(output);
                        return file;
                    }
                }
            } catch (IOException e2) {
                output2 = output;
                input = input2;
                closeQuietly(input);
                closeQuietly(output2);
                return null;
            } catch (Throwable th3) {
                th = th3;
                output2 = output;
                input = input2;
                closeQuietly(input);
                closeQuietly(output2);
                throw th;
            }
        } catch (IOException e3) {
            closeQuietly(input);
            closeQuietly(output2);
            return null;
        } catch (Throwable th4) {
            th = th4;
            closeQuietly(input);
            closeQuietly(output2);
            throw th;
        }
    }

    public static Bitmap decodeSampledBitmapFromUri(Context context, Uri sourceUri, int requestSize) {
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(sourceUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Options options = new Options();
        options.inSampleSize = calculateInSampleSize(context, sourceUri, requestSize);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static int calculateInSampleSize(Context context, Uri sourceUri, int requestSize) {
        InputStream is = null;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            is = context.getContentResolver().openInputStream(sourceUri);
            BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
        } finally {
            closeQuietly(is);
        }
        int inSampleSize = 1;
        sInputImageWidth = options.outWidth;
        sInputImageHeight = options.outHeight;
        while (true) {
            if (options.outWidth / inSampleSize <= requestSize && options.outHeight / inSampleSize <= requestSize) {
                return inSampleSize;
            }
            inSampleSize *= 2;
        }
    }

    public static Bitmap getScaledBitmapForHeight(Bitmap bitmap, int outHeight) {
        return getScaledBitmap(bitmap, Math.round(((float) outHeight) * (((float) bitmap.getWidth()) / ((float) bitmap.getHeight()))), outHeight);
    }

    public static Bitmap getScaledBitmapForWidth(Bitmap bitmap, int outWidth) {
        return getScaledBitmap(bitmap, outWidth, Math.round(((float) outWidth) / (((float) bitmap.getWidth()) / ((float) bitmap.getHeight()))));
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap, int outWidth, int outHeight) {
        int currentWidth = bitmap.getWidth();
        int currentHeight = bitmap.getHeight();
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.postScale(((float) outWidth) / ((float) currentWidth), ((float) outHeight) / ((float) currentHeight));
        return Bitmap.createBitmap(bitmap, 0, 0, currentWidth, currentHeight, scaleMatrix, true);
    }

    public static int getMaxSize() {
        int[] arr = new int[1];
        GLES10.glGetIntegerv(3379, arr, 0);
        if (arr[0] > 0) {
            return Math.min(arr[0], 4096);
        }
        return 2048;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }
}
