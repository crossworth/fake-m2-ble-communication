package com.zhuoyi.system.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.zhuoyi.system.util.constant.FileConstants;
import com.zhuoyi.system.util.model.Count;
import com.zhuoyi.system.util.model.Size;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static final String CONFIG_FILE = ".config";

    public static void getFileListBySuffix(ArrayList<File> targetList, File dir, String suffix) {
        if (suffix != null && !TextUtils.isEmpty(suffix.trim())) {
            try {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (!f.isFile()) {
                            getFileListBySuffix(targetList, f, suffix);
                        } else if (f.getName().toLowerCase().endsWith(suffix.toLowerCase())) {
                            targetList.add(f);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void getFileListByName(ArrayList<File> targetList, File dir, String fileName) {
        if (fileName != null && !TextUtils.isEmpty(fileName.trim())) {
            try {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (!f.isFile()) {
                            getFileListByName(targetList, f, fileName);
                        } else if (f.getName().toLowerCase().equals(fileName.toLowerCase())) {
                            targetList.add(f);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void isFileInDir(File dir, String fileName, boolean isFile, StringBuffer ret) {
        if (!TextUtils.isEmpty(fileName.trim()) && TextUtils.isEmpty(ret.toString().trim())) {
            try {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (isFile) {
                            if (!f.isFile()) {
                                isFileInDir(f, fileName, isFile, ret);
                            } else if (fileName.equalsIgnoreCase(f.getName())) {
                                ret.append(f.getAbsolutePath());
                                return;
                            }
                        } else if (f.isDirectory()) {
                            if (fileName.equalsIgnoreCase(f.getName())) {
                                ret.append(f.getAbsolutePath());
                            } else {
                                isFileInDir(f, fileName, isFile, ret);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        copyStream(new FileInputStream(sourceFile), new FileOutputStream(targetFile));
    }

    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        byte[] b = new byte[2048];
        while (true) {
            int len = bis.read(b);
            if (len == -1) {
                bos.flush();
                bis.close();
                bos.close();
                is.close();
                os.close();
                return;
            }
            bos.write(b, 0, len);
        }
    }

    public static void copyStream(InputStream is, OutputStream os, Size size) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        byte[] b = new byte[2048];
        while (true) {
            int len = bis.read(b);
            if (len == -1) {
                bos.flush();
                bis.close();
                bos.close();
                is.close();
                os.close();
                return;
            }
            bos.write(b, 0, len);
            if (size != null) {
                size.setFileSize(size.getFileSize() + ((long) len));
            }
        }
    }

    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        File fileTargetDir = new File(targetDir);
        if (!fileTargetDir.exists()) {
            fileTargetDir.mkdirs();
        }
        File[] file = new File(sourceDir).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                copyFile(file[i], new File(new StringBuilder(String.valueOf(new File(targetDir).getAbsolutePath())).append(File.separator).append(file[i].getName()).toString()));
            }
            if (file[i].isDirectory()) {
                copyDirectiory(new StringBuilder(String.valueOf(sourceDir)).append("/").append(file[i].getName()).toString(), new StringBuilder(String.valueOf(targetDir)).append("/").append(file[i].getName()).toString());
            }
        }
    }

    public static void getAllFilePath(List<String> pathList, File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFilePath(pathList, f);
                } else if (f.isFile()) {
                    pathList.add(f.getAbsolutePath());
                }
            }
        }
    }

    public static String readFile(File f) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        while (true) {
            String readoneline = br.readLine();
            if (readoneline == null) {
                br.close();
                isr.close();
                return sb.toString();
            }
            sb.append(readoneline);
        }
    }

    public static String readFile(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        while (true) {
            String readoneline = br.readLine();
            if (readoneline == null) {
                br.close();
                isr.close();
                return sb.toString();
            }
            sb.append(readoneline);
        }
    }

    public static void writeFile(File file, String content, boolean isAppend) throws IOException {
        FileWriter fw = new FileWriter(file, isAppend);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.flush();
        bw.close();
        fw.close();
    }

    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        while (true) {
            int ch = is.read();
            if (ch == -1) {
                byte[] imgdata = bytestream.toByteArray();
                bytestream.close();
                return imgdata;
            }
            bytestream.write(ch);
        }
    }

    public static int getFileSize(String fileName) {
        int ret = 0;
        try {
            FileInputStream fis = new FileInputStream(new File(fileName));
            ret = fis.available();
            fis.close();
            return ret;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ret;
        } catch (IOException e2) {
            e2.printStackTrace();
            return ret;
        }
    }

    public static Object readObjectFromFile(String fileName) {
        Object obj = null;
        try {
            FileInputStream freader = new FileInputStream(fileName);
            obj = new ObjectInputStream(freader).readObject();
            freader.close();
            return obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return obj;
        } catch (IOException e2) {
            e2.printStackTrace();
            return obj;
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return obj;
        }
    }

    public static void writeObjectToFile(String fileName, Object srcObject, boolean isAppend) {
        try {
            FileOutputStream outStream = new FileOutputStream(fileName, isAppend);
            new ObjectOutputStream(outStream).writeObject(srcObject);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static boolean deleteDirectory(String sPath) {
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else {
                    deleteDirectory(files[i].getAbsolutePath());
                }
            }
        }
        if (dirFile.delete()) {
            return true;
        }
        return false;
    }

    public static void CopyAssets(Context context, String assetDir, String dir, Count count, boolean reserveRoot) {
        try {
            String[] files = context.getResources().getAssets().list(assetDir);
            if (!reserveRoot) {
                dir = dir.replace("/" + assetDir, "");
            }
            File mWorkingPath = new File(dir);
            if (!mWorkingPath.exists()) {
                mWorkingPath.mkdirs();
            }
            for (String fileName : files) {
                try {
                    if (fileName.contains(".")) {
                        File outFile = new File(mWorkingPath, fileName);
                        if (!outFile.exists()) {
                            outFile.createNewFile();
                        }
                        InputStream in = context.getAssets().open(new StringBuilder(String.valueOf(assetDir)).append("/").append(fileName).toString());
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];
                        while (true) {
                            int len = in.read(buf);
                            if (len <= 0) {
                                break;
                            }
                            out.write(buf, 0, len);
                        }
                        in.close();
                        out.close();
                        count.setCount(count.getCount() + 1);
                    } else {
                        CopyAssets(context, new StringBuilder(String.valueOf(assetDir)).append("/").append(fileName).toString(), new StringBuilder(String.valueOf(dir)).append("/").append(fileName).append("/").toString(), count, reserveRoot);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
        }
    }

    public static void CopyAssets(Context context, String assetDir, String dir, Count count, boolean reserveRoot, Size size) {
        try {
            String[] files = context.getResources().getAssets().list(assetDir);
            if (!reserveRoot) {
                dir = dir.replace("/" + assetDir, "");
            }
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (String fileName : files) {
                try {
                    if (fileName.contains(".")) {
                        file = new File(file, fileName);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        InputStream in = context.getAssets().open(new StringBuilder(String.valueOf(assetDir)).append("/").append(fileName).toString());
                        OutputStream out = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        while (true) {
                            int len = in.read(buf);
                            if (len <= 0) {
                                break;
                            }
                            out.write(buf, 0, len);
                            size.setFileSize(size.getFileSize() + ((long) len));
                        }
                        in.close();
                        out.close();
                        count.setCount(count.getCount() + 1);
                    } else {
                        CopyAssets(context, new StringBuilder(String.valueOf(assetDir)).append("/").append(fileName).toString(), new StringBuilder(String.valueOf(dir)).append("/").append(fileName).append("/").toString(), count, reserveRoot, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
        }
    }

    public static File getDebugFile() {
        return new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append(File.separator).append("debug").append(File.separator).append(EncryptUtils.getDebugFileName()).toString());
    }

    public static void modifyString(String path, String original, String newString) {
        write(path, readAndModifyString(path, original, newString));
    }

    public static String readAndModifyString(String filePath, String original, String newString) {
        Exception e;
        Throwable th;
        BufferedReader br = null;
        StringBuffer buf = new StringBuffer();
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(filePath));
            while (true) {
                try {
                    String line = br2.readLine();
                    if (line == null) {
                        break;
                    }
                    buf.append(line.replaceAll(original, newString));
                    buf.append("\n");
                } catch (Exception e2) {
                    e = e2;
                    br = br2;
                } catch (Throwable th2) {
                    th = th2;
                    br = br2;
                }
            }
            if (br2 != null) {
                try {
                    br2.close();
                    br = br2;
                } catch (IOException e3) {
                }
            }
        } catch (Exception e4) {
            e = e4;
            try {
                e.printStackTrace();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e5) {
                    }
                }
                return buf.toString();
            } catch (Throwable th3) {
                th = th3;
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e6) {
                    }
                }
                throw th;
            }
        }
        return buf.toString();
    }

    public static void write(String filePath, String content) {
        Exception e;
        Throwable th;
        BufferedWriter bw = null;
        try {
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(filePath));
            try {
                bw2.write(content);
                if (bw2 != null) {
                    try {
                        bw2.close();
                        bw = bw2;
                        return;
                    } catch (IOException e2) {
                        return;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                bw = bw2;
                try {
                    e.printStackTrace();
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e4) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e5) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bw = bw2;
                if (bw != null) {
                    bw.close();
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            e.printStackTrace();
            if (bw != null) {
                bw.close();
            }
        }
    }

    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(new File(zipFileString)));
        String szName = "";
        while (true) {
            ZipEntry zipEntry = inZip.getNextEntry();
            if (zipEntry == null) {
                inZip.close();
                return;
            }
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                new File(new StringBuilder(String.valueOf(outPathString)).append(File.separator).append(szName.substring(0, szName.length() - 1)).toString()).mkdirs();
            } else {
                File file = new File(new StringBuilder(String.valueOf(outPathString)).append(File.separator).append(szName).toString());
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
        }
    }

    public static String getConfigByNameFromFile(String name) {
        Exception e;
        String ret = null;
        Properties p = new Properties();
        File file = new File("/sdcard/" + FileConstants.FILE_ROOT + "/" + CONFIG_FILE);
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                FileInputStream fileInputStream;
                try {
                    p.load(fis);
                    ret = p.getProperty(name);
                    fileInputStream = fis;
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream = fis;
                    e.printStackTrace();
                    return ret;
                }
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return ret;
        }
        return ret;
    }

    public static void putConfigToFile(String name, String value) {
        Properties p = new Properties();
        File file = new File("/sdcard/" + FileConstants.FILE_ROOT + "/" + CONFIG_FILE);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            p.load(new FileInputStream(file));
            p.setProperty(name, value);
            p.store(new FileOutputStream(file), "");
        } catch (Exception e) {
            Logger.m3375p(e);
        }
    }

    public static boolean hasFileInAssets(Context context, String fileName) {
        try {
            String[] names = context.getAssets().list("");
            for (String equals : names) {
                if (equals.equals(fileName.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

    public static File getFileFromAssets(Context context, String fileName) {
        IOException e;
        File file = null;
        try {
            File f = new File("/sdcard/" + FileConstants.FILE_ROOT);
            if (!f.exists()) {
                f.mkdirs();
            }
            InputStream is = context.getAssets().open(fileName);
            File file2 = new File("/sdcard/" + FileConstants.FILE_ROOT + "/" + fileName);
            try {
                file2.createNewFile();
                FileOutputStream fos = new FileOutputStream(file2);
                byte[] temp = new byte[1024];
                while (true) {
                    int i = is.read(temp);
                    if (i <= 0) {
                        fos.close();
                        is.close();
                        return file2;
                    }
                    fos.write(temp, 0, i);
                }
            } catch (IOException e2) {
                e = e2;
                file = file2;
                Logger.m3375p(e);
                return file;
            }
        } catch (IOException e3) {
            e = e3;
            Logger.m3375p(e);
            return file;
        }
    }
}
