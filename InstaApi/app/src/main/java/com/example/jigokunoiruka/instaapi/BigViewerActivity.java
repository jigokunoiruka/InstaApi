package com.example.jigokunoiruka.instaapi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by JigokuNoIruka on 2017/11/21.
 */

public class BigViewerActivity extends Activity{
    private ArrayList<AdapterItem> items;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        items=AsyncTaskGetImage.items;

        ListView listView=new ListView(this);
        listView.setScrollingCacheEnabled(false);
        listView.setAdapter(new MyAdapter());
        setContentView(listView);
    }

    private class MyAdapter extends BaseAdapter{
        //写真数の取得
        @Override
        public int getCount(){
            return items.size();
        }

        //写真の取得
        @Override
        public AdapterItem getItem(int pos){
            return items.get(pos);
        }

        //ID取得
        @Override
        public long getItemId(int pos){
            return pos;
        }

        //ビューの生成
        @Override
        public View getView(int pos, View view, ViewGroup parent){
            Context context=BigViewerActivity.this;
            AdapterItem item=items.get(pos);

            if(view==null){
                LinearLayout layout = new LinearLayout(context);
                layout.setBackgroundColor(Color.WHITE);
                layout.setPadding(10, 10, 10, 10);
                layout.setGravity(Gravity.CENTER_VERTICAL);
                view = layout;

                ImageView imageView = new ImageView(context);
                imageView.setTag("icon");
                imageView.setLayoutParams(new LinearLayout.LayoutParams(1000, 1000));
                layout.addView(imageView);
            }
            ImageView imageView=(ImageView)view.findViewWithTag("icon");
            imageView.setImageBitmap(item.icon);
            return view;
        }
    }

}
