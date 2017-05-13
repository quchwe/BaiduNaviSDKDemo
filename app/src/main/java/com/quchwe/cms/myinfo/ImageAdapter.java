package com.quchwe.cms.myinfo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.navi.sdkdemo.R;
import com.bumptech.glide.Glide;
import com.quchwe.cms.util.PhotoViewActivity;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by quchwe on 2017/5/8 0008.
 */

public class ImageAdapter extends BaseRecyclerViewAdapter<String> {


    public ImageAdapter(@NonNull Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public BaseRecyclerViewAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, int position) {
        final String url = mDataList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;

        Glide.with(mContext).load(url).into(viewHolder.image);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoViewActivity.class);
                intent.putExtra(PhotoViewActivity.ImageUrl, url);
                mContext.startActivity(intent);
            }
        });

    }

    class ViewHolder extends BaseViewHolder {
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
