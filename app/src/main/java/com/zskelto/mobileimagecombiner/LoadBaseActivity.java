package com.zskelto.mobileimagecombiner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class LoadBaseActivity extends AppCompatActivity {
    public final int RESULT_LOAD_IMG = 3;
    private final int BASE_LOAD = 1;
    private final int TOP_LOAD = 2;

    private int state = BASE_LOAD;
    private ImageView imageView;
    private ImageButton rotateButton;
    private Button nextButton;
    private Bitmap baseLayer;
    private Bitmap topLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_base);
        getSupportActionBar().hide();
        imageView = (ImageView) findViewById(R.id.baseImage);
        rotateButton = (ImageButton) findViewById(R.id.rotateButton);
        nextButton = (Button) findViewById(R.id.nextButton);
    }

    public void loadImage(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                imageView.setVisibility(View.VISIBLE);
                rotateButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "No image" + resultCode, Toast.LENGTH_SHORT).show();
        }
    }

    public void rotateImage(View view){
        ImageView baseImage = (ImageView) findViewById(R.id.baseImage);
        Bitmap baseImageBM = ((BitmapDrawable) baseImage.getDrawable()).getBitmap();
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotated = Bitmap.createBitmap(baseImageBM, 0, 0, baseImageBM.getWidth(), baseImageBM.getHeight(), matrix, true);
        baseImage.setImageBitmap(rotated);
    }

    public void cancel(View view){
        finish();
    }

    public void next(View view){
        switch(state){
            case BASE_LOAD:
                ImageView baseImageView = (ImageView) findViewById(R.id.baseImage);
                Bitmap baseImageBM = ((BitmapDrawable) baseImageView.getDrawable()).getBitmap();
                try{
                    FileOutputStream fo = openFileOutput(MainActivity.BASE, Context.MODE_PRIVATE);
                    baseImageBM.compress(Bitmap.CompressFormat.PNG, 100, fo);
                    fo.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                TextView textView = (TextView) findViewById(R.id.loadDescription);
                textView.setText("Load a top image!");
                imageView.setVisibility(View.GONE);
                rotateButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);
                break;
            case TOP_LOAD:
                ImageView topImageView = (ImageView) findViewById(R.id.baseImage);
                Bitmap topImageBM = ((BitmapDrawable) topImageView.getDrawable()).getBitmap();
                try{
                    FileOutputStream fo = openFileOutput(MainActivity.TOP, Context.MODE_PRIVATE);
                    topImageBM.compress(Bitmap.CompressFormat.PNG, 100, fo);
                    fo.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, CombineImageActivity.class);
                startActivity(intent);
                break;
        }
        state++;
    }
}