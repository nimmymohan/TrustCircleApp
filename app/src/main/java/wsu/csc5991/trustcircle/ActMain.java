package wsu.csc5991.trustcircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ActMain extends AppCompatActivity {

    Button buttonSignIn;
    Button buttonJoinCircle;
    Button buttonMemberSignUp;
    Button buttonCircleSignUp;
    Button buttonEditCircle;
    Button buttonDeleteCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonJoinCircle = (Button) findViewById(R.id.buttonJoinCircle);
        buttonMemberSignUp = (Button) findViewById(R.id.buttonMemberSignUp);
        buttonCircleSignUp = (Button) findViewById(R.id.buttonCircleSignUp);
        buttonEditCircle = (Button) findViewById(R.id.buttonEditCircle);
        buttonDeleteCircle = (Button) findViewById(R.id.buttonDeleteCircle);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActSignIn.class);
                startActivity(i);
            }
        });
        buttonJoinCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActJoinCircle.class);
                startActivity(i);
            }
        });
        buttonMemberSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActMemberSignUp.class);
                startActivity(i);
            }
        });
        buttonCircleSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActCircleSignUp.class);
                startActivity(i);
            }
        });
        buttonEditCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActEditCircle.class);
                startActivity(i);
            }
        });
        buttonDeleteCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActDeleteCircle.class);
                startActivity(i);
            }
        });
    }
}
