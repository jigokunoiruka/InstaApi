package com.example.jigokunoiruka.penty;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by JigokuNoIruka on 2018/01/09.
 */

public class AlarmBroadCastReceiver extends WakefulBroadcastReceiver {

    Context context;

    @Override   // データを受信した
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        Log.d("AlarmBroadcastReceiver","onReceive() pid=" + android.os.Process.myPid());

        int bid = intent.getIntExtra("intentId",0);
        String getMessage = intent.getStringExtra("message");
        String title=intent.getStringExtra("title");
        Bitmap bigIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.notify);

        Intent intent2 = new Intent(context, SplashActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, bid, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle(title);
        String[] messageArray = getMessage.split("\n");

        // 分割された文字列を行として追加
        for (int i=0; i < messageArray.length; i++) {
            inboxStyle.addLine(messageArray[i]);
        }


         Notification notification = new NotificationCompat.Builder(context)
         .setSmallIcon(R.drawable.small_icon)
         .setLargeIcon(bigIcon)
         .setColor(ContextCompat.getColor(context, R.color.colorNaviBack))
         .setTicker(getMessage)
         .setContentTitle("自分")
         .setContentText(messageArray[0])
         .setStyle(inboxStyle)
         // 音、バイブレート、LEDで通知
         .setDefaults(Notification.DEFAULT_ALL)
         // 通知をタップした時にMainActivityを立ち上げる
         .setContentIntent(pendingIntent)
         .build();

        // 通知
        notificationManager.notify(R.string.app_name, notification);
    }
}