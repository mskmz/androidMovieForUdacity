package com.example.mskmz.androidmovieforudacity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.example.mskmz.androidmovieforudacity.utils.Utils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView
            mTitleTextView,
            mYearTextView,
            mTimeLongTextView,
            mScoreTextView,
            mDetail;
    ImageView
            mShowPictureImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findView();
        init();
    }

    private void init() {
        Intent intent = getIntent();
        if(intent!=null&&intent.hasExtra(Content.MOIVE_LIST_VO_SER)){
            MoiveListVo.DateBean dateBean = (MoiveListVo.DateBean) intent.getSerializableExtra(Content.MOIVE_LIST_VO_SER);
            mTitleTextView.setText(dateBean.getTitle());
            String year = "";
            if(dateBean.getRelease_date().length()>4){
                year=dateBean.getRelease_date().substring(0,4);
            }
            mYearTextView.setText(year);
            mScoreTextView.setText(dateBean.getVote_average()+"/10");
            mDetail.setText(dateBean.getOverview());
            Picasso
                    .with(this)
                    .load("https://image.tmdb.org/t/p/w185/"+dateBean.getPoster_path())
                    .into(mShowPictureImageView);
        }
    }

    private void findView() {
        mTitleTextView = (TextView) findViewById(R.id.tv_detail_tile);
        mYearTextView = (TextView) findViewById(R.id.tv_detail_year);
        mTimeLongTextView = (TextView) findViewById(R.id.tv_detail_time_long);
        mScoreTextView = (TextView) findViewById(R.id.tv_detail_score);
        mDetail = (TextView) findViewById(R.id.tv_dateil_detail);
        mShowPictureImageView = (ImageView) findViewById(R.id.iv_detail_show);
    }
}
