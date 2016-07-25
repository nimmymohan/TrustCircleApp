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

import java.net.URI;

import wsu.csc5991.trustcircle.vo.Member;

/**
 * Class to create a new member by providing member details
 */
public class ActMemberSignUp extends ActBase {

    EditText editTextMobileNumber;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    Button buttonCreateMember;

    //----------------------------------------------------------------
    // Validates the inputs
    // If validation fails, display the error messages
    // If validation succeeds, invoke rest service to create a member
    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymembersignup);
        ((LinearLayout)findViewById(R.id.LayMemberSignUp)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);

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

    //----------------------------------------------------------------
    // Invokes the rest service to create a member
    //----------------------------------------------------------------
    private class HttpRequestTask extends AsyncTask<String, Void, Member> {
        @Override
        protected Member doInBackground(String... params) {
            Member member = null;
            try {
                String url = getResources().getString(R.string.rest_service_url) + "/member";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                member = new Member();
                member.setFirstName(params[0]);
                member.setLastName(params[1]);
                member.setMobileNumber(Integer.parseInt(params[2]));
                member.setPin(Integer.parseInt(params[3]));

                Boolean output = restTemplate.postForObject(new URI(url), member, Boolean.class);
                if(!output){
                    member = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return member;
        }

        @Override
        protected void onPostExecute(final Member member) {
            if (member != null &&  member.getMobileNumber() >0) {
                AlertDialog alertDialog = new AlertDialog.Builder(ActMemberSignUp.this).create();
                alertDialog.setTitle("Trust Circle Member SignUp");
                alertDialog.setMessage("Member successfully created!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), ActCircleConfig.class);
                            i.putExtra("member_mobile_number", member.getMobileNumber());
                            i.putExtra("member_first_name", member.getFirstName());
                            i.putExtra("member_last_name", member.getLastName());
                            i.putExtra("member_pin", member.getPin());
                            startActivity(i);
                        }
                    });
                alertDialog.show();
            } else {
                Util.showDialogBox(ActMemberSignUp.this, "Trust Circle Member SignUp", "Member creation failed!");
            }
        }
    }
}
