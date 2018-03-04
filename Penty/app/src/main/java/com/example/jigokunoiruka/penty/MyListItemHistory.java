package com.example.jigokunoiruka.penty;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by JigokuNoIruka on 2018/01/13.
 */
public class MyListItemHistory {
    protected int id;           // ID
    protected String message;   // 品名

    public MyListItemHistory(int id, String message) {
        this.id = id;
        this.message=message;
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

}