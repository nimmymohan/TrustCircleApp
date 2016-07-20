package wsu.csc5991.trustcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        editTextMobileNumber = (EditText) findViewById(R.id.editTextMobileNumber);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        buttonCreateMember = (Button) findViewById(R.id.buttonCreateMember);

        buttonCreateMember.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editTextMobileNumber.getText().length() != 10) {
                    editTextMobileNumber.setError("Mobile number should be 10 digits long!");
                }
                if (editTextFirstName.getText().length() == 0) {
                    editTextFirstName.setError("First name is required to create member!");
                }
                if (editTextLastName.getText().length() == 0) {
                    editTextLastName.setError("Last name is required to create member!");
                }
                if (editTextPassword.getText().length() == 0) {
                    editTextPassword.setError("Password is required to create member!");
                }
                if (editTextConfirmPassword.getText().length() == 0) {
                    editTextConfirmPassword.setError("Please confirm the password!");
                }
                if (!editTextConfirmPassword.getText().equals(editTextPassword.getText())) {
                    editTextConfirmPassword.setError("The passwords does not match!");
                }
            }
        });
    }
}
