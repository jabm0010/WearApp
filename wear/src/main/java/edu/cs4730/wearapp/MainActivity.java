package edu.cs4730.wearapp;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Random;

/*
  * simple app that shows random number.
  *
  * Note there is a round, notround, and layout directory.  in support.wear it will now select the
  * "correct" one.  It square uses notround, round uses round and layout appears unused in the wear.
  *
  * bluetooth debugging: https://developer.android.com/training/wearables/apps/debugging.html
  *
  * remember these gpt bluetooth:
  * adb forward tcp:4444 localabstract:/adb-hub
    adb connect 127.0.0.1:4444
 */

public class MainActivity extends WearableActivity {

    private static final String TAG = "MainActivity";
    private TextView mTextView;
    Random myRandom = new Random();
    ImageButton ib;
    private String baseURLBeSoccer = "http://apiclient.resultados-futbol.com/scripts/api/api.php?key=39cc50aeaf79cb0f11ca0eeab8d9ee74&tz=Europe/Madrid&format=json&req=matchsday&";
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        /*
        mTextView.setText("   " + myRandom.nextInt(10) + " ");
        //get the imagebutton (checkmark) and set up the listener for a random number.
        ib = (ImageButton) findViewById(R.id.myButton);
        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTextView.setText("   " + myRandom.nextInt(10) + " ");

            }
        });
*/
        date = "date=2020-1-18&limit=1";
        //String petition = baseURLBeSoccer + date;
        String petition = "http://apiclient.resultados-futbol.com/scripts/api/api.php?key=39cc50aeaf79cb0f11ca0eeab8d9ee74&tz=Europe/Madrid&format=&req=matchs&league=1&round=7&order=twin&twolegged=1&year=2020";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, petition, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        response.toString();
                       // mTextView.setText("Response: " + response.toString());
                        Log.d(TAG, "He hecho la peticion");
                        Log.d(TAG,  response.toString());
                       /*
                        try {
                            Log.d(TAG, response.getJSONArray("matches")toString());
                        }catch(JSONException j){

                        }
                        */

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());

                    }


                });


        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        requestQueue.add(jsonObjectRequest);


        // Enables Always-on
        setAmbientEnabled();
    }
}
