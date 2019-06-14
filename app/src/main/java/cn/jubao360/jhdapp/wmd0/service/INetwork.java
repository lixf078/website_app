package cn.jubao360.jhdapp.wmd0.service;


import lib.network.model.NetworkListener;
import lib.network.model.NetworkRequest;

/**
 * 网络操作
 *
 * @author lixf
 */
public interface INetwork extends NetworkListener {

    /**
     * @param id
     * @param request
     */
    void exeNetworkRequest(int id, NetworkRequest request);

    /**
     * 可以自行设置重试次数及超时时间, 多用于一些需要不断重试的任务
     *
     * @param id
     * @param request
     * @param listener
     */
    void exeNetworkRequest(int id, NetworkRequest request, NetworkListener listener);

    /**
     * 取消所有网络任务
     */
    void cancelAllNetworkRequest();

    void cancelNetworkRequest(int id);
}
