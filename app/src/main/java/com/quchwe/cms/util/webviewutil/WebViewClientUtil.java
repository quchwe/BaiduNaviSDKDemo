package com.quchwe.cms.util.webviewutil;//package com.quchwe.cms.util.webviewutil;
//
//import android.graphics.Bitmap;
//import android.util.Log;
//
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//
//import static android.content.ContentValues.TAG;
//
///**
// * Created by bruce on 10/28/15.
// */
//public class WebViewClientUtil extends WebViewClient {
//    private TbsWebView webView;
//
//    public WebViewClientUtil(TbsWebView webView) {
//        this.webView = webView;
//    }
//
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        try {
//
//            Log.d(TAG, "shouldOverrideUrlLoading: " + url);
//            url = URLDecoder.decode(url, "UTF-8");
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        if (url.startsWith("https") || url.startsWith("http")) {
//            webView.loadUrl(url);
//            return true;
//        }
//        return super.shouldOverrideUrlLoading(view, url);
//
//    }
//
//    @Override
//    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//        super.onPageStarted(view, url, favicon);
//    }
//
//    @Override
//    public void onPageFinished(WebView view, String url) {
//        super.onPageFinished(view, url);
//        //Log.e(TAG, "onPageFinished: "+webView.getUrl()+"->origialurl->"+webView.getOriginalUrl() );
//    }
//
//    @Override
//    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//        super.onReceivedError(view, errorCode, description, failingUrl);
//    }
//}