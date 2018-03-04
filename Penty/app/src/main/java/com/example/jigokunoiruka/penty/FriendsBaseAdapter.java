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
 * Created by JigokuNoIruka on 2018/01/18.
 */

public class FriendsBaseAdapter  extends BaseAdapter {
    protected MyFriendsList myFriendsList;

    private Context context;
    private List<MyFriendsList> items;

    // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
    private class ViewHolder {
        TextView nameText;
    }

    // コンストラクタの生成
    public FriendsBaseAdapter(Context context, List<MyFriendsList> items) {
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
        FriendsBaseAdapter.ViewHolder holder;

        // データを取得
        myFriendsList = items.get(position);


        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_sendto, parent, false);

            TextView textHistory = view.findViewById(R.id.people_name_text);

            // holderにviewを持たせておく
            holder = new FriendsBaseAdapter.ViewHolder();
            holder.nameText = textHistory;
            view.setTag(holder);

        } else {
            // 初めて表示されるときにつけておいたtagを元にviewを取得する
            holder = (FriendsBaseAdapter.ViewHolder) view.getTag();
        }

        // 取得した各データを各TextViewにセット
        holder.nameText.setText(myFriendsList.getFriendsName());

        return view;

    }
}