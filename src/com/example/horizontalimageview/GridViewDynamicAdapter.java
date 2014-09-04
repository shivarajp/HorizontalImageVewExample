package com.example.horizontalimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.askerov.dynamicgid.BaseDynamicGridAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GridViewDynamicAdapter extends BaseDynamicGridAdapter {
	ImageDetails details;
	public static List<ImageDetails> list;

	public GridViewDynamicAdapter(Context context, List<ImageDetails> items,
			int columnCount) {
		super(context, items, columnCount);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheeseViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_grid, null);
			holder = new CheeseViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (CheeseViewHolder) convertView.getTag();
		}
		if (position == getCount() - 1) {
			ImageApplication.fromRecorderPaths = (List<ImageDetails>) getItems();
		}
		details = (ImageDetails) getItem(position);
		// Log.d("Setting=", details.path);
		holder.build(getContext(), details.path);
		return convertView;
	}

	private class CheeseViewHolder {
		private ImageView image;

		private CheeseViewHolder(View view) {
			image = (ImageView) view.findViewById(R.id.item_img);
		}

		void build(final Context context, String title) {
			Picasso.with(context).load(new File(title)).centerCrop()
					.resize(150, 150).error(R.drawable.ic_launcher).into(image);
		}
	}

}