package cn.jubao360.jhdapp.wmd0.sp;

import java.util.Observable;

/**
 * 单例事件管理, 统一释放
 *
 * @author lixf
 */
public class SingletonEvent extends Observable {

    private static SingletonEvent mInst;

    private SingletonEvent() {
    }

    public static SingletonEvent inst() {
        if (mInst == null) {
            mInst = new SingletonEvent();
        }
        return mInst;
    }

    public void freeAll() {
        setChanged();
        notifyObservers();
        deleteObservers();
    }
}
