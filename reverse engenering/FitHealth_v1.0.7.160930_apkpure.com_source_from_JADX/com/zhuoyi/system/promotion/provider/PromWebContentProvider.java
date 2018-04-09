package com.zhuoyi.system.promotion.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.util.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

public class PromWebContentProvider extends ContentProvider {
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        File file = new File(URI.create(new StringBuilder(String.valueOf("file://" + PromUtils.getInstance(getContext()).getDesktopAdPath())).append(uri.getEncodedPath()).toString()));
        Logger.m3373e(PromWebContentProvider.class.getName(), "path=" + file.getAbsolutePath());
        return ParcelFileDescriptor.open(file, 268435456);
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public boolean onCreate() {
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
