package com.wmsviewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class WMSImageGenerator extends AsyncTask<String, Void, Bitmap> {

	public WMSImageGenerator(ImageView _img_view)
	{
		this.img_view = _img_view;
		
	}
	private String debug_tag = "WMS Image retrieval";
	private ImageView img_view;
	
	/*private String WMS_URL;
	public String getWMS_URL()
	{
		return WMS_URL;
	}
	public void setWMS_URL(String value)
	{
		WMS_URL = value;
	}*/
	
	
	@Override
	protected Bitmap doInBackground(String... params) {
		try {
			String final_url = "";
			//wms layer properties
			String WMS_URL= params[0];
			String Layers = "Layers="+params[1];
			String Styles = "Styles="+params[2];
	        String Format = "Format="+params[3];
	        String Service= "Service="+params[4];
	        String Version= "Version="+params[5];
	        String Request = "Request="+params[6];
	        String SRS ="SRS="+params[7];
	        String BBOX = "BBOX="+params[8];
	        String Width = "Width="+params[9];
	        String Height = "Height="+params[10];
	        
	        final_url= WMS_URL + "?"+Layers+"&"+Styles+"&"+Format+"&"+Service+"&"+Version+"&"+Request+"&"+SRS+"&"+BBOX+"&"+Width+"&"+Height;
			Log.d(debug_tag, final_url);
	        java.net.URL url = new java.net.URL(final_url);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        Log.e(debug_tag, e.toString());
	        return null;
	    }
	}
	
	@Override
    protected void onPostExecute(Bitmap result) {
        this.img_view.setImageBitmap(result);
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}
