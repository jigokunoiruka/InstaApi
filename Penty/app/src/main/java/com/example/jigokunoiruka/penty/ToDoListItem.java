package com.example.jigokunoiruka.penty;

import android.util.Log;

/**
 * Created by JigokuNoIruka on 2018/01/29.
 */

public class ToDoListItem {
    protected int id;
    protected String message;   // 品名
    protected String time;

    public ToDoListItem(int id, String message,String time) {
        this.id = id;
        this.message=message;
        this.time=time;
    }

    /**
     * IDを取得
     * getId()
     *
     * @return id int ID
     */
    public int getId() {
        Log.d("取得したID：", String.valueOf(id));
        return id;
    }



    /**
     * 品名を取得
     * getMessage()
     *
     * @return product String 品名
     */
    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
