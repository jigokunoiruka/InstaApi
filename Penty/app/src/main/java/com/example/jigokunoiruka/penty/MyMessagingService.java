package com.example.jigokunoiruka.penty;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by JigokuNoIruka on 2018/01/24.
 */

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // FCMメッセージを受信したときに呼び出される
        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            String title = notification.getTitle();
            String body = notification.getBody();

            android.util.Log.d("FCM-TEST", "メッセージタイプ: 通知\nタイトル: " + title + "\n本文: " + body);
        }
    }
}