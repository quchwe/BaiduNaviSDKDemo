package com.quchwe.cms.main_frame.HomeFrag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;



import com.baidu.navi.sdkdemo.BNDemoMainActivity;
import com.baidu.navi.sdkdemo.DemoApplication;
import com.baidu.navi.sdkdemo.LocationActivity;
import com.baidu.navi.sdkdemo.R;
import com.baidu.navi.sdkdemo.SurroundActivity;
import com.baidu.navi.sdkdemo.search.PoiSearchDemo;
import com.baidu.navi.sdkdemo.search.ShareDemo;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;


import com.quchwe.cms.Constans;
import com.quchwe.cms.main_frame.CarNavActivity;
import com.quchwe.cms.main_frame.MainFrameActivity;
import com.quchwe.cms.main_frame.RepaireFrg.RepairFragment;
import com.quchwe.cms.main_frame.WebActivity;
import com.quchwe.cms.util.NormalUtil.NormalUtil;
import com.quchwe.cms.util.NormalUtil.StringUtils;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.base.AppApplication;
import com.quchwe.cms.util.base.BaseFragment;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;
import com.quchwe.cms.util.compressor.Compressor;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YSten on 2017/4/6.
 */

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View, BaseSliderView.OnSliderClickListener,TencentLocationListener {


    public static final String TAG = "HomeFrag-";

    //    private LocationService locationService;
    private TencentLocationManager mLocationManager;
    private String mRequestParams;
    TencentLocationRequest request;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.rv_home_wy)
    RecyclerView homeTransRec;
    @Bind(R.id.slider)
    SliderLayout mSlider;
    @Bind(R.id.custom_indicator)
    PagerIndicator indicator;
    @Bind(R.id.tv_location)
    TextView LocationResult;
    @Bind(R.id.tv_location_edit)
    EditText LocationEdit;
    @Bind(R.id.tv_search)
    TextView search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        CURRENT_FRAGMENT = TAG;
        View root = inflater.inflate(R.layout.frag_home, container, false);
        ButterKnife.bind(this, root);
        initView();
        return root;

    }

    private void initView() {
        setHomeData();
        setHomeTransRec();
        mLocationManager = TencentLocationManager.getInstance(getContext());
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        startLocation();

//        LocationResult.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//                locationService.start();// 定位SDK
//                // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
//                Log.d(TAG, "onClick: start-location");
//
//            }
//        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmptyString(LocationEdit.getText().toString())) {

                    if (NormalUtil.isDrivingId(LocationEdit.getText().toString())) {
                        MainFrameActivity activity = (MainFrameActivity)getActivity();
                        activity.toCarInfo(LocationEdit.getText().toString());
                        return;
                    }
                    Intent intent = new Intent(getContext(),WebActivity.class);
                    intent.putExtra("url", "http://m.baidu.com/s?wd=汽车" + " " + LocationEdit.getText().toString());
                    startActivity(intent);
                } else {
                    ToastUtil.showToast(getContext(), "请输入搜索内容");
                }
            }
        });
    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public void setHomeData() {


        mSlider.removeAllSliders();
        DefaultSliderView textSliderView = new DefaultSliderView(getContext());
        textSliderView.image(R.mipmap.car2)
                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                .setOnSliderClickListener(this);

        DefaultSliderView textSliderView1 = new DefaultSliderView(getContext());
        textSliderView1.image(R.mipmap.car1)
                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                .setOnSliderClickListener(this);

        mSlider.addSlider(textSliderView);
        mSlider.addSlider(textSliderView1);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        //mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomIndicator(indicator);
//        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(3000);

    }

    private void setHomeTransRec() {


        List<HomeTransBean> list = new ArrayList<>();
        HomeTransBean bean = new HomeTransBean();
        bean.setImageId(R.mipmap.gps_nav);
        bean.setTitle("导航");

        HomeTransBean bean1 = new HomeTransBean();
        bean1.setImageId(R.mipmap.repair);
        bean1.setTitle("维修");

        HomeTransBean bean2 = new HomeTransBean();
        bean2.setImageId(R.mipmap.surround);
        bean2.setTitle("周边");

        HomeTransBean bean3 = new HomeTransBean();
        bean3.setImageId(R.mipmap.map);
        bean3.setTitle("地图");

        HomeTransBean bean4 = new HomeTransBean();
        bean4.setImageId(R.mipmap.car_nav);
        bean4.setTitle("车辆门户");

        HomeTransBean bean5 = new HomeTransBean();
        bean5.setImageId(R.mipmap.second);
        bean5.setTitle("二手市场");

        HomeTransBean bean6 = new HomeTransBean();
        bean6.setImageId(R.mipmap.illegal);
        bean6.setTitle("违章查询");

        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);



        HomeTransAdapter adapter = new HomeTransAdapter(getContext(), list);
        homeTransRec.setAdapter(adapter);
        homeTransRec.setLayoutManager(new GridLayoutManager(getContext(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        homeTransRec.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnclikListener(new BaseRecyclerViewAdapter.OnItemclickListener() {
            @Override
            public void onClick(View v, int position) {
                if (position == 0) {
                    startActivity(new Intent(getContext(), BNDemoMainActivity.class));

                }
                if (position == 1) {

                    MainFrameActivity activity = (MainFrameActivity) getActivity();
                    RepairFragment fragment = (RepairFragment) activity.getSupportFragmentManager().findFragmentByTag(RepairFragment.TAG);
                    if (fragment == null) {
                        fragment = activity.getmRepairView();
                    }
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, fragment, RepairFragment.TAG)
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
                                    R.anim.slide_in_left, R.anim.slide_out_left)
                            .addToBackStack(null)
                            .commit();
                }
                if (position == 2) {
                    startActivity(new Intent(getContext(), PoiSearchDemo.class));
                }
                if (position == 3) {
                    startActivity(new Intent(getContext(), SurroundActivity.class));
                }
                if (position == 4) {
                    startActivity(new Intent(getContext(), CarNavActivity.class));
                }if (position==5){
                    Intent intent = new Intent(getContext(),WebActivity.class);
                    intent.putExtra("url", Constans.SECOND_HOME);
                    startActivity(intent);
                }if (position==6){
                    Intent intent = new Intent(getContext(),WebActivity.class);
                    intent.putExtra("url", Constans.ILLEGAL_HOME);
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(View v, int position) {

            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        final String s = str;
        try {
            if (LocationResult != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LocationResult.post(new Runnable() {
                            @Override
                            public void run() {
                                LocationResult.setText(s);
                            }
                        });

                    }
                }).start();
            }
            //LocationResult.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
//        locationService.unregisterListener(mListener); //注销掉监听
//        locationService.stop(); //停止定位服务
        super.onStop();
        mSlider.stopAutoCycle();
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

////         -----------location config ------------
//        locationService = ((AppApplication) getActivity().getApplication()).locationService;
//        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
//        locationService.registerListener(mListener);
//        //注册监听
//        int type = getActivity().getIntent().getIntExtra("from", 0);
//        if (type == 0) {
//            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        } else if (type == 1) {
//            locationService.setLocationOption(locationService.getOption());
//        }
//        locationService.start();
        mSlider.startAutoCycle();
    }


//    private BDLocationListener mListener = new BDLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // TODO Auto-generated method stub
//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                StringBuffer sb = new StringBuffer(256);
//
//                sb.append(location.getCity());
//                sb.append(location.getDistrict());
//                sb.append(location.getStreet());
////                sb.append("time : ");
////                /**
////                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
////                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
////                 */
////                sb.append(location.getTime());
////                sb.append("\nlocType : ");// 定位类型
////                sb.append(location.getLocType());
////                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
////                sb.append(location.getLocTypeDescription());
////                sb.append("\nlatitude : ");// 纬度
////                sb.append(location.getLatitude());
////                sb.append("\nlontitude : ");// 经度
////                sb.append(location.getLongitude());
////                sb.append("\nradius : ");// 半径
////                sb.append(location.getRadius());
////                sb.append("\nCountryCode : ");// 国家码
////                sb.append(location.getCountryCode());
////                sb.append("\nCountry : ");// 国家名称
////                sb.append(location.getCountry());
////                sb.append("\ncitycode : ");// 城市编码
////                sb.append(location.getCityCode());
////                sb.append("\ncity : ");// 城市
////                sb.append(location.getCity());
////                sb.append("\nDistrict : ");// 区
////                sb.append(location.getDistrict());
////                sb.append("\nStreet : ");// 街道
////                sb.append(location.getStreet());
////                sb.append("\naddr : ");// 地址信息
////                sb.append(location.getAddrStr());
////                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
////                sb.append(location.getUserIndoorState());
////                sb.append("\nDirection(not all devices have value): ");
////                sb.append(location.getDirection());// 方向
////                sb.append("\nlocationdescribe: ");
////                sb.append(location.getLocationDescribe());// 位置语义化信息
////                sb.append("\nPoi: ");// POI信息
////                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
////                    for (int i = 0; i < location.getPoiList().size(); i++) {
////                        Poi poi = (Poi) location.getPoiList().get(i);
////                        sb.append(poi.getName() + ";");
////                    }
////                }
////                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
////                    sb.append("\nspeed : ");
////                    sb.append(location.getSpeed());// 速度 单位：km/h
////                    sb.append("\nsatellite : ");
////                    sb.append(location.getSatelliteNumber());// 卫星数目
////                    sb.append("\nheight : ");
////                    sb.append(location.getAltitude());// 海拔高度 单位：米
////                    sb.append("\ngps status : ");
////                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
////                    sb.append("\ndescribe : ");
////                    sb.append("gps定位成功");
////                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
////                    // 运营商信息
////                    if (location.hasAltitude()) {// *****如果有海拔高度*****
////                        sb.append("\nheight : ");
////                        sb.append(location.getAltitude());// 单位：米
////                    }
////                    sb.append("\noperationers : ");// 运营商信息
////                    sb.append(location.getOperators());
////                    sb.append("\ndescribe : ");
////                    sb.append("网络定位成功");
////                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
////                    sb.append("\ndescribe : ");
////                    sb.append("离线定位成功，离线定位结果也是有效的");
////                } else if (location.getLocType() == BDLocation.TypeServerError) {
////                    sb.append("\ndescribe : ");
////                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
////                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
////                    sb.append("\ndescribe : ");
////                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
////                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
////                    sb.append("\ndescribe : ");
////                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
////                }
//
//                Log.d(TAG, "onReceiveLocation: " + sb.toString());
//                logMsg(sb.toString());
////                ToastUtil.showToast(getContext(), sb.toString());
////                LocationResult.setText(sb.toString());
//                locationService.stop();
//            }
//        }
//
//        public void onConnectHotSpotMessage(String s, int i) {
//        }
//    };

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        System.err.println("定位失败" + i + s);
        if (i == TencentLocation.ERROR_OK) {

            // 定位成功
            StringBuilder sb = new StringBuilder();
            sb.append("定位参数=").append(mRequestParams).append("\n");
            sb.append("(纬度=").append(tencentLocation.getLatitude()).append(",经度=")
                    .append(tencentLocation.getLongitude()).append(",精度=")
                    .append(tencentLocation.getAccuracy()).append("), 来源=")
                    .append(tencentLocation.getProvider()).append(", 地址=")
                    .append(tencentLocation.getAddress());

            String locat = tencentLocation.getCity();
            if (locat.length() > 0)
                LocationResult.setText(tencentLocation.getCity().substring(0, locat.length() - 1));
            else {
                LocationResult.setText(tencentLocation.getCity());
            }


            //Toast.makeText(mContext,sb.toString(),Toast.LENGTH_SHORT).show();
            Log.d("地址", "onLocationChanged: " + sb.toString());
            // 更新 location Marker
            // stopLocation();

        } else {
            LocationResult.setText("定位失败");
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocation();
    }

    private void startLocation() {
        request = TencentLocationRequest.create();
        request.setInterval(8000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        int error = mLocationManager.requestLocationUpdates(request, this);

        mRequestParams = request.toString() + ", 坐标系="
                + toString(mLocationManager.getCoordinateType());

        Log.e("tag", "startLocation: " + mRequestParams + error);
        // location.setText(tencentLocation.getCity_id());

    }

    private void stopLocation() {
        mLocationManager.removeUpdates(this);
    }

    public static String toString(int coordinateType) {
        if (coordinateType == TencentLocationManager.COORDINATE_TYPE_GCJ02) {
            return "国测局坐标(火星坐标)";
        } else if (coordinateType == TencentLocationManager.COORDINATE_TYPE_WGS84) {
            return "WGS84坐标(GPS坐标, 地球坐标)";
        } else {
            return "非法坐标";
        }
    }
}
