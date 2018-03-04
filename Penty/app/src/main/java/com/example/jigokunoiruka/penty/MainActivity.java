package com.example.jigokunoiruka.penty;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends Activity {

    private LinearLayout home;
    private LinearLayout people;
    private LinearLayout setting;
    private LinearLayout todo;
    TextView date;
    TextView time;
    TextView year;
    EditText message;
    ImageButton send_button;
    ImageButton back_button;
    ImageView imageDate;
    ImageView imageTime;
    int alarmData[]=new int[5];
    int homeScene=0;
    protected MyListItemHistory myListItemHistory;
    protected ToDoListItem toDoListItem;

    private DBAdapter dbAdapter;                // DBAdapter
    private MyBaseAdapter myBaseAdapter;       // ArrayAdapter
    private List<MyListItemHistory> items;            // List

    private ToDoBaseAdapter toDoBaseAdapter;
    private ToDoDBAdapter toDoDBAdapter;
    private List<ToDoListItem> todoItems;

    private ListView mListViewHistory;        // ListView
    private ListView todoListView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switch (homeScene){
                        case 0:
                            home.setVisibility(View.VISIBLE);
                            people.setVisibility(View.INVISIBLE);
                            setting.setVisibility(View.INVISIBLE);
                            todo.setVisibility(View.INVISIBLE);
                            back_button.setVisibility(View.INVISIBLE);
                            return true;
                        case 1:
                            home.setVisibility(View.INVISIBLE);
                            people.setVisibility(View.INVISIBLE);
                            setting.setVisibility(View.INVISIBLE);
                            todo.setVisibility(View.INVISIBLE);
                            back_button.setVisibility(View.VISIBLE);
                            return true;
                    }

                case R.id.navigation_people:
                    home.setVisibility(View.INVISIBLE);
                    people.setVisibility(View.VISIBLE);
                    setting.setVisibility(View.INVISIBLE);
                    todo.setVisibility(View.INVISIBLE);
                    back_button.setVisibility(View.INVISIBLE);
                    return true;

                case R.id.navigation_setting:
                    home.setVisibility(View.INVISIBLE);
                    people.setVisibility(View.INVISIBLE);
                    setting.setVisibility(View.VISIBLE);
                    todo.setVisibility(View.INVISIBLE);
                    back_button.setVisibility(View.INVISIBLE);
                    return true;

                case R.id.navigation_todo:
                    home.setVisibility(View.INVISIBLE);
                    people.setVisibility(View.INVISIBLE);
                    setting.setVisibility(View.INVISIBLE);
                    todo.setVisibility(View.VISIBLE);
                    back_button.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        findView();
        rightNow();

        items = new ArrayList<>();
        todoItems=new ArrayList<>();
        myBaseAdapter = new MyBaseAdapter(this, items);
        toDoBaseAdapter=new ToDoBaseAdapter(this,todoItems);
        showHistory();
        showToDoList();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("token","token====="+token);


        imageDate.setOnTouchListener(new ImageViewHighlighter());
        imageTime.setOnTouchListener(new ImageViewHighlighter());

        imageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });
        imageTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });


        // 日時を指定したアラーム
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplication(), SendToActivity.class);
                inte.putExtra("message",message.getText().toString());
                inte.putExtra("alarm",alarmData);
                startActivityForResult(inte,1);

                items.clear();
                showHistory();
                showToDoList();
                init();
            }
        });

        mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {
                    case R.id.cancel_button:
                        // IDを取得する
                        myListItemHistory = items.get(position);
                        int listId = myListItemHistory.getId();

                        dbAdapter.openDB();     // DBの読み込み(読み書きの方)
                        dbAdapter.selectDelete(String.valueOf(listId));     // DBから取得したIDが入っているデータを削除する
                        dbAdapter.closeDB();    // DBを閉じる
                        showHistory();
                        break;
                    default:
                        myListItemHistory = items.get(position);
                        String writeMessage = myListItemHistory.getMessage();
                        message.setText(writeMessage);
                        Log.e("",writeMessage);
                }
            }
        });

        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (view.getId()) {
                    case R.id.deleteTodo:
                        // IDを取得する
                        toDoListItem = todoItems.get(i);
                        int listId = toDoListItem.getId();

                        toDoDBAdapter.openDB();     // DBの読み込み(読み書きの方)
                        toDoDBAdapter.selectDelete(String.valueOf(listId));     // DBから取得したIDが入っているデータを削除する
                        toDoDBAdapter.closeDB();    // DBを閉じる
                        showToDoList();
                        break;
                    default:
                }
            }
        });


        BottomNavigationView navigation =findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void showHistory(){

        items.clear();

        dbAdapter = new DBAdapter(this);
        dbAdapter.openDB();     // DBの読み込み(読み書きの方)


        String[] columns = null;     // DBのカラム
        Cursor c = dbAdapter.getDB(columns);

        if (c.moveToFirst()) {
            do {
                myListItemHistory=new MyListItemHistory(c.getInt(0),c.getString(1));
                items.add(myListItemHistory);
                Log.d("取得したCursor:", c.getString(0));

            } while (c.moveToNext());
        }
        c.close();
        dbAdapter.closeDB();    // DBを閉じる


        mListViewHistory.setAdapter(myBaseAdapter);     //ListViewにアダプターをセット(=表示)
        // ArrayAdapterに対してListViewのリスト(items)の更新
        myBaseAdapter.notifyDataSetChanged();

    }

    public void showToDoList(){

        todoItems.clear();

        toDoDBAdapter = new ToDoDBAdapter(this);
        toDoDBAdapter.openDB();     // DBの読み込み(読み書きの方)


        String[] columns = null;     // DBのカラム
        Cursor c = toDoDBAdapter.getDB(columns);

        if (c.moveToFirst()) {
            do {
                toDoListItem=new ToDoListItem(c.getInt(0),c.getString(1),c.getString(2));
                todoItems.add(toDoListItem);
                Log.d("取得したCursor:", c.getString(0));

            } while (c.moveToNext());
        }
        c.close();
        toDoDBAdapter.closeDB();    // DBを閉じる


        todoListView.setAdapter(toDoBaseAdapter);     //ListViewにアダプターをセット(=表示)
        // ArrayAdapterに対してListViewのリスト(items)の更新
        toDoBaseAdapter.notifyDataSetChanged();
    }

    public void rightNow(){
        Calendar calendar=Calendar.getInstance();
        int textyear=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int textdate=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);

        alarmData[0]=textyear;
        alarmData[1]=month;
        alarmData[2]=textdate;
        alarmData[3]=hour;
        alarmData[4]=minute;

        String strDate = String.format("%02d/%02d", month+1, textdate);
        String strTime = String.format("%02d:%02d",hour,minute);

        year.setText(String.valueOf(textyear));
        date.setText(strDate);
        time.setText(strTime);
    }

    public void setDate(int getYear, int monthOfYear, int dayOfMonth) {

        String str = String.format("%02d/%02d", monthOfYear, dayOfMonth);
        String setYear=String.valueOf(getYear);

        alarmData[0]=getYear;
        alarmData[1]=monthOfYear;
        alarmData[2]=dayOfMonth;

        date.setText( str );
        year.setText(setYear);


    }

    public void setTime(int hour, int min) {

        String str = String.format("%02d:%02d", hour, min);

        alarmData[3]=hour;
        alarmData[4]=min;

        time.setText( str );

    }




    private void init() {
        message.setText("");
    }

    private void findView(){
        home=findViewById(R.id.home);
        people=findViewById(R.id.people);
        setting=findViewById(R.id.setting);
        todo=findViewById(R.id.todo);
        home.setVisibility(View.VISIBLE);
        people.setVisibility(View.INVISIBLE);
        setting.setVisibility(View.INVISIBLE);
        todo.setVisibility(View.INVISIBLE);



        send_button = findViewById(R.id.sendButton);
        back_button=findViewById(R.id.backButton);
        back_button.setVisibility(View.INVISIBLE);

        message=findViewById(R.id.editText5);

        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        imageDate=findViewById(R.id.imageDate);
        imageTime=findViewById(R.id.imageTime);
        year=findViewById(R.id.year);
        mListViewHistory=findViewById(R.id.history_list);
        todoListView=findViewById(R.id.todo_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        showHistory();
        showToDoList();
    }

}
