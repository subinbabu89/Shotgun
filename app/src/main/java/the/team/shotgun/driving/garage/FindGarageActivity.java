package the.team.shotgun.driving.garage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import the.team.shotgun.Constants;
import the.team.shotgun.R;
import the.team.shotgun.driving.SelectDrivingDestinationActivity;

public class FindGarageActivity extends AppCompatActivity {

    ArrayList<Garage> garages;
    ListView lstview_garage_list;

    AppCompatButton btn_repair_work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_garage);

        lstview_garage_list = (ListView)findViewById(R.id.lstview_garage_list);
        btn_repair_work = (AppCompatButton) findViewById(R.id.btn_repair_work);

        btn_repair_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindGarageActivity.this,SelectDrivingDestinationActivity.class));
            }
        });

        garages = new ArrayList<>();

        Map<String, String> params = new HashMap<>();
        params.put("customer_id", Constants.currentId);
        params.put("answer", "false");
        params.put("source_lat", Constants.current_lat);
        params.put("source_lon", Constants.current_lng);
        JSONObject jsonBody = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.question_url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for( int i =0;i<results.length();i++){
                        Garage garage = new Garage();
                        JSONObject jsonObject = (JSONObject) results.get(i);
                        garage.garage_name = jsonObject.getString("garage_name");
                        garage.garage_src_lat = jsonObject.getDouble("garage_src_lat");
                        garage.garage_src_lon = jsonObject.getDouble("garage_src_lon");
                        garages.add(garage);
                    }
                    lstview_garage_list.setAdapter(new GarageAdapter(FindGarageActivity.this,garages));

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
        Volley.newRequestQueue(FindGarageActivity.this).add(jsonObjectRequest);
    }
}
