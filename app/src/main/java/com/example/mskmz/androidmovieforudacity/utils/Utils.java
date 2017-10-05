package com.example.mskmz.androidmovieforudacity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wangzekang on 2017/10/5.
 */

public class Utils {
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public static int getWidth(Activity activity){
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }
    public static int  getHeight(Activity activity){
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        return height;
    }
    public static int getActionBarHeight(Activity activity){
        if(activity.getActionBar()!=null){
            return activity.getActionBar().getHeight();
        }else{
            return -1;
        }
    }
    public static int getTitleHeight(Activity activity){
        return getTopHeight(activity)-getStatusBarHeight(activity);
    }
    public static int getTopHeight(Activity activity){
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop;
    }

    public static int getStatusBarHeight(Activity activity) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 0;
        }
    }
    public static double getdouble(String number){
        try{
            return new Double(number);
        }catch (Exception e){
            return 0;
        }
    }
    public static int  getHasVirtualKey(Activity activity) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }
}
