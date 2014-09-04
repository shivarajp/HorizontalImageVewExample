package com.example.horizontalimageview;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soham on 07/06/14.
 */
public class ImageDetails implements Parcelable {
	// the path of the image (to be shown)
	public String path;
	// this is the delay from the starting of the recording in milliseconds
	public long position;
	// should copy image to the local directory (NOT USED IN
	// AUDIOSERVICE/RECORDERLISTFRAGMENT, USED ONLY TO CREATE THE
	// IMAGEDETAILSLIST IN EDIT MODE)
	public boolean shouldCopy = false;
	
	//For deleting
	public boolean isSelected = false;

	final static String KEY_PATH = "path";
	final static String KEY_POSITION = "position";

	/**
	 * Constant to represent image loading in the database
	 */
	public static final String LOADING = "loading";

	public ImageDetails(Parcel in) {
		readFromParcel(in);
	}

	public ImageDetails() {
	}

	public ImageDetails(String path, long position) {
		this.path = path;
		this.position = position;
	}

	public ImageDetails(String path, long position, boolean shouldCopy, boolean isSelected) {
		this.path = path;
		this.position = position;
		this.shouldCopy = shouldCopy;
		this.isSelected = isSelected;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(path);
		dest.writeLong(position);
		dest.writeByte((byte) (shouldCopy ? 1 : 0));
	}

	private void readFromParcel(Parcel in) {
		this.path = in.readString();
		this.position = in.readLong();
		this.shouldCopy = in.readByte() != 0;
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public ImageDetails createFromParcel(Parcel in) {
			return new ImageDetails(in);
		}

		public ImageDetails[] newArray(int size) {
			return new ImageDetails[size];
		}
	};

	/**
	 * Generates the JSON from the List, call from a Non UI Thread!!
	 * 
	 * @param imageDetailsList
	 * @return the Json String
	 */
	public static String generateJson(List<ImageDetails> imageDetailsList) {
		String json = null;
		if (imageDetailsList != null) {
			json = "[";
			for (int i = 0; i < imageDetailsList.size(); i++) {
				ImageDetails imageDetails = imageDetailsList.get(i);
				json += "{ \"path\": \"" + imageDetails.path
						+ "\", \"position\": " + imageDetails.position + "}";
				if (i < (imageDetailsList.size() - 1)) {
					json += ",";
				}
			}
			json += "]";
		}
		return json;
	}

	/**
	 * Generates the ImageDetailsList from a Json file, call from a Non UI
	 * Thread!!
	 * 
	 * @param json
	 * @return the ImageDetails List
	 */
	public static List<ImageDetails> generateImageDetailsList(String json) {

		if (json != null) {
			try {
				List<ImageDetails> imageDetailsList = new ArrayList<ImageDetails>();
				JsonFactory jfactory = new JsonFactory();

				JsonParser parser = jfactory.createParser(json);
				parser.nextToken();
				while (parser.nextToken() != JsonToken.END_ARRAY) {
					parser.nextToken();
					parser.nextToken();
					String path = parser.getText();
					parser.nextToken();
					parser.nextToken();
					long position = parser.getLongValue();
					imageDetailsList.add(new ImageDetails(path, position));
					parser.nextToken();
				}
				return imageDetailsList;
			} catch (JsonGenerationException e) {
				// ExceptionUtils.logException(e);
			} catch (IOException e) {
				// ExceptionUtils.logException(e);
			} catch (Exception e) {
				// ExceptionUtils.logException(e);
			}
		}
		return null;
	}

	/**
	 * Helps search for a path in an ImageDetailsList O(n)
	 * 
	 * @param imageDetailsList
	 *            the list where we must search
	 * @param path
	 *            the path to be searched
	 * @return the position of the entry, -1 if not found
	 */
	public static int getPositionByPath(List<ImageDetails> imageDetailsList,
			String path) {
		ImageDetails imageDetails;
		for (int i = 0; i < imageDetailsList.size(); i++) {
			imageDetails = imageDetailsList.get(i);
			if (imageDetails.path.equals(path)) {
				return i;
			}
		}
		return -1;
	}
}