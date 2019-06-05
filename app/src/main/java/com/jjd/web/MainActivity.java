package com.jjd.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jjd.web.service.DownloadApkService;

import java.io.Serializable;

public class MainActivity extends Activity {

    WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.m_webview);
        WebSettings webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//        webSettings.setTextZoom(2);//设置文本的缩放倍数，默认为 100

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级

        webSettings.setStandardFontFamily("");//设置 WebView 的字体，默认字体为 "sans-serif"
        webSettings.setDefaultFontSize(20);//设置 WebView 字体的大小，默认大小为 16
        webSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8

        // 5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //其他操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setGeolocationEnabled(true);//允许网页执行定位操作
//        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");//设置User-Agent

        //不允许访问本地文件（不影响assets和resources资源的加载）
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDatabaseEnabled(true);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        String appCachePath = getApplication().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setDatabaseEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("lxf", "shouldOverrideUrlLoading url " + url);
                return false;
            }
        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.e("lxf", "onDownloadStart url " + url + ", contentDisposition " + contentDisposition);
                DownLoadModel data = new DownLoadModel();
                data.setTitle(contentDisposition);
                data.setUrl(url);
                data.setVersion("1.1");
                data.setVersionCode("1.2");
                MainActivity.this.startService(new Intent(MainActivity.this, DownloadApkService.class).putExtra("data", (Serializable) data));
            }
        });
        mWebView.loadUrl("file:///android_asset/web/index.html");
//        mWebView.loadUrl("file:///android_asset/web/jhd/index/html/main.html");
    }
}
