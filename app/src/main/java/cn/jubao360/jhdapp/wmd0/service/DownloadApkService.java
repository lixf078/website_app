package cn.jubao360.jhdapp.wmd0.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import cn.jubao360.jhdapp.wmd0.DownLoadModel;
import cn.jubao360.jhdapp.wmd0.R;
import cn.jubao360.jhdapp.wmd0.util.CacheUtil;
import cn.jubao360.jhdapp.wmd0.util.NetFactory;
import cn.jubao360.jhdapp.wmd0.util.NotificationBuilder;
import cn.jubao360.jhdapp.wmd0.util.NotificationId;
import cn.jubao360.jhdapp.wmd0.util.NotificationUtil;
import cn.jubao360.jhdapp.wmd0.util.TimeUtil;

import java.io.File;

import lib.network.error.NetError;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * App升级新版本服务
 *
 * @author lixf
 */
public class DownloadApkService extends BaseService {

   // http://downloadpkg.apicloud.com/app/download?path=http://A6001809635896.qiniucdn.apicloud-system.com/25a75139aa3e1869b79da5fa150e8a49_d
    // http://A6001809635896.qiniucdn.apicloud-system.com/25a75139aa3e1869b79da5fa150e8a49_d
    private static final long KRefreshDelay = MilliUtil.second(1);
    private Notification mNotification;
    private DownLoadModel mApk;

    private Handler mHandler;
    private Handler mActivityHandler;

    private int mProgress;

    static IDownloadListener iDownloadListener;

    private static int state = 0;

    public static Intent getFileIntent(File file) {
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        // 取得扩展名
//        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length());
//        if (end.equals("apk")) {
//            type = "application/vnd.android.package-archive";
//        } else {
             /*如果无法直接打开，就跳出软件列表给用户选择 */
//            type = "*/*";
//        }
        type = "application/vnd.android.package-archive";
        return type;
    }

    @Override
    public void onNetworkProgress(int id, float progress, long totalSize) {
//        Log.d(TAG, "onNetworkProgress() progress = " + progress);
        mProgress = (int) progress;
    }

    @Override
    public void onNetworkSuccess(int id, Object result) {
        mNotification.contentView.setViewVisibility(R.id.notification_download_layout_progress, View.GONE);
        mNotification.contentView.setViewVisibility(R.id.notification_download_tv_install, View.VISIBLE);

        mNotification.flags |= PendingIntent.FLAG_UPDATE_CURRENT;
        NotificationUtil.launch(getApplicationContext(), NotificationId.app_download, mNotification);
        if (fileIntent != null){
            if (Build.VERSION.SDK_INT >= 26){
                startActivity(fileIntent);
//                if (getPackageManager().canRequestPackageInstalls()){
//                }else {
//                    Toast.makeText(getApplicationContext(), "请先打开安装权限", Toast.LENGTH_LONG).show();
//                }
            }else {
                startActivity(fileIntent);
            }
        }
        if (iDownloadListener != null){
//                    Bundle resultData = new Bundle();
//                    resultData.putInt("progress" ,mProgress);
//                    receiver.send(UPDATE_PROGRESS, resultData);
            iDownloadListener.onDownloadProgress(100, 100);
        }
        Log.e("lxf", "onNetworkSuccess state "  + state);
        state = 0;
        stopSelf();
    }

