package com.example.mskmz.androidmovieforudacity.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CONTENT_URL;
import static com.example.mskmz.androidmovieforudacity.data.CollectionContent.CollectionEntry.TABLE_NAME;

/**
 * Created by wangzekang on 2018/1/30.
 */

public class CollectionVontentProvider extends ContentProvider {
    private CollectionDbHelper collectionDbHelper;
    public static final int COLLECTIONS = 100;
    public static final int COLLECTION_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        Context content = getContext();
        collectionDbHelper = new CollectionDbHelper(content);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case COLLECTION_WITH_ID: {
                String normalizedUtcDateString = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{normalizedUtcDateString};

                cursor = collectionDbHelper.getReadableDatabase().query(
                        CollectionContent.CollectionEntry.TABLE_NAME,
                        projection,
                        CollectionContent.CollectionEntry._ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }
            case COLLECTIONS: {
                cursor = collectionDbHelper.getReadableDatabase().query(
                        CollectionContent.CollectionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = collectionDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        switch (match) {
            case COLLECTIONS:
                long id = db.insert(CollectionContent.CollectionEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URL, id);
                }
                break;
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return collectionDbHelper.getWritableDatabase().delete(TABLE_NAME, s, strings);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return collectionDbHelper.getWritableDatabase().update(TABLE_NAME, contentValues, s, strings);
    }

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CollectionContent.AUTHORITY, CollectionContent.PATH_COLLECTION, COLLECTIONS);
        uriMatcher.addURI(CollectionContent.AUTHORITY, CollectionContent.PATH_COLLECTION + "/#", COLLECTION_WITH_ID);
        return uriMatcher;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        collectionDbHelper.close();
        super.shutdown();
    }
}
