package com.eu.locationalarm;

import android.content.Context;
import android.util.AttributeSet;

public class ProfileButton extends androidx.appcompat.widget.AppCompatImageButton {

    private String profileName;

    public ProfileButton(Context context) {
        super(context);
    }

    public ProfileButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getProfileName() {
        return profileName;
    }
}
