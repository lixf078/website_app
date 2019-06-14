package cn.jubao360.jhdapp.wmd0;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import cn.jubao360.jhdapp.wmd0.util.CacheUtil;

import java.util.Locale;

/**
 * @author lixf
 */
abstract public class AppParent extends AppEx {

    public static final int KTitleBarHeightDp = 44;
    private static final int KTitleBarIconPaddingHorizontalDp = 10;
    private static final int KTitleBarTextMarginHorizontalDp = 16;
    private static final int KTitleBarTextSizeMidDp = 17;
    private static final int KTitleBarTextSizeRightDp = 16;
    private static final int KTitleBarIconSizeDp = 22;

    @Override
    protected String getNetworkImageCacheDir() {
        return CacheUtil.getBmpCacheDir(getContext());
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void setParams() {

    }
}
