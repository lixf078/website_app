package cn.jubao360.jhdapp.wmd0.util;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


/**
 * @author lixf
 */
public class NotificationUtil {

    private static NotificationManagerCompat mManager;

    public static Notification launch(Context c, int id, NotificationCompat.Builder builder) {
        Notification n = builder.build();
        launch(c, id, n);
        return n;
    }

    public static void launch(Context c, int id, Notification n) {
        getMgr(c).notify(id, n);
    }

    public static void cancel(Context c, int id) {
        getMgr( c).cancel(id);
    }

    public static void cancelAll(Context c ) {
        getMgr( c).cancelAll();
    }

    private static NotificationManagerCompat getMgr(Context c) {
        if (mManager == null) {
            mManager = NotificationManagerCompat.from(c);
        }
        return mManager;
    }
}
