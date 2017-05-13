package com.quchwe.cms.util.webviewutil;//package com.quchwe.cms.util.webviewutil;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

/**
 * Created by quchwe on 2017/3/30 0030.
 */

public class TbsWebView extends WebView {
    public TbsWebView(Context context) {
        super(context);
        init();
    }

    public TbsWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TbsWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public TbsWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        init();
    }

    public TbsWebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        init();
    }
    private void init() {
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setAllowUniversalAccessFromFileURLs(true);
        this.getSettings().setAllowFileAccess(true);
        //this.getSettings().setAllowFileAccessFromFileURLs(true);
        this.getSettings().setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        this.getSettings().setBuiltInZoomControls(true);
        //this.getSettings().setAllowFileAccess(true);
        this.requestFocusFromTouch();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            this.getSettings().setDisplayZoomControls(false);
        }

        //this.addJavascriptInterface(this, "AndroidFunction");
        // Enable remote debugging via chrome://inspect
        //加快HTML网页加载完成速度（默认情况html代码下载到WebView后，webkit开始解析网页各个节点，
        // 发现有外部样式文件或者外部脚本文件时，会异步发起网络请求下载文件，但如果在这之前也有解析到image节点，
        // 那势必也会发起网络请求下载相应的图片。在网络情况较差的情况下，过多的网络请求就会造成带宽紧张，
        // 影响到css或js文件加载完成的时间，造成页面空白loading过
        // 久。解决的方法就是告诉WebView先不要自动加载图片，等页面finish后再发起图片加载。）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
            this.getSettings().setLoadsImagesAutomatically(true);

        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            this.getSettings().setLoadsImagesAutomatically(false);
        }

        this.setWebViewClient(new WebViewClient());

        //Log.e(TAG, "init: version-->>"+this.getSettings().getUserAgentString());
    }
}
