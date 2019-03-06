package com.hkucs.cookbook.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.hkucs.cookbook.R;

import androidx.appcompat.app.AppCompatActivity;

public abstract class CookbookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_next_page, R.anim.slide_out_next_page);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_previous_page, R.anim.slide_out_previous_page);
    }
}
