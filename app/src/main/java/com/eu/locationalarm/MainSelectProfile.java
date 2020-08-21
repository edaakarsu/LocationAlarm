package com.eu.locationalarm;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.TransactionTooLargeException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainSelectProfile extends AppCompatActivity {

    public ImageButton btnGuess;
    Intent intent;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select_profile);

        init();

    }

    private void init() {
        initToolbar();
        initImgGuess();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.tbSelectProfile);
        toolbar.setTitle("Select Profile");
        setSupportActionBar(toolbar);

    }


    private void initImgGuess() {
        btnGuess = findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent = new Intent(MainSelectProfile.this,MainLocation.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_add)
        {
            Toast.makeText(MainSelectProfile.this, "new profile added", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.add);
        return true;
    }

}


