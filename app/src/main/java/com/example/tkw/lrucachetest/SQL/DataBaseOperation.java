package com.example.tkw.lrucachetest.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tkw.lrucachetest.News.Stories;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOperation {

    private static  volatile DataBaseOperation dataBaseOperation;

    public static DataBaseOperation GetInstance(Context context){
        if(dataBaseOperation == null){
            synchronized (DataBaseOperation.class){
                if(dataBaseOperation == null) {
                    dataBaseOperation = new DataBaseOperation(context);
                }
            }
        }
        return dataBaseOperation;
    }

    private final StoriesDatabaseHelper storiesDatabaseHelper;

    private DataBaseOperation(Context context){
        storiesDatabaseHelper = new StoriesDatabaseHelper(context);
    }

    public void insert(int id, String imageAddress, String title, int date, String stories_url){
        SQLiteDatabase db = storiesDatabaseHelper.getWritableDatabase();
        if(db.isOpen()){
            db.execSQL("insert into stories(id, images, title, date, stories_url)values(" + "'"+  id + "'"+ "," + "'"+ imageAddress + "'" + "," + "'" + title + "'" + "," + "'"+date + "'"+ "," + "'" +stories_url + "')");
            db.close();
        }
    }

    public List<Stories> queryStories(int date){
        List<Stories> storiesList = new ArrayList<>();
        SQLiteDatabase db = storiesDatabaseHelper.getWritableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from stories where id = ?",new String[]{date + ""});
            while (cursor.moveToNext()){
                Stories stories = new Stories();
                stories.setId(cursor.getInt(cursor.getColumnIndex("id")));
                stories.setImages(cursor.getString(cursor.getColumnIndex("images")));
                stories.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                stories.setDate(cursor.getInt(cursor.getColumnIndex("date")));
                stories.setStories_address(cursor.getString(cursor.getColumnIndex("stories_url")));
                storiesList.add(stories);
            }
            cursor.close();
            db.close();
        }
        return storiesList;
    }



}
