package com.example.horizontalimageview;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class ImageApplication extends Application {

	public static ArrayList<ImageDetails> fromEditPaths;
	public static List<ImageDetails> fromRecorderPaths ;

	public static void setPaths(ArrayList<ImageDetails> imagePaths2) {
		fromEditPaths = imagePaths2;
	}

	public static ArrayList<ImageDetails> getPaths() {
		return fromEditPaths;
	}
}
