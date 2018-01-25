package com.example.mskmz.androidmovieforudacity.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.view.View;

import com.example.mskmz.androidmovieforudacity.R;
import com.example.mskmz.androidmovieforudacity.databinding.ListitemDetailTrailerShowBinding;
import com.example.mskmz.androidmovieforudacity.model.vo.MoiveListVo;
import com.example.mskmz.androidmovieforudacity.model.vo.VideosListVo;
import com.example.mskmz.androidmovieforudacity.utils.Utils;

import java.util.List;

/**
 * Created by wangzekang on 2017/10/4.
 */

public class MovieDetailTrailerListAdapter extends BaseRecyclerViewAdapter<VideosListVo.DateBean> {
    private static final String TAG = "MovieShowListAdapter";


    public MovieDetailTrailerListAdapter(Context context, List<VideosListVo.DateBean> postListVoList, ListItemClickListener listItemClickListener) {
        super(context, postListVoList, listItemClickListener);
    }

    @Override
    int setItemLayout() {
        return R.layout.listitem_detail_trailer_show;
    }

    @Override
    BaseViewHolder setViewHolder(View view, Context context) {
        return new ViewHodler(view, context);
    }

    public class ViewHodler extends BaseViewHolder<VideosListVo.DateBean> {
        ListitemDetailTrailerShowBinding listitemDetailTrailerShowBinding;

        public ViewHodler(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        void findView(View itemView) {
            listitemDetailTrailerShowBinding = DataBindingUtil.bind(itemView);
        }

        @Override
        void bind(final VideosListVo.DateBean dataVo) {
            if(isStart(dataVo)){
                listitemDetailTrailerShowBinding.tvTotalTitle.setVisibility(View.VISIBLE);
            }else{
                listitemDetailTrailerShowBinding.tvTotalTitle.setVisibility(View.GONE);
            }
            listitemDetailTrailerShowBinding.tvTitle.setText(dataVo.getName());
            listitemDetailTrailerShowBinding.ivPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String type = "video/* ";
                    Uri uri = null;
                    switch (dataVo.getSite()) {
                        case "YouTube":
                            uri = Uri.parse("https://www.youtube.com/watch?v=" + dataVo.getKey() + "&feature=youtu.be");
                            break;
                    }
                    intent.setDataAndType(uri, type);
                    if (Utils.isIntentAvailable(context, intent)) {
                        context.startActivity(intent);
                    } else {
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
