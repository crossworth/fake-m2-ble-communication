package com.amap.api.mapcore.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import java.io.FileDescriptor;

/* compiled from: ImageResizer */
public class dc extends dd {
    protected int f4163a;
    protected int f4164b;

    public dc(Context context, int i, int i2) {
        super(context);
        m4205a(i, i2);
    }

    public void m4205a(int i, int i2) {
        this.f4163a = i;
        this.f4164b = i2;
    }

    private Bitmap m4201a(int i) {
        return m4202a(this.d, i, this.f4163a, this.f4164b, m520a());
    }

    protected Bitmap mo1640a(Object obj) {
        return m4201a(Integer.parseInt(String.valueOf(obj)));
    }

    public static Bitmap m4202a(Resources resources, int i, int i2, int i3, da daVar) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inSampleSize = m4200a(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, i, options);
    }

    public static Bitmap m4203a(FileDescriptor fileDescriptor, int i, int i2, da daVar) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = m4200a(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    public static int m4200a(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            i5 = Math.round(((float) i3) / ((float) i2));
            int round = Math.round(((float) i4) / ((float) i));
            if (i5 >= round) {
                i5 = round;
            }
            float f = (float) (i4 * i3);
            while (f / ((float) (i5 * i5)) > ((float) ((i * i2) * 2))) {
                i5++;
            }
        }
        return i5;
    }
}
