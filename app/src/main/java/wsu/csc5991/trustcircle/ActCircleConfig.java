package wsu.csc5991.trustcircle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.text.DecimalFormat;

import wsu.csc5991.trustcircle.task.RecordEventTask;
import wsu.csc5991.trustcircle.vo.Member;

/**
 * Class to handle all the buttons for configuring a circle
 */
public class ActCircleConfig extends ActBase {
    Button buttonJoinCircle;
    Button buttonCircleSignUp;
    Button buttonEditCircle;
    Button buttonDeleteCircle;

    Member member;
    // Declare GPS constants
    private final float MIN_DISTANCE_BETWEEN_UPDATES = 1f;  // Meters
    private final long MIN_TIME_BETWEEN_UPDATES = 3600000;  // 1hr Milliseconds
    private final DecimalFormat FORMAT_COORDINATE = new DecimalFormat("0.000000");

    // Declare location variables
    private Location currentLocation;  // Set from GPS sensor
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laycircleconfig);
        ((LinearLayout)findViewById(R.id.LayCircleConfig)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);

        /*buttonSignIn = (Button) findViewById(R.id.buttonSignIn);*/
        buttonJoinCircle = (Button) findViewById(R.id.buttonJoinCircle);
        buttonCircleSignUp = (Button) findViewById(R.id.buttonCircleSignUp);
        buttonEditCircle = (Button) findViewById(R.id.buttonEditCircle);
        buttonDeleteCircle = (Button) findViewById(R.id.buttonDeleteCircle);

        buttonJoinCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                Intent i = new Intent(getApplicationContext(), ActJoinCircle.class);

                System.out.println("member_mobile_number: " + extras.getInt("member_mobile_number"));
                System.out.println("member_pin: " + extras.getInt("member_pin"));
                i.putExtra("member_mobile_number",extras.getInt("member_mobile_number"));
                i.putExtra("member_pin",extras.getInt("member_pin"));

                startActivity(i);
            }
        });
        buttonCircleSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActCircleSignUp.class);
                startActivity(i);
            }
        });
        buttonEditCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActEditCircle.class);
                startActivity(i);
            }
        });
        buttonDeleteCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActDeleteCircle.class);
                startActivity(i);
            }
        });


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Start location manager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permission not granted");
            return;
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME_BETWEEN_UPDATES,
            MIN_DISTANCE_BETWEEN_UPDATES,
            locationListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("### DEBUG ### onStart");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            member = new Member();
            member.setId(extras.getInt("m_Id"));
            member.setMobileNumber(extras.getInt("m_Phone"));
            member.setPin(extras.getInt("m_pin"));
            member.setFirstName(extras.getString("m_first_name"));
            member.setLastName(extras.getString("m_last_name"));
        }
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            String title = mi.getTitle().toString();
            Spannable newTitle = new SpannableString(title);
            newTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(newTitle);
        }
        return true;
    }

    //----------------------------------------------------------------
    // locationListener
    //----------------------------------------------------------------
    public LocationListener locationListener = new LocationListener() {

        //------------------------------------------------------------
        // onLocationChanged
        //------------------------------------------------------------
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
            System.out.println("On Location Changed Method ");
            if(member != null && member.getMobileNumber() > 0){
                String locationStr = FORMAT_COORDINATE.format(currentLocation.getLatitude()) + "," + FORMAT_COORDINATE.format(currentLocation.getLongitude());
                String[] arr = {String.valueOf(member.getMobileNumber()), String.valueOf(member.getPin()) , locationStr, getResources().getString(R.string.rest_service_url).toString()};
                new RecordEventTask().execute(arr);
            }
        }

        //------------------------------------------------------------
        // onStatusChanged
        //------------------------------------------------------------
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        }

        //------------------------------------------------------------
        // onProviderEnabled
        //------------------------------------------------------------
        @Override
        public void onProviderEnabled(String provider) {
        }

        //------------------------------------------------------------
        // onProviderDisabled
        //------------------------------------------------------------
        @Override
        public void onProviderDisabled(String provider) {
        }

    };
}
