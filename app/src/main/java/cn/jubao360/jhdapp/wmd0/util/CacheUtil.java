package cn.jubao360.jhdapp.wmd0.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author lixf
 */
public class CacheUtil extends CacheUtilEx {
    private static final String KHomeDir = "/UTOU/";
    private static final String KHomeDir_File = "/files/";

    private static final String KImgFilesDef = KHomeDir_File + "/images/";
    private static final String KDownloadFilesDef = KHomeDir_File + "/download/";
    private static final String KBmpCacheDef = KHomeDir + "cache/bmp/";
    private static final String KCameraDef = KHomeDir + "cache/bmp/camera/";
    private static final String KVoiceCacheDef = KHomeDir + "cache/voice/";
    private static final String KConfigDef = KHomeDir + "config/";
    private static final String KDownloadDef = KHomeDir + "download/";
    private static final String KMainSplashAdEn = KHomeDir + "main_splash_ad/en/";
    private static final String KMainSplashAdCn = KHomeDir + "main_splash_ad/cn/";

    private static String mImgFileDir;
    private static String mDownloadFileDir;

    private static String mBmpCacheDir;
    private static String mCameraDir;
    private static String mVoiceCacheDir;
    private static String mConfigDir;
    private static String mDownloadDir;
    private static String mMainSplashAdEn;
    private static String mMainSplashAdCn;

    public static void initCache(Context c){
        if (TextUtils.isEmpty(mImgFileDir)){
            return;
        }
        init(c, KHomeDir);
        init(c, KHomeDir_File);

        mImgFileDir = makeDir(c, KImgFilesDef);
        mDownloadFileDir = makeDir(c, KDownloadFilesDef);

        mBmpCacheDir = makeDir(c, KBmpCacheDef);
        mCameraDir = makeDir(c, KCameraDef);
        mVoiceCacheDir = makeDir(c, KVoiceCacheDef);
        mConfigDir = makeDir(c, KConfigDef);
        mDownloadDir = makeDir(c, KDownloadDef);
        mMainSplashAdCn = makeDir(c, KMainSplashAdCn);
        mMainSplashAdEn = makeDir(c, KMainSplashAdEn);
    }

    public static String getBmpCacheDir(Context c) {
        initCache(c);
        return mBmpCacheDir;
    }

    public static String getCameraDir(Context c) {
        initCache(c);
        return mCameraDir;
    }

    public static String getVoiceCacheDir(Context c) {
        initCache(c);
        return mVoiceCacheDir;
    }

    public static String getVoiceTmpPath(Context c, String fileName) {
        initCache(c);
        return mVoiceCacheDir + fileName;
    }

    public static String getConfigPath(Context c, String fileName) {
        initCache(c);
        return mConfigDir + fileName;
    }

    public static String getDownloadDir(Context c) {
        initCache(c);
        return mDownloadDir;
    }

    public static String getMainSplashAdDirCn(Context c) {
        initCache(c);
        return mMainSplashAdCn;
    }
    public static String getMainSplashAdDirEn(Context c) {
        initCache(c);
        return mMainSplashAdEn;
    }

    public static String getApkName(Context c, String fileName) {
        initCache(c);
        return fileName + ".apk";
    }

    public static File createImageFile(Context context) throws IOException {
        initCache(context);
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                storageDir      /* 文件夹 */
        );
        return image;
    }

    public static File createApkFile(Context context, String fileName)  {
        initCache(context);
        File apkFile = null;
        try{
            //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            Log.e("lxf", "createApkFile storageDir " + storageDir.getAbsolutePath());
            //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
            apkFile = new File(storageDir, fileName);
//            apkFile = File.createTempFile(
//                    fileName,  /* 前缀 */
//                    ".apk",         /* 后缀 */
//                    storageDir      /* 文件夹 */
//            );
//            apkFile.createNewFile();
        }catch (Exception e){

        }
        return apkFile;
    }
}
