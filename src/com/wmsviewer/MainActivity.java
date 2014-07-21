package com.wmsviewer;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity implements OnClickListener {

	WMSImageGenerator wms_service = null;
	WMSRequestInfo wms_request = null;
	String debug_tag = "Main Activity";
	ImageView img;
	int zoom_level_init = 20;
	int zoom_level_current = 20;
	double xmin_current = 0;
	double ymin_current = 0;
	double xmax_current = 0;
	double ymax_current = 0;
	double xmin_init = 0;
	double ymin_init = 0;
	double xmax_init = 0;
	double ymax_init = 0;
	ImageButton zoomout;
	ImageButton zoomin;
	ImageButton left;
	ImageButton right;
	ImageButton up;
	ImageButton down;
	
	MapNavigator mapNavigator = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        img = (ImageView)findViewById(R.id.img_view);
        wms_service = new WMSImageGenerator(img);
        this.initialize_request();
        String BBOX = getString(R.string.BBOX);
        xmin_init = Double.parseDouble(BBOX.split(",")[0]);
        xmin_current =xmin_init;
        ymin_init = Double.parseDouble(BBOX.split(",")[1]);
        ymin_current= ymin_init;
        xmax_init = Double.parseDouble(BBOX.split(",")[2]);
        xmax_current = xmax_init;
        ymax_init = Double.parseDouble(BBOX.split(",")[3]);
        ymax_current = ymax_init;
        mapNavigator = new MapNavigator(xmin_init, ymin_init, xmax_init, ymax_init, zoom_level_init);
        zoomin = (ImageButton)findViewById(R.id.zoomin);
        zoomout = (ImageButton)findViewById(R.id.zoomout);
        left = (ImageButton)findViewById(R.id.panleft);
        right = (ImageButton)findViewById(R.id.panright);
        up = (ImageButton)findViewById(R.id.panup);
        down = (ImageButton)findViewById(R.id.pandown);
        zoomin.setOnClickListener(this);
        zoomout.setOnClickListener(this);
        up.setOnClickListener(this);
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        down.setOnClickListener(this);
        
        ViewTreeObserver vto = img.getViewTreeObserver(); 
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
            
        	@SuppressWarnings("deprecation")
        	@SuppressLint("NewApi")
			@Override 
            public void onGlobalLayout() { 
            	
        		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    img.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    img.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            	Log.d(debug_tag, "Image view measured");
            	int width  =img.getMeasuredWidth();
                int height = img.getMeasuredHeight(); 
                String Width = Integer.toString(width);
                String Height = Integer.toString(height);
                wms_request.Width = Width;
                wms_request.Height = Height;
                if(!wms_service.isBusy)
                {
                	wms_service.isBusy = true;
                	Log.d(debug_tag, "Calling service");
                	wms_service.execute(wms_request);
                }
                
            } 
        });
    }


    private void initialize_request()
    {
    	this.wms_request = new WMSRequestInfo();
    	String WMS_URL= getString(R.string.WMS_URL);
		String Layers = getString(R.string.Layers);
		String Styles = getString(R.string.Styles);
        String Format = getString(R.string.Format);
        String Service= getString(R.string.Service);
        String Version= getString(R.string.Version);
        String Request = getString(R.string.Request);
        String SRS =getString(R.string.SRS);
        String BBOX = getString(R.string.BBOX);
    	this.wms_request.BBOX= BBOX;
    	this.wms_request.SRS = SRS;
    	this.wms_request.Request = Request;
    	this.wms_request.Version = Version;
    	this.wms_request.Service = Service;
    	this.wms_request.Format = Format;
    	this.wms_request.Styles = Styles;
    	this.wms_request.Layers = Layers;
    	this.wms_request.WMS_URL = WMS_URL;
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


    @Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		ImageButton arg = (ImageButton)arg0;
		double center_x = (xmin_current+xmax_current)/2;
		double center_y = (ymin_current+ymax_current)/2;
		String bbox_new = "";
		if(arg.getTag().toString().compareTo("zoomin") == 0)
		{
			if(zoom_level_current > 1)
			{
				bbox_new = this.mapNavigator.ZoomChanged((zoom_level_current-1), center_x, center_y);
				zoom_level_current = zoom_level_current -1;
			}
		}
		else if(arg.getTag().toString().compareTo("zoomout") == 0)
		{
			if(zoom_level_current < 20)
			{
				bbox_new = this.mapNavigator.ZoomChanged((zoom_level_current-1), center_x, center_y);
				zoom_level_current = zoom_level_current +1;
			}
		}
		else if(arg.getTag().toString().compareTo("left") == 0)
		{
			if(xmin_current > xmin_init)
			{
				bbox_new = this.mapNavigator.Pan(MapNavigator.PanDirection.left, (zoom_level_current-1), center_x, center_y);
			}
		}
		else if(arg.getTag().toString().compareTo("right") == 0)
		{
			if(xmin_current < xmax_init)
			{
				bbox_new = this.mapNavigator.Pan(MapNavigator.PanDirection.right, (zoom_level_current-1), center_x, center_y);
			}
		}
		else if(arg.getTag().toString().compareTo("up") == 0)
		{
			if(ymax_current < ymax_init)
			{
				bbox_new = this.mapNavigator.Pan(MapNavigator.PanDirection.up, (zoom_level_current-1), center_x, center_y);
			}
		}
		else if(arg.getTag().toString().compareTo("down") == 0)
		{
			if(ymin_current > ymin_init)
			{
				bbox_new = this.mapNavigator.Pan(MapNavigator.PanDirection.down, (zoom_level_current-1), center_x, center_y);
			}
		}
		xmin_current = Double.parseDouble(bbox_new.split(",")[0]);
		ymin_current = Double.parseDouble(bbox_new.split(",")[1]);
		xmax_current = Double.parseDouble(bbox_new.split(",")[2]);
		ymax_current = Double.parseDouble(bbox_new.split(",")[3]);
		
		this.wms_request.BBOX = bbox_new;
		this.wms_service.execute(this.wms_request);
	}
    
}
