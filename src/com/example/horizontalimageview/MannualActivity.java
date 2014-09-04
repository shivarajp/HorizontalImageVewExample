package com.example.horizontalimageview;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import de.greenrobot.event.EventBus;

public class MannualActivity extends FragmentActivity {
	static int numberOfPages = 2;
	ViewPager myViewPager;
	private static final int SELECT_PICTURE = 1;
	Button previous, next;
	ImageButton add, delete;
	MyFragmentPagerAdapter myFragmentPagerAdapter;
	String text = "test";
	private String selectedImagePath;
	private String filemanagerstring;

	public void onEvent(ReloadEvent event) {
	}

	@Override
	protected void onPause() {
		super.onPause();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mannual);

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.actionbar_custom_view_home);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);

		myViewPager = (ViewPager) findViewById(R.id.vpager);
		myFragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager());
		myViewPager.setAdapter(myFragmentPagerAdapter);
		next = (Button) findViewById(R.id.next2);
		previous = (Button) findViewById(R.id.previous);
		add = (ImageButton) findViewById(R.id.addImage);

		setButtons();
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myViewPager = (ViewPager) findViewById(R.id.vpager);
				myViewPager.setCurrentItem(myViewPager.getCurrentItem() + 1,
						true);
				setButtons();
			}
		});

		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myViewPager = (ViewPager) findViewById(R.id.vpager);
				myViewPager.setCurrentItem(myViewPager.getCurrentItem() - 1,
						true);
				setButtons();
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			if (requestCode == SELECT_PICTURE) {
				ImageDetails details;
				Uri selectedImageUri = data.getData();
				Log.d("onActivityRes", "" + selectedImageUri);
				// OI FILE Manager
				filemanagerstring = selectedImageUri.getPath();
				// MEDIA GALLERY
				selectedImagePath = getPath(selectedImageUri);
				Log.d("path=", "" + selectedImagePath);
				details = new ImageDetails(selectedImagePath, -1, true, false);
				ImageApplication.fromRecorderPaths.add(details);
				//EventBus.getDefault().post(new ReloadEvent());
				
				
				//RearrangeFragment fragment = (RearrangeFragment) (myFragmentPagerAdapter.getItem(0));
				//fragment.refresh();
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	private void setButtons() {
		next = (Button) findViewById(R.id.next2);
		previous = (Button) findViewById(R.id.previous);
		if (myViewPager.getCurrentItem() == 0) {
			previous.setVisibility(View.INVISIBLE);
			next.setText("Next");
		} else {
			previous.setVisibility(View.VISIBLE);
			next.setText("Finsh");
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		ImageApplication.fromRecorderPaths = null;
	}

}
