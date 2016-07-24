package wsu.csc5991.trustcircle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import wsu.csc5991.trustcircle.vo.Member;

public class ActBase extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuitems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent aboutIntent = new Intent(getApplicationContext(), ActAbout.class);
                startActivity(aboutIntent);
                return true;

            case R.id.settings:
                Intent settingsIntent = new Intent(getApplicationContext(), ActSettings.class);
                startActivity(settingsIntent);
                return true;

            case R.id.help:
                Intent helpIntent = new Intent(getApplicationContext(), ActHelp.class);
                startActivity(helpIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
