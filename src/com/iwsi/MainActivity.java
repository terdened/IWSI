package com.iwsi;

import java.util.List;

import com.iwsi.datamanager.DataManager;
import com.iwsi.downloadmanager.CheckInternetConnection;
import com.iwsi.downloadmanager.DownloadMovie;
import com.iwsi.downloadmanager.DownloadMoviesList;
import com.iwsi.model.MovieModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = getResources().getString(R.string.host)+"movies.php?limit=10";
        new CheckInternetConnection(this).execute(url);
    }
    
    public void onNewsClick(View v)
    {
    	String url = getResources().getString(R.string.host)+"movies.php?limit=10";
    	new DownloadMoviesList(this).execute(url);
    }
    
    public void onInCinemaClick(View v)
    {
    	String url = getResources().getString(R.string.host)+"movies_in_cinema.php?limit=10";
    	new DownloadMoviesList(this).execute(url);
    }
    
    public void onBadConnection()
    {
    	Intent intent = new Intent(MainActivity.this, InternetConnectionActivity.class);
    	startActivity(intent);
    }
    
    public void onMySubscribeClick(View v)
    {
    	DataManager dm = new DataManager(getSharedPreferences("com.iwsi", Context.MODE_PRIVATE));
    	
    	List<Integer> sibscribes = dm.readMessageSubscribe();
    	if(sibscribes.size()>0)
    	{
    		int[] subs = new int[sibscribes.size()];
    		for(int i=0;i<sibscribes.size();i++)
    			subs[i] = sibscribes.get(i);
    		onMovieListLoaded(subs);
    	}
    }
    
    public void onGoodConnection()
    {
    	
    }
    
    public void onMovieListLoaded(int[] pIdList)
    {
    	Intent intent = new Intent(MainActivity.this, MoviesActivity.class);
    	intent.putExtra("moviesList",pIdList);
    	startActivity(intent);
    }
    
}
