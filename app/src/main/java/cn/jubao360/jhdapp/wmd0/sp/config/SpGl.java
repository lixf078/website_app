package cn.jubao360.jhdapp.wmd0.sp.config;

import android.content.Context;

import cn.jubao360.jhdapp.wmd0.sp.SpBase;

import java.util.Observable;


/**
 * @author lixf
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
