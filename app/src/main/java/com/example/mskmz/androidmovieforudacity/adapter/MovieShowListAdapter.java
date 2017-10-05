package com.example.mskmz.androidmovieforudacity.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mskmz.androidmovieforudacity.R;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.example.mskmz.androidmovieforudacity.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.mskmz.androidmovieforudacity.utils.Utils.isPad;

/**
 * Created by wangzekang on 2017/10/4.
 */

public class MovieShowListAdapter extends BaseRecyclerViewAdapter<MoiveListVo.DateBean> {
    private static final String TAG = "MovieShowListAdapter";


    public MovieShowListAdapter(Context context, List<MoiveListVo.DateBean> postListVoList, ListItemClickListener listItemClickListener) {
        super(context, postListVoList, listItemClickListener);
    }

    @Override
    int setItemLayout() {
        return R.layout.listitem_movie_show;
    }

    @Override
    BaseViewHolder setViewHolder(View view, Context context) {
        return new ViewHodler(view,context);
    }


    public class ViewHodler extends  BaseViewHolder<MoiveListVo.DateBean>{
        public ImageView mMovieShowImageView ;

        public ViewHodler(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        void findView(View itemView) {
            mMovieShowImageView = (ImageView) itemView.findViewById(R.id.iv_listitem_movie_show);
        }

        @Override
        void bind(MoiveListVo.DateBean dataVo) {
            Activity activity = (Activity) context;
            Log.d(TAG, "bindActionBar: "+Utils.getActionBarHeight(activity));
            Log.d(TAG, "bindTitle: "+Utils.getTitleHeight(activity));
            Log.d(TAG, "bindTop: "+Utils.getTopHeight(activity));
            Log.d(TAG, "bindSbar: "+Utils.getStatusBarHeight(activity));
            int count=2;
            if(isPad(context)){
                count=3;
            }
            Picasso
                    .with(context)
                    .load("https://image.tmdb.org/t/p/w185/"+dataVo.getPoster_path())
                    .resize(Utils.getWidth(activity)/count,(Utils.getHeight(activity)-Utils.getTopHeight(activity))/count)
                    .centerCrop()
                    .into(mMovieShowImageView);
        }


    }
}
