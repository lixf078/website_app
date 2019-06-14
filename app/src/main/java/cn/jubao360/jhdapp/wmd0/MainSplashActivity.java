package cn.jubao360.jhdapp.wmd0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.util.Random;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lc on 2017/4/23.
 */

public class MainSplashActivity extends Activity {

    private static final int KCodeCamera = 101;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2: {
                        Intent intent = new Intent(MainSplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(2, 2000);
    }
//
//    @Override
//    public void onPermissionResult(int code, @PermissionResult int result) {
//        if (code == KCodeCamera) {
//            switch (result) {
//                case PermissionResult.granted: {
//                }
//                break;
//                default: {
//                    showToast(lib.ut.R.string.toast_please_granted_camera_permission);
//                }
//                break;
//            }
//
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(2);
        mHandler = null;
    }
}
