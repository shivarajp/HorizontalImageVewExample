package com.example.horizontalimageview;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class ShowTwoway extends Activity {
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_show_twoway);
		
	}

	public void onEventMainThread(ReloadEvent event)
	{
		Log.d("Done","Done");
	}
	
	public void onEvent( ReloadEvent event)
	{
		Log.d("Done","Done");
	}
}
