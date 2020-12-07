package com.example.juegodel21;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class StartActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        timer();
    }
    private void timer(){
        CountDownTimer cd = new CountDownTimer(4000, 1000){
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
        cd.start();
    }
}
