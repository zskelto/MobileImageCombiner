package com.zskelto.mobileimagecombiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String BASE = "BaseImage";
    public static final String TOP = "TopImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void startButton(View view){
        Intent intent = new Intent(this, LoadBaseActivity.class);
        startActivity(intent);
    }
}