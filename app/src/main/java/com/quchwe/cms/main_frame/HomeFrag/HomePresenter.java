package com.quchwe.cms.main_frame.HomeFrag;

import android.content.Context;

import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.login_and_signup.register_frag.RegisterContract;

import java.io.File;

import rx.Subscriber;

/**
 * Created by YSten on 2017/4/6.
 */

public class HomePresenter implements HomeContract.Presenter {

    private final DataRepository mDataRepository;
    private final Context mContext;
    private final HomeContract.View mView;


    public HomePresenter(DataRepository mDataRepository, Context mContext, HomeContract.View mView) {
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
    public void upload(String type,String desc,File ... files) {

//        mDataRepository.createRepair("18014923897",type,desc,files).subscribe(new Subscriber<BaseBean<String>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(BaseBean<String> stringBaseBean) {
//                System.out.println(stringBaseBean.getErrMsg());
//            }
//        });
    }
}
