package com.iwsi.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iwsi.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

public class MovieModel {
	
	public int mId;
	public String mTitle;
	public String mDescription;
	public Bitmap mPoster;
	public Bitmap[] mScreenshots;
	private final Activity mActivity;
	
	public MovieModel(final Activity pActivity, String pJSON)
	{
		mActivity = pActivity;
		parseJSON(pJSON);
	}
	
	private void parseJSON(String pJSON)
	{
		JSONObject obj;
		try {
			
			obj = new JSONObject(pJSON);
    		mId = obj.getInt("id");
    		mTitle = obj.getString("title");
    		mDescription = obj.getString("description");
    		
    		JSONArray screens = obj.getJSONArray("screenshots");
    		
    		if(screens.length()>1)
    		{
    			mPoster = decodeToImage(screens.getJSONObject(0).getString("data"));
    			mScreenshots = new Bitmap[screens.length()-2];
    		}
    		else
    		{
    			mPoster = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.noposter);
    			mScreenshots = null;
    		}
    		
        	for(int i=1;i<screens.length()-1;i++)
        	{
        		String screenEncode = screens.getJSONObject(i).getString("data");
        		mScreenshots[i-1] = decodeToImage(screenEncode);
        	}
        	
	    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
	}
	
	public static Bitmap decodeToImage(String imageString) {

		String imageDataBytes = imageString.substring(imageString.indexOf(",")+1);

		InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
		Bitmap image = BitmapFactory.decodeStream(stream);
	    return image;
	}
}
