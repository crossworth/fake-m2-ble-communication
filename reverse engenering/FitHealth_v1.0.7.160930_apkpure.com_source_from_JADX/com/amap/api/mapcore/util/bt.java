package com.amap.api.mapcore.util;

@el(a = "update_item_download_info")
/* compiled from: DTDownloadInfo */
class bt {
    @em(a = "mAdcode", b = 6)
    private String f283a = "";
    @em(a = "fileLength", b = 5)
    private long f284b = 0;
    @em(a = "splitter", b = 2)
    private int f285c = 0;
    @em(a = "startPos", b = 5)
    private long f286d = 0;
    @em(a = "endPos", b = 5)
    private long f287e = 0;

    public bt(String str, long j, int i, long j2, long j3) {
        this.f283a = str;
        this.f284b = j;
        this.f285c = i;
        this.f286d = j2;
        this.f287e = j3;
    }

    public long m349a(int i) {
        switch (i) {
            case 0:
                return m350b();
            default:
                return 0;
        }
    }

    public long m351b(int i) {
        switch (i) {
            case 0:
                return m352c();
            default:
                return 0;
        }
    }

    public static String m347a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mAdcode");
        stringBuilder.append("='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    public long m348a() {
        return this.f284b;
    }

    public long m350b() {
        return this.f286d;
    }

    public long m352c() {
        return this.f287e;
    }
}
