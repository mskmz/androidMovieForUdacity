package com.example.mskmz.androidmovieforudacity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.mskmz.androidmovieforudacity.adapter.BaseRecyclerViewAdapter;
import com.example.mskmz.androidmovieforudacity.adapter.MovieShowListAdapter;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.example.mskmz.androidmovieforudacity.utils.NetWorkUtils;
import com.example.mskmz.androidmovieforudacity.utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.mskmz.androidmovieforudacity.Content.MOIVE_LIST_VO_SER;
import static com.example.mskmz.androidmovieforudacity.Content.MY_KEY;
import static com.example.mskmz.androidmovieforudacity.Content.USER_LANGUAGE;
import static com.example.mskmz.androidmovieforudacity.utils.Utils.isPad;


public class MainActivity extends AppCompatActivity {
    RecyclerView mMovieShowListRecyclerView;
    private static final String TAG = "MainActivity";
    private static final String HOT_LIST_URL = "http://api.themoviedb.org/3/movie/popular";
    private static final String FIND_URL_PRE = "https://api.themoviedb.org/3/movie";
    private static final String KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    MovieShowListAdapter movieShowAdapter;
    List<MoiveListVo.DateBean> moiveDetailVoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        init();
        setClick();
    }

    private void init() {
        moiveDetailVoList = new ArrayList<>();
        movieShowAdapter = new MovieShowListAdapter(this, moiveDetailVoList, new BaseRecyclerViewAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int postion) {
                Intent staryDetailActivityIntent = new Intent(MainActivity.this,DetailActivity.class);
                staryDetailActivityIntent.putExtra(MOIVE_LIST_VO_SER,moiveDetailVoList.get(postion));
                startActivity(staryDetailActivityIntent);
            }
        });
        mMovieShowListRecyclerView.setHasFixedSize(true);
        mMovieShowListRecyclerView.setAdapter(movieShowAdapter);
        if(isPad(this)){
            mMovieShowListRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }else{
            mMovieShowListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        Log.d(TAG, "init: GetMovieListTask 运行");
        new GetMovieListTask().execute();
    }

    private void setClick() {
    }

    private void findView() {
        mMovieShowListRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_show_list);
    }

    public URL buildListUrl() {
        Uri builtUri = Uri.parse(HOT_LIST_URL).buildUpon()
                .appendQueryParameter(KEY_PARAM, MY_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public URL buildDetailUrl(String id) {
        Uri builtUri = Uri.parse(FIND_URL_PRE + "/" + id).buildUpon()
                .appendQueryParameter(KEY_PARAM, MY_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, USER_LANGUAGE)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public class GetMovieListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            URL weatherRequestUrl = buildListUrl();
            try {
                String result = NetWorkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);
//                MoiveListVo showVo = JSON.parseObject(result, MoiveListVo.class);
//                List<MoiveDetailVo> moiveDetailVoList = null;
//                for (MoiveListVo.DateBean dateBean : showVo.getProduction_companies()) {
//                    if (moiveDetailVoList == null) {
//                        moiveDetailVoList = new ArrayList<>();
//                    }
//                    String movieDetailJson = NetWorkUtils
//                            .getResponseFromHttpUrl(buildDetailUrl(dateBean.getId()));
//                    MoiveDetailVo moiveDetail = JSON.parseObject(movieDetailJson, MoiveDetailVo.class);
//                    moiveDetailVoList.add(moiveDetail);
//                }
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
                Log.d(TAG, "onPostExecute: " + result);
                MoiveListVo showVo = JSON.parseObject(result, MoiveListVo.class);
                if (showVo != null && showVo.getResults() != null) {
                    moiveDetailVoList.clear();
                    moiveDetailVoList.addAll(showVo.getResults());
                    movieShowAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT);

                }
            } else {
                Toast.makeText(MainActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_more_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rule_hot:
                ruleHot();
                return true;
            case R.id.action_rule_score:
                ruleScore();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean isRuleHotUp=true;
    public void ruleHot() {
        Collections.sort(moiveDetailVoList, new Comparator<MoiveListVo.DateBean>() {
            @Override
            public int compare(MoiveListVo.DateBean t0, MoiveListVo.DateBean t1) {
                double numbrt1 = Utils.getdouble(t0.getPopularity());
                double numbrt2 = Utils.getdouble(t1.getPopularity());
                if(isRuleHotUp){
                    return (int) (numbrt1 - numbrt2);
                }else{
                    return (int) (numbrt2 - numbrt1);
                }
            }
        });
        isRuleHotUp=!isRuleHotUp;
        movieShowAdapter.notifyDataSetChanged();
    }
    public boolean isRuleScoreUp=true;
    public void ruleScore() {
        Collections.sort(moiveDetailVoList, new Comparator<MoiveListVo.DateBean>() {
            @Override
            public int compare(MoiveListVo.DateBean t0, MoiveListVo.DateBean t1) {
                double numbrt1 = Utils.getdouble(t0.getVote_average());
                double numbrt2 = Utils.getdouble(t1.getVote_average());
                if(isRuleScoreUp){
                    return (int) (numbrt1 - numbrt2);
                }else{
                    return (int) (numbrt2 - numbrt1);
                }
            }
        });
        isRuleScoreUp=!isRuleScoreUp;
        movieShowAdapter.notifyDataSetChanged();
    }

}
