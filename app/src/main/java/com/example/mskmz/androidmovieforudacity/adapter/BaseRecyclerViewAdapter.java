package com.example.mskmz.androidmovieforudacity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangzekang on 2017/9/27.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {
    List<T> mList;
    Context context;
    BaseRecyclerViewAdapter.ListItemClickListener mListItemClickListener;
    List<String> mAdvReadHistoryList;

    @Override
    public BaseRecyclerViewAdapter.BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = setItemLayout();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        BaseRecyclerViewAdapter.BaseViewHolder viewHolder = setViewHolder(view, context);

        return viewHolder;
    }

    abstract int setItemLayout();

    abstract BaseViewHolder setViewHolder(View view, Context context);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    public BaseRecyclerViewAdapter(Context context, List<T> postListVoList, BaseRecyclerViewAdapter.ListItemClickListener listItemClickListener) {
        this.context = context;
        this.mList = postListVoList;
        mListItemClickListener = listItemClickListener;
    }

    public T getItem(int postion) {
        return mList.get(postion);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;

        public BaseViewHolder(View itemView, Context context) {
            super(itemView);
            findView(itemView);
            itemView.setOnClickListener(this);
            this.context = context;
        }

        abstract void findView(View itemView);

        abstract void bind(T dataVo);

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedPosition);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int postion);
    }
}
