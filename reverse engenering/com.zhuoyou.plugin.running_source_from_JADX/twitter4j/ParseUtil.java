package twitter4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.LinkedBlockingQueue;

final class ParseUtil {
    private static final Map<String, LinkedBlockingQueue<SimpleDateFormat>> formatMapQueue = new HashMap();

    private ParseUtil() {
        throw new AssertionError();
    }

    static String getUnescapedString(String str, JSONObject json) {
        return HTMLEntity.unescape(getRawString(str, json));
    }

    public static String getRawString(String name, JSONObject json) {
        String str = null;
        try {
            if (!json.isNull(name)) {
                str = json.getString(name);
            }
        } catch (JSONException e) {
        } catch (Exception e2) {
        }
        return str;
    }

    static String getURLDecodedString(String name, JSONObject json) {
        String returnValue = getRawString(name, json);
        if (returnValue != null) {
            try {
                returnValue = URLDecoder.decode(returnValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return returnValue;
    }

    public static Date parseTrendsDate(String asOfStr) throws TwitterException {
        switch (asOfStr.length()) {
            case 10:
                return new Date(Long.parseLong(asOfStr) * 1000);
            case 20:
                return getDate(asOfStr, "yyyy-MM-dd'T'HH:mm:ss'Z'");
            default:
                return getDate(asOfStr, "EEE, d MMM yyyy HH:mm:ss z");
        }
    }

    public static Date getDate(String name, JSONObject json) throws TwitterException {
        return getDate(name, json, "EEE MMM d HH:mm:ss z yyyy");
    }

    public static Date getDate(String name, JSONObject json, String format) throws TwitterException {
        String dateStr = getUnescapedString(name, json);
        if ("null".equals(dateStr) || dateStr == null) {
            return null;
        }
        return getDate(dateStr, format);
    }

    public static Date getDate(String dateString, String format) throws TwitterException {
        LinkedBlockingQueue<SimpleDateFormat> simpleDateFormats = (LinkedBlockingQueue) formatMapQueue.get(format);
        if (simpleDateFormats == null) {
            simpleDateFormats = new LinkedBlockingQueue();
            formatMapQueue.put(format, simpleDateFormats);
        }
        SimpleDateFormat sdf = (SimpleDateFormat) simpleDateFormats.poll();
        if (sdf == null) {
            sdf = new SimpleDateFormat(format, Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        try {
            Date parse = sdf.parse(dateString);
            try {
                simpleDateFormats.put(sdf);
            } catch (InterruptedException e) {
            }
            return parse;
        } catch (Throwable pe) {
            throw new TwitterException("Unexpected date format(" + dateString + ") returned from twitter.com", pe);
        } catch (Throwable th) {
            try {
                simpleDateFormats.put(sdf);
            } catch (InterruptedException e2) {
            }
        }
    }

    public static int getInt(String name, JSONObject json) {
        return getInt(getRawString(name, json));
    }

    public static int getInt(String str) {
        int i = -1;
        if (!(str == null || "".equals(str) || "null".equals(str))) {
            try {
                i = Integer.valueOf(str).intValue();
            } catch (NumberFormatException e) {
            }
        }
        return i;
    }

    public static long getLong(String name, JSONObject json) {
        return getLong(getRawString(name, json));
    }

    public static long getLong(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return -1;
        }
        if (str.endsWith("+")) {
            return Long.valueOf(str.substring(0, str.length() - 1)).longValue() + 1;
        }
        return Long.valueOf(str).longValue();
    }

    public static double getDouble(String name, JSONObject json) {
        String str2 = getRawString(name, json);
        if (str2 == null || "".equals(str2) || "null".equals(str2)) {
            return -1.0d;
        }
        return Double.valueOf(str2).doubleValue();
    }

    public static boolean getBoolean(String name, JSONObject json) {
        String str = getRawString(name, json);
        if (str == null || "null".equals(str)) {
            return false;
        }
        return Boolean.valueOf(str).booleanValue();
    }

    public static int toAccessLevel(HttpResponse res) {
        if (res == null) {
            return -1;
        }
        String xAccessLevel = res.getResponseHeader("X-Access-Level");
        if (xAccessLevel == null) {
            return 0;
        }
        switch (xAccessLevel.length()) {
            case 4:
                return 1;
            case 10:
                return 2;
            case 25:
                return 3;
            case 26:
                return 3;
            default:
                return 0;
        }
    }
}
