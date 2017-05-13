package com.quchwe.cms.main_frame;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.base.BaseActivity;
import com.quchwe.cms.util.webviewutil.TbsWebView;

import com.quchwe.cms.util.webviewutil.WebViewChromeClientUtil;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WebActivity extends BaseActivity {


    @Bind(R.id.ll_web)
    LinearLayout webBaseLayout;
    @Bind(R.id.myProgressBar)
    ProgressBar bar;
    @Bind(R.id.textTitle)
    TextView title;
    @Bind(R.id.bw_webView)
    TbsWebView mWebView;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.rl_netError)
    RelativeLayout netErrorLayout;

    @Bind(R.id.tv_more)
    TextView exit;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        url = getIntent().getStringExtra("url");

        exit.setVisibility(View.VISIBLE);
        exit.setText("退出");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (url != null && !url.equals("")) {
            mWebView.loadUrl(url);
        } else ToastUtil.showToast(this, "未知错误");
        if (netErrorLayout.getVisibility() == View.VISIBLE) {
            netErrorLayout.setVisibility(View.GONE);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        mWebView.setWebChromeClient(new WebViewChromeClientUtil(bar) {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (webView != null && s != null && title != null) {
                    title.setText(s);
                }
            }

        });

    }
}