    @Override
    public void onNetworkError(int id, NetError error) {
        super.onNetworkError(id, error);
        cancelAllNetworkRequest();
        NotificationUtil.cancel(getApplicationContext(), NotificationId.app_download);
        if (iDownloadListener != null){
//                    Bundle resultData = new Bundle();
//                    resultData.putInt("progress" ,mProgress);
//                    receiver.send(UPDATE_PROGRESS, resultData);
            iDownloadListener.onDownloadProgress(-1, 100);
        }
        Log.e("lxf", "onNetworkError state "  + state + ", error " + error.getCode() + ", msg " + error.getMessage());
        state = 0;
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mHandler.hasMessages(0)) {
            mHandler.removeMessages(0);
        }
        mHandler = null;
        iDownloadListener = null;
        Log.e("lxf", "onDestroy state "  + state);
        state = 0;
    }

    private Intent webLauncher(String downloadUrl){
        Uri download = Uri.parse(downloadUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, download);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return intent;
    }

    File tempFile = null;
    Intent fileIntent = null;
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("lxf", "onHandleIntent state "  + state);
        if (state == 1){
            return;
        }
        cancelAllNetworkRequest();
        mApk = (DownLoadModel) intent.getSerializableExtra("data");
        String fileName = mApk.getTitle() + ".apk";

        if(Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            try {
                File filePath = new File(getApplicationContext().getCacheDir() + "/files/", "download");
                tempFile = new File(filePath, fileName);
            }catch (Exception e){
                e.printStackTrace();
            }
            fileIntent = getIntent(tempFile);
        }else{
            String dir = CacheUtil.getDownloadDir(getApplication());
            tempFile = new File(dir, fileName);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            fileIntent = getFileIntent(tempFile);
        }

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.layout_notification_download);
        String version = mApk.getVersion();
        if (version.endsWith("000")){
            version = version.substring(0,2);
        }else if (version.endsWith("00")){
            version = version.substring(0,3);
        }
        StringBuffer sb = new StringBuffer(version);
        sb.insert(1,".");
        views.setTextViewText(R.id.notification_download_tv_title,
                DeviceUtil.getAppName(getApplicationContext()) + ": V" + sb.toString() + " " + getString(R.string.notification_apk_download_extend));
        views.setTextViewText(R.id.notification_download_tv_time, TimeUtil.formatMilli(System.currentTimeMillis(), TimeUtil.from_h_to_m_24));
        views.setTextViewText(R.id.notification_download_tv_progress, 0 + "%");

        mNotification = NotificationUtil.launch(getApplicationContext(), NotificationId.app_download, NotificationBuilder.downloadApk(
                this,
                fileIntent,
                R.mipmap.ic_launcher,
                views,
                getResources().getString(R.string.notification_ticker_app_download)
        ));
        exeNetworkRequest(0, NetFactory.downloadFile(mApk.getUrl(), tempFile.getParent(), fileName));
//        exeNetworkRequest(0, NetFactory.downloadFile("http://A6001809635896.qiniucdn.apicloud-system.com/25a75139aa3e1869b79da5fa150e8a49_d", tempFile.getParent(), fileName));
        //http://A6001809635896.qiniucdn.apicloud-system.com/25a75139aa3e1869b79da5fa150e8a49_d

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                mNotification.contentView.setProgressBar(R.id.notification_download_pb, 100, mProgress, false);
                mNotification.contentView.setTextViewText(R.id.notification_download_tv_progress, mProgress + "%");

                mNotification.flags |= PendingIntent.FLAG_UPDATE_CURRENT;
                NotificationUtil.launch(getApplicationContext(), NotificationId.app_download, mNotification);

                sendEmptyMessageDelayed(0, KRefreshDelay);

                if (iDownloadListener != null){
//                    Bundle resultData = new Bundle();
//                    resultData.putInt("progress" ,mProgress);
//                    receiver.send(UPDATE_PROGRESS, resultData);
                    iDownloadListener.onDownloadProgress(mProgress, 100);
                }
            }
        };

        mHandler.sendEmptyMessageDelayed(0, KRefreshDelay);
        state = 1;
    }

    public Intent getIntent(File tempFile2){
        Uri apkUri = null;
        apkUri = getUriForFile(getApplicationContext(), "cn.jubao360.jhdapp.wmd0.fileprovider", tempFile2);
        Intent tempIntent = new Intent(Intent.ACTION_VIEW);
        tempIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        tempIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        tempIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        return tempIntent;
    }

    public static void setDownloadListener(IDownloadListener downloadListener) {
        iDownloadListener = downloadListener;
    }

    public static int getState(){
        Log.e("lxf", "getState state "  + state);
        return state;
    }

}
