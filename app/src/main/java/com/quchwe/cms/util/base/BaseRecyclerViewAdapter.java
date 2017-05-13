package com.quchwe.cms.util.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by quchwe on 2016/11/13 0013.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {

    protected final Context mContext;
    protected final List<T> mDataList;
    protected OnItemclickListener mListener;

    public BaseRecyclerViewAdapter(@NonNull Context context, List<T> list) {
        this.mContext = context;
        this.mDataList = list;

    }

    @Override
    public abstract BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int position);

    public void setOnclikListener(OnItemclickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    public interface OnItemclickListener {
        void onClick(View v, int position);

        void onLongClick(View v, int position);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
