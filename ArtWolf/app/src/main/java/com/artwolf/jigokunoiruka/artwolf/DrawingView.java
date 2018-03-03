package com.artwolf.jigokunoiruka.artwolf;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {

    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private ArrayList<Path> draw_List = new ArrayList<Path>();
    private int mPenColor = Color.RED;
    private Canvas canvas;
    DrawingActivity da = (DrawingActivity) this.getContext();
    boolean finish=false;
    boolean diapear=false;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mPenColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        path.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < draw_List.size(); i++) {
            Path pt = draw_List.get(i);

            canvas.drawPath(pt, paint);
        }
        // current
        if (path != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.drawPath(path, paint);
        }
    }

    public void changeColor(int color){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setColor(color);
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh) {
        if (finish==true){
            return;
        }
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (finish==true || diapear==true){
            return true;
        }

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(x - 1, y - 1);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x, y);
                da.strokeWasFinished();
                canvas.drawPath(path, paint);
                draw_List.add(path);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }

    public void delete() {
        path.reset();
        invalidate();
    }
}
