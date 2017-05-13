package com.quchwe.cms.data;

import android.content.Context;


import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.data.beans.RepairInfo;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.data.local.LocalSource;

import com.quchwe.cms.data.remote.RemoteSource;
import com.quchwe.cms.myinfo.IInfoList;

import java.io.File;
import java.util.List;

import rx.Observable;

/**
 * Created by quchwe on 2017/4/5 0005.
 */

public class DataRepository {
    private final Context mContext;
    private static DataRepository mDataRepository;
    private final LocalSource mLocalSource;
    private final RemoteSource mRemoteSource;

    private DataRepository(Context context) {
        this.mContext = context;
        this.mLocalSource = LocalSource.instance(context);
        this.mRemoteSource = RemoteSource.instance(context);
    }

    public static DataRepository instance(Context context) {
        if (mDataRepository == null) {
            mDataRepository = new DataRepository(context);
        }
        return mDataRepository;
    }

    public Observable<BaseBean<SysUser>> login(SysUser user) {
        return mRemoteSource.login(user);
    }

    public Observable<BaseBean<SysUser>> register(SysUser user) {
        return mRemoteSource.register(user);
    }

    public Observable<BaseBean<String>> createRepair(String phoneNumber, String type, String drVid, String description, File... files) {
        return mRemoteSource.createRepair(phoneNumber, type, description, drVid, files);
    }

    public Observable<BaseBean<SysUser>> updateUserInfo(SysUser user, File headImage) {
        return mRemoteSource.updateUserInfo(user, headImage);
    }

    public Observable<BaseBean<List<IInfoList>>> getMyCars(Context mContext, String carId, int i) {
        return mRemoteSource.getMyCars(mContext,carId,i);
    }

    public Observable<BaseBean<String>> updateCar(Context mContext, Car car) {
        return mRemoteSource.updateCar(mContext, car);
    }

    public Observable<BaseBean<List<IInfoList>>> getRepair(Context mContext) {
        return mRemoteSource.getRepair(mContext);
    }
}
