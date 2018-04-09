package org.andengine.util;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    public static void copyToExternalStorage(Context pContext, int pSourceResourceID, String pFilename) throws FileNotFoundException {
        copyToExternalStorage(pContext, pContext.getResources().openRawResource(pSourceResourceID), pFilename);
    }

    public static void copyToInternalStorage(Context pContext, int pSourceResourceID, String pFilename) throws FileNotFoundException {
        copyToInternalStorage(pContext, pContext.getResources().openRawResource(pSourceResourceID), pFilename);
    }

    public static void copyToExternalStorage(Context pContext, String pSourceAssetPath, String pFilename) throws IOException {
        copyToExternalStorage(pContext, pContext.getAssets().open(pSourceAssetPath), pFilename);
    }

    public static void copyToInternalStorage(Context pContext, String pSourceAssetPath, String pFilename) throws IOException {
        copyToInternalStorage(pContext, pContext.getAssets().open(pSourceAssetPath), pFilename);
    }

    private static void copyToInternalStorage(Context pContext, InputStream pInputStream, String pFilename) throws FileNotFoundException {
        StreamUtils.copyAndClose(pInputStream, new FileOutputStream(new File(pContext.getFilesDir(), pFilename)));
    }

    public static void copyToExternalStorage(InputStream pInputStream, String pFilePath) throws FileNotFoundException {
        if (isExternalStorageWriteable()) {
            StreamUtils.copyAndClose(pInputStream, new FileOutputStream(getAbsolutePathOnExternalStorage(pFilePath)));
            return;
        }
        throw new IllegalStateException("External Storage is not writeable.");
    }

    public static void copyToExternalStorage(Context pContext, InputStream pInputStream, String pFilePath) throws FileNotFoundException {
        if (isExternalStorageWriteable()) {
            StreamUtils.copyAndClose(pInputStream, new FileOutputStream(getAbsolutePathOnExternalStorage(pContext, pFilePath)));
            return;
        }
        throw new IllegalStateException("External Storage is not writeable.");
    }

    public static boolean isFileExistingOnExternalStorage(String pFilePath) {
        if (isExternalStorageReadable()) {
            File file = new File(getAbsolutePathOnExternalStorage(pFilePath));
            return file.exists() && file.isFile();
        } else {
            throw new IllegalStateException("External Storage is not readable.");
        }
    }

    public static boolean isFileExistingOnExternalStorage(Context pContext, String pFilePath) {
        if (isExternalStorageReadable()) {
            File file = new File(getAbsolutePathOnExternalStorage(pContext, pFilePath));
            return file.exists() && file.isFile();
        } else {
            throw new IllegalStateException("External Storage is not readable.");
        }
    }

    public static boolean isDirectoryExistingOnExternalStorage(Context pContext, String pDirectory) {
        if (isExternalStorageReadable()) {
            File file = new File(getAbsolutePathOnExternalStorage(pContext, pDirectory));
            return file.exists() && file.isDirectory();
        } else {
            throw new IllegalStateException("External Storage is not readable.");
        }
    }

    public static boolean ensureDirectoriesExistOnExternalStorage(Context pContext, String pDirectory) {
        if (isDirectoryExistingOnExternalStorage(pContext, pDirectory)) {
            return true;
        }
        if (isExternalStorageWriteable()) {
            return new File(getAbsolutePathOnExternalStorage(pContext, pDirectory)).mkdirs();
        }
        throw new IllegalStateException("External Storage is not writeable.");
    }

    public static InputStream openOnExternalStorage(String pFilePath) throws FileNotFoundException {
        return new FileInputStream(getAbsolutePathOnExternalStorage(pFilePath));
    }

    public static InputStream openOnExternalStorage(Context pContext, String pFilePath) throws FileNotFoundException {
        return new FileInputStream(getAbsolutePathOnExternalStorage(pContext, pFilePath));
    }

    public static String[] getDirectoryListOnExternalStorage(Context pContext, String pFilePath) {
        return new File(getAbsolutePathOnExternalStorage(pContext, pFilePath)).list();
    }

    public static String[] getDirectoryListOnExternalStorage(Context pContext, String pFilePath, FilenameFilter pFilenameFilter) {
        return new File(getAbsolutePathOnExternalStorage(pContext, pFilePath)).list(pFilenameFilter);
    }

    public static String getAbsolutePathOnInternalStorage(Context pContext, String pFilePath) {
        return pContext.getFilesDir().getAbsolutePath() + pFilePath;
    }

    public static String getAbsolutePathOnExternalStorage(String pFilePath) {
        return Environment.getExternalStorageDirectory() + "/" + pFilePath;
    }

    public static String getAbsolutePathOnExternalStorage(Context pContext, String pFilePath) {
        return Environment.getExternalStorageDirectory() + "/Android/data/" + pContext.getApplicationInfo().packageName + "/files/" + pFilePath;
    }

    public static boolean isExternalStorageWriteable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return state.equals("mounted") || state.equals("mounted_ro");
    }

    public static void copyFile(File pSourceFile, File pDestinationFile) throws IOException {
        Throwable th;
        InputStream in = null;
        OutputStream out = null;
        try {
            OutputStream out2;
            InputStream in2 = new FileInputStream(pSourceFile);
            try {
                out2 = new FileOutputStream(pDestinationFile);
            } catch (Throwable th2) {
                th = th2;
                in = in2;
                StreamUtils.close(in);
                StreamUtils.close(out);
                throw th;
            }
            try {
                StreamUtils.copy(in2, out2);
                StreamUtils.close(in2);
                StreamUtils.close(out2);
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                in = in2;
                StreamUtils.close(in);
                StreamUtils.close(out);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            StreamUtils.close(in);
            StreamUtils.close(out);
            throw th;
        }
    }

    public static boolean deleteDirectory(File pFileOrDirectory) {
        if (pFileOrDirectory.isDirectory()) {
            for (String file : pFileOrDirectory.list()) {
                if (!deleteDirectory(new File(pFileOrDirectory, file))) {
                    return false;
                }
            }
        }
        return pFileOrDirectory.delete();
    }
}
