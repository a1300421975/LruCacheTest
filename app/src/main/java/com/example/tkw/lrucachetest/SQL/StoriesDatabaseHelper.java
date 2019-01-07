package com.example.tkw.lrucachetest.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoriesDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String CREATE_STORIES_DB = ""
            + "create table stories("
            + "num integer primary key autoincrement,"
            + "id integer,"
            + "images varchar(70),"
            + "title varchar(60),"
            + "date integer,"
            + "stories_url varchar(70))";

    public StoriesDatabaseHelper( Context context) {
        super(context, "stories_db", null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORIES_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
