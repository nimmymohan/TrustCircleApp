package wsu.csc5991.trustcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class ActDeleteCircle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydeletecircle);
        ((LinearLayout)findViewById(R.id.LayDeleteCircle)).setBackgroundColor(Setting.Shared.Data.backgroundColor);
    }
}
