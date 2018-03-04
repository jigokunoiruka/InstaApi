package com.example.jigokunoiruka.penty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by JigokuNoIruka on 2018/01/07.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        ImageView imageView= findViewById(R.id.imageView3);
        imageView.setImageResource(R.drawable.splash_clip);

        Handler handler = new Handler();
        handler.postDelayed(new splashHandler(), 2000);
    }

    class splashHandler implements Runnable {
        public void run() {
            Intent inte = new Intent(getApplication(), MainActivity.class);
            startActivity(inte);
            SplashActivity.this.finish();
        }
    }
}