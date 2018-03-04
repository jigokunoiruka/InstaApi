package com.example.jigokunoiruka.penty;

import android.util.Log;

/**
 * Created by JigokuNoIruka on 2018/01/18.
 */

public class MyFriendsList {
    protected int id;           // ID
    protected String friendsName;   // 品名

    public MyFriendsList(int id, String friendsName) {
        this.id = id;
        this.friendsName=friendsName;
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
    public String getFriendsName() {
        return friendsName;
    }


}
