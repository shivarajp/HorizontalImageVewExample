package com.example.horizontalimageview;

import java.io.File;
import java.util.List;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HorizontalImagesBaseAdapter extends BaseAdapter {
	final Context context;
	private List<ImageDetails> imageDetailsObjects;// list of file paths

	public HorizontalImagesBaseAdapter(Context context,
			List<ImageDetails> imageDetailsObjects) {
		this.context = context;
		this.imageDetailsObjects = imageDetailsObjects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.listview_layout, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Picasso.with(context)
				.load(new File(imageDetailsObjects.get(position).path))
				.centerCrop().resize(250, 250).error(R.drawable.ic_launcher)
				.into(holder.imageView);
		return convertView;
	}

	@Override
	public int getCount() {
		return imageDetailsObjects.size();
	}

	@Override
	public Object getItem(int position) {
		return imageDetailsObjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
	}

}