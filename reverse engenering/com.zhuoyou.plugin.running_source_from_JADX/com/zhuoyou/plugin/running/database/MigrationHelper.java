package com.zhuoyou.plugin.running.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MigrationHelper {
    private static java.util.List<java.lang.String> getColumns(android.database.sqlite.SQLiteDatabase r5, java.lang.String r6) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 0;
        r1 = 0;
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r3.<init>();	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r4 = "SELECT * FROM ";	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r3 = r3.append(r6);	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r4 = " limit 0";	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r3 = r3.toString();	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r4 = 0;	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r1 = r5.rawQuery(r3, r4);	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        if (r1 == 0) goto L_0x0030;	 Catch:{ Exception -> 0x003d, all -> 0x004e }
    L_0x0022:
        r3 = r1.getColumnCount();	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        if (r3 <= 0) goto L_0x0030;	 Catch:{ Exception -> 0x003d, all -> 0x004e }
    L_0x0028:
        r3 = r1.getColumnNames();	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        r0 = java.util.Arrays.asList(r3);	 Catch:{ Exception -> 0x003d, all -> 0x004e }
    L_0x0030:
        if (r1 == 0) goto L_0x0035;
    L_0x0032:
        r1.close();
    L_0x0035:
        if (r0 != 0) goto L_0x003c;
    L_0x0037:
        r0 = new java.util.ArrayList;
        r0.<init>();
    L_0x003c:
        return r0;
    L_0x003d:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ Exception -> 0x003d, all -> 0x004e }
        if (r1 == 0) goto L_0x0046;
    L_0x0043:
        r1.close();
    L_0x0046:
        if (r0 != 0) goto L_0x003c;
    L_0x0048:
        r0 = new java.util.ArrayList;
        r0.<init>();
        goto L_0x003c;
    L_0x004e:
        r3 = move-exception;
        if (r1 == 0) goto L_0x0054;
    L_0x0051:
        r1.close();
    L_0x0054:
        if (r0 != 0) goto L_0x005b;
    L_0x0056:
        r0 = new java.util.ArrayList;
        r0.<init>();
    L_0x005b:
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.database.MigrationHelper.getColumns(android.database.sqlite.SQLiteDatabase, java.lang.String):java.util.List<java.lang.String>");
    }

    @SafeVarargs
    public static void migrate(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
        createAllTables(db, false, daoClasses);
        restoreData(db, daoClasses);
    }

    @SafeVarargs
    private static void generateTempTables(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);
            String tableName = daoConfig.tablename;
            if (checkTable(db, tableName).booleanValue()) {
                db.execSQL("alter table " + tableName + " rename to " + daoConfig.tablename.concat("_TEMP") + ";");
            }
        }
    }

    private static Boolean checkTable(SQLiteDatabase db, String tableName) {
        boolean z = false;
        Cursor c = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        if (c.moveToNext()) {
            int count = c.getInt(0);
            c.close();
            if (count > 0) {
                z = true;
            }
            return Boolean.valueOf(z);
        }
        c.close();
        return Boolean.valueOf(false);
    }

    @SafeVarargs
    private static void dropAllTables(SQLiteDatabase db, boolean ifExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
    }

    @SafeVarargs
    private static void createAllTables(SQLiteDatabase db, boolean ifNotExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
    }

    @SafeVarargs
    private static void reflectMethod(SQLiteDatabase db, String methodName, boolean isExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (daoClasses.length >= 1) {
            try {
                for (Class cls : daoClasses) {
                    cls.getDeclaredMethod(methodName, new Class[]{SQLiteDatabase.class, Boolean.TYPE}).invoke(null, new Object[]{db, Boolean.valueOf(isExists)});
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
    }

    @SafeVarargs
    private static void restoreData(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            if (checkTable(db, tempTableName).booleanValue()) {
                List<String> columns = getColumns(db, tempTableName);
                ArrayList<String> properties = new ArrayList(columns.size());
                for (Property property : daoConfig.properties) {
                    String columnName = property.columnName;
                    if (columns.contains(columnName)) {
                        properties.add(columnName);
                    }
                }
                if (properties.size() > 0) {
                    String columnSQL = TextUtils.join(",", properties);
                    db.execSQL("INSERT INTO " + tableName + " (" + columnSQL + ") SELECT " + columnSQL + " FROM " + tempTableName + ";");
                }
                db.execSQL("DROP TABLE " + tempTableName);
            }
        }
    }
}
