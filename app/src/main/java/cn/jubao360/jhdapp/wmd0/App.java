package cn.jubao360.jhdapp.wmd0;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

/**
 * 企业端
 *
 * @author lixf
 */
public class App extends AppParent {

    public static boolean isShowNav = false;
    public static int activityState = -1;
    public static int hasShowNav = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        hasShowNav = -1;
        // bugly
        initAliPush(this);
    }

    @Override
    protected void setParams() {
        super.setParams();

//        JPush.inst().init(BuildConfig.DEBUG_LOG);

        registerActivityLifecycleCallbacks(callbacks);

        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }


    Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activity.getLocalClassName().equals("yt.co.app.activity.MainCalcActivity") && activityState == 3){
                activityState = 0;
                isShowNav = true;
            }else if (activity.getLocalClassName().equals("yt.co.app.activity.MainSplashActivity")){
                isShowNav = false;
                hasShowNav = 1;
            }else{
                isShowNav = false;
                hasShowNav = 0;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.getLocalClassName().equals("yt.co.app.activity.MainCalcActivity")){
                activityState = 3;
            }else{
                activityState = 5;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };



    public interface MsgDisplayListener {
        void handle(String msg);
    }
    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();


//    CloudPushService pushService = null;
    private void initAliPush(Context applicationContext){
        initCloudChannel(applicationContext);
    }

    /**
     * 初始化云推送通道
     * @param applicationContext
     */

    private void initCloudChannel(Context applicationContext) {
//        createNotificationChannel();
//        PushServiceFactory.init(applicationContext);
//        pushService = PushServiceFactory.getCloudPushService();
//        pushService.addAlias("12035", new CommonCallback() {
//            @Override
//            public void onSuccess(String s) {
//
//            }
//
//            @Override
//            public void onFailed(String s, String s1) {
//
//            }
//        });
//        pushService.register(applicationContext, new CommonCallback() {
//            @Override
//            public void onSuccess(String response) {
//                Log.e("lxf", "init cloudchannel success");
//                Log.e("lxf", pushService.getDeviceId());
//            }
//            @Override
//            public void onFailed(String errorCode, String errorMessage) {
//                Log.e("lxf", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
//            }
//        });
////        pushService.removeAlias();
//
//        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
//        MiPushRegister.register(applicationContext, "2882303761517599192", "5421759920192");
//        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
//        HuaWeiRegister.register(applicationContext);
//        //GCM/FCM辅助通道注册
////        GcmRegister.register(this, sendId, applicationId); //sendId/applicationId为步骤获得的参数
//        // OPPO通道注册
//        OppoRegister.register(applicationContext, "1Nl0s0m3gKE8W0wco8kgGS40S", "4FB3527D02C9e605Db7C537d7e35ED2d"); // appKey/appSecret在OPPO通道开发者平台获取

    }


    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            // 通知渠道的id
//            String id = "1";
//            // 用户可以看到的通知渠道的名字.
//            CharSequence name = "notification channel";
//            // 用户可以看到的通知渠道的描述
//            String description = "notification description";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//            // 配置通知渠道的属性
//            mChannel.setDescription(description);
//            // 设置通知出现时的闪灯（如果 android 设备支持的话）
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            // 设置通知出现时的震动（如果 android 设备支持的话）
//            mChannel.enableVibration(true);
//            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            //最后在notificationmanager中创建该通知渠道
//            mNotificationManager.createNotificationChannel(mChannel);
//        }
    }
}
