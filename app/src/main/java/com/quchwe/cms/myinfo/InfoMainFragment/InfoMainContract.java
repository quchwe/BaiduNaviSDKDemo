package com.quchwe.cms.myinfo.InfoMainFragment;

import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

/**
 * Created by quchwe on 2017/4/12 0012.
 */

public interface InfoMainContract {
    interface Presenter extends IBasePresenter {
        void setSexDialog();

        void setDefaultData();

        void updateUserInfo(SysUser user);
    }

    interface View extends IBaseView<Presenter> {

        void setDefaultData(SysUser user);

        void setSexString(String s);

        void setSexDialog(SelectDialogItemAdapter adapter);
    }
}
