package com.quchwe.cms.myinfo.InfoMainFragment;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.util.NormalUtil.FileUtils;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by quchwe on 2017/4/12 0012.
 */

public class InfoMainPresenter implements InfoMainContract.Presenter {

    private final Context mContext;
    private final InfoMainContract.View mView;
    private final DataRepository mDataRepository;
    CompositeSubscription mSubs;


    public InfoMainPresenter(Context mContext, InfoMainContract.View mView, DataRepository mDataRepository) {
        this.mContext = mContext;
        this.mView = mView;
        this.mDataRepository = mDataRepository;
        mSubs = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void setSexDialog() {
        final List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        SelectDialogItemAdapter adapter = new SelectDialogItemAdapter(mContext, sexList);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();

        dialog.show();
        Window window = dialog.getWindow();

        window.setContentView(R.layout.dialog_choose_sex);
        TextView t = (TextView) window.findViewById(R.id.tv_dialog_title);
        t.setText(mContext.getString(R.string.sex));
        final RecyclerView numberList = (RecyclerView) window.findViewById(R.id.rv_number_list);
        numberList.setLayoutManager(new LinearLayoutManager(mContext));
        numberList.setItemAnimator(new DefaultItemAnimator());
        numberList.setAdapter(adapter);
        adapter.setOnclikListener(new BaseRecyclerViewAdapter.OnItemclickListener() {
            @Override
            public void onClick(View v, int position) {
                mView.setSexString(sexList.get(position));
                dialog.dismiss();
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        });
    }

    @Override
    public void setDefaultData() {

        mView.setDefaultData(SharedPreferencesUtil.getUserInfo(mContext));
    }

    @Override
    public void updateUserInfo(final SysUser user) {
        File f = null;
        if (user.getHeadImage() != null) {
            f = new File(user.getHeadImage());
        }

        FileUtils.compressFiles(mContext, f).subscribe(new Subscriber<File[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(File[] files) {
                mDataRepository.updateUserInfo(user, files[0]).subscribe(new Subscriber<BaseBean<SysUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(BaseBean<SysUser> sysUserBaseBean) {
                        if (sysUserBaseBean.getErrCode() == BaseBean.SUCCESS_CODE) {
                            SharedPreferencesUtil.saveUserInfo(mContext, sysUserBaseBean.getResultInfo());
                        }
                    }
                });
            }
        });
    }
}
