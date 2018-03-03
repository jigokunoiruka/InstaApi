package com.artwolf.jigokunoiruka.artwolf;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MessageDialogFragment extends DialogFragment {

    private static final String BUTTON = "button";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String KEY ="key";

    private String mTitle;
    private String mMessage;
    private String mButton;
    private int mKey;

    //OKボタンが押されたことを通知するリスナー
    private OnOkButtonClickedListener mListener;

    public static MessageDialogFragment newInstance(String title, String message,String button,int key) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        Bundle args = new Bundle();
        args.putString(BUTTON, button);
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        args.putInt(KEY,key);

        fragment.setArguments(args);
        return fragment;
    }

    public MessageDialogFragment() {
        //何も書かない
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mMessage = getArguments().getString(MESSAGE);
            mButton= getArguments().getString(BUTTON);
            mKey=getArguments().getInt(KEY);
        }
        final Activity activity = getActivity();
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.setContentView(R.layout.fragment_message_dialog);
        dialog.setCanceledOnTouchOutside(false);

        TextView tv_title=(TextView)dialog.findViewById(R.id.textView6) ;
        TextView tv=(TextView)dialog.findViewById(R.id.text_message) ;
        Button btn=(Button)dialog.findViewById(R.id.button4);
        btn.setText(mButton);
        btn.setWidth(100);
        tv_title.setText(mTitle);
        tv.setText(mMessage);
        if(mKey==0) {
            CharSequence styledText = Html.fromHtml(mMessage);
            tv.setText(styledText);
        }else if(mKey==1){
            btn.setWidth(160);
        }


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mKey==0){
                    ((MainActivity) activity).onOkButtonClicked();
                }else {
                    ((DrawingActivity) activity).onOkButtonClicked();
                }
                dismiss();
            }
        });
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        //リスナーのセット
        super.onAttach(activity);
        try {
            mListener = (OnOkButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnOkButtonListener");
        }
    }

    @Override
    public void onDetach() {
        //リスナーの削除
        super.onDetach();
        mListener = null;
    }

    public interface OnOkButtonClickedListener {
        public void onOkButtonClicked();
    }
}