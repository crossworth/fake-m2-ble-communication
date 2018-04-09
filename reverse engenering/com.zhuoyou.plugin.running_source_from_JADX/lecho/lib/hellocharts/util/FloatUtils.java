package lecho.lib.hellocharts.util;

import com.baidu.mapapi.map.WeightedLatLng;
import com.zhuoyou.plugin.running.baas.Rank;
import org.andengine.util.time.TimeConstants;

public class FloatUtils {
    public static final int[] POW10 = new int[]{1, 10, 100, 1000, 10000, Rank.MIN, TimeConstants.MICROSECONDS_PER_SECOND};

    public static float nextUpF(float f) {
        if (Float.isNaN(f) || f == Float.POSITIVE_INFINITY) {
            return f;
        }
        f += 0.0f;
        return Float.intBitsToFloat((f >= 0.0f ? 1 : -1) + Float.floatToRawIntBits(f));
    }

    public static float nextDownF(float f) {
        if (Float.isNaN(f) || f == Float.NEGATIVE_INFINITY) {
            return f;
        }
        if (f == 0.0f) {
            return -1.4E-45f;
        }
        return Float.intBitsToFloat((f > 0.0f ? -1 : 1) + Float.floatToRawIntBits(f));
    }

    public static double nextUp(double d) {
        if (Double.isNaN(d) || d == Double.POSITIVE_INFINITY) {
            return d;
        }
        d += 0.0d;
        return Double.longBitsToDouble(((long) (d >= 0.0d ? 1 : -1)) + Double.doubleToRawLongBits(d));
    }

    public static double nextDown(double d) {
        if (Double.isNaN(d) || d == Double.NEGATIVE_INFINITY) {
            return d;
        }
        if (d == 0.0d) {
            return -1.401298464324817E-45d;
        }
        return Double.longBitsToDouble(((long) (d > 0.0d ? -1 : 1)) + Double.doubleToRawLongBits(d));
    }

    public static boolean almostEqual(float a, float b, float absoluteDiff, float relativeDiff) {
        float diff = Math.abs(a - b);
        if (diff <= absoluteDiff) {
            return true;
        }
        float largest;
        a = Math.abs(a);
        b = Math.abs(b);
        if (a > b) {
            largest = a;
        } else {
            largest = b;
        }
        if (diff > largest * relativeDiff) {
            return false;
        }
        return true;
    }

    public static float roundToOneSignificantFigure(double num) {
        double d;
        if (num < 0.0d) {
            d = -num;
        } else {
            d = num;
        }
        float magnitude = (float) Math.pow(10.0d, (double) (1 - ((int) ((float) Math.ceil((double) ((float) Math.log10(d)))))));
        return ((float) Math.round(((double) magnitude) * num)) / magnitude;
    }

    public static int formatFloat(char[] formattedValue, float value, int endIndex, int digits, char separator) {
        if (digits >= POW10.length) {
            formattedValue[endIndex - 1] = '.';
            return 1;
        }
        boolean negative = false;
        if (value == 0.0f) {
            formattedValue[endIndex - 1] = '0';
            return 1;
        }
        if (value < 0.0f) {
            negative = true;
            value = -value;
        }
        if (digits > POW10.length) {
            digits = POW10.length - 1;
        }
        long lval = (long) Math.round(value * ((float) POW10[digits]));
        int charsNumber = 0;
        int index = endIndex - 1;
        while (true) {
            if (lval == 0 && charsNumber >= digits + 1) {
                break;
            }
            lval /= 10;
            int index2 = index - 1;
            formattedValue[index] = (char) (((int) (lval % 10)) + 48);
            charsNumber++;
            if (charsNumber == digits) {
                index = index2 - 1;
                formattedValue[index2] = separator;
                charsNumber++;
                index2 = index;
            }
            index = index2;
        }
        if (formattedValue[index + 1] == separator) {
            index2 = index - 1;
            formattedValue[index] = '0';
            charsNumber++;
            index = index2;
        }
        if (negative) {
            index2 = index - 1;
            formattedValue[index] = '-';
            return charsNumber + 1;
        }
        return charsNumber;
    }

    public static void computeAutoGeneratedAxisValues(float start, float stop, int steps, AxisAutoValues outValues) {
        double range = (double) (stop - start);
        if (steps == 0 || range <= 0.0d) {
            outValues.values = new float[0];
            outValues.valuesNumber = 0;
            return;
        }
        double intervalValue;
        double interval = (double) roundToOneSignificantFigure(range / ((double) steps));
        double intervalMagnitude = Math.pow(10.0d, (double) ((int) Math.log10(interval)));
        if (((int) (interval / intervalMagnitude)) > 5) {
            interval = Math.floor(10.0d * intervalMagnitude);
        }
        double first = Math.ceil(((double) start) / interval) * interval;
        int valuesNum = 0;
        for (intervalValue = first; intervalValue <= nextUp(Math.floor(((double) stop) / interval) * interval); intervalValue += interval) {
            valuesNum++;
        }
        outValues.valuesNumber = valuesNum;
        if (outValues.values.length < valuesNum) {
            outValues.values = new float[valuesNum];
        }
        intervalValue = first;
        for (int valueIndex = 0; valueIndex < valuesNum; valueIndex++) {
            outValues.values[valueIndex] = (float) intervalValue;
            intervalValue += interval;
        }
        if (interval < WeightedLatLng.DEFAULT_INTENSITY) {
            outValues.decimals = (int) Math.ceil(-Math.log10(interval));
        } else {
            outValues.decimals = 0;
        }
    }
}