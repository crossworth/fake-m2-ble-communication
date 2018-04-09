package com.tencent.wxop.stat.p023b;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

final class C0887n implements FileFilter {
    C0887n() {
    }

    public final boolean accept(File file) {
        return Pattern.matches("cpu[0-9]", file.getName());
    }
}
