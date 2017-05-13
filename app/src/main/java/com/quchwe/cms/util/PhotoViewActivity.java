package com.quchwe.cms.util;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.bumptech.glide.Glide;
import com.quchwe.cms.util.base.BaseActivity;
import com.quchwe.cms.util.photoview.PhotoView;


import java.util.ArrayList;

/**
 * Created by quchwe on 2017/1/3 0003.
 */

public class PhotoViewActivity extends BaseActivity {
    public static final String ImageUrl = "imageUrl";
    private String urls =null;

    TextView title;
    LinearLayout back;
    PhotoView myPhoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        title = (TextView)findViewById(R.id.textTitle);
        back = (LinearLayout) findViewById(R.id.back);

        title.setText("预览");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        urls = getIntent().getStringExtra(ImageUrl);
        myPhoto = (PhotoView)findViewById(R.id.iv_photo);
        //Glide.with(this).load("http://himg.bdimg.com/sys/portrait/item/9ee6696465615f776a1718.jpg").into(myPhoto);
        if (urls!=null&&!urls.equals("")){

            Glide.with(this).load(urls).into(myPhoto);
        }
    }
}
