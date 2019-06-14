package cn.jubao360.jhdapp.wmd0.util;

/**
 * @author lixf
 */
abstract public class UrlUtil {

    protected static String mBase = null;
    private static void init() {
    }
    public static String getBaseUrl() {
        return mBase;
    }

}
