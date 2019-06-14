package cn.jubao360.jhdapp.wmd0.sp;

import android.content.Context;

import java.util.Observable;


/**
 *
 * @author lixf
 */
public class Sp extends SpBase {

    private static final String KFileName = "sp_config";

    private static Sp mInst;

    public static String SP_DOWNLOAD_ID = "sp_download_id";
    public static String SP_DOWNLOAD_URL = "sp_download_url";

    private Sp(Context context, String fileName) {
        super(context, fileName);
    }

    public static Sp inst(Context context) {
        if (mInst == null) {
            mInst = new Sp(context, KFileName);
        }
        return mInst;
    }

    @Override
    public void update(Observable observable, Object data) {
        mInst = null;
    }

    public boolean isChinese() {
        return true;
    }

    @Override
    public synchronized boolean save(String key, Object value) {
        return super.save(key, value);
    }

    public synchronized boolean saveData(String key, Object value) {
        return super.save(key, value);
    }
}
