package com.example.mskmz.androidmovieforudacity.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_ADULT;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_BACKDROP_PATH;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_ID;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_ORIGINAL_LANGUAGE;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_ORIGINAL_TITLE;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_OVERVIEW;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_POPULARITY;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_POSTER_PATH;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_RELEASE_DATE;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_TITLE;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_VIDEO;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_VOTE_AVERAGE;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.COLUMN_VOTE_COUNT;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.TABLE_NAME;

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
