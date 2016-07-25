//==================================================================================================
//
// Title:       Trust Circle
// Course:      CSC 5991 – Mobile Application Development in Android
// Application: Final Project
// Author:      Vimal Sasidharan, Nimmy Mohan Mohan
// Date:        07/25/2016
// Description:
//              Trust circle app lets your family members and friends to be in a closed loop.
//              It helps in tracking the location of a member with in the circle.
//              The application has below screens:
//          Main Screen
//              ● Members can sign in using mobile number or pin
//              ● Members can click sign up button if not already registered
//          DisplayCircle Screen
//              ● Signed in member's circle name will be displayed
//              ● Circle member's latitude and longitude will be displayed in a table
//          MemberSignUp Screen
//              ● A new member can be created by providing mobile number, first name, last name, pin
//          CircleConfig Screen
//              ● This has all the buttons for configuring a circle
//          CircleSignUp Screen
//              ● A new circle can be created by providing circle name and pin
//          JoinCircle Screen
//              ● A member can join a circle by providing circle name and pin
//          Settings Screen
//              ● The app's background color can be changed using this screen
//          About Screen
//              ● A brief description of app's functionality is provided in this screen
//          Help Screen
//              ● This screen talks about few use cases
//
//==================================================================================================

package wsu.csc5991.trustcircle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

import wsu.csc5991.trustcircle.vo.Circle;
import wsu.csc5991.trustcircle.vo.Member;

/**
 * Class for member sign in and sign up
 */
public class ActMain extends ActBase {

    EditText etPhoneNumber;
    EditText etPin;
    Button btnSignIn;
    Button btnSignUp;
    int enteredMemberPin;
    Circle memberCircle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);
        ((LinearLayout)findViewById(R.id.LayMain)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPin = (EditText) findViewById(R.id.etPin);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn(v);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActMemberSignUp.class);
                startActivity(i);
            }
        });
    }

    //----------------------------------------------------------------
    // Validates the inputs
    // If validation fails, display the error messages
    // If validation succeeds, invoke rest service to get circle details
    //----------------------------------------------------------------
    public void signIn(View view){
        String mobileNumber = etPhoneNumber.getText().toString();
        String password = etPin.getText().toString();
        boolean isValidInput = true;
        if (mobileNumber.length() != 10) {
            etPhoneNumber.setError("Mobile number should be 10 digits long!");
            isValidInput = false;
        }
        if (password.length() == 0) {
            etPin.setError("Password is required to sign in!");
            isValidInput = false;
        }
        if (isValidInput) {
            new HttpRequestTask().execute(mobileNumber, password);
        }
    }

    //----------------------------------------------------------------
    // Invokes the rest service to get circle details
    //----------------------------------------------------------------
    private class HttpRequestTask extends AsyncTask<String, Void, Member> {
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
                    Util.showDialogBox(ActMain.this, "Trust Circle SignIn", "Invalid pin, please enter correct pin!");
                }
            } else {
                Util.showDialogBox(ActMain.this, "Trust Circle SignIn", "Invalid member, please sign-up first!");
            }
        }
    }
}