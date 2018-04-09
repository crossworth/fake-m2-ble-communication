package com.amap.api.mapcore.util;

@el(a = "update_item_file")
/* compiled from: DTFileInfo */
class bu {
    @em(a = "mAdcode", b = 6)
    private String f288a = "";
    @em(a = "file", b = 6)
    private String f289b = "";

    public bu(String str, String str2) {
        this.f288a = str;
        this.f289b = str2;
    }

    public String m355a() {
        return this.f289b;
    }

    public static String m353a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mAdcode");
        stringBuilder.append("='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    public static String m354b(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mAdcode");
        stringBuilder.append("='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}
