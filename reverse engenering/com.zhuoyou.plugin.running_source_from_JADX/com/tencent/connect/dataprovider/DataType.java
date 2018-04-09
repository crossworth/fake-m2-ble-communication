package com.tencent.connect.dataprovider;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public final class DataType {
    public static final int CONTENT_AND_IMAGE_PATH = 1;
    public static final int CONTENT_AND_VIDEO_PATH = 2;
    public static final int CONTENT_ONLY = 4;

    /* compiled from: ProGuard */
    public static class TextAndMediaPath implements Parcelable {
        public static final Creator<TextAndMediaPath> CREATOR = new C11751();
        private String f3655a;
        private String f3656b;

        /* compiled from: ProGuard */
        static class C11751 implements Creator<TextAndMediaPath> {
            C11751() {
            }

            public TextAndMediaPath createFromParcel(Parcel parcel) {
                return new TextAndMediaPath(parcel);
            }

            public TextAndMediaPath[] newArray(int i) {
                return new TextAndMediaPath[i];
            }
        }

        public TextAndMediaPath(String str, String str2) {
            this.f3655a = str;
            this.f3656b = str2;
        }

        public String getText() {
            return this.f3655a;
        }

        public String getMediaPath() {
            return this.f3656b;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.f3655a);
            parcel.writeString(this.f3656b);
        }

        private TextAndMediaPath(Parcel parcel) {
            this.f3655a = parcel.readString();
            this.f3656b = parcel.readString();
        }
    }

    /* compiled from: ProGuard */
    public static class TextOnly implements Parcelable {
        public static final Creator<TextOnly> CREATOR = new C11761();
        private String f3657a;

        /* compiled from: ProGuard */
        static class C11761 implements Creator<TextOnly> {
            C11761() {
            }

            public TextOnly createFromParcel(Parcel parcel) {
                return new TextOnly(parcel);
            }

            public TextOnly[] newArray(int i) {
                return new TextOnly[i];
            }
        }

        public TextOnly(String str) {
            this.f3657a = str;
        }

        public String getText() {
            return this.f3657a;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.f3657a);
        }

        private TextOnly(Parcel parcel) {
            this.f3657a = parcel.readString();
        }
    }
}
