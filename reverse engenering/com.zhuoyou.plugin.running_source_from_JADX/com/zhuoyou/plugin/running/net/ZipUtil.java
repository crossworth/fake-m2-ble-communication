package com.zhuoyou.plugin.running.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {
    public static byte[] compress(byte[] byteArray) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(byteArray);
        gzip.close();
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] byteArry) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream gunzip = new GZIPInputStream(new ByteArrayInputStream(byteArry));
        byte[] buffer = new byte[256];
        while (true) {
            int n = gunzip.read(buffer);
            if (n < 0) {
                return out.toByteArray();
            }
            out.write(buffer, 0, n);
        }
    }
}
