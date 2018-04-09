package twitter4j;

class StringUtil {
    private StringUtil() {
        throw new AssertionError();
    }

    public static String join(long[] follows) {
        StringBuilder buf = new StringBuilder(follows.length * 11);
        for (long follow : follows) {
            if (buf.length() != 0) {
                buf.append(",");
            }
            buf.append(follow);
        }
        return buf.toString();
    }

    public static String join(String[] track) {
        StringBuilder buf = new StringBuilder(track.length * 11);
        for (String str : track) {
            if (buf.length() != 0) {
                buf.append(",");
            }
            buf.append(str);
        }
        return buf.toString();
    }
}
