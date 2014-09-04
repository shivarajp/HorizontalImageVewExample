package com.example.horizontalimageview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseStore  extends SQLiteOpenHelper {
    private static  final  String LOGTAG="EXPLORECA";
    private static  final  String DATABASE_NAME="quiz1.db";
    private static  final  int DATABASE_VERSION=1;
    private static  final  String TABLE_IMAGES="auto";
    private static  final  String imagesPath=" imagesPath";
    private static  final  String deltaTime="deltaTime";
    private static  final  String shouldCopy="shouldCopy";
    
    private static  final  String TABLE_CREATE=
            "CREATE TABLE "+TABLE_IMAGES +" ("+
            		deltaTime+"INTEGER, "+
            		shouldCopy+"BOOLEAN, "+
            		imagesPath +" TEXT )";
    
    public DatabaseStore(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
        Log.i(LOGTAG,"Table is Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGES);
        onCreate(sqLiteDatabase);
    }
}
