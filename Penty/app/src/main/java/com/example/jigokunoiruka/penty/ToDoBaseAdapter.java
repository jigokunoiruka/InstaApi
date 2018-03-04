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
 * Created by JigokuNoIruka on 2018/01/29.
 */

public class ToDoBaseAdapter extends BaseAdapter{
    protected ToDoListItem toDoListItem;

    private Context context;
    private List<ToDoListItem> items;

    // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
    private class ViewHolder {
        TextView timeText;
        TextView todoText;
        Button deleteButton;
    }

    // コンストラクタの生成
    public ToDoBaseAdapter(Context context, List<ToDoListItem> items) {
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
        ToDoBaseAdapter.ViewHolder holder;

        // データを取得
        toDoListItem = items.get(position);


        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_todo, parent, false);

            TextView timeText = view.findViewById(R.id.todoDate);
            TextView todoText=view.findViewById(R.id.todoContents);

            // holderにviewを持たせておく
            holder = new ToDoBaseAdapter.ViewHolder();
            holder.timeText = timeText;
            holder.todoText=todoText;
            holder.deleteButton = view.findViewById(R.id.deleteTodo);
            view.setTag(holder);


        } else {
            // 初めて表示されるときにつけておいたtagを元にviewを取得する
            holder = (ToDoBaseAdapter.ViewHolder) view.getTag();
        }

        // 取得した各データを各TextViewにセット
        holder.todoText.setText(toDoListItem.getTime());
        holder.timeText.setText(toDoListItem.getMessage());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.deleteTodo);
            }
        });

        return view;

    }
}
