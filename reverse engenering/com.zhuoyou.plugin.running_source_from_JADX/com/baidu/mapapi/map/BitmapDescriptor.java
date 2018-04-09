package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class BitmapDescriptor {
    Bitmap f1053a;
    private Bundle f1054b;

    BitmapDescriptor(Bitmap bitmap) {
        if (bitmap != null) {
            this.f1053a = m1103a(bitmap, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    private Bitmap m1103a(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    byte[] m1104a() {
        Buffer allocate = ByteBuffer.allocate((this.f1053a.getWidth() * this.f1053a.getHeight()) * 4);
        this.f1053a.copyPixelsToBuffer(allocate);
        return allocate.array();
    }

    Bundle m1105b() {
        if (this.f1053a == null) {
            throw new IllegalStateException("the bitmap has been recycled! you can not use it again");
        }
        if (this.f1054b == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("image_width", this.f1053a.getWidth());
            bundle.putInt("image_height", this.f1053a.getHeight());
            byte[] a = m1104a();
            bundle.putByteArray("image_data", a);
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            messageDigest.update(a, 0, a.length);
            byte[] digest = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder("");
            for (byte b : digest) {
                stringBuilder.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            bundle.putString("image_hashcode", stringBuilder.toString());
            this.f1054b = bundle;
        }
        return this.f1054b;
    }

    public Bitmap getBitmap() {
        return this.f1053a;
    }

    public void recycle() {
        if (this.f1053a != null && !this.f1053a.isRecycled()) {
            this.f1053a.recycle();
            this.f1053a = null;
        }
    }
}
