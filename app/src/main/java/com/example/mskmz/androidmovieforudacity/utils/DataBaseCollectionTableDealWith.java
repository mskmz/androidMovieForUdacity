package com.example.mskmz.androidmovieforudacity.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Created by wangzekang on 2018/1/30.
 */

public class DataBaseCollectionTableDealWith {
    public static List<MoiveListVo.DateBean> cursorToList(Cursor cursor){
        List<MoiveListVo.DateBean> dateBeanList = new ArrayList<>();
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
    public static ContentValues dateVoToContentValues(MoiveListVo.DateBean moiveListVo){
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
            return values;
        }
        return null;
    }
    public static final boolean isEmpty(Cursor cursor){
        while (cursor.moveToNext()) {
            return true;// //有城市在数据库已存在，返回true
        }
        return false;// //在数据库以前存在 false
    }
}
