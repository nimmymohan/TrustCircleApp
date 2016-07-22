package wsu.csc5991.trustcircle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActMain extends AppCompatActivity {

    Button buttonSignIn;
    Button buttonJoinCircle;
    Button buttonMemberSignUp;
    Button buttonCircleSignUp;
    Button buttonEditCircle;
    Button buttonDeleteCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonJoinCircle = (Button) findViewById(R.id.buttonJoinCircle);
        buttonMemberSignUp = (Button) findViewById(R.id.buttonMemberSignUp);
        buttonCircleSignUp = (Button) findViewById(R.id.buttonCircleSignUp);
        buttonEditCircle = (Button) findViewById(R.id.buttonEditCircle);
        buttonDeleteCircle = (Button) findViewById(R.id.buttonDeleteCircle);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),ActSignIn.class);
            startActivity(i);
            }
        });
        buttonJoinCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),ActJoinCircle.class);
            startActivity(i);
            }
        });
        buttonMemberSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),ActMemberSignUp.class);
            startActivity(i);
            }
        });
        buttonCircleSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),ActCircleSignUp.class);
            startActivity(i);
            }
        });
        buttonEditCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),ActEditCircle.class);
            startActivity(i);
            }
        });
        buttonDeleteCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),ActDeleteCircle.class);
            startActivity(i);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i=0; i<menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            String title = mi.getTitle().toString();
            Spannable newTitle = new SpannableString(title);
            newTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(newTitle);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuitems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bg_white:
                changeBackgroundColor(getResources().getColor(R.color.ghost_white));
                return true;

            case R.id.bg_yellow:
                changeBackgroundColor(getResources().getColor(R.color.light_yellow));
                return true;

            case R.id.bg_green:
                changeBackgroundColor(getResources().getColor(R.color.light_green));
                return true;

            case R.id.bg_blue:
                changeBackgroundColor(getResources().getColor(R.color.light_blue));
                return true;

            case R.id.help:
                Toast toast = Toast.makeText(getApplicationContext(), "RAKSHIKANEY", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.START, 0, 0);
                toast.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeBackgroundColor(int color) {
        ((LinearLayout)findViewById(R.id.LayMain)).setBackgroundColor(color);
        Setting.Shared.Data.backgroundColor = color;
    }
}
