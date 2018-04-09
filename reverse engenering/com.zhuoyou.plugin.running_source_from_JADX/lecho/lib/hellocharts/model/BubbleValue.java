package lecho.lib.hellocharts.model;

import java.util.Arrays;
import lecho.lib.hellocharts.util.ChartUtils;

public class BubbleValue {
    private int color = ChartUtils.DEFAULT_COLOR;
    private int darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
    private float diffX;
    private float diffY;
    private float diffZ;
    private char[] label;
    private float originX;
    private float originY;
    private float originZ;
    private ValueShape shape = ValueShape.CIRCLE;
    private float f5012x;
    private float f5013y;
    private float f5014z;

    public BubbleValue() {
        set(0.0f, 0.0f, 0.0f);
    }

    public BubbleValue(float x, float y, float z) {
        set(x, y, z);
    }

    public BubbleValue(float x, float y, float z, int color) {
        set(x, y, z);
        setColor(color);
    }

    public BubbleValue(BubbleValue bubbleValue) {
        set(bubbleValue.f5012x, bubbleValue.f5013y, bubbleValue.f5014z);
        setColor(bubbleValue.color);
        this.label = bubbleValue.label;
    }

    public void update(float scale) {
        this.f5012x = this.originX + (this.diffX * scale);
        this.f5013y = this.originY + (this.diffY * scale);
        this.f5014z = this.originZ + (this.diffZ * scale);
    }

    public void finish() {
        set(this.originX + this.diffX, this.originY + this.diffY, this.originZ + this.diffZ);
    }

    public BubbleValue set(float x, float y, float z) {
        this.f5012x = x;
        this.f5013y = y;
        this.f5014z = z;
        this.originX = x;
        this.originY = y;
        this.originZ = z;
        this.diffX = 0.0f;
        this.diffY = 0.0f;
        this.diffZ = 0.0f;
        return this;
    }

    public BubbleValue setTarget(float targetX, float targetY, float targetZ) {
        set(this.f5012x, this.f5013y, this.f5014z);
        this.diffX = targetX - this.originX;
        this.diffY = targetY - this.originY;
        this.diffZ = targetZ - this.originZ;
        return this;
    }

    public float getX() {
        return this.f5012x;
    }

    public float getY() {
        return this.f5013y;
    }

    public float getZ() {
        return this.f5014z;
    }

    public int getColor() {
        return this.color;
    }

    public BubbleValue setColor(int color) {
        this.color = color;
        this.darkenColor = ChartUtils.darkenColor(color);
        return this;
    }

    public int getDarkenColor() {
        return this.darkenColor;
    }

    public ValueShape getShape() {
        return this.shape;
    }

    public BubbleValue setShape(ValueShape shape) {
        this.shape = shape;
        return this;
    }

    @Deprecated
    public char[] getLabel() {
        return this.label;
    }

    public BubbleValue setLabel(String label) {
        this.label = label.toCharArray();
        return this;
    }

    public char[] getLabelAsChars() {
        return this.label;
    }

    @Deprecated
    public BubbleValue setLabel(char[] label) {
        this.label = label;
        return this;
    }

    public String toString() {
        return "BubbleValue [x=" + this.f5012x + ", y=" + this.f5013y + ", z=" + this.f5014z + "]";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BubbleValue that = (BubbleValue) o;
        if (this.color != that.color) {
            return false;
        }
        if (this.darkenColor != that.darkenColor) {
            return false;
        }
        if (Float.compare(that.diffX, this.diffX) != 0) {
            return false;
        }
        if (Float.compare(that.diffY, this.diffY) != 0) {
            return false;
        }
        if (Float.compare(that.diffZ, this.diffZ) != 0) {
            return false;
        }
        if (Float.compare(that.originX, this.originX) != 0) {
            return false;
        }
        if (Float.compare(that.originY, this.originY) != 0) {
            return false;
        }
        if (Float.compare(that.originZ, this.originZ) != 0) {
            return false;
        }
        if (Float.compare(that.f5012x, this.f5012x) != 0) {
            return false;
        }
        if (Float.compare(that.f5013y, this.f5013y) != 0) {
            return false;
        }
        if (Float.compare(that.f5014z, this.f5014z) != 0) {
            return false;
        }
        if (!Arrays.equals(this.label, that.label)) {
            return false;
        }
        if (this.shape != that.shape) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int floatToIntBits;
        int i = 0;
        if (this.f5012x != 0.0f) {
            result = Float.floatToIntBits(this.f5012x);
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.f5013y != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.f5013y);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.f5014z != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.f5014z);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.originX != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.originX);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.originY != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.originY);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.originZ != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.originZ);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.diffX != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.diffX);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.diffY != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.diffY);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.diffZ != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.diffZ);
        } else {
            floatToIntBits = 0;
        }
        i2 = (((((i2 + floatToIntBits) * 31) + this.color) * 31) + this.darkenColor) * 31;
        if (this.shape != null) {
            floatToIntBits = this.shape.hashCode();
        } else {
            floatToIntBits = 0;
        }
        floatToIntBits = (i2 + floatToIntBits) * 31;
        if (this.label != null) {
            i = Arrays.hashCode(this.label);
        }
        return floatToIntBits + i;
    }
}
