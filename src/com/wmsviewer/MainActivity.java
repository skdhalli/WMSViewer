package com.wmsviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

	WMSImageGenerator wms_service = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = (ImageView)findViewById(R.id.img_view);
        
        
        wms_service = new WMSImageGenerator(img);
        String WMS_URL= getString(R.string.WMS_URL);
		String Layers = getString(R.string.Layers);
		String Styles = getString(R.string.Styles);
        String Format = getString(R.string.Format);
        String Service= getString(R.string.Service);
        String Version= getString(R.string.Version);
        String Request = getString(R.string.Request);
        String SRS =getString(R.string.SRS);
        String BBOX = getString(R.string.BBOX);
        String Width = getString(R.string.Width);
        String Height = getString(R.string.Height);
        wms_service.execute(WMS_URL, Layers, Styles, Format, Service, Version, Request, SRS, BBOX, Width, Height);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
