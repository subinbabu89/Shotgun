package the.team.shotgun.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import the.team.shotgun.Constants;
import the.team.shotgun.R;

public class RegistrationActivity extends AppCompatActivity {

    private AppCompatEditText txtv_first_name;
    private AppCompatEditText txtv_last_name;
    private AppCompatEditText txtv_user_name;
    private AppCompatEditText txtv_password;
    private AppCompatEditText txtv_phone_number;
    private AppCompatEditText car_owner_status;

    private AppCompatButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        txtv_first_name = (AppCompatEditText) findViewById(R.id.txtv_first_name);
        txtv_last_name = (AppCompatEditText) findViewById(R.id.txtv_last_name);
        txtv_user_name = (AppCompatEditText) findViewById(R.id.txtv_user_name);
        txtv_password = (AppCompatEditText) findViewById(R.id.txtv_password);
        txtv_phone_number = (AppCompatEditText) findViewById(R.id.txtv_phone_number);
        car_owner_status = (AppCompatEditText) findViewById(R.id.car_owner_status);

        btn_register = (AppCompatButton) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", txtv_first_name.getText().toString());
                params.put("lastname", txtv_last_name.getText().toString());
                params.put("username", txtv_user_name.getText().toString());
                params.put("password", txtv_password.getText().toString());
                params.put("number", txtv_phone_number.getText().toString());
                params.put("carowner", car_owner_status.getText().toString());
                JSONObject jsonBody = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.registration_url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("message").toString().equalsIgnoreCase("success")){
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                Volley.newRequestQueue(RegistrationActivity.this).add(jsonObjectRequest);
            }
        });
    }
}
