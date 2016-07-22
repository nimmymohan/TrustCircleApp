package wsu.csc5991.trustcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ActCircleSignUp extends AppCompatActivity {

    EditText editTextCircleName;
    EditText editTextCirclePassword;
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
        editTextPrimaryMemberMobileNumber = (EditText) findViewById(R.id.editTextPrimaryMemberMobileNumber);
        editTextPrimaryMemberPassword = (EditText) findViewById(R.id.editTextPrimaryMemberPassword);
        buttonCreateCircle = (Button) findViewById(R.id.buttonCreateCircle);

        buttonCreateCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editTextCircleName.getText().length() == 0) {
                    editTextCircleName.setError("Circle name is required to create circle!");
                }
                if (editTextCirclePassword.getText().length() == 0) {
                    editTextCirclePassword.setError("Circle password is required to create circle!");
                }
                if (editTextPrimaryMemberMobileNumber.getText().length() != 10) {
                    editTextPrimaryMemberMobileNumber.setError("Primary member mobile number should be 10 digits long!");
                }
                if (editTextPrimaryMemberPassword.getText().length() == 0) {
                    editTextPrimaryMemberPassword.setError("Primary member password is required to create circle!");
                }
            }
        });
    }
}
