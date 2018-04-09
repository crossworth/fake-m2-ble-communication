package com.droi.library.pickerviews.picker;

import android.graphics.Typeface;

public interface IPickerView {
    void setDividerColor(int i);

    void setDividerWidth(float f);

    void setLabelColor(int i);

    void setLineSpacingMultiplier(float f);

    void setTextColorCenter(int i);

    void setTextColorOut(int i);

    void setTextSize(int i);

    void setTypeface(Typeface typeface);

    void show();

    void showLabel(boolean z);
}
