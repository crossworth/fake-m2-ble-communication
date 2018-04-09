package com.droi.sdk.feedback;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class C0957g {
    private static C0956f f3118a;
    private static SQLiteDatabase f3119b;

    public static void m2826a() {
        f3118a = new C0956f(C0958h.f3120a);
        f3119b = f3118a.getReadableDatabase();
    }

    public static List<DroiFeedbackInfo> m2828b() {
        C0957g.m2826a();
        List<DroiFeedbackInfo> arrayList = new ArrayList();
        Cursor rawQuery = f3119b.rawQuery("SELECT * FROM feedbackInfo", null);
        while (rawQuery.moveToNext()) {
            try {
                DroiFeedbackInfo droiFeedbackInfo = new DroiFeedbackInfo();
                droiFeedbackInfo.setContact(rawQuery.getString(rawQuery.getColumnIndex("contact")));
                droiFeedbackInfo.setContent(rawQuery.getString(rawQuery.getColumnIndex("content")));
                droiFeedbackInfo.setCreateTime(rawQuery.getString(rawQuery.getColumnIndex(LogColumns.TIME)));
                droiFeedbackInfo.setReply(rawQuery.getString(rawQuery.getColumnIndex("reply")));
                droiFeedbackInfo.setReplyTime(rawQuery.getString(rawQuery.getColumnIndex("reply_time")));
                arrayList.add(droiFeedbackInfo);
            } finally {
                rawQuery.close();
                C0957g.m2830d();
            }
        }
        return arrayList;
    }

    public static void m2827a(List<DroiFeedbackInfo> list) {
        C0957g.m2826a();
        f3119b.beginTransaction();
        try {
            for (DroiFeedbackInfo droiFeedbackInfo : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("contact", droiFeedbackInfo.getContact());
                contentValues.put("content", droiFeedbackInfo.getContent());
                contentValues.put(LogColumns.TIME, droiFeedbackInfo.getUTCCreateTime());
                contentValues.put("reply", droiFeedbackInfo.getReply());
                contentValues.put("reply_time", droiFeedbackInfo.getUTCReplyTime());
                f3119b.insert("feedbackInfo", null, contentValues);
            }
            f3119b.setTransactionSuccessful();
        } finally {
            f3119b.endTransaction();
            C0957g.m2830d();
        }
    }

    public static void m2829c() {
        C0957g.m2826a();
        try {
            f3119b.delete("feedbackInfo", null, null);
        } finally {
            C0957g.m2830d();
        }
    }

    public static void m2830d() {
        if (f3119b != null && f3119b.isOpen()) {
            f3119b.close();
        }
    }
}
