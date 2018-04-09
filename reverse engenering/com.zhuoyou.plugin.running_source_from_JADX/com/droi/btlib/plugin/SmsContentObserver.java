package com.droi.btlib.plugin;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.droi.btlib.connection.MapConstants;
import com.droi.btlib.service.BtManagerService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsContentObserver extends ContentObserver {
    private static final String PATTERN_CODER = "";
    private static final String TAG = "SmsContentObserver";
    private static final String VERIFY_CODE_FROM = "";
    private Context mContext;
    private Handler mHandler;
    private String preId = new String();

    public SmsContentObserver(Context context, Handler handler) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
    }

    public void onChange(boolean selfChange) {
        Log.i(TAG, "onChange");
        Cursor c = this.mContext.getContentResolver().query(Uri.parse(MapConstants.INBOX), null, null, null, "date desc");
        if (c != null) {
            if (c.moveToNext()) {
                String number = c.getString(c.getColumnIndex(MapConstants.ADDRESS));
                String body = c.getString(c.getColumnIndex("body"));
                String id = c.getString(c.getColumnIndex(MapConstants._ID));
                Log.i(TAG, "body:" + body);
                if (!(TextUtils.isEmpty(number) || this.preId.equals(id))) {
                    this.preId = id;
                    Message msg = Message.obtain();
                    msg.what = BtManagerService.TASK_ADD;
                    msg.arg1 = 4116;
                    msg.obj = number + "|" + body + "|" + id;
                    this.mHandler.sendMessage(msg);
                }
            }
            c.close();
        }
    }

    private String patternCode(String patternContent) {
        if (TextUtils.isEmpty(patternContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile("").matcher(patternContent);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
