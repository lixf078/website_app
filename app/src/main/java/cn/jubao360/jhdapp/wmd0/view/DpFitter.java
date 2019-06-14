package cn.jubao360.jhdapp.wmd0.view;

import android.support.annotation.DimenRes;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import cn.jubao360.jhdapp.wmd0.AppEx;

/**
 * 专门用于适配分辨率, 根据dp来设置的
 *
 * @author lixf
 */
public class DpFitter {
    // 基于屏幕分辨率的缩放
    private static float mScaleResolution = Integer.MIN_VALUE;
    private static float mDensity = Integer.MIN_VALUE;

    /**
     * 根据缩放比例将dp转换成px
     *
     * @param dp
     * @return
     */
    public static int dp(float dp) {
        return (int) (dp * FitConfig.getDpBaseScale() * getScale());
    }

    /**
     * 根据比例将dimen.xml里的值转换成px
     *
     * @param dimenResId R.dimen.xxx
     * @return
     */
    public static int dimen(@DimenRes int dimenResId) {
        return (int) (getDimensionDp(dimenResId) * FitConfig.getDpBaseScale() * getScale());
    }

    /**
     * 把经过density缩放的px值转换为scale后的px
     *
     * @param px
     * @return
     */
    public static float densityPx(float px) {
        float dp = px / getDensity(); // 转换为dp
        return dp(dp);
    }

    /**
     * 把经过density缩放的px值转换为scale后的px
     *
     * @param px
     * @return
     */
    public static int densityPx(int px) {
        float fPx = px;
        return (int) densityPx(fPx);
    }

    public static float getDensity() {
        if (mDensity == Integer.MIN_VALUE) {
            mDensity = Screen.getDensity(AppEx.getContext());
        }
        return mDensity;
    }

    public static void relateParams(final View v, final int w, final int h) {
        relateParams(v, w, h, null);
    }

    public static void relateParams(final View v, final int w, final int h, final int[] margins) {
        if (margins != null && margins.length == 4) {
            margins[0] = dp(margins[0]);
            margins[1] = dp(margins[1]);
            margins[2] = dp(margins[2]);
            margins[3] = dp(margins[3]);
        }
        PxFitter.fitRelateParams(v, getRealValue(w), getRealValue(h), margins);
    }

    public static void linerParams(final View v, final int w, final int h) {
        linerParams(v, w, h, null);
    }

    public static void linerParams(final View v, final int w, final int h, final int[] margins) {
        if (margins != null && margins.length == 4) {
            margins[0] = dp(margins[0]);
            margins[1] = dp(margins[1]);
            margins[2] = dp(margins[2]);
            margins[3] = dp(margins[3]);
        }
        PxFitter.fitLinerParams(v, getRealValue(w), getRealValue(h), margins);
    }

    public static void absParams(View v, int w, int h, int x, int y) {
        PxFitter.fitAbsParams(v, dp(w), dp(h), dp(x), dp(y));
    }

    public static void textSize(TextView tv, int textSizeDp) {
        PxFitter.fitTvTextSize(tv, dp(textSizeDp));
    }

    private static int getRealValue(int value) {
        int realValue = 0;
        switch (value) {
            case LayoutParams.WRAP_CONTENT:
            case LayoutParams.MATCH_PARENT: {
                realValue = value;
            }
            break;
            default: {
                realValue = dp(value);
            }
            break;
        }
        return realValue;
    }

    public static float getScale() {
        if (mScaleResolution == Integer.MIN_VALUE) {
            float scaleW = 0;
            float scaleH = 0;

            if (Screen.getWidth(AppEx.getContext()) > Screen.getHeight(AppEx.getContext())) {
                // 横屏
                scaleW = Screen.getWidth(AppEx.getContext()) / FitConfig.getMaxScreenHeight();
                scaleH = Screen.getHeight(AppEx.getContext()) / FitConfig.getMaxScreenWidth();
            } else {
                // 竖屏
                scaleW = Screen.getWidth(AppEx.getContext()) / FitConfig.getMaxScreenWidth();
                scaleH = Screen.getHeight(AppEx.getContext()) / FitConfig.getMaxScreenHeight();
            }

            mScaleResolution = scaleW < scaleH ? scaleW : scaleH;
        }
        return mScaleResolution;
    }

    public static void setScale(float scale) {
        mScaleResolution = scale;
    }

    /**
     * 把dimen.xml里面的值以DP的形式取出来
     *
     * @param id
     * @return
     */
    public static int getDimensionDp(@DimenRes int id) {
        float px = AppEx.getContext().getResources().getDimension(id);
        float dp = px / getDensity();
        return (int) dp;
    }
}
