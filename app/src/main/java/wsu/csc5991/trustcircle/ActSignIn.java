package wsu.csc5991.trustcircle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import wsu.csc5991.trustcircle.vo.Member;


public class ActSignIn extends AppCompatActivity {

    EditText editTextMobileNumberToLogin;
    EditText editTextPasswordToLogin;
    Button buttonSignIn;
    int pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laysignin);
        ((LinearLayout)findViewById(R.id.LaySignIn)).setBackgroundColor(Setting.Shared.Data.backgroundColor);

        editTextMobileNumberToLogin = (EditText) findViewById(R.id.editTextMobileNumberToLogin);
        editTextPasswordToLogin = (EditText) findViewById(R.id.editTextPasswordToLogin);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String mobileNumber = editTextMobileNumberToLogin.getText().toString();
                String password = editTextPasswordToLogin.getText().toString();
                boolean isValidInput = true;
                if (mobileNumber.length() != 10) {
                    editTextMobileNumberToLogin.setError("Mobile number should be 10 digits long!");
                    isValidInput = false;
                }
                if (password.length() == 0) {
                    editTextPasswordToLogin.setError("Password is required to sign in!");
                    isValidInput = false;
                }
                if (isValidInput) {
                    new HttpRequestTask().execute(mobileNumber, password);
                }
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Member> {
        @Override
        protected Member doInBackground(String... params) {
            try {
                String url = getResources().getString(R.string.rest_service_url) + "/member/mobile/" + params[0];

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Member member = restTemplate.getForObject(url, Member.class);
                pin = Integer.parseInt(params[1]);

                return member;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Member member) {
            if(member != null) {
                if (pin == member.getPin()) {
                    Intent i = new Intent(getApplicationContext(), ActDisplayCircle.class);
                    startActivity(i);
                } else {
                    Setting.showDialogBox(ActSignIn.this, "Trust Circle SignIn", "Invalid Password!");
                }
            } else {
                Setting.showDialogBox(ActSignIn.this, "Trust Circle SignIn", "Invalid User!");
            }
        }
    }
}
