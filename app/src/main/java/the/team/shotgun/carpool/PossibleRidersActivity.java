package the.team.shotgun.carpool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
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
import the.team.shotgun.LandingActivity;
import the.team.shotgun.R;

public class PossibleRidersActivity extends AppCompatActivity {

    ArrayList<Rider> riders;
    ListView lstview_riders_list;
    AppCompatButton btn_start_driving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_possible_riders);

        lstview_riders_list = (ListView) findViewById(R.id.lstview_riders_list);
        btn_start_driving = (AppCompatButton) findViewById(R.id.btn_start_driving);

        btn_start_driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> params = new HashMap<>();
                params.put("customer_id", Constants.currentId);
                JSONObject jsonBody = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.provide_pool_url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = (String)response.get("check");
                            Intent intent = new Intent(PossibleRidersActivity.this, LandingActivity.class);
                            PendingIntent pIntent = PendingIntent.getActivity(PossibleRidersActivity.this, (int) System.currentTimeMillis(), intent, 0);

                            // Build notification
                            // Actions are just fake
                            Notification noti = new NotificationCompat.Builder(PossibleRidersActivity.this)
                                    .setContentTitle("Weather Update")
                                    .setSmallIcon(android.R.drawable.ic_menu_mapmode)
                                    .setContentText("notification")
                                    .setDefaults(Notification.DEFAULT_ALL)
                                    .setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText(message))
                                    .setContentIntent(pIntent).build();
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            // hide the notification after its selected
                            noti.flags |= Notification.FLAG_AUTO_CANCEL;

                            notificationManager.notify(0, noti);

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

                Volley.newRequestQueue(PossibleRidersActivity.this).add(jsonObjectRequest);

                String encodedStr = Constants.dest_location_address.replace(" ","+");
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+encodedStr+"&avoid=tf");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("customer_id", Constants.currentId);
        JSONObject jsonBody = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Constants.provide_pool_url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for( int i =0;i<results.length();i++){
                        Rider rider = new Rider();
                        JSONObject jsonObject = (JSONObject) results.get(i);
                        rider.name = jsonObject.getString("name");
                        rider.phonenumber = jsonObject.getDouble("phonenumber");
                        riders.add(rider);
                    }
                    lstview_riders_list.setAdapter(new RiderAdapter(PossibleRidersActivity.this,riders));

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
        Volley.newRequestQueue(PossibleRidersActivity.this).add(jsonObjectRequest);
    }
}
