package com.umeng.socialize.weixin.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WXAuthUtils {
    public static String request(String urlStr) {
        String emptyStr = "";
        try {
            URLConnection conn = new URL(urlStr).openConnection();
            if (conn != null) {
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                if (inputStream != null) {
                    emptyStr = convertStreamToString(inputStream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emptyStr;
    }

    private static String convertStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String result = "";
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                result = result + line;
            } else {
                inputStream.close();
                return result;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String convertStreamToString(java.io.InputStream r6) {
        /*
        r2 = new java.io.BufferedReader;
        r4 = new java.io.InputStreamReader;
        r4.<init>(r6);
        r2.<init>(r4);
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r1 = 0;
    L_0x0010:
        r1 = r2.readLine();	 Catch:{ IOException -> 0x002d }
        if (r1 == 0) goto L_0x0039;
    L_0x0016:
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x002d }
        r4.<init>();	 Catch:{ IOException -> 0x002d }
        r4 = r4.append(r1);	 Catch:{ IOException -> 0x002d }
        r5 = "/n";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x002d }
        r4 = r4.toString();	 Catch:{ IOException -> 0x002d }
        r3.append(r4);	 Catch:{ IOException -> 0x002d }
        goto L_0x0010;
    L_0x002d:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0047 }
        r6.close();	 Catch:{ IOException -> 0x0042 }
    L_0x0034:
        r4 = r3.toString();
        return r4;
    L_0x0039:
        r6.close();	 Catch:{ IOException -> 0x003d }
        goto L_0x0034;
    L_0x003d:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0034;
    L_0x0042:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0034;
    L_0x0047:
        r4 = move-exception;
        r6.close();	 Catch:{ IOException -> 0x004c }
    L_0x004b:
        throw r4;
    L_0x004c:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.weixin.net.WXAuthUtils.convertStreamToString(java.io.InputStream):java.lang.String");
    }
}
