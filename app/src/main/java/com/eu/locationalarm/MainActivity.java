package com.eu.locationalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;

    private ImageView imgLogo;
    private TextView txtAppName;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(intent!=null) {
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);

    }

    private void init() {
        imgLogo = findViewById(R.id.imgLogo);
        txtAppName=findViewById(R.id.txtAppName);
        intent = new Intent(MainActivity.this,MainSelectProfile.class);
    }

}
