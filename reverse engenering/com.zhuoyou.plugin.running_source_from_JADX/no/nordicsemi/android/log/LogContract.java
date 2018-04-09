package no.nordicsemi.android.log;

import android.net.Uri;
import android.provider.BaseColumns;

public class LogContract {
    public static final String AUTHORITY = "no.nordicsemi.android.log";
    public static final Uri AUTHORITY_URI = Uri.parse("content://no.nordicsemi.android.log");

    protected interface ApplicationColumns {
        public static final String APPLICATION = "application";
    }

    public static final class Application implements BaseColumns, ApplicationColumns {
        public static final String APPLICATION_CONTENT_DIRECTORY = "application";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/no.nordicsemi.android.log.application";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/no.nordicsemi.android.log.applications";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(LogContract.AUTHORITY_URI, "application");

        private Application() {
        }
    }

    protected interface LogColumns {
        public static final String DATA = "data";
        public static final String LEVEL = "level";
        public static final String SESSION_ID = "session_id";
        public static final String TIME = "time";
    }

    public static final class Log implements BaseColumns, LogColumns {
        public static final String CONTENT_DIRECTORY = "log";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/no.nordicsemi.android.log.entry";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/no.nordicsemi.android.log.enties";

        public final class Level {
            public static final int APPLICATION = 10;
            public static final int DEBUG = 0;
            public static final int ERROR = 20;
            public static final int INFO = 5;
            public static final int VERBOSE = 1;
            public static final int WARNING = 15;

            private Level() {
            }
        }

        private Log() {
        }

        public static Uri createUri(long sessionId) {
            return Session.CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(sessionId)).appendEncodedPath("log").build();
        }

        public static Uri createUri(String key, int number) {
            return Session.CONTENT_URI.buildUpon().appendEncodedPath("key").appendEncodedPath(key).appendEncodedPath(String.valueOf(number)).appendEncodedPath("log").build();
        }
    }

    protected interface SessionColumns {
        public static final String APPLICATION_ID = "application_id";
        public static final String CREATED_AT = "created_at";
        public static final String DESCRIPTION = "description";
        public static final String KEY = "key";
        public static final String MARK = "mark";
        public static final String NAME = "name";
        public static final String NUMBER = "number";
    }

    public static final class Session implements BaseColumns, SessionColumns {
        public static final String APPLICATION_CONTENT_DIRECTORY = "application";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/no.nordicsemi.android.log.session";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/no.nordicsemi.android.log.sessions";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(LogContract.AUTHORITY_URI, SESSION_CONTENT_DIRECTORY);
        public static final String KEY_CONTENT_DIRECTORY = "key";
        public static final String SESSION_CONTENT_DIRECTORY = "session";

        public static final class Content {
            public static final String CONTENT = "content";
        }

        private Session() {
        }

        public static Uri createUri(long sessionId) {
            return CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(sessionId)).build();
        }

        public static Uri createUri(String key, int number) {
            return CONTENT_URI.buildUpon().appendEncodedPath("key").appendEncodedPath(key).appendEncodedPath(String.valueOf(number)).build();
        }

        public static Uri createSessionsUri(long applicationId) {
            return CONTENT_URI.buildUpon().appendEncodedPath("application").appendEncodedPath(String.valueOf(applicationId)).build();
        }
    }
}
