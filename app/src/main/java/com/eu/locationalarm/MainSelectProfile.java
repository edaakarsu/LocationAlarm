package com.eu.locationalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.eu.locationalarm.action.ProfileButtonAction;

import java.util.List;


public class MainSelectProfile extends AppCompatActivity {

    //TODO List şeklinde tüm profile tutulmalı
    private ProfileButton profileButton;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select_profile);

        init();

    }

    //TODO profile bilgisini db üzerinden çekmek gerekiyor. Her profile için listeye birer obje eklemelisin
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
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new ProfileButtonAction(this, profileButton));
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


