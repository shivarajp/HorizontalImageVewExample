package com.example.horizontalimageview;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeSettingsFragment extends Fragment{
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 final View v = inflater.inflate(R.layout.time_settings_fragment, container, false);	 
         return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	public static Fragment newInstance(int  sectionNumber)
	{
		TimeSettingsFragment reFragment = new  TimeSettingsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		reFragment.setArguments(args);
		return reFragment;
	}
}
