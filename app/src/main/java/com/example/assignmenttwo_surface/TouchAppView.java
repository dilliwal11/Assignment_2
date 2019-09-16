package com.example.assignmenttwo_surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;



public class TouchAppView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float x, y;
    private boolean touching, drawingTouch;
    int drawingpic_x = 0, drawingpic_y = 0;

    int drawingPicOffset_x = 0, drawingPicOffset_y = 0;

    public TouchAppView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(drawingpic_x, drawingpic_y,100, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                touching = true;
                if ((x > drawingpic_x) && (x < drawingpic_x + 100)
                        && (y > drawingpic_y) && (y < drawingpic_y + 100)) {
                    drawingPicOffset_x = (int) x - drawingpic_x;
                    drawingPicOffset_y = (int) y - drawingpic_y;
                    drawingTouch = true;
                }
                break;
           /* case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                touching = true;
                if (drawingTouch) {
                    drawingpic_x = (int) x-drawingPicOffset_x;
                    drawingpic_y = (int) y-drawingPicOffset_y;
                }
                break;*/
            case MotionEvent.ACTION_UP:
                   /* drawingpic_x=0;
                    drawingpic_y=0;*/
            default:
                drawingTouch = false;
                touching = false;
        }
        invalidate();
        return true;
    }
}
