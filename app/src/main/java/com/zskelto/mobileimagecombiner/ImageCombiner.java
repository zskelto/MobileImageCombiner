package com.zskelto.mobileimagecombiner;

import android.graphics.Bitmap;

public class ImageCombiner {

    private Bitmap base, top, output;

    public ImageCombiner(){
        base = null;
        top = null;
        output = null;
    }

    public void setBase(Bitmap bitmap){
        base = bitmap;
    }

    public void setTop(Bitmap bitmap){
        top = bitmap;
    }

    public Bitmap getOutput(){
        return output;
    }
}
