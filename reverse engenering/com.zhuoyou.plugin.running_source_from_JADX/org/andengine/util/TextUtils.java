package org.andengine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class TextUtils {
    public static final Pattern SPLITPATTERN_COMMA = Pattern.compile(",");
    public static final Pattern SPLITPATTERN_SPACE = Pattern.compile(" ");
    public static final Pattern SPLITPATTERN_SPACES = Pattern.compile(" +");

    public static final CharSequence padFront(CharSequence pText, char pPadChar, int pLength) {
        int padCount = pLength - pText.length();
        if (padCount <= 0) {
            return pText;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = padCount - 1; i >= 0; i--) {
            sb.append(pPadChar);
        }
        sb.append(pText);
        return sb.toString();
    }

    public static final int countOccurrences(CharSequence pText, char pCharacter) {
        int count = 0;
        int lastOccurrence = android.text.TextUtils.indexOf(pText, pCharacter, 0);
        while (lastOccurrence != -1) {
            count++;
            lastOccurrence = android.text.TextUtils.indexOf(pText, pCharacter, lastOccurrence + 1);
        }
        return count;
    }

    public static final ArrayList<CharSequence> split(CharSequence pText, char pCharacter) {
        return (ArrayList) split(pText, pCharacter, new ArrayList());
    }

    public static final <L extends List<CharSequence>> L split(CharSequence pText, char pCharacter, L pResult) {
        int partCount = countOccurrences(pText, pCharacter) + 1;
        if (partCount == 0) {
            pResult.add(pText);
        } else {
            int from = 0;
            for (int i = 0; i < partCount - 1; i++) {
                int to = android.text.TextUtils.indexOf(pText, pCharacter, from);
                pResult.add(pText.subSequence(from, to));
                from = to + 1;
            }
            pResult.add(pText.subSequence(from, pText.length()));
        }
        return pResult;
    }

    public static final String formatStackTrace(StackTraceElement pStackTraceElement) {
        return pStackTraceElement.getClassName() + '.' + pStackTraceElement.getMethodName() + '(' + pStackTraceElement.getFileName() + ':' + pStackTraceElement.getLineNumber() + ')';
    }

    public static final String formatStackTrace(StackTraceElement[] pStackTraceElements) {
        StringBuilder sb = new StringBuilder();
        int stackTraceElementCount = pStackTraceElements.length;
        for (int i = 0; i < stackTraceElementCount; i++) {
            sb.append(pStackTraceElements[i]);
            if (i < stackTraceElementCount - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public static int countCharacters(List<CharSequence> pTexts) {
        return countCharacters(pTexts, false);
    }

    public static int countCharacters(List<CharSequence> pTexts, boolean pIgnoreWhitespaces) {
        int characters = 0;
        int i;
        if (pIgnoreWhitespaces) {
            for (i = pTexts.size() - 1; i >= 0; i--) {
                CharSequence text = (CharSequence) pTexts.get(i);
                for (int j = text.length() - 1; j >= 0; j--) {
                    if (!Character.isWhitespace(text.charAt(j))) {
                        characters++;
                    }
                }
            }
        } else {
            for (i = pTexts.size() - 1; i >= 0; i--) {
                characters += ((CharSequence) pTexts.get(i)).length();
            }
        }
        return characters;
    }
}
