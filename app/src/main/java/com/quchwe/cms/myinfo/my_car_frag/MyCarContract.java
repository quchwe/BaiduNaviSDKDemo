package com.quchwe.cms.myinfo.my_car_frag;

import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.myinfo.IInfoList;
import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

import java.util.List;

/**
 * Created by quchwe on 2017/5/1 0001.
 */

public interface MyCarContract {
    interface Presenter extends IBasePresenter {

        void getCarList();
    }

    interface View extends IBaseView<Presenter> {
//        void setRecycler(List<Car> cars);

        void setRecycler(List<IInfoList> cars);

        void showError();
    }

}
