package com.umeng.socialize.editorpage;

import com.umeng.socialize.editorpage.KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener;
import com.umeng.socialize.utils.Log;

/* compiled from: ShareActivity */
class C1809a implements IOnKeyboardStateChangedListener {
    final /* synthetic */ ShareActivity f4822a;

    C1809a(ShareActivity shareActivity) {
        this.f4822a = shareActivity;
    }

    public void mo2173a(int i) {
        this.f4822a.f3270D = i;
        Log.m3248d("ShareActivity", "onKeyboardStateChanged  now state is " + i);
    }
}
