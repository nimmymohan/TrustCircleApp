package wsu.csc5991.trustcircle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import wsu.csc5991.trustcircle.vo.Greeting;

//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;


public class ActSignIn extends AppCompatActivity {

    EditText editTextMobileNumberToLogin;
    EditText editTextPasswordToLogin;
    Button buttonSignIn;

    private static final String REST_URL_BASE_DOMAIN = "http://det-sasidhav-m.sea.ds.adp.com:8080/member/mobile/1002003000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laysignin);

        editTextMobileNumberToLogin = (EditText) findViewById(R.id.editTextMobileNumberToLogin);
        editTextPasswordToLogin = (EditText) findViewById(R.id.editTextPasswordToLogin);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isValidInput = true;
                if (editTextMobileNumberToLogin.getText().length() != 10) {
                    editTextMobileNumberToLogin.setError("Mobile number should be 10 digits long!");
                    isValidInput = false;
                }
                if (editTextPasswordToLogin.getText().length() == 0) {
                    editTextPasswordToLogin.setError("Password is required to sign in!");
                    isValidInput = false;
                }
                if (isValidInput) {
                    new HttpRequestTask().execute();
                }
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Greeting> {
        @Override
        protected Greeting doInBackground(String... params) {
            try {
                //String url = REST_URL_BASE_DOMAIN+"/TrustCircleService/TrustCircleService/members/"+params[0];
                String url = REST_URL_BASE_DOMAIN+"/greeting";

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);

                System.out.println(greeting);

//                Client client = ClientBuilder.newClient();
//                WebTarget webTarget = client.target(url);
//                String response = webTarget.request().get(String.class);
//                System.out.println(response);

                return greeting;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            if(greeting != null){
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+greeting.getId());
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+greeting.getContent());

                Toast toast = Toast.makeText(getApplicationContext(), "The content is " + greeting.getContent(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.START, 0, 0);
                toast.show();

            } else {
                System.out.println("Null greetings");
            }
        }
    }
}
