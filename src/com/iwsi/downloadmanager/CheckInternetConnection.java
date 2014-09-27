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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iwsi.MainActivity;
import com.iwsi.R;

import android.os.AsyncTask;

public class CheckInternetConnection extends AsyncTask<String, Void, String> {
	
	private final MainActivity mMainActivity;
	
	public CheckInternetConnection(final MainActivity pMainActivity)
	{
		mMainActivity = pMainActivity;
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
        	mMainActivity.onBadConnection();
        	return "404";
        } catch (IOException e) {
        	mMainActivity.onBadConnection();
        	return "404";
        }
        return responseString;
    }
    
    protected void onPostExecute(String result) {
    	JSONObject obj;
		try {
			obj = new JSONObject(result);
        	JSONArray arr = obj.getJSONArray("movies");
        	mMainActivity.onGoodConnection();
        	
		} catch (JSONException e) {
			mMainActivity.onBadConnection();
			e.printStackTrace();
		}
    	
    }
}
