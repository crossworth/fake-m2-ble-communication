package com.zhuoyi.system.promotion.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.zhuoyi.system.network.object.AdInfo;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.object.SerApkInfo;
import com.zhuoyi.system.promotion.model.Shortcut;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class PromDBUtils {
    public static final String DATABASE_NAME = "ZySDK_prom";
    public static final int DATABASE_VERSION = 8;
    public static final String PROM_APP_INFO_ACTION = "prom_app_info_action";
    public static final String PROM_APP_INFO_ACTION_TYPE = "prom_app_info_action_type";
    public static final String PROM_APP_INFO_AD_TYPE = "prom_app_info_ad_type";
    public static final String PROM_APP_INFO_APP_NAME = "prom_app_info_app_name";
    public static final String PROM_APP_INFO_CONTENT = "prom_app_info_content";
    public static final String PROM_APP_INFO_DESC = "prom_app_info_desc";
    public static final String PROM_APP_INFO_DISC_PICS = "prom_app_info_disc_pics";
    public static final String PROM_APP_INFO_DOWNLOAD_NUM = "prom_app_info_downloapp_num";
    public static final String PROM_APP_INFO_FILE_SIZE = "prom_app_info_file_size";
    public static final String PROM_APP_INFO_ICON_ID = "prom_app_info_icon_id";
    public static final String PROM_APP_INFO_ID = "prom_app_info_id";
    public static final String PROM_APP_INFO_MD5 = "prom_app_info_md5";
    public static final String PROM_APP_INFO_PACKAGE_NAME = "prom_app_info_package_name";
    public static final String PROM_APP_INFO_POSITION = "prom_app_info_position";
    public static final String PROM_APP_INFO_RESERVED1 = "prom_app_info_reserved1";
    public static final String PROM_APP_INFO_SHOW_PIC_ID = "prom_app_info_show_pic_id";
    public static final String PROM_APP_INFO_SHOW_PIC_URL = "prom_app_info_show_pic_url";
    public static final String PROM_APP_INFO_SHOW_TIME = "prom_app_info_show_time";
    public static final String PROM_APP_INFO_TITLE = "prom_app_info_title";
    public static final String PROM_APP_INFO_TYPE = "prom_app_info_type";
    public static final String PROM_APP_INFO_URL = "prom_app_info_url";
    public static final String PROM_APP_INFO_VERSION = "prom_app_info_version";
    public static final String PROM_APP_INFO_VERSION_NAME = "prom_app_info_version_name";
    public static final String PROM_CONFIG_KEY = "prom_cfg_key";
    public static final String PROM_CONFIG_TABLE = "prom_cfg_table";
    public static final String PROM_CONFIG_VALUE = "prom_cfg_value";
    public static final String PROM_DESKTOP_AD_HTML5_TABLE = "prom_desktop_ad_html5_table";
    public static final String PROM_DESKTOP_AD_IMAGE_ACTION_TYPE = "prom_desktop_ad_image_action_type";
    public static final String PROM_DESKTOP_AD_IMAGE_ACTION_URL = "prom_desktop_ad_image_action_url";
    public static final String PROM_DESKTOP_AD_IMAGE_APP_NAME = "prom_desktop_ad_image_app_name";
    public static final String PROM_DESKTOP_AD_IMAGE_APP_VER_CODE = "prom_desktop_ad_image_app_ver_code";
    public static final String PROM_DESKTOP_AD_IMAGE_APP_VER_NAME = "prom_desktop_ad_image_app_ver_name";
    public static final String PROM_DESKTOP_AD_IMAGE_DESC = "prom_desktop_ad_image_desc";
    public static final String PROM_DESKTOP_AD_IMAGE_ICON_ID = "prom_desktop_ad_image_icon_id";
    public static final String PROM_DESKTOP_AD_IMAGE_ICON_URL = "prom_desktop_ad_image_icon_url";
    public static final String PROM_DESKTOP_AD_IMAGE_ID = "prom_desktop_ad_image_id";
    public static final String PROM_DESKTOP_AD_IMAGE_MD5 = "prom_desktop_ad_image_md5";
    public static final String PROM_DESKTOP_AD_IMAGE_PACKNAME = "prom_desktop_ad_image_packname";
    public static final String PROM_DESKTOP_AD_IMAGE_REMAIN_TIME = "prom_desktop_ad_image_remain_time";
    public static final String PROM_DESKTOP_AD_IMAGE_RESERVED1 = "prom_desktop_ad_image_reserved1";
    public static final String PROM_DESKTOP_AD_IMAGE_RESERVED2 = "prom_desktop_ad_image_reserved2";
    public static final String PROM_DESKTOP_AD_IMAGE_RESERVED3 = "prom_desktop_ad_image_reserved3";
    public static final String PROM_DESKTOP_AD_IMAGE_SHOWTIME = "prom_desktop_ad_image_showtime";
    public static final String PROM_DESKTOP_AD_IMAGE_SHOWTYPE = "prom_desktop_ad_image_showtype";
    public static final String PROM_DESKTOP_AD_IMAGE_TABLE = "prom_desktop_ad_image_table";
    public static final String PROM_DESKTOP_AD_IMAGE_TITLE = "prom_desktop_ad_image_title";
    public static final String PROM_DESKTOP_AD_IMAGE_TYPE = "prom_desktop_ad_image_type";
    public static final String PROM_DESKTOP_AD_IMAGE_URL = "prom_desktop_ad_image_url";
    public static final String PROM_EXIT_TABLE = "prom_exit_table";
    public static final String PROM_HTML5_ID = "prom_html5_id";
    public static final String PROM_HTML5_TABLE = "prom_html5_table";
    public static final String PROM_HTMl5_DESC = "prom_html5_desc";
    public static final String PROM_HTMl5_REMAIN_TIME = "prom_html5_remaintime";
    public static final String PROM_HTMl5_RESERVED1 = "prom_html5_reserved1";
    public static final String PROM_HTMl5_RESERVED2 = "prom_html5_reserved2";
    public static final String PROM_HTMl5_RESERVED3 = "prom_html5_reserved3";
    public static final String PROM_HTMl5_SHOWTIME = "prom_html5_showtime";
    public static final String PROM_HTMl5_SHOWTYPE = "prom_html5_showtype";
    public static final String PROM_HTMl5_TITLE = "prom_html5_title";
    public static final String PROM_HTMl5_URL = "prom_html5_url";
    public static final String PROM_INSTALLED_APP_TABLE = "prom_installed_app_table";
    public static final String PROM_KEY_APP_LIST_DISPLAY_TYPE = "prom_key_app_list_display_type";
    public static final String PROM_KEY_FIRST_REQ_TIME = "prom_key_first_req_time";
    public static final String PROM_KEY_HAS_CREATE_SHORTCUT = "prom_key_has_create_shortcut";
    public static final String PROM_KEY_IS_PHONE_ROOTED = "prom_key_is_phone_rooted";
    public static final String PROM_KEY_IS_SYSTEM_APP = "prom_key_is_system_app";
    public static final String PROM_KEY_SYS_OPTI_PROMPT_NETWORK = "prom_key_sys_opti_prompt_network";
    public static final String PROM_PUSH_NOTIFY_TABLE = "prom_push_notify_table";
    public static final String PROM_SHORTCUT_ACTIVITY_NAME = "prom_shortcut_activity_name";
    public static final String PROM_SHORTCUT_ICON_ID = "prom_shortcut_icon_id";
    public static final String PROM_SHORTCUT_NAME = "prom_shortcut_name";
    public static final String PROM_SHORTCUT_PACKAGE_NAME = "prom_shortcut_package_names";
    public static final String PROM_SHORTCUT_STATUS = "prom_shortcut_status";
    public static final String PROM_SHORTCUT_TABLE = "prom_shortcut_table";
    public static final String PROM_TAB_PAGE_INFO_ID = "prom_tab_page_info_id";
    public static final String PROM_TAB_PAGE_INFO_NAME = "prom_tab_page_info_name";
    public static final String PROM_TAB_PAGE_INFO_TYPE = "prom_tab_page_info_type";
    public static final String PROM_WIDGET_INFO_APP_NAME = "prom_widget_info_app_name";
    public static final String PROM_WIDGET_INFO_COMMAND_TYPE = "prom_widget_info_command_type";
    public static final String PROM_WIDGET_INFO_DOWNLOAD_URL = "prom_widget_info_download_url";
    public static final String PROM_WIDGET_INFO_FILE_NAME = "prom_widget_info_file_name";
    public static final String PROM_WIDGET_INFO_FILE_SIZE = "prom_widget_info_file_size";
    public static final String PROM_WIDGET_INFO_ICON_ID = "prom_widget_info_icon_id";
    public static final String PROM_WIDGET_INFO_ICON_URL = "prom_widget_info_icon_url";
    public static final String PROM_WIDGET_INFO_ID = "prom_widget_info_id";
    public static final String PROM_WIDGET_INFO_MD5 = "prom_widget_info_md5";
    public static final String PROM_WIDGET_INFO_NETWORK_ENABLED = "prom_widget_info_network_enabled";
    public static final String PROM_WIDGET_INFO_PACKAGE_NAME = "prom_widget_info_package_name";
    public static final String PROM_WIDGET_INFO_RESERVED2 = "prom_widget_info_reserved2";
    public static final String PROM_WIDGET_INFO_TABLE = "prom_widget_info_table";
    public static final String PROM_WIDGET_INFO_VER = "prom_widget_info_ver";
    public static final String PROM_WIDGET_INFO_VER_NAME = "prom_widget_info_ver_name";
    private static PromDBUtils instance = null;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase = null;
    private SQLiteOpenHelper mSqlOpenHelper;

    public class PromotionDataBaseHelper extends SQLiteOpenHelper {
        public PromotionDataBaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_push_notify_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, prom_app_info_id INTEGER, prom_app_info_app_name text, prom_app_info_icon_id INTEGER, prom_app_info_url text, prom_app_info_desc text, prom_app_info_title text, prom_app_info_type INTEGER, prom_app_info_action text, prom_app_info_position INTEGER, prom_app_info_package_name text, prom_app_info_show_time text, prom_app_info_version INTEGER, prom_app_info_downloapp_num INTEGER, prom_app_info_file_size INTEGER, prom_app_info_disc_pics text, prom_app_info_md5 text,prom_app_info_version_name text,prom_app_info_content text, prom_app_info_ad_type INTEGER, prom_app_info_action_type INTEGER, prom_app_info_show_pic_id INTEGER, prom_app_info_show_pic_url text, prom_app_info_reserved1 text)");
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_cfg_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, prom_cfg_key text, prom_cfg_value text)");
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_shortcut_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, prom_shortcut_package_names text, prom_shortcut_activity_name text, prom_shortcut_name text,prom_shortcut_icon_id INTEGER, prom_shortcut_status INTEGER)");
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_desktop_ad_image_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, prom_desktop_ad_image_id INTEGER, prom_desktop_ad_image_url text, prom_desktop_ad_image_title text, prom_desktop_ad_image_desc text, prom_desktop_ad_image_showtype INTEGER, prom_desktop_ad_image_showtime text, prom_desktop_ad_image_remain_time INTEGER, prom_desktop_ad_image_type INTEGER, prom_desktop_ad_image_action_type INTEGER, prom_desktop_ad_image_action_url text, prom_desktop_ad_image_packname text, prom_desktop_ad_image_app_ver_code INTEGER, prom_desktop_ad_image_app_ver_name text, prom_desktop_ad_image_app_name text, prom_desktop_ad_image_md5 text, prom_desktop_ad_image_icon_url text, prom_desktop_ad_image_icon_id INTEGER, prom_desktop_ad_image_reserved1 text, prom_desktop_ad_image_reserved2 text, prom_desktop_ad_image_reserved3 text )");
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_html5_table (_id INTEGER PRIMARY KEY AUTOINCREMENT,  prom_html5_id INTEGER, prom_html5_url text, prom_html5_title text, prom_html5_desc text, prom_html5_showtype INTEGER, prom_html5_showtime text, prom_html5_remaintime INTEGER, prom_html5_reserved1 text, prom_html5_reserved2 text, prom_html5_reserved3 text)");
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_widget_info_table (_id INTEGER PRIMARY KEY AUTOINCREMENT,  prom_widget_info_id INTEGER, prom_widget_info_icon_id INTEGER, prom_widget_info_icon_url text, prom_widget_info_app_name text, prom_widget_info_package_name text, prom_widget_info_download_url text, prom_widget_info_md5 text, prom_widget_info_ver INTEGER, prom_widget_info_file_size INTEGER, prom_widget_info_command_type INTEGER, prom_widget_info_network_enabled INTEGER, prom_widget_info_ver_name text, prom_widget_info_file_name text, prom_widget_info_reserved2 text)");
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_exit_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, prom_app_info_id INTEGER, prom_app_info_app_name text, prom_app_info_icon_id INTEGER, prom_app_info_url text, prom_app_info_desc text, prom_app_info_title text, prom_app_info_type INTEGER, prom_app_info_action text, prom_app_info_position INTEGER, prom_app_info_package_name text, prom_app_info_show_time text, prom_app_info_version INTEGER, prom_app_info_downloapp_num INTEGER, prom_app_info_file_size INTEGER, prom_app_info_disc_pics text, prom_app_info_md5 text,prom_app_info_version_name text,prom_app_info_content text, prom_app_info_ad_type INTEGER, prom_app_info_action_type INTEGER, prom_app_info_show_pic_id INTEGER, prom_app_info_show_pic_url text, prom_app_info_reserved1 text)");
            db.execSQL("CREATE TABLE IF NOT EXISTS prom_installed_app_table (_id INTEGER PRIMARY KEY AUTOINCREMENT,  prom_app_info_package_name text, prom_app_info_version INTEGER)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                try {
                    PromDBUtils.this.dropAll(db);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            onCreate(db);
        }
    }

    public static synchronized PromDBUtils getInstance(Context context) {
        PromDBUtils promDBUtils;
        synchronized (PromDBUtils.class) {
            if (instance == null) {
                instance = new PromDBUtils(context);
            }
            if (!instance.isOpen()) {
                instance.open();
            }
            promDBUtils = instance;
        }
        return promDBUtils;
    }

    public PromDBUtils(Context context) {
        this.mContext = context;
    }

    private boolean isOpen() {
        return (this.mSqlOpenHelper == null || this.mSQLiteDatabase == null) ? false : true;
    }

    public void open() {
        try {
            this.mSqlOpenHelper = new PromotionDataBaseHelper(this.mContext, DATABASE_NAME, null, 8);
            this.mSQLiteDatabase = this.mSqlOpenHelper.getWritableDatabase();
            this.mSQLiteDatabase.setLocale(Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (this.mSqlOpenHelper != null) {
            this.mSqlOpenHelper.close();
        }
    }

    private ArrayList<String> swtichPicStringToArray(String picString) {
        ArrayList<String> ret = new ArrayList();
        if (!TextUtils.isEmpty(picString)) {
            for (String s : picString.split(SeparatorConstants.SEPARATOR_RES_URL)) {
                if (!TextUtils.isEmpty(s.trim())) {
                    ret.add(s);
                }
            }
        }
        return ret;
    }

    private String swtichArrayToPicString(ArrayList<String> picArray) {
        String ret = "";
        if (picArray != null && picArray.size() > 0) {
            Iterator it = picArray.iterator();
            while (it.hasNext()) {
                String s = (String) it.next();
                if (!TextUtils.isEmpty(s.trim())) {
                    ret = new StringBuilder(String.valueOf(ret)).append(s).append(SeparatorConstants.SEPARATOR_RES_URL).toString();
                }
            }
        }
        return ret;
    }

    public PromAppInfo queryPushNotifyById(int id) {
        PromAppInfo ret = null;
        if (this.mSQLiteDatabase == null) {
            return null;
        }
        Cursor c = this.mSQLiteDatabase.query(true, PROM_PUSH_NOTIFY_TABLE, new String[]{PROM_APP_INFO_ID, PROM_APP_INFO_APP_NAME, PROM_APP_INFO_ICON_ID, PROM_APP_INFO_URL, PROM_APP_INFO_DESC, PROM_APP_INFO_TITLE, PROM_APP_INFO_TYPE, PROM_APP_INFO_ACTION, PROM_APP_INFO_POSITION, PROM_APP_INFO_PACKAGE_NAME, PROM_APP_INFO_SHOW_TIME, PROM_APP_INFO_VERSION, PROM_APP_INFO_DOWNLOAD_NUM, PROM_APP_INFO_FILE_SIZE, PROM_APP_INFO_DISC_PICS, PROM_APP_INFO_MD5, PROM_APP_INFO_VERSION_NAME, PROM_APP_INFO_CONTENT, PROM_APP_INFO_AD_TYPE, PROM_APP_INFO_ACTION_TYPE, PROM_APP_INFO_SHOW_PIC_ID, PROM_APP_INFO_SHOW_PIC_URL, PROM_APP_INFO_RESERVED1}, "prom_app_info_id='" + id + "'", null, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            ret = new PromAppInfo();
            ret.setId(c.getInt(c.getColumnIndex(PROM_APP_INFO_ID)));
            ret.setAppName(c.getString(c.getColumnIndex(PROM_APP_INFO_APP_NAME)));
            ret.setAction(c.getString(c.getColumnIndex(PROM_APP_INFO_ACTION)));
            ret.setDesc(c.getString(c.getColumnIndex(PROM_APP_INFO_DESC)));
            ret.setIconId(c.getInt(c.getColumnIndex(PROM_APP_INFO_ICON_ID)));
            ret.setPackageName(c.getString(c.getColumnIndex(PROM_APP_INFO_PACKAGE_NAME)));
            ret.setPosition((short) c.getInt(c.getColumnIndex(PROM_APP_INFO_POSITION)));
            ret.setTitle(c.getString(c.getColumnIndex(PROM_APP_INFO_TITLE)));
            ret.setType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_TYPE)));
            ret.setUrl(c.getString(c.getColumnIndex(PROM_APP_INFO_URL)));
            ret.setDisplayTime(c.getString(c.getColumnIndex(PROM_APP_INFO_SHOW_TIME)));
            ret.setVer(c.getInt(c.getColumnIndex(PROM_APP_INFO_VERSION)));
            ret.setDownloadNum(c.getInt(c.getColumnIndex(PROM_APP_INFO_DOWNLOAD_NUM)));
            ret.setFileSize(c.getInt(c.getColumnIndex(PROM_APP_INFO_FILE_SIZE)));
            ret.setDetailPic(swtichPicStringToArray(c.getString(c.getColumnIndex(PROM_APP_INFO_DISC_PICS))));
            ret.setFileVerifyCode(c.getString(c.getColumnIndex(PROM_APP_INFO_MD5)));
            ret.setVersionName(c.getString(c.getColumnIndex(PROM_APP_INFO_VERSION_NAME)));
            ret.setTip(c.getString(c.getColumnIndex(PROM_APP_INFO_CONTENT)));
            ret.setAdType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_AD_TYPE)));
            ret.setActionType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_ACTION_TYPE)));
            ret.setShowIconId(c.getInt(c.getColumnIndex(PROM_APP_INFO_SHOW_PIC_ID)));
            ret.setShowPicUrl(c.getString(c.getColumnIndex(PROM_APP_INFO_SHOW_PIC_URL)));
            ret.setReserved1(c.getString(c.getColumnIndex(PROM_APP_INFO_RESERVED1)));
        }
        c.close();
        return ret;
    }

    public ArrayList<PromAppInfo> queryAllPushNotify() {
        ArrayList<PromAppInfo> ret = new ArrayList();
        if (this.mSQLiteDatabase != null) {
            Cursor c = this.mSQLiteDatabase.query(true, PROM_PUSH_NOTIFY_TABLE, new String[]{PROM_APP_INFO_ID, PROM_APP_INFO_APP_NAME, PROM_APP_INFO_ICON_ID, PROM_APP_INFO_URL, PROM_APP_INFO_DESC, PROM_APP_INFO_TITLE, PROM_APP_INFO_TYPE, PROM_APP_INFO_ACTION, PROM_APP_INFO_POSITION, PROM_APP_INFO_PACKAGE_NAME, PROM_APP_INFO_SHOW_TIME, PROM_APP_INFO_VERSION, PROM_APP_INFO_DOWNLOAD_NUM, PROM_APP_INFO_FILE_SIZE, PROM_APP_INFO_DISC_PICS, PROM_APP_INFO_MD5, PROM_APP_INFO_VERSION_NAME, PROM_APP_INFO_CONTENT, PROM_APP_INFO_AD_TYPE, PROM_APP_INFO_ACTION_TYPE, PROM_APP_INFO_ACTION_TYPE, PROM_APP_INFO_SHOW_PIC_ID, PROM_APP_INFO_SHOW_PIC_URL, PROM_APP_INFO_RESERVED1}, null, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    PromAppInfo promAppInfo = new PromAppInfo();
                    promAppInfo.setId(c.getInt(c.getColumnIndex(PROM_APP_INFO_ID)));
                    promAppInfo.setAppName(c.getString(c.getColumnIndex(PROM_APP_INFO_APP_NAME)));
                    promAppInfo.setAction(c.getString(c.getColumnIndex(PROM_APP_INFO_ACTION)));
                    promAppInfo.setDesc(c.getString(c.getColumnIndex(PROM_APP_INFO_DESC)));
                    promAppInfo.setIconId(c.getInt(c.getColumnIndex(PROM_APP_INFO_ICON_ID)));
                    promAppInfo.setPackageName(c.getString(c.getColumnIndex(PROM_APP_INFO_PACKAGE_NAME)));
                    promAppInfo.setPosition((short) c.getInt(c.getColumnIndex(PROM_APP_INFO_POSITION)));
                    promAppInfo.setTitle(c.getString(c.getColumnIndex(PROM_APP_INFO_TITLE)));
                    promAppInfo.setType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_TYPE)));
                    promAppInfo.setUrl(c.getString(c.getColumnIndex(PROM_APP_INFO_URL)));
                    promAppInfo.setDisplayTime(c.getString(c.getColumnIndex(PROM_APP_INFO_SHOW_TIME)));
                    promAppInfo.setVer(c.getInt(c.getColumnIndex(PROM_APP_INFO_VERSION)));
                    promAppInfo.setDownloadNum(c.getInt(c.getColumnIndex(PROM_APP_INFO_DOWNLOAD_NUM)));
                    promAppInfo.setFileSize(c.getInt(c.getColumnIndex(PROM_APP_INFO_FILE_SIZE)));
                    promAppInfo.setDetailPic(swtichPicStringToArray(c.getString(c.getColumnIndex(PROM_APP_INFO_DISC_PICS))));
                    promAppInfo.setFileVerifyCode(c.getString(c.getColumnIndex(PROM_APP_INFO_MD5)));
                    promAppInfo.setVersionName(c.getString(c.getColumnIndex(PROM_APP_INFO_VERSION_NAME)));
                    promAppInfo.setTip(c.getString(c.getColumnIndex(PROM_APP_INFO_CONTENT)));
                    promAppInfo.setAdType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_AD_TYPE)));
                    promAppInfo.setActionType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_ACTION_TYPE)));
                    promAppInfo.setShowIconId(c.getInt(c.getColumnIndex(PROM_APP_INFO_SHOW_PIC_ID)));
                    promAppInfo.setShowPicUrl(c.getString(c.getColumnIndex(PROM_APP_INFO_SHOW_PIC_URL)));
                    promAppInfo.setReserved1(c.getString(c.getColumnIndex(PROM_APP_INFO_RESERVED1)));
                    ret.add(promAppInfo);
                    c.moveToNext();
                }
            }
            c.close();
        }
        return ret;
    }

    public List<AdInfo> queryAllDesktopImageAd() {
        List<AdInfo> adInfos = new ArrayList();
        if (this.mSQLiteDatabase != null) {
            Cursor c = this.mSQLiteDatabase.query(true, PROM_DESKTOP_AD_IMAGE_TABLE, new String[]{PROM_DESKTOP_AD_IMAGE_ID, PROM_DESKTOP_AD_IMAGE_URL, PROM_DESKTOP_AD_IMAGE_TITLE, PROM_DESKTOP_AD_IMAGE_DESC, PROM_DESKTOP_AD_IMAGE_SHOWTYPE, PROM_DESKTOP_AD_IMAGE_SHOWTIME, PROM_DESKTOP_AD_IMAGE_REMAIN_TIME, PROM_DESKTOP_AD_IMAGE_TYPE, PROM_DESKTOP_AD_IMAGE_ACTION_TYPE, PROM_DESKTOP_AD_IMAGE_ACTION_URL, PROM_DESKTOP_AD_IMAGE_PACKNAME, PROM_DESKTOP_AD_IMAGE_APP_VER_CODE, PROM_DESKTOP_AD_IMAGE_APP_VER_NAME, PROM_DESKTOP_AD_IMAGE_APP_NAME, PROM_DESKTOP_AD_IMAGE_MD5, PROM_DESKTOP_AD_IMAGE_ICON_ID, PROM_DESKTOP_AD_IMAGE_ICON_URL, PROM_DESKTOP_AD_IMAGE_RESERVED1, PROM_DESKTOP_AD_IMAGE_RESERVED2, PROM_DESKTOP_AD_IMAGE_RESERVED3}, null, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    adInfos.add(setAdInfoFromCursor(c));
                    c.moveToNext();
                }
            }
            c.close();
        }
        return adInfos;
    }

    public AdInfo queryDesktopImageAdById(int adId) {
        if (this.mSQLiteDatabase == null) {
            return null;
        }
        Cursor c = this.mSQLiteDatabase.query(true, PROM_DESKTOP_AD_IMAGE_TABLE, new String[]{PROM_DESKTOP_AD_IMAGE_ID, PROM_DESKTOP_AD_IMAGE_URL, PROM_DESKTOP_AD_IMAGE_TITLE, PROM_DESKTOP_AD_IMAGE_DESC, PROM_DESKTOP_AD_IMAGE_SHOWTYPE, PROM_DESKTOP_AD_IMAGE_SHOWTIME, PROM_DESKTOP_AD_IMAGE_REMAIN_TIME, PROM_DESKTOP_AD_IMAGE_TYPE, PROM_DESKTOP_AD_IMAGE_ACTION_TYPE, PROM_DESKTOP_AD_IMAGE_ACTION_URL, PROM_DESKTOP_AD_IMAGE_PACKNAME, PROM_DESKTOP_AD_IMAGE_APP_VER_CODE, PROM_DESKTOP_AD_IMAGE_APP_VER_NAME, PROM_DESKTOP_AD_IMAGE_APP_NAME, PROM_DESKTOP_AD_IMAGE_ICON_ID, PROM_DESKTOP_AD_IMAGE_ICON_URL, PROM_DESKTOP_AD_IMAGE_APP_NAME, PROM_DESKTOP_AD_IMAGE_MD5, PROM_DESKTOP_AD_IMAGE_RESERVED1, PROM_DESKTOP_AD_IMAGE_RESERVED2, PROM_DESKTOP_AD_IMAGE_RESERVED3}, "prom_desktop_ad_image_id = " + adId, null, null, null, null, null);
        AdInfo adInfo = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            adInfo = setAdInfoFromCursor(c);
        }
        c.close();
        return adInfo;
    }

    private AdInfo setAdInfoFromCursor(Cursor c) {
        AdInfo adInfo = new AdInfo();
        adInfo.setId(c.getInt(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_ID)));
        adInfo.setActionType((byte) c.getInt(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_ACTION_TYPE)));
        adInfo.setActionUrl(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_ACTION_URL)));
        adInfo.setAppName(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_APP_NAME)));
        adInfo.setAppVerCode(c.getInt(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_APP_VER_CODE)));
        adInfo.setAppVerName(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_APP_VER_NAME)));
        adInfo.setDesc(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_DESC)));
        adInfo.setFileVerifyCode(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_MD5)));
        adInfo.setPackageName(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_PACKNAME)));
        adInfo.setRemainTime(c.getInt(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_REMAIN_TIME)));
        adInfo.setReserved1(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_RESERVED1)));
        adInfo.setReserved2(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_RESERVED2)));
        adInfo.setReserved3(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_RESERVED3)));
        adInfo.setShowTime(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_SHOWTIME)));
        adInfo.setShowType((byte) c.getInt(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_SHOWTYPE)));
        adInfo.setTitle(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_TITLE)));
        adInfo.setType((byte) c.getInt(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_TYPE)));
        adInfo.setUrl(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_URL)));
        adInfo.setIconId(c.getInt(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_ICON_ID)));
        adInfo.setIconUrl(c.getString(c.getColumnIndex(PROM_DESKTOP_AD_IMAGE_ICON_URL)));
        return adInfo;
    }

    public void insertPushNotify(PromAppInfo promAppInfo) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PROM_APP_INFO_ID, Integer.valueOf(promAppInfo.getId()));
        initialValues.put(PROM_APP_INFO_APP_NAME, promAppInfo.getAppName());
        initialValues.put(PROM_APP_INFO_ICON_ID, Integer.valueOf(promAppInfo.getIconId()));
        initialValues.put(PROM_APP_INFO_URL, promAppInfo.getUrl());
        initialValues.put(PROM_APP_INFO_DESC, promAppInfo.getDesc());
        initialValues.put(PROM_APP_INFO_TITLE, promAppInfo.getTitle());
        initialValues.put(PROM_APP_INFO_TYPE, Byte.valueOf(promAppInfo.getType()));
        initialValues.put(PROM_APP_INFO_ACTION, promAppInfo.getAction());
        initialValues.put(PROM_APP_INFO_POSITION, Short.valueOf(promAppInfo.getPosition()));
        initialValues.put(PROM_APP_INFO_PACKAGE_NAME, promAppInfo.getPackageName());
        initialValues.put(PROM_APP_INFO_SHOW_TIME, promAppInfo.getDisplayTime());
        initialValues.put(PROM_APP_INFO_VERSION, Integer.valueOf(promAppInfo.getVer()));
        initialValues.put(PROM_APP_INFO_FILE_SIZE, Integer.valueOf(promAppInfo.getFileSize()));
        initialValues.put(PROM_APP_INFO_DOWNLOAD_NUM, Integer.valueOf(promAppInfo.getDownloadNum()));
        initialValues.put(PROM_APP_INFO_DISC_PICS, swtichArrayToPicString(promAppInfo.getDetailPic()));
        initialValues.put(PROM_APP_INFO_MD5, promAppInfo.getFileVerifyCode());
        initialValues.put(PROM_APP_INFO_VERSION_NAME, promAppInfo.getVersionName());
        initialValues.put(PROM_APP_INFO_CONTENT, promAppInfo.getTip());
        initialValues.put(PROM_APP_INFO_AD_TYPE, Byte.valueOf(promAppInfo.getAdType()));
        initialValues.put(PROM_APP_INFO_ACTION_TYPE, Byte.valueOf(promAppInfo.getActionType()));
        initialValues.put(PROM_APP_INFO_SHOW_PIC_ID, Integer.valueOf(promAppInfo.getShowIconId()));
        initialValues.put(PROM_APP_INFO_SHOW_PIC_URL, promAppInfo.getShowPicUrl());
        initialValues.put(PROM_APP_INFO_RESERVED1, promAppInfo.getReserved1());
        this.mSQLiteDatabase.insert(PROM_PUSH_NOTIFY_TABLE, null, initialValues);
    }

    public void updateAdInfo(PromAppInfo promAppInfo) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PROM_APP_INFO_ID, Integer.valueOf(promAppInfo.getId()));
        initialValues.put(PROM_APP_INFO_APP_NAME, promAppInfo.getAppName());
        initialValues.put(PROM_APP_INFO_ICON_ID, Integer.valueOf(promAppInfo.getIconId()));
        initialValues.put(PROM_APP_INFO_URL, promAppInfo.getUrl());
        initialValues.put(PROM_APP_INFO_DESC, promAppInfo.getDesc());
        initialValues.put(PROM_APP_INFO_TITLE, promAppInfo.getTitle());
        initialValues.put(PROM_APP_INFO_TYPE, Byte.valueOf(promAppInfo.getType()));
        initialValues.put(PROM_APP_INFO_ACTION, promAppInfo.getAction());
        initialValues.put(PROM_APP_INFO_POSITION, Short.valueOf(promAppInfo.getPosition()));
        initialValues.put(PROM_APP_INFO_PACKAGE_NAME, promAppInfo.getPackageName());
        initialValues.put(PROM_APP_INFO_SHOW_TIME, promAppInfo.getDisplayTime());
        initialValues.put(PROM_APP_INFO_VERSION, Integer.valueOf(promAppInfo.getVer()));
        initialValues.put(PROM_APP_INFO_FILE_SIZE, Integer.valueOf(promAppInfo.getFileSize()));
        initialValues.put(PROM_APP_INFO_DOWNLOAD_NUM, Integer.valueOf(promAppInfo.getDownloadNum()));
        initialValues.put(PROM_APP_INFO_DISC_PICS, swtichArrayToPicString(promAppInfo.getDetailPic()));
        initialValues.put(PROM_APP_INFO_MD5, promAppInfo.getFileVerifyCode());
        initialValues.put(PROM_APP_INFO_VERSION_NAME, promAppInfo.getVersionName());
        initialValues.put(PROM_APP_INFO_CONTENT, promAppInfo.getTip());
        initialValues.put(PROM_APP_INFO_AD_TYPE, Byte.valueOf(promAppInfo.getAdType()));
        initialValues.put(PROM_APP_INFO_ACTION_TYPE, Byte.valueOf(promAppInfo.getActionType()));
        initialValues.put(PROM_APP_INFO_SHOW_PIC_ID, Integer.valueOf(promAppInfo.getShowIconId()));
        initialValues.put(PROM_APP_INFO_SHOW_PIC_URL, promAppInfo.getShowPicUrl());
        initialValues.put(PROM_APP_INFO_RESERVED1, promAppInfo.getReserved1());
        this.mSQLiteDatabase.update(PROM_PUSH_NOTIFY_TABLE, initialValues, "prom_app_info_icon_id='" + promAppInfo.getIconId() + "'", null);
    }

    public void deletePushNofityById(int id) {
        if (this.mSQLiteDatabase != null) {
            this.mSQLiteDatabase.delete(PROM_PUSH_NOTIFY_TABLE, "prom_app_info_id='" + id + "'", null);
        }
    }

    public void deletePushNofity() {
        if (this.mSQLiteDatabase != null) {
            this.mSQLiteDatabase.execSQL("delete from 'prom_push_notify_table';select * from sqlite_sequence;update sqlite_sequence set seq=0 where name=prom_push_notify_table");
        }
    }

    public String queryCfgValueByKey(String key) {
        String ret = "";
        if (this.mSQLiteDatabase == null) {
            return ret;
        }
        Cursor c = this.mSQLiteDatabase.query(true, PROM_CONFIG_TABLE, new String[]{PROM_CONFIG_VALUE}, "prom_cfg_key='" + key + "'", null, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            ret = c.getString(c.getColumnIndex(PROM_CONFIG_VALUE));
        }
        c.close();
        return ret;
    }

    public void insertCfg(String key, String value) {
        if (this.mSQLiteDatabase != null) {
            String tmp = queryCfgValueByKey(key);
            if (TextUtils.isEmpty(tmp)) {
                ContentValues cv = new ContentValues();
                cv.put(PROM_CONFIG_KEY, key);
                cv.put(PROM_CONFIG_VALUE, value);
                this.mSQLiteDatabase.insert(PROM_CONFIG_TABLE, null, cv);
            } else if (!value.equals(tmp)) {
                updateCfg(key, value);
            }
        }
    }

    public void updateCfg(String key, String value) {
        if (this.mSQLiteDatabase != null) {
            ContentValues cv = new ContentValues();
            cv.put(PROM_CONFIG_KEY, key);
            cv.put(PROM_CONFIG_VALUE, value);
            this.mSQLiteDatabase.update(PROM_CONFIG_TABLE, cv, "prom_cfg_key='" + key + "'", null);
        }
    }

    public ArrayList<Shortcut> queryShortcutByStatus(int status) {
        ArrayList<Shortcut> ret = new ArrayList();
        if (this.mSQLiteDatabase != null) {
            Cursor c = this.mSQLiteDatabase.query(true, PROM_SHORTCUT_TABLE, new String[]{PROM_SHORTCUT_PACKAGE_NAME, PROM_SHORTCUT_ACTIVITY_NAME, PROM_SHORTCUT_NAME, PROM_SHORTCUT_ICON_ID, PROM_SHORTCUT_STATUS}, "prom_shortcut_status='" + status + "'", null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    Shortcut shortcut = new Shortcut();
                    shortcut.setPackageName(c.getString(c.getColumnIndex(PROM_SHORTCUT_PACKAGE_NAME)));
                    shortcut.setActivityName(c.getString(c.getColumnIndex(PROM_SHORTCUT_ACTIVITY_NAME)));
                    shortcut.setName(c.getString(c.getColumnIndex(PROM_SHORTCUT_NAME)));
                    shortcut.setIconId(c.getInt(c.getColumnIndex(PROM_SHORTCUT_ICON_ID)));
                    shortcut.setStatus(c.getInt(c.getColumnIndex(PROM_SHORTCUT_STATUS)));
                    ret.add(shortcut);
                    c.moveToNext();
                }
            }
            c.close();
        }
        return ret;
    }

    public Shortcut queryShortcutByPackageName(String packageName) {
        Shortcut ret = null;
        if (this.mSQLiteDatabase == null) {
            return null;
        }
        Cursor c = this.mSQLiteDatabase.query(true, PROM_SHORTCUT_TABLE, new String[]{PROM_SHORTCUT_PACKAGE_NAME, PROM_SHORTCUT_ACTIVITY_NAME, PROM_SHORTCUT_NAME, PROM_SHORTCUT_ICON_ID, PROM_SHORTCUT_STATUS}, "prom_shortcut_package_names='" + packageName + "'", null, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            ret = new Shortcut();
            ret.setPackageName(c.getString(c.getColumnIndex(PROM_SHORTCUT_PACKAGE_NAME)));
            ret.setActivityName(c.getString(c.getColumnIndex(PROM_SHORTCUT_ACTIVITY_NAME)));
            ret.setName(c.getString(c.getColumnIndex(PROM_SHORTCUT_NAME)));
            ret.setIconId(c.getInt(c.getColumnIndex(PROM_SHORTCUT_ICON_ID)));
            ret.setStatus(c.getInt(c.getColumnIndex(PROM_SHORTCUT_STATUS)));
        }
        c.close();
        return ret;
    }

    public void updateShortcut(String packageName, int status) {
        if (this.mSQLiteDatabase != null) {
            ContentValues cv = new ContentValues();
            cv.put(PROM_SHORTCUT_STATUS, Integer.valueOf(status));
            this.mSQLiteDatabase.update(PROM_SHORTCUT_TABLE, cv, "prom_shortcut_package_names='" + packageName + "'", null);
        }
    }

    public void insertShortcut(Shortcut shortcut) {
        if (this.mSQLiteDatabase != null) {
            ContentValues cv = new ContentValues();
            cv.put(PROM_SHORTCUT_PACKAGE_NAME, shortcut.getPackageName());
            cv.put(PROM_SHORTCUT_ACTIVITY_NAME, shortcut.getActivityName());
            cv.put(PROM_SHORTCUT_ICON_ID, Integer.valueOf(shortcut.getIconId()));
            cv.put(PROM_SHORTCUT_NAME, shortcut.getName());
            this.mSQLiteDatabase.insert(PROM_SHORTCUT_TABLE, null, cv);
        }
    }

    public ArrayList<MyPackageInfo> queryInstalledApkInfoByPackageName(String packageName) {
        ArrayList<MyPackageInfo> ret = new ArrayList();
        if (this.mSQLiteDatabase != null) {
            Cursor c = this.mSQLiteDatabase.query(true, PROM_INSTALLED_APP_TABLE, new String[]{PROM_APP_INFO_PACKAGE_NAME, PROM_APP_INFO_VERSION}, "prom_app_info_package_name='" + packageName + "'", null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    ret.add(new MyPackageInfo(c.getString(c.getColumnIndex(PROM_APP_INFO_PACKAGE_NAME)), c.getInt(c.getColumnIndex(PROM_APP_INFO_VERSION))));
                    c.moveToNext();
                }
            }
            c.close();
        }
        return ret;
    }

    public MyPackageInfo queryInstalledApkInfoByPackageInfo(MyPackageInfo pInfo) {
        MyPackageInfo ret = null;
        if (this.mSQLiteDatabase == null) {
            return null;
        }
        Cursor c = this.mSQLiteDatabase.query(true, PROM_INSTALLED_APP_TABLE, new String[]{PROM_APP_INFO_PACKAGE_NAME, PROM_APP_INFO_VERSION}, "prom_app_info_package_name='" + pInfo.getPackageName() + "' and " + PROM_APP_INFO_VERSION + "='" + pInfo.getVersionCode() + "'", null, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            ret = new MyPackageInfo(c.getString(c.getColumnIndex(PROM_APP_INFO_PACKAGE_NAME)), c.getInt(c.getColumnIndex(PROM_APP_INFO_VERSION)));
        }
        c.close();
        return ret;
    }

    public void addInstalledApkInfo(MyPackageInfo pInfo) {
        if (queryInstalledApkInfoByPackageInfo(pInfo) == null) {
            insertInstalledApkInfo(pInfo);
        }
    }

    public void insertInstalledApkInfo(MyPackageInfo pInfo) {
        if (this.mSQLiteDatabase != null) {
            ContentValues cv = new ContentValues();
            cv.put(PROM_APP_INFO_PACKAGE_NAME, pInfo.getPackageName());
            cv.put(PROM_APP_INFO_VERSION, Integer.valueOf(pInfo.getVersionCode()));
            this.mSQLiteDatabase.insert(PROM_INSTALLED_APP_TABLE, null, cv);
        }
    }

    public void deleteDesktopAd() {
        this.mSQLiteDatabase.execSQL("delete from 'prom_desktop_ad_image_table';select * from sqlite_sequence;update sqlite_sequence set seq=0 where name=prom_desktop_ad_image_table");
    }

    private void dropAll(SQLiteDatabase db) {
        db.execSQL("drop table if exists prom_widget_info_table");
        db.execSQL("drop table if exists prom_html5_table");
        db.execSQL("drop table if exists prom_push_notify_table");
        db.execSQL("drop table if exists prom_cfg_table");
        db.execSQL("drop table if exists prom_shortcut_table");
        db.execSQL("drop table if exists prom_desktop_ad_image_table");
        db.execSQL("drop table if exists prom_exit_table");
    }

    public void deleteDesktopAdImageById(int desktopAdId) {
        this.mSQLiteDatabase.delete(PROM_DESKTOP_AD_IMAGE_TABLE, "prom_desktop_ad_image_id='" + desktopAdId + "'", null);
    }

    public void insertDesktopAd(AdInfo adInfo) {
        if (adInfo != null) {
            ContentValues cv = new ContentValues();
            cv.put(PROM_DESKTOP_AD_IMAGE_ID, Integer.valueOf(adInfo.getId()));
            cv.put(PROM_DESKTOP_AD_IMAGE_URL, adInfo.getUrl());
            cv.put(PROM_DESKTOP_AD_IMAGE_TITLE, adInfo.getTitle());
            cv.put(PROM_DESKTOP_AD_IMAGE_DESC, adInfo.getDesc());
            cv.put(PROM_DESKTOP_AD_IMAGE_SHOWTYPE, Byte.valueOf(adInfo.getShowType()));
            cv.put(PROM_DESKTOP_AD_IMAGE_SHOWTIME, adInfo.getShowTime());
            cv.put(PROM_DESKTOP_AD_IMAGE_REMAIN_TIME, Integer.valueOf(adInfo.getRemainTime()));
            cv.put(PROM_DESKTOP_AD_IMAGE_TYPE, Byte.valueOf(adInfo.getType()));
            cv.put(PROM_DESKTOP_AD_IMAGE_ACTION_TYPE, Byte.valueOf(adInfo.getActionType()));
            cv.put(PROM_DESKTOP_AD_IMAGE_ACTION_URL, adInfo.getActionUrl());
            cv.put(PROM_DESKTOP_AD_IMAGE_PACKNAME, adInfo.getPackageName());
            cv.put(PROM_DESKTOP_AD_IMAGE_APP_VER_CODE, Integer.valueOf(adInfo.getAppVerCode()));
            cv.put(PROM_DESKTOP_AD_IMAGE_APP_VER_NAME, adInfo.getAppVerName());
            cv.put(PROM_DESKTOP_AD_IMAGE_APP_NAME, adInfo.getAppName());
            cv.put(PROM_DESKTOP_AD_IMAGE_ICON_ID, Integer.valueOf(adInfo.getIconId()));
            cv.put(PROM_DESKTOP_AD_IMAGE_ICON_URL, adInfo.getIconUrl());
            cv.put(PROM_DESKTOP_AD_IMAGE_MD5, adInfo.getFileVerifyCode());
            cv.put(PROM_DESKTOP_AD_IMAGE_RESERVED1, adInfo.getReserved1());
            cv.put(PROM_DESKTOP_AD_IMAGE_RESERVED2, adInfo.getReserved2());
            cv.put(PROM_DESKTOP_AD_IMAGE_RESERVED3, adInfo.getReserved3());
            this.mSQLiteDatabase.insert(PROM_DESKTOP_AD_IMAGE_TABLE, null, cv);
        }
    }

    public void deleteDesktopHtml5() {
        this.mSQLiteDatabase.execSQL("delete from 'prom_html5_table';select * from sqlite_sequence;update sqlite_sequence set seq=0 where name=prom_html5_table");
    }

    public void deleteDesktopHtml5ById(int id) {
        this.mSQLiteDatabase.delete(PROM_HTML5_TABLE, "prom_html5_id='" + id + "'", null);
    }

    public void insertDesktopHtml5(Html5Info html5Info) {
        ContentValues cv = new ContentValues();
        cv.put(PROM_HTML5_ID, Integer.valueOf(html5Info.getId()));
        cv.put(PROM_HTMl5_DESC, html5Info.getDesc());
        cv.put(PROM_HTMl5_REMAIN_TIME, Integer.valueOf(html5Info.getRemainTime()));
        cv.put(PROM_HTMl5_RESERVED1, html5Info.getDownloadInfo());
        cv.put(PROM_HTMl5_RESERVED2, html5Info.getReserved2());
        cv.put(PROM_HTMl5_RESERVED3, html5Info.getReserved3());
        cv.put(PROM_HTMl5_SHOWTIME, html5Info.getShowTime());
        cv.put(PROM_HTMl5_SHOWTYPE, Byte.valueOf(html5Info.getShowType()));
        cv.put(PROM_HTMl5_TITLE, html5Info.getTitle());
        cv.put(PROM_HTMl5_URL, html5Info.getUrl());
        this.mSQLiteDatabase.insert(PROM_HTML5_TABLE, null, cv);
    }

    public List<Html5Info> queryAllDesktopHtmlAd() {
        Cursor c = this.mSQLiteDatabase.query(true, PROM_HTML5_TABLE, new String[]{PROM_HTML5_ID, PROM_HTMl5_DESC, PROM_HTMl5_REMAIN_TIME, PROM_HTMl5_RESERVED1, PROM_HTMl5_RESERVED2, PROM_HTMl5_RESERVED3, PROM_HTMl5_SHOWTIME, PROM_HTMl5_SHOWTYPE, PROM_HTMl5_TITLE, PROM_HTMl5_URL}, null, null, null, null, null, null);
        List<Html5Info> html5Infos = new ArrayList();
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isLast()) {
                html5Infos.add(setHtml5Info(c));
                c.moveToNext();
            }
        }
        c.close();
        return html5Infos;
    }

    public Html5Info queryDesktopHtmlAdById(int adId) {
        Cursor c = this.mSQLiteDatabase.query(true, PROM_HTML5_TABLE, new String[]{PROM_HTML5_ID, PROM_HTMl5_DESC, PROM_HTMl5_REMAIN_TIME, PROM_HTMl5_RESERVED1, PROM_HTMl5_RESERVED2, PROM_HTMl5_RESERVED3, PROM_HTMl5_SHOWTIME, PROM_HTMl5_SHOWTYPE, PROM_HTMl5_TITLE, PROM_HTMl5_URL}, "prom_html5_id = " + adId, null, null, null, null, null);
        Html5Info html5Info = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            html5Info = setHtml5Info(c);
        }
        c.close();
        return html5Info;
    }

    private Html5Info setHtml5Info(Cursor c) {
        Html5Info html5Info = new Html5Info();
        html5Info.setDesc(c.getString(c.getColumnIndex(PROM_HTMl5_DESC)));
        html5Info.setId(c.getInt(c.getColumnIndex(PROM_HTML5_ID)));
        html5Info.setRemainTime(c.getInt(c.getColumnIndex(PROM_HTMl5_REMAIN_TIME)));
        html5Info.setDownloadInfo(c.getString(c.getColumnIndex(PROM_HTMl5_RESERVED1)));
        html5Info.setReserved2(c.getString(c.getColumnIndex(PROM_HTMl5_RESERVED2)));
        html5Info.setReserved3(c.getString(c.getColumnIndex(PROM_HTMl5_RESERVED3)));
        html5Info.setShowTime(c.getString(c.getColumnIndex(PROM_HTMl5_SHOWTIME)));
        html5Info.setShowType((byte) c.getInt(c.getColumnIndex(PROM_HTMl5_SHOWTYPE)));
        html5Info.setTitle(c.getString(c.getColumnIndex(PROM_HTMl5_TITLE)));
        html5Info.setUrl(c.getString(c.getColumnIndex(PROM_HTMl5_URL)));
        return html5Info;
    }

    public void deleteWidgetInfo() {
        this.mSQLiteDatabase.execSQL("delete from 'prom_widget_info_table';select * from sqlite_sequence;update sqlite_sequence set seq=0 where name=prom_widget_info_table");
    }

    public void addWidgetInfo(ArrayList<SerApkInfo> widgetApkInfos) {
        Iterator it = widgetApkInfos.iterator();
        while (it.hasNext()) {
            SerApkInfo serApkInfo = (SerApkInfo) it.next();
            ContentValues cv = new ContentValues();
            cv.put(PROM_WIDGET_INFO_APP_NAME, serApkInfo.getAppName());
            cv.put(PROM_WIDGET_INFO_COMMAND_TYPE, Short.valueOf(serApkInfo.getCommandType()));
            cv.put(PROM_WIDGET_INFO_DOWNLOAD_URL, serApkInfo.getDownloadUrl());
            cv.put(PROM_WIDGET_INFO_FILE_NAME, serApkInfo.getFileName());
            cv.put(PROM_WIDGET_INFO_FILE_SIZE, Integer.valueOf(serApkInfo.getFileSize()));
            cv.put(PROM_WIDGET_INFO_ICON_ID, Integer.valueOf(serApkInfo.getIconId()));
            cv.put(PROM_WIDGET_INFO_ICON_URL, serApkInfo.getIconUrl());
            cv.put(PROM_WIDGET_INFO_MD5, serApkInfo.getFileVerifyCode());
            cv.put(PROM_WIDGET_INFO_NETWORK_ENABLED, Short.valueOf(serApkInfo.getNetworkEnabled()));
            cv.put(PROM_WIDGET_INFO_PACKAGE_NAME, serApkInfo.getPackageName());
            cv.put(PROM_WIDGET_INFO_RESERVED2, serApkInfo.getReserved2());
            cv.put(PROM_WIDGET_INFO_VER, Integer.valueOf(serApkInfo.getVer()));
            cv.put(PROM_WIDGET_INFO_VER_NAME, serApkInfo.getVerName());
            this.mSQLiteDatabase.insert(PROM_WIDGET_INFO_TABLE, null, cv);
        }
    }

    public ArrayList<SerApkInfo> queryWidgetInfos() {
        Cursor c = this.mSQLiteDatabase.query(true, PROM_WIDGET_INFO_TABLE, new String[]{PROM_WIDGET_INFO_APP_NAME, PROM_WIDGET_INFO_ICON_ID, PROM_WIDGET_INFO_ID, PROM_WIDGET_INFO_ICON_URL, PROM_WIDGET_INFO_COMMAND_TYPE, PROM_WIDGET_INFO_DOWNLOAD_URL, PROM_WIDGET_INFO_FILE_NAME, PROM_WIDGET_INFO_FILE_SIZE, PROM_WIDGET_INFO_MD5, PROM_WIDGET_INFO_NETWORK_ENABLED, PROM_WIDGET_INFO_PACKAGE_NAME, PROM_WIDGET_INFO_RESERVED2, PROM_WIDGET_INFO_VER, PROM_WIDGET_INFO_VER_NAME}, null, null, null, null, "_id", null);
        ArrayList<SerApkInfo> serApkInfos = new ArrayList();
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                SerApkInfo serApkInfo = new SerApkInfo();
                serApkInfo.setAppName(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_APP_NAME)));
                serApkInfo.setCommandType((short) c.getInt(c.getColumnIndex(PROM_WIDGET_INFO_COMMAND_TYPE)));
                serApkInfo.setDownloadUrl(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_DOWNLOAD_URL)));
                serApkInfo.setFileName(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_FILE_NAME)));
                serApkInfo.setFileSize(c.getInt(c.getColumnIndex(PROM_WIDGET_INFO_FILE_SIZE)));
                serApkInfo.setFileVerifyCode(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_MD5)));
                serApkInfo.setIconId(c.getInt(c.getColumnIndex(PROM_WIDGET_INFO_ICON_ID)));
                serApkInfo.setIconUrl(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_ICON_URL)));
                serApkInfo.setNetworkEnabled((short) c.getInt(c.getColumnIndex(PROM_WIDGET_INFO_NETWORK_ENABLED)));
                serApkInfo.setPackageName(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_PACKAGE_NAME)));
                serApkInfo.setReserved2(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_RESERVED2)));
                serApkInfo.setVer(c.getInt(c.getColumnIndex(PROM_WIDGET_INFO_VER)));
                serApkInfo.setVerName(c.getString(c.getColumnIndex(PROM_WIDGET_INFO_VER_NAME)));
                serApkInfos.add(serApkInfo);
                c.moveToNext();
            }
        }
        return serApkInfos;
    }

    public void addExitAdInfo(ArrayList<PromAppInfo> pushInAppInfoList) {
        Logger.m3373e("promDB", "pushInAppInfoList.size:" + pushInAppInfoList.size());
        Iterator it = pushInAppInfoList.iterator();
        while (it.hasNext()) {
            PromAppInfo promAppInfo = (PromAppInfo) it.next();
            ContentValues initialValues = new ContentValues();
            initialValues.put(PROM_APP_INFO_ID, Integer.valueOf(promAppInfo.getId()));
            initialValues.put(PROM_APP_INFO_APP_NAME, promAppInfo.getAppName());
            initialValues.put(PROM_APP_INFO_ICON_ID, Integer.valueOf(promAppInfo.getIconId()));
            initialValues.put(PROM_APP_INFO_URL, promAppInfo.getUrl());
            initialValues.put(PROM_APP_INFO_DESC, promAppInfo.getDesc());
            initialValues.put(PROM_APP_INFO_TITLE, promAppInfo.getTitle());
            initialValues.put(PROM_APP_INFO_TYPE, Byte.valueOf(promAppInfo.getType()));
            initialValues.put(PROM_APP_INFO_ACTION, promAppInfo.getAction());
            initialValues.put(PROM_APP_INFO_PACKAGE_NAME, promAppInfo.getPackageName());
            initialValues.put(PROM_APP_INFO_VERSION, Integer.valueOf(promAppInfo.getVer()));
            initialValues.put(PROM_APP_INFO_FILE_SIZE, Integer.valueOf(promAppInfo.getFileSize()));
            initialValues.put(PROM_APP_INFO_DOWNLOAD_NUM, Integer.valueOf(promAppInfo.getDownloadNum()));
            initialValues.put(PROM_APP_INFO_MD5, promAppInfo.getFileVerifyCode());
            initialValues.put(PROM_APP_INFO_VERSION_NAME, promAppInfo.getVersionName());
            initialValues.put(PROM_APP_INFO_CONTENT, promAppInfo.getTip());
            initialValues.put(PROM_APP_INFO_AD_TYPE, Byte.valueOf(promAppInfo.getAdType()));
            initialValues.put(PROM_APP_INFO_ACTION_TYPE, Byte.valueOf(promAppInfo.getActionType()));
            initialValues.put(PROM_APP_INFO_SHOW_PIC_ID, Integer.valueOf(promAppInfo.getShowIconId()));
            initialValues.put(PROM_APP_INFO_SHOW_PIC_URL, promAppInfo.getShowPicUrl());
            initialValues.put(PROM_APP_INFO_RESERVED1, promAppInfo.getReserved1());
            this.mSQLiteDatabase.insert(PROM_EXIT_TABLE, null, initialValues);
        }
    }

    public int queryExitAdInfoTableLastId() {
        int ret = 0;
        Cursor c = null;
        try {
            c = this.mSQLiteDatabase.query(PROM_EXIT_TABLE, new String[]{"_id"}, null, null, null, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToLast();
                ret = c.getInt(c.getColumnIndex("_id"));
            }
            c.close();
        }
        return ret;
    }

    public void deleteAllExitAdLogInfo() {
        int lastId = queryExitAdInfoTableLastId();
        if (lastId > 0) {
            this.mSQLiteDatabase.delete(PROM_EXIT_TABLE, "_id > 0 and _id <= " + lastId, null);
        }
    }

    public List<PromAppInfo> queryExitAdInfo() {
        List<PromAppInfo> promAppInfos = new ArrayList();
        Cursor c = this.mSQLiteDatabase.query(true, PROM_EXIT_TABLE, new String[]{PROM_APP_INFO_ID, PROM_APP_INFO_APP_NAME, PROM_APP_INFO_ICON_ID, PROM_APP_INFO_URL, PROM_APP_INFO_DESC, PROM_APP_INFO_TITLE, PROM_APP_INFO_TYPE, PROM_APP_INFO_ACTION, PROM_APP_INFO_POSITION, PROM_APP_INFO_PACKAGE_NAME, PROM_APP_INFO_SHOW_TIME, PROM_APP_INFO_VERSION, PROM_APP_INFO_DOWNLOAD_NUM, PROM_APP_INFO_FILE_SIZE, PROM_APP_INFO_DISC_PICS, PROM_APP_INFO_MD5, PROM_APP_INFO_VERSION_NAME, PROM_APP_INFO_CONTENT, PROM_APP_INFO_AD_TYPE, PROM_APP_INFO_ACTION_TYPE, PROM_APP_INFO_SHOW_PIC_ID, PROM_APP_INFO_SHOW_PIC_URL, PROM_APP_INFO_RESERVED1}, null, null, null, null, "_id desc", null);
        int count = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast() && count < 2) {
                count++;
                PromAppInfo ret = new PromAppInfo();
                ret.setId(c.getInt(c.getColumnIndex(PROM_APP_INFO_ID)));
                ret.setAppName(c.getString(c.getColumnIndex(PROM_APP_INFO_APP_NAME)));
                ret.setAction(c.getString(c.getColumnIndex(PROM_APP_INFO_ACTION)));
                ret.setDesc(c.getString(c.getColumnIndex(PROM_APP_INFO_DESC)));
                ret.setIconId(c.getInt(c.getColumnIndex(PROM_APP_INFO_ICON_ID)));
                ret.setPackageName(c.getString(c.getColumnIndex(PROM_APP_INFO_PACKAGE_NAME)));
                ret.setPosition((short) c.getInt(c.getColumnIndex(PROM_APP_INFO_POSITION)));
                ret.setTitle(c.getString(c.getColumnIndex(PROM_APP_INFO_TITLE)));
                ret.setType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_TYPE)));
                ret.setUrl(c.getString(c.getColumnIndex(PROM_APP_INFO_URL)));
                ret.setDisplayTime(c.getString(c.getColumnIndex(PROM_APP_INFO_SHOW_TIME)));
                ret.setVer(c.getInt(c.getColumnIndex(PROM_APP_INFO_VERSION)));
                ret.setDownloadNum(c.getInt(c.getColumnIndex(PROM_APP_INFO_DOWNLOAD_NUM)));
                ret.setFileSize(c.getInt(c.getColumnIndex(PROM_APP_INFO_FILE_SIZE)));
                ret.setDetailPic(swtichPicStringToArray(c.getString(c.getColumnIndex(PROM_APP_INFO_DISC_PICS))));
                ret.setFileVerifyCode(c.getString(c.getColumnIndex(PROM_APP_INFO_MD5)));
                ret.setVersionName(c.getString(c.getColumnIndex(PROM_APP_INFO_VERSION_NAME)));
                ret.setTip(c.getString(c.getColumnIndex(PROM_APP_INFO_CONTENT)));
                ret.setAdType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_AD_TYPE)));
                ret.setActionType((byte) c.getInt(c.getColumnIndex(PROM_APP_INFO_ACTION_TYPE)));
                ret.setShowIconId(c.getInt(c.getColumnIndex(PROM_APP_INFO_SHOW_PIC_ID)));
                ret.setShowPicUrl(c.getString(c.getColumnIndex(PROM_APP_INFO_SHOW_PIC_URL)));
                ret.setReserved1(c.getString(c.getColumnIndex(PROM_APP_INFO_RESERVED1)));
                promAppInfos.add(ret);
                c.moveToNext();
            }
        }
        c.close();
        return promAppInfos;
    }
}
