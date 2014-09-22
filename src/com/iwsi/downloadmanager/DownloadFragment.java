package com.iwsi.downloadmanager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.iwsi.MainActivity;
import com.iwsi.MoviesActivity.SectionsPagerAdapter;

import android.os.AsyncTask;

public class DownloadFragment extends AsyncTask<String, Void, String> {
	private final SectionsPagerAdapter mAdapter;
	
	public DownloadFragment(final SectionsPagerAdapter pAdapter)
	{
		mAdapter = pAdapter;
	}
	
    protected String  doInBackground(String... urls) {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(urls[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }
    
    protected void onPostExecute(String result) {
	    mAdapter.onFragmentLoaded(result);
    }
}
