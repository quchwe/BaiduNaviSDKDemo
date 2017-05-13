package com.quchwe.cms.main_frame;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.Constans;
import com.quchwe.cms.util.base.BaseActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarNavActivity extends BaseActivity {

    @Bind(R.id.textTitle)
    TextView title;

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.rv_text)
    ListView textRec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_nav);
        ButterKnife.bind(this);


        title.setText("汽车门户");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final List<String> strings = new ArrayList<>();

        strings.add("汽车之家");
        strings.add("太平洋汽车");
        strings.add("网上车市");
        strings.add("网易汽车");
        final Map<String,String> map = new HashMap<>();

        map.put(strings.get(0), Constans.CAR_HOME);
        map.put(strings.get(1),Constans.PC_CAR);
        map.put(strings.get(2),Constans.SECOND_CAR);
        map.put(strings.get(3),Constans.NETEASE_CAR);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings);

        textRec.setAdapter(stringArrayAdapter);

        textRec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CarNavActivity.this,WebActivity.class);
                intent.putExtra("url",map.get(strings.get(position)));
                startActivity(intent);
            }
        });


    }
}
