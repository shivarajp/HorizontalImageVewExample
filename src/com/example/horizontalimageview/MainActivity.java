package com.example.horizontalimageview;

import java.util.ArrayList;
import java.util.List;
import org.lucasr.twowayview.TwoWayView;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {
	public ArrayList<String> images = new ArrayList<String>();
	public static final String INTENT_KEY = "imageDetailsList";
	public static final long START_TIME = System.currentTimeMillis() - 600000000;
	public static final long END_TIME = System.currentTimeMillis() + 69000;
	private Button Extract, mannual;
	private HorizontalImagesBaseAdapter adapter;
	private ImageDetailsList parcelableImageDetailsList;
	RelativeLayout myLayout;

	/**
	 * Pass the start time and endtime to ImagePathExtractor.ExtractAndReturn()
	 * it will extract images and store it to ImageApplication object.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myLayout = new RelativeLayout(this);
		Extract = (Button) findViewById(R.id.auto);
		mannual = (Button) findViewById(R.id.mannual);
		Extract.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ImageApplication.fromRecorderPaths == null) {
					ImagePathExtractor.ExtractAndReturn(
							getApplicationContext(), START_TIME, END_TIME,
							listner);
				}
			}
		});
		mannual.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
						"com.example.horizontalimageview.MannualActivity");
				i.putExtra(INTENT_KEY, parcelableImageDetailsList);
				startActivity(i);
			}
		});
	}

	OnImagePathExtractedListner listner = new OnImagePathExtractedListner() {

		@Override
		public void onLoaded(boolean notEmpty) {
			try {
				// true if images are taken & added to the ImagesApplication
				// object
				// false if images are not taken during the recording
				if (notEmpty) {
					showTwowayview();
				} else {
					// Show no images
				}
			} catch (Exception e) {
				Log.d("Error", "Error");
			}
		}
	};

	protected void showTwowayview() {
		int LEFT = 1;
		int TOP = 1;
		int RIGHT = 1;
		int BOTTOM = 1;
		// Get the welcome card layout and add this layout
		// below that.
		adapter = new HorizontalImagesBaseAdapter(MainActivity.this,
				ImageApplication.fromRecorderPaths);
		RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.welcomeCard);
		Log.d("in", "in");
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 300);
		// Id of the view below which twoway view will be displayed
		params.addRule(RelativeLayout.BELOW, R.id.mannual);
		// set the layout margin
		params.setMargins(LEFT, TOP, RIGHT, BOTTOM);
		myLayout.setLayoutParams(params);
		TwoWayView lparams = (TwoWayView) getLayoutInflater().inflate(
				R.layout.twowayview, myLayout, false);
		TwoWayView twowayview = (TwoWayView) lparams.findViewById(R.id.lvItems);
		myLayout.addView(twowayview, 0);
		//0 is the pisition off the twowayview in the layout
		TwoWayView listView = (TwoWayView) myLayout.getChildAt(0);
		listView.layout(1,1,1,1);
		
		//listView.setItemMargin(1);
		listView.setAdapter(adapter);
	}
}
