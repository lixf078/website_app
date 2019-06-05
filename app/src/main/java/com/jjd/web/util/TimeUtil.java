package com.jjd.web.util;

import android.text.format.DateFormat;


/**
 * 转换时间显示样式
 *
 * @author yuansui
 */
public class TimeUtil {


    public static String simple_ymd = "yyyy-MM-dd"; // 2016-08-21
    public static String from_y_24 = "yyyy-MM-dd HH:mm:ss"; // 24h 2016-08-21 18:22:22
    public static String from_y_to_m_24 = "yyyy-MM-dd HH:mm"; // 24h
    public static String from_y_to_h_12 = "yyyy-MM-dd hh"; // 12h
    public static String from_y_to_h_24 = "yyyy-MM-dd HH"; // 24h
    public static String from_y_12 = "yyyy-MM-dd hh:mm:ss"; // 12h
    public static String from_h_12 = "hh:mm:ss";
    public static String from_h_to_m_12 = "hh:mm";
    public static String from_h_to_m_24 = "HH:mm";
    public static String from_m = "mm:ss";
    public static String only_MM = "MM";
    public static String only_dd = "dd";
    public static String only_yy = "yyyy"; // 2016
    /**
     * 转换毫秒
     *
     * @param milli
     * @param format
     * @return
     */
    public static String formatMilli(long milli, String format) {
        return DateFormat.format(format, milli).toString();
    }

    public static String formatMilli(String milli, String format) {
        return formatMilli(Long.parseLong(milli), format);
    }

    /**
     * 转换秒
     *
     * @param second
     * @param format
     * @return
     */
    public static String formatSecond(long second, String format) {
        return formatMilli(second * 1000, format);
    }

    public static String formatSecond(String second, String format) {
        return formatSecond(Long.parseLong(second), format);
    }
}
