package com.sina.weibo.sdk.statistic;

import android.os.Environment;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class LogFileUtil {
    public static final String ANALYTICS_FILE_NAME = "app_logs";
    private static final String ANALYTICS_FILE_SUFFIX = ".txt";
    private static final String SDCARD_WEIBO_ANALYTICS_DIR = "/sina/weibo/.applogs/";

    LogFileUtil() {
    }

    public static String getAppLogs(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        return readStringFromFile(filePath);
    }

    public static String getAppLogPath(String fileName) {
        String filePath = "";
        String parent = "";
        if (LogReport.getPackageName() != null) {
            parent = new StringBuilder(String.valueOf(MD5.hexdigest(LogReport.getPackageName()))).append("/").toString();
        }
        return getSDPath() + SDCARD_WEIBO_ANALYTICS_DIR + parent + fileName + ANALYTICS_FILE_SUFFIX;
    }

    private static String getSDPath() {
        File sdDir = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            return sdDir.toString();
        }
        return null;
    }

    private static String readStringFromFile(String path) {
        IOException e;
        OutOfMemoryError e2;
        Throwable th;
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        File file = new File(path);
        if (!file.isFile() || !file.exists()) {
            return "";
        }
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder((int) file.length());
        try {
            BufferedReader reader2 = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String temp = reader2.readLine();
                    if (temp == null) {
                        break;
                    }
                    content.append(temp);
                } catch (IOException e3) {
                    e = e3;
                    reader = reader2;
                } catch (OutOfMemoryError e4) {
                    e2 = e4;
                    reader = reader2;
                } catch (Throwable th2) {
                    th = th2;
                    reader = reader2;
                }
            }
            if (reader2 != null) {
                try {
                    reader2.close();
                    reader = reader2;
                } catch (IOException e5) {
                    reader = reader2;
                }
            }
        } catch (IOException e6) {
            e = e6;
            try {
                e.printStackTrace();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e7) {
                    }
                }
                return content.toString();
            } catch (Throwable th3) {
                th = th3;
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e8) {
                    }
                }
                throw th;
            }
        } catch (OutOfMemoryError e9) {
            e2 = e9;
            e2.printStackTrace();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e10) {
                }
            }
            return content.toString();
        }
        return content.toString();
    }

    public static synchronized void writeToFile(String filePath, String content, boolean isAppend) {
        Throwable th;
        synchronized (LogFileUtil.class) {
            if (!TextUtils.isEmpty(filePath)) {
                LogUtil.m3309i(WBAgent.TAG, "filePath:" + filePath);
                if (!(content == null || content.length() == 0)) {
                    StringBuilder sb = new StringBuilder(content);
                    if (sb.charAt(0) == '[') {
                        sb.replace(0, 1, "");
                    }
                    if (sb.charAt(sb.length() - 1) != ',') {
                        sb.replace(sb.length() - 1, sb.length(), ",");
                    }
                    File file = new File(filePath);
                    FileWriter fileWriter = null;
                    try {
                        File parent = file.getParentFile();
                        if (!parent.exists()) {
                            parent.mkdirs();
                        }
                        if (!file.exists()) {
                            file.createNewFile();
                        } else if (file.lastModified() > 0 && System.currentTimeMillis() - file.lastModified() > LogBuilder.MAX_INTERVAL) {
                            isAppend = false;
                        }
                        FileWriter fileWriter2 = new FileWriter(file, isAppend);
                        try {
                            fileWriter2.write(sb.toString());
                            fileWriter2.flush();
                            if (fileWriter2 != null) {
                                try {
                                    fileWriter2.close();
                                    fileWriter = fileWriter2;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            fileWriter = fileWriter2;
                        } catch (IOException e2) {
                            fileWriter = fileWriter2;
                            if (fileWriter != null) {
                                try {
                                    fileWriter.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileWriter = fileWriter2;
                            if (fileWriter != null) {
                                try {
                                    fileWriter.close();
                                } catch (IOException e32) {
                                    e32.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e4) {
                        if (fileWriter != null) {
                            fileWriter.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (fileWriter != null) {
                            fileWriter.close();
                        }
                        throw th;
                    }
                }
            }
        }
    }

    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        file.delete();
        return true;
    }
}
