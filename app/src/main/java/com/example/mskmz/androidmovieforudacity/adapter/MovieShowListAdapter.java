package com.example.mskmz.androidmovieforudacity.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mskmz.androidmovieforudacity.R;
import com.example.mskmz.androidmovieforudacity.databinding.ListitemMovieShowBinding;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.example.mskmz.androidmovieforudacity.utils.Utils;

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
        return new ViewHodler(view, context);
    }

    public class ViewHodler extends BaseViewHolder<MoiveListVo.DateBean> {
        ListitemMovieShowBinding listitemMovieShowBinding;

        public ViewHodler(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        void findView(View itemView) {
            listitemMovieShowBinding = DataBindingUtil.bind(itemView);
        }

        @Override
        void bind(final MoiveListVo.DateBean dataVo) {
            Activity activity = (Activity) context;
            int count = 2;
            if (isPad(context)) {
                count = 3;
            }
            int width= Utils.getWidth(activity) / count;
            int height = (Utils.getHeight(activity) - Utils.getTopHeight(activity)) / count;
            ViewGroup.LayoutParams layoutParams  = listitemMovieShowBinding.ivListitemMovieShow.getLayoutParams();
            layoutParams.width=width;
            layoutParams.height=height;
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .override(width,height);
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/" + dataVo.getPoster_path())
                    .apply(myOptions)
                    .into(listitemMovieShowBinding.ivListitemMovieShow);
        }


    }
}
