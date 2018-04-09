package no.nordicsemi.android.log.localprovider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import com.droi.btlib.connection.MapConstants;
import java.util.ArrayList;
import java.util.Calendar;
import no.nordicsemi.android.log.LogContract;
import no.nordicsemi.android.log.LogContract.Session;
import no.nordicsemi.android.log.localprovider.LocalLogDatabaseHelper.Tables;

public abstract class LocalLogContentProvider extends ContentProvider {
    private static final int BULK_INSERTS_PER_YIELD_POINT = 50;
    private static final String DB_TAG = "local_log_db";
    private static final int MAX_OPERATIONS_PER_YIELD_POINT = 500;
    private static final int SESSION = 1020;
    private static final int SESSION_ID = 1021;
    private static final int SESSION_ID_LOG = 1022;
    private static final int SESSION_ID_LOG_CONTENT = 1023;
    private static final int SESSION_KEY = 1024;
    protected static final int SLEEP_AFTER_YIELD_DELAY = 4000;
    private static final String TAG = "LocalLogContentProvider";
    private static final ProjectionMap sCountProjectionMap = ProjectionMap.builder().add("_count", "COUNT(*)").build();
    private static final ProjectionMap sLogColumns = ProjectionMap.builder().add(MapConstants._ID).add(LogColumns.SESSION_ID).add("level").add(LogColumns.TIME).add("data").build();
    private static final ProjectionMap sSessionColumns = ProjectionMap.builder().add(MapConstants._ID).add("key").add("name").add(SessionColumns.CREATED_AT).build();
    private static final UriMatcher sUriMatcher = new UriMatcher(-1);
    private LocalLogDatabaseHelper mDatabaseHelper;
    private final ThreadLocal<LocalLogDatabaseHelper> mLocalDatabaseHelper = new ThreadLocal();
    private final String[] mSelectionArgs1 = new String[1];
    private String mSerializeDbTag;
    private SQLiteOpenHelper mSerializeOnDbHelper;
    private final ThreadLocal<LogTransaction> mTransactionHolder = new ThreadLocal();
    private final ContentValues mValues = new ContentValues();

    protected abstract Uri getAuthorityUri();

    protected LocalLogDatabaseHelper getDatabaseHelper(Context context) {
        return LocalLogDatabaseHelper.getInstance(context);
    }

    public void setDbHelperToSerializeOn(SQLiteOpenHelper serializeOnDbHelper, String tag) {
        this.mSerializeOnDbHelper = serializeOnDbHelper;
        this.mSerializeDbTag = tag;
    }

    public boolean onCreate() {
        try {
            return initialize();
        } catch (RuntimeException e) {
            Log.e(TAG, "Cannot start provider", e);
            return false;
        }
    }

