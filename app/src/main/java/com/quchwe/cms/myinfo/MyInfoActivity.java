package com.quchwe.cms.myinfo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.main_frame.HomeFrag.HomeFragment;
import com.quchwe.cms.main_frame.RepaireFrg.RepairFragment;
import com.quchwe.cms.myinfo.InfoMainFragment.InfoMainContract;
import com.quchwe.cms.myinfo.InfoMainFragment.InfoMainFragment;
import com.quchwe.cms.myinfo.InfoMainFragment.InfoMainPresenter;
import com.quchwe.cms.myinfo.my_car_frag.MyCarContract;
import com.quchwe.cms.util.base.BaseActivity;
import com.quchwe.cms.util.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyInfoActivity extends BaseActivity {
    public static final String TAG = "myInfoActivity-";


    private InfoMainContract.View mInfoView;
    private InfoMainContract.Presenter mInfoPresenter;

    private MyCarContract.View myCarView;
    private MyCarContract.Presenter myCarPresenter;
    private DataRepository mDataRepository = DataRepository.instance(this);
    @Bind(R.id.frame)
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        ButterKnife.bind(this);

        initAllFragment();
        initAllPresenter();

        BaseFragment frag = (BaseFragment) mFragmentManager.findFragmentById(R.id.frame);

        if (frag == null) {
            frag = getmInfoView();
            mFragmentManager.beginTransaction().add(R.id.frame, frag, HomeFragment.TAG).commit();
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void initAllPresenter() {
        mInfoPresenter = new InfoMainPresenter(this,getmInfoView(),mDataRepository);
    }

    private void initAllFragment() {
        Fragment frag = null;
        frag = mFragmentManager.findFragmentByTag(InfoMainFragment.TAG);
        mInfoView = (frag == null) ? InfoMainFragment.newInstance() : (InfoMainFragment) frag;
    }


    public InfoMainFragment getmInfoView() {
        return (InfoMainFragment)mInfoView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode== RepairFragment.INPUT_FILE_REQUEST_CODE||requestCode==RepairFragment.SELECT_PHOTO){
            InfoMainFragment fragment = (InfoMainFragment)mFragmentManager.findFragmentByTag(InfoMainFragment.TAG);
            if (fragment==null){
                fragment = getmInfoView();
            }
            fragment.onActivityResult(requestCode,resultCode,data);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
