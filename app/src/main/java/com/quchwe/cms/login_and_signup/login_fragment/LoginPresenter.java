package com.quchwe.cms.login_and_signup.login_fragment;

import android.content.Context;

import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.data.beans.BaseBean;

import rx.Subscriber;

/**
 * Created by quchwe on 2017/4/5 0005.
 */

public class LoginPresenter implements LoginContract.Presenter {


    private final DataRepository mDataRepository;
    private final Context mContext;
    private final LoginContract.View mView;


    public LoginPresenter(DataRepository mDataRepository, Context mContext, LoginContract.View mView) {
        this.mDataRepository = mDataRepository;
        this.mContext = mContext;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onLogin(final String phoneNumber, final String password) {
        SysUser user = new SysUser();
        user.setPhoneNumber(phoneNumber);
        user.setUserPassword(password);
        mDataRepository.login(user).subscribe(new Subscriber<BaseBean<SysUser>>() {
            @Override
            public void onCompleted() {
                System.out.printf("oncompleted");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.failed();
            }

            @Override
            public void onNext(BaseBean<SysUser> baseBean) {
                if (baseBean.getErrCode() == BaseBean.SUCCESS_CODE) {

                    SysUser user = baseBean.getResultInfo();
                    SharedPreferencesUtil.saveUserInfo(mContext, user);
                    mView.success();
                } else {
                    mView.failed();
                }
                System.out.println(baseBean.getErrMsg());

            }
        });

    }
}
