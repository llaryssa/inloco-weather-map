package com.example.llaryssa.inloco_weather_map;

import android.content.Intent;
import android.os.Handler;
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
import com.inlocomedia.android.InLocoMedia;
import com.inlocomedia.android.InLocoMediaOptions;
import com.inlocomedia.android.ads.AdError;
import com.inlocomedia.android.ads.AdRequest;
import com.inlocomedia.android.ads.interstitial.InterstitialAd;
import com.inlocomedia.android.ads.interstitial.InterstitialAdListener;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private LatLng latlng;

    private ProgressBar progressBar;
    private FloatingActionButton searchButton;
    private FrameLayout layout;

    private InLocoMediaOptions options;
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // check if it is back from detail page
        boolean showAd = false;
        if(getIntent().getExtras()!=null)
            if(getIntent().getExtras().containsKey("isBack"))
                showAd = getIntent().getExtras().getBoolean("isBack");

        if (showAd)
            setUpInLocoSDK();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_id);
        mapFragment.getMapAsync(this);

        // progress bar
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_id);
        progressBar.setVisibility(View.INVISIBLE);

        layout = (FrameLayout) findViewById(R.id.frame_layout_id);

        // search button
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

    public void setUpInLocoSDK() {
        // initialize inloco sdk
        options = InLocoMediaOptions.getInstance(this);
        options.setAdsKey("e2331adb120cff928a8a9d35ecd89b658dcbd5cab1f4cc681844241e55eeaea3");
        options.setLogEnabled(true);
        options.setDevelopmentDevices("DE7BA49CD426762CF21D6D7FDE4AAFC9");
        InLocoMedia.init(this, options);

        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setInterstitialAdListener(new InterstitialAdListener() {

            @Override
            public void onAdReady(final InterstitialAd ad) {
                //Called when the view has received an advertisement and is ready to be shown
                //You can call the interstitialAd present method here, or save it for any other moment you wish to present it.
                ad.show();
            }

            @Override
            public void onAdError(InterstitialAd ad, AdError error) {
                //Called when the ad request has failed.
            }

            @Override
            public void onAdOpened(InterstitialAd ad) {
                //Called right before the interstitialAd content is presented on the screen
            }

            @Override
            public void onAdClosed(InterstitialAd ad) {
                //Called right before the interstitialAd content is dismissed on the screen
            }
        });

        AdRequest adRequest = new AdRequest();
        adRequest.setAdUnitId("d1e20695e6de98ddc8e26fb7d1c1b189ff47d96cdf47a02f1763840355717854");
        interstitialAd.loadAd(adRequest);
    }
}
