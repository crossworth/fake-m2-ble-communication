package com.droi.btlib.plugin;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadContactname {
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    private static final String[] PHONES_PROJECTION = new String[]{"display_name", "data1", "photo_id", "contact_id"};
    private Context mContext;
    Button text;
    String username;
    String usernumber;

    class MyContact {
        String contactName;
        String phoneNumber;

        MyContact(String phoneNumber, String contactName) {
            this.phoneNumber = phoneNumber;
            this.contactName = contactName;
        }
    }

    public ReadContactname(Context context) {
        this.mContext = context;
    }

    public ArrayList<MyContact> getPhoneContacts() {
        Cursor phoneCursor = this.mContext.getContentResolver().query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        ArrayList<MyContact> arr_contactName = new ArrayList();
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(1).replaceAll(" ", "").replaceAll("-", "");
                String contactName = phoneCursor.getString(0);
                Long contactid = Long.valueOf(phoneCursor.getLong(3));
                Long photoid = Long.valueOf(phoneCursor.getLong(2));
                Log.i("chenxin", "contactName_s = " + contactName + " phoneNum:" + phoneNumber);
                arr_contactName.add(new MyContact(phoneNumber, contactName));
            }
            phoneCursor.close();
        }
        return arr_contactName;
    }

    public String getContactNameFromPhoneBook(String phoneNum) {
        String contactName = new String();
        String phone = phoneNum.replaceAll(" ", "").replaceAll("-", "");
        Iterator it = getPhoneContacts().iterator();
        while (it.hasNext()) {
            MyContact contact = (MyContact) it.next();
            if (!contact.phoneNumber.contains(phone)) {
                if (phone.contains(contact.phoneNumber)) {
                }
            }
            contactName = contact.contactName;
        }
        Log.i("chenxin", "getContactNameFromPhoneBook:" + contactName);
        return contactName;
    }
}
