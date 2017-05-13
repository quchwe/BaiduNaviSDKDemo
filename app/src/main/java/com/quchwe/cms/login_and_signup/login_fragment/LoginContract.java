package com.quchwe.cms.login_and_signup.login_fragment;

import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

/**
 * Created by quchwe on 2017/4/5 0005.
 */

public interface LoginContract {
    interface Presenter extends IBasePresenter{
        void onLogin(String phoneNumber,String password);
    }
    interface View extends IBaseView<Presenter>{
        void success();
        void failed();
    }
}
