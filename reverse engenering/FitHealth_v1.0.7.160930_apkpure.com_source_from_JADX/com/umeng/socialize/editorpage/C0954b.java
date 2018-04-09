package com.umeng.socialize.editorpage;

import android.text.Editable;
import android.text.TextWatcher;

/* compiled from: ShareActivity */
class C0954b implements TextWatcher {
    final /* synthetic */ ShareActivity f3299a;

    C0954b(ShareActivity shareActivity) {
        this.f3299a = shareActivity;
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.f3299a.f3298z = this.f3299a.m3212d();
    }
}
