package com.zhuoyou.plugin.running;

import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortCursor extends CursorWrapper implements Comparator<SortEntry> {
    public Cursor mCursor;
    int mpos = 0;
    ArrayList<SortEntry> sortList = new ArrayList();

    public static class SortEntry {
        public String key;
        public int order;
    }

    public SortCursor(Cursor cursor, String columnName) {
        super(cursor);
        this.mCursor = cursor;
        if (this.mCursor != null && this.mCursor.getCount() > 0) {
            int i = 0;
            int column = cursor.getColumnIndexOrThrow(columnName);
            this.mCursor.moveToFirst();
            while (!this.mCursor.isAfterLast()) {
                SortEntry entry = new SortEntry();
                entry.key = cursor.getString(column);
                entry.order = i;
                this.sortList.add(entry);
                this.mCursor.moveToNext();
                i++;
            }
        }
        Collections.sort(this.sortList, this);
    }

    public boolean moveToPosition(int position) {
        if (position < 0 || position >= this.sortList.size()) {
            if (position < 0) {
                this.mpos = -1;
            }
            if (position >= this.sortList.size()) {
                this.mpos = this.sortList.size();
            }
            return this.mCursor.moveToPosition(position);
        }
        this.mpos = position;
        return this.mCursor.moveToPosition(((SortEntry) this.sortList.get(position)).order);
    }

    public boolean moveToFirst() {
        return moveToPosition(0);
    }

    public boolean moveToLast() {
        return moveToPosition(getCount() - 1);
    }

    public boolean moveToNext() {
        return moveToPosition(this.mpos + 1);
    }

    public boolean moveToPrevious() {
        return moveToPosition(this.mpos - 1);
    }

    public boolean move(int offset) {
        return moveToPosition(this.mpos + offset);
    }

    public int getPosition() {
        return this.mpos;
    }

    public int compare(SortEntry lhs, SortEntry rhs) {
        String[] arr = lhs.key.split(":");
        String[] brr = rhs.key.split(":");
        int arrInt = (Integer.parseInt(arr[0]) * 60) + getStringInt(arr[1]);
        int brrInt = (Integer.parseInt(brr[0]) * 60) + getStringInt(brr[1]);
        if (arrInt > brrInt) {
            return -1;
        }
        if (arrInt >= brrInt) {
            return 0;
        }
        return 1;
    }

    private int getStringInt(String time) {
        StringBuffer buffer = new StringBuffer();
        if (time.charAt(0) != '0') {
            return Integer.parseInt(time);
        }
        buffer.append(time.charAt(1));
        return Integer.parseInt(buffer.toString());
    }
}
