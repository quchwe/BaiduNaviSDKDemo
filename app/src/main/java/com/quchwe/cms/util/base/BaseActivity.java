package com.quchwe.cms.util.base;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.util.NormalUtil.StatusBarUtils;


/**
 * Created by quchwe on 2016/9/7 0007.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected FragmentManager mFragmentManager;
    public static String CASH_FILE = null;
    public static final int REQUEST_CODE_CAMERA = 4;
    public static final int REQUEST_CODE_SDCARD = 5;
    public static final int REQUEST_CODE_LOCATION = 6;
    public static final int REQUEST_CODE_CALL = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (CASH_FILE == null) {
            CASH_FILE = getExternalCacheDir().toString();
        }
        StatusBarUtils.setWindowStatusBarColor(this, R.color.status);
        mFragmentManager = getSupportFragmentManager();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

    }

}
