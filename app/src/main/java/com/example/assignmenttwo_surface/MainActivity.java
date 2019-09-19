package com.example.assignmenttwo_surface;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.assignmenttwo_surface.R;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    Paint paint2;
    Path path;



    OurView ov;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float x, y;
    private boolean touching, drawingTouch, drawingTouch2;
    int drawingpic_x = 0, drawingpic_y = 0;
    int drawingpic_x2 = 0, drawingpic_y2 = 0;

    int drawingPicOffset_x = 0, drawingPicOffset_y = 0;
    int drawingPicOffset_x2 = 0, drawingPicOffset_y2 = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ov = new OurView(this);
        paint = new Paint();

        paint.setColor(Color.RED);
        ov.setOnTouchListener(this);

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
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int action = motionEvent.getAction();


        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = motionEvent.getX();
                y = motionEvent.getY();
                Log.d("position", "onTouch: x distance" + Math.abs(x - drawingpic_x)
                + " y distance: "+ Math.abs(y - drawingpic_y
                ));

                touching = true;
                if (100 >= (Math.abs(x - drawingpic_x)) && 100>= Math.abs(y - drawingpic_y ) ) {
                    drawingPicOffset_x = (int) x - drawingpic_x;
                    drawingPicOffset_y = (int) y - drawingpic_y;
                    drawingTouch = true;

                    path.moveTo(x,y);

                    Toast.makeText(this,"touch",Toast.LENGTH_SHORT).show();


                }

                if (50 >= (Math.abs(x - drawingpic_x2)) && 50>= Math.abs(y - drawingpic_y2 ) ) {
                    drawingPicOffset_x2 = (int) x - drawingpic_x2;
                    drawingPicOffset_y2 = (int) y - drawingpic_y2;
                    drawingTouch2 = true;

                    Toast.makeText(this,"touch 2",Toast.LENGTH_SHORT).show();
                }






                break;

            case MotionEvent.ACTION_MOVE:
                x = motionEvent.getX();
                y = motionEvent.getY();
                touching = true;
                if (drawingTouch) {
                    drawingpic_x = (int) x-drawingPicOffset_x;
                    drawingpic_y = (int) y-drawingPicOffset_y;
                    path.lineTo(x,y);
                }

                if(drawingTouch2){
                    drawingpic_x2 = (int) x-drawingPicOffset_x2;
                    drawingpic_y2 = (int) y-drawingPicOffset_y2;

                }
                break;



            default:
                drawingTouch = false;
                drawingTouch2 = false;
                touching = false;

            /*case  MotionEvent.ACTION_MOVE:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;

            case MotionEvent.ACTION_UP:

                break;*/
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
            path = new Path();
            paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setColor(Color.YELLOW);
            paint2.setStrokeJoin(Paint.Join.ROUND);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeWidth(200);

        }

        @Override
        public void run() {

            while(isItOk){

                if(!holder.getSurface().isValid()){
                   continue;
                }
                Canvas c = holder.lockCanvas();
                c.drawARGB(255,150,150,10);
                c.drawPath(path,paint2);
                //Rect rectangle = new Rect(0,0,100,100);
                //c.drawBitmap(ball, null, rectangle, null);
                c.drawCircle(drawingpic_x,drawingpic_y,100,paint);
                c.drawCircle(drawingpic_x2,drawingpic_y2,50,paint);

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