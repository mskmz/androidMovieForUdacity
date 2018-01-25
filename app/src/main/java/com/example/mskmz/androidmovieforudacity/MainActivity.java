package com.example.mskmz.androidmovieforudacity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.mskmz.androidmovieforudacity.adapter.BaseRecyclerViewAdapter;
import com.example.mskmz.androidmovieforudacity.adapter.MovieShowListAdapter;
import com.example.mskmz.androidmovieforudacity.corecomponents.loadermanager.prototype.AsyncTaskLoaderForBase;
import com.example.mskmz.androidmovieforudacity.corecomponents.loadermanager.prototype.AsyncTaskLoaderForString;
import com.example.mskmz.androidmovieforudacity.data.CollectionDbHelper;
import com.example.mskmz.androidmovieforudacity.databinding.ActivityMainBinding;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;

import java.util.ArrayList;
import java.util.List;

import static com.example.mskmz.androidmovieforudacity.data.Content.MOIVE_LIST_VO_SER;
import static com.example.mskmz.androidmovieforudacity.utils.Utils.buildListUrl;
import static com.example.mskmz.androidmovieforudacity.utils.Utils.isPad;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    private static final String TAG = "MainActivity";
    private static final String HOT_LIST_URL = "http://api.themoviedb.org/3/movie/popular";
    private static final String SCORE_LIST_URL = "http://api.themoviedb.org/3/movie/top_rated";
    private static final String FIND_URL_PRE = "https://api.themoviedb.org/3/movie";
    private static final String LANGUAGE_PARAM = "language";
    MovieShowListAdapter movieShowAdapter;
    List<MoiveListVo.DateBean> moiveDetailVoList;

    private static final int GET_HOTLIST_LOADER = 11;
    private static final int GET_SCORE_LOADER = 12;

    private static final int DATEBASE_GET_LIST = 21;


    ActivityMainBinding mainBinding;

    private Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        setClick();
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
        mainBinding.rvMovieShowList.setHasFixedSize(true);
        mainBinding.rvMovieShowList.setAdapter(movieShowAdapter);
        if (isPad(this)) {
            mainBinding.rvMovieShowList.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mainBinding.rvMovieShowList.setLayoutManager(new GridLayoutManager(this, 2));
        }

        runLoaderAsyncTask(GET_HOTLIST_LOADER);

    }

    private void setClick() {
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
                runLoaderAsyncTask(GET_HOTLIST_LOADER);
                return true;
            case R.id.action_rule_score:
                runLoaderAsyncTask(GET_SCORE_LOADER);
                return true;
            case R.id.action_rule_favorte:
                runLoaderAsyncTask(DATEBASE_GET_LIST);
        }
        return super.onOptionsItemSelected(item);
    }

    public void runLoaderAsyncTask(int identifier) {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(identifier);
        // COMPLETED (23) If the Loader was null, initialize it. Else, restart it.
        if (githubSearchLoader == null) {
            loaderManager.initLoader(identifier, null, this);
        } else {
            loaderManager.restartLoader(identifier, null, this);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case GET_HOTLIST_LOADER:
                return new AsyncTaskLoaderForString(
                        id,
                        MainActivity.this,
                        buildListUrl(HOT_LIST_URL),
                        new AsyncTaskLoaderForString.AsTaskCallBack() {
                            @Override
                            public void callback(String result) {
                                MoiveListVo showVo = JSON.parseObject(result, MoiveListVo.class);
                                if (showVo != null && showVo.getResults() != null) {
                                    moiveDetailVoList.clear();
                                    moiveDetailVoList.addAll(showVo.getResults());
                                    Log.d(TAG, "callback: GET_HOTLIST_LOADER刷新");
                                    movieShowAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                );
            case GET_SCORE_LOADER:
                return new AsyncTaskLoaderForString(
                        id,
                        MainActivity.this,
                        buildListUrl(SCORE_LIST_URL),
                        new AsyncTaskLoaderForString.AsTaskCallBack() {
                            @Override
                            public void callback(String result) {
                                MoiveListVo showVo = JSON.parseObject(result, MoiveListVo.class);
                                if (showVo != null && showVo.getResults() != null) {
                                    moiveDetailVoList.clear();
                                    moiveDetailVoList.addAll(showVo.getResults());
                                    Log.d(TAG, "callback: SCORE_LIST_URL刷新");
                                    movieShowAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            case DATEBASE_GET_LIST:
                return new AsyncTaskLoaderForBase<List<MoiveListVo.DateBean>>(
                        id,
                        getContext(),
                        new AsyncTaskLoaderForBase.BaseAsTask<List<MoiveListVo.DateBean>>() {
                            @Override
                            public List<MoiveListVo.DateBean> runBase() {
                                CollectionDbHelper collectionDbHelper = new CollectionDbHelper(getContext());
                                return collectionDbHelper.getAllList();
                            }

                            @Override
                            public void callback(List<MoiveListVo.DateBean> result) {
                                moiveDetailVoList.clear();
                                moiveDetailVoList.addAll(result);
                                movieShowAdapter.notifyDataSetChanged();
                            }
                        }

                );
            default:
                return new AsyncTaskLoaderForString(MainActivity.this);
        }

    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader instanceof AsyncTaskLoaderForString) {
            AsyncTaskLoaderForString asyncTaskLoaderForString = (AsyncTaskLoaderForString) loader;
            asyncTaskLoaderForString.callBack((String) data);
        } else if (loader instanceof AsyncTaskLoaderForBase) {
            AsyncTaskLoaderForBase asyncTaskLoaderForString = (AsyncTaskLoaderForBase) loader;
            asyncTaskLoaderForString.callBack(data);

        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
