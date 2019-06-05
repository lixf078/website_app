package com.jjd.web.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * @author yuansui
 */
public class NotificationBuilder {

    public static NotificationCompat.Builder message(Context context,
                                                     Intent intent,
                                                     @DrawableRes int logoId,
                                                     String title,
                                                     String content,
                                                     String ticker) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(logoId)
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS) // 声音,呼吸灯提醒为默认属性
                .setOngoing(false);

        return builder;
    }

    public static NotificationCompat.Builder downloadApk(Context context,
                                                         Intent intent,
                                                         @DrawableRes int logoId,
                                                         RemoteViews views,
                                                         String ticker) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setCustomContentView(views)
                .setContentIntent(pendingIntent)
                .setSmallIcon(logoId)
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setDefaults(Notification.DEFAULT_LIGHTS) // 呼吸灯提醒为默认属性
                .setOngoing(false);

        return builder;
    }
}
