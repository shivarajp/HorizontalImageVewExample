package com.example.horizontalimageview;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class ImagePathExtractor {
	/**
	 * This Method extracts all the images between startTime - endTime and
	 * return the ImageDetailsList Object.
	 * 
	 * @param context
	 *            -> Context
	 * @param startTimeInMillis
	 *            -> Start time in Millis
	 * @param Id
	 *            ->
	 * @return
	 */

	public static void ExtractAndReturn(final Context context,
			long startTimeInMillis, long endTimeInMillis,
			OnImagePathExtractedListner listner) {
		ArrayList<ImageDetails> paths = new ArrayList<ImageDetails>();
		String tempPath;
		boolean isSelected = false;
		long tempDeltaTime;
		boolean shouldCopy = true;
		String[] projection = new String[] {
				MediaStore.Images.ImageColumns._ID,
				MediaStore.Images.ImageColumns.DATA,// will get path
				MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
				MediaStore.Images.ImageColumns.DATE_TAKEN,
				MediaStore.Images.ImageColumns.MIME_TYPE,
				MediaStore.Images.ImageColumns.TITLE };

		// Extract images taken between startTime and endTime (During recording)
		String query = MediaStore.Images.ImageColumns.DATE_TAKEN + " > "
				+ startTimeInMillis + " AND "
				+ MediaStore.Images.ImageColumns.DATE_TAKEN + " < "
				+ endTimeInMillis;
		final Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
				query, null,
				MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

		// Only if cursor is moved to first and cursor is not empty
		// Ex:- When no images were taken between the given time.
		if (cursor.moveToFirst() && cursor.getCount() != 0) {
			do {
				ImageDetails imgdetails;
				tempDeltaTime = cursor
						.getLong(cursor
								.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN))
						- startTimeInMillis;
				tempPath = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
				shouldCopy = true;
				imgdetails = new ImageDetails(tempPath, tempDeltaTime,
						shouldCopy, isSelected);
				paths.add(imgdetails);
			} while (cursor.moveToNext());
			if (!paths.isEmpty()) {
				// If last recording images paths still exists clear.
				ImageApplication.fromRecorderPaths = null;
				// Assign newly taken Images
				ImageApplication.fromRecorderPaths = paths;
				listner.onLoaded(true);
			} else {
				// No images were taken during the recording
				listner.onLoaded(false);
			}
		}
	}
}