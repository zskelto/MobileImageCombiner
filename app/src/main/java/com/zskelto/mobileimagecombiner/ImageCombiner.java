package com.zskelto.mobileimagecombiner;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageCombiner implements Parcelable {

    private Bitmap base, top, output;

    public ImageCombiner(){
        base = null;
        top = null;
        output = null;
    }

    protected ImageCombiner(Parcel in) {
        base = in.readParcelable(Bitmap.class.getClassLoader());
        top = in.readParcelable(Bitmap.class.getClassLoader());
        output = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(base, flags);
        dest.writeParcelable(top, flags);
        dest.writeParcelable(output, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageCombiner> CREATOR = new Creator<ImageCombiner>() {
        @Override
        public ImageCombiner createFromParcel(Parcel in) {
            return new ImageCombiner(in);
        }

        @Override
        public ImageCombiner[] newArray(int size) {
            return new ImageCombiner[size];
        }
    };

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
