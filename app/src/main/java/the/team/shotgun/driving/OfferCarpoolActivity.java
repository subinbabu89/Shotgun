package the.team.shotgun.driving;

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
import the.team.shotgun.carpool.PossibleRidersActivity;

public class OfferCarpoolActivity extends AppCompatActivity {

    private AppCompatButton btn_carpool_no;
    private AppCompatButton btn_carpool_yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_carpool);

        btn_carpool_no = (AppCompatButton)findViewById(R.id.btn_carpool_no) ;
        btn_carpool_yes = (AppCompatButton)findViewById(R.id.btn_carpool_yes) ;


        btn_carpool_no.setOnClickListener(new View.OnClickListener() {
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
                            Intent intent = new Intent(OfferCarpoolActivity.this, LandingActivity.class);
                            PendingIntent pIntent = PendingIntent.getActivity(OfferCarpoolActivity.this, (int) System.currentTimeMillis(), intent, 0);

                            // Build notification
                            // Actions are just fake
                            Notification noti = new NotificationCompat.Builder(OfferCarpoolActivity.this)
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

                Volley.newRequestQueue(OfferCarpoolActivity.this).add(jsonObjectRequest);

                String encodedStr = Constants.dest_location_address.replace(" ","+");
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+encodedStr+"&avoid=tf");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btn_carpool_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OfferCarpoolActivity.this, PossibleRidersActivity.class));
            }
        });
    }
}
