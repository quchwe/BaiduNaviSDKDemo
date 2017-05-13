package com.quchwe.cms.myinfo.my_repair;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.beans.RepairInfo;
import com.quchwe.cms.main_frame.InfoAdapter;
import com.quchwe.cms.myinfo.CarInfoActivity;
import com.quchwe.cms.myinfo.IInfoList;

import com.quchwe.cms.myinfo.RepairInfoActivity;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.base.BaseActivity;

import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by quchwe on 2017/5/1 0001.
 */

public class MyRepairActivity extends BaseActivity implements MyRepairContract.View {

    public static final String TAG = "MyRepair-";

    @Bind(R.id.textTitle)
    TextView title;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.rv_my_car_list)
    RecyclerView carListRv;

    private MyRepairContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_my_car);

        ButterKnife.bind(this);
        initView();

    }


    private void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("我的报修");

        setPresenter(new MyRepairPresenter(MyRepairActivity.this, this));
        mPresenter.getRepairList();

    }

    @Override
    public void setPresenter(MyRepairContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setRecycler(final List<IInfoList> cars) {
        InfoAdapter adapter = new InfoAdapter(this, cars);
        carListRv.setLayoutManager(new LinearLayoutManager(this));
        carListRv.setAdapter(adapter);
        adapter.setOnclikListener(new BaseRecyclerViewAdapter.OnItemclickListener() {
            @Override
            public void onClick(View v, int position) {

                RepairInfo car = (RepairInfo) cars.get(position);

                Intent intent = new Intent(MyRepairActivity.this, RepairInfoActivity.class);
                intent.putExtra("car", car);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View v, int position) {

            }
        });
    }

    @Override
    public void showError() {
        ToastUtil.showToast(this, "获取数据失败");
    }
}
