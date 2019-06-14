package cn.jubao360.jhdapp.wmd0.util;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * @author lixf
 */
public class CheckUtil {

    public static boolean checkNotNull(EditText et) {
        return !TextUtils.isEmpty(et.getText());
    }

    public static boolean checkNotNull(CharSequence text) {
        return !TextUtils.isEmpty(text);
    }

    /**
     * 检测密码有效性
     *
     * @param pwd
     * @return
     */
//    public static boolean pwd(CharSequence pwd) {
//        if (!checkNotNull(pwd)) {
//            ToastUtil.makeToast(R.string.toast_pwd_not_null);
//            return false;
//        } else if (pwd.length() < Constants.KMaxPwdLen) {
//            ToastUtil.makeToast(R.string.toast_pwd_len_err);
//            return false;
//        }
//        return true;
//    }

    /**
     * 检测电话号码
     *
     * @param phone
     * @param checkIllegal 是否检测真实性
     * @return
     */
//    public static boolean phone(CharSequence phone, boolean checkIllegal) {
//        if (TextUtils.isEmpty(phone)) {
//            ToastUtil.makeToast(R.string.toast_phone_not_null);
//            return false;
//        }
////        if (checkIllegal) {
////            if (!RegexUtil.isPhone(phone)) {
////                ToastUtil.makeToast(R.string.toast_phone_illegal);
////                return false;
////            }
////        }
//
//        return true;
//    }

    /**
     * 检测验证码
     *
     * @param captcha
     * @return
     */
//    public static boolean captcha(CharSequence captcha) {
//        if (!checkNotNull(captcha)) {
//            ToastUtil.makeToast(R.string.toast_captcha_not_null);
//            return false;
//        }
//        return true;
//    }

    /**
     * 检测用户名
     *
     * @param text
     * @return
     */
//    public static boolean userName(CharSequence text) {
//        if (!checkNotNull(text)) {
//            ToastUtil.makeToast(R.string.toast_user_name_not_null);
//            return false;
//        }
//        return true;
//    }
}
