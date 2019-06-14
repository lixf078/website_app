package cn.jubao360.jhdapp.wmd0.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;


@SuppressWarnings("deprecation")
public class DeviceUtil {

    private static final String TAG = DeviceUtil.class.getSimpleName();

    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkEnable(Context ctx ) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    public static boolean isAirplaneModeOn(Context context) {
        return (0 != Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0));
    }


    /**
     * sdcard是否装好
     *
     * @return
     */
    public static boolean isSdcardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * sdcard是否可用
     *
     * @return
     */
    public static boolean isSdcardEnable() {
        return isSdcardMounted() && !Environment.getExternalStorageState().equals(Environment.MEDIA_SHARED);
    }

    public static String getMetaValue(Context ctx, String metaKey) {
        if (metaKey == null) {
            return null;
        }

        Bundle metaData = null;
        String apiKey = null;
        try {
            ApplicationInfo ai = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
            apiKey = "";
        }
        return apiKey;
    }

    public static void setMetaValue(Context ctx, String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        ApplicationInfo appInfo = null;
        try {
            appInfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            appInfo.metaData.putString(key, value);
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static WifiInfo getWifiInfo(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info;
    }

    public static long getRuntimeMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * 获取当前SDK的版本号
     *
     * @return
     */
    public static int getSDKVersion() {
        int version = 0;
        try {
            version = Build.VERSION.SDK_INT;
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
        }
        return version;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    // 获取手机型号
    public static String getMobileType() {
        return Build.MODEL.replaceAll(" ", "");
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    // 获取当前软件版本名
    public static String getAppVersionName(Context ctx) {
        String versionName = "";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return versionName;
    }

    // 获取当前软件版本号
    public static int getAppVersion(Context ctx) {
        int versionCode = -1;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return versionCode;
    }

    public static File getSdcardDir() {
        File dir = Environment.getExternalStorageDirectory();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 是否>=6.0
     *
     * @return
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    /**
     * 获取App的名字
     *
     * @return
     */
    public static String getAppName(Context ctx) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = ctx.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    public static Drawable getAppIcon(Context ctx) {
        PackageManager packageManager = ctx.getPackageManager();
        try {
            return packageManager.getApplicationIcon(ctx.getPackageName());
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static void makePhoneCall(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 获取android id
     *
     * @return
     */
    public static String getAndroidId(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }
}
