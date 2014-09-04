package org.askerov.dynamicgid;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import com.example.horizontalimageview.ImageApplication;
import com.example.horizontalimageview.ImageDetails;

/**
 * Author: alex askerov
 * Date: 9/7/13
 * Time: 10:14 PM
 */
public class DynamicGridUtils {
    /**
     * Delete item in <code>list</code> from position <code>indexFrom</code> and insert it to <code>indexTwo</code>
     *
     * @param list
     * @param indexFrom
     * @param indexTwo
     */
    public static void reorder(ArrayList list, int indexFrom, int indexTwo) {
    	
    	//Before
    	 for(int i=0;i<ImageApplication.fromRecorderPaths.size();i++)
         {
         	Log.d("B4reorder",""+ImageApplication.fromRecorderPaths.get(i).path);
         }
        Object obj = list.remove(indexFrom);
        list.add(indexTwo, obj);
        ImageApplication.fromRecorderPaths=(ArrayList<ImageDetails>)list;
     
     //After   
        for(int i=0;i<ImageApplication.fromRecorderPaths.size();i++)
        {
        	Log.d("after",""+ImageApplication.fromRecorderPaths.get(i).path);
        }
        
    }

    public static float getViewX(View view) {
        return Math.abs((view.getRight() - view.getLeft()) / 2);
    }

    public static float getViewY(View view) {
        return Math.abs((view.getBottom() - view.getTop()) / 2);
    }
}
