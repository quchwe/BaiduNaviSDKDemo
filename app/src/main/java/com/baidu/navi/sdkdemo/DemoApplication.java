package com.baidu.navi.sdkdemo;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;


import com.squareup.leakcanary.LeakCanary;

/**
 * Created by dingbbin on 2017/2/8.
 */

public class DemoApplication extends Application {

//    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
//        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

    }
}
