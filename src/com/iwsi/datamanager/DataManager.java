package com.iwsi.datamanager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;


public class DataManager {
	
	private SharedPreferences mPrefs;
	
	public DataManager(SharedPreferences pPrefs)
	{
		mPrefs = pPrefs;
	}
	
	public void addMessageSubscribe(int pId)
	{
		List<Integer> subscribe = readMessageSubscribe();
		
		if(subscribe!=null)
		{
			
			subscribe.add(pId);
			writeMessageSubscribe(subscribe);

		}
		else
		{
			subscribe = new ArrayList<Integer>();
			subscribe.add(pId);
			writeMessageSubscribe(subscribe);
		}
	}
	
	public void removeMessageSubscribe(int pId)
	{
		List<Integer> subscribe = readMessageSubscribe();
		
		if(subscribe!=null)
		{
			
			if(subscribe.contains((Object)0))
			{
				subscribe.remove((Object)0);
			}
			else
			{
				subscribe.remove((Object)pId);
			}
			writeMessageSubscribe(subscribe);
		}
	}
	
	public List<Integer> readMessageSubscribe()
	{
		String key = "com.iwsi.messagesubscribe";
		String value = mPrefs.getString(key, null);
		
		List<Integer> result = new ArrayList<Integer>();
		
		if(value!=null)
		{
			JSONObject obj;
			try {
				obj = new JSONObject(value);
	        	JSONArray arr = obj.getJSONArray("movies");
	        	
	        	if(arr.length()>0)
	        	{	
		        	for(int i=0;i<arr.length();i++)
		        	{
		        		if(arr.getJSONObject(i).getInt("id")!=0)
		        			result.add(arr.getJSONObject(i).getInt("id"));
		        		else
		        			result.add(1);
		        	}
	        	}
	        	
			} catch (JSONException e) {
				
			}
		}
		return result;
	}
	
	private void writeMessageSubscribe(List<Integer> pIds)
	{
		String resultJSON = "{ movies:[";
		
		for(int i=0;i<pIds.size();i++)
		{
			resultJSON += "{id:\""+pIds.get(i)+"\"}";
			if(i<pIds.size()-1)
				resultJSON += ",";
		}
		resultJSON += "]}";
		String key = "com.iwsi.messagesubscribe";
		mPrefs.edit().putString(key, resultJSON).commit();
	}
	
	public Boolean isMessageSubscribed(int pId)
	{
		List<Integer> subscribe = readMessageSubscribe();
		
		if(subscribe!=null)
			for(int i=0;i<subscribe.size();i++)
				if(subscribe.get(i)==pId)
					return true;
		
		return false;
		
	}
	
}
