package com.quchwe.cms.myinfo.my_car_frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.main_frame.InfoAdapter;
import com.quchwe.cms.myinfo.CarInfoActivity;
import com.quchwe.cms.myinfo.IInfoList;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.base.BaseActivity;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by quchwe on 2017/5/1 0001.
 */

public class MyCarActivity extends BaseActivity implements MyCarContract.View{

    public static final String TAG = "MyCarFrag-";

    @Bind(R.id.textTitle)
    TextView title;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.rv_my_car_list)
    RecyclerView carListRv;
    @Bind(R.id.tv_more)
    TextView add;

    private MyCarContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.frag_my_car);
        ButterKnife.bind(this);
        setPresenter(new MyCarPresenter(this, DataRepository.instance(this),this));
        initView();
    }



    private void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        add.setText(getString(R.string.add));
        title.setText(getString(R.string.my_cars));
        add.setVisibility(View.VISIBLE);
        mPresenter.getCarList();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCarActivity.this, CarInfoActivity.class);
                intent.putExtra("intent","add");
                startActivity(intent);
            }
        });


    }


    @Override
    public void setRecycler(final List<IInfoList> cars) {

        InfoAdapter adapter = new InfoAdapter(this,cars);
        carListRv.setLayoutManager(new LinearLayoutManager(this));
        carListRv.setAdapter(adapter);
        adapter.setOnclikListener(new BaseRecyclerViewAdapter.OnItemclickListener() {
            @Override
            public void onClick(View v, int position) {

                Car car = (Car)cars.get(position);

                Intent intent = new Intent(MyCarActivity.this, com.quchwe.cms.myinfo.CarInfoActivity.class);
                intent.putExtra("car",car);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View v, int position) {

            }
        });
    }

    @Override
    public void showError() {
        ToastUtil.showToast(this,"获取数据失败");
    }

    @Override
    public void setPresenter(MyCarContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
