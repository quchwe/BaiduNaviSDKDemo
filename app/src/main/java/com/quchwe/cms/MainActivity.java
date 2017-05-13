package com.quchwe.cms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.navi.sdkdemo.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);
        ButterKnife.bind(this);


    }
}
