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

public class ActJoinCircle extends AppCompatActivity {

    EditText editTextCircleName;
    EditText editTextCirclePassword;
    EditText editTextMemberMobileNumber;
    EditText editTextMemberPassword;
    Button buttonJoinCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layjoincircle);
        ((LinearLayout)findViewById(R.id.LayJoinCircle)).setBackgroundColor(Setting.Shared.Data.backgroundColor);

        editTextCircleName = (EditText) findViewById(R.id.editTextCircleName);
        editTextCirclePassword = (EditText) findViewById(R.id.editTextCirclePassword);
        editTextMemberMobileNumber = (EditText) findViewById(R.id.editTextMemberMobileNumber);
        editTextMemberPassword = (EditText) findViewById(R.id.editTextMemberPassword);
        buttonJoinCircle = (Button) findViewById(R.id.buttonJoinCircle);

        buttonJoinCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean isValidInput = true;
                String circleName = editTextCircleName.getText().toString();
                String circlePassword = editTextCirclePassword.getText().toString();
                String memberMobileNumber = editTextMemberMobileNumber.getText().toString();
                String memberPassword = editTextMemberPassword.getText().toString();

                if (editTextCircleName.getText().length() == 0) {
                    editTextCircleName.setError("Circle name is required to join circle!");
                    isValidInput = false;
                }
                if (editTextCirclePassword.getText().length() == 0) {
                    editTextCirclePassword.setError("Circle pin is required to join circle!");
                    isValidInput = false;
                }
                if (editTextMemberMobileNumber.getText().length() != 10) {
                    editTextMemberMobileNumber.setError("Member mobile number should be 10 digits long!");
                    isValidInput = false;
                }
                if (editTextMemberPassword.getText().length() == 0) {
                    editTextMemberPassword.setError("Member pin is required to join circle!");
                    isValidInput = false;
                }
                if (isValidInput) {
                    new HttpRequestTask().execute(circleName, circlePassword, memberMobileNumber, memberPassword);
                }
            }
        });
    }

    // TO BE MODIFIED - UNDER PROGRESS
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
                        //output = restTemplate.postForObject(new URI(circleSignUpUrl), circle, Boolean.class);
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
                Setting.showDialogBox(ActJoinCircle.this, "Trust Circle SignUp", "Trust Circle successfully created!");
            } else {
                errorMessage = errorMessage != null ? errorMessage : "Trust Circle creation failed!";
                Setting.showDialogBox(ActJoinCircle.this, "Trust Circle SignUp", errorMessage);
            }
        }
    }
}
