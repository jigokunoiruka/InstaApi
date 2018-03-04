package com.example.jigokunoiruka.penty;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by JigokuNoIruka on 2018/01/17.
 */

public class ListViewHighlighter implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(Color.argb(20, 0, 0, 0));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                v.setBackgroundColor(Color.argb(100, 255, 255, 255));
                break;
        }
        return false;
    }
}
