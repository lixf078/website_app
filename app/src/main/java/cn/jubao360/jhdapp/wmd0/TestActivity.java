package cn.jubao360.jhdapp.wmd0;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.os.ResultReceiver;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import java.lang.reflect.Field;
import java.text.NumberFormat;

public class TestActivity extends Activity implements IDownloadListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
       findViewById(R.id.click_me).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DownLoadModel data = new DownLoadModel();
               data.setTitle("sdf");
               data.setUrl("http://A6001809635896.qiniucdn.apicloud-system.com/25a75139aa3e1869b79da5fa150e8a49_d");
               data.setVersion("1.1");
               data.setVersionCode("1.2");
               Intent intent = new Intent(TestActivity.this, DownloadApkService.class).putExtra("data", (Serializable) data);
//               intent.putExtra("receiver", new TestActivity.DownloadReceiver(new Handler()));
               TestActivity.this.startService(intent);
//               showProgressDialog();
           }
       });

        DownloadApkService.setDownloadListener(this);
    }


    private void initFormats() {
        mProgressNumberFormat = "%1d/%2d";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    @SuppressLint("HandlerLeak")
    public void showProgressDialog() {

        dialog = new RegSuccessDialog(TestActivity.this);
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

//        new Thread(new Runnable() {
//            int progress = 0;
//
//            @Override
//            public void run() {
//                while (progress <= 100) {
//                    progressBar.setProgress(progress);
//                    handler.sendEmptyMessage(0);
//                    if (progress == 100) {
//                        dialog.dismiss();
//                    }
//                    try {
//                        Thread.sleep(35);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    progress++;
//                }
//            }
//        }).start();
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
        if (dialog != null && dialog.isShowing()){
            progressBar.setProgress(progress);
            handler.sendEmptyMessage(0);
            if (progress == 100) {
                dialog.dismiss();
            }
            if (progress == -1){
                Toast.makeText(TestActivity.this, "下载失败，请稍后重试", Toast.LENGTH_LONG);
                dialog.dismiss();
            }
        }
    }
}
