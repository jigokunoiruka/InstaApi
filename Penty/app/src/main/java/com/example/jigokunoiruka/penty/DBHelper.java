package com.example.jigokunoiruka.penty;

/**
 * Created by JigokuNoIruka on 2018/01/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベースの生成やアップグレードを管理するSQLiteOpenHelperを継承したクラス
 * DBHelper
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "history.db";      // DB名
    private final static String DB_TABLE = "historys";       // DBのテーブル名
    private final static String ToDo_TABLE="todo";
    private final static String Friends_TABLE="friends";
    private final static int DB_VERSION = 1;                // DBのバージョン

    /**
     * DBのカラム名
     */
    public final static String COL_ID = "_id";             // id

    public final static String COL_HISTORYS = "message_history";    // 履歴
    public final static String COL_LastTime = "last_time";    // 最後に使った日時

    public final static String COL_ToDo = "ToDos";    // 履歴
    public final static String COL_Time = "whenToDo";    // 最後に使った日時

    public final static String COL_FriendsName="FriendsName";
    public final static String COL_FriendsID="FriendsID";
    public final static String COL_FriendsDate="FriendsDate";
    // コンストラクタ
    public DBHelper(Context context) {
        //第1引数：コンテキスト
        //第2引数：DB名
        //第3引数：factory nullでよい
        //第4引数：DBのバージョン
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * DB生成時に呼ばれる
     * onCreate()
     *
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //テーブルを作成するSQL文の定義 ※スペースに気を付ける
        String createTbl = "CREATE TABLE " + DB_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_HISTORYS + " TEXT NOT NULL,"
                + COL_LastTime + " TEXT NOT NULL"
                + ");";

        String createTbl2 = "CREATE TABLE " + ToDo_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_ToDo + " TEXT NOT NULL,"
                + COL_Time + " TEXT NOT NULL"
                + ");";

        String createTbl3 = "CREATE TABLE " + Friends_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_FriendsName + " TEXT NOT NULL,"
                + COL_FriendsID + " TEXT NOT NULL,"
                + COL_FriendsDate +" TEXT NOT NULL"
                + ");";

        db.execSQL(createTbl);      //SQL文の実行
        db.execSQL(createTbl2);
        db.execSQL(createTbl3);
    }

    /**
     * DBアップグレード(バージョンアップ)時に呼ばれる
     *
     * @param db         SQLiteDatabase
     * @param oldVersion int 古いバージョン
     * @param newVersion int 新しいバージョン
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DBからテーブル削除
        db.execSQL("DROP TABLE IF EXISTS'" + DB_TABLE+"'");
        db.execSQL("DROP TABLE IF EXISTS'" + ToDo_TABLE+"'");
        db.execSQL("DROP TABLE IF EXISTS'" + Friends_TABLE+"'");
        // テーブル生成
        onCreate(db);
    }
}