package cn.jubao360.jhdapp.wmd0;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

import cn.jubao360.jhdapp.wmd0.service.DownloadApkService;
import cn.jubao360.jhdapp.wmd0.sp.Sp;
import cn.jubao360.jhdapp.wmd0.util.permission.PermissionHelper;

/**
 * Created by admin on 2019/6/14.
 */

public class BaseActivity extends Activity {

    /**
     * 获取权限使用的 RequestCode
     */
    public static final int PERMISSIONS_REQUEST_CODE = 1002;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                Log.e("lxf", "onRequestPermissionsResult grantResults.length " + grantResults.length);
                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    Toast.makeText(BaseActivity.this, "请您在设置中打开应用内安装权限1", Toast.LENGTH_LONG).show();
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(BaseActivity.this, "请您在设置中打开应用内安装权限2", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                // 所需的权限均正常获取
                Toast.makeText(BaseActivity.this, "您已经打开应用内安装权限3", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 在某个权限下运行某些代码
     * 注意：必须在AndroidManifest.xml中先声明需要申请的权限（有些不需要声明，有些必须声明，所以建议全部声明）。
     * @param permissions     android.Manifest.permission.xxx
     * @param actionOnGranted 在权限被授权的情况运行的代码
     * @param actionOnDenied  在权限被拒绝的情况下运行的代码
     */
    public static void runOnPermissionGranted(Activity activity, Runnable actionOnGranted, Runnable actionOnDenied, String... permissions) {
        PermissionHelper.runOnPermissionGranted(activity, actionOnGranted, actionOnDenied, permissions);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    public void checkPermission(final String url, final String contentDisposition){
//        if (Build.VERSION.SDK_INT >= 56){
//            boolean grant = getPackageManager().canRequestPackageInstalls();
//            if (grant){
//                downloadApk(url, contentDisposition);
//                return;
//            }
//            PermissionHelper.runOnPermissionGranted(BaseActivity.this, new Runnable() {
//                @Override
//                public void run() {
//                    downloadApk(url, contentDisposition);
//                }
//            }, new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(BaseActivity.this, "请您在设置中打开应用内安装权限", Toast.LENGTH_LONG).show();
//                }
//            }, android.Manifest.permission.REQUEST_INSTALL_PACKAGES);
//        }else {
//            downloadApk(url, contentDisposition);
//        }

    }

    public void downloadApk(String url, String contentDisposition){
        if (DownloadApkService.getState() == 0){
            DownLoadModel data = new DownLoadModel();
            data.setTitle(contentDisposition);
            data.setUrl(url);
            data.setVersion("1.0");
            data.setVersionCode("10");
            Intent intent = new Intent(BaseActivity.this, DownloadApkService.class).putExtra("data", (Serializable) data);
            BaseActivity.this.startService(intent);
            showProgressDialog();
        }else {
            Toast.makeText(BaseActivity.this, "当前正在下载，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    public void showProgressDialog() {

    }

    public void checkPermission(){
//        if (Build.VERSION.SDK_INT >= 26){
//            if (Sp.inst(BaseActivity.this).getBoolean("isShowPermission")){
//                boolean grant = getPackageManager().canRequestPackageInstalls();
//                if (grant){
//                    return;
//                }
//                PermissionHelper.runOnPermissionGranted(BaseActivity.this, new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(BaseActivity.this, "请您在设置中打开应用内安装权限", Toast.LENGTH_LONG).show();
//                    }
//                }, android.Manifest.permission.REQUEST_INSTALL_PACKAGES);
//                Sp.inst(BaseActivity.this).save("isShowPermission", true);
//            }
//        }

    }

}
