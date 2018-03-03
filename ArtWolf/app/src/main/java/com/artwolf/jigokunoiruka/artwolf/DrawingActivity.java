package com.artwolf.jigokunoiruka.artwolf;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.R.attr.countDown;
import static android.R.attr.id;

public class DrawingActivity extends AppCompatActivity implements MessageDialogFragment.OnOkButtonClickedListener{

    private DrawingView dv;
    private Button plusOneButton, minusOneButton;
    private TextView timerText;
    CountDown countDown;
    String[] player_names=new String[10];
    String[] player_shuffled=new String[10];
    String[] monster;
    String[] food;
    String[] fairyTail;
    String[] vehicle;
    String[] furniture;
    String[] machine;
    String[] subculture;
    String[] person;
    String[] sports;
    String[] facility;
    String[] fashion;
    String[] animal;
    String[] allCategory;

    String theme,category,wolfName,test;
    boolean lay_scene=false;
    int player_num,wolf,boo_dialog=0;
    int count=0;
    long timerNow=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        Log.e("kokoshra","toottenai") ;


        monster   =getResources().getStringArray(R.array.monster);
        food      =getResources().getStringArray(R.array.food);
        fairyTail =getResources().getStringArray(R.array.fairyTail);
        vehicle   =getResources().getStringArray(R.array.vehicle);
        furniture =getResources().getStringArray(R.array.furniture);
        machine   =getResources().getStringArray(R.array.machin);
        subculture=getResources().getStringArray(R.array.subculture);
        person    =getResources().getStringArray(R.array.person);
        sports    =getResources().getStringArray(R.array.sports);
        facility  =getResources().getStringArray(R.array.facility);
        fashion   =getResources().getStringArray(R.array.fasion);
        animal    =getResources().getStringArray(R.array.animal);
        allCategory=getResources().getStringArray(R.array.allCategory);

        player_names=getIntent().getStringArrayExtra("names");
        player_num=getIntent().getIntExtra("num",0);
        String[] player_shuffle=new String[player_num];
        Random r = new Random();
        wolf = r.nextInt(player_num);
        this.dv = (DrawingView)findViewById(R.id.drawing_view);
        countDown = new CountDown(180000, 1000);

        decideTheme();

        AdView mAdView = (AdView) findViewById(R.id.adViewdrawing);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        shuffle(player_shuffle);

