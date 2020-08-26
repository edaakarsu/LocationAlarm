package com.eu.locationalarm.action;

import android.content.Intent;
import android.view.View;

import com.eu.locationalarm.MainLocation;
import com.eu.locationalarm.MainSelectProfile;
import com.eu.locationalarm.ProfileButton;
import com.eu.locationalarm.util.IntentConstant;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileButtonAction implements View.OnClickListener {

    private Intent intent;
    private MainSelectProfile appCompatActivity;
    private ProfileButton profileButton;

    public ProfileButtonAction(AppCompatActivity appCompatActivity, ProfileButton profileButton) {
    this.appCompatActivity = (MainSelectProfile) appCompatActivity;
    this.intent = new Intent(appCompatActivity, MainLocation.class);
    this.profileButton = profileButton;
}

    @Override
    public void onClick(View v) {
        intent.putExtra(IntentConstant.PROFILE_NAME, profileButton.getProfileName());
        appCompatActivity.startActivity(intent);
    }
}
