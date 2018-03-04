package com.example.jigokunoiruka.penty;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by JigokuNoIruka on 2018/01/18.
 */

public class SendToActivity extends Activity {

    TextView titlebarSelf;
    TextView controltitlebarSelf;
    TextView titlebarFriends;
    TextView controlTitlebarFriends;
    RadioButton myName;
    boolean myNameRadiobuttoun;
    ImageButton sendButton;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sendto);
        findView();
        myName.setChecked(false);
        myNameRadiobuttoun=false;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThisActivity();
            }
        });

        myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myNameRadiobuttoun){
                    myName.setChecked(false);
                    myNameRadiobuttoun=false;
                }else{
                    Log.e("false","false");
                    myName.setChecked(true);
                    myNameRadiobuttoun=true;
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myName.isChecked()) {
                    Intent i = getIntent();
                    int[] alarmData = i.getIntArrayExtra("alarm");
                    Calendar calendar2 = Calendar.getInstance();
                    // 過去の時間は即実行されます
                    calendar2.set(Calendar.YEAR, alarmData[0]);
                    calendar2.set(Calendar.MONTH, alarmData[1]);
                    calendar2.set(Calendar.DATE, alarmData[2]);
                    calendar2.set(Calendar.HOUR_OF_DAY, alarmData[3]);
                    calendar2.set(Calendar.MINUTE, alarmData[4]);
                    calendar2.set(Calendar.SECOND, 0);
                    calendar2.set(Calendar.MILLISECOND, 0);

                    String messageStr = i.getStringExtra("message");
                    String finalTime = String.valueOf(alarmData[0]) + " " + String.valueOf(alarmData[1] + 1) + "/" + String.valueOf(alarmData[2]) + " " +
                            String.valueOf(alarmData[3]) + ":" + String.valueOf(alarmData[4]);

                    Intent intent = new Intent(getApplicationContext(), AlarmBroadCastReceiver.class);
                    intent.putExtra("intentId", 0);
                    intent.putExtra("message", messageStr);
                    intent.putExtra("title", finalTime);
                    PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                    // アラームをセットする
                    AlarmManager am = (AlarmManager) SendToActivity.this.getSystemService(ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pending);

                    Toast.makeText(SendToActivity.this, "メッセージを送信いたしました", Toast.LENGTH_SHORT).show();
                    saveList(messageStr,finalTime);
                }
            }
        });
    }


    private void saveList(String message,String finalTime) {

        // 各EditTextで入力されたテキストを取得
        String strMessage = message;

        // EditTextが空白の場合
        if (message.equals("")) {


        } else {

            // DBへの登録処理
            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き

            dbAdapter.saveDB(strMessage,getTime());   // DBに登録

            dbAdapter.closeDB();                                        // DBを閉じる

            ToDoDBAdapter toDoDBAdapter=new ToDoDBAdapter(this);
            toDoDBAdapter.openDB();
            toDoDBAdapter.saveDB(message,finalTime);
            toDoDBAdapter.closeDB();


            finishThisActivity();

        }

    }

    public void finishThisActivity(){
        setResult(1);
        SendToActivity.this.finish();
    }


    private String getTime(){
        String timeToStr;
        Calendar calendar=Calendar.getInstance();
        int textyear=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int textdate=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int mill=calendar.get(Calendar.MILLISECOND);
        timeToStr=String.valueOf(textyear)+String.valueOf(month)+String.valueOf(textdate)+String.valueOf(hour)+String.valueOf(minute)+String.valueOf(mill);

        return timeToStr;
    }


    public void findView(){
        titlebarSelf=findViewById(R.id.first_titlebar).findViewById(R.id.category_name);
        controltitlebarSelf=findViewById(R.id.first_titlebar).findViewById(R.id.control_list_text);
        titlebarFriends=findViewById(R.id.first_titlebar).findViewById(R.id.category_name);
        controlTitlebarFriends=findViewById(R.id.title_friendslist).findViewById(R.id.control_list_text);
        myName=findViewById(R.id.first_myname).findViewById(R.id.people_name_text);
        sendButton=findViewById(R.id.sendButton);
        backButton=findViewById(R.id.backButton);
        myName.setText("自分");

    }


}
