package com.quchwe.cms.myinfo.my_repair;

import android.content.Context;
import android.util.Log;

import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.myinfo.IInfoList;
import com.quchwe.cms.myinfo.my_car_frag.MyCarActivity;
import com.quchwe.cms.myinfo.my_car_frag.MyCarContract;

import java.util.List;

import rx.Subscriber;

/**
 * Created by quchwe on 2017/5/1 0001.
 */

public class MyRepairPresenter implements MyRepairContract.Presenter {

    private final Context mContext;
    private final DataRepository repository;
    private final MyRepairContract.View mView;


    public MyRepairPresenter(Context mContext, MyRepairContract.View mView) {
        this.mContext = mContext;
        this.repository = DataRepository.instance(mContext);
        this.mView = mView;
        mView.setPresenter(this);

    }

    @Override
    public void getRepairList() {
        repository.getRepair(mContext).subscribe(new Subscriber<BaseBean<List<IInfoList>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(MyCarActivity.TAG, "onError: ", e);
            }

            @Override
            public void onNext(BaseBean<List<IInfoList>> carBaseBean) {

                if (carBaseBean.getErrCode() == BaseBean.SUCCESS_CODE) {
                    mView.setRecycler(carBaseBean.getResultInfo());
                } else {
                    mView.showError();
                }

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
