package lib.network.provider.ok.callback;

import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import lib.network.LogNetwork;
import lib.network.error.CallbackEmptyError;
import lib.network.error.ConnectionNetError;
import lib.network.error.ParseNetError;
import lib.network.provider.Delivery;
import lib.network.provider.IRequestBuilder;
import okhttp3.Call;

/**
 * @author lixf
 */
public class DownloadFileCallback extends FileCallBack {

    private IRequestBuilder mBuilder;
    private Delivery mDelivery;

    public DownloadFileCallback(IRequestBuilder builder, Delivery delivery) {
        super(builder.request().getDir(), builder.request().getFileName());

        mBuilder = builder;
        mDelivery = delivery;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        mDelivery.deliverError(mBuilder, new ConnectionNetError(e.getMessage()));
    }

    @Override
    public void onResponse(File response, int id) {
        if (mBuilder.listener() != null) {
            if (response != null) {
                mDelivery.deliverSuccess(mBuilder, response);
            } else {
                LogNetwork.d("数据解析错误");
                mDelivery.deliverError(mBuilder, new ParseNetError("数据解析错误"));
            }
        } else {
            LogNetwork.d("没有回调");
            mDelivery.deliverError(mBuilder, new CallbackEmptyError());
        }
    }

    @Override
    public void inProgress(float progress, long total, int id) {
        mDelivery.deliverProgress(mBuilder, progress * 100, total);
    }
}
