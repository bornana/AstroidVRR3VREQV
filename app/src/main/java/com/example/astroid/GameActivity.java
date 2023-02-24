package com.example.astroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    DrawSurfaceView ds;
    Thread thread;
    FrameLayout frame;
    TextView hp;
    Button bUp,bDown,bRight,bLeft,pause, resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        frame = findViewById(R.id.frame);
        bLeft = findViewById(R.id.btnL);
        bRight = findViewById(R.id.btnR);
        bDown = findViewById(R.id.btnD);
        bUp = findViewById(R.id.btnU);
        hp = (TextView) findViewById(R.id.hp);
        pause = findViewById(R.id.pause);
        resume = findViewById(R.id.resume);
        ds = new DrawSurfaceView(this);
        frame.addView(ds);
        thread = new Thread(ds);
        thread.start();
        bLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds.moveL();
            }
        });
        bRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds.moveR();
            }
        });bUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds.moveU();
            }
        });bDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds.moveD();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseResume(v);
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playResume(v);
            }
        });


    }


    public void pauseResume(View view)
    {
        ds.Pause();
    }

    public void playResume(View view)
    {
        ds.Resume();
    }
    public void move(View view)
    {
        //TODO

    }
}
