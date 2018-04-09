package no.nordicsemi.android.log;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;
import no.nordicsemi.android.log.LogContract.Application;
import no.nordicsemi.android.log.LogContract.Session;

public class Logger {
    public static final int MARK_CLEAR = 0;
    public static final int MARK_FLAG_BLUE = 5;
    public static final int MARK_FLAG_RED = 6;
    public static final int MARK_FLAG_YELLOW = 4;
    public static final int MARK_STAR_BLUE = 2;
    public static final int MARK_STAR_RED = 3;
    public static final int MARK_STAR_YELLOW = 1;
    private static final int SESSION_ID = 100;
    private static final int SESSION_ID_LOG = 101;
    private static final int SESSION_KEY_NUMBER = 102;
    private static final int SESSION_KEY_NUMBER_LOG = 103;
    private static final UriMatcher mUriMatcher = new UriMatcher(-1);

    static {
        UriMatcher matcher = mUriMatcher;
        matcher.addURI(LogContract.AUTHORITY, "session/#", 100);
        matcher.addURI(LogContract.AUTHORITY, "session/#/log", 101);
        matcher.addURI(LogContract.AUTHORITY, "session/key/*/#", 102);
        matcher.addURI(LogContract.AUTHORITY, "session/key/*/#/log", 103);
    }

    public static LogSession newSession(Context context, String key, String name) {
        return newSession(context, null, key, name);
    }

    public static LogSession newSession(Context context, String profile, String key, String name) {
        ArrayList<ContentProviderOperation> ops = new ArrayList();
        Builder builder = ContentProviderOperation.newInsert(Application.CONTENT_URI);
        String appName = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        if (profile != null) {
            builder.withValue("application", new StringBuilder(String.valueOf(appName)).append(" ").append(profile).toString());
        } else {
            builder.withValue("application", appName);
        }
        ops.add(builder.build());
        builder = ContentProviderOperation.newInsert(Session.CONTENT_URI.buildUpon().appendEncodedPath("key").appendEncodedPath(key).build());
        builder.withValueBackReference(SessionColumns.APPLICATION_ID, 0);
        builder.withValue("name", name);
        ops.add(builder.build());
        try {
            return new LogSession(context, context.getContentResolver().applyBatch(LogContract.AUTHORITY, ops)[1].uri);
        } catch (Exception e) {
            return null;
        }
    }

    public static ILogSession openSession(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        switch (mUriMatcher.match(uri)) {
            case 100:
            case 102:
                return new LogSession(context, uri);
            case 101:
            case 103:
                Uri.Builder buider = Session.CONTENT_URI.buildUpon();
                List<String> segments = uri.getPathSegments();
                for (int i = 1; i < segments.size() - 1; i++) {
                    buider.appendEncodedPath((String) segments.get(i));
                }
                return new LogSession(context, buider.build());
            default:
                return new LocalLogSession(context, uri);
        }
    }

    public static void setSessionDescription(LogSession session, String description) {
        if (session != null) {
            ContentValues values = new ContentValues();
            values.put("description", description);
            try {
                session.getContext().getContentResolver().update(session.getSessionUri(), values, null, null);
            } catch (Exception e) {
            }
        }
    }

    public static void setSessionMark(LogSession session, int mark) {
        if (session != null) {
            ContentValues values = new ContentValues();
            values.put(SessionColumns.MARK, Integer.valueOf(mark));
            try {
                session.getContext().getContentResolver().update(session.getSessionUri(), values, null, null);
            } catch (Exception e) {
            }
        }
    }

    public static void m4575d(ILogSession session, String message) {
        log(session, 0, message);
    }

    public static void m4581v(ILogSession session, String message) {
        log(session, 1, message);
    }

    public static void m4579i(ILogSession session, String message) {
        log(session, 5, message);
    }

    public static void m4573a(ILogSession session, String message) {
        log(session, 10, message);
    }

    public static void m4583w(ILogSession session, String message) {
        log(session, 15, message);
    }

    public static void m4577e(ILogSession session, String message) {
        log(session, 20, message);
    }

    public static void log(ILogSession session, int level, String message) {
        if (session != null) {
            ContentValues values = new ContentValues();
            values.put("level", Integer.valueOf(level));
            values.put("data", message);
            try {
                session.getContext().getContentResolver().insert(session.getSessionEntriesUri(), values);
            } catch (Exception e) {
            }
        }
    }

    public static void m4574d(ILogSession session, int messageResId, Object... params) {
        log(session, 0, messageResId, params);
    }

    public static void m4580v(ILogSession session, int messageResId, Object... params) {
        log(session, 1, messageResId, params);
    }

    public static void m4578i(ILogSession session, int messageResId, Object... params) {
        log(session, 5, messageResId, params);
    }

    public static void m4572a(ILogSession session, int messageResId, Object... params) {
        log(session, 10, messageResId, params);
    }

    public static void m4582w(ILogSession session, int messageResId, Object... params) {
        log(session, 15, messageResId, params);
    }

    public static void m4576e(ILogSession session, int messageResId, Object... params) {
        log(session, 20, messageResId, params);
    }

    public static void log(ILogSession session, int level, int messageResId, Object... params) {
        if (session != null) {
            ContentValues values = new ContentValues();
            values.put("level", Integer.valueOf(level));
            values.put("data", session.getContext().getString(messageResId, params));
            try {
                session.getContext().getContentResolver().insert(session.getSessionEntriesUri(), values);
            } catch (Exception e) {
            }
        }
    }
}
