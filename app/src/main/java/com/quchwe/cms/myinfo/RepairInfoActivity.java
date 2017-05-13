package com.quchwe.cms.myinfo;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.beans.RepairInfo;
import com.quchwe.cms.util.NormalUtil.StringUtils;
import com.quchwe.cms.util.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepairInfoActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.textTitle)
    TextView title;

    @Bind(R.id.et_car_id)
    EditText etCarId;
    @Bind(R.id.et_accidentType)
    EditText etAccidentType;
    @Bind(R.id.et_create_time)
    EditText etCreateTime;
    @Bind(R.id.et_progress)
    EditText etProgress;
    @Bind(R.id.et_desc)
    EditText etDesc;
    @Bind(R.id.rv_images)
    RecyclerView imageRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_info);

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
        title.setText("维修信息");

        RepairInfo info = (RepairInfo) getIntent().getSerializableExtra("car");

        etAccidentType.setText(info.getAccidentType());
        etCarId.setText(info.getCarId());
        etCreateTime.setText(info.getCreateTime().toString());
        etDesc.setText(info.getDescription());
        etProgress.setText(info.getRepairProgress());

        if (!StringUtils.isEmptyString(info.getFilePath())) {
            String[] i = info.getFilePath().split(",");
            List<String> images = Arrays.asList(i);
            if (images != null) {
                ImageAdapter adapter = new ImageAdapter(this, images);
                imageRec.setLayoutManager(new GridLayoutManager(this, 3));
                imageRec.setItemAnimator(new DefaultItemAnimator());

                imageRec.setAdapter(adapter);
            }
        }


    }
}
