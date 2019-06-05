package com.jjd.web;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import com.jjd.web.util.CacheUtil;

import java.util.Locale;

/**
 * @author yuansui
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

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        Locale l = config.locale;
        if (Build.VERSION.SDK_INT >= VERSION_CODES.N) {
            l = getResources().getConfiguration().getLocales().get(0);
        } else {
            l = getResources().getConfiguration().locale;
        }
    }
}
