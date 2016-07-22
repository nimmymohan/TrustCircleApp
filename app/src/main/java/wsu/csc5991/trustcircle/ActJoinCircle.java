package wsu.csc5991.trustcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
                if (editTextCircleName.getText().length() == 0) {
                    editTextCircleName.setError("Circle name is required to join circle!");
                }
                if (editTextCirclePassword.getText().length() == 0) {
                    editTextCirclePassword.setError("Circle password is required to join circle!");
                }
                if (editTextMemberMobileNumber.getText().length() != 10) {
                    editTextMemberMobileNumber.setError("Mobile number should be 10 digits long!");
                }
                if (editTextMemberPassword.getText().length() == 0) {
                    editTextMemberPassword.setError("Password is required to join circle!");
                }
            }
        });
    }
}
