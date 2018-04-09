package com.zhuoyou.plugin.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.SocialConstants;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.io.ByteArrayOutputStream;

public class ShareToWeixin {
    public static final String APP_ID = "wx9e68ecf43e6b8493";
    public static final String APP_KEY = "90aeacf25a411cc51265c0161803daa5";
    private static final int THUMB_SIZE = 300;
    public static IWXAPI api = WXAPIFactory.createWXAPI(RunningApp.getInstance().getApplicationContext(), APP_ID, true);

    public static void regToWx() {
        api.registerApp(APP_ID);
    }

    public static void SharetoWX(Context context, boolean isFriend, String fileName) {
        int i = 1;
        regToWx();
        Bitmap bmp = Tools.convertFileToBitmap("/Running/share/" + fileName);
        if (bmp != null) {
            WXImageObject imageObject = new WXImageObject(bmp);
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imageObject;
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 300, (bmp.getHeight() * 300) / bmp.getWidth(), true);
            bmp.recycle();
            msg.thumbData = compressImage(thumbBmp);
            Req req = new Req();
            req.transaction = buildTransaction(SocialConstants.PARAM_IMG_URL);
            req.message = msg;
            if (!isFriend) {
                i = 0;
            }
            req.scene = i;
            api.sendReq(req);
        }
    }

    private static byte[] compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 32) {
            baos.reset();
            image.compress(CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        return baos.toByteArray();
    }

    private static String buildTransaction(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
