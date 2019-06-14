package cn.jubao360.jhdapp.wmd0.service;

/**
 * Created by admin on 2019/6/11.
 */

public interface IDownloadListener {
    void onDownloadProgress(int progress, int totalSize);
}
