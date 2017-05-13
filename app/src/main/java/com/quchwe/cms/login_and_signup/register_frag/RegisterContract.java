package com.quchwe.cms.login_and_signup.register_frag;

import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

/**
 * Created by quchwe on 2017/4/5 0005.
 */

public interface RegisterContract {
    interface Presenter extends IBasePresenter{
        void onRegister(SysUser user);
    }

    interface View extends IBaseView<Presenter>{
        void success();
        void failed(String errMsg);
    }
}
