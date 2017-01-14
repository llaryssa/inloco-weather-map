package com.example.llaryssa.inloco_weather_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

public class CityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("description");
        float maxT = intent.getFloatExtra("maxT", 0);
        float minT = intent.getFloatExtra("minT", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);


        TextView textView = (TextView) findViewById(R.id.text_view_id);
        String text = "Temperatura mínima (em Celsius): " + minT + "\nTemperatura máxima (em Celsius): " + maxT + "\nDescrição do tempo: " + desc;

        int begin1 = 0;
        int end1 = 33;

        int begin2 = end1 + (minT + "").length();
        int end2 = begin2 + 34;

        int begin3 = end2 + (maxT + "").length();
        int end3 = begin3 + 20;

        final SpannableStringBuilder str = new SpannableStringBuilder(text);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), begin1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), begin2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), begin3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(str);

    }
}
