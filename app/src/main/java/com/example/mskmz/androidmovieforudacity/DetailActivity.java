package com.example.mskmz.androidmovieforudacity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.mskmz.androidmovieforudacity.adapter.MovieDetailCommentListAdapter;
import com.example.mskmz.androidmovieforudacity.adapter.MovieDetailTrailerListAdapter;
import com.example.mskmz.androidmovieforudacity.corecomponents.loadermanager.prototype.AsyncTaskLoaderForBase;
import com.example.mskmz.androidmovieforudacity.corecomponents.loadermanager.prototype.AsyncTaskLoaderForString;
import com.example.mskmz.androidmovieforudacity.data.CollectionDbHelper;
import com.example.mskmz.androidmovieforudacity.data.Content;
import com.example.mskmz.androidmovieforudacity.databinding.ActivityDetailBinding;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveDetailVo;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.example.mskmz.androidmovieforudacity.model.vo.ReviewsListVo;
import com.example.mskmz.androidmovieforudacity.model.vo.VideosListVo;

import java.util.ArrayList;
import java.util.List;

import static com.example.mskmz.androidmovieforudacity.data.Content.BASE_URL;
import static com.example.mskmz.androidmovieforudacity.utils.Utils.buildListUrl;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks, View.OnClickListener {


    private static final String TAG = "DetailActivity";
    ActivityDetailBinding detailBinding;
    private final static int GET_LIST_TRAILER = 10;
    private final static int GET_LIST_COMMENT = 11;
    private final static int GET_BASE_INFO = 12;

    private final static int DATEBASE_INSERT = 21;
    private final static int DATEBASE_ISEXIST = 22;
    private final static int DATEBASE_DEL = 23;


    private static final String URL_GET_LIST_TRAILER = "/videos";
    private static final String URL_GET_LIST_COMMENT = "/reviews";
    MoiveListVo.DateBean dateBean;
    private List<VideosListVo.DateBean> videoDatesList;
    private List<ReviewsListVo.DateBean> reviewDatesList;
    private MovieDetailCommentListAdapter movieDetailCommentListAdapter;
    private MovieDetailTrailerListAdapter movieDetailTrailerListAdapter;
    private boolean isFavorte = false;
    private boolean setFavorte = true;


    private Context getContext() {
        return DetailActivity.this;
    }

    private void setFavorte() {
        if (setFavorte) {
            detailBinding.btnSetFavorte.setBackgroundColor(getResources().getColor(R.color.colorGray));
            detailBinding.btnSetFavorte.setText(getResources().getString(R.string.set_favorte_loading));
        } else if (!isFavorte) {
            detailBinding.btnSetFavorte.setBackgroundColor(getResources().getColor(R.color.colorDetailTop));
            detailBinding.btnSetFavorte.setText(getResources().getString(R.string.set_favorte_set));
        } else {
            detailBinding.btnSetFavorte.setBackgroundColor(getResources().getColor(R.color.colorRed));
            detailBinding.btnSetFavorte.setText(getResources().getString(R.string.set_favorte_cancel));
        }
    }

    private void readFavorte() {
        CollectionDbHelper collectionDbHelper = new CollectionDbHelper(this);
        if (collectionDbHelper.idIsExists(dateBean.getId())) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        init();
        initRecyc();
        setClick();
        runLoaderAsyncTask(DATEBASE_ISEXIST);
    }

    private void setClick() {
        detailBinding.btnSetFavorte.setOnClickListener(this);
    }

    private void initRecyc() {
        videoDatesList = new ArrayList<>();
        movieDetailTrailerListAdapter = new MovieDetailTrailerListAdapter(this, videoDatesList, null);
        detailBinding.recycTrailers.setHasFixedSize(true);
        detailBinding.recycTrailers.setAdapter(movieDetailTrailerListAdapter);
        detailBinding.recycTrailers.setLayoutManager(new LinearLayoutManager(this));

        reviewDatesList = new ArrayList<>();
        movieDetailCommentListAdapter = new MovieDetailCommentListAdapter(this, reviewDatesList, null);
        detailBinding.recycComment.setHasFixedSize(true);
        detailBinding.recycComment.setAdapter(movieDetailCommentListAdapter);
        detailBinding.recycComment.setLayoutManager(new LinearLayoutManager(this));


    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Content.MOIVE_LIST_VO_SER)) {
            dateBean = intent.getParcelableExtra(Content.MOIVE_LIST_VO_SER);
            detailBinding.tvDetailTile.setText(dateBean.getTitle());
            Glide
                    .with(this)
                    .load("https://image.tmdb.org/t/p/w185/" + dateBean.getPoster_path())
                    .into(detailBinding.ivDetailShow);
            runLoaderAsyncTask(GET_LIST_TRAILER);
            runLoaderAsyncTask(GET_LIST_COMMENT);
            runLoaderAsyncTask(GET_BASE_INFO);

        }
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case GET_LIST_TRAILER:
                return new AsyncTaskLoaderForString(
                        id,
                        DetailActivity.this,
                        buildListUrl(BASE_URL + "/" + dateBean.getId() + URL_GET_LIST_TRAILER),
                        new AsyncTaskLoaderForString.AsTaskCallBack() {
                            @Override
                            public void callback(String result) {
                                Log.d(TAG, "callback: " + result);
                                VideosListVo videosListVo = JSON.parseObject(result, VideosListVo.class);
                                if (videoDatesList.size() > 0) {
                                    videoDatesList.clear();
                                }
                                videoDatesList.addAll(videosListVo.getResults());
                                movieDetailTrailerListAdapter.notifyDataSetChanged();
                            }
                        }
                );
            case GET_LIST_COMMENT:
                return new AsyncTaskLoaderForString(
                        id,
                        DetailActivity.this,
                        buildListUrl(BASE_URL + "/" + dateBean.getId() + URL_GET_LIST_COMMENT),
                        new AsyncTaskLoaderForString.AsTaskCallBack() {
                            @Override
                            public void callback(String result) {
                                Log.d(TAG, "callback: " + result);
                                ReviewsListVo reviewsListVo = JSON.parseObject(result, ReviewsListVo.class);
                                if (reviewDatesList.size() > 0) {
                                    reviewDatesList.clear();
                                }
                                reviewDatesList.addAll(reviewsListVo.getResults());
                                movieDetailCommentListAdapter.notifyDataSetChanged();
                            }
                        }
                );
            case GET_BASE_INFO:
                return new AsyncTaskLoaderForString(
                        id,
                        DetailActivity.this,
                        buildListUrl(BASE_URL + "/" + dateBean.getId()),
                        new AsyncTaskLoaderForString.AsTaskCallBack() {
                            @Override
                            public void callback(String result) {
                                Log.d(TAG, "callback: " + result);
                                MoiveDetailVo moiveDetailVo = JSON.parseObject(result, MoiveDetailVo.class);

                                String year = "";
                                if (moiveDetailVo.getRelease_date().length() > 4) {
                                    year = moiveDetailVo.getRelease_date().substring(0, 4);
                                }
                                detailBinding.tvDetailYear.setText(year);
                                detailBinding.tvDetailScore.setText(moiveDetailVo.getVote_average() + "/10");
                                detailBinding.tvDetailTimeLong.setText(moiveDetailVo.getRuntime() + "min");
                                detailBinding.tvDateilDetail.setText(moiveDetailVo.getOverview());
                            }
                        }
                );
            case DATEBASE_INSERT:
                return new AsyncTaskLoaderForBase<Void>(
                        id,
                        DetailActivity.this,
                        new AsyncTaskLoaderForBase.BaseAsTask<Void>() {
                            @Override
                            public Void runBase() {
                                setFavorte = true;
                                CollectionDbHelper collectionDbHelper = new CollectionDbHelper(getContext());
                                collectionDbHelper.insert(dateBean);
                                return null;
                            }

                            @Override
                            public void callback(Void result) {
                                runLoaderAsyncTask(DATEBASE_ISEXIST);
                            }
                        }
                );
            case DATEBASE_DEL:
                return new AsyncTaskLoaderForBase<Void>(
                        id,
                        getContext(),
                        new AsyncTaskLoaderForBase.BaseAsTask<Void>() {
                            @Override
                            public Void runBase() {
                                setFavorte = true;
                                CollectionDbHelper collectionDbHelper = new CollectionDbHelper(getContext());
                                collectionDbHelper.del(dateBean.getId());
                                return null;
                            }

                            @Override
                            public void callback(Void result) {
                                runLoaderAsyncTask(DATEBASE_ISEXIST);
                            }
                        }
                );
            case DATEBASE_ISEXIST:
                return new AsyncTaskLoaderForBase<Boolean>(
                        id,
                        getContext(),
                        new AsyncTaskLoaderForBase.BaseAsTask<Boolean>() {
                            @Override
                            public Boolean runBase() {
                                setFavorte = true;
                                CollectionDbHelper collectionDbHelper = new CollectionDbHelper(getContext());
                                if (collectionDbHelper.idIsExists(dateBean.getId())) {
                                    Log.d(TAG, "runBase: idIsExists is Ture");
                                    return true;
                                }
                                Log.d(TAG, "runBase: idIsExists is False");
                                return false;
                            }

                            @Override
                            public void callback(Boolean result) {
                                isFavorte = result;
                                setFavorte = false;
                                setFavorte();
                            }
                        }
                );
            default:
                return new AsyncTaskLoaderForString(DetailActivity.this);
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

    public void runLoaderAsyncTask(int identifier) {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader githubSearchLoader = loaderManager.getLoader(identifier);
        // COMPLETED (23) If the Loader was null, initialize it. Else, restart it.
        if (githubSearchLoader == null) {
            loaderManager.initLoader(identifier, null, this);
        } else {
            loaderManager.restartLoader(identifier, null, this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_set_favorte:
                if (setFavorte) {
                    return;
                }
                setFavorte=true;
                setFavorte();
                if (isFavorte) {
                    runLoaderAsyncTask(DATEBASE_DEL);
                } else {
                    runLoaderAsyncTask(DATEBASE_INSERT);
                }
                break;
        }

    }
}
