package com.droi.sdk.core.priv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class C0904h {
    private C0904h() {
    }

    public static void m2676a(String str) throws IOException {
        String str2 = null;
        String name = new File(str).getName();
        if (name.startsWith("lib") && name.endsWith(".so")) {
            int i;
            try {
                System.loadLibrary(name.substring(3, name.length() - 3));
                i = 1;
            } catch (UnsatisfiedLinkError e) {
                i = 0;
            }
            if (i != 0) {
                return;
            }
        }
        if (str.startsWith("/")) {
            String[] split = str.split("/");
            name = split.length > 1 ? split[split.length - 1] : null;
            String str3 = "";
            if (name != null) {
                String[] split2 = name.split("\\.", 2);
                str3 = split2[0];
                if (split2.length > 1) {
                    str2 = "." + split2[split2.length - 1];
                }
            }
            if (name == null || str3.length() < 3) {
                throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
            }
            File createTempFile = File.createTempFile(str3, str2);
            createTempFile.deleteOnExit();
            if (createTempFile.exists()) {
                byte[] bArr = new byte[1024];
                InputStream resourceAsStream = C0904h.class.getResourceAsStream(str);
                if (resourceAsStream == null) {
                    throw new FileNotFoundException("File " + str + " was not found inside JAR.");
                }
                OutputStream fileOutputStream = new FileOutputStream(createTempFile);
                while (true) {
                    try {
                        int read = resourceAsStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    } finally {
                        fileOutputStream.close();
                        resourceAsStream.close();
                    }
                }
                System.load(createTempFile.getAbsolutePath());
                return;
            }
            throw new FileNotFoundException("File " + createTempFile.getAbsolutePath() + " does not exist.");
        }
        throw new IllegalArgumentException("The path has to be absolute (start with '/').");
    }
}
