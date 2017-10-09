package com.example.mskmz.androidmovieforudacity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.mskmz.androidmovieforudacity.adapter.BaseRecyclerViewAdapter;
import com.example.mskmz.androidmovieforudacity.adapter.MovieShowListAdapter;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.example.mskmz.androidmovieforudacity.utils.AsTaskUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mskmz.androidmovieforudacity.Content.MOIVE_LIST_VO_SER;
import static com.example.mskmz.androidmovieforudacity.Content.MY_KEY;
import static com.example.mskmz.androidmovieforudacity.utils.Utils.isPad;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String HOT_LIST_URL = "http://api.themoviedb.org/3/movie/popular";
    private static final String SCORE_LIST_URL = "http://api.themoviedb.org/3/movie/top_rated";
    private static final String FIND_URL_PRE = "https://api.themoviedb.org/3/movie";
    private static final String KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    MovieShowListAdapter movieShowAdapter;
    List<MoiveListVo.DateBean> moiveDetailVoList;
    AsTaskUtil
            mGetHotListTask,
            mGetScoreTask;
    @BindView(R.id.rv_movie_show_list)
    RecyclerView mMovieShowListRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTask();
        init();
        setClick();
    }

    private void initTask() {
        mGetScoreTask = new AsTaskUtil(buildListUrl(SCORE_LIST_URL), this, new AsTaskUtil.AsTaskOnPostExecuteCalllBack() {
            @Override
            public void callback(String result) {
                MoiveListVo showVo = JSON.parseObject(result, MoiveListVo.class);
                if (showVo != null && showVo.getResults() != null) {
                    moiveDetailVoList.clear();
                    moiveDetailVoList.addAll(showVo.getResults());
                    movieShowAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT);

                }
            }
        });
        mGetHotListTask = new AsTaskUtil(buildListUrl(HOT_LIST_URL), this, new AsTaskUtil.AsTaskOnPostExecuteCalllBack() {
            @Override
            public void callback(String result) {
                MoiveListVo showVo = JSON.parseObject(result, MoiveListVo.class);
                if (showVo != null && showVo.getResults() != null) {
                    moiveDetailVoList.clear();
                    moiveDetailVoList.addAll(showVo.getResults());
                    movieShowAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT);

                }
            }
        });
    }

    private void init() {
        moiveDetailVoList = new ArrayList<>();
        movieShowAdapter = new MovieShowListAdapter(this, moiveDetailVoList, new BaseRecyclerViewAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int postion) {
                Intent staryDetailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
                staryDetailActivityIntent.putExtra(MOIVE_LIST_VO_SER, moiveDetailVoList.get(postion));
                startActivity(staryDetailActivityIntent);
            }
        });
        mMovieShowListRecyclerView.setHasFixedSize(true);
        mMovieShowListRecyclerView.setAdapter(movieShowAdapter);
        if (isPad(this)) {
            mMovieShowListRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mMovieShowListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        Log.d(TAG, "init: GetMovieListTask 运行");
        mGetHotListTask.run();
    }

    private void setClick() {
    }

    public URL buildListUrl(String url) {
        Uri builtUri = Uri.parse(url).buildUpon()
                .appendQueryParameter(KEY_PARAM, MY_KEY)
                .build();
        URL restUrl = null;
        try {
            restUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return restUrl;
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

    public void ruleHot() {
        mGetHotListTask.run();
    }

    public void ruleScore() {
        mGetScoreTask.run();
    }

}
