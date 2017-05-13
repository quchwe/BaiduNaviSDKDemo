package com.quchwe.cms.main_frame.MineFrag;

import android.content.Context;

import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.login_and_signup.register_frag.RegisterContract;

import butterknife.Bind;

/**
 * Created by YSten on 2017/4/6.
 */

public class MinePresenter implements MineContract.Presenter {

    private final DataRepository mDataRepository;
    private final Context mContext;
    private final MineContract.View mView;

    public MinePresenter(DataRepository mDataRepository, Context mContext, MineContract.View mView) {
        this.mDataRepository = mDataRepository;
        this.mContext = mContext;
        this.mView = mView;
        mView.setPresenter(this);

    }


    @Override
    public void setDefaultData() {
        String phone = (String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.MOBILE, "");
        String headImage = (String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.HEADIMGURL, "");
        String userName = (String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.USER_NAME, "");

        SysUser user = new SysUser();

        user.setUserName(userName);
        user.setHeadImage(headImage);
        user.setPhoneNumber(phone);

        mView.setDefaultData(user);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
