package com.lemon.cx.micolumnar;

import java.util.Calendar;

public class MiColumnarSportData extends MiColumnarData implements Comparable<MiColumnarSportData> {
    private float calories;
    private Calendar date;
    private float meter;

    public MiColumnarSportData(Calendar date, int step, float meter, float calories) {
        this.date = date;
        if (step <= 0) {
            step = 0;
        }
        this.value = step;
        this.meter = meter;
        this.calories = calories;
    }

    public MiColumnarSportData(Calendar date, String label, int step, float meter, float calories) {
        this.date = date;
        this.label = label;
        if (step <= 0) {
            step = 0;
        }
        this.value = step;
        this.meter = meter;
        this.calories = calories;
    }

    public MiColumnarSportData(MiColumnarSportData data) {
        this.date = data.getDate();
        this.label = data.getLabel();
        this.value = data.getValue();
        this.meter = data.getMeter();
        this.calories = data.getCalories();
    }

    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getStep() {
        return this.value;
    }

    public void setStep(int step) {
        if (step <= 0) {
            step = 0;
        }
        this.value = step;
    }

    public float getMeter() {
        return this.meter;
    }

    public void setMeter(float meter) {
        this.meter = meter;
    }

    public float getCalories() {
        return this.calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public int compareTo(MiColumnarSportData another) {
        return this.date.compareTo(another.date);
    }

    public String toString() {
        return "MiColumnarSportData{date=" + this.date + ", meter=" + this.meter + ", calories=" + this.calories + '}';
    }
}
