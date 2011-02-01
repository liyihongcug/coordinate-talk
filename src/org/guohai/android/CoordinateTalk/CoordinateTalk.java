/******************************
 * 主窗体UI控制
 */

package org.guohai.android.CoordinateTalk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class CoordinateTalk extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Tools a =new Tools();
        findViews();
        textCoordinate.setText("xxx"+a.GetResolutionWeight());
    }
    
    private TextView textCoordinate;
    /* 查找所有的视图 */
    private void findViews()
    {
    	textCoordinate = (TextView)findViewById(R.id.coordinate);
    }
}