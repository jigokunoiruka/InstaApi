package com.artwolf.jigokunoiruka.artwolf;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.R.attr.id;
import static java.security.AccessController.getContext;


public class SettingActivity extends AppCompatActivity {

    int player_num=3;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    String[] player_names= new String[10];
    DetectableKeyboardEventLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LinearLayout linearLayout = (LinearLayout)findViewById(R.layout.activity_setting);
        setContentView(R.layout.activity_setting);
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_set);
        layout = (DetectableKeyboardEventLayout) findViewById(R.id.activity_setting);
        layout.setKeyboardListener(new DetectableKeyboardEventLayout.KeyboardListener() {
            @Override
            public void onKeyboardShown() {
                Log.e("shown","");
                RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_set);
                //ViewGroup.LayoutParams layoutParams=rl.getLayoutParams();
                //layoutParams.height=0;
                rl.setVisibility(View.GONE);
            }
            @Override
            public void onKeyboardHidden() {
                Log.e("hidden","");
                RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl_set);
                //ViewGroup.LayoutParams layoutParams=rl.getLayoutParams();
                //layoutParams.height=600;
                rl.setVisibility(View.VISIBLE);
                onResume();
            }
        });


        Button minus = (Button)findViewById(R.id.button);
        Button plus = (Button)findViewById(R.id.button2);
        Button next= (Button)findViewById(R.id.next);
        final TextView p_num=(TextView) findViewById(R.id.textView2);

        makeNewEdit(1);
        makeNewEdit(2);
        makeNewEdit(3);

        AdView mAdView = (AdView) findViewById(R.id.adViewSet);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView mAdViewTop = (AdView) findViewById(R.id.adViewSetTop);
        AdRequest adRequestTop = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequestTop);

        minus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(player_num!=3){
                    player_num-=1;
                    p_num.setText(String.valueOf(player_num));
                    deleteLastEdit(player_num);
                }

            }
        });
        plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(player_num!=10){
                    player_num+=1;
                    p_num.setText(String.valueOf(player_num));
                    makeNewEdit(player_num);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                LinearLayout layout = (LinearLayout)findViewById(R.id.activity_setting).findViewById(R.id.name_lay);

                for(int i=0 ; i<player_num ; i++){
                    EditText edit=(EditText) layout.getChildAt(i);
                    player_names[i]=edit.getText().toString();
                    if(player_names[i].length()==0){
                        player_names[i]=edit.getHint().toString();
                    }
                }
                Intent intent=new Intent(SettingActivity.this, DrawingActivity.class);
                intent.putExtra("names",player_names);
                intent.putExtra("num",player_num);
                startActivity(intent);
            }
        });

    }


    public void makeNewEdit(int num){
        String str="player"+String.valueOf(num);
        int viewId = getResources().getIdentifier(str, "color", getPackageName());

        EditText edit = new EditText(this);
        Drawable back = edit.getBackground();
        back.setColorFilter(ContextCompat.getColor(this, viewId), PorterDuff.Mode.SRC_IN);

        edit.setHint(str);
        edit.setHintTextColor(ContextCompat.getColor(this,viewId));
        edit.setTextColor(ContextCompat.getColor(this, viewId));
        edit.setInputType(InputType.TYPE_CLASS_TEXT);


        LinearLayout layout = (LinearLayout)findViewById(R.id.activity_setting).findViewById(R.id.name_lay);
        layout.addView(edit,num-1, new LinearLayout.LayoutParams(MP,WC));

        //setContentView(layout);
    }

    public void deleteLastEdit(int num){
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity_setting).findViewById(R.id.name_lay);
        layout.removeViewAt(num);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.activity_setting).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            findViewById(R.id.activity_setting).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            findViewById(R.id.activity_setting).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }



}
