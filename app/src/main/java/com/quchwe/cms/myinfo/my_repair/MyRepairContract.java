package com.quchwe.cms.myinfo.my_repair;

import com.quchwe.cms.myinfo.IInfoList;

import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

import java.util.List;

/**
 * Created by quchwe on 2017/5/7 0007.
 */

public interface MyRepairContract {

    interface Presenter extends IBasePresenter {

        void getRepairList();
    }

    interface View extends IBaseView<Presenter> {
//        void setRecycler(List<Car> cars);

        void setRecycler(List<IInfoList> repairs);

        void showError();
    }
}
