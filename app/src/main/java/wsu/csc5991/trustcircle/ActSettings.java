package wsu.csc5991.trustcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * Class to change app's background color
 */
public class ActSettings extends AppCompatActivity {

    Button btnGoToMainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laysettings);
        ((LinearLayout)findViewById(R.id.LaySettings)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);

        //set spinner
        final Spinner spinnerBgColor = (Spinner) findViewById(R.id.spinnerBgColor);
        assert spinnerBgColor != null;
        spinnerBgColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinnerBgColor.getSelectedItem().toString()) {
                    case "White":
                        changeBackgroundColor(getResources().getColor(R.color.ghost_white));
                        return;
                    case "Yellow":
                        changeBackgroundColor(getResources().getColor(R.color.light_yellow));
                        return;
                    case "Blue":
                        changeBackgroundColor(getResources().getColor(R.color.light_blue));
                        return;
                    case "Green":
                        changeBackgroundColor(getResources().getColor(R.color.light_green));
                        return;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnGoToMainPage = (Button) findViewById(R.id.btnGoToMainPage);
        btnGoToMainPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActMain.class);
                startActivity(i);
            }
        });
    }

    private void changeBackgroundColor(int color) {
        Util.Shared.Data.backgroundColor = color;
        ((LinearLayout)findViewById(R.id.LaySettings)).setBackgroundColor(color);
    }
}
