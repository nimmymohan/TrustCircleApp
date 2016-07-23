package wsu.csc5991.trustcircle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActDisplayCircle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydisplaycircle);
        ((ScrollView)findViewById(R.id.LayDisplayCircle)).setBackgroundColor(Setting.Shared.Data.backgroundColor);

        init();
    }

    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.MainTable);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText(" Name ");
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Latitude ");
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Longitude ");
        tbrow0.addView(tv2);

        stk.addView(tbrow0);
        for (int i = 0; i < 1; i++) {
            TableRow tbrow = new TableRow(this);

            TextView t1v = new TextView(this);
            t1v.setTextSize(20);
            t1v.setText("Vimal");
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setTextSize(20);
            t2v.setText("100");
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setTextSize(20);
            t3v.setText("200");
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            stk.addView(tbrow);
        }
    }
}
