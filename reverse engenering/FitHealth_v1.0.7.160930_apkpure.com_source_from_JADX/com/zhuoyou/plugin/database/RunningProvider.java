package com.zhuoyou.plugin.database;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.util.ArrayList;

public class RunningProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(-1);
    private DBOpenHelper mDBOpenHelper;

    static {
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "data", 1);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "data/#", 2);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, DataBaseContants.TABLE_DELETE_NAME, 3);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "cloud/#", 4);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, DataBaseContants.TABLE_POINT_NAME, 5);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "point_message2/#", 6);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, DataBaseContants.TABLE_OPERATION_NAME, 7);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "operation_time2/#", 8);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, DataBaseContants.TABLE_GPSSPORT_NAME, 9);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "gps_sport2/#", 10);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, DataBaseContants.TABLE_GPS_SYNC, 11);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "gps_sync/#", 12);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, DataBaseContants.TABLE_ACTION_MSG, 13);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "action_msg_info/#", 14);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, DataBaseContants.TEMP_POINT_NAME, 15);
        uriMatcher.addURI(DataBaseContants.AUTHORITY, "point_temp/#", 16);
    }

    public int delete(Uri uri, String where, String[] whereArgs) {
        int count;
        SQLiteDatabase mWriteable = this.mDBOpenHelper.getWritableDatabase();
        String id;
        switch (uriMatcher.match(uri)) {
            case 1:
                count = mWriteable.delete("data", where, whereArgs);
                break;
            case 2:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete("data", where, whereArgs);
                break;
            case 3:
                count = mWriteable.delete(DataBaseContants.TABLE_DELETE_NAME, where, whereArgs);
                break;
            case 4:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete(DataBaseContants.TABLE_DELETE_NAME, where, whereArgs);
                break;
            case 5:
                count = mWriteable.delete(DataBaseContants.TABLE_POINT_NAME, where, whereArgs);
                break;
            case 6:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete(DataBaseContants.TABLE_POINT_NAME, where, whereArgs);
                break;
            case 7:
                count = mWriteable.delete(DataBaseContants.TABLE_OPERATION_NAME, where, whereArgs);
                break;
            case 8:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete(DataBaseContants.TABLE_OPERATION_NAME, where, whereArgs);
                break;
            case 9:
                count = mWriteable.delete(DataBaseContants.TABLE_GPSSPORT_NAME, where, whereArgs);
                break;
            case 10:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete(DataBaseContants.TABLE_GPSSPORT_NAME, where, whereArgs);
                break;
            case 11:
                count = mWriteable.delete(DataBaseContants.TABLE_GPS_SYNC, where, whereArgs);
                break;
            case 12:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete(DataBaseContants.TABLE_GPS_SYNC, where, whereArgs);
                break;
            case 13:
                count = mWriteable.delete(DataBaseContants.TABLE_ACTION_MSG, where, whereArgs);
                break;
            case 14:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete(DataBaseContants.TABLE_ACTION_MSG, where, whereArgs);
                break;
            case 15:
                count = mWriteable.delete(DataBaseContants.TEMP_POINT_NAME, where, whereArgs);
                break;
            case 16:
                id = (String) uri.getPathSegments().get(1);
                if (TextUtils.isEmpty(where)) {
                    where = "_id=" + id;
                } else {
                    where = "_id=" + id + " AND (" + where + SocializeConstants.OP_CLOSE_PAREN;
                }
                count = mWriteable.delete(DataBaseContants.TEMP_POINT_NAME, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot delete for URL:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/data";
            case 2:
                return "vnd.android.cursor.item/data";
            case 3:
                return "vnd.android.cursor.dir/delete";
            case 4:
                return "vnd.android.cursor.item/delete";
            case 5:
                return "vnd.android.cursor.dir/point";
            case 6:
                return "vnd.android.cursor.item/point";
            case 7:
                return "vnd.android.cursor.dir/operation";
            case 8:
                return "vnd.android.cursor.item/operation";
            case 9:
                return "vnd.android.cursor.dir/gpssport";
            case 10:
                return "vnd.android.cursor.item/gpssport";
            case 11:
                return "vnd.android.cursor.dir/gpssync";
            case 12:
                return "vnd.android.cursor.item/gpssync";
            case 13:
                return "vnd.android.cursor.dir/actionmsg";
            case 14:
                return "vnd.android.cursor.item/actionmsg";
            case 15:
                return "vnd.android.cursor.dir/temppoint";
            case 16:
                return "vnd.android.cursor.item/temppoint";
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
    }

    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == 1 || uriMatcher.match(uri) == 3 || uriMatcher.match(uri) == 5 || uriMatcher.match(uri) == 7 || uriMatcher.match(uri) == 9 || uriMatcher.match(uri) == 11 || uriMatcher.match(uri) == 13 || uriMatcher.match(uri) == 15) {
            SQLiteDatabase mWriteable = this.mDBOpenHelper.getWritableDatabase();
            long rowId;
            Uri newUri;
            switch (uriMatcher.match(uri)) {
                case 1:
                    rowId = mWriteable.insert("data", "data", values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_URI, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                case 3:
                    rowId = mWriteable.insert(DataBaseContants.TABLE_DELETE_NAME, MessageObj.ACTION_DEL, values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_DELETE_URI, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                case 5:
                    rowId = mWriteable.insert(DataBaseContants.TABLE_POINT_NAME, "point", values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_URI_POINT, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                case 7:
                    rowId = mWriteable.insert(DataBaseContants.TABLE_OPERATION_NAME, "operation", values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_URI_OPERATION, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                case 9:
                    rowId = mWriteable.insert(DataBaseContants.TABLE_GPSSPORT_NAME, "gpssport", values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_URI_GPSSPORT, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                case 11:
                    rowId = mWriteable.insert(DataBaseContants.TABLE_GPS_SYNC, "gpssync", values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_URI_GPSSPORT, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                case 13:
                    rowId = mWriteable.insert(DataBaseContants.TABLE_ACTION_MSG, "actionmsg", values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_MSG_URI, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                case 15:
                    rowId = mWriteable.insert(DataBaseContants.TEMP_POINT_NAME, "temppoint", values);
                    if (rowId > 0) {
                        newUri = ContentUris.withAppendedId(DataBaseContants.CONTENT_URI_POINT, rowId);
                        getContext().getContentResolver().notifyChange(newUri, null);
                        return newUri;
                    }
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
                default:
                    throw new IllegalArgumentException("Faied to insert row into " + uri);
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean onCreate() {
        this.mDBOpenHelper = new DBOpenHelper(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case 1:
                qb.setTables("data");
                break;
            case 2:
                qb.setTables("data");
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            case 3:
                qb.setTables(DataBaseContants.TABLE_DELETE_NAME);
                break;
            case 4:
                qb.setTables(DataBaseContants.TABLE_DELETE_NAME);
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            case 5:
                qb.setTables(DataBaseContants.TABLE_POINT_NAME);
                break;
            case 6:
                qb.setTables(DataBaseContants.TABLE_POINT_NAME);
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            case 7:
                qb.setTables(DataBaseContants.TABLE_OPERATION_NAME);
                break;
            case 8:
                qb.setTables(DataBaseContants.TABLE_OPERATION_NAME);
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            case 9:
                qb.setTables(DataBaseContants.TABLE_GPSSPORT_NAME);
                break;
            case 10:
                qb.setTables(DataBaseContants.TABLE_GPSSPORT_NAME);
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            case 11:
                qb.setTables(DataBaseContants.TABLE_GPS_SYNC);
                break;
            case 12:
                qb.setTables(DataBaseContants.TABLE_GPS_SYNC);
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            case 13:
                qb.setTables(DataBaseContants.TABLE_ACTION_MSG);
                break;
            case 14:
                qb.setTables(DataBaseContants.TABLE_ACTION_MSG);
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            case 15:
                qb.setTables(DataBaseContants.TEMP_POINT_NAME);
                break;
            case 16:
                qb.setTables(DataBaseContants.TEMP_POINT_NAME);
                qb.appendWhere("_id=");
                qb.appendWhere((CharSequence) uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        Cursor c = qb.query(this.mDBOpenHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        int count;
        SQLiteDatabase mWriteable = this.mDBOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case 1:
                count = mWriteable.update("data", values, where, whereArgs);
                break;
            case 2:
                count = mWriteable.update("data", values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case 3:
                count = mWriteable.update(DataBaseContants.TABLE_DELETE_NAME, values, where, whereArgs);
                break;
            case 4:
                count = mWriteable.update(DataBaseContants.TABLE_DELETE_NAME, values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case 5:
                count = mWriteable.update(DataBaseContants.TABLE_POINT_NAME, values, where, whereArgs);
                break;
            case 6:
                count = mWriteable.update(DataBaseContants.TABLE_POINT_NAME, values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case 7:
                count = mWriteable.update(DataBaseContants.TABLE_OPERATION_NAME, values, where, whereArgs);
                break;
            case 8:
                count = mWriteable.update(DataBaseContants.TABLE_OPERATION_NAME, values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case 9:
                count = mWriteable.update(DataBaseContants.TABLE_GPSSPORT_NAME, values, where, whereArgs);
                break;
            case 10:
                count = mWriteable.update(DataBaseContants.TABLE_GPSSPORT_NAME, values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case 11:
                count = mWriteable.update(DataBaseContants.TABLE_GPS_SYNC, values, where, whereArgs);
                break;
            case 12:
                count = mWriteable.update(DataBaseContants.TABLE_GPS_SYNC, values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case 13:
                count = mWriteable.update(DataBaseContants.TABLE_ACTION_MSG, values, where, whereArgs);
                break;
            case 14:
                count = mWriteable.update(DataBaseContants.TABLE_ACTION_MSG, values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case 15:
                count = mWriteable.update(DataBaseContants.TEMP_POINT_NAME, values, where, whereArgs);
                break;
            case 16:
                count = mWriteable.update(DataBaseContants.TEMP_POINT_NAME, values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow URL " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        SQLiteDatabase db = this.mDBOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}
