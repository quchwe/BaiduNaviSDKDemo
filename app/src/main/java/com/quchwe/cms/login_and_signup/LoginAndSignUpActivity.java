package com.quchwe.cms.login_and_signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.quchwe.cms.login_and_signup.login_fragment.LoginContract;
import com.quchwe.cms.login_and_signup.login_fragment.LoginFragment;
import com.quchwe.cms.login_and_signup.login_fragment.LoginPresenter;
import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.login_and_signup.register_frag.RegisterContract;
import com.quchwe.cms.login_and_signup.register_frag.RegisterFragment;
import com.quchwe.cms.login_and_signup.register_frag.RegisterPresenter;
import com.quchwe.cms.util.base.BaseActivity;
import com.quchwe.cms.util.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YSten on 2017/3/3.
 */

public class LoginAndSignUpActivity extends BaseActivity {


    public static final String TAG = "LoginAndSign-";
    private final DataRepository mDataResposity = DataRepository.instance(this);
    private LoginContract.View mLoginView;
    private LoginContract.Presenter mLoginPresenter;
    private RegisterContract.View mRegisterView;
    private RegisterContract.Presenter mRegisterPresenter;

    @Bind(R.id.frame)
    FrameLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        initAllFragment();
        initAllPresenter();

        BaseFragment frag = (BaseFragment) mFragmentManager.findFragmentById(R.id.frame);

        if (frag == null) {
            frag = getmLoginView();
            mFragmentManager.beginTransaction().add(R.id.frame, frag, LoginFragment.TAG).commit();
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

    }

    private void initAllPresenter() {
        mLoginPresenter = new LoginPresenter(mDataResposity,this,getmLoginView());
        mRegisterPresenter = new RegisterPresenter(mDataResposity,this,getmRegisterView());
    }

    private void initAllFragment() {
        Fragment frag = null;
        frag = mFragmentManager.findFragmentByTag(LoginFragment.TAG);
        mLoginView = (frag == null) ? LoginFragment.newInstance() : (LoginFragment) frag;
        frag = mFragmentManager.findFragmentByTag(RegisterFragment.TAG);
        mRegisterView = (frag ==null)?RegisterFragment.newInstance():(RegisterFragment)frag;

    }

    public LoginFragment getmLoginView() {
        return (LoginFragment) mLoginView;
    }

    public RegisterFragment getmRegisterView(){
        return (RegisterFragment)mRegisterView;
    }

}
