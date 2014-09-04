package com.example.horizontalimageview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	RearrangeFragment fragment1;
	TimeSettingsFragment fragment2;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	final public static int NUMBER_OF_PAGES = 2;

	@Override
	public Fragment getItem(int index) {

		if (index == 0) {
			if (fragment1 == null) {
				fragment1 = (RearrangeFragment) RearrangeFragment
						.newInstance(index + 1);
				return fragment1;
			} else {
				return fragment1;
			}
		} else {

			if (fragment2 == null) {
				fragment2 = (TimeSettingsFragment) TimeSettingsFragment
						.newInstance(index + 1);
				return fragment2;
			} else {
				return fragment2;
			}

		}
	}

	@Override
	public int getCount() {
		return NUMBER_OF_PAGES;
	}
}