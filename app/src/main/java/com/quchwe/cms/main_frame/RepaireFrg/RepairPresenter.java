package com.quchwe.cms.main_frame.RepaireFrg;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.util.NormalUtil.FileUtils;
import com.quchwe.cms.util.compressor.Compressor;
import com.quchwe.cms.util.net.SchedulerProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by quchwe on 2017/4/10 0010.
 */

public class RepairPresenter implements RepaireContract.Presenter {

    private final Context mContext;
    private final RepaireContract.View mView;
    private final DataRepository mDataRepository;
    CompositeSubscription mSubs;

    public RepairPresenter(Context mContext, RepaireContract.View mView, DataRepository mDataRepository) {
        this.mContext = mContext;
        this.mView = mView;
        this.mDataRepository = mDataRepository;
        this.mView.setPresenter(this);
        mSubs = new CompositeSubscription();
    }


    @Override
    public void setDefaultData() {
        String phoneNumber = (String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.MOBILE, "");
        String drivingId = (String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.DRIVING_ID, "");

        mView.setDefaultData(phoneNumber, drivingId);
    }

    @Override
    public void createRepair(final String type, final String desc, final String drvId, final File... files) {
        final String phoneNumber = (String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.MOBILE, "");


       FileUtils.compressFiles(mContext,files).subscribe(new Subscriber<File[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(File[] files) {

                mDataRepository.createRepair(phoneNumber, type, drvId, desc, files).subscribe(new Subscriber<BaseBean<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showFailed("提交失败");
                    }

                    @Override
                    public void onNext(BaseBean<String> stringBaseBean) {
                        if (stringBaseBean.getErrCode() == BaseBean.SUCCESS_CODE) {
                            mView.showSuccess();
                        } else mView.showFailed(stringBaseBean.getErrMsg());
                    }
                });


            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubs.clear();
    }


}
