package com.example.jigokunoiruka.instaapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity{
    private String text;
    private Handler handler=new Handler();
    //ここにアクセストークンの乗せたURLを貼る
    private final static String URL=<ここにURL>;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //レイアウトファイルの適用


        //スレッドの準備をする
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    text=new String(http2data(URL));    //Jsonを取得するための関数

                }catch(Exception e){
                    text=null;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(text!=null){
                            //editText.setText(text);
                            try {
                                // JSONな文字列をオブジェクトに変換
                                JSONObject json = new JSONObject(text);
                                // "data"の項目を取得し、ループ
                                JSONArray data = json.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    // imagesを取得
                                    JSONObject row = data.getJSONObject(i);
                                    String title = row.getString("images");

                                    //thumbnailを取得
                                    JSONObject thB= row.getJSONObject("images");
                                    String thbn=thB.getString("thumbnail");

                                    //urlを取得
                                    JSONObject url= thB.getJSONObject("thumbnail");
                                    String imageUrl=url.getString("url");

                                    //URLから取得した画像をAdapterItemに保存する
                                    ImageView imageView=(ImageView)findViewById(R.id.imageView3);
                                    Uri uri = Uri.parse(imageUrl);
                                    Uri.Builder builder = uri.buildUpon();
                                    AsyncTaskGetImage task = new AsyncTaskGetImage(imageView);
                                    task.execute(builder);


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                        }
                    }
                });
            }
        });
        thread.start();

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            // このメソッドがクリック毎に呼び出される
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ViewerActivity.class);
                startActivity(intent);
            }
        });
        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            // このメソッドがクリック毎に呼び出される
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, BigViewerActivity.class);
                startActivity(intent);
            }
        });
    }



    //Jsonを取得するための関数
    public static byte[] http2data(String path)throws Exception{
        byte[] w=new byte[1024];
        HttpURLConnection c=null;
        InputStream in=null;
        ByteArrayOutputStream out=null;
        try{
            java.net.URL url=new URL(path);
            c=(HttpURLConnection)url.openConnection();
            c.setRequestMethod("GET");
            in=c.getInputStream();

            out=new ByteArrayOutputStream();
            //ここでJsonを取得する
            while(true){
                int size=in.read(w);
                if(size<=0) break;
                out.write(w,0,size);
            }
            out.close();

            in.close();
            c.disconnect();
            return out.toByteArray();


        }catch (Exception e){
            try{
                if(c!=null)c.disconnect();
                if(in!=null)in.close();
                if(out!=null)out.close();
            }catch(Exception e2){

            }
            throw e;
        }
    }

}