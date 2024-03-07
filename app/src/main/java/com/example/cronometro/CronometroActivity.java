package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.TextureView;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;

public class CronometroActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean reset;

    private ArrayList<Integer> lapTimes = new ArrayList<>();
    private int lapCounter = 0;
    private TextView lapTimesTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cronometro_main);

        lapTimesTextView = findViewById(R.id.lap_times_text);
        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }
    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset(View view){
        running = false;
        seconds = 0;
        lapTimes.clear();
        lapCounter = 0;
        lapTimesTextView.setText("");
    }

    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }

        });
    }

    public void onClickLap(View view) {
        if (lapCounter < 5) {
            if (seconds > 0) {
                lapTimes.add(seconds - 1);
            } else {
                lapTimes.add(0);
            }
            lapCounter++;
            updateLapTimesTextView();
        }
    }

    private void updateLapTimesTextView() {
        StringBuilder lapTimesString = new StringBuilder();
        for (int i = 0; i < lapTimes.size(); i++) {
            int lapNumber = i + 1;
            int lapTime = lapTimes.get(i);
            lapTimesString.append("Vuelta ").append(lapNumber).append(": ").append(formatTime(lapTime)).append("\n");
        }
        lapTimesTextView.setText(lapTimesString.toString());
    }

    private String formatTime(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }




}