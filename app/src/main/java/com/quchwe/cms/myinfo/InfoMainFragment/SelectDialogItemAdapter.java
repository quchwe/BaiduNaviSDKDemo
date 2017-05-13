package com.quchwe.cms.myinfo.InfoMainFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by quchwe on 2017/2/7 0007.
 */

public class SelectDialogItemAdapter extends BaseRecyclerViewAdapter<String> {



    public SelectDialogItemAdapter(@NonNull Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_radio_select_dialog,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        String bean = mDataList.get(position);
        viewHolder.number.setText(bean);
        //viewHolder.isChecked.setChecked(bean.isSelect());

        if (mListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.isChecked.setChecked(true);
                    mListener.onClick(v,position);
                }
            });
        }

    }

    class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder{
            TextView number;
            RadioButton isChecked;
        public ViewHolder(View itemView) {
            super(itemView);
            number = (TextView)itemView.findViewById(R.id.text);
            isChecked = (RadioButton)itemView.findViewById(R.id.radio);
        }
    }
}
