package com.zhuoyou.plugin.custom;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.widget.Button;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p031u.aly.au;

public class ReadContactname {
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    private static final String[] PHONES_PROJECTION = new String[]{au.f3578g, "data1", "photo_id", "contact_id"};
    private Context mContext;
    Button text;
    String username;
    String usernumber;

    public ReadContactname(Context context) {
        this.mContext = context;
    }

    public ArrayList getPhoneContacts() {
        Cursor phoneCursor = this.mContext.getContentResolver().query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        ArrayList<String> arr_contactName = new ArrayList();
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(1);
                String contactName = phoneCursor.getString(0);
                Long contactid = Long.valueOf(phoneCursor.getLong(3));
                Long photoid = Long.valueOf(phoneCursor.getLong(2));
                Matcher m = Pattern.compile("[a-zA-Z]").matcher(contactName);
                if (contactName.matches("^[a-zA-Z \\u0031-\\u0039]*")) {
                    Log.i("zhangweinan", "contactName_s = " + contactName);
                    arr_contactName.add(contactName);
                }
            }
            phoneCursor.close();
        }
        return arr_contactName;
    }

    public String getContactNameFromPhoneBook(Context context, String phoneNum) {
        String contactName = "";
        Cursor pCur = context.getContentResolver().query(Phone.CONTENT_URI, null, "data1 = ?", new String[]{phoneNum}, null);
        if (!pCur.moveToFirst()) {
            return contactName;
        }
        contactName = pCur.getString(pCur.getColumnIndex(au.f3578g));
        pCur.close();
        return contactName;
    }
}
