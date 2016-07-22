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

import wsu.csc5991.trustcircle.vo.Circle;
import wsu.csc5991.trustcircle.vo.Member;

public class ActCircleSignUp extends AppCompatActivity {

    EditText editTextCircleName;
    EditText editTextCirclePassword;
    EditText editTextConfirmCirclePassword;
    EditText editTextPrimaryMemberMobileNumber;
    EditText editTextPrimaryMemberPassword;
    Button buttonCreateCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laycirclesignup);
        ((LinearLayout)findViewById(R.id.LayCircleSignUp)).setBackgroundColor(Setting.Shared.Data.backgroundColor);

        editTextCircleName = (EditText) findViewById(R.id.editTextCircleName);
        editTextCirclePassword = (EditText) findViewById(R.id.editTextCirclePassword);
        editTextConfirmCirclePassword = (EditText) findViewById(R.id.editTextConfirmCirclePassword);
        editTextPrimaryMemberMobileNumber = (EditText) findViewById(R.id.editTextPrimaryMemberMobileNumber);
        editTextPrimaryMemberPassword = (EditText) findViewById(R.id.editTextPrimaryMemberPassword);
        buttonCreateCircle = (Button) findViewById(R.id.buttonCreateCircle);

        buttonCreateCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean isValidInput = true;
                String circleName = editTextCircleName.getText().toString();
                String circlePassword = editTextCirclePassword.getText().toString();
                String confirmCirclePassword = editTextConfirmCirclePassword.getText().toString();
                String primaryMemberMobileNumber = editTextPrimaryMemberMobileNumber.getText().toString();
                String primaryMemberPassword = editTextPrimaryMemberPassword.getText().toString();

                if (circleName.length() == 0) {
                    editTextCircleName.setError("Circle name is required to create circle!");
                    isValidInput = false;
                }
                if (circlePassword.length() == 0) {
                    editTextCirclePassword.setError("Circle pin is required to create circle!");
                    isValidInput = false;
                }
                if (confirmCirclePassword.length() == 0) {
                    editTextConfirmCirclePassword.setError("Please confirm the circle pin!");
                    isValidInput = false;
                }
                if (!confirmCirclePassword.equals(circlePassword)) {
                    editTextConfirmCirclePassword.setError("The circle pins does not match!");
                    isValidInput = false;
                }
                if (primaryMemberMobileNumber.length() != 10) {
                    editTextPrimaryMemberMobileNumber.setError("Primary member mobile number should be 10 digits long!");
                    isValidInput = false;
                }
                if (primaryMemberPassword.length() == 0) {
                    editTextPrimaryMemberPassword.setError("Primary member pin is required to create circle!");
                    isValidInput = false;
                }
                if (isValidInput) {
                    new HttpRequestTask().execute(circleName, circlePassword, primaryMemberMobileNumber, primaryMemberPassword);
                }
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Boolean> {

        String errorMessage = null;

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean output = false;
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                Circle circle = new Circle();
                circle.setName(params[0]);
                circle.setPin(Integer.parseInt(params[1]));

                Member member = new Member();
                member.setMobileNumber(Integer.parseInt(params[2]));
                member.setPin(Integer.parseInt(params[3]));
                circle.setPrimaryMember(member);

                String getMemberUrl = getResources().getString(R.string.rest_service_url) + "/member/mobile/" + params[2];
                Member storedMember = restTemplate.getForObject(getMemberUrl, Member.class);

                if (storedMember != null) {
                    if (storedMember.getPin() == Integer.parseInt(params[3])) {
                        String circleSignUpUrl = getResources().getString(R.string.rest_service_url) + "/circle";
                        output = restTemplate.postForObject(new URI(circleSignUpUrl), circle, Boolean.class);
                    } else {
                        errorMessage = "Trust Circle creation failed!\nInvalid member pin, please enter the correct member pin.";
                    }
                } else {
                    errorMessage = "Trust Circle creation failed!\nMember not registered, please sign up the member first.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Trust Circle creation failed!\nMember not registered, please sign up the member first.";
            }
            return output;
        }

        @Override
        protected void onPostExecute(Boolean output) {
            if (output) {
                Setting.showDialogBox(ActCircleSignUp.this, "Trust Circle SignUp", "Trust Circle successfully created!");
            } else {
                errorMessage = errorMessage != null ? errorMessage : "Trust Circle creation failed!";
                Setting.showDialogBox(ActCircleSignUp.this, "Trust Circle SignUp", errorMessage);
            }
        }
    }
}
