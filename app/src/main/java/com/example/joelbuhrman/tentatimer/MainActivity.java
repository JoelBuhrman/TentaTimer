package com.example.joelbuhrman.tentatimer;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout ekonomiLayout, köSystemLayout;

    private int registeredMSEkonomi=0;
    private int registeredMSKösystem=0;


    boolean köSystemRunning, ekonomiRunning;

    TextView timerEkonomi, timerKöSystem;
    long startTimeEkonomi = 0;
    long startTimeKöSystem=0;

    Handler timerHandlerEkonomi, timerHandlerKöSystem;
    Runnable timerRunnableEkonomi, timerRunnableKöSystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        ekonomiLayout = (RelativeLayout) findViewById(R.id.ekonomiRelLayout);
        köSystemLayout = (RelativeLayout) findViewById(R.id.köSystemRelLayout);

        timerEkonomi = (TextView) findViewById(R.id.ekonomiTimer);
        timerKöSystem = (TextView) findViewById(R.id.köSystemTimer);

        köSystemRunning = false;
        ekonomiRunning = false;

        timerHandlerEkonomi = new Handler();
        timerRunnableEkonomi = new Runnable() {

            @Override
            public void run() {
                long millis = registeredMSEkonomi+System.currentTimeMillis() - startTimeEkonomi;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                timerEkonomi.setText(String.format("%d:%02d", minutes, seconds));

                timerHandlerEkonomi.postDelayed(this, 500);
            }
        };

        timerHandlerKöSystem = new Handler();
        timerRunnableKöSystem = new Runnable() {

            @Override
            public void run() {
                long millis =registeredMSKösystem+ System.currentTimeMillis() - startTimeKöSystem;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                timerKöSystem.setText(String.format("%d:%02d", minutes, seconds));

                timerHandlerKöSystem.postDelayed(this, 500);
            }
        };
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startKöSystemTimer(View view) {

        if (köSystemRunning) {
            köSystemRunning = false;
            timerHandlerKöSystem.removeCallbacks(timerRunnableKöSystem);
        } else {
            köSystemRunning = true;
            startTimeKöSystem = System.currentTimeMillis();
            timerHandlerKöSystem.postDelayed(timerRunnableKöSystem, 0);
        }

    }

    public void startEkonomiTimer(View view) {

        if(ekonomiRunning){
            ekonomiRunning=false;
            timerHandlerEkonomi.removeCallbacks(timerRunnableEkonomi);
        }
        else{
            ekonomiRunning=true;
            startTimeEkonomi = System.currentTimeMillis();
            timerHandlerEkonomi.postDelayed(timerRunnableEkonomi, 0);
        }
    }

}
