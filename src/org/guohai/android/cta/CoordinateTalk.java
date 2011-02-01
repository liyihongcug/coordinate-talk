/**
 * Copyright 2011 H!guo
 */
package org.guohai.android.cta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Main views 
 * @author H!Guo
 *
 */
public class CoordinateTalk extends Activity {

    private TextView textCoordinate;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        textCoordinate.setText("xxx");
    }
    /** Find all views */
    private void findViews()
    {
    	textCoordinate = (TextView)findViewById(R.id.coordinate);
    }
}
