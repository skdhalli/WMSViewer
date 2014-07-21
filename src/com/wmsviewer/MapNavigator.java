package com.wmsviewer;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MapNavigator {

	public MapNavigator(double _xmin, double _xmax, double _ymin, double _ymax, int _zoom_level)
	{
		this.xmin = _xmin;
		this.xmax = _xmax;
		this.ymin = _ymin;
		this.ymax = _ymax;
		this.zoom_level = _zoom_level;
		
		this.distance_per_zoom_level = Math.sqrt(Math.pow((xmax-xmin),2) + Math.pow((ymax-ymin),2))/(2*zoom_level);
	}
	
	double xmin;
	double xmax;
	double ymin;
	double ymax;
	int zoom_level;
	//increasing pan sensitivity with decrease the movement of the map with each swipe
	int pan_sensitivity =20;
	double distance_per_zoom_level;
	
	public enum PanDirection{left, right, up, down, rightup, rightdown, leftup, leftdown};
	public String ZoomChanged(int zoom, double center_x, double center_y)
	{
		String retval = "";
		double slope= Math.tanh((ymax-ymin)/(xmax-xmin));
		double _xmax = center_x +(this.distance_per_zoom_level*zoom*Math.cos(slope));
		double _xmin = center_x -(this.distance_per_zoom_level*zoom*Math.cos(slope));
		double _ymax = center_y +(this.distance_per_zoom_level*zoom*Math.sin(slope));
		double _ymin = center_y -(this.distance_per_zoom_level*zoom*Math.sin(slope));
		retval= Double.toString(_xmin)+","+Double.toString(_ymin)+","+Double.toString(_xmax)+","+Double.toString(_ymax);
		return retval;
	}
	
	public String Pan(PanDirection direction, int zoom, double center_x, double center_y)
	{
		String retval = "";
		double slope= Math.tanh((ymax-ymin)/(xmax-xmin));
		double _xmax = center_x +(this.distance_per_zoom_level*zoom*Math.cos(slope));
		double _xmin = center_x -(this.distance_per_zoom_level*zoom*Math.cos(slope));
		double _ymax = center_y +(this.distance_per_zoom_level*zoom*Math.sin(slope));
		double _ymin = center_y -(this.distance_per_zoom_level*zoom*Math.sin(slope));
		double swipe_x =(_xmax-_xmin)/(2*pan_sensitivity);
		double swipe_y =(_ymax-_ymin)/(2*pan_sensitivity);
		switch(direction)
		{
			case right:
				_xmax = _xmax + swipe_x;
				_xmin = _xmin + swipe_x;
				break;
			case left:
				_xmax = _xmax - swipe_x;
				_xmin = _xmin - swipe_x;
				break;
			case up:
				_ymax = _ymax + swipe_y;
				_ymin = _ymin + swipe_y;
				break;
			case down:
				_ymax = _ymax - swipe_y;
				_ymin = _ymin - swipe_y;
				break;
		}
		retval= Double.toString(_xmin)+","+Double.toString(_ymin)+","+Double.toString(_xmax)+","+Double.toString(_ymax);
		return retval;
	}

	

	
}
