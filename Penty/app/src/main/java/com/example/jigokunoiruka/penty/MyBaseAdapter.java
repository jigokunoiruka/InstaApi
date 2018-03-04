package com.example.jigokunoiruka.penty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JigokuNoIruka on 2018/01/17.
 */

public class MyBaseAdapter extends BaseAdapter {
    protected MyListItemHistory myListItemHistory;

    private Context context;
    private List<MyListItemHistory> items;

    // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
    private class ViewHolder {
        TextView textHistory;
        Button deleteButton;
    }

    // コンストラクタの生成
    public MyBaseAdapter(Context context, List<MyListItemHistory> items) {
        this.context = context;
        this.items = items;
    }

    // Listの要素数を返す
    @Override
    public int getCount() {
        return items.size();
    }

    // indexやオブジェクトを返す
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    // IDを他のindexに返す
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 新しいデータが表示されるタイミングで呼び出される
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        // データを取得
        myListItemHistory = items.get(position);


        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_list, parent, false);

            TextView textHistory = view.findViewById(R.id.history_list_text);

            // holderにviewを持たせておく
            holder = new ViewHolder();
            holder.textHistory = textHistory;
            holder.deleteButton = view.findViewById(R.id.cancel_button);
            view.setTag(holder);

        } else {
            // 初めて表示されるときにつけておいたtagを元にviewを取得する
            holder = (ViewHolder) view.getTag();
        }

        // 取得した各データを各TextViewにセット
        holder.textHistory.setText(myListItemHistory.getMessage());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.cancel_button);
            }
        });

        view.setOnTouchListener(new ListViewHighlighter());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.layout.row_list);
            }
        });

        return view;

    }
}