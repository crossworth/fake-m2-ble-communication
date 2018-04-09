package org.andengine.util;

import org.andengine.util.time.TimeConstants;

public final class TimeUtils implements TimeConstants {
    public static final String formatSeconds(int pSecondsTotal) {
        return formatSeconds(pSecondsTotal, new StringBuilder());
    }

    public static final String formatSeconds(int pSecondsTotal, StringBuilder pStringBuilder) {
        int seconds = pSecondsTotal % 60;
        pStringBuilder.append(pSecondsTotal / 60);
        pStringBuilder.append(':');
        if (seconds < 10) {
            pStringBuilder.append('0');
        }
        pStringBuilder.append(seconds);
        return pStringBuilder.toString();
    }
}
