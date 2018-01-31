package com.example.mskmz.androidmovieforudacity.data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by wangzekang on 2018/1/30.
 */

public class MoviePreferences {
    public static final String SORT_TYPE = "sort_type";
    public static final int SORT_HOT = 100;
    public static final int SORT_SCORE = 101;
    public static final int SORT_COLLECTION = 102;

    public static SharedPreferences.Editor getEditor(Activity activity) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.edit();
    }

    public static void changeSortType(Activity activity, int sortType) {
        SharedPreferences.Editor editor = getEditor(activity);
        editor.putInt(SORT_TYPE, sortType);
        editor.commit();
    }

    public static int getSortType(Activity activity) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getInt(SORT_TYPE, SORT_HOT);//第二个参数为默认值
    }

}
