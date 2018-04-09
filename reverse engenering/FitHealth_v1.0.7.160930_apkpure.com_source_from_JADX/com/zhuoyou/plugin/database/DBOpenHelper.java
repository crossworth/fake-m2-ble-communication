package com.zhuoyou.plugin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context) {
        super(context, DataBaseContants.DB_NAME, null, 12);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS data ( _id LONG primary key , date TEXT , time_duration TEXT , time_start TEXT , time_end TEXT , steps INTEGER , kilometer INTEGER ,calories INTEGER ,weight TEXT ,bmi TEXT ,img_uri TEXT ,img_explain TEXT ,sports_type INTEGER DEFAULT (0) NOT NULL,type INTEGER DEFAULT (-1) NOT NULL,img_cloud TEXT ,complete INTEGER DEFAULT (0) NOT NULL ,sync INTEGER DEFAULT (0) NOT NULL,statistics INTEGER ,heart_rate_time TEXT ,heart_rate_count TEXT ,data_from TEXT );");
        db.execSQL(" CREATE TABLE IF NOT EXISTS cloud ( _id integer PRIMARY KEY AUTOINCREMENT , delete_value LONG );");
        db.execSQL(" CREATE TABLE IF NOT EXISTS point_message2 ( _id LONG primary key , latitude double , longtitude double , address char , accuracy float , provider char , location_time long ,location_systime long ,speed float ,altitude double ,point_state int ,gps_number integer DEFAULT(0), sync_state integer DEFAULT(0) NOT NULL);");
        db.execSQL(" CREATE TABLE IF NOT EXISTS point_temp ( _id integer PRIMARY KEY AUTOINCREMENT , latitude double , longtitude double , address char , accuracy float , provider char , location_time long ,location_systime long ,speed float ,altitude double ,point_state int ,gps_number integer DEFAULT(0), sync_state integer DEFAULT(0) NOT NULL);");
        db.execSQL(" CREATE TABLE IF NOT EXISTS operation_time2 ( _id LONG primary key , operation_time long,operation_systime long,operation_state integer ,sync_state integer DEFAULT(0) NOT NULL);");
        db.execSQL(" CREATE TABLE IF NOT EXISTS gps_sport2 ( _id LONG primary key , starttime long , endtime long , sys_starttime long , sys_endtime long , startaddress TEXT , endaddress TEXT , durationtime long , avespeed float , total_distance float , steps integer ,calorie float,heart_rate_count TEXT ,sync_state integer DEFAULT(0) NOT NULL );");
        db.execSQL(" CREATE TABLE IF NOT EXISTS gps_sync ( _id integer PRIMARY KEY AUTOINCREMENT , table_name TEXT,delete_id TEXT);");
        db.execSQL(" CREATE TABLE IF NOT EXISTS action_msg_info ( _id integer PRIMARY KEY AUTOINCREMENT ,activity_id integer , msg_id integer , content TEXT , msg_type integer DEFAULT(0) NOT NULL ,msg_time TEXT , state INTEGER DEFAULT(0) NOT NULL);");
        db.execSQL(" CREATE TABLE IF NOT EXISTS sleep ( _id LONG primary key , type integer , start_time long , end_time long , turn_data text ) ;");
        db.execSQL(" CREATE TABLE IF NOT EXISTS sleep2 ( _id integer PRIMARY KEY AUTOINCREMENT , date TEXT , sleep_details TEXT ) ;");
    }

    private void initSleepDBdata(SQLiteDatabase db) {
        db.execSQL("insert into sleep VALUES ( '1' , '0' ,'20160320203035' , '20160321083035' , '6|5|3|2|50|0|0|60|0|0|0|0|0|0|0|0|0|0|0|2|3|4|5|' );");
        db.execSQL("insert into sleep VALUES ( '2' , '0' ,'20160321203035' , '20160322083035' , '60|5|3|60|0|0|5|0|0|0|0|0|0|0|0|0|0|0|0|2|3|4|5|' );");
        db.execSQL("insert into sleep VALUES ( '3' , '0' ,'20151205203035' , '20151206083035' , '3|5|3|2|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|2|3|4|5|' );");
        db.execSQL("insert into sleep VALUES ( '4' , '0' ,'20150403203035' , '20150404083035' , '3|5|3|2|0|0|0|0|0|0|0|0|0|0|0|0|0|0|2|3|4|5|' );");
        db.execSQL("insert into sleep VALUES ( '5' , '0' ,'20150404203035' , '20150405083035' , '3|5|3|2|0|0|0|0|0|0|0|0|0|0|0|0|0|0|2|3|4|5|' );");
        db.execSQL("insert into sleep VALUES ( '6' , '0' ,'20150405203035' , '20150406083035' , '3|5|3|2|0|0|0|0|0|0|0|0|0|0|0|0|0|0|2|3|4|5|' );");
        db.execSQL("insert into sleep VALUES ( '7' , '0' ,'20150406203035' , '20150407083035' , '3|5|3|2|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|5|5|5|5|' );");
        db.execSQL("insert into sleep VALUES ( '8' , '0' ,'20150518113035' , '20150518143035' , '60|5|3|2|60|0|0|0|0|0|1|5|5|5|' );");
        db.execSQL("insert into sleep VALUES ( '9' , '0' ,'20150519113035' , '20150519143035' , '3|5|3|2|0|0|0|0|0|0|5|5|5|5|' );");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS data");
        db.execSQL("DROP TABLE IF EXISTS  action_msg");
        db.execSQL("DROP TABLE IF EXISTS  point_message");
        db.execSQL("DROP TABLE IF EXISTS  operation_time");
        db.execSQL("DROP TABLE IF EXISTS  gps_sport");
        db.execSQL("DROP TABLE IF EXISTS  heart_rate");
        db.execSQL("DROP TABLE IF EXISTS gps_sport2");
        onCreate(db);
    }
}
