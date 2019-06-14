package cn.jubao360.jhdapp.wmd0.util.permission;

public interface OnPermissionListener {
    void onPermissionResult(int code, @PermissionResult int result);
}