package com.zskelto.mobileimagecombiner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.SeekBar;

public class CombineImageActivity extends AppCompatActivity {

    private int progress;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_image);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setKeyProgressIncrement(1);
        seekBar.setMax(100);
        seekBar.setMin(0);
        seekBar.setProgress(50, true);
    }
}