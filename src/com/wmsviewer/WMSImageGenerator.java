package com.wmsviewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class WMSImageGenerator extends  AsyncTask<WMSRequestInfo, Void, Bitmap> {

	public WMSImageGenerator(ImageView _img_view)
	{
		this.img_view = _img_view;
		
	}
	private String debug_tag = "WMS Image retrieval";
	private ImageView img_view;
	public boolean isBusy = false;
	
	@Override
	protected Bitmap doInBackground(WMSRequestInfo... params) {
		try {
			String final_url = "";
			//wms layer properties
			WMSRequestInfo input = params[0];
			String WMS_URL= input.WMS_URL;
			String Layers = "Layers="+input.Layers;
			String Styles = "Styles="+input.Styles;
	        String Format = "Format="+input.Format;
	        String Service= "Service="+input.Service;
	        String Version= "Version="+input.Version;
	        String Request = "Request="+input.Request;
	        String SRS ="SRS="+input.SRS;
	        String BBOX = "BBOX="+input.BBOX;
	        String Width = "Width="+input.Width;
	        String Height = "Height="+input.Height;
	        
	        final_url= WMS_URL + "?"+Layers+"&"+Styles+"&"+Format+"&"+Service+"&"+Version+"&"+Request+"&"+SRS+"&"+BBOX+"&"+Width+"&"+Height;
			java.net.URL url = new java.net.URL(final_url);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input_s = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input_s);
	        return myBitmap;
	    } catch (IOException e) {
	        Log.e(debug_tag, e.toString());
	        return null;
	    }
	}
	
	@Override
    protected void onPostExecute(Bitmap result) {
		this.img_view.setImageBitmap(result);
		isBusy = false;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
    
 
}
