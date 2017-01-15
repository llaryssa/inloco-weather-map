package com.example.llaryssa.inloco_weather_map;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private LatLng latlng;

    private ProgressBar progressBar;
    private FloatingActionButton searchButton;
    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_id);
        mapFragment.getMapAsync(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_id);
        progressBar.setVisibility(View.INVISIBLE);

        layout = (FrameLayout) findViewById(R.id.frame_layout_id);

        searchButton = (FloatingActionButton) findViewById(R.id.search_button_id);
        searchButton.setVisibility(View.INVISIBLE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Engine engine = new Engine(latlng, getApplicationContext());
                engine.getClosestCities();

                // put a loading page
                layout.setAlpha(.7f);
                progressBar.setAlpha(1.0f);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;

        // to call when dropping a pin in the map (to be tested in a real phone)
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng position) {
                gMap.clear();
                latlng = position;
                gMap.addMarker(new MarkerOptions().position(latlng));
                searchButton.setVisibility(View.VISIBLE);

                Log.d("arg0", latlng.toString());
            }
        });

    }

}
