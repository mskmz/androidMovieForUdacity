package com.example.mskmz.androidmovieforudacity.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.example.mskmz.androidmovieforudacity.R;
import com.example.mskmz.androidmovieforudacity.databinding.ListitemDetailCommentShowBinding;
import com.example.mskmz.androidmovieforudacity.model.vo.ReviewsListVo;

import java.util.List;

/**
 * Created by wangzekang on 2017/10/4.
 */

public class MovieDetailCommentListAdapter extends BaseRecyclerViewAdapter<ReviewsListVo.DateBean> {
    private static final String TAG = "MovieShowListAdapter";


    public MovieDetailCommentListAdapter(Context context, List<ReviewsListVo.DateBean> postListVoList, ListItemClickListener listItemClickListener) {
        super(context, postListVoList, listItemClickListener);
    }

    @Override
    int setItemLayout() {
        return R.layout.listitem_detail_comment_show;
    }

    @Override
    BaseViewHolder setViewHolder(View view, Context context) {
        return new ViewHodler(view, context);
    }

    public class ViewHodler extends BaseViewHolder<ReviewsListVo.DateBean> {
        ListitemDetailCommentShowBinding listitemDetailCommentShowBinding;

        public ViewHodler(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        void findView(View itemView) {
            listitemDetailCommentShowBinding = DataBindingUtil.bind(itemView);
        }

        @Override
        void bind(final ReviewsListVo.DateBean dataVo) {
            listitemDetailCommentShowBinding.authorName.setText(dataVo.getAuthor()+":");
            listitemDetailCommentShowBinding.commentDetail.setText(dataVo.getContent());
        }


    }
}
