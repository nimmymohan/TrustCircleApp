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

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import wsu.csc5991.trustcircle.vo.Circle;
import wsu.csc5991.trustcircle.vo.Member;

/**
 * Class to let a member join a circle by providing circle name and pin
 */
public class ActJoinCircle extends ActBase {

    EditText editTextCircleName;
    EditText editTextCirclePassword;
    EditText editTextMemberMobileNumber;
    EditText editTextMemberPassword;
    Button buttonJoinCircle;
    String memberMobileNumber;
    String memberPin;
    int enteredMemberPin;
    Circle memberCircle = null;

    //----------------------------------------------------------------
    // Validates the inputs
    // If validation fails, display the error messages
    // If validation succeeds, invoke rest service to add member to the circle
    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layjoincircle);
        ((LinearLayout)findViewById(R.id.LayJoinCircle)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        editTextCircleName = (EditText) findViewById(R.id.editTextCircleName);
        editTextCirclePassword = (EditText) findViewById(R.id.editTextCirclePassword);
        editTextMemberMobileNumber = (EditText) findViewById(R.id.editTextMemberMobileNumber);
        editTextMemberPassword = (EditText) findViewById(R.id.editTextMemberPassword);
        buttonJoinCircle = (Button) findViewById(R.id.buttonJoinCircle);

        Bundle extras = getIntent().getExtras();
        memberMobileNumber = String.valueOf(extras.getInt("member_mobile_number"));
        memberPin = String.valueOf(extras.getInt("member_pin"));

        editTextMemberMobileNumber.setText(memberMobileNumber);
        editTextMemberMobileNumber.setEnabled(false);

        editTextMemberPassword.setText(String.valueOf(extras.getInt("member_pin")));
        editTextMemberPassword.setEnabled(false);

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

                /*
                if (editTextMemberMobileNumber.getText().length() != 10) {
                    editTextMemberMobileNumber.setError("Member mobile number should be 10 digits long!");
                    isValidInput = false;
                }
                if (editTextMemberPassword.getText().length() == 0) {
                    editTextMemberPassword.setError("Member pin is required to join circle!");
                    isValidInput = false;
                }
                */
                if (isValidInput) {
                    new HttpRequestTask().execute(circleName, circlePassword, memberMobileNumber, memberPassword);
                }
            }
        });
    }

    //----------------------------------------------------------------
    // Invokes the rest service to add a member to a circle
    //----------------------------------------------------------------
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
                List<Member> members = new ArrayList<Member>();
                members.add(member);
                circle.setMembers(members);

                String getCircleUrl = getResources().getString(R.string.rest_service_url) + "/circle/search/?name=" + params[0];
                Circle storedCircle = restTemplate.getForObject(getCircleUrl, Circle.class);

                String getMemberUrl = getResources().getString(R.string.rest_service_url) + "/member/mobile/" + params[2];
                Member storedMember = restTemplate.getForObject(getMemberUrl, Member.class);

                if (storedCircle != null) {
                    if (storedCircle.getPin() == Integer.parseInt(params[1])) {
                        if (storedMember != null) {
                            if (storedMember.getPin() == Integer.parseInt(params[3])) {
                                String joinCircleUrl = getResources().getString(R.string.rest_service_url) + "/circle/name/" + params[0];
                                output = restTemplate.postForObject(new URI(joinCircleUrl), circle, Boolean.class);
                            } else {
                                errorMessage = "Trust Circle enrolment failed!\nInvalid member pin, please enter the correct member pin.";
                            }
                        } else {
                            errorMessage = "Trust Circle enrolment failed!\nMember not registered, please sign up the member first.";
                        }
                    } else {
                        errorMessage = "Trust Circle enrolment failed!\nInvalid circle pin, please enter the correct circle pin.";
                    }
                } else {
                    errorMessage = "Trust Circle enrolment failed!\nCircle not registered, please create the circle first.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Trust Circle enrolment failed!\nPlease enter correct values and try again.";
            }
            return output;
        }

        @Override
        protected void onPostExecute(Boolean output) {
            if (output) {
                Util.showDialogBox(ActJoinCircle.this, "Trust Circle Enrolment", "Trust Circle enrolment successful!");

                AlertDialog alertDialog = new AlertDialog.Builder(ActJoinCircle.this).create();
                alertDialog.setTitle("Trust Circle Enrolment");
                alertDialog.setMessage("Trust Circle enrolment successful!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new DisplayCircleTask().execute(memberMobileNumber, memberPin);
                        }
                    });
                alertDialog.show();
            } else {
                errorMessage = errorMessage != null ? errorMessage : "Trust Circle enrolment failed!";
                Util.showDialogBox(ActJoinCircle.this, "Trust Circle Enrolment", errorMessage);
            }
        }
    }

    //----------------------------------------------------------------
    // Invokes the rest service to display latitude and longitude of a members of the circle
    //----------------------------------------------------------------
    private class DisplayCircleTask extends AsyncTask<String, Void, Member> {
        @Override
        protected Member doInBackground(String... params) {
            Member member = null;
            enteredMemberPin = Integer.parseInt(params[1]);
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                String getMemberUrl = getResources().getString(R.string.rest_service_url) + "/member/mobile/" + params[0];
                member = restTemplate.getForObject(getMemberUrl, Member.class);

                String getCircleUrl = getResources().getString(R.string.rest_service_url) + "/circle/event/?mobile=" + params[0];
                memberCircle = restTemplate.getForObject(getCircleUrl, Circle.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return member;
        }

        @Override
        protected void onPostExecute(Member member) {
            if(member != null) {
                if (enteredMemberPin == member.getPin()) {
                    if (memberCircle != null) {
                        Intent i = new Intent(getApplicationContext(), ActDisplayCircle.class);
                        i.putExtra("member_first_name", member.getFirstName());
                        i.putExtra("member_last_name", member.getLastName());
                        i.putExtra("circle_name", memberCircle.getName());

                        Bundle memberBundle = new Bundle();
                        memberBundle.putSerializable("memberList",(Serializable)memberCircle.getMembers());
                        i.putExtra("memberBundle", memberBundle);

                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(), ActCircleConfig.class);
                        startActivity(i);
                    }
                } else {
                    Util.showDialogBox(ActJoinCircle.this, "Trust Circle SignIn", "Invalid pin, please enter correct pin!");
                }
            } else {
                Util.showDialogBox(ActJoinCircle.this, "Trust Circle SignIn", "Invalid member, please sign-up first!");
            }
        }
    }
}
