package com.example.jigokunoiruka.penty;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by JigokuNoIruka on 2018/01/24.
 */

public class MyInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // トークンが更新されたときに呼び出される
        String token = FirebaseInstanceId.getInstance().getToken();

        // tokenをサーバに送信するなど、端末外で永続的に保存するための処理をこのあたりに書きます
        // あとでテストに使用したいので、ここではLogcatに出力しています（本来はNG）
        if (!TextUtils.isEmpty(token)) {
            android.util.Log.d("FCM-TEST", "token = " + token);
        }
    }
}