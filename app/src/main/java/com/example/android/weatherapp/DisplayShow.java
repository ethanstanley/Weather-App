package com.example.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class DisplayShow extends AppCompatActivity {
    private TextView mShowSources, mShowName ;
    private String output, output2;
    private Boolean nothappen;
    private String url, show, id, sources, input, maincar, star, date, genre;
    RequestQueue queue;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("EzFinder");
        show = getIntent().getStringExtra("show") + "\n";
        id = getIntent().getStringExtra("id");
        sources = getIntent().getStringExtra("sources").replace("_", " ");
        input = getIntent().getStringExtra("maincar");
        star = getIntent().getStringExtra("star");
        date = getIntent().getStringExtra("date");
        genre = getIntent().getStringExtra("genre");
        //genre = getIntent().getStringExtra("genre");
        mShowName = (TextView)findViewById(R.id.show);
        mShowName.setText(show);
        mShowSources = (TextView)findViewById(R.id.show_sources);
        nothappen = false;
        if (input.length() < 2){
            mShowSources.setText(show + " is a "+ genre.toLowerCase() + " show and was released in " + date + ". " + sources + " and it stars " + star + ".\n");
        }
        else {
            mShowSources.setText(show + " is a " + genre.toLowerCase() + " show and was released in " + date + ". " + sources + " and it stars " + star + " as " + input + ".\n");
        }
        mBackButton = (Button)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayShow.this, ShowActivity.class);
                startActivity(i);
            }
        });
        // Instantiate the RequestQueue.
// Request a string response from the provided UR

    }

}
