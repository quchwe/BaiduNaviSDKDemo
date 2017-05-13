package com.quchwe.cms.main_frame.MineFrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.bumptech.glide.Glide;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.login_and_signup.LoginAndSignUpActivity;
import com.quchwe.cms.myinfo.AboutUsActivity;
import com.quchwe.cms.myinfo.InfoMainFragment.InfoMainFragment;
import com.quchwe.cms.myinfo.MyInfoActivity;
import com.quchwe.cms.myinfo.my_car_frag.MyCarActivity;
import com.quchwe.cms.myinfo.my_repair.MyRepairActivity;
import com.quchwe.cms.util.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YSten on 2017/4/6.
 */

public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {
    public static final String TAG = "MineFrag-";

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.iv_headImage)
    ImageView headImage;
    @Bind(R.id.tv_userName)
    TextView userName;
    @Bind(R.id.tv_phone_number)
    TextView phoneNumber;
    @Bind(R.id.ll_self_info)
    LinearLayout selfInfoLinear;
    @Bind(R.id.tv_about_us)
    TextView aboutUsText;
    @Bind(R.id.textTitle)
    TextView title;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.tv_my_cars)
    TextView myCarsText;
    @Bind(R.id.tv_my_repair)
    TextView myRepair;

    @Bind(R.id.tv_exit)
    TextView exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        CURRENT_FRAGMENT = TAG;
        View root = inflater.inflate(R.layout.frag_mine, container, false);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    private void initView() {
        back.setVisibility(View.GONE);
        title.setText(getString(R.string.mine));
        mPresenter.setDefaultData();
    }


    @OnClick({R.id.tv_exit, R.id.ll_self_info, R.id.tv_about_us, R.id.tv_my_cars, R.id.tv_my_repair})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_self_info:
                startActivity(new Intent(getContext(), MyInfoActivity.class));
                break;
            case R.id.tv_my_cars:
                startActivity(new Intent(getContext(), MyCarActivity.class));
                break;
            case R.id.tv_my_repair:
                startActivity(new Intent(getContext(), MyRepairActivity.class));
                break;
            case R.id.tv_about_us:
                startActivity(new Intent(getContext(), AboutUsActivity.class));
                break;
            case R.id.tv_exit:
                Intent intent = new Intent(getContext(), LoginAndSignUpActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void setDefaultData(SysUser user) {
        if (!user.getUserName().equals("")) {
            userName.setText(user.getUserName());
        }
        if (!user.getPhoneNumber().equals("")) {
            phoneNumber.setText(user.getPhoneNumber());
        }
        if (!user.getHeadImage().equals("")) {
            Glide.with(getContext()).load(user.getHeadImage()).into(headImage);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        String imageUrl = (String) SharedPreferencesUtil.getData(getContext(), SharedPreferencesUtil.HEADIMGURL, "");
        if (!imageUrl.equals(""))
            Glide.with(getContext()).load(imageUrl).into(headImage);
    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
