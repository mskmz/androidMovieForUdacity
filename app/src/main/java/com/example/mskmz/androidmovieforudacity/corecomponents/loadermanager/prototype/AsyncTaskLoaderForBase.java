package com.example.mskmz.androidmovieforudacity.corecomponents.loadermanager.prototype;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.mskmz.androidmovieforudacity.utils.NetWorkUtils;

import java.net.URL;

/**
 * Created by wangzekang on 2018/1/22.
 */

public class AsyncTaskLoaderForBase<T> extends AsyncTaskLoader {
    private static final String TAG = "AsyncTaskLoaderForLoade";
    private URL url;
    private int identifier = -1;


    private BaseAsTask<T> dateBaseAsTask;

    public AsyncTaskLoaderForBase(Context context) {
        super(context);
    }

    public AsyncTaskLoaderForBase(int identifier, Context context, BaseAsTask<T> dateBaseAsTask) {
        super(context);
        this.identifier = identifier;
        this.url = url;
        this.dateBaseAsTask = dateBaseAsTask;
    }

    @Override
    protected void onStartLoading() {
        if (identifier < 1 || dateBaseAsTask == null) {
            return;
        }
        forceLoad();
    }

    @Override
    public T loadInBackground() {
        try {
            return dateBaseAsTask.runBase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void callBack(T result) {
        if (dateBaseAsTask != null) {
            dateBaseAsTask.callback(result);
        }
    }

    public interface BaseAsTask<T> {
        public T runBase();

        public void callback(T result);
    }
}
