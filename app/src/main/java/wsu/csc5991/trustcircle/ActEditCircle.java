package wsu.csc5991.trustcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActEditCircle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layeditcircle);
        ((LinearLayout)findViewById(R.id.LayEditCircle)).setBackgroundColor(Setting.Shared.Data.backgroundColor);
    }
}
