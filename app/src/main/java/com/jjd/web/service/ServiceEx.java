package com.jjd.web.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

import com.jjd.web.R;

import org.json.JSONException;

import lib.network.NetworkExecutor;
import lib.network.error.ConnectionNetError;
import lib.network.error.NetError;
import lib.network.model.NetworkListener;
import lib.network.model.NetworkRequest;
import lib.network.model.NetworkResponse;
import lib.network.model.NetworkRetry;


abstract public class ServiceEx extends Service implements INetwork {

    protected final String TAG = getClass().getSimpleName();

    private NetworkExecutor mNetworkExecutor;

    private SparseArray<NetworkRequest> mMapRetryTask = new SparseArray<>();
    private Handler mHandlerRetry;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public final void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent != null) {
            onHandleIntent(intent);
        }
    }

    abstract protected void onHandleIntent(Intent intent);

    /**
     * http task part
     */

    public void exeNetworkRequest(int id, NetworkRequest request) {
        exeNetworkRequest(id, request, this);
    }

    public void exeNetworkRequest(int id, NetworkRequest request, NetworkListener listener) {
        if (request.getRetry() != null) {
            mMapRetryTask.put(id, request);

            if (mHandlerRetry == null) {
                mHandlerRetry = new Handler() {

                    @Override
                    public void handleMessage(Message msg) {
                        exeNetworkRequest(msg.what, mMapRetryTask.get(msg.what));
                    }
                };
            }
        }

        if (!DeviceUtil.isNetworkEnable(getApplicationContext())) {
            onNetworkError(id, new ConnectionNetError(getString(R.string.toast_network_disconnect)));
            return;
        }

        if (mNetworkExecutor == null) {
            mNetworkExecutor = new NetworkExecutor(getClass().getName(), this);
        }
        mNetworkExecutor.execute(id, request, listener);
    }

    @Override
    public void cancelAllNetworkRequest() {
        if (mNetworkExecutor != null) {
            mNetworkExecutor.cancelAll();
        }

        if (mHandlerRetry != null) {
            mHandlerRetry.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void cancelNetworkRequest(int id) {
        if (mNetworkExecutor != null) {
            mNetworkExecutor.cancel(id);
        }

        if (mHandlerRetry != null) {
            mHandlerRetry.removeMessages(id);
        }
    }

    @Override
    public Object onNetworkResponse(int id, NetworkResponse nr) throws JSONException {
        return null;
    }

    @Override
    public void onNetworkSuccess(int id, Object result) {
    }

    @Override
    public void onNetworkError(int id, NetError error) {
        Exception e = error.getException();
        if (e != null) {
            Log.d(TAG, "onNetworkError: id = " + id);
            Log.d(TAG, "onNetworkError: e = " + e.getMessage());
            Log.d(TAG, "onNetworkError: msg = " + error.getMessage());
            Log.d(TAG, "onNetworkError: end=======================");
        } else {
            Log.d(TAG, "onNetworkError(): " + "tag = [" + id + "], error = [" + error.getMessage() + "]");
        }
    }

    @Override
    public void onNetworkProgress(int id, float progress, long totalSize) {
    }

    protected boolean retryNetworkRequest(int id) {
        NetworkRequest request = mMapRetryTask.get(id);
        if (request == null) {
            return false;
        }

        NetworkRetry retry = request.getRetry();
        if (retry.reduce()) {
            mHandlerRetry.sendEmptyMessageDelayed(id, retry.getDelay());
        } else {
            mMapRetryTask.remove(id);
            return false;
        }

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cancelAllNetworkRequest();
        mNetworkExecutor = null;

        if (mMapRetryTask != null) {
            for (int i = 0; i < mMapRetryTask.size(); ++i) {
                mHandlerRetry.removeMessages(i);
            }
            mMapRetryTask.clear();
            mMapRetryTask = null;
        }
        mHandlerRetry = null;
    }

    protected void startService(Class<? extends Service> clz) {
        startService(new Intent(this, clz));
    }

    protected void runOnUIThread(Runnable r) {
        UtilEx.runOnUIThread(r);
    }

    protected void runOnUIThread(Runnable r, long delayMillis) {
        UtilEx.runOnUIThread(r, delayMillis);
    }
}
