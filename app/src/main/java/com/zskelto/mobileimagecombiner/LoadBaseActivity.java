package com.zskelto.mobileimagecombiner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class LoadBaseActivity extends AppCompatActivity {
    public final int RESULT_LOAD_IMG = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_base);
        getSupportActionBar().hide();
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
        if(requestCode == RESULT_LOAD_IMG){
            try {
                ImageView imageView = (ImageView) findViewById(R.id.baseImage);
                ImageButton rotateButton = (ImageButton) findViewById(R.id.rotateButton);
                Button nextButton = (Button) findViewById(R.id.nextButton);
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
        ImageCombiner imageCombiner = new ImageCombiner();
        ImageView baseImageView = (ImageView) findViewById(R.id.baseImage);
        Bitmap baseImageBM = ((BitmapDrawable) baseImageView.getDrawable()).getBitmap();
        imageCombiner.setBase(baseImageBM);

        Intent intent = new Intent(this, LoadTopActivity.class);
        intent.putExtra(MainActivity.IMAGE_COMBINER, (Parcelable) imageCombiner);
        startActivity(intent);
        finish();
    }
}