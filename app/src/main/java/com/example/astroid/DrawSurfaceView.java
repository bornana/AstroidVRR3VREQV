package com.example.astroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;

import java.util.Random;

/**
 * Created by asafamir on 19/05/2017.
 */

public class DrawSurfaceView extends SurfaceView implements Runnable {
    int interval = 40, HitPoints = 3;//try to change it
    float dx = 15;
    float dy = 15;
    float antidy = 15;
    Context context;
    SurfaceHolder holder;
    TextView hp;
    boolean threadRunning = true;
    boolean isRunning = true;
    boolean reachedLimit = true;
    Bitmap astro4bitmap, antiastro4bitmap;
    float x = 450;
    float y = 1600;
    int temp = this.getHeight();
    int AmountOfAsteroids = 3;
    float antix, antiy = 0;
    AntiAsteroid[] asteroids = new AntiAsteroid[AmountOfAsteroids];
    boolean[] GotHit = new boolean[3];
    Random r = new Random();
    CharSequence texty = "HitPoints left";


    public DrawSurfaceView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();
        astro4bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.astro4);
        antiastro4bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.antiastro4);
    }

    @Override
    public void run() {
        while (threadRunning) {
            if (isRunning) {
                if (!holder.getSurface().isValid())
                    continue;

                Canvas c = null;
                try {
                    c = this.getHolder().lockCanvas();//what with line meaning?
                    synchronized (this.getHolder()) {
                        c.drawRGB(100, 100, 255);//Try pushing this line into a remark. what happened? you can change the color.
                        c.drawBitmap(astro4bitmap, x, y, null);
                        //this.getHeight();

                        if (reachedLimit) {

                            for (int i = 0; i < 3; i++) {

                                antix = r.nextInt(this.getWidth());
                                asteroids[i] = new AntiAsteroid(antix, 0, antidy, false);
                                asteroids[i].SetDY(antidy + r.nextInt(11));
                                c.drawBitmap(antiastro4bitmap, asteroids[i].GetX(), 0, null);
                            }
                            reachedLimit = false;
                        } else {
                            for (int j = 0; j < 3; j++) {

                                c.drawBitmap(antiastro4bitmap, asteroids[j].GetX(), asteroids[j].GetY(), null);
                                if (Math.abs(asteroids[j].GetX() - x) < 140 && Math.abs(asteroids[j].GetY() - y) < 140) {
                                    //Pause();
                                    if (!asteroids[j].GetHit()) {
                                        //System.out.println(HitPoints);
                                        asteroids[j].SetHit(true);
                                        //hp.setText(texty);
                                        //Toast.makeText(context,"hp left", Toast.LENGTH_SHORT).show();
                                        HitPoints--;
                                        if (HitPoints <= 0) {
                                            Pause();
                                        }
                                    }
                                }

                            }
                        }


                        automaticMove();
                    }
                    Thread.sleep(interval);
                } catch (Exception e) {

                }
                /*what is finally?
                 */ finally {
                    if (c != null) {
                        this.getHolder().unlockCanvasAndPost(c);//what this line meaning?
                    }
                }
            }
        }
    }

    /*
    how it works?
     */
    public void automaticMove() {
        int counter = 0;
        x = x + dx;
        y = y + dy;
        if (x < 0 || x > this.getWidth())
            dx = -dx;
        if (y < 0 || y > this.getHeight())
            dy = -dy;
        antiy = antiy + asteroids[0].GetDY();
        for (int d = 0; d < asteroids.length; d++) {
            if (asteroids[d].GetY() < 0 || asteroids[d].GetY() > this.getHeight()) {
                asteroids[d].SetY(0);
                antix = r.nextInt(this.getWidth());
                antidy += 3;
                counter++;
                if (counter == 3) {
                    reachedLimit = true;
                }
            }
        }
        for (int z = 0; z < asteroids.length; z++) {
            asteroids[z].SetY(asteroids[z].GetY() + asteroids[z].GetDY());
        }
    }

    public void moveD() {
        if (dy < 0) {
            dy = -dy;
        }
        //y = y + Math.abs(dy);
        if (y < 0 || y > this.getHeight())
            dy = -dy;
    }

    public void moveU() {
        if (dy > 0) {
            dy = -dy;
        }
        //y = y - Math.abs(dy);
        if (y < 0 || y > this.getHeight())
            dy = -dy;
    }

    public void moveR() {
        if (dx < 0) {
            dx = -dx;
        }
        //x = x + Math.abs(dx);
        if (y < 0 || y > this.getHeight())
            dx = -dx;
    }

    public void moveL() {
        if (dx > 0) {
            dx = -dx;
        }
        //x = x - Math.abs(dx);
        if (y < 0 || y > this.getHeight())
            dx = -dx;
    }

    public void Pause() {
        dx = 0;
        dy = 0;
        for (int h = 0; h < asteroids.length; h++) {
            asteroids[h].SetDY(0);
        }
    }

    public void Resume() {
        dx = 15;
        dy = 15;
        for (int h = 0; h < asteroids.length; h++) {
            asteroids[h].SetDY(antidy);
        }
    }
}
