package com.example.mskmz.androidmovieforudacity.corecomponents.loadermanager.prototype;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.mskmz.androidmovieforudacity.utils.NetWorkUtils;

import java.net.URL;

/**
 * Created by wangzekang on 2018/1/22.
 */

public class AsyncTaskLoaderForString extends AsyncTaskLoader<String> {
    private static final String TAG = "AsyncTaskLoaderForLoade";
    private URL url;

    private int identifier = -1;
    private AsTaskCallBack asTaskCallBack;

    public AsyncTaskLoaderForString(Context context) {
        super(context);
    }

    public AsyncTaskLoaderForString(int identifier, Context context, URL url, AsTaskCallBack asTaskCallBack) {
        super(context);
        this.identifier = identifier;
        this.url = url;
        this.asTaskCallBack = asTaskCallBack;
    }

    @Override
    protected void onStartLoading() {
        if (identifier < 1) {
            return;
        }
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        try {
            URL weatherRequestUrl = url;
            Log.d(TAG, "loadInBackground: 执行"+url);
            String result = NetWorkUtils.getResponseFromHttpUrl(weatherRequestUrl);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void callBack(String result) {
        if (asTaskCallBack != null) {
            asTaskCallBack.callback(result);
        }
    }

    public interface AsTaskCallBack {
        public void callback(String result);
    }
}
