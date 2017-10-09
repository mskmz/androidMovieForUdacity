package com.example.mskmz.androidmovieforudacity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_detail_tile)
    TextView mTitleTextView;
    @BindView(R.id.iv_detail_show)
    ImageView mShowPictureImageView;
    @BindView(R.id.tv_detail_year)
    TextView mYearTextView;
    @BindView(R.id.tv_detail_time_long)
    TextView mTimeLongTextView;
    @BindView(R.id.tv_detail_score)
    TextView mScoreTextView;
    @BindView(R.id.tv_dateil_detail)
    TextView mDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Content.MOIVE_LIST_VO_SER)) {
            MoiveListVo.DateBean dateBean = intent.getParcelableExtra(Content.MOIVE_LIST_VO_SER);
            mTitleTextView.setText(dateBean.getTitle());
            String year = "";
            if (dateBean.getRelease_date().length() > 4) {
                year = dateBean.getRelease_date().substring(0, 4);
            }
            mYearTextView.setText(year);
            mScoreTextView.setText(dateBean.getVote_average() + "/10");
            mDetail.setText(dateBean.getOverview());
            Picasso
                    .with(this)
                    .load("https://image.tmdb.org/t/p/w185/" + dateBean.getPoster_path())
                    .into(mShowPictureImageView);
        }
    }

}
