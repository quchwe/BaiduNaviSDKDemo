package com.quchwe.cms.myinfo;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.util.NormalUtil.NormalUtil;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.base.BaseActivity;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class CarInfoActivity extends BaseActivity {

    public static final String TAG = "carInfoActivity";

    @Bind(R.id.et_car_type)
    EditText etCarType;
    @Bind(R.id.et_category)
    EditText etCategory;
    @Bind(R.id.et_miles)
    EditText etMiles;
    @Bind(R.id.et_displacement)
    EditText etDisplacement;
    @Bind(R.id.et_speed)
    EditText etSpeed;
    @Bind(R.id.rv_images)
    RecyclerView imagesRecy;
    @Bind(R.id.et_origin)
    EditText etOrigin;
    @Bind(R.id.rl_car_id)
    RelativeLayout carIdRL;
    @Bind(R.id.et_phone_number)
    EditText etPhoneNumber;
    @Bind(R.id.rl_phoneNumber)
    RelativeLayout rlPhoneNumber;
    @Bind(R.id.et_car_id)
    EditText etCarId;
    @Bind(R.id.et_desc)
    EditText etDesc;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.textTitle)
    TextView title;
    Car car;
    @Bind(R.id.tv_more)
    TextView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        car = (Car) getIntent().getSerializableExtra("car");
        String intenString = getIntent().getStringExtra("intent");
        if (intenString != null && intenString.equals("add")) {
            rlPhoneNumber.setVisibility(View.VISIBLE);
            carIdRL.setVisibility(View.VISIBLE);
            title.setText("添加车辆");
            etCarId.setEnabled(true);
            String phoneNUmber = (String)SharedPreferencesUtil.getData(this,SharedPreferencesUtil.MOBILE,"");
            etPhoneNumber.setText(phoneNUmber);
            edit.setVisibility(View.VISIBLE);
            edit.setText("提交");
            setInfoCanEdit(true);


        }else {
            if (intenString!=null&&intenString.equals("qrcode")){
                rlPhoneNumber.setVisibility(View.VISIBLE);
                carIdRL.setVisibility(View.VISIBLE);
                title.setText("车辆信息");
                etCarId.setEnabled(false);

                etPhoneNumber.setText(car.getPhoneNumber());

                edit.setVisibility(View.GONE);
            }else {
                edit.setVisibility(View.VISIBLE);
                edit.setText(getString(R.string.modify));
            }
            title.setText(car.getCarId());
            etCarType.setText(car.getCarType());
            etCategory.setText(car.getCategory());
            etDisplacement.setText(car.getDisplacement());
            etDesc.setText(car.getDescription());
            etSpeed.setText(car.getSpeed());
            etOrigin.setText(car.getOrigin());
            etMiles.setText(car.getMiles());
            etPhoneNumber.setText(car.getPhoneNumber());
            etCarId.setText(car.getCarId());

        }



    }

    @OnClick({R.id.back, R.id.tv_more})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_more:
                if (edit.getText().toString().equals(getString(R.string.modify))) {
                    edit.setText("提交");
                    setInfoCanEdit(true);
                } else {
                    setInfoCanEdit(false);
                }
                break;
        }
    }

    private void setInfoCanEdit(boolean b) {
        if (b) {
            etDisplacement.setEnabled(true);
            etMiles.setEnabled(true);
            etCategory.setEnabled(true);
            etOrigin.setEnabled(true);
            etDesc.setEnabled(true);
            etCarType.setEnabled(true);
            etCarType.setEnabled(true);
            etSpeed.setEnabled(true);
        } else {
            Car car = new Car();
            car.setPhoneNumber(etPhoneNumber.getText().toString());
            car.setCarId(etCarId.getText().toString());
            if (!NormalUtil.isDrivingId(car.getCarId())){
                ToastUtil.showToast(this,"请输入正确的车牌号");
                return;
            }
            car.setCarType(etCarType.getText().toString());
            car.setCategory(etCategory.getText().toString());
            car.setDescription(etDesc.getText().toString());
            car.setMiles(etMiles.getText().toString());
            car.setSpeed(etSpeed.getText().toString());
            car.setDisplacement(etDisplacement.getText().toString());
            car.setProductionDate(new Date());

            DataRepository repository = DataRepository.instance(this);

            repository.updateCar(this, car).subscribe(new Subscriber<BaseBean<String>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: ", e);
                }

                @Override
                public void onNext(BaseBean<String> stringBaseBean) {
                    if (stringBaseBean.getErrCode() == BaseBean.SUCCESS_CODE) {
                        ToastUtil.showToast(CarInfoActivity.this, "修改，添加成功");
                        finish();
                    } else {
                        ToastUtil.showToast(CarInfoActivity.this, stringBaseBean.getErrMsg());
                    }
                }
            });
        }
    }

}
