package com.jjd.web.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.jjd.web.R;

/**
 * 简单样式dialog
 * text，确定，取消button
 *
 * @author lixufeng
 */
public final class RegSuccessDialog extends DialogEx {

    private TextView mTv;
    private TextView mTitle;
    private TextView mConfirmBtn;

    public RegSuccessDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getContentViewId() {
        return R.layout.dialog_user_reg_success;
    }

    @Override
    public void initData() {
    }

    @Override
    public void findViews() {
        mTitle = findView(R.id.ic_reg_success_tv);
        mTv = findView(R.id.dialog_sample_tv_content);
        mConfirmBtn = findView(R.id.dialog_confirm);
    }

    @Override
    public void setViewsValue() {
        setOnClickListener(mConfirmBtn);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_confirm){
            callback(1);
        }
        dismiss();
    }

    public void setContent(@StringRes int id) {
        mTv.setText(id);
    }

    public void setContent(CharSequence text) {
        mTv.setText(text);
    }

    public void setConfirmBtnText(@StringRes int id){
        mConfirmBtn.setText(id);
    }

    public void setConfirmBtnText(String id){
        mConfirmBtn.setText(id);
    }

    public void setTitle(@StringRes int id) {
        mTitle.setText(id);
    }

    public void setTitle(CharSequence text) {
        mTitle.setText(text);
    }
}
