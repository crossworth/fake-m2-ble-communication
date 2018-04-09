package com.umeng.socialize.editorpage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/* compiled from: ShareActivity */
class C0957e implements OnClickListener {
    final /* synthetic */ ShareActivity f3302a;

    C0957e(ShareActivity shareActivity) {
        this.f3302a = shareActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.f3302a.f3269C = null;
        this.f3302a.m3203a(false);
        dialogInterface.cancel();
    }
}
