package com.zhuoyou.plugin.cloud;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CvsUtils {
    private static final String Tag = "CvsUtils";

    public static void deleteFolder(File dir) {
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
        dir.renameTo(to);
        if (to.isDirectory()) {
            String[] children = to.list();
            for (String file : children) {
                File temp = new File(to, file);
                if (temp.isDirectory()) {
                    deleteFolder(temp);
                } else if (!temp.delete()) {
                    Log.d("deleteSDCardFolder", "DELETE FAIL");
                }
            }
            to.delete();
        }
    }

    public static String GetDir() {
        String emove_dir;
        if (Environment.getExternalStorageState().equals("mounted")) {
            emove_dir = Environment.getExternalStorageDirectory() + "/emove_gps";
        } else {
            emove_dir = "com/zhuoyou/plugin/running/emove_tmp";
        }
        File destDir = new File(emove_dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return emove_dir;
    }

    public static void DBTableToFile(Context mCtx, String accountId, String filePath, String fileName, Uri tableUri, String[] projection, String selection, String[] selectionArgs, String sortOrder) throws IOException {
        Cursor mCursor = mCtx.getContentResolver().query(tableUri, projection, selection, selectionArgs, sortOrder);
        if (mCursor == null || mCursor.getCount() == 0) {
            mCursor.close();
            return;
        }
        File fileDirs = new File(filePath);
        if (!fileDirs.exists()) {
            fileDirs.mkdirs();
        }
        File txtFile = new File(filePath, fileName);
        if (!txtFile.exists()) {
            txtFile.createNewFile();
        }
        FileWriter mFw = new FileWriter(txtFile);
        BufferedWriter mBW = new BufferedWriter(mFw);
        while (mCursor.moveToNext()) {
            for (int i = 0; i < mCursor.getColumnCount(); i++) {
                if (i == 0) {
                    mBW.write(mCursor.getString(0) + ',' + accountId + ',');
                } else if (i == mCursor.getColumnCount() - 1) {
                    res = mCursor.getString(i);
                    if (!TextUtils.isEmpty(res)) {
                        mBW.write(getValue(mCursor, i, res));
                    }
                } else {
                    res = mCursor.getString(i);
                    if (TextUtils.isEmpty(res)) {
                        mBW.write(44);
                    } else {
                        mBW.write(getValue(mCursor, i, res) + ',');
                    }
                }
            }
            mBW.newLine();
        }
        mCursor.close();
        mBW.flush();
        mBW.close();
        mFw.close();
    }

    private static String getValue(Cursor mCursor, int columnIndex, String defRes) {
        if (mCursor.getType(columnIndex) == 2) {
            return "" + mCursor.getDouble(columnIndex);
        }
        return defRes;
    }

    public static File doZip(String sourceDir, String zipFilePath) throws IOException {
        Throwable th;
        File file = new File(sourceDir);
        File zipFile = new File(zipFilePath);
        ZipOutputStream zos = null;
        try {
            ZipOutputStream zos2 = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            try {
                String basePath;
                if (file.isDirectory()) {
                    basePath = file.getPath();
                } else {
                    basePath = file.getParent();
                }
                zipFile(file, basePath, zos2);
                if (zos2 != null) {
                    zos2.closeEntry();
                    zos2.close();
                }
                return zipFile;
            } catch (Throwable th2) {
                th = th2;
                zos = zos2;
                if (zos != null) {
                    zos.closeEntry();
                    zos.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (zos != null) {
                zos.closeEntry();
                zos.close();
            }
            throw th;
        }
    }

    private static void zipFile(File source, String basePath, ZipOutputStream zos) throws IOException {
        Throwable th;
        int i = 0;
        File[] files = source.isDirectory() ? source.listFiles() : new File[]{source};
        InputStream inputStream = null;
        byte[] buf = new byte[1024];
        int length = files.length;
        InputStream is = null;
        while (i < length) {
            File file = files[i];
            if (file.isDirectory()) {
                zos.putNextEntry(new ZipEntry(file.getPath().substring(basePath.length() + 1) + "/"));
                zipFile(file, basePath, zos);
                inputStream = is;
            } else {
                try {
                    String pathName = file.getPath().substring(basePath.length() + 1);
                    inputStream = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    zos.putNextEntry(new ZipEntry(pathName));
                    while (true) {
                        int length2 = bis.read(buf);
                        if (length2 > 0) {
                            zos.write(buf, 0, length2);
                        } else {
                            try {
                                break;
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                    }
                    bis.close();
                } catch (Throwable th3) {
                    th = th3;
                    inputStream = is;
                }
            }
            i++;
            is = inputStream;
        }
        if (is != null) {
            is.close();
            return;
        }
        return;
        if (inputStream != null) {
            inputStream.close();
        }
        throw th;
    }

    public static void CVSUnzipFile(String zipFilePath, String outFilePath) throws Exception {
        Log.i(Tag, "into CVSUnzipFile");
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFilePath));
        String szName = "";
        File decodepath = new File(outFilePath);
        if (!decodepath.exists()) {
            decodepath.mkdirs();
        } else if (!decodepath.isDirectory()) {
            decodepath.delete();
            decodepath.mkdir();
        }
        Log.i(Tag, "into CVSUnzipFile1");
        while (true) {
            ZipEntry zipEntry = inZip.getNextEntry();
            if (zipEntry != null) {
                szName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    new File(outFilePath + File.separator + szName.substring(0, szName.length() - 1)).mkdirs();
                } else {
                    File file = new File(outFilePath + File.separator + szName);
                    file.createNewFile();
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = inZip.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                }
            } else {
                inZip.close();
                return;
            }
        }
    }

    public static Boolean unZip(String filePath, String outPath) {
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(filePath));
            while (true) {
                ZipEntry entry = zin.getNextEntry();
                if (entry == null) {
                    return Boolean.valueOf(true);
                }
                if (entry.isDirectory()) {
                    File directory = new File(outPath, entry.getName());
                    if (!directory.exists() && !directory.mkdirs()) {
                        return Boolean.valueOf(false);
                    }
                    zin.closeEntry();
                } else {
                    FileOutputStream fout = new FileOutputStream(outPath + new File(entry.getName()).getPath());
                    DataOutputStream dout = new DataOutputStream(fout);
                    byte[] b = new byte[1024];
                    while (true) {
                        int len = zin.read(b);
                        if (len == -1) {
                            break;
                        }
                        dout.write(b, 0, len);
                    }
                    dout.close();
                    fout.close();
                    zin.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    public static ArrayList<ArrayList<String>> parseFile(String filePath, String fileName) {
        Exception e;
        File file = new File(filePath + "/" + fileName);
        if (!file.exists() || file == null) {
            return null;
        }
        ArrayList<ArrayList<String>> mResultList = new ArrayList();
        ArrayList<String> subList = new ArrayList();
        try {
            BufferedReader mBreader = new BufferedReader(new FileReader(file));
            ArrayList<String> subList2 = subList;
            while (true) {
                try {
                    String lineString = mBreader.readLine();
                    if (lineString != null) {
                        int point = 0;
                        if (lineString == null || lineString.equals("")) {
                            subList = subList2;
                        } else {
                            for (int i = 0; i < lineString.length(); i++) {
                                if (lineString.charAt(i) == ',') {
                                    subList2.add(lineString.substring(point, i));
                                    point = i + 1;
                                }
                            }
                            subList2.add(lineString.substring(lineString.lastIndexOf(SeparatorConstants.SEPARATOR_ADS_ID) + 1, lineString.length()));
                            mResultList.add(subList2);
                            subList = new ArrayList();
                        }
                        subList2 = subList;
                    } else {
                        mBreader.close();
                        subList = subList2;
                        return mResultList;
                    }
                } catch (Exception e2) {
                    e = e2;
                    subList = subList2;
                }
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return mResultList;
        }
    }
}
