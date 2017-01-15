package com.example.llaryssa.inloco_weather_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_id);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CityDetail.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar.setTitle(name);

        String minTstr = minT + "";
        String maxTstr = maxT + "";
        minTstr = minTstr.substring(0, minTstr.indexOf(".")+3);
        maxTstr = maxTstr.substring(0, maxTstr.indexOf(".")+3);

        TextView textView = (TextView) findViewById(R.id.text_view_id);
        String text = "Temperatura mínima (em Celsius)\n" + minTstr + "\n\nTemperatura máxima (em Celsius)\n" + maxTstr + "\n\nDescrição do tempo\n" + desc;

        int begin1 = 0;
        int end1 = 32;

        int begin2 = end1 + (minTstr + "").length();
        int end2 = begin2 + 34;

        int begin3 = end2 + (maxTstr + "").length();
        int end3 = begin3 + 20;

        final SpannableStringBuilder str = new SpannableStringBuilder(text);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), begin1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), begin2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), begin3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(str);

    }
}