    private boolean initialize() {
        this.mDatabaseHelper = getDatabaseHelper(getContext());
        this.mLocalDatabaseHelper.set(this.mDatabaseHelper);
        setDbHelperToSerializeOn(this.mDatabaseHelper, DB_TAG);
        String authority = getAuthorityUri().getAuthority();
        UriMatcher matcher = sUriMatcher;
        matcher.addURI(authority, Session.SESSION_CONTENT_DIRECTORY, 1020);
        matcher.addURI(authority, "session/#", SESSION_ID);
        matcher.addURI(authority, "session/#/log", SESSION_ID_LOG);
        matcher.addURI(authority, "session/#/log/content", SESSION_ID_LOG_CONTENT);
        matcher.addURI(authority, "session/key/*", 1024);
        return true;
    }

    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case SESSION_ID /*1021*/:
                return Session.CONTENT_ITEM_TYPE;
            case SESSION_ID_LOG /*1022*/:
                return LogContract.Log.CONTENT_TYPE;
            default:
                return null;
        }
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        this.mLocalDatabaseHelper.set(this.mDatabaseHelper);
        SQLiteDatabase db = ((LocalLogDatabaseHelper) this.mLocalDatabaseHelper.get()).getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int match = sUriMatcher.match(uri);
        String id;
        switch (match) {
            case SESSION_ID /*1021*/:
                id = uri.getLastPathSegment();
                qb.setTables(Tables.LOG_SESSIONS);
                qb.setProjectionMap(sSessionColumns);
                qb.appendWhere("_id=?");
                selectionArgs = appendSelectionArgs(selectionArgs, id);
                break;
            case SESSION_ID_LOG /*1022*/:
            case SESSION_ID_LOG_CONTENT /*1023*/:
                id = (String) uri.getPathSegments().get(1);
                qb.setTables("log");
                qb.setProjectionMap(sLogColumns);
                qb.appendWhere("session_id=?");
                selectionArgs = appendSelectionArgs(selectionArgs, id);
                if (match == SESSION_ID_LOG_CONTENT) {
                    StringBuilder builder = new StringBuilder();
                    String[] sessionProjection = new String[]{"key", "name", SessionColumns.CREATED_AT};
                    String sessionSelection = "_id=?";
                    String[] sessionSelArgs = this.mSelectionArgs1;
                    sessionSelArgs[0] = id;
                    Cursor c = db.query(Tables.LOG_SESSIONS, sessionProjection, "_id=?", sessionSelArgs, null, null, null);
                    try {
                        StringBuilder stringBuilder;
                        Calendar calendar;
                        if (c.moveToNext()) {
                            Calendar.getInstance().setTimeInMillis(c.getLong(2));
                            String appName = getContext().getApplicationInfo().loadLabel(getContext().getPackageManager()).toString();
                            stringBuilder = builder;
                            stringBuilder.append(String.format("%s, %tF\n", new Object[]{appName, calendar}));
                            String name = c.getString(1);
                            String str = "%s (%s)\n";
                            Object[] objArr = new Object[2];
                            if (name == null) {
                                name = "No name";
                            }
                            objArr[0] = name;
                            objArr[1] = c.getString(0);
                            builder.append(String.format(str, objArr));
                        }
                        c.close();
                        Uri uri2 = uri;
                        SQLiteDatabase sQLiteDatabase = db;
                        c = query(uri2, sQLiteDatabase, qb, new String[]{LogColumns.TIME, "level", "data"}, selection, selectionArgs, "time ASC");
                        try {
                            calendar = Calendar.getInstance();
                            while (c.moveToNext()) {
                                builder.append(getLevelAsChar(c.getInt(1)));
                                calendar.setTimeInMillis(c.getLong(0));
                                stringBuilder = builder;
                                stringBuilder.append(String.format("\t%1$tR:%1$tS.%1$tL\t%2$s\n", new Object[]{calendar, c.getString(2)}));
                            }
                            Cursor matrixCursor = new MatrixCursor(new String[]{"content"});
                            matrixCursor.addRow(new String[]{builder.toString()});
                            return matrixCursor;
                        } finally {
                            c.close();
                        }
                    } catch (Throwable th) {
                        c.close();
                    }
                }
                break;
        }
        return query(uri, db, qb, projection, selection, selectionArgs, sortOrder);
    }

    private char getLevelAsChar(int level) {
        switch (level) {
            case 1:
                return 'V';
            case 5:
                return 'I';
            case 10:
                return 'A';
            case 15:
                return 'W';
            case 20:
                return 'E';
            default:
                return 'D';
        }
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
        LogTransaction transaction = startTransaction(true);
        int opCount = 0;
        for (ContentValues insert : values) {
            insert(uri, insert);
            opCount++;
            if (opCount >= 50) {
                opCount = 0;
                try {
                    yield(transaction);
                } catch (RuntimeException re) {
                    transaction.markYieldFailed();
                    throw re;
                } catch (Throwable th) {
                    endTransaction(uri, true);
                }
            }
        }
        transaction.markSuccessful(true);
        endTransaction(uri, true);
        return numValues;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        int ypCount = 0;
        int opCount = 0;
        LogTransaction transaction = startTransaction(true);
        int numOperations = operations.size();
        ContentProviderResult[] results = new ContentProviderResult[numOperations];
        for (int i = 0; i < numOperations; i++) {
            opCount++;
            if (opCount >= 500) {
                throw new OperationApplicationException("Too many content provider operations between yield points. The maximum number of operations per yield point is 500", ypCount);
            }
            ContentProviderOperation operation = (ContentProviderOperation) operations.get(i);
            if (i > 0 && operation.isYieldAllowed()) {
                opCount = 0;
                try {
                    if (yield(transaction)) {
                        ypCount++;
                    }
                } catch (RuntimeException re) {
                    transaction.markYieldFailed();
                    throw re;
                } catch (Throwable th) {
                    endTransaction(Session.CONTENT_URI, true);
                }
            }
            results[i] = operation.apply(this, results, i);
        }
        transaction.markSuccessful(true);
        endTransaction(Session.CONTENT_URI, true);
        return results;
    }

    public Uri insert(Uri uri, ContentValues values) {
        LogTransaction transaction = startTransaction(false);
        try {
            Uri result = insertInTransaction(uri, values);
            if (result != null) {
                transaction.markDirty();
            }
            transaction.markSuccessful(false);
            return result;
        } finally {
            endTransaction(uri, false);
        }
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LogTransaction transaction = startTransaction(false);
        try {
            int deleted = deleteInTransaction(uri, selection, selectionArgs);
            if (deleted > 0) {
                transaction.markDirty();
            }
            transaction.markSuccessful(false);
            return deleted;
        } finally {
            endTransaction(uri, false);
        }
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        LogTransaction transaction = startTransaction(false);
        try {
            int updated = updateInTransaction(uri, values, selection, selectionArgs);
            if (updated > 0) {
                transaction.markDirty();
            }
            transaction.markSuccessful(false);
            return updated;
        } finally {
            endTransaction(uri, false);
        }
    }

    private Cursor query(Uri uri, SQLiteDatabase db, SQLiteQueryBuilder qb, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (projection != null && projection.length == 1 && "_count".equals(projection[0])) {
            qb.setProjectionMap(sCountProjectionMap);
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        if (c != null) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected android.net.Uri insertInTransaction(android.net.Uri r13, android.content.ContentValues r14) {
        /*
        r12 = this;
        r8 = r12.mLocalDatabaseHelper;
        r9 = r12.mDatabaseHelper;
        r8.set(r9);
        r8 = sUriMatcher;
        r3 = r8.match(r13);
        r0 = 0;
        switch(r3) {
            case 1020: goto L_0x0044;
            case 1021: goto L_0x0012;
            case 1022: goto L_0x001a;
            case 1023: goto L_0x0012;
            case 1024: goto L_0x0067;
            default: goto L_0x0012;
        };
    L_0x0012:
        r8 = 0;
        r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r8 >= 0) goto L_0x0098;
    L_0x0018:
        r8 = 0;
    L_0x0019:
        return r8;
    L_0x001a:
        r8 = r13.getPathSegments();
        r9 = 1;
        r8 = r8.get(r9);
        r8 = (java.lang.String) r8;
        r4 = java.lang.Long.parseLong(r8);
        r8 = "time";
        r10 = java.lang.System.currentTimeMillis();
        r9 = java.lang.Long.valueOf(r10);
        r14.put(r8, r9);
        r8 = "session_id";
        r9 = java.lang.Long.valueOf(r4);
        r14.put(r8, r9);
        r0 = r12.insertLog(r13, r14);
        goto L_0x0012;
    L_0x0044:
        r8 = "key";
        r2 = r14.getAsString(r8);
        if (r2 != 0) goto L_0x004e;
    L_0x004c:
        r8 = 0;
        goto L_0x0019;
    L_0x004e:
        r6 = java.lang.System.currentTimeMillis();
        r8 = "created_at";
        r9 = java.lang.Long.valueOf(r6);
        r14.put(r8, r9);
        r0 = r12.insertSession(r13, r14);
        r8 = 0;
        r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r8 >= 0) goto L_0x0067;
    L_0x0065:
        r8 = 0;
        goto L_0x0019;
    L_0x0067:
        r2 = r13.getLastPathSegment();
        r6 = java.lang.System.currentTimeMillis();
        r8 = "key";
        r14.put(r8, r2);
        r8 = "created_at";
        r9 = java.lang.Long.valueOf(r6);
        r14.put(r8, r9);
        r0 = r12.insertSession(r13, r14);
        r8 = 0;
        r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r8 >= 0) goto L_0x0089;
    L_0x0087:
        r8 = 0;
        goto L_0x0019;
    L_0x0089:
        r8 = r12.getAuthorityUri();
        r9 = "session";
        r8 = android.net.Uri.withAppendedPath(r8, r9);
        r8 = android.content.ContentUris.withAppendedId(r8, r0);
        goto L_0x0019;
    L_0x0098:
        r8 = android.content.ContentUris.withAppendedId(r13, r0);
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.log.localprovider.LocalLogContentProvider.insertInTransaction(android.net.Uri, android.content.ContentValues):android.net.Uri");
    }

    private long insertSession(Uri uri, ContentValues values) {
        this.mValues.clear();
        this.mValues.putAll(values);
        return ((LocalLogDatabaseHelper) this.mLocalDatabaseHelper.get()).getWritableDatabase().insert(Tables.LOG_SESSIONS, null, this.mValues);
    }

    private long insertLog(Uri uri, ContentValues values) {
        this.mValues.clear();
        this.mValues.putAll(values);
        return ((LocalLogDatabaseHelper) this.mLocalDatabaseHelper.get()).getWritableDatabase().insert("log", null, this.mValues);
    }

    private int deleteInTransaction(Uri uri, String selection, String[] selectionArgs) {
        this.mLocalDatabaseHelper.set(this.mDatabaseHelper);
        switch (sUriMatcher.match(uri)) {
            case 1020:
                return deleteSessions();
            case SESSION_ID /*1021*/:
                return deleteSession(ContentUris.parseId(uri));
            default:
                return 0;
        }
    }

    private int deleteSessions() {
        SQLiteDatabase db = ((LocalLogDatabaseHelper) this.mLocalDatabaseHelper.get()).getWritableDatabase();
        db.delete("log", null, null);
        return db.delete(Tables.LOG_SESSIONS, null, null);
    }

    private int deleteSession(long sessionId) {
        SQLiteDatabase db = ((LocalLogDatabaseHelper) this.mLocalDatabaseHelper.get()).getWritableDatabase();
        String[] args = this.mSelectionArgs1;
        args[0] = String.valueOf(sessionId);
        db.delete("log", "session_id=?", args);
        return db.delete(Tables.LOG_SESSIONS, "_id=?", args);
    }

    private int updateInTransaction(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Updating log is not supported. You can not change the history.");
    }

    private LogTransaction startTransaction(boolean callerIsBatch) {
        LogTransaction transaction = (LogTransaction) this.mTransactionHolder.get();
        if (transaction == null) {
            transaction = new LogTransaction(callerIsBatch);
            if (this.mSerializeOnDbHelper != null) {
                transaction.startTransactionForDb(this.mSerializeOnDbHelper.getWritableDatabase(), this.mSerializeDbTag);
            }
            this.mTransactionHolder.set(transaction);
        }
        return transaction;
    }

    private void endTransaction(Uri uri, boolean callerIsBatch) {
        LogTransaction transaction = (LogTransaction) this.mTransactionHolder.get();
        if (transaction == null) {
            return;
        }
        if (!transaction.isBatch() || callerIsBatch) {
            try {
                if (transaction.isDirty()) {
                    notifyChange(Uri.withAppendedPath(getAuthorityUri(), Session.SESSION_CONTENT_DIRECTORY));
                }
                transaction.finish(callerIsBatch);
            } finally {
                this.mTransactionHolder.set(null);
            }
        }
    }

    protected boolean yield(LogTransaction transaction) {
        SQLiteDatabase db = transaction.getDbForTag(DB_TAG);
        return db != null && db.yieldIfContendedSafely(4000);
    }

    protected void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null, false);
    }

    private String[] appendSelectionArgs(String[] selectionArgs, String... arg) {
        if (selectionArgs == null) {
            return arg;
        }
        String[] newSelectionArgs = new String[(selectionArgs.length + arg.length)];
        System.arraycopy(arg, 0, newSelectionArgs, 0, arg.length);
        System.arraycopy(selectionArgs, 0, newSelectionArgs, arg.length, selectionArgs.length);
        return newSelectionArgs;
    }
}
