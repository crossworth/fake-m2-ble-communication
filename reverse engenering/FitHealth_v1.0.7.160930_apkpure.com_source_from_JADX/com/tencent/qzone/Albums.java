package com.tencent.qzone;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.HttpUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ProGuard */
public class Albums extends BaseApi {

    /* compiled from: ProGuard */
    public enum AlbumSecurity {
        publicToAll("1"),
        privateOnly("2"),
        friendsOnly("4"),
        needQuestion("5");
        
        private final String f2763a;

        private AlbumSecurity(String str) {
            this.f2763a = str;
        }

        public String getSecurity() {
            return this.f2763a;
        }
    }

    public Albums(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
    }

    public Albums(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public void listAlbum(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, this.mContext, "photo/list_album", composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }

    public void listPhotos(String str, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str2 = "albumid";
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str2, str);
        HttpUtils.requestAsync(this.mToken, this.mContext, "photo/list_photo", composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void uploadPicture(String str, String str2, String str3, String str4, String str5, IUiListener iUiListener) {
        Object tempRequestListener = new TempRequestListener(iUiListener);
        try {
            InputStream fileInputStream = new FileInputStream(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.close();
            fileInputStream.close();
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            Bundle composeCGIParams = composeCGIParams();
            File file = new File(str);
            composeCGIParams.putByteArray("picture", toByteArray);
            String str6 = "photodesc";
            if (str2 == null) {
                str2 = "";
            }
            composeCGIParams.putString(str6, str2);
            composeCGIParams.putString("title", file.getName());
            if (str3 != null) {
                str6 = "albumid";
                if (str3 == null) {
                    str3 = "";
                }
                composeCGIParams.putString(str6, str3);
            }
            str6 = "x";
            if (str4 == null) {
                str4 = "";
            }
            composeCGIParams.putString(str6, str4);
            str6 = "y";
            if (str5 == null) {
                str5 = "";
            }
            composeCGIParams.putString(str6, str5);
            HttpUtils.requestAsync(this.mToken, this.mContext, "photo/upload_pic", composeCGIParams, "POST", tempRequestListener);
        } catch (IOException e) {
            tempRequestListener.onIOException(e);
        }
    }

    public void addAlbum(String str, String str2, AlbumSecurity albumSecurity, String str3, String str4, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str5 = "albumname";
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str5, str);
        str5 = "albumdesc";
        if (str2 == null) {
            str2 = "";
        }
        composeCGIParams.putString(str5, str2);
        composeCGIParams.putString("priv", albumSecurity == null ? AlbumSecurity.publicToAll.getSecurity() : albumSecurity.getSecurity());
        str5 = "question";
        if (str3 == null) {
            str3 = "";
        }
        composeCGIParams.putString(str5, str3);
        str5 = "answer";
        if (str4 == null) {
            str4 = "";
        }
        composeCGIParams.putString(str5, str4);
        HttpUtils.requestAsync(this.mToken, this.mContext, "photo/add_album", composeCGIParams, "POST", new TempRequestListener(iUiListener));
    }
}
