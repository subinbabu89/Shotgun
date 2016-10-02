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
import the.team.shotgun.LandingActivity;
import the.team.shotgun.R;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText edtxtv_user_name;
    private AppCompatEditText edtxtv_user_pass;

    private AppCompatButton btn_login;
    private AppCompatButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtxtv_user_name = (AppCompatEditText)findViewById(R.id.edtxtv_user_name);
        edtxtv_user_pass = (AppCompatEditText)findViewById(R.id.edtxtv_user_pass);

        btn_login = (AppCompatButton) findViewById(R.id.btn_login);
        btn_register = (AppCompatButton) findViewById(R.id.btn_register);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("username", edtxtv_user_name.getText().toString());
                params.put("password", edtxtv_user_pass.getText().toString());
                JSONObject jsonBody = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.login_url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if((boolean)response.get("login")){
                                Constants.currentId = (String)response.get("cust_id");
                                Intent intent = new Intent(MainActivity.this,LandingActivity.class);
                                intent.putExtra("car_owner",(String)response.get("car_owner"));
                                startActivity(intent);
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
                Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
    }
}
