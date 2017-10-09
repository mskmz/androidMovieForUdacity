package com.example.mskmz.androidmovieforudacity.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.List;


/**
 * Created by wangzekang on 2017/9/14.
 */


public class AsTaskUtil {
    private URL url;
    private Context context;
    private Boolean isShowDialog = true;
    private static final String TAG = "AsTaskUtil";
    private AsTaskOnPostExecuteCalllBack asTaskOnPostExecuteCalllBack;

    public static void listRun(List<AsTaskUtil> asTaskList) {
        for (AsTaskUtil astask : asTaskList) {
            astask.run();
        }
    }

    public AsTaskUtil(URL url, Context context, AsTaskOnPostExecuteCalllBack asTaskOnPostExecuteCalllBack) {
        this.url = url;
        this.context = context;
        this.asTaskOnPostExecuteCalllBack = asTaskOnPostExecuteCalllBack;
    }

    public URL getUrl() {
        return url;
    }


    public void run() {
        Log.d(TAG, "run: "+url);
        new MyAsyncTask().execute();
    }



    public class MyAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            URL weatherRequestUrl = url;
            try {
                String result = NetWorkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);
                Log.d(TAG, "doInBackground: " + result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (!TextUtils.isEmpty(result)) {
                if(asTaskOnPostExecuteCalllBack!=null){
                    asTaskOnPostExecuteCalllBack.callback(result);
                }
            } else {
                Toast.makeText(context, "请求失败，请检查网络状态", Toast.LENGTH_SHORT);

            }
        }
    }

    public interface AsTaskOnPostExecuteCalllBack {
        public void callback(String result);
    }

    public interface AsTaskOnRunCallBack {
        public void callback(String result);
    }
}
