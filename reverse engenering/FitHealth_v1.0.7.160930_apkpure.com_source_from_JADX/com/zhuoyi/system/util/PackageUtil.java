package com.zhuoyi.system.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.regex.Pattern;

public class PackageUtil {
    private static final List<String> EMPTY_LIST = new ArrayList();

    class C10811 implements FileFilter {
        private final /* synthetic */ boolean val$recursive;

        C10811(boolean z) {
            this.val$recursive = z;
        }

        public boolean accept(File file) {
            return (this.val$recursive && file.isDirectory()) || file.getName().endsWith(".class");
        }
    }

    public static String[] getResourceInPackage(String packageName) throws IOException {
        String packageOnly = packageName;
        boolean recursive = false;
        if (packageName.endsWith(".*")) {
            packageOnly = packageName.substring(0, packageName.lastIndexOf(".*"));
            recursive = true;
        }
        if (packageOnly.endsWith("/")) {
            packageOnly = packageOnly.substring(0, packageName.length() - 1);
        }
        List<String> vResult = new ArrayList();
        String packageDirName = packageOnly.replace('.', '/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = (URL) dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                getResourceInDirPackage(packageOnly, URLDecoder.decode(url.getFile(), "UTF-8"), recursive, vResult);
            } else if ("jar".equals(protocol)) {
                Enumeration<JarEntry> entries = ((JarURLConnection) url.openConnection()).getJarFile().entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) == '/') {
                        name = name.substring(1);
                    }
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf(47);
                        if (idx != -1) {
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        if ((idx != -1 || recursive) && !entry.isDirectory()) {
                            vResult.add(new StringBuilder(String.valueOf(packageName)).append(".").append(name.substring(packageName.length() + 1)).toString());
                        }
                    }
                }
            }
        }
        return (String[]) vResult.toArray(new String[vResult.size()]);
    }

    private static void getResourceInDirPackage(String packageName, String packagePath, boolean recursive, List<String> classes) {
        File dir = new File(packagePath);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    getResourceInDirPackage(new StringBuilder(String.valueOf(packageName)).append(".").append(file.getName()).toString(), file.getAbsolutePath(), recursive, classes);
                } else {
                    classes.add(new StringBuilder(String.valueOf(packageName)).append(".").append(file.getName()).toString());
                }
            }
        }
    }

    public static String[] findClassesInPackage(String packageName, List<String> included, List<String> excluded) throws IOException {
        String packageOnly = packageName;
        boolean recursive = false;
        if (packageName.endsWith(".*")) {
            packageOnly = packageName.substring(0, packageName.lastIndexOf(".*"));
            recursive = true;
        }
        List<String> vResult = new ArrayList();
        String packageDirName = packageOnly.replace('.', '/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = (URL) dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                findClassesInDirPackage(packageOnly, included, excluded, URLDecoder.decode(url.getFile(), "UTF-8"), recursive, vResult);
            } else if ("jar".equals(protocol)) {
                Enumeration<JarEntry> entries = ((JarURLConnection) url.openConnection()).getJarFile().entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) == '/') {
                        name = name.substring(1);
                    }
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf(47);
                        if (idx != -1) {
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        if (idx != -1 || recursive) {
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                includeOrExcludeClass(packageName, name.substring(packageName.length() + 1, name.length() - 6), included, excluded, vResult);
                            }
                        }
                    }
                }
            }
        }
        return (String[]) vResult.toArray(new String[vResult.size()]);
    }

    private static void findClassesInDirPackage(String packageName, List<String> included, List<String> excluded, String packagePath, boolean recursive, List<String> classes) {
        File dir = new File(packagePath);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles(new C10811(recursive))) {
                if (file.isDirectory()) {
                    findClassesInDirPackage(new StringBuilder(String.valueOf(packageName)).append(".").append(file.getName()).toString(), included, excluded, file.getAbsolutePath(), recursive, classes);
                } else {
                    includeOrExcludeClass(packageName, file.getName().substring(0, file.getName().length() - 6), included, excluded, classes);
                }
            }
        }
    }

    private static void includeOrExcludeClass(String packageName, String className, List<String> included, List<String> excluded, List<String> classes) {
        if (isIncluded(className, included, excluded)) {
            classes.add(new StringBuilder(String.valueOf(packageName)).append('.').append(className).toString());
        }
    }

    private static boolean isIncluded(String name, List<String> included, List<String> excluded) {
        if (included == null) {
            included = EMPTY_LIST;
        }
        if (excluded == null) {
            excluded = EMPTY_LIST;
        }
        if (included.size() == 0 && excluded.size() == 0) {
            return true;
        }
        boolean isIncluded = find(name, included);
        boolean isExcluded = find(name, excluded);
        if (isIncluded && !isExcluded) {
            return true;
        }
        if (isExcluded) {
            return false;
        }
        return included.size() == 0;
    }

    private static boolean find(String name, List<String> list) {
        for (String regexpStr : list) {
            if (Pattern.matches(regexpStr, name)) {
                return true;
            }
        }
        return false;
    }
}