        wolfName=player_shuffled[wolf];
        //checkPlayerDialog("あなたは"+player_shuffled[count]+"ですか？？", "YES",count);
        FraDialog(getString(R.string.drawingTime),String.format(getText(R.string.checkPlayer).toString(),player_shuffled[count]), "YES",1);

    }

    public void FraDialog(String title,String message,String btn, int key){ //key=1forWhatAreYou 2forTellTheme 3forDiscussion
        if(count/player_num==2 && key!=4){
            MessageDialogFragment dialog = MessageDialogFragment.newInstance(getString(R.string.disscussionTime),getString(R.string.disscussionExplain),"OK",3);
            dialog.show(getSupportFragmentManager(), "dialog");
            dv.finish=true;
            makeTimer();
            countDown.start();
            return;
        }else if(key==1) {
            boo_dialog = 1;
        }else if(key==4){
            boo_dialog=2;
        }else if(key==2){ //after telling theme
            boo_dialog=3;
        }
        dv.diapear=true;
        MessageDialogFragment dialog = MessageDialogFragment.newInstance(title,message,btn,key);
        dialog.show(getSupportFragmentManager(), "dialog");
        if(key==2){
            colorChange();
        }
    }



    public void colorChange(){
        int target=0;
        for(int i=0;i<player_num;i++){
            if(player_shuffled[(count)%player_num]==player_names[i]){
                target=i+1;
                break;
            }
        }
        String str="player"+String.valueOf(target);
        Log.e(str,player_shuffled[(count)%player_num]);
        int viewId = getResources().getIdentifier(str, "color", getPackageName());
        dv.changeColor(ContextCompat.getColor(this, viewId));
    }

    public String[] shuffle(String[] shuffling){
        for(int i=0;i<player_num;i++){
            shuffling[i]=player_names[i];
            makeNewText(i+1);
        }

        List<String> list= Arrays.asList(shuffling);

        // リストの並びをシャッフルします。
        Collections.shuffle(list);

        // listから配列へ戻します。
        shuffling =(String[])list.toArray(new String[list.size()]);

        for(int i=0;i<player_num;i++){
            player_shuffled[i]=shuffling[i];
        }

        return shuffling;
    }

    public void setAnswer(int num){
        String str;
        LinearLayout layout = (LinearLayout) findViewById(R.id.answer).findViewById(R.id.answertext);
        TextView edit = new TextView(this);
        if(player_names[num]==wolfName){
            str=player_names[num]+"     THE WOLF";
        }else{
            str=player_names[num]+"     "+theme;
        }
        edit.setText(str);
        edit.setTextSize(25);
        edit.setTextColor(Color.LTGRAY);
        layout.addView(edit);

    }

    public void makeNewText(int num){
        String str="player"+String.valueOf(num);
        int viewId = getResources().getIdentifier(str, "color", getPackageName());

        TextView edit = new TextView(this);
        RelativeLayout.LayoutParams prm = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        prm.setMargins(0, 0, 0, 0);
        edit.setText("■"+player_names[num-1]);
        edit.setTextColor(ContextCompat.getColor(this, viewId));
        edit.setId(num);


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_drawing).findViewById(R.id.drawing_names);
        layout.addView(edit, prm);


        //setContentView(layout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if(lay_scene==true){
            return;
        }
        //super.onWindowFocusChanged(hasFocus);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_drawing).findViewById(R.id.drawing_names);
        // 子供の数を取得
        int l = rl.getChildCount();
        // 無いなら何もしない
        if (l == 0) {
            return;
        }

        int max = rl.getWidth();

        int margin = 0;
        // 一番最初は基点となるので何もしない
        View pline = rl.getChildAt(0);
        // 一行全体の長さ
        int total=pline.getWidth() + (margin*2);

        for (int i = 1; i < l; i++) {

            int w = rl.getChildAt(i).getWidth() + (margin*2);
            test=String.valueOf(rl.getChildAt(i).getHeight());
            RelativeLayout.LayoutParams prm = (RelativeLayout.LayoutParams) rl
                    .getChildAt(i).getLayoutParams();
            // 横幅を超えないなら前のボタンの右に出す

            if (max > total + w) {
                total += w;
                prm.addRule(RelativeLayout.ALIGN_BOTTOM, i);
                prm.addRule(RelativeLayout.RIGHT_OF, i);
            }
            // 超えたら下に出す
            else {
                prm.addRule(RelativeLayout.BELOW, pline.getId());
                // 基点を変更
                pline = rl.getChildAt(i);
                // 長さをリセット
                total = pline.getWidth() + (margin*2);
            }


            rl.getChildAt(i).setLayoutParams(prm);
        }
    }

    public void makeTimer(){
        timerText=new TextView(this);
        plusOneButton=new Button(this);
        minusOneButton=new Button(this);

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_drawing).findViewById(R.id.timer);
        plusOneButton.setText("+1min");
        minusOneButton.setText("-1min");
        plusOneButton.setBackgroundResource(R.drawable.start_button);
        minusOneButton.setBackgroundResource(R.drawable.start_button);
        timerText.setText("0:00");
        timerText.setTextSize(40);

        ll.addView(plusOneButton,0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.addView(minusOneButton,0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.addView(timerText,1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        plusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDown.cancel();
                timerNow+=60000;
                countDown=new CountDown(timerNow,1000);
                countDown.start();
            }
        });

        minusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerNow-60000>1000){
                    countDown.cancel();
                    timerNow-=60000;
                    countDown=new CountDown(timerNow,1000);
                    countDown.start();
                }else{
                    countDown.cancel();
                    countDown=new CountDown(3000,1000);
                    countDown.start();
                }

            }
        });
    }


    class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 完了
            timerText.setText("0:00");

            FraDialog(getString(R.string.timeOut),getString(R.string.timeOutExplain),getString(R.string.checkTheAnswer),4);

        }

        // インターバルで呼ばれる
        @Override
        public void onTick(long millisUntilFinished) {
            // 残り時間を分、秒、ミリ秒に分割
            long mm = millisUntilFinished / 1000 / 60;
            long ss = millisUntilFinished / 1000 % 60;
            timerNow=millisUntilFinished;

            timerText.setText(String.format("%1$02d:%2$02d", mm, ss));
        }
    }

    public void decideTheme(){
        Random r=new Random();
        List<String> list;
        switch (r.nextInt(12)){
            case 0:
                category=allCategory[0];
                theme=monster[r.nextInt(monster.length-1)];
                break;
            case 1:
                category=allCategory[1];
                theme=food[r.nextInt(food.length-1)];
                break;
            case 2:
                category=allCategory[2];
                theme=fairyTail[r.nextInt(fairyTail.length-1)];
                break;
            case 3:
                category=allCategory[3];
                theme=vehicle[r.nextInt(vehicle.length-1)];
                break;
            case 4:
                category=allCategory[4];
                theme=furniture[r.nextInt(furniture.length-1)];
                break;
            case 5:
                category=allCategory[5];
                theme=machine[r.nextInt(machine.length-1)];
                break;
            case 6:
                category=allCategory[6];
                theme=subculture[r.nextInt(subculture.length-1)];
                break;
            case 7:
                category=allCategory[7];
                theme=person[r.nextInt(person.length-1)];
                break;
            case 8:
                category=allCategory[8];
                theme=sports[r.nextInt(sports.length-1)];
                break;
            case 9:
                category=allCategory[9];
                theme=facility[r.nextInt(facility.length-1)];
                break;
            case 10:
                category=allCategory[10];
                theme=fashion[r.nextInt(fashion.length-1)];
                break;
            case 11:
                category=allCategory[11];
                theme=animal[r.nextInt(animal.length-1)];
                break;
        }
    }

    public void strokeWasFinished() {
        //check
        FraDialog(getString(R.string.drawingTime),String.format(getText(R.string.checkPlayer).toString(),player_shuffled[count%player_num]), "YES",1);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(lay_scene==false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                findViewById(R.id.activity_drawing).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                findViewById(R.id.activity_drawing).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            } else {
                findViewById(R.id.activity_drawing).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }else if(lay_scene==true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                findViewById(R.id.answer).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                findViewById(R.id.answer).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            } else {
                findViewById(R.id.answer).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    }

    @Override
    public void onOkButtonClicked() {
        //今回は何もしない
        if(boo_dialog==1){ // check after AreYou
            if(count%player_num!=wolf) {
                //tellThemeDialog
                FraDialog(getString(R.string.drawingTime),String.format(getText(R.string.tellToPlayer).toString(),theme,category),"OK",2);
                //"テーマは"+category+"です。"+"\n"+"\n"+"お題は"+theme+"です。"
            }if(count%player_num==wolf){
                //tellThemeDialog
                FraDialog(getString(R.string.drawingTime),String.format(getText(R.string.tellToWolf).toString(),category),"OK",2);
            }
             count++;
        }else if(boo_dialog==2) { // after discuss
            lay_scene = true;
            setContentView(R.layout.answer);
            AdView mAdView = (AdView) findViewById(R.id.adViewAns);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            for (int i = 0; i < player_num; i++) {
                setAnswer(i);
            }
            Button oneMore = (Button) findViewById(R.id.button3);
            oneMore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
        }else if(boo_dialog==3) { //after theme
            dv.diapear = false;
        }
        boo_dialog=0;
    }

}
