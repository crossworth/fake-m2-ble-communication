package com.umeng.facebook.share.model;

import android.os.Parcel;
import com.umeng.facebook.share.ShareBuilder;

public interface ShareModelBuilder<P extends ShareModel, E extends ShareModelBuilder> extends ShareBuilder<P, E> {
    E readFrom(Parcel parcel);

    E readFrom(P p);
}
