package wsu.csc5991.trustcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Class to delete a circle
 * TO-DO: Yet to be implemented
 */
public class ActDeleteCircle extends ActBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydeletecircle);
        ((LinearLayout)findViewById(R.id.LayDeleteCircle)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);
    }
}
