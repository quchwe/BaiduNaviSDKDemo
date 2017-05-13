package com.quchwe.cms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.login_and_signup.LoginAndSignUpActivity;
import com.quchwe.cms.main_frame.MainFrameActivity;
import com.quchwe.cms.util.base.BaseActivity;


import java.util.TimerTask;

public class StartUpActivity extends BaseActivity {

    public static final String TAG = "StartUpActivity-";
    private final int SPLASH_DISPLAY_LENGHT = 2000; //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new TimerTask() {
            @Override
            public void run() {
                if (isLogin()) {
                    startActivity(new Intent(StartUpActivity.this, MainFrameActivity.class));
                }else {
                    startActivity(new Intent(StartUpActivity.this, LoginAndSignUpActivity.class));
                }
                finish();
            }
        }, 700);

    }


    public boolean isLogin(){
        String phoneNumber = (String)SharedPreferencesUtil.getData(this,SharedPreferencesUtil.MOBILE,"");
        String password = (String)SharedPreferencesUtil.getData(this,SharedPreferencesUtil.PASSWORD_EDIT,"");

        if (!("").equals(phoneNumber)&&!("").equals(password)){
            return true;
        }
        return false;
    }


}
