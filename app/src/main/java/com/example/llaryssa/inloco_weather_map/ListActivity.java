package com.example.llaryssa.inloco_weather_map;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

public class ListActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    // Cities
    private String[] cityNames;
    private float[] cityMaxT;
    private float[] cityMinT;
    private String[] cityDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.selectCity);

        // getting the extras
        Intent intent = getIntent();
        cityNames = intent.getStringArrayExtra("names");
        cityDescriptions = intent.getStringArrayExtra("descriptions");
        cityMaxT = intent.getFloatArrayExtra("maxT");
        cityMinT = intent.getFloatArrayExtra("minT");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityNames);

        ListView listView = (ListView) findViewById(R.id.list_view_id);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ListActivity.this, CityDetail.class);
                intent.putExtra("name", cityNames[position]);
                intent.putExtra("description", cityDescriptions[position]);
                intent.putExtra("maxT", cityMaxT[position]);
                intent.putExtra("minT", cityMinT[position]);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // so it wont be back to loading page
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
