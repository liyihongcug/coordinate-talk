package org.guohai.android.CoordinateTalk;


import android.app.Activity;
import android.view.Display;

public class Tools extends Activity{
	
	//get diskplay weightd
	public int GetResolutionWeight()
	{
		Display dis = getWindowManager().getDefaultDisplay();
		return dis.getWidth();
	}
}
