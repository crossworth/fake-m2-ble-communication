package com.zhuoyou.plugin.cloud;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SportsAlbumSync {
    private Context mContext;

    public SportsAlbumSync(Context con) {
        this.mContext = con;
    }

    private byte[] getUriToBytes(String uri) throws IOException {
        InputStream inStream = new FileInputStream(uri);
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while (true) {
            int len = inStream.read(buffer);
            if (len != -1) {
                outStream.write(buffer, 0, len);
            } else {
                byte[] data = outStream.toByteArray();
                outStream.close();
                inStream.close();
                return data;
            }
        }
    }
}
