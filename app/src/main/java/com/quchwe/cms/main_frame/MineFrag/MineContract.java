package com.quchwe.cms.main_frame.MineFrag;

import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

/**
 * Created by YSten on 2017/4/6.
 */

public interface MineContract {
    interface Presenter extends IBasePresenter{

        void setDefaultData();
    }
    interface View extends IBaseView<Presenter>{

        void setDefaultData(SysUser user);
    }
}
