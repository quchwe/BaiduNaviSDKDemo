package com.quchwe.cms.util.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by quchwe on 2016/9/7 0007.
 */

public class BaseFragment<T extends IBasePresenter> extends Fragment {

    public static final String TAG = "BaseFrament";
    protected BaseActivity mActivity;
    protected Context mContext;
    protected T mPresenter;
    //判断当前显示的fragment
    public static String CURRENT_FRAGMENT = "";
    protected Bundle saveState = null;

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
        onSaveState();
        ButterKnife.unbind(this);
        //CURRENT_FRAGMENT = "";

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        mContext = mActivity;
        // Restore State Here

    }

    protected boolean onFirstTimeLaunched() {
        return saveState == null;
    }

    /**
     * 在destroyView时保存数据，
     */
    protected void onSaveState() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Here

    }


}
