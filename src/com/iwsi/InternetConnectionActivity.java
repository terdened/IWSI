package com.iwsi;

import com.iwsi.downloadmanager.CheckInternetConnection;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class InternetConnectionActivity extends MainActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet_connection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.internet_connection, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onTryAgain(View v)
	{
		String url = getResources().getString(R.string.host)+"movies.php?limit=10";
        new CheckInternetConnection(this).execute(url);
	}
	
	@Override
	public void onBadConnection()
    {
    	
    }
	
	@Override
	public void onGoodConnection()
    {
		Intent intent = new Intent(InternetConnectionActivity.this, MainActivity.class);
    	startActivity(intent);
    }

}
