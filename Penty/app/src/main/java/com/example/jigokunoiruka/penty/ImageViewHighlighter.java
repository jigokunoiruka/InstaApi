package com.example.jigokunoiruka.penty;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by JigokuNoIruka on 2018/01/09.
 */

public class ImageViewHighlighter implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ((ImageView) v).setColorFilter(Color.argb(100, 0, 0, 0));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ((ImageView) v).setColorFilter(null);
                break;
        }
        return false;
    }
}
