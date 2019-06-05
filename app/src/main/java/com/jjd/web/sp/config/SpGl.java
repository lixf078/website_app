package com.jjd.web.sp.config;

import android.content.Context;

import com.jjd.web.sp.SpBase;

import java.util.Observable;


/**
 * @author yuansui
 */
public class SpGl extends SpBase {

    private static final String KFileName = "sp_gl";

    private static SpGl mInst;

    private SpGl(Context context, String fileName) {
        super(context, fileName);
    }

    public static SpGl inst(Context context) {
        if (mInst == null) {
            mInst = new SpGl(context, KFileName);
        }
        return mInst;
    }

    @Override
    public void update(Observable observable, Object data) {
        mInst = null;
    }
}
