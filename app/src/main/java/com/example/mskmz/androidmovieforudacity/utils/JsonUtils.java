package com.example.mskmz.androidmovieforudacity.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzekang on 2017/10/4.
 */

public class JsonUtils {
    public static String getParme(String json, String pama) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            String rest = null;
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                rest = jsonObject.getString(pama);
            }
            return rest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getParmeList(String json, String pama) {
        List<String> restList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                restList.add(jsonObject.getString(pama));
            }
            return restList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
