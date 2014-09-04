package com.example.horizontalimageview;

import android.os.Parcel;
import android.os.Parcelable;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * Created by soham on 09/06/14.
 */
public class ImageDetailsList implements Parcelable {
    public List<ImageDetails> imageDetailsList;
 
    // constant for passing in Intents
    public static final String INTENT_KEY = "imageDetailsList";
 
    public ImageDetailsList(List<ImageDetails> imageDetailsList) {
        this.imageDetailsList = imageDetailsList;
    }
 
    public ImageDetailsList(Parcel in) {
        readFromParcel(in);
    }
 
    @Override
    public int describeContents() {
        return 0;
    }
 
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(imageDetailsList);
    }
 
    private void readFromParcel(Parcel in) {
        imageDetailsList = new ArrayList<ImageDetails>();
        in.readTypedList(imageDetailsList, ImageDetails.CREATOR);
    }
 
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ImageDetailsList createFromParcel(Parcel in) {
            return new ImageDetailsList(in);
        }
        public ImageDetailsList[] newArray(int size) {
            return new ImageDetailsList[size];
        }
    };
}