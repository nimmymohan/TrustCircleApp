package wsu.csc5991.trustcircle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import wsu.csc5991.trustcircle.vo.Member;

public class ActMemberSignUp extends AppCompatActivity {

    EditText editTextMobileNumber;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    Button buttonCreateMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymembersignup);
        ((LinearLayout)findViewById(R.id.LayMemberSignUp)).setBackgroundColor(Setting.Shared.Data.backgroundColor);

        editTextMobileNumber = (EditText) findViewById(R.id.editTextMobileNumber);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        buttonCreateMember = (Button) findViewById(R.id.buttonCreateMember);


        buttonCreateMember.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean isValidInput = true;
                String mobileNumber = editTextMobileNumber.getText().toString();
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (mobileNumber.length() != 10) {
                    editTextMobileNumber.setError("Mobile number should be 10 digits long!");
                    isValidInput = false;
                }
                if (firstName.length() == 0) {
                    editTextFirstName.setError("First name is required to create member!");
                    isValidInput = false;
                }
                if (lastName.length() == 0) {
                    editTextLastName.setError("Last name is required to create member!");
                    isValidInput = false;
                }
                if (password.length() == 0) {
                    editTextPassword.setError("Pin is required to create member!");
                    isValidInput = false;
                }
                if (confirmPassword.length() == 0) {
                    editTextConfirmPassword.setError("Please confirm the pin!");
                    isValidInput = false;
                }
                if (!confirmPassword.equals(password)) {
                    editTextConfirmPassword.setError("The pins does not match!");
                    isValidInput = false;
                }
                if (isValidInput) {
                    new HttpRequestTask().execute(firstName, lastName, mobileNumber, password);
                }
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Boolean output = false;
            try {
                String url = getResources().getString(R.string.rest_service_url) + "/member";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                Member member = new Member();
                member.setFirstName(params[0]);
                member.setLastName(params[1]);
                member.setMobileNumber(Integer.parseInt(params[2]));
                member.setPin(Integer.parseInt(params[3]));

                output = restTemplate.postForObject(new URI(url), member, Boolean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output;
        }

        @Override
        protected void onPostExecute(Boolean output) {
            if (output) {
                Setting.showDialogBox(ActMemberSignUp.this, "Trust Circle Member SignUp", "Member successfully created!");
            } else {
                Setting.showDialogBox(ActMemberSignUp.this, "Trust Circle Member SignUp", "Member creation failed!");
            }
        }
    }
}
