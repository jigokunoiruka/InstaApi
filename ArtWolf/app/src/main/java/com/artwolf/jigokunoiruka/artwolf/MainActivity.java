package com.artwolf.jigokunoiruka.artwolf;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements MessageDialogFragment.OnOkButtonClickedListener{

    TextView tv,tv2;
    boolean rule_visible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.start);
        Button rule = (Button)findViewById(R.id.rules);

        tv=(TextView)findViewById(R.id.textView4);
        String explain=getString(R.string.rule);
        CharSequence styledText = Html.fromHtml(explain);
        tv.setText(styledText);
        tv.setVisibility(View.GONE);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView mAdViewTop = (AdView) findViewById(R.id.adViewMainTop);
        AdRequest adRequestTop = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequestTop);

        // クリックイベントを受け取れるようにする
        btn.setOnClickListener(new View.OnClickListener() {
            // このメソッドがクリック毎に呼び出される
            public void onClick(View v) {
                // ここにクリックされたときの処理を記述
                Intent intent=new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        rule.setOnClickListener(new View.OnClickListener() {
            // このメソッドがクリック毎に呼び出される
            public void onClick(View v) {
                // ここにクリックされたときの処理を記述

                MessageDialogFragment dialog = MessageDialogFragment.newInstance(getString(R.string.ruleTitle), getString(R.string.rule),"OK",0);
                dialog.show(getSupportFragmentManager(), "dialog");
                /*
                if(rule_visible) {
                    tv.setVisibility(View.GONE);
                    rule_visible=false;
                }else{
                    tv.setVisibility(View.VISIBLE);
                    rule_visible=true;
                }*/
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.activity_main).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            findViewById(R.id.activity_main).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            findViewById(R.id.activity_main).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    @Override
    public void onOkButtonClicked() {
        //今回は何もしない
    }
}
