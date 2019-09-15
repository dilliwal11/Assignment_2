package com.example.assignmenttwo_surface;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.assignmenttwo_surface.R;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    OurView ov;
    Bitmap ball;
    Paint paint = null;

    float x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ov = new OurView(this);
        paint = new Paint();
        paint.setColor(Color.RED);
        ov.setOnTouchListener(this);
        ball = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);

        x =  y = 0;
        setContentView(ov);


    }

    @Override
    protected void onResume() {
        super.onResume();

        ov.resume();

    }


    @Override
    protected void onPause() {
        super.onPause();
        ov.pause();



    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = motionEvent.getX();
                y = motionEvent.getY();

                break;


            case  MotionEvent.ACTION_MOVE:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;

            case MotionEvent.ACTION_UP:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
        }





        return true;
    }

    public class OurView extends SurfaceView implements Runnable {


        Thread t = null;
        SurfaceHolder holder;
        boolean isItOk = false;





        public OurView(Context context) {
            super(context);

            holder = getHolder();
        }

        @Override
        public void run() {

            while(isItOk){

                if(!holder.getSurface().isValid()){
                   continue;
                }
                Canvas c = holder.lockCanvas();
                c.drawARGB(255,150,150,10);
                //Rect rectangle = new Rect(0,0,100,100);
                //c.drawBitmap(ball, null, rectangle, null);
                c.drawCircle(x,y,100,paint);
                //c.drawBitmap(ball,x,y,null);
                holder.unlockCanvasAndPost(c);



            }

        }


        public void pause() {
            isItOk = false;

            while(true){

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;

            }
            t = null;
        }

        public void resume() {

            isItOk = true;
            t = new Thread(this);
            t.start();
        }
    }
}