package com.example.llaryssa.inloco_weather_map;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by llaryssa on 1/14/17.
 */

public class Engine {

    private LatLng latlng;
    private String openWeatherAppId = "9b68a4fab1c08f6766a21b40115a38be";
    private Context context;

    // Cities
    private String[] cityNames;
    private float[] cityMaxT;
    private float[] cityMinT;
    private String[] cityDescriptions;

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
                        // parsing and populating data
                        parseJson(response);

                        Log.v("parse", response);
                        Log.v("parse", "finished parsing");

                        Intent intent = new Intent(context, ListActivity.class);
                        intent.putExtra("names", cityNames);
                        intent.putExtra("descriptions", cityDescriptions);
                        intent.putExtra("minT", cityMinT);
                        intent.putExtra("maxT", cityMaxT);
                        context.startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("engine", error.toString() + " / " + error.getMessage());
                        Log.v("engine", "ERRO");

////                         This is raising too many errors
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        builder.setMessage(error.getMessage());
//                        builder.setTitle(R.string.error);
//                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent = new Intent(context, MapsActivity.class);
//                                context.startActivity(intent);
//                            }
//                        });
//                        AlertDialog dialog = builder.create();
//                        dialog.show();

                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("latitude", latlng.latitude);
                        intent.putExtra("longitude", latlng.longitude);
                        context.startActivity(intent);
                        Toast.makeText(context, error.getMessage() + ". Tente Novamente.", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void parseJson(String response) {
        JSONObject json = null;

        try {
            json = new JSONObject(response);
            int length = json.getInt("count");

            cityNames = new String[length];
            cityDescriptions = new String[length];
            cityMaxT = new float[length];
            cityMinT = new float[length];

            JSONArray list = json.getJSONArray("list");
            String name, desc;
            float maxT, minT;

            for (int i = 0; i < length; ++i) {
                JSONObject jsonCity = list.getJSONObject(i);
                JSONObject jsonCityMain = jsonCity.getJSONObject("main");
                JSONArray jsonCityWeather = jsonCity.getJSONArray("weather");

                name = jsonCity.getString("name");
                maxT = (float) jsonCityMain.getDouble("temp_max");
                minT = (float) jsonCityMain.getDouble("temp_min");
                desc = "";

                for (int j = 0; j < jsonCityWeather.length(); ++j) {
                    JSONObject jsonCityWeatherEntry = jsonCityWeather.getJSONObject(j);
                    desc += jsonCityWeatherEntry.getString("description");
                }

                // temperatures are in Kelvin
                cityNames[i] = name;
                cityMinT[i] = minT - 273;
                cityMaxT[i] = maxT - 273;
                cityDescriptions[i] = desc;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
