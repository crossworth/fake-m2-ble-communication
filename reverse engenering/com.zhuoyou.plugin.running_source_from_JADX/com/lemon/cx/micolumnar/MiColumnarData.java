package com.lemon.cx.micolumnar;

public class MiColumnarData {
    protected String label = "00/00";
    protected int target;
    protected int value;

    public MiColumnarData(String label, int target, int value) {
        this.label = label;
        if (target < 0) {
            target = 0;
        }
        this.target = target;
        if (value < 0) {
            value = 0;
        }
        this.value = value;
    }

    public MiColumnarData(MiColumnarData data) {
        this.label = data.getLabel();
        this.target = data.getTarget();
        this.value = data.getValue();
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        if (value < 0) {
            value = 0;
        }
        this.value = value;
    }

    public int getTarget() {
        return this.target;
    }

    public void setTarget(int target) {
        if (target < 0) {
            target = 0;
        }
        this.target = target;
    }

    public String toString() {
        return "MiColumnarData{label=" + this.label + ", target=" + this.target + ", value=" + this.value + '}';
    }
}
