package cn.jubao360.jhdapp.wmd0;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.jubao360.jhdapp.wmd0.service.DownloadApkService;
import cn.jubao360.jhdapp.wmd0.service.IDownloadListener;
import cn.jubao360.jhdapp.wmd0.view.DialogEx;
import cn.jubao360.jhdapp.wmd0.view.RegSuccessDialog;

import java.io.Serializable;
import java.text.NumberFormat;

public class CustomerWebActivity extends Activity  implements IDownloadListener {

    WebView mWebView = null;

    View topView = null;
    TextView web_view_top_layout_title;
    String title = "";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        mWebView = (WebView) findViewById(R.id.m_customer_webview);

        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.clearFormData();

        topView = findViewById(R.id.web_view_top_layout);
        web_view_top_layout_title = (TextView) findViewById(R.id.web_view_top_layout_title);

        url = getIntent().getStringExtra("jumpCustomerUrl");
        title = getIntent().getStringExtra("jumpCustomerTitle");
        web_view_top_layout_title.setText(title);

        findViewById(R.id.web_view_top_layout_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canGoBack()){
                    goBack();
                }else {
                    finish();
                }
            }
        });

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
                if (DownloadApkService.getState() == 0){
                    DownLoadModel data = new DownLoadModel();
                    data.setTitle(contentDisposition);
                    data.setUrl(url);
                    data.setVersion("1.0");
                    data.setVersionCode("10");
                    Intent intent = new Intent(CustomerWebActivity.this, DownloadApkService.class).putExtra("data", (Serializable) data);
                    CustomerWebActivity.this.startService(intent);
                    showProgressDialog();
                }else {
                    Toast.makeText(CustomerWebActivity.this, "当前正在下载，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

                                        @Override
                                        public void onProgressChanged(WebView view, int newProgress) {
                                        }

                                        @Override
                                        public void onReceivedTitle(WebView view, String title) {
                                            super.onReceivedTitle(view, title);
                                        }
                                    });

        JJDJavaScriptInterface JSInterface = new JJDJavaScriptInterface(this); ////------
        mWebView.addJavascriptInterface(JSInterface, "JSInterface"); // 设置js接口  第一个参数事件接口实例，第二个是实例在js中的别名，这个在js中会用到
//        mWebView.loadUrl("file:///android_asset/web/index.html?start_time=" + System.currentTimeMillis());
        mWebView.loadUrl(url);
        DownloadApkService.setDownloadListener(this);
    }

    @Override
    public void onBackPressed() {
        if (canGoBack()){
            goBack();
        }else {
            finish();
        }
    }

    /**
     * 是否可后退
     *
     * @return
     */
    protected boolean canGoBack() {
        return mWebView.canGoBack();
    }

    /**
     * 后退
     */
    protected void goBack() {
        mWebView.goBack();
    }

    public class JJDJavaScriptInterface {
        Context mContext;
        JJDJavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void jsAction(String action){
            Log.e("lxf", "jsAction url " + action);
        }

        @JavascriptInterface
        public void jumpCustomerUrl(String url, String title){
            Log.e("lxf", "jumpUrl url " + url + ",title " + title);
        }
    }

    private void initFormats() {
        mProgressNumberFormat = "%1d/%2d";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    @SuppressLint("HandlerLeak")
    public void showProgressDialog() {
        Log.e("lxf", "CustomerWebActivity showProgressDialog url " + dialog);
        if (dialog != null){
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            dialog = null;
        }
        dialog = new RegSuccessDialog(CustomerWebActivity.this);
        dialog.setListener(new DialogEx.OnDialogListener() {
            @Override
            public void callback(Object... params) {
                int event = (int)params[0];
                if (event == 1){
                    // confirm
                }else{
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        progressBar = dialog.findView(R.id.progress_bar);
        progress_percent = dialog.findView(R.id.progress_number);
        progress_number = dialog.findView(R.id.progress_percent);
        initFormats();
        updateProgressView();
    }


    @SuppressLint("HandlerLeak")
    private void updateProgressView() {
        final String format = mProgressNumberFormat;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    /* Update the number and percent */
                int progress = progressBar.getProgress();
                int max = progressBar.getMax();
                if (mProgressNumberFormat != null) {
                    progress_number.setText(String.format(format, progress, max));
                } else {
                    progress_number.setText("");
                }
                if (mProgressPercentFormat != null) {
                    double percent = (double) progress / (double) max;
                    SpannableString tmp1 = new SpannableString(mProgressPercentFormat.format(percent));
                    tmp1.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0, tmp1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    progress_percent.setText(tmp1);
                } else {
                    progress_percent.setText("");
                }
                if (progress == progressBar.getMax()) {
                    dialog.dismiss();
                }
            }
        };
    }

    RegSuccessDialog dialog = null;
    private Handler handler;
    private ProgressBar progressBar;
    private TextView progress_number;
    private TextView progress_percent;
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;

    @Override
    public void onDownloadProgress(int progress, int totalSize) {
        Log.e("lxf", "CustomerWebActivity onDownloadProgress progress " + progress);
        try{
            if (dialog.isShowing()){
                progressBar.setProgress(progress);
                handler.sendEmptyMessage(0);
                if (progress == 100) {
                    dialog.dismiss();
                }
                if (progress == -1){
                    Toast.makeText(CustomerWebActivity.this, "下载失败，请稍后重试", Toast.LENGTH_LONG);
                    dialog.dismiss();
                }
            }
        }catch (Exception e){

        }
    }
}
