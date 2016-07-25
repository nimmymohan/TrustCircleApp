package wsu.csc5991.trustcircle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import wsu.csc5991.trustcircle.vo.Event;
import wsu.csc5991.trustcircle.vo.Member;

public class ActDisplayCircle extends ActBase {

    TableLayout memberTable;
    TextView circleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydisplaycircle);
        ((LinearLayout)findViewById(R.id.LayDisplayCircle)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();

        memberTable = (TableLayout) findViewById(R.id.MemberTable);
        circleName = (TextView) findViewById(R.id.circleName);
        circleName.setText(extras.getString("circle_name"));

        init(extras);
    }

    public void init(Bundle extras) {
        memberTable.setStretchAllColumns(true);
        memberTable.bringToFront();

        addHeader();

        Bundle args = getIntent().getBundleExtra("memberBundle");
        ArrayList<Member> memberList = (ArrayList<Member>) args.getSerializable("memberList");

        Integer count = 0;
        String name = null;
        String latitude = null;
        String longitude = null;

        for (Member member : memberList) {
            name = member.getFirstName() + " " + member.getLastName();
            List<Event> eventList = member.getEvents();
            if(eventList != null) {
                for (Event event : eventList) {
                    String[] parts = event.getValue().split(",");
                    latitude = parts[0];
                    longitude = parts[1];
                    addRow(count, name, latitude, longitude);
                    count++;
                }
            }
        }
    }

    private void addHeader() {
        TableRow tr_head = new TableRow(this);
        tr_head.setBackgroundColor(getResources().getColor(R.color.blue));
        tr_head.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        TextView label_name = new TextView(this);
        label_name.setText("NAME");
        label_name.setTextColor(Color.WHITE);
        label_name.setPadding(5, 5, 5, 5);
        label_name.setTextSize(18);
        tr_head.addView(label_name);

        TextView label_latitude = new TextView(this);
        label_latitude.setText("LATITUDE");
        label_latitude.setTextColor(Color.WHITE);
        label_latitude.setPadding(5, 5, 5, 5);
        label_latitude.setTextSize(18);
        tr_head.addView(label_latitude);

        TextView label_longitude = new TextView(this);
        label_longitude.setText("LONGITUDE");
        label_longitude.setTextColor(Color.WHITE);
        label_longitude.setPadding(5, 5, 5, 5);
        label_longitude.setTextSize(18);
        tr_head.addView(label_longitude);

        memberTable.addView(tr_head, new TableLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
    }


    private void addRow(int count, String name, String latitude, String longitude) {
        TableRow tr = new TableRow(this);
        if(count%2!=0) {
            tr.setBackgroundColor(getResources().getColor(R.color.light_blue));
        } else {
            tr.setBackgroundColor(getResources().getColor(R.color.white));
        }
        tr.setId(100+count);
        tr.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        TextView labelName = new TextView(this);
        labelName.setText(name);
        labelName.setPadding(2, 0, 5, 0);
        labelName.setTextColor(Color.BLACK);
        labelName.setTextSize(16);
        tr.addView(labelName);

        TextView labelLatitude = new TextView(this);
        labelLatitude.setText(latitude);
        labelLatitude.setTextColor(Color.BLACK);
        labelLatitude.setTextSize(16);
        tr.addView(labelLatitude);

        TextView labelLongitude = new TextView(this);
        labelLongitude.setText(longitude);
        labelLongitude.setTextColor(Color.BLACK);
        labelLongitude.setTextSize(16);
        tr.addView(labelLongitude);

        memberTable.addView(tr, new TableLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
    }
}
