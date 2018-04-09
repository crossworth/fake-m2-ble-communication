package com.tencent.stat.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class C0832d {
    public static File m2688a(String str) {
        File file = new File(str);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                C0832d.m2688a(file.getParentFile().getAbsolutePath());
            }
            file.mkdir();
        }
        return file;
    }

    public static List<String> m2689a(File file) {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            arrayList.add(readLine.trim());
        }
    }
}