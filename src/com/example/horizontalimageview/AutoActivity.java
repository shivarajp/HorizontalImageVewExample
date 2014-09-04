package com.example.horizontalimageview;

import java.util.ArrayList;
import java.util.Arrays;

import org.askerov.dynamicgid.DynamicGridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.horizontalimageview.GridViewDynamicAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AutoActivity extends Activity {
	DynamicGridView gridView;
	String ss;
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	ArrayList<String> paths = new ArrayList<String>();
	private static final String LOGTAG = "EXPLORECA";
	private static final String DATABASE_NAME = "quiz1.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_IMAGES = "auto";
	private static final String recName = "recName";
	private static final String imagesPath = "imagesPath";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto);
		getvalues();
		gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
		gridView.setAdapter(new GridViewDynamicAdapter(this, ImageApplication
				.getPaths(), 3));
	}

	public void getvalues() {
		// Select All Query
		String selectQuery = "SELECT imagesPath from " + TABLE_IMAGES;
		dbhelper = new DatabaseStore(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				String s = cursor.getString(0);
				// s.replace("\\","//");
				Log.d("path", s);
				try {
					JSONArray array = new JSONArray(s);
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						Log.d("new", obj.getString("path"));
						paths.add(obj.getString("path"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (cursor.moveToNext());
			cursor.close();
		}

	}
}
