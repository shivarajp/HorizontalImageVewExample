package com.example.horizontalimageview;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import org.askerov.dynamicgid.DynamicGridView;
import org.askerov.dynamicgid.DynamicGridView.OnDropListener;
import org.w3c.dom.ls.LSInput;

import de.greenrobot.event.EventBus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RearrangeFragment extends Fragment {
	private static DynamicGridView gridView, gridView2;
	public static final String INTENT_KEY = "imageDetailsList";
	public static final int DELETEMODE_SET = 1;
	public static final int DELETEMODE_UNSET = 0;
	ImageButton delete;
	boolean listnerFlag = false;
	int current, dropped;
	private String selectedImagePath;
	ArrayList<ImageDetails> imageDetailsPaths;
	private String filemanagerstring;
	private static final int SELECT_PICTURE = 1;
	public GridViewDynamicAdapter adapter;
	int flag = 0;
	static int callback = 0;

	public void refresh() {
		Log.d("RearramgeFragment", "refresh");
		gridView.setAdapter(null);
		adapter = null;
		adapter = new GridViewDynamicAdapter(getActivity(),
				ImageApplication.fromRecorderPaths, 3);
		// adapter.set(ImageApplication.fromRecorderPaths);
		// adapter.notifyDataSetChanged();
		gridView.setAdapter(adapter);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("RearramgeFragment", "onpause");
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		// Log.d("RearramgeFragment", "onresume");
		// EventBus.getDefault().register(this);
		if (callback == 0) {
			callback = 1;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.rearrange_fragment1,
				container, false);
		gridView = (DynamicGridView) v.findViewById(R.id.dynamic_grid);
		delete = (ImageButton) getActivity().findViewById(R.id.deleteImage);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (callback == 1) {
			adapter = new GridViewDynamicAdapter(getActivity(),
					ImageApplication.fromRecorderPaths, 3);
			gridView.setAdapter(adapter);
		} else {

			ImageDetailsList dl = getActivity().getIntent().getParcelableExtra(
					INTENT_KEY);
			imageDetailsPaths = (ArrayList<ImageDetails>) dl.imageDetailsList;
			ImageApplication.fromRecorderPaths = null;
			ImageApplication.fromRecorderPaths = (List<ImageDetails>) dl.imageDetailsList;
			gridView.setPadding(5, 5, 5, 5);
			adapter = new GridViewDynamicAdapter(getActivity(),
					imageDetailsPaths, 3);
			gridView.setAdapter(adapter);

			delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					gridView.setOnItemLongClickListener(null);
					if (flag == 0) {
						flag = 1;
						gridView.setBackgroundColor(getResources().getColor(
								android.R.color.darker_gray));
					} else if (flag == 1) {
						flag = 0;
						gridView.setBackgroundColor(getResources().getColor(
								android.R.color.background_light));
					}
					gridView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							ImageDetails detail = ImageApplication.fromRecorderPaths
									.get(position);
							if (detail.isSelected) {
								detail.isSelected = false;
								ImageApplication.fromRecorderPaths.set(
										position, detail);
								view.setBackgroundColor(getResources()
										.getColor(
												android.R.color.background_light));
							} else {
								detail.isSelected = true;
								ImageApplication.fromRecorderPaths.set(
										position, detail);
								view.setBackgroundColor(getResources()
										.getColor(
												android.R.color.holo_blue_bright));
							}
						}
					});

					if (flag == 1) {
						// show action bar buttons
						getActivity().getActionBar().setCustomView(
								R.layout.actionbar_delete_and_cancel);
					}
				}
			});

			gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// Log.d("position", "" + position);
					// current = position;
					listnerFlag = true;
					// Log.d("Current-", "" + current);
					gridView.startEditMode(position);
					return false;
				}
			});

			gridView.setOnDropListener(new OnDropListener() {
				@Override
				public void onActionDrop() {
				}

				@Override
				public void onListChange(int originalPosition, int newPosition) {

					gridView.stopEditMode();
				}
			});
		}
	}

	public void onEventMainThread(ReloadEvent event) {
		Log.d("eventRcvd", "Rcvd");

		// adapter.notifyDataSetChanged();
		adapter = new GridViewDynamicAdapter(getActivity(),
				ImageApplication.fromRecorderPaths, 3);
		gridView.setAdapter(adapter);
	}

	public static Fragment newInstance(int sectionNumber) {
		RearrangeFragment reFragment = new RearrangeFragment();
		return reFragment;
	}

}
