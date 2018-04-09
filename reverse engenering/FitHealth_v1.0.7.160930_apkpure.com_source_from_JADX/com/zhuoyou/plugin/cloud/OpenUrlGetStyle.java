package com.zhuoyou.plugin.cloud;

import android.util.Log;
import com.zhuoyou.plugin.selfupdate.DESUtil;
import com.zhuoyou.plugin.selfupdate.ZipUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;

public class OpenUrlGetStyle {
    public static String ENCODE_DECODE_KEY = "x_s0_s22";

    public static String accessNetworkByPost(String urlString, String contents) throws IOException {
        Exception e;
        DataOutputStream dataOutputStream;
        Throwable th;
        String line = "";
        BufferedInputStream bis = null;
        ByteArrayBuffer baf = null;
        HttpURLConnection connection = null;
        try {
            BufferedInputStream bis2;
            byte[] encrypted = DESUtil.encrypt(contents.getBytes("utf-8"), ENCODE_DECODE_KEY.getBytes());
            connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(20000);
            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("contentType", "utf-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + encrypted.length);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            try {
                out.write(encrypted);
                out.flush();
                out.close();
                bis2 = new BufferedInputStream(connection.getInputStream());
            } catch (Exception e2) {
                e = e2;
                dataOutputStream = out;
                try {
                    e.printStackTrace();
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    return line.trim();
                } catch (Throwable th2) {
                    th = th2;
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream = out;
                if (connection != null) {
                    connection.disconnect();
                }
                if (bis != null) {
                    bis.close();
                }
                if (baf != null) {
                    baf.clear();
                }
                throw th;
            }
            try {
                ByteArrayBuffer baf2 = new ByteArrayBuffer(1024);
                try {
                    boolean isPress = Boolean.valueOf(connection.getHeaderField("isPress")).booleanValue();
                    while (true) {
                        int current = bis2.read();
                        if (current == -1) {
                            break;
                        }
                        baf2.append((byte) current);
                    }
                    if (baf2.length() > 0) {
                        if (isPress) {
                            line = new String(ZipUtil.uncompress(DESUtil.decrypt(baf2.toByteArray(), ENCODE_DECODE_KEY.getBytes())));
                        } else {
                            line = new String(DESUtil.decrypt(baf2.toByteArray(), ENCODE_DECODE_KEY.getBytes()));
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis2 != null) {
                        bis2.close();
                    }
                    if (baf2 != null) {
                        baf2.clear();
                        baf = baf2;
                        bis = bis2;
                        dataOutputStream = out;
                    } else {
                        bis = bis2;
                        dataOutputStream = out;
                    }
                } catch (Exception e3) {
                    e = e3;
                    baf = baf2;
                    bis = bis2;
                    dataOutputStream = out;
                    e.printStackTrace();
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    return line.trim();
                } catch (Throwable th4) {
                    th = th4;
                    baf = baf2;
                    bis = bis2;
                    dataOutputStream = out;
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                bis = bis2;
                dataOutputStream = out;
                e.printStackTrace();
                if (connection != null) {
                    connection.disconnect();
                }
                if (bis != null) {
                    bis.close();
                }
                if (baf != null) {
                    baf.clear();
                }
                return line.trim();
            } catch (Throwable th5) {
                th = th5;
                bis = bis2;
                dataOutputStream = out;
                if (connection != null) {
                    connection.disconnect();
                }
                if (bis != null) {
                    bis.close();
                }
                if (baf != null) {
                    baf.clear();
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
            if (bis != null) {
                bis.close();
            }
            if (baf != null) {
                baf.clear();
            }
            return line.trim();
        }
        return line.trim();
    }

    public static String getAccessToken(String url, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = null;
        try {
            realUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection conn = null;
        try {
            conn = realUrl.openConnection();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", HTTP.CONN_KEEP_ALIVE);
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        try {
            out = new PrintWriter(conn.getOutputStream());
        } catch (IOException e22) {
            e22.printStackTrace();
        }
        out.print(params);
        out.flush();
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e222) {
            e222.printStackTrace();
        }
        while (true) {
            try {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                result = result + "/n" + line;
            } catch (IOException e2222) {
                e2222.printStackTrace();
            }
        }
        Log.i("zhao", "返回的是：" + result);
        return result;
    }
}
