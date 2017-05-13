package com.quchwe.cms.util.webviewutil;

import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by quchwe on 2016/11/12 0012.
 */

public class WebViewChromeClientUtil extends WebChromeClient {

    ProgressBar bar;

    public WebViewChromeClientUtil(ProgressBar bar){
        this.bar = bar;
    }
    @Override
    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
        if (i == 100) {
            bar.setVisibility(View.GONE);
        } else {
            if (View.GONE == bar.getVisibility()) {
                bar.setVisibility(View.VISIBLE);
            }
            bar.setProgress(i);
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onReceivedTitle(WebView webView, String s) {
        super.onReceivedTitle(webView, s);
    }

    @Override
    public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
        return super.onJsAlert(webView, s, s1, jsResult);
    }
}
