package com.iwsi;

import com.iwsi.downloadmanager.DownloadMovie;
import com.iwsi.downloadmanager.DownloadMoviesList;
import com.iwsi.model.MovieModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    
    public void onMovieListLoaded(int[] pIdList)
    {
    	Intent intent = new Intent(MainActivity.this, MoviesActivity.class);
    	intent.putExtra("moviesList",pIdList);
    	startActivity(intent);
    }
    
}
