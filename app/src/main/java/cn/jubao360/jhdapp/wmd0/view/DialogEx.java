package cn.jubao360.jhdapp.wmd0.view;

import android.Manifest;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.PermissionChecker;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;

import cn.jubao360.jhdapp.wmd0.AppEx;
import cn.jubao360.jhdapp.wmd0.R;


abstract public class DialogEx implements OnClickListener,
        IInitialize,
        IOption{

    protected String TAG = getClass().getSimpleName();

    private OnClickListener mDismissLsn;

    private Dialog mDialog;

    public interface OnDialogListener {
        void callback(Object... params);
    }

    private OnDialogListener mListener;


    protected View mContentView;
    private Context mContext;


    public DialogEx(@NonNull Context context) {
        if (context == null) {
            throw new NullPointerException("context can not be null");
        }
        mContext = context;
        init();
    }

    public DialogEx(@NonNull Context context, @StyleRes int themeResId) {
        if (context == null) {
            throw new NullPointerException("context can not be null");
        }
        mContext = context;
        init(themeResId);
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.dialog);
        mContentView = getLayoutInflater().inflate(getContentViewId(), null);

        initData();
        findViews();
        setViewsValue();

        ViewGroup.LayoutParams params = getParams();
        if (params != null) {
            mDialog.setContentView(mContentView, params);
        } else {
            mDialog.setContentView(mContentView);
        }

        LayoutFitter.fit(mContentView);
    }

    private void init(@StyleRes int themeResId) {
        mDialog = new Dialog(mContext, themeResId);
        mContentView = getLayoutInflater().inflate(getContentViewId(), null);

        initData();
        findViews();
        setViewsValue();

        ViewGroup.LayoutParams params = getParams();
        if (params != null) {
            mDialog.setContentView(mContentView, params);
        } else {
            mDialog.setContentView(mContentView);
        }

        LayoutFitter.fit(mContentView);
    }

    @Nullable
    @Override
    public int getContentHeaderViewId() {
        return 0;
    }

    @Nullable
    @Override
    public int getContentFooterViewId() {
        return 0;
    }

    /**
     * 如果要调整宽高的参数可以再这里处理
     */
    @Nullable
    protected ViewGroup.LayoutParams getParams() {
        return null;
    }

    /**
     * @param type 如果type是 {@link LayoutParams#TYPE_SYSTEM_ALERT}
     *             需要添加权限 <p>
     *             permission:android.permission.SYSTEM_ALERT_WINDOW
     */
    public void setType(int type) {
//        if (type == LayoutParams.TYPE_SYSTEM_ALERT && !PermissionChecker.allow(mContext, Manifest.permission.SYSTEM_ALERT_WINDOW)) {
//            throw new IllegalArgumentException("请在manifest添加权限 TYPE_SYSTEM_ALERT");
//        }
//        mDialog.getWindow().setType(type);
    }

    public void setCancelable(boolean flag) {
        mDialog.setCancelable(flag);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
    }

    public void setOnCancelListener(OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void show() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    protected View findViewById(int id) {
        return mContentView.findViewById(id);
    }

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 设置点击可以关闭dialog的v
     *
     * @param v
     */
    protected void setDismissClicker(View v) {
        if (v == null) {
            return;
        }

        if (mDismissLsn == null) {
            mDismissLsn = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                }
            };
        }

        v.setOnClickListener(mDismissLsn);
    }

    /**
     * 设置点击可以关闭dialog的v的id
     *
     * @param viewId
     */
    protected void setDismissClicker(int viewId) {
        if (mDismissLsn == null) {
            mDismissLsn = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                }
            };
        }

        View v = findViewById(viewId);
        if (v != null) {
            v.setOnClickListener(mDismissLsn);
        }
    }

    @Override
    public void initTitleBar() {
        // 没有titlebar
    }

    @Override
    public void startActivity(Class<?> clz) {
        mContext.startActivity(new Intent(mContext, clz));
    }

    @Override
    public void startActivity(Intent intent) {
        mContext.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<?> clz, int requestCode) {
    }

    @Override
    public void showToast(String content) {
        AppEx.showToast(content);
    }

    @Override
    public void showToast(@StringRes int... resId) {
        AppEx.showToast(resId);
    }

    protected void startService(Class<? extends Service> clz) {
        mContext.startService(new Intent(mContext, clz));
    }

    protected void startService(Intent intent) {
        mContext.startService(intent);
    }

    /**
     * 设置空白处黑暗度
     *
     * @param amount 0-1.0, 0为全透明. 1为全黑
     */
    protected void setDimAmount(float amount) {
        LayoutParams params = mDialog.getWindow().getAttributes();
        params.dimAmount = amount;
        mDialog.getWindow().setAttributes(params);
        mDialog.getWindow().addFlags(LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 设置对齐方式, 默认居中
     *
     * @param gravity {@link Gravity}
     */
    protected void setGravity(int gravity) {
        mDialog.getWindow().setGravity(gravity);
    }

    /**
     * 设置弹出动画方式
     *
     * @param dialogStyleId
     */
    protected void setAnimation(@StyleRes int dialogStyleId) {
        mDialog.getWindow().setWindowAnimations(dialogStyleId);
    }

    protected void setOnClickListener(int resId) {
        View v = findViewById(resId);
        if (v != null) {
            v.setOnClickListener(this);
        }
    }

    protected void setOnClickListener(View v) {
        if (v != null) {
            v.setOnClickListener(this);
        }
    }

    protected Context getContext() {
        return mContext;
    }

    protected View getContentView() {
        return mContentView;
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    public void setListener(OnDialogListener l) {
        mListener = l;
    }

    protected void callback(Object... params) {
        if (mListener != null) {
            mListener.callback(params);
        }
    }


    public void showView(View v) {
        if (v != null){
            if (v.getVisibility() != View.VISIBLE) {
                v.setVisibility(View.VISIBLE);
            }
        }
    }

    public void goneView(View v) {
        if (v != null){
            if (v.getVisibility() != View.GONE) {
                v.setVisibility(View.GONE);
            }
        }
    }

    public void hideView(View v) {
        if (v != null){
            if (v.getVisibility() != View.INVISIBLE) {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }
}
