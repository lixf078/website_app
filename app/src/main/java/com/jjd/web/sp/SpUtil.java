package com.jjd.web.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


@SuppressWarnings({"rawtypes", "unchecked"})
public class SpUtil {

    private static final String TAG = SpUtil.class.getSimpleName();

    /**
     * 根据类型自动匹配存储
     *
     * @param sp
     * @param key
     * @param value
     * @return
     */
    public static boolean save(SharedPreferences sp, String key, Object value) {
        if (value instanceof Integer) {
            return save(sp, key, (Integer) value);
        } else if (value instanceof String) {
            return save(sp, key, (String) value);
        } else if (value instanceof Long) {
            return save(sp, key, (Long) value);
        } else if (value instanceof Boolean) {
            return save(sp, key, (Boolean) value);
        }else if (value instanceof Serializable) {
            return save(sp, key, (Serializable) value);
        } else {
            Log.d(TAG, "未知类型 = " + value);
            return false;
        }
    }

    public static boolean save(Context ctx, String spName, String key, Object value) {
        SharedPreferences sp = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return save(sp, key, value);
    }

    private static boolean save(SharedPreferences sp, String key, Integer value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    private static boolean save(SharedPreferences sp, String key, Long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    private static boolean save(SharedPreferences sp, String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    private static boolean save(SharedPreferences sp, String key, Boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    private static boolean save(SharedPreferences sp, String key, Serializable data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            String dataString = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            save(sp, key, dataString);
            oos.close();
            baos.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean contains(SharedPreferences sp, String key) {
        return sp.contains(key);
    }

    public static Integer getInt(SharedPreferences sp, String key) {
        return sp.getInt(key, -1);
    }

    public static Integer getInt(SharedPreferences sp, String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static Integer getInt(Context ctx, String spName, String key, int defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * Get Long Value
     */
    public static long getLong(SharedPreferences sp, String key) {
        return sp.getLong(key, -1);
    }

    public static long getLong(SharedPreferences sp, String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    /**
     * Get String Value
     */
    public static String getString(SharedPreferences sp, String key) {
        return sp.getString(key, "");
    }

    public static String getString(SharedPreferences sp, String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static String getString(Context ctx, String spName, String key, String defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return getString(sp, key, defValue);
    }

    /**
     * get boolean value
     */
    public static Boolean getBoolean(SharedPreferences sp, String key) {
        return sp.getBoolean(key, false);
    }

    public static Boolean getBoolean(SharedPreferences sp, String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static Boolean getBoolean(Context ctx, String spName, String key, boolean defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return getBoolean(sp, key, defValue);
    }

    /**
     * clear all values
     */
    public static boolean clear(SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }

    public static boolean remove(SharedPreferences sp, String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean removeKeys(SharedPreferences sp, String... keys) {
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < keys.length; ++i) {
            editor.remove(keys[i]);
        }
        return editor.commit();
    }

    /**
     * @param sp
     * @param key
     * @return 没有值的时候返回null
     */
    public static Serializable getSerializable(SharedPreferences sp, String key) {
        String objBase64 = getString(sp, key);
        if (TextUtils.isEmpty(objBase64)) {
            return null;
        }
        byte[] base64Bytes = Base64.decode(objBase64.getBytes(), Base64.DEFAULT);

        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            Serializable ret = (Serializable) ois.readObject();
            ois.close();
            bais.close();
            return ret;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}
