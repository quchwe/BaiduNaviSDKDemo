package com.quchwe.cms.main_frame;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.data.beans.RepairInfo;
import com.quchwe.cms.myinfo.IInfoList;
import com.quchwe.cms.util.NormalUtil.StringUtils;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by quchwe on 2017/5/1 0001.
 */

public class InfoAdapter extends BaseRecyclerViewAdapter<IInfoList> {

    public InfoAdapter(@NonNull Context context, List<IInfoList> list) {
        super(context, list);
    }

    @Override
    public BaseRecyclerViewAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_car_or_repair, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        IInfoList info = mDataList.get(position);


        viewHolder.tvId.setText("车牌号：" + info.getCarId());


        if (info instanceof Car) {

            viewHolder.createTime.setText(((Car) info).getProductionDate().toString());
        } else if (info.getCreateTime() != null) {
            viewHolder.createTime.setText(info.getCreateTime().toString());

        }
        if (StringUtils.isEmptyString(info.getDescription())) {
            viewHolder.desc.setText("描述：无");
        } else viewHolder.desc.setText("描述：" + info.getDescription());


        if (mListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, position);
                }
            });
        }

    }

    class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {
        TextView tvId;
        TextView createTime;
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_info_id);
            createTime = (TextView) itemView.findViewById(R.id.tv_create_time);
            desc = (TextView) itemView.findViewById(R.id.tv_desc);
        }

    }
}
