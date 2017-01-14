package com.example.llaryssa.inloco_weather_map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by llaryssa on 1/14/17.
 */

public class Engine {

    private LatLng latlng;
    private String openWeatherAppId = "9b68a4fab1c08f6766a21b40115a38be";
    private Context context;

    public Engine(LatLng position, Context ctx) {
        latlng = position;
        context = ctx;
    }

    public void getClosestCities() {
        String url = "http://api.openweathermap.org/data/2.5/find?lat="
                + latlng.latitude +"&lon=" + latlng.longitude
                + "&cnt=15&APPID=" + openWeatherAppId;

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("engine", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("engine", error.toString());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


}
