package com.quchwe.cms.main_frame.HomeFrag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.util.NormalUtil.CircleImage;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by quchwe on 2017/4/7 0007.
 */

public class HomeTransAdapter extends BaseRecyclerViewAdapter<HomeTransBean> {


    public HomeTransAdapter(@NonNull Context context, List<HomeTransBean> list) {
        super(context, list);
    }

    @Override
    public BaseRecyclerViewAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_trans, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, final int position) {
        HomeTransBean bean = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
       Glide.with(mContext).load(bean.getImageId()).into(viewHolder.circleImage);

//        Glide.with(mContext).load("http://192.168.43.8:8443/image/get").into(viewHolder.circleImage);
        viewHolder.text.setText(bean.getTitle());

        if (mListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v,position);
                }
            });
        }
    }

    public class ViewHolder extends BaseViewHolder {
        public TextView text;
        public ImageView circleImage;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImage = (ImageView) itemView.findViewById(R.id.iv_wy);
            text = (TextView) itemView.findViewById(R.id.tv_wy);

        }
    }
}
