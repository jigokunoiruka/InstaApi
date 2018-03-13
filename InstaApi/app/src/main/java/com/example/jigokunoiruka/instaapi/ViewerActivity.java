package com.example.jigokunoiruka.instaapi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by JigokuNoIruka on 2017/11/21.
 */

public class ViewerActivity extends Activity {
    private ArrayList<AdapterItem> items;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        items=AsyncTaskGetImage.items;

        GridView gridView=new GridView(this);
        gridView.setNumColumns(4);
        gridView.setAdapter(new MyAdapter());
        setContentView(gridView);
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        //写真数の取得
        public int getCount(){
            return items.size();
        }

        //写真の取得
        @Override
        public Object getItem(int pos){
            return items.get(pos);
        }

        //ID取得
        @Override
        public long getItemId(int pos){
            return pos;
        }

        //ビューの生成
        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            Context context = ViewerActivity.this;
            AdapterItem item = items.get(pos);

            if (view == null) {
                LinearLayout layout = new LinearLayout(context);
                layout.setBackgroundColor(Color.WHITE);
                layout.setPadding(10, 40, 10, 10);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER);
                view = layout;

                ImageView imageView = new ImageView(context);
                imageView.setTag("icon");
                imageView.setLayoutParams(new LinearLayout.LayoutParams(250, 250));
                layout.addView(imageView);
            }
            ImageView imageView=(ImageView)view.findViewWithTag("icon");
            imageView.setImageBitmap(item.icon);
            return view;
        }

    }
}