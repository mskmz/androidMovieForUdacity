package com.example.mskmz.androidmovieforudacity.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;

import java.util.ArrayList;
import java.util.List;

import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.*;

/**
 * Created by wangzekang on 2018/1/24.
 */

public class CollectionDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "collection.db";

    private static final int DATABASE_VERSION = 1;

    public CollectionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private SQLiteDatabase db;

    public void insert(ContentValues values, String tableName) {
        db = getWritableDatabase();
        db.insert(tableName, null, values);
    }

    public boolean idIsExists(String str) {

        db = getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME, new String[]{"id"}, "id=?", new String[]{str}, null, null, null);

        while (cursor.moveToNext()) {
            return true;// //有城市在数据库已存在，返回true
        }
        return false;// //在数据库以前存在 false

    }

    public List<MoiveListVo.DateBean> getAllList() {
        List<MoiveListVo.DateBean> dateBeanList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.query(
                /* Name of table on which to perform the query */
                TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        while (cursor.moveToNext()) {
            MoiveListVo.DateBean dateBean = new MoiveListVo.DateBean();
            dateBean.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            dateBean.setVideo(cursor.getString(cursor.getColumnIndex(COLUMN_VIDEO)));
            dateBean.setVote_count(cursor.getString(cursor.getColumnIndex(COLUMN_VOTE_COUNT)));
            dateBean.setVote_average(cursor.getString(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE)));
            dateBean.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            dateBean.setPopularity(cursor.getString(cursor.getColumnIndex(COLUMN_POPULARITY)));
            dateBean.setPoster_path(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
            dateBean.setOriginal_language(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_LANGUAGE)));
            dateBean.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_TITLE)));
            dateBean.setBackdrop_path(cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP_PATH)));
            dateBean.setAdult(cursor.getString(cursor.getColumnIndex(COLUMN_ADULT)));
            dateBean.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
            dateBean.setRelease_date(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE)));
            dateBeanList.add(dateBean);
        }
        return dateBeanList;

    }

    public void del(String id) {
        if (db == null) {
            db = getWritableDatabase();
        }
        db.delete(TABLE_NAME, "id=?", new String[]{id});
    }

    public void insert(MoiveListVo.DateBean moiveListVo) {
        if (moiveListVo != null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VOTE_COUNT, moiveListVo.getVote_count());
            values.put(COLUMN_ID, moiveListVo.getId());
            values.put(COLUMN_VIDEO, moiveListVo.getVideo());
            values.put(COLUMN_VOTE_AVERAGE, moiveListVo.getVote_average());
            values.put(COLUMN_TITLE, moiveListVo.getTitle());
            values.put(COLUMN_POPULARITY, moiveListVo.getPopularity());
            values.put(COLUMN_POSTER_PATH, moiveListVo.getPoster_path());
            values.put(COLUMN_ORIGINAL_LANGUAGE, moiveListVo.getOriginal_language());
            values.put(COLUMN_ORIGINAL_TITLE, moiveListVo.getTitle());
            values.put(COLUMN_BACKDROP_PATH, moiveListVo.getBackdrop_path());
            values.put(COLUMN_ADULT, moiveListVo.getAdult());
            values.put(COLUMN_OVERVIEW, moiveListVo.getOverview());
            values.put(COLUMN_RELEASE_DATE, moiveListVo.getRelease_date());
            insert(values, TABLE_NAME);
        }
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    /**
     * Called when the database is created for the first time. This is where the creation of
     * tables and the initial population of the tables should happen.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;
        /*
         * This String will contain a simple SQL statement that will create a table that will
         * cache our weather data.
         */
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE if not exists " + TABLE_NAME + " (" +

                /*
                 * WeatherEntry did not explicitly declare a column called "_ID". However,
                 * WeatherEntry implements the interface, "BaseColumns", which does have a field
                 * named "_ID". We use that here to designate our table's primary key.
                 */
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_VOTE_COUNT + " TEXT," +
                        COLUMN_VIDEO + " TEXT," +
                        COLUMN_VOTE_AVERAGE + " TEXT," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_POPULARITY + " TEXT," +
                        COLUMN_POSTER_PATH + " TEXT," +
                        COLUMN_ORIGINAL_LANGUAGE + " TEXT," +
                        COLUMN_ORIGINAL_TITLE + " TEXT," +
                        COLUMN_BACKDROP_PATH + " TEXT," +
                        COLUMN_ADULT + " TEXT," +
                        COLUMN_OVERVIEW + " TEXT," +
                        COLUMN_RELEASE_DATE + " TEXT" +
                        ");";

        /*
         * After we've spelled out our SQLite table creation statement above, we actually execute
         * that SQL with the execSQL method of our SQLite database object.
         */
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    /**
     * This database is only a cache for online data, so its upgrade policy is simply to discard
     * the data and call through to onCreate to recreate the table. Note that this only fires if
     * you change the version number for your database (in our case, DATABASE_VERSION). It does NOT
     * depend on the version number for your application found in your app/build.gradle file. If
     * you want to update the schema without wiping data, commenting out the current body of this
     * method should be your top priority before modifying this method.
     *
     * @param sqLiteDatabase Database that is being upgraded
     * @param oldVersion     The old database version
     * @param newVersion     The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
