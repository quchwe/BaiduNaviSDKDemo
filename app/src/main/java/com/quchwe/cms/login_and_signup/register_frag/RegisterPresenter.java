package com.quchwe.cms.login_and_signup.register_frag;

import android.content.Context;

import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.data.beans.SysUser;

import rx.Subscriber;


/**
 * Created by YSten on 2017/4/5.
 */

public class RegisterPresenter implements RegisterContract.Presenter{

    private final DataRepository mDataRepository;
    private final Context mContext;
    private final RegisterContract.View mView;

    public RegisterPresenter(DataRepository mDataRepository, Context mContext, RegisterContract.View mView) {
        this.mDataRepository = mDataRepository;
        this.mContext = mContext;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void onRegister(SysUser user) {
        mDataRepository.register(user)
                .subscribe(new Subscriber<BaseBean<SysUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean<SysUser> stringBaseBean) {
                        if (stringBaseBean.getErrCode()==BaseBean.SUCCESS_CODE){
                            SharedPreferencesUtil.saveUserInfo(mContext,stringBaseBean.getResultInfo());
                            mView.success();
                        }
                        else {
                            mView.failed(stringBaseBean.getErrMsg());
                        }
                        System.out.println();
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
