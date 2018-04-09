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
        public static final Creator<TextAndMediaPath> CREATOR = new C07071();
        private String f2445a;
        private String f2446b;

        /* compiled from: ProGuard */
        static class C07071 implements Creator<TextAndMediaPath> {
            C07071() {
            }

            public TextAndMediaPath createFromParcel(Parcel parcel) {
                return new TextAndMediaPath(parcel);
            }

            public TextAndMediaPath[] newArray(int i) {
                return new TextAndMediaPath[i];
            }
        }

        public TextAndMediaPath(String str, String str2) {
            this.f2445a = str;
            this.f2446b = str2;
        }

        public String getText() {
            return this.f2445a;
        }

        public String getMediaPath() {
            return this.f2446b;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.f2445a);
            parcel.writeString(this.f2446b);
        }

        private TextAndMediaPath(Parcel parcel) {
            this.f2445a = parcel.readString();
            this.f2446b = parcel.readString();
        }
    }

    /* compiled from: ProGuard */
    public static class TextOnly implements Parcelable {
        public static final Creator<TextOnly> CREATOR = new C07081();
        private String f2447a;

        /* compiled from: ProGuard */
        static class C07081 implements Creator<TextOnly> {
            C07081() {
            }

            public TextOnly createFromParcel(Parcel parcel) {
                return new TextOnly(parcel);
            }

            public TextOnly[] newArray(int i) {
                return new TextOnly[i];
            }
        }

        public TextOnly(String str) {
            this.f2447a = str;
        }

        public String getText() {
            return this.f2447a;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.f2447a);
        }

        private TextOnly(Parcel parcel) {
            this.f2447a = parcel.readString();
        }
    }
}
